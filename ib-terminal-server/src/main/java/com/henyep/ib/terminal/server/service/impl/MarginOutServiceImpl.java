package com.henyep.ib.terminal.server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.henyep.ib.terminal.api.dto.db.IbAccountDetailsBean;
import com.henyep.ib.terminal.api.dto.db.IbProfileBean;
import com.henyep.ib.terminal.api.dto.db.MarginOutBean;
import com.henyep.ib.terminal.api.dto.request.marginout.AdminUpdateMarginOutRequest;
import com.henyep.ib.terminal.api.dto.request.marginout.BatchApproveMarginOutRequest;
import com.henyep.ib.terminal.api.dto.request.marginout.CancelMarginOutRequest;
import com.henyep.ib.terminal.api.dto.request.marginout.GetMarginOutRequest;
import com.henyep.ib.terminal.api.dto.request.marginout.GetMaxWithdrawalRequest;
import com.henyep.ib.terminal.api.dto.request.marginout.InsertMarginOutRequest;
import com.henyep.ib.terminal.api.dto.request.marginout.InsertMarginOutRequestDto;
import com.henyep.ib.terminal.api.dto.request.marginout.MaxWithdrawalModel;
import com.henyep.ib.terminal.api.dto.request.marginout.UpdateMarginOutRequestDto;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.marginout.BatchApproveMarginOutsResponseDto;
import com.henyep.ib.terminal.api.dto.response.marginout.GetMarginOutResponseDto;
import com.henyep.ib.terminal.api.dto.response.marginout.GetMaxWithdrawalResponseDto;
import com.henyep.ib.terminal.api.dto.response.marginout.InsertMarginOutResponseDto;
import com.henyep.ib.terminal.api.dto.response.marginout.UpdateMarginOutResponseDto;
import com.henyep.ib.terminal.server.adapter.MT4ServiceAdapterFactory;
import com.henyep.ib.terminal.server.component.HySpringUtil;
import com.henyep.ib.terminal.server.dao.IbAccountDetailsDao;
import com.henyep.ib.terminal.server.dao.IbProfileDao;
import com.henyep.ib.terminal.server.dao.MarginOutDao;
import com.henyep.ib.terminal.server.dao.SystemParamsDao;
import com.henyep.ib.terminal.server.dto.mt4.response.DepositResponse;
import com.henyep.ib.terminal.server.factory.DtoFactory;
import com.henyep.ib.terminal.server.global.Constants;
import com.henyep.ib.terminal.server.service.MarginOutService;
import com.henyep.ib.terminal.server.util.DateUtil;
import com.henyep.ib.terminal.server.util.RateExchangeUtil;
import com.henyep.white.label.api.dto.request.balance.transfer.DepositMT4RequestDto;

@Service("MarginOutService")
public class MarginOutServiceImpl extends AbstractServiceImpl implements MarginOutService {
	final static Logger log = Logger.getLogger(MarginOutServiceImpl.class);
	private IbProfileDao ibProfileDao;
	private MarginOutDao marginOutDao;
	private IbAccountDetailsDao ibAccountDetailsDao;

	@Autowired
	public MarginOutServiceImpl(MarginOutDao marginOutDao, IbProfileDao ibProfileDao, IbAccountDetailsDao ibAccountDetailsDao) {
		this.marginOutDao = marginOutDao;
		this.ibProfileDao = ibProfileDao;
		this.ibAccountDetailsDao = ibAccountDetailsDao;
	}

	@Resource(name = "sz_SimpleDtoFactory")
	protected DtoFactory dtoFactory;

	@Resource(name = "sz_HySpringUtil")
	protected HySpringUtil hySpringUtil;

	@Resource(name = "SystemParamsDao")
	protected SystemParamsDao systemParamsDao;

	@Resource(name = "RateExchangeUtil")
	private RateExchangeUtil rateExchangeUtil;

	@Resource(name = "MT4ServiceAdapterFactory")
	private MT4ServiceAdapterFactory mt4ServiceAdapterFactory;

