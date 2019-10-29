package com.henyep.ib.terminal.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.henyep.ib.terminal.api.dto.db.IbAccountDetailsBean;
import com.henyep.ib.terminal.api.dto.db.IbProfileBean;
import com.henyep.ib.terminal.api.dto.db.MarginInBean;
import com.henyep.ib.terminal.api.dto.request.marginin.BatchApproveMarginInRequest;
import com.henyep.ib.terminal.api.dto.request.marginin.GetMarginInRequest;
import com.henyep.ib.terminal.api.dto.request.marginin.InsertMarginInRequest;
import com.henyep.ib.terminal.api.dto.request.marginin.UpdateMarginInRequest;
import com.henyep.ib.terminal.api.dto.request.marginin.UpdateMarginInRequestDto;
import com.henyep.ib.terminal.api.dto.response.marginin.BatchApproveMarginInResponseDto;
import com.henyep.ib.terminal.api.dto.response.marginin.ErrorCodeMarginInModel;
import com.henyep.ib.terminal.api.dto.response.marginin.GetMarginInResponseDto;
import com.henyep.ib.terminal.api.dto.response.marginin.InsertMarginInResponseDto;
import com.henyep.ib.terminal.api.dto.response.marginin.UpdateMarginInResponseDto;
import com.henyep.ib.terminal.server.dao.IbAccountDetailsDao;
import com.henyep.ib.terminal.server.dao.IbProfileDao;
import com.henyep.ib.terminal.server.dao.MarginInDao;
import com.henyep.ib.terminal.server.global.Constants;
import com.henyep.ib.terminal.server.service.MarginInService;
import com.henyep.ib.terminal.server.util.RateExchangeUtil;

@Service("MarginInService")
public class MarginInServiceImpl extends AbstractServiceImpl implements MarginInService {

	private MarginInDao marginInDao;
	private IbAccountDetailsDao ibAccountDetailsDao;
	private IbProfileDao ibProfileDao;

	@Resource(name = "messageSource")
	private MessageSource messageSource;

	@Resource(name = "RateExchangeUtil")
	private RateExchangeUtil rateExchangeUtil;