	private List<String> validateCategoriesMethod(String inputCategory, String inputMethod) {
		List<String> errorMsgs = new ArrayList<String>();

		List<String> availableCategories = new ArrayList<String>();
		availableCategories.add(Constants.MARGIN_OUT_CATEGORY_ADJUSTMENT);
		availableCategories.add(Constants.MARGIN_OUT_CATEGORY_USER_REQUEST);
		availableCategories.add(Constants.MARGIN_OUT_CATEGORY_FEE);
		availableCategories.add(Constants.MARGIN_OUT_CATEGORY_EXCEL_UPLOAD);

		if (!availableCategories.contains(inputCategory)) {
			errorMsgs.add(Constants.ERR_MARGIN_OUT_INVALID_CATEGORY);
		}

		List<String> availableMethods = new ArrayList<String>();
		availableMethods.add(Constants.MARGIN_OUT_METHOD_ADJUSTMENT);
		availableMethods.add(Constants.MARGIN_OUT_METHOD_BANK_TRANSFER);
		availableMethods.add(Constants.MARGIN_OUT_METHOD_PAYMENT_GATEWAY);
		availableMethods.add(Constants.MARGIN_OUT_METHOD_TO_TRADE_ACCOUNT);
		availableMethods.add(Constants.MARGIN_OUT_METHOD_INTERNAL_ACCOUNT_TRANSFER);

		if (!availableMethods.contains(inputMethod)) {
			errorMsgs.add(Constants.ERR_MARGIN_OUT_INVALID_METHOD);
		}

		if (inputCategory.equals(Constants.MARGIN_OUT_CATEGORY_ADJUSTMENT) && !inputMethod.equals(Constants.MARGIN_OUT_METHOD_ADJUSTMENT)) {
			errorMsgs.add(Constants.ERR_MARGIN_OUT_INVALID_ADJUSTMENT_METHOD);
		}
		if (inputCategory.equals(Constants.MARGIN_OUT_CATEGORY_USER_REQUEST) && inputMethod.equals(Constants.MARGIN_OUT_METHOD_ADJUSTMENT)) {
			errorMsgs.add(Constants.ERR_MARGIN_OUT_INVALID_USER_REQUEST_METHOD);
		}

		return errorMsgs;
	}

	private List<String> validateCreateMarginOut(InsertMarginOutRequest request) throws Exception {
		List<String> errorMsgs = new ArrayList<String>();

		String account = request.getBody().getAccount();
		String category = request.getBody().getCategory();
		String method = request.getBody().getMethod();
		Date tradeDate = request.getBody().getTrade_date();

		errorMsgs.addAll(validateCategoriesMethod(category, method));

		List<IbProfileBean> ibProfileBeans = ibProfileDao.getIbProfileByIbCode(account);
		if (ibProfileBeans.size() == 0) {
			errorMsgs.add(Constants.ERR_MARGIN_OUT_IB_PROFILE_NOT_EXIST);
		}

		List<IbAccountDetailsBean> ibAccountDetailsBeans = ibAccountDetailsDao.getIbAccountDetailsByKey(account);
		if (ibAccountDetailsBeans.size() == 0) {
			errorMsgs.add(Constants.ERR_MARGIN_OUT_ACCOUNT_DETAIL_NOT_EXIST);
		}

		if (ibProfileBeans.size() > 0 && ibAccountDetailsBeans.size() > 0) {
			// check enough account balance to perform margin out
			IbAccountDetailsBean targetIbAccountDetail = ibAccountDetailsBeans.get(0);

			String marginOutCurrency = request.getBody().getCurrency();
			String accountCurrency = targetIbAccountDetail.getCurrency();
			Double[] accountAmountSnipples = rateExchangeUtil.getExchangePrice(marginOutCurrency, accountCurrency, request.getBody().getAmount());
			double accountAmount = accountAmountSnipples[1];

			List<MarginOutBean> pendingMarginOuts = marginOutDao.getMarginOutByIbCodeStatus(targetIbAccountDetail.getIb_code(),
					Constants.MARGIN_OUT_STATUS_PENDING);
			double totalPendingMarginOutAmount = 0;
			for (MarginOutBean pendingMarginOut : pendingMarginOuts) {
				totalPendingMarginOutAmount += pendingMarginOut.getAccount_amount();
			}

			// get subsequent margin out fee
			double marginOutFee = getMarginOutFee(account, category, tradeDate);

			// check account has enough account balance
			if (accountAmount <= 0 || (targetIbAccountDetail.getAccount_balance() - totalPendingMarginOutAmount) < accountAmount + marginOutFee) {
				errorMsgs.add(Constants.ERR_MARGIN_OUT_ACCOUNT_NOT_ENOUGH_ACCOUNT_BALANCE);
			}

			// check below the min margin out amount
			if (category.equals(Constants.MARGIN_OUT_CATEGORY_USER_REQUEST)) {
				if (accountAmount < systemParamsDao.getMinMarginOutAmount()) {
					errorMsgs.add(Constants.ERR_MARGIN_OUT_BELOW_MIN_AMOUNT);
				}
			}

		}
		return errorMsgs;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public IbResponseDto<InsertMarginOutResponseDto> createMarginOut(InsertMarginOutRequest request, String status) throws Exception {

		List<String> errorMsgs = validateCreateMarginOut(request);
		// get margin out response
		IbResponseDto<InsertMarginOutResponseDto> response = dtoFactory.createWebResponse(request.getHeader());

		if (errorMsgs.size() > 0) {

			for (String errorMsg : errorMsgs) {
				hySpringUtil.setDtoErrorFromErrorCode(response, errorMsg);
			}

		} else {
			InsertMarginOutRequestDto requestDto = request.getBody();
			List<IbProfileBean> ibProfiles = ibProfileDao.getIbProfileByIbCode(requestDto.getAccount());
			List<IbAccountDetailsBean> ibAccountDetailsBeans = ibAccountDetailsDao.getIbAccountDetailsByKey(requestDto.getAccount());
			IbProfileBean ibProfile = ibProfiles.get(0);
			IbAccountDetailsBean ibAccountDetailsBean = ibAccountDetailsBeans.get(0);

			double marginOutFee = getMarginOutFee(requestDto.getAccount(), request.getBody().getCategory(), request.getBody().getTrade_date());

			// perform insert margin out
			MarginOutBean marginOutBean = new MarginOutBean();
			marginOutBean.setBrand_code(ibProfile.getBrand_code());
			marginOutBean.setCategory(requestDto.getCategory());
			marginOutBean.setMethod(requestDto.getMethod());
			marginOutBean.setAccount(requestDto.getAccount());
			marginOutBean.setCurrency(requestDto.getCurrency());
			marginOutBean.setAmount(requestDto.getAmount());

			String accountCurrency = ibAccountDetailsBean.getCurrency();
			marginOutBean.setAccount_currency(accountCurrency);
			// exchange rate
			Double[] snipples = rateExchangeUtil.getExchangePrice(requestDto.getCurrency(), accountCurrency, requestDto.getAmount());
			marginOutBean.setExchange_rate(snipples[0]);
			marginOutBean.setAccount_amount(snipples[1]);

			marginOutBean.setTrade_date(requestDto.getTrade_date());
			marginOutBean.setStatus(status);
			marginOutBean.setComment(requestDto.getComment());
			marginOutBean.setCreate_user(getSender(request.getHeader()));

			int marginOutId = marginOutDao.saveMarginOut(marginOutBean);

			// update ib profile details
			if (status.equals(Constants.MARGIN_OUT_STATUS_EXECUTED)) {
				List<IbAccountDetailsBean> ibAccountDetails = ibAccountDetailsDao.getIbAccountDetailsByKey(marginOutBean.getAccount());
				IbAccountDetailsBean ibAccountDetail = ibAccountDetails.get(0);
				double oriAccountBalance = ibAccountDetail.getAccount_balance();
				double adjustedAccountBalance = oriAccountBalance;
				adjustedAccountBalance -= (marginOutBean.getAccount_amount());
				ibAccountDetail.setAccount_balance(adjustedAccountBalance);
				ibAccountDetailsDao.updateIbAccountDetails(ibAccountDetail);
				log.info(String.format("Update ib (account: %s) account balance: %s -> %s", marginOutBean.getAccount(), oriAccountBalance,
						adjustedAccountBalance));
			}

			InsertMarginOutResponseDto dto = new InsertMarginOutResponseDto();
			List<MarginOutBean> insertedMarginOuts = marginOutDao.getMarginOutByKey(marginOutId);
			if (insertedMarginOuts.size() > 0) {
				dto.setMarginOut(insertedMarginOuts.get(0));
			}

			// insert margin out fee
			log.info(String.format("Margin out fee: %s", marginOutFee));
			if (marginOutFee > 0) {
				MarginOutBean marginOutFeeBean = new MarginOutBean();
				marginOutFeeBean.setBrand_code(ibProfile.getBrand_code());
				marginOutFeeBean.setCategory(Constants.MARGIN_OUT_CATEGORY_FEE);
				marginOutFeeBean.setMethod(Constants.MARGIN_OUT_METHOD_FEE);
				marginOutFeeBean.setAccount(requestDto.getAccount());
				marginOutFeeBean.setCurrency(requestDto.getCurrency());
				marginOutFeeBean.setAmount(marginOutFee);
				marginOutFeeBean.setAccount_currency("USD");
				marginOutFeeBean.setExchange_rate(1.0);
				marginOutFeeBean.setAccount_amount(marginOutFee);
				marginOutFeeBean.setTrade_date(requestDto.getTrade_date());
				marginOutFeeBean.setStatus(Constants.MARGIN_OUT_STATUS_EXECUTED);
				marginOutFeeBean.setComment(getMarginOutFeeComment(marginOutId + ""));
				marginOutFeeBean.setLast_update_user(getSender(request.getHeader()));

				int marginOutFeeId = marginOutDao.saveMarginOut(marginOutFeeBean);
				log.info(String.format("Insert Margin out fee, ID: %s", marginOutFeeId));

				List<IbAccountDetailsBean> ibAccountDetails = ibAccountDetailsDao.getIbAccountDetailsByKey(marginOutBean.getAccount());
				IbAccountDetailsBean ibAccountDetail = ibAccountDetails.get(0);
				double oriAccountBalance = ibAccountDetail.getAccount_balance();
				double adjustedAccountBalance = oriAccountBalance;
				adjustedAccountBalance -= marginOutFee;
				ibAccountDetail.setAccount_balance(adjustedAccountBalance);
				ibAccountDetailsDao.updateIbAccountDetails(ibAccountDetail);
				log.info(String.format("Update ib (account: %s) account balance: %s -> %s", marginOutBean.getAccount(), oriAccountBalance,
						adjustedAccountBalance));

				List<MarginOutBean> insertedMarginOutFees = marginOutDao.getMarginOutByKey(marginOutFeeId);
				if (insertedMarginOutFees.size() > 0) {
					dto.setMarginOutFee(insertedMarginOutFees.get(0));
				}

			}

			response.setBody(dto);
		}

		return response;
	}

	private List<String> validateCancelMarginOut(CancelMarginOutRequest request) throws Exception {
		List<String> errorList = new ArrayList<String>();

		List<MarginOutBean> marginOutBeans = marginOutDao.getMarginOutByKey(request.getBody().getMargin_out_id());
		// check margin out id exist or not
		if (marginOutBeans.size() == 0) {
			errorList.add(Constants.ERR_MARGIN_OUT_ID_NOT_EXIST);
		} else {
			MarginOutBean marginOutBean = marginOutBeans.get(0);
			if (!marginOutBean.getStatus().equals(Constants.MARGIN_OUT_STATUS_PENDING)) {
				// margin out id not pending
				errorList.add(Constants.ERR_MARGIN_OUT_STATUS_NOT_PENDING);
			}
		}

		return errorList;
	}

	private List<String> validateUpdateMarginOut(AdminUpdateMarginOutRequest request) throws Exception {
		List<String> errorList = new ArrayList<String>();

		Integer marginOutId = request.getBody().getMargin_out_id();
		List<MarginOutBean> marginOutBeans = marginOutDao.getMarginOutByKey(marginOutId);
		// check margin out id exist or not
		if (marginOutBeans.size() == 0) {
			errorList.add(Constants.ERR_MARGIN_OUT_ID_NOT_EXIST);
		} else {

			MarginOutBean marginOutBean = marginOutBeans.get(0);
			if (!marginOutBean.getStatus().equals(Constants.MARGIN_OUT_STATUS_PENDING)) {
				// margin out id not pending
				errorList.add(Constants.ERR_MARGIN_OUT_STATUS_NOT_PENDING);
			} else {

				String ibCode = marginOutBean.getAccount();
				List<IbAccountDetailsBean> ibAccountDetailBeans = ibAccountDetailsDao.getIbAccountDetailsByKey(ibCode);

				if (ibAccountDetailBeans.size() == 0) {
					// account not exist
					errorList.add(Constants.ERR_MARGIN_OUT_ACCOUNT_NOT_EXIST);
				} else {
					UpdateMarginOutRequestDto dto = request.getBody();
					// check category and check method
					String updatedCategory = marginOutBean.getCategory();
					if (dto.getCategory() != null) {
						updatedCategory = dto.getCategory();
					}
					String updatedMethod = marginOutBean.getMethod();
					if (dto.getMethod() != null) {
						updatedMethod = dto.getMethod();
					}
					errorList.addAll(validateCategoriesMethod(updatedCategory, updatedMethod));

					IbAccountDetailsBean targetIbAccountDetail = ibAccountDetailBeans.get(0);
					if (dto.getStatus() != null && dto.getStatus().equals(Constants.MARGIN_OUT_STATUS_EXECUTED)) {
						// execute margin out

						if (dto.getAmount() != null)
							marginOutBean.setAmount(dto.getAmount());
						if (dto.getCurrency() != null)
							marginOutBean.setCurrency(dto.getCurrency());

						if (dto.getAmount() != null || dto.getCurrency() != null) {
							String marginOutCurrency = marginOutBean.getCurrency();
							String accountCurrency = targetIbAccountDetail.getCurrency();
							Double[] accountAmountSnipples = rateExchangeUtil.getExchangePrice(marginOutCurrency, accountCurrency,
									marginOutBean.getAmount());
							double exchangeRate = accountAmountSnipples[0];
							double accountAmount = accountAmountSnipples[1];
							marginOutBean.setAccount_amount(accountAmount);
							marginOutBean.setExchange_rate(exchangeRate);
						}

						List<MarginOutBean> pendingMarginOuts = marginOutDao.getMarginOutByIbCodeStatus(targetIbAccountDetail.getIb_code(),
								Constants.MARGIN_OUT_STATUS_PENDING);
						double totalPendingMarginOutAmount = 0;
						for (MarginOutBean pendingMarginOut : pendingMarginOuts) {
							if (pendingMarginOut.getId() != marginOutBean.getId()) {
								totalPendingMarginOutAmount += pendingMarginOut.getAccount_amount();
							}
						}

						// check account has enough account balance
						if (targetIbAccountDetail.getAccount_balance() - totalPendingMarginOutAmount < marginOutBean.getAccount_amount()) {
							errorList.add(Constants.ERR_MARGIN_OUT_ACCOUNT_NOT_ENOUGH_ACCOUNT_BALANCE);
						}
					}
				}
			}
		}

		return errorList;

	}

	@Override
	public IbResponseDto<UpdateMarginOutResponseDto> cancelMarginOut(CancelMarginOutRequest request) throws Exception {
		IbResponseDto<UpdateMarginOutResponseDto> response = dtoFactory.createWebResponse(request.getHeader());
		List<String> errorMsgs = validateCancelMarginOut(request);
		if (errorMsgs.size() > 0) {
			for (String errorMsg : errorMsgs) {
				hySpringUtil.setDtoErrorFromErrorCode(response, errorMsg);
			}

		} else {

			List<MarginOutBean> marginOutBeans = marginOutDao.getMarginOutByKey(request.getBody().getMargin_out_id());
			MarginOutBean marginOutBean = marginOutBeans.get(0);
			marginOutBean.setLast_update_user(getSender(request.getHeader()));
			updateMarginOutStatus(marginOutBean, Constants.MARGIN_OUT_STATUS_CANCELLED);

			rollBackMarginOutFee(request.getBody().getMargin_out_id());

			UpdateMarginOutResponseDto responseDto = new UpdateMarginOutResponseDto();
			List<MarginOutBean> resultMarginOutBeans = marginOutDao.getMarginOutByKey(request.getBody().getMargin_out_id());
			if (resultMarginOutBeans.size() > 0) {
				responseDto.setMarginOut(resultMarginOutBeans.get(0));
			}
			response.setBody(responseDto);
		}
		return response;
	}

	private void rollBackMarginOutFee(Integer marginOutID) throws Exception {

		MarginOutBean searchFeeMarginOutBean = new MarginOutBean();
		searchFeeMarginOutBean.setComment(getMarginOutFeeComment(marginOutID.toString()));
		List<MarginOutBean> marginOutFeeBeans = marginOutDao.getMarginOutByExample(null, null, searchFeeMarginOutBean);
		if (marginOutFeeBeans.size() > 0) {
			for (MarginOutBean marginOutFeeBean : marginOutFeeBeans) {
				log.info("Roll back margin out fee, ID:" + marginOutFeeBean.getId());
				updateMarginOutStatus(marginOutFeeBean, Constants.MARGIN_OUT_STATUS_DELETED);

				List<IbAccountDetailsBean> ibAccountDetails = ibAccountDetailsDao.getIbAccountDetailsByKey(marginOutFeeBean.getAccount());
				IbAccountDetailsBean ibAccountDetail = ibAccountDetails.get(0);
				double oriAccountBalance = ibAccountDetail.getAccount_balance();
				double adjustedAccountBalance = oriAccountBalance + marginOutFeeBean.getAccount_amount();
				ibAccountDetail.setAccount_balance(adjustedAccountBalance);
				ibAccountDetailsDao.updateIbAccountDetails(ibAccountDetail);
				log.info(String.format("Update ib (account: %s) account balance: %s -> %s", marginOutFeeBean.getAccount(), oriAccountBalance,
						adjustedAccountBalance));

			}

		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public IbResponseDto<UpdateMarginOutResponseDto> updateMarginOut(AdminUpdateMarginOutRequest request) throws Exception {
		List<String> errorMsgs = validateUpdateMarginOut(request);
		// get margin out response
		IbResponseDto<UpdateMarginOutResponseDto> response = dtoFactory.createWebResponse(request.getHeader());
		UpdateMarginOutRequestDto requestDto = request.getBody();

		if (errorMsgs.size() > 0) {

			for (String errorMsg : errorMsgs) {
				hySpringUtil.setDtoErrorFromErrorCode(response, errorMsg);
			}

		} else {
			UpdateMarginOutResponseDto responseDto = new UpdateMarginOutResponseDto();
			List<MarginOutBean> marginOutBeans = marginOutDao.getMarginOutByKey(requestDto.getMargin_out_id());
			MarginOutBean marginOutBean = marginOutBeans.get(0);
			List<IbAccountDetailsBean> ibAccountDetails = ibAccountDetailsDao.getIbAccountDetailsByKey(marginOutBean.getAccount());
			IbAccountDetailsBean ibAccountDetail = ibAccountDetails.get(0);

			if (requestDto.getCategory() != null)
				marginOutBean.setCategory(requestDto.getCategory());
			if (requestDto.getAmount() != null)
				marginOutBean.setAmount(requestDto.getAmount());
			if (requestDto.getTrade_date() != null)
				marginOutBean.setTrade_date(requestDto.getTrade_date());
			if (requestDto.getCurrency() != null)
				marginOutBean.setCurrency(requestDto.getCurrency());
			if (requestDto.getCategory() != null)
				marginOutBean.setCategory(requestDto.getCategory());
			if (requestDto.getComment() != null)
				marginOutBean.setComment(requestDto.getComment());
			if (requestDto.getMethod() != null)
				marginOutBean.setMethod(requestDto.getMethod());

			if (requestDto.getAmount() != null || requestDto.getCurrency() != null) {
				Double[] accountAmountSnipples = rateExchangeUtil.getExchangePrice(marginOutBean.getCurrency(), ibAccountDetail.getCurrency(),
						marginOutBean.getAmount());
				double exchangeRate = accountAmountSnipples[0];
				double accountAmount = accountAmountSnipples[1];
				marginOutBean.setAccount_amount(accountAmount);
				marginOutBean.setExchange_rate(exchangeRate);
			}
			marginOutBean.setLast_update_user(getSender(request.getHeader()));
			marginOutDao.updateMarginOut(marginOutBean);

			if (requestDto.getStatus() != null) {
				String updatedStatus = requestDto.getStatus();

				if (updatedStatus.equals(Constants.MARGIN_OUT_STATUS_EXECUTED)) {
					executeMarginOut(marginOutBean);
				} else if (updatedStatus.equals(Constants.MARGIN_OUT_STATUS_CANCELLED)) {
					updateMarginOutStatus(marginOutBean, Constants.MARGIN_OUT_STATUS_CANCELLED);
					rollBackMarginOutFee(marginOutBean.getId());
				} else if (updatedStatus.equals(Constants.MARGIN_OUT_STATUS_REJECTED)) {
					updateMarginOutStatus(marginOutBean, Constants.MARGIN_OUT_STATUS_REJECTED);
					rollBackMarginOutFee(marginOutBean.getId());
				}
			}

			List<MarginOutBean> resultMarginOutBeans = marginOutDao.getMarginOutByKey(requestDto.getMargin_out_id());
			if (resultMarginOutBeans.size() > 0) {
				responseDto.setMarginOut(resultMarginOutBeans.get(0));
			}
			response.setBody(responseDto);
		}
		return response;

	}

	public void executeMarginOut(MarginOutBean targetMarginOutBean) throws Exception {

		List<IbAccountDetailsBean> ibAccountDetails = ibAccountDetailsDao.getIbAccountDetailsByKey(targetMarginOutBean.getAccount());
		IbAccountDetailsBean ibAccountDetail = ibAccountDetails.get(0);

		if (targetMarginOutBean != null) {
			// payment gateway

			boolean isTransferSuccess = true;
			if (targetMarginOutBean.getMethod().equals(Constants.MARGIN_OUT_METHOD_TO_TRADE_ACCOUNT)) {
				isTransferSuccess = transferMarginToTradeAccount(targetMarginOutBean);
			}

			if (isTransferSuccess) {
				// update margin out status
				log.info(String.format("Update margin out (id: %s) status: %s -> %s", targetMarginOutBean.getId(), targetMarginOutBean.getStatus(),
						Constants.MARGIN_OUT_STATUS_EXECUTED));
				targetMarginOutBean.setStatus(Constants.MARGIN_OUT_STATUS_EXECUTED);
				marginOutDao.updateMarginOut(targetMarginOutBean);

				// update ib profile details
				double oriAccountBalance = ibAccountDetail.getAccount_balance();
				double adjustedAccountBalance = oriAccountBalance - targetMarginOutBean.getAccount_amount();
				ibAccountDetail.setAccount_balance(adjustedAccountBalance);
				ibAccountDetailsDao.updateIbAccountDetails(ibAccountDetail);
				log.info(String.format("Update ib (account: %s) account balance: %s -> %s", targetMarginOutBean.getAccount(), oriAccountBalance,
						adjustedAccountBalance));
			}
		}
	}

	private void updateMarginOutStatus(MarginOutBean targetMarginOutBean, String status) throws Exception {

		if (targetMarginOutBean != null) {
			// update margin out status
			log.info(String.format("Update margin out (id: %s) status: %s -> %s", targetMarginOutBean.getId(), targetMarginOutBean.getStatus(),
					status));
			targetMarginOutBean.setStatus(status);
			marginOutDao.updateMarginOut(targetMarginOutBean);

		}
	}

	private boolean transferMarginToTradeAccount(MarginOutBean marginOutBean) {
		log.info("Transfering payment to trade account");

		boolean success = false;
		if (marginOutBean.getMethod().equals(Constants.MARGIN_OUT_METHOD_TO_TRADE_ACCOUNT)) {
			DepositMT4RequestDto data = new DepositMT4RequestDto();
			Integer mt4Id = Integer.parseInt(marginOutBean.getComment());
			data.setLogin_id(mt4Id);
			// assume the currency is USD
			data.setAmount(marginOutBean.getAmount());
			data.setComment("IB Commission");
			String json = mt4ServiceAdapterFactory.sendRequest(data);
			if (json != null) {
				Object mt4ResModel = mt4ServiceAdapterFactory.getResponseObject(data, json);
				if (mt4ResModel != null && mt4ResModel instanceof DepositResponse) {
					if (((DepositResponse) mt4ResModel).getTradeTransInfo().getOrder() > 0)
						success = true;

				}
			}
		}
		return success;
	}

	@Override
	public GetMarginOutResponseDto getMarginOut(GetMarginOutRequest request) throws Exception {

		GetMarginOutResponseDto dto = new GetMarginOutResponseDto();
		Date startDate = request.getBody().getStartDate();
		Date endDate = request.getBody().getEndDate();
		MarginOutBean exampleBean = request.getBody().getMarginOutBean();

		List<MarginOutBean> marginOutBeans = marginOutDao.getMarginOut(startDate, endDate, exampleBean);

		dto.setMarginOutBeans(marginOutBeans);

		return dto;

	}

	@Override
	public GetMaxWithdrawalResponseDto getMaxWithdrawal(GetMaxWithdrawalRequest request) throws Exception {

		String ibCode = request.getBody().getIb_code();
		MaxWithdrawalModel maxWithdrawalModel = marginOutDao.getMaxWithdrawal(ibCode);
		GetMaxWithdrawalResponseDto dto = new GetMaxWithdrawalResponseDto();
		dto.setIb_code(ibCode);
		double maxWithdrawal = 0;
		if (maxWithdrawalModel != null && maxWithdrawalModel.getAccount_balance() != null && maxWithdrawalModel.getPending_margin() != null) {
			maxWithdrawal = maxWithdrawalModel.getAccount_balance() - maxWithdrawalModel.getPending_margin();
		}

		double marginOutFee = getMarginOutFee(ibCode, Constants.MARGIN_OUT_CATEGORY_USER_REQUEST, Calendar.getInstance().getTime());

		dto.setMax_withdrawal(maxWithdrawal);
		dto.setPending_margin_out(maxWithdrawalModel.getPending_margin());
		dto.setAccount_balance(maxWithdrawalModel.getAccount_balance());
		dto.setSubsequent_margin_out_fee(marginOutFee);

		return dto;
	}

	private String getMarginOutFeeComment(String marginOutId) {
		return "#" + marginOutId;
	}

	private double getMarginOutFee(String ibCode, String category, Date tradeDate) throws Exception {
		// get subsequent margin out fee
		double marginOutFee = 0;
		int dbMarginOutCount = 0;

		if (category.equals(Constants.MARGIN_OUT_CATEGORY_USER_REQUEST)) {
			Calendar tradeDateCal = Calendar.getInstance();
			tradeDateCal.setTime(tradeDate);
			Pair<Date, Date> monthDateRange = DateUtil.getMonthDateRange(tradeDateCal.get(Calendar.YEAR), tradeDateCal.get(Calendar.MONTH) + 1);
			MarginOutBean exampleBean = new MarginOutBean();
			exampleBean.setAccount(ibCode);

			exampleBean.setStatus(Constants.MARGIN_OUT_STATUS_EXECUTED);
			List<MarginOutBean> executedMarginOuts = marginOutDao.getMarginOut(monthDateRange.getKey(), monthDateRange.getValue(), exampleBean);
			for (MarginOutBean executedMarginOut : executedMarginOuts) {
				if (executedMarginOut.getCategory().equals(Constants.MARGIN_OUT_CATEGORY_USER_REQUEST)) {
					dbMarginOutCount++;
				}
			}
			exampleBean.setStatus(Constants.MARGIN_OUT_STATUS_PENDING);
			List<MarginOutBean> pendingMarginOuts = marginOutDao.getMarginOut(monthDateRange.getKey(), monthDateRange.getValue(), exampleBean);
			for (MarginOutBean pendingMarginOut : pendingMarginOuts) {
				if (pendingMarginOut.getCategory().equals(Constants.MARGIN_OUT_CATEGORY_USER_REQUEST)) {
					dbMarginOutCount++;
				}
			}
			if (systemParamsDao.getFreeMarginOutCount() <= dbMarginOutCount) {
				marginOutFee = systemParamsDao.getSubsequentMarginOutFee();
			}
		}

		return marginOutFee;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public IbResponseDto<BatchApproveMarginOutsResponseDto> approveMarginOuts(BatchApproveMarginOutRequest request) throws Exception {
		List<String> marginOutIds = request.getBody().getMargin_out_ids();
		List<MarginOutBean> maginOutBeans = marginOutDao.getMarginOutByIds(marginOutIds);
		for (MarginOutBean model : maginOutBeans) {
			model.setLast_update_user(getSender(request.getHeader()));
		}

		for (MarginOutBean marginOutBean : maginOutBeans) {
			executeMarginOut(marginOutBean);
		}

		BatchApproveMarginOutsResponseDto dto = new BatchApproveMarginOutsResponseDto();
		List<MarginOutBean> updatedMarginOuts = marginOutDao.getMarginOutByIds(marginOutIds);
		dto.setMarginOuts(updatedMarginOuts);
		IbResponseDto<BatchApproveMarginOutsResponseDto> response = new IbResponseDto<BatchApproveMarginOutsResponseDto>();
		response.setBody(dto);
		return response;
	}

	@Override
	public List<String> validateApproveMarginOuts(BatchApproveMarginOutRequest request) throws Exception {
		List<String> errorList = new ArrayList<String>();

		List<String> inputMarginOutIds = request.getBody().getMargin_out_ids();
		List<MarginOutBean> dbMarginOutBeans = marginOutDao.getMarginOutByIds(inputMarginOutIds);
		List<String> dbMarginOutIds = new ArrayList<String>();
		for (MarginOutBean dbMarginOutBean : dbMarginOutBeans) {
			dbMarginOutIds.add(dbMarginOutBean.getId().toString());
		}

		for (String inputMarginOutId : inputMarginOutIds) {
			if (!dbMarginOutIds.contains(inputMarginOutId)) {
				errorList.add(Constants.ERR_MARGIN_OUT_ID_NOT_EXIST);
				break;
			}
		}

		if (errorList.size() == 0) {
			for (MarginOutBean dbMarginOutBean : dbMarginOutBeans) {
				if (!dbMarginOutBean.getStatus().equals(Constants.MARGIN_OUT_STATUS_PENDING)) {
					// margin out id not pending
					errorList.add(Constants.ERR_MARGIN_OUT_STATUS_NOT_PENDING);
					break;
				}
			}
		}
		return errorList;
	}

}