	@Autowired
	public MarginInServiceImpl(MarginInDao marginInDao, IbAccountDetailsDao ibAccountDetailsDao, IbProfileDao ibProfileDao) {
		this.marginInDao = marginInDao;
		this.ibAccountDetailsDao = ibAccountDetailsDao;
		this.ibProfileDao = ibProfileDao;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public UpdateMarginInResponseDto updateMarginIn(UpdateMarginInRequest request) throws Exception {

		String sender = getSender(request.getHeader());
		int marginInId = request.getBody().getMargin_in_id();

		List<MarginInBean> marginInBeans = marginInDao.getMarginInByKey(marginInId);
		MarginInBean marginInBean = marginInBeans.get(0);

		String oriStatus = marginInBean.getStatus();

		UpdateMarginInRequestDto updateMarginInRequestDto = request.getBody();
		String updatedStatus = marginInBean.getStatus();
		if (updateMarginInRequestDto.getStatus() != null)
			updatedStatus = updateMarginInRequestDto.getStatus();
		if (updateMarginInRequestDto.getCategory() != null)
			marginInBean.setCategory(updateMarginInRequestDto.getCategory());
		if (updateMarginInRequestDto.getAmount() != null)
			marginInBean.setAmount(updateMarginInRequestDto.getAmount());
		if (updateMarginInRequestDto.getTrade_date() != null)
			marginInBean.setTrade_date(updateMarginInRequestDto.getTrade_date());
		if (updateMarginInRequestDto.getCurrency() != null)
			marginInBean.setCurrency(updateMarginInRequestDto.getCurrency());
		if (updateMarginInRequestDto.getCategory() != null)
			marginInBean.setCategory(updateMarginInRequestDto.getCategory());
		if (updateMarginInRequestDto.getComment() != null)
			marginInBean.setComment(updateMarginInRequestDto.getComment());

		// exchanged rate and account amount
		List<IbAccountDetailsBean> ibAccountDetails = ibAccountDetailsDao.getIbAccountDetailsByKey(marginInBean.getAccount());
		IbAccountDetailsBean ibAccountDetail = ibAccountDetails.get(0);

		String accountCurrency = ibAccountDetail.getCurrency();

		marginInBean.setAccount_currency(accountCurrency);
		String currency = marginInBean.getCurrency();
		if (updateMarginInRequestDto.getCurrency() != null) {
			currency = updateMarginInRequestDto.getCurrency();
		}
		Double amount = marginInBean.getAmount();
		if (updateMarginInRequestDto.getAmount() != null) {
			amount = updateMarginInRequestDto.getAmount();
		}

		Double[] accountAmountSnipples = rateExchangeUtil.getExchangePrice(currency, accountCurrency, amount);
		double exchangeRate = accountAmountSnipples[0];
		double accountAmount = accountAmountSnipples[1];
		double oriAccountAmount = marginInBean.getAccount_amount();
		marginInBean.setAccount_amount(accountAmount);
		marginInBean.setExchange_rate(exchangeRate);

		marginInBean.setLast_update_user(sender);
		logger.debug("Updating margin in content...");
		marginInDao.updateMarginIn(marginInBean);

		// update account pending margin
		if (updatedStatus.equals(Constants.MARGIN_IN_STATUS_PENDING)) {
			List<IbAccountDetailsBean> ibAccounts = ibAccountDetailsDao.getIbAccountDetailsByKey(marginInBean.getAccount());
			IbAccountDetailsBean targetIbAccount = ibAccounts.get(0);
			double oriPendingCommission = targetIbAccount.getPending_commission();
			targetIbAccount.setPending_commission(oriPendingCommission - oriAccountAmount + accountAmount);
			ibAccountDetailsDao.updateIbAccountDetails(targetIbAccount);
		}

		// update margin in status
		if (!oriStatus.equals(updatedStatus) && oriStatus.equals(Constants.MARGIN_IN_STATUS_PENDING)) {
			if (updatedStatus.equals(Constants.MARGIN_IN_STATUS_EXECUTED)) {
				executeMarginIn(request);
			} else if (updatedStatus.equals(Constants.MARGIN_IN_STATUS_CANCELLED)) {
				cancelMarginIn(request);
			} else if (updatedStatus.equals(Constants.MARGIN_IN_STATUS_REJECTED)) {
				rejectMarginIn(request);
			}
		}

		UpdateMarginInResponseDto response = new UpdateMarginInResponseDto();
		List<MarginInBean> updatedMarginInBeans = marginInDao.getMarginInByKey(marginInBean.getId());
		if (updatedMarginInBeans.size() > 0) {
			response.setMarginInBean(updatedMarginInBeans.get(0));
		}
		return response;
	}

	private void executeMarginIn(UpdateMarginInRequest request) throws Exception {

		Integer marginInId = request.getBody().getMargin_in_id();
		logger.info("Start execute margin, marginIn ID: " + marginInId);

		List<MarginInBean> marginInBeans = marginInDao.getMarginInByKey(marginInId);

		MarginInBean targetMarginInBean = marginInBeans.get(0);
		targetMarginInBean.setLast_update_user(getSender(request.getHeader()));
		executeMarginIn(targetMarginInBean);
	}

	private void executeMarginIn(MarginInBean marginInBean) throws Exception {
		logger.info("MarginIn Bean: " + marginInBean.toString());

		String ibCode = marginInBean.getAccount();
		double marginInPendingCommission = marginInBean.getAccount_amount();

		List<IbAccountDetailsBean> ibAccounts = ibAccountDetailsDao.getIbAccountDetailsByKey(ibCode);
		IbAccountDetailsBean targetIbAccount = ibAccounts.get(0);
		double oriPendingCommission = targetIbAccount.getPending_commission();
		double oriAccountBalance = targetIbAccount.getAccount_balance();
		targetIbAccount.setPending_commission(oriPendingCommission - marginInPendingCommission);
		targetIbAccount.setAccount_balance(oriAccountBalance + marginInPendingCommission);

		logger.debug("Updating account detail...");
		ibAccountDetailsDao.updateIbAccountDetails(targetIbAccount);

		logger.debug("Updating margin in status to " + Constants.MARGIN_IN_STATUS_EXECUTED);
		marginInBean.setStatus(Constants.MARGIN_IN_STATUS_EXECUTED);

		marginInDao.updateMarginIn(marginInBean);

		logger.info("Finish!");
	}

	private void cancelMarginIn(UpdateMarginInRequest request) throws Exception {
		updateMarginInStatus(request, Constants.MARGIN_IN_STATUS_CANCELLED);
	}

	private void rejectMarginIn(UpdateMarginInRequest request) throws Exception {
		updateMarginInStatus(request, Constants.MARGIN_IN_STATUS_REJECTED);
	}

	private void updateMarginInStatus(UpdateMarginInRequest request, String status) throws Exception {
		Integer marginInId = request.getBody().getMargin_in_id();

		MarginInBean targetMarginInBean = getMarginInBean(marginInId);
		logger.info("MarginIn Bean: " + targetMarginInBean.toString());

		String ibCode = targetMarginInBean.getAccount();
		double marginInPendingCommission = targetMarginInBean.getAccount_amount();

		IbAccountDetailsBean targetIbAccount = getIbAccountDetail(ibCode);

		double oriPendingCommission = targetIbAccount.getPending_commission();

		targetIbAccount.setPending_commission(oriPendingCommission - marginInPendingCommission);
		targetMarginInBean.setLast_update_user(getSender(request.getHeader()));
		targetMarginInBean.setLast_update_time(new Date());
		targetMarginInBean.setStatus(status);

		logger.info("Update margin in status to " + status);
		marginInDao.updateMarginIn(targetMarginInBean);

		logger.info("Update account pending commission from " + oriPendingCommission + " to " + targetIbAccount.getPending_commission());
		ibAccountDetailsDao.updateIbAccountDetails(targetIbAccount);

	}

	private MarginInBean getMarginInBean(Integer marginInId) throws Exception {

		List<MarginInBean> marginInBeans = marginInDao.getMarginInByKey(marginInId);
		MarginInBean targetMarginInBean = marginInBeans.get(0);
		return targetMarginInBean;
	}

	private IbAccountDetailsBean getIbAccountDetail(String ibCode) throws Exception {
		List<IbAccountDetailsBean> ibAccounts = ibAccountDetailsDao.getIbAccountDetailsByKey(ibCode);
		IbAccountDetailsBean targetIbAccount = ibAccounts.get(0);

		return targetIbAccount;
	}

	public List<String> validateUpdateMarginIn(UpdateMarginInRequest request) throws Exception {

		List<String> errorMsgs = new ArrayList<String>();

		String category = request.getBody().getCategory();
		String status = request.getBody().getStatus();

		// check category
		if (category != null) {
			if (!category.equals(Constants.MARGIN_IN_CATEGORY_ADJUSTMENT) && !category.equals(Constants.MARGIN_IN_CATEGORY_BONUS)
					&& !category.equals(Constants.MARGIN_IN_CATEGORY_DEPOSIT) && !category.equals(Constants.MARGIN_IN_CATEGORY_INTERNAL_TRANSFER)
					&& !category.equals(Constants.MARGIN_IN_CATEGORY_REBATE))
				errorMsgs.add(Constants.ERR_MARGIN_IN_CATEGORY_NOT_EXIST);
		}
		// check status
		if (status != null) {
			if (!status.equals(Constants.MARGIN_IN_STATUS_CANCELLED) && !status.equals(Constants.MARGIN_IN_STATUS_EXECUTED)
					&& !status.equals(Constants.MARGIN_IN_STATUS_PENDING) && !status.equals(Constants.MARGIN_IN_STATUS_REJECTED)) {
				errorMsgs.add(Constants.ERR_MARGIN_IN_INVALID_STATUS);
			}
		}

		List<MarginInBean> marginInBeans = marginInDao.getMarginInByKey(request.getBody().getMargin_in_id());
		if (marginInBeans.size() <= 0) {
			errorMsgs.add(Constants.ERR_MARGIN_IN_ID_NOT_EXIST);

		} else {
			MarginInBean targetMarginInBean = marginInBeans.get(0);

			String account = targetMarginInBean.getAccount();

			// check ib profile
			List<IbProfileBean> ibProfileBeans = ibProfileDao.getIbProfileByIbCode(account);
			if (ibProfileBeans.size() <= 0) {
				errorMsgs.add(Constants.ERR_COMMON_IB_PROFILE_NOT_EXIST);
			}

			// check ib account detail
			List<IbAccountDetailsBean> ibAccountDetails = ibAccountDetailsDao.getIbAccountDetailsByKey(account);
			if (ibAccountDetails.size() <= 0) {
				errorMsgs.add(Constants.ERR_COMMON_ACCOUNT_DETAIL_NOT_EXIST);
			}

			// check margin in status is pending?
			if (!targetMarginInBean.getStatus().equals(Constants.MARGIN_IN_STATUS_PENDING)) {
				errorMsgs.add(Constants.ERR_MARGIN_IN_STATUS_NOT_PENDING);
			}

			if (errorMsgs.size() == 0) {
				IbAccountDetailsBean ibAccountDetail = ibAccountDetails.get(0);

				if (status != null) {
					// status changed
					if (status.equals(Constants.MARGIN_IN_STATUS_EXECUTED) || status.equals(Constants.MARGIN_IN_STATUS_CANCELLED)
							|| status.equals(Constants.MARGIN_IN_STATUS_REJECTED)) {

						// execute, cancel and reject margin in

						String accountCurrency = ibAccountDetail.getCurrency();

						targetMarginInBean.setAccount_currency(accountCurrency);

						String currency = targetMarginInBean.getCurrency();
						if (request.getBody().getCurrency() != null) {
							currency = request.getBody().getCurrency();
						}

						Double amount = targetMarginInBean.getAmount();
						if (request.getBody().getAmount() != null) {
							amount = request.getBody().getAmount();
						}
						Double[] accountAmountSnipples = rateExchangeUtil.getExchangePrice(currency, accountCurrency, amount);

						double accountAmount = accountAmountSnipples[1];

						// execute, cancel or reject margin in
						if (ibAccountDetails.get(0).getPending_commission() < accountAmount) {
							// not enough pending commision to execute this
							// margin
							errorMsgs.add(Constants.ERR_MARGIN_IN_IB_NOT_ENOUGH_PENDING_COMMISSION);
						}
					}
				} else {
					// status not changed
					if (ibAccountDetail.getPending_commission() < targetMarginInBean.getAccount_amount()) {
						errorMsgs.add(Constants.ERR_MARGIN_IN_IB_NOT_ENOUGH_PENDING_COMMISSION);
					}
				}
			}

		}

		return errorMsgs;
	}

	@Override
	public List<String> validateGetMarginIn(GetMarginInRequest request) throws Exception {

		List<String> errorCodes = new ArrayList<String>();

		return errorCodes;
	}

	@Override
	public GetMarginInResponseDto getMarginIn(GetMarginInRequest request) throws Exception {

		Date startDate = request.getBody().getStartDate();
		Date endDate = request.getBody().getEndDate();
		MarginInBean searchBean = request.getBody().getMarginInBean();

		List<MarginInBean> rtnBeans = marginInDao.getMarginInByExample(startDate, endDate, searchBean);
		GetMarginInResponseDto dto = new GetMarginInResponseDto();
		dto.setMarginInBeans(rtnBeans);

		return dto;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public InsertMarginInResponseDto insertMarginIn(InsertMarginInRequest request) throws Exception {
		String account = request.getBody().getAccount();
		Double amount = request.getBody().getAmount();
		String comment = request.getBody().getComment();
		String currency = request.getBody().getCurrency();
		Date tradeDate = request.getBody().getTrade_date();
		String category = request.getBody().getCategory();

		MarginInBean marginInBean = new MarginInBean();
		marginInBean.setAccount(account);
		List<IbAccountDetailsBean> ibAccountDetails = ibAccountDetailsDao.getIbAccountDetailsByKey(account);
		IbAccountDetailsBean ibAccountDetail = ibAccountDetails.get(0);

		List<IbProfileBean> ibProfileBeans = ibProfileDao.getIbProfileByIbCode(account);
		IbProfileBean ibProfile = ibProfileBeans.get(0);

		String accountCurrency = ibAccountDetail.getCurrency();

		marginInBean.setAccount_currency(accountCurrency);
		Double[] accountAmountSnipples = rateExchangeUtil.getExchangePrice(currency, accountCurrency, amount);
		double exchangeRate = accountAmountSnipples[0];
		double accountAmount = accountAmountSnipples[1];
		marginInBean.setAccount_amount(accountAmount);
		marginInBean.setAmount(amount);
		marginInBean.setBrand_code(ibProfile.getBrand_code());
		marginInBean.setCategory(category);
		marginInBean.setComment(comment);
		marginInBean.setCreate_user(null);
		marginInBean.setCurrency(currency);
		marginInBean.setExchange_rate(exchangeRate);
		marginInBean.setStatus(Constants.MARGIN_IN_STATUS_EXECUTED);
		marginInBean.setTrade_date(tradeDate);

		marginInBean.setTransfer_id(0);

		marginInBean.setCreate_user(getSender(request.getHeader()));
		int marginInId = marginInDao.saveMarginIn(marginInBean);

		List<MarginInBean> marginInBeans = marginInDao.getMarginInByKey(marginInId);
		MarginInBean insertedMarginIn = null;
		if (marginInBeans.size() > 0) {
			insertedMarginIn = marginInBeans.get(0);
		}

		// update ib account balance
		List<IbAccountDetailsBean> ibAccounts = ibAccountDetailsDao.getIbAccountDetailsByKey(marginInBean.getAccount());
		IbAccountDetailsBean targetIbAccount = ibAccounts.get(0);
		Double oriAccountBalance = targetIbAccount.getAccount_balance();
		targetIbAccount.setAccount_balance(oriAccountBalance + accountAmount);

		logger.info("Updating account detail...");
		ibAccountDetailsDao.updateIbAccountDetails(targetIbAccount);

		// generate insert margin in response
		InsertMarginInResponseDto responseDto = new InsertMarginInResponseDto();
		responseDto.setMarginInBean(insertedMarginIn);
		return responseDto;
	}

	@Override
	public List<String> validateInsertMarginIn(InsertMarginInRequest request) throws Exception {
		List<String> errorList = new ArrayList<String>();

		String account = request.getBody().getAccount();

		List<IbProfileBean> ibProfileBeans = ibProfileDao.getIbProfileByIbCode(account);
		if (ibProfileBeans.size() <= 0) {
			errorList.add(Constants.ERR_COMMON_IB_PROFILE_NOT_EXIST);
		}

		List<IbAccountDetailsBean> ibAccountDetails = ibAccountDetailsDao.getIbAccountDetailsByKey(account);
		if (ibAccountDetails.size() <= 0) {
			errorList.add(Constants.ERR_COMMON_ACCOUNT_DETAIL_NOT_EXIST);
		}

		String category = request.getBody().getCategory();

		if (!category.equals(Constants.MARGIN_IN_CATEGORY_ADJUSTMENT) && !category.equals(Constants.MARGIN_IN_CATEGORY_BONUS)
				&& !category.equals(Constants.MARGIN_IN_CATEGORY_DEPOSIT) && !category.equals(Constants.MARGIN_IN_CATEGORY_INTERNAL_TRANSFER)
				&& !category.equals(Constants.MARGIN_IN_CATEGORY_REBATE))
			errorList.add(Constants.ERR_MARGIN_IN_CATEGORY_NOT_EXIST);
		// check category
		return errorList;
	}

	@Override
	public List<String> validateBatchApproveMarginInRequest(BatchApproveMarginInRequest request) throws Exception {
		List<String> errorList = new ArrayList<String>();

		// List<String> inputMarginInIds = request.getBody().getMargin_in_ids();
		// List<MarginInBean> dbMarginInBeans =
		// marginInDao.getMarginInByKeys(inputMarginInIds);
		// List<String> dbMarginOutIds = new ArrayList<String>();
		// for(MarginInBean dbMarginInBean : dbMarginInBeans){
		// dbMarginOutIds.add(dbMarginInBean.getId().toString());
		// }
		//
		// for(String inputMarginOutId : inputMarginInIds){
		// if(!dbMarginOutIds.contains(inputMarginOutId)){
		// errorList.add(Constants.ERR_MARGIN_IN_ID_NOT_EXIST);
		// break;
		// }
		// }

		// if(errorList.size() == 0){
		// for(MarginInBean dbMarginInBean : dbMarginInBeans){
		// if(!dbMarginInBean.getStatus().equals(Constants.MARGIN_IN_STATUS_PENDING)){
		// // margin out id not pending
		// errorList.add(Constants.ERR_MARGIN_IN_STATUS_NOT_PENDING);
		// break;
		// }
		// }
		// }
		return errorList;
	}

	@Override
	public BatchApproveMarginInResponseDto batchApproveMarginIn(BatchApproveMarginInRequest request) throws Exception {

		List<String> marginInIds = request.getBody().getMargin_in_ids();
		List<MarginInBean> maginInBeans = marginInDao.getMarginInByKeys(marginInIds);
		List<String> updatedMarginInIds = new ArrayList<String>();
		List<String> dbMarginInIds = new ArrayList<String>();
		List<ErrorCodeMarginInModel> notUpdatedMarginIns = new ArrayList<ErrorCodeMarginInModel>();

		// get margin in from db
		for (MarginInBean marginInBean : maginInBeans) {
			dbMarginInIds.add(marginInBean.getId().toString());

			String errorCode = null;

			if (marginInBean.getStatus().equals(Constants.MARGIN_IN_STATUS_PENDING)) {
				// correct status (pending)
				List<IbAccountDetailsBean> ibAccountDetails = ibAccountDetailsDao.getIbAccountDetailsByKey(marginInBean.getAccount());
				if (ibAccountDetails.size() <= 0) {
					// account detail not exist
					errorCode = Constants.ERR_COMMON_ACCOUNT_DETAIL_NOT_EXIST;
				} else {
					IbAccountDetailsBean ibAccountDetail = ibAccountDetails.get(0);
					if (ibAccountDetail.getPending_commission() < marginInBean.getAccount_amount()) {
						// not enough pending commission
						errorCode = Constants.ERR_MARGIN_IN_IB_NOT_ENOUGH_PENDING_COMMISSION;
					}
				}
			} else {
				errorCode = Constants.ERR_MARGIN_IN_STATUS_NOT_PENDING;
			}

			if (errorCode != null) {
				ErrorCodeMarginInModel model = new ErrorCodeMarginInModel();
				String msg = messageSource.getMessage(errorCode, null, Locale.ROOT);
				model.getErrorMap().put(errorCode, msg);
				model.setMargin_in_bean(marginInBean);
				notUpdatedMarginIns.add(model);
			} else {
				// execute margin in
				executeMarginIn(marginInBean);
				updatedMarginInIds.add(marginInBean.getId().toString());
			}
		}

		// margin in id not exist
		for (String inputMarginId : marginInIds) {
			if (!dbMarginInIds.contains(inputMarginId)) {
				String errorCode = Constants.ERR_MARGIN_IN_ID_NOT_EXIST;
				ErrorCodeMarginInModel model = new ErrorCodeMarginInModel();
				String msg = messageSource.getMessage(errorCode, null, Locale.ROOT);
				model.getErrorMap().put(errorCode, msg);
				model.setMargin_in_bean(null);
				notUpdatedMarginIns.add(model);
			}
		}

		// create response dto
		BatchApproveMarginInResponseDto dto = new BatchApproveMarginInResponseDto();
		List<MarginInBean> updatedMarginIns = marginInDao.getMarginInByKeys(updatedMarginInIds);
		dto.setNot_updated_margin_ins(notUpdatedMarginIns);
		dto.setUpdated_margin_ins(updatedMarginIns);
		return dto;
	}

}
