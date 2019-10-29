package com.henyep.ib.terminal.server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.henyep.ib.terminal.api.dto.db.IbAccountDetailsBean;
import com.henyep.ib.terminal.api.dto.db.IbCommissionCpaBean;
import com.henyep.ib.terminal.api.dto.db.IbCommissionDetailsBean;
import com.henyep.ib.terminal.api.dto.db.IbCommissionDetailsSupplementaryBean;
import com.henyep.ib.terminal.api.dto.db.IbProfileBean;
import com.henyep.ib.terminal.api.dto.db.IbTradingAccountMapBean;
import com.henyep.ib.terminal.api.dto.db.MarginInBean;
import com.henyep.ib.terminal.api.dto.request.BaseTradeDateRequestBodyDto;
import com.henyep.ib.terminal.api.dto.request.ibcommission.CalculateIbCommissionRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.CpaCalculateRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.EvConfirmDto;
import com.henyep.ib.terminal.api.dto.request.ibcommission.EvConfirmRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.EvConfirmRequestDto;
import com.henyep.ib.terminal.api.dto.request.ibcommission.EvDataRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.EvFigureUpdateDto;
import com.henyep.ib.terminal.api.dto.request.ibcommission.EvFigureUpdateRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.EvFigureUpdateRequestDto;
import com.henyep.ib.terminal.api.dto.request.ibcommission.GetIbClientSummaryRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.GetIbSummaryRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.GetTradingAccountListRequest;
import com.henyep.ib.terminal.api.dto.response.ibcommission.CalculateIbCommissionResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.GetIbClientSummaryResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.GetIbCommissionDetailsResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.GetIbCommissionResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.GetIbSummaryResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.GetIbSummaryTrimmedResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.GetTradingAccountListResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.IbAccountModel;
import com.henyep.ib.terminal.api.dto.response.ibcommission.IbClientAccountModel;
import com.henyep.ib.terminal.api.dto.response.ibcommission.IbClientSummariesModel;
import com.henyep.ib.terminal.api.dto.response.ibcommission.IbCommissionDetailsModel;
import com.henyep.ib.terminal.api.dto.response.ibcommission.IbCommissionModel;
import com.henyep.ib.terminal.api.dto.response.ibcommission.IbMonthBalanceModel;
import com.henyep.ib.terminal.api.dto.response.ibcommission.IbSummaryByDateRangeModel;
import com.henyep.ib.terminal.api.dto.response.ibcommission.TradeAccountModel;
import com.henyep.ib.terminal.api.dto.response.user.CurrentMothData;
import com.henyep.ib.terminal.server.adapter.MT4ServiceAdapterFactory;
import com.henyep.ib.terminal.server.dao.ClientDetailsDao;
import com.henyep.ib.terminal.server.dao.IbAccountDetailsDao;
import com.henyep.ib.terminal.server.dao.IbCommissionClientSummaryDao;
import com.henyep.ib.terminal.server.dao.IbCommissionCpaDao;
import com.henyep.ib.terminal.server.dao.IbCommissionDetailsDao;
import com.henyep.ib.terminal.server.dao.IbCommissionDetailsSupplementaryDao;
import com.henyep.ib.terminal.server.dao.IbCommissionSummaryDao;
import com.henyep.ib.terminal.server.dao.IbProfileDao;
import com.henyep.ib.terminal.server.dao.IbTradingAccountMapDao;
import com.henyep.ib.terminal.server.dao.MarginInDao;
import com.henyep.ib.terminal.server.dao.SystemParamsDao;
import com.henyep.ib.terminal.server.dto.mt4.model.Mt4WebServiceConnectionModel;
import com.henyep.ib.terminal.server.dto.mt4.response.GetUserProfileResponse;
import com.henyep.ib.terminal.server.exception.ServiceException;
import com.henyep.ib.terminal.server.global.Constants;
import com.henyep.ib.terminal.server.processor.IbCommissionCpaProcessor;
import com.henyep.ib.terminal.server.processor.IbCommissionDetailsProcessor;
import com.henyep.ib.terminal.server.processor.IbCommissionEvProcessor;
import com.henyep.ib.terminal.server.processor.IbCommissionGroupEvProcessor;
import com.henyep.ib.terminal.server.service.IbCommissionService;
import com.henyep.ib.terminal.server.util.DateUtil;
import com.henyep.ib.terminal.server.util.RateExchangeUtil;
import com.henyep.white.label.api.dto.request.user.GetUserProfileRequestDto;

@Service(value = "IbCommissionService")
public class IbCommissionServiceImpl extends AbstractServiceImpl implements IbCommissionService {

	final static Logger log = Logger.getLogger(IbCommissionServiceImpl.class);

	@Resource(name = "IbCommissionDetailsProcessor")
	private IbCommissionDetailsProcessor detailsProcessor;

	@Resource(name = "IbCommissionEvProcessor")
	private IbCommissionEvProcessor evProcessor;

	@Resource(name = "IbCommissionGroupEvProcessor")
	private IbCommissionGroupEvProcessor groupEvProcessor;

	@Resource(name = "IbCommissionCpaProcessor")
	private IbCommissionCpaProcessor ibCommissionCpaProcessor;

	@Resource(name = "IbTradingAccountMapDao")
	private IbTradingAccountMapDao ibTradingAccountMapDao;

	@Resource(name = "MT4ServiceAdapterFactory")
	private MT4ServiceAdapterFactory mt4ServiceAdapterFactory;

	@Resource(name = "SystemParamsDao")
	private SystemParamsDao systemParamsDao;

	@Resource(name = "IbCommissionCpaDao")
	private IbCommissionCpaDao ibCommissionCpaDao;	

	@Resource(name = "RateExchangeUtil")
	private RateExchangeUtil rateExchangeUtil;

	private IbCommissionDetailsDao detailDao;
	private IbCommissionClientSummaryDao clientSummaryDao;
	private IbCommissionSummaryDao summaryDao;
	private IbAccountDetailsDao ibAccountDetailsDao;
	private IbProfileDao ibProfileDao;
	private MarginInDao marginInDao;
	private IbCommissionDetailsSupplementaryDao commSupplementaryDao;
	private ClientDetailsDao clientDetailsDao;

	@Autowired
	public IbCommissionServiceImpl(IbCommissionDetailsDao detailDao, IbCommissionDetailsSupplementaryDao commSupplementaryDao,
			IbCommissionClientSummaryDao clientSummaryDao, IbCommissionSummaryDao summaryDao, IbAccountDetailsDao ibAccountDetailsDao,
			MarginInDao marginInDao, ClientDetailsDao clientDetailsDao, IbProfileDao ibProfileDao) {

		this.detailDao = detailDao;
		this.commSupplementaryDao = commSupplementaryDao;
		this.clientSummaryDao = clientSummaryDao;
		this.summaryDao = summaryDao;
		this.ibAccountDetailsDao = ibAccountDetailsDao;
		this.marginInDao = marginInDao;
		this.clientDetailsDao = clientDetailsDao;
		this.ibProfileDao = ibProfileDao;

	}

	private int clearCommissionDetails(Date startDate, Date endDate) throws Exception {
		writeLog("Start deleting ib commission details...");
		int deletedRecordsCount = detailDao.deleteIbCommissionDetails(startDate, endDate);
		writeLog("Number of ib commission details deleted: " + deletedRecordsCount);
		writeLog("Finish!");
		return deletedRecordsCount;
	}

	private int clearCommissionDetails(Date startDate, Date endDate, List<String> ibCodes) throws Exception {
		writeLog("Start deleting ib commission details...");
		int deletedRecordsCount = detailDao.deleteIbCommissionDetails(startDate, endDate, ibCodes);
		writeLog("Number of ib commission details deleted: " + deletedRecordsCount);
		writeLog("Finish!");
		return deletedRecordsCount;
	}

	private void clearAccountDetails(Date tradeDate, List<IbCommissionDetailsBean> detailsBeans) throws Exception {

		ArrayList<String> ibCodes = new ArrayList<String>();
		for (IbCommissionDetailsBean detailsBean : detailsBeans) {
			String ibCode = detailsBean.getIb_code();
			if (!ibCodes.contains(ibCode)) {
				ibCodes.add(ibCode);
			}
		}

		clearAccountDetailsByIbCodes(tradeDate, ibCodes);

	}

	private void clearAccountDetailsByIbCodes(Date tradeDate, List<String> ibCodes) throws Exception {
		writeLog("Start clearing account profile detail...");
		ibAccountDetailsDao.resetPendingCommissions(tradeDate, ibCodes);
		ibAccountDetailsDao.resetAccBalance(tradeDate, ibCodes);
		writeLog("Finish!");
	}

	private int clearMarginIn(Date startDate, Date endDate, String comment, String updatedBy) throws Exception {

		writeLog("Start deleting margin in by trade date...");
		int deletedRecordsCount = marginInDao.deleteMarginInDateByDateRange(startDate, endDate, comment, updatedBy);
		writeLog("Number of margin in deleted: " + deletedRecordsCount);
		writeLog("Finish!");
		return deletedRecordsCount;
	}

	private int clearMarginIn(Date startDate, Date endDate, List<String> ibCodes, String comment, String updatedBy) throws Exception {

		writeLog("Start deleting margin in by trade date...");
		int deletedRecordsCount = marginInDao.deleteMarginInDateByDateRangeIbCodes(startDate, endDate, ibCodes, comment, updatedBy);
		writeLog("Number of margin in deleted: " + deletedRecordsCount);
		writeLog("Finish!");
		return deletedRecordsCount;
	}

	private void saveCommissionDetails(List<IbCommissionDetailsBean> detailsBeans, Date startDate, Date endDate) throws Exception {
		writeLog("Start inserting ib commission details...");
		detailDao.saveIbCommissionDetails(detailsBeans);
		writeLog("Finish!");
	}

	private void generateCommissionClientSummary(Date startDate, Date endDate) throws Exception {

		writeLog("Start SaveCommissionClientSummaries...");
		clientSummaryDao.generateIbCommissionClientSummarys(startDate, endDate);
		writeLog("Finish!");

	}

	private void generateCommissionSummary(Date startDate, Date endDate) throws Exception {

		writeLog("Saving SaveCommissionSummary...");
		summaryDao.generateIbCommissionSummarys(startDate, endDate);
		writeLog("Finish!");
	}

	private Map<String, MarginInBean> getMarginInMapByIbCommissionCpa(String sender, List<IbCommissionCpaBean> ibCommissionCpaBeans, Date tradeDate,
			String status, String category, String comment) {
		writeLog("Generating margin in beans by cpa");
		// generate margin in beans
		HashMap<String, MarginInBean> marginInBeanMap = new HashMap<String, MarginInBean>();
		for (IbCommissionCpaBean cpa : ibCommissionCpaBeans) {
			String ibCode = cpa.getIb_code();

			double amount = cpa.getAmount();
			String amountCurrency = cpa.getCurrency();
			String brandCode = cpa.getBrand_code();

			if (amount > 0) {
				if (!marginInBeanMap.containsKey(ibCode)) {
					marginInBeanMap.put(ibCode, new MarginInBean());
				}

				MarginInBean marginInBean = marginInBeanMap.get(ibCode);
				double originalAmount = marginInBean.getAmount() == null ? 0.0 : marginInBean.getAmount().doubleValue();
				marginInBean.setStatus(status);
				marginInBean.setAmount(originalAmount + amount);
				marginInBean.setCurrency(amountCurrency);
				marginInBean.setBrand_code(brandCode);
				marginInBean.setAccount(ibCode);
				marginInBean.setCategory(category);
				marginInBean.setCreate_time(new Date());
				marginInBean.setCreate_user(sender);
				marginInBean.setLast_update_time(new Date());
				marginInBean.setLast_update_user(sender);
				marginInBean.setTrade_date(tradeDate);
				marginInBean.setComment(comment);
			}

		}
		return marginInBeanMap;
	}

	private Map<String, MarginInBean> getMarginInMapByIbCommissionDetails(String sender, List<IbCommissionDetailsBean> ibCommissionDetailsBeans,
			Date tradeDate, String status, String category, String comment) {
		writeLog("Generating margin in beans by commission details");
		// generate margin in beans
		HashMap<String, MarginInBean> marginInBeanMap = new HashMap<String, MarginInBean>();
		for (IbCommissionDetailsBean detailBean : ibCommissionDetailsBeans) {
			String ibCode = detailBean.getIb_code();

			double amount = detailBean.getClient_fix_commission() + detailBean.getClient_spread_commission() + detailBean.getRebate_commission_lot()
					+ detailBean.getRebate_commission_pip();
			String amountCurrency = detailBean.getCurrency();
			String brandCode = detailBean.getBrand_code();

			if (amount > 0) {
				if (!marginInBeanMap.containsKey(ibCode)) {
					marginInBeanMap.put(ibCode, new MarginInBean());
				}

				MarginInBean marginInBean = marginInBeanMap.get(ibCode);
				double originalAmount = marginInBean.getAmount() == null ? 0.0 : marginInBean.getAmount().doubleValue();
				marginInBean.setStatus(status);
				marginInBean.setAmount(originalAmount + amount);
				marginInBean.setCurrency(amountCurrency);
				marginInBean.setBrand_code(brandCode);
				marginInBean.setAccount(detailBean.getIb_code());
				marginInBean.setCategory(category);
				marginInBean.setCreate_time(new Date());
				marginInBean.setCreate_user(sender);
				marginInBean.setLast_update_time(new Date());
				marginInBean.setLast_update_user(sender);
				marginInBean.setTrade_date(tradeDate);
				marginInBean.setComment(comment);
			}

		}

		return marginInBeanMap;
	}

	private int generateMarginIn(String sender, Map<String, MarginInBean> marginInBeanMap) throws Exception {

		if (marginInBeanMap != null && marginInBeanMap.size() > 0) {
			writeLog("Uploading account detail account balance...");
			// set account currency and account amount
			for (String ibCode : marginInBeanMap.keySet()) {
				List<IbAccountDetailsBean> ibAccountDetailBeans = ibAccountDetailsDao.getIbAccountDetailsByKey(ibCode);
				if (ibAccountDetailBeans.size() >= 1) {
					IbAccountDetailsBean ibAccount = ibAccountDetailBeans.get(0);
					MarginInBean marginInBean = marginInBeanMap.get(ibCode);
					marginInBean.setAccount_currency(ibAccount.getCurrency());

					String fromCurrency = marginInBean.getCurrency();
					String toCurrency = marginInBean.getAccount_currency();
					Double[] exchangeAmountInfo = rateExchangeUtil.getExchangePrice(fromCurrency, toCurrency, marginInBean.getAmount());
					double exchangeRate = exchangeAmountInfo[0];
					double exchangedAmount = exchangeAmountInfo[1];
					marginInBean.setAccount_amount(exchangedAmount);
					marginInBean.setExchange_rate(exchangeRate);

					double accountBalance = ibAccount.getAccount_balance() + exchangedAmount;
					ibAccount.setAccount_balance(accountBalance);
					ibAccount.setLast_update_user(sender);
					ibAccount.setLast_update_time(new Date());
					ibAccountDetailsDao.updateIbAccountDetails(ibAccount);
				}
			}

			// upload margin in bean to database
			writeLog("Uploading margin in bean to database");
			List<MarginInBean> marginInList = new ArrayList<MarginInBean>();
			marginInList.addAll(marginInBeanMap.values());
			marginInDao.saveMarginIns(marginInList);
			writeLog("Finish!");
			return marginInList.size();
		}

		return 0;

	}

	// private MarginInBean saveMarginInAccountAmount(MarginInBean model) {
	// try {
	// List<IbAccountDetailsBean> ibAccountDetailBeans =
	// ibAccountDetailsDao.getIbAccountDetailsByKey(model.getAccount());
	// if (ibAccountDetailBeans.size() >= 1) {
	// IbAccountDetailsBean ibAccount = ibAccountDetailBeans.get(0);
	//
	// model.setAccount_currency(ibAccount.getCurrency());
	//
	// String fromCurrency = model.getCurrency();
	// String toCurrency = model.getAccount_currency();
	// double[] exchangeAmountInfo =
	// RateExchangeUtil.getExchangePrice(fromCurrency, toCurrency,
	// model.getAmount());
	// double exchangeRate = exchangeAmountInfo[0];
	// double exchangedAmount = exchangeAmountInfo[1];
	// model.setAccount_amount(exchangedAmount);
	// model.setExchange_rate(exchangeRate);
	//
	// double pendingCommission = ibAccount.getPending_commission() +
	// exchangedAmount;
	// ibAccount.setPending_commission(pendingCommission);
	// ibAccountDetailsDao.updateIbAccountDetails(ibAccount);
	// marginInDao.saveMarginIn(model);
	// }
	// } catch (Exception e) {
	// log.error(e, e);
	// }
	// return model;
	// }

	// private MarginInBean convertToMarginInBean(String sender,
	// IbCommissionDetailsSupplementaryBean model) {
	// MarginInBean marginIn = new MarginInBean();
	// marginIn.setStatus(Constants.MARGIN_IN_STATUS_PENDING);
	// marginIn.setAmount(model.getEv_commission());
	// marginIn.setCurrency(model.getCurrency());
	// marginIn.setBrand_code(model.getBrand_code());
	// marginIn.setAccount(model.getIb_code());
	// marginIn.setCategory(Constants.MARGIN_IN_CATEGORY_REBATE);
	// marginIn.setCreate_time(new Date());
	// marginIn.setCreate_user(sender);
	// marginIn.setTrade_date(model.getTrade_date());
	// marginIn.setComment("");
	// return marginIn;
	// }

	private void writeLog(String message) {
		log.info(message);
		System.out.println(message);
	}

	@Override
	public CurrentMothData calculateIbCommissionByDate(String ibCode, String startDate, String endDate) throws Exception {
		return summaryDao.calculateIbCommissionByDate(ibCode, startDate, endDate);
	}

	@Override
	public Date getLastTradeDate() throws Exception {
		Date lastTradeDate = summaryDao.getLastTradeDate();
		return lastTradeDate;
	}

	@Override
	public GetIbSummaryResponseDto getIbSummary(GetIbSummaryRequest request) throws Exception {

		Date inputTradeDate = request.getBody().getTrade_date();
		int month = DateUtil.getMonthByDate(inputTradeDate);
		int year = DateUtil.getYearByDate(inputTradeDate);

		Date lastTradeDate = summaryDao.getLastTradeDate();
		Pair<Date, Date> lastTradeDateRange = DateUtil.getDayDateRange(lastTradeDate);

		Calendar cal = Calendar.getInstance();
		cal.setTime(inputTradeDate);
		cal.add(Calendar.MONTH, -1);
		int lastMonth = cal.get(Calendar.MONTH) + 1;
		int lastMonthYear = cal.get(Calendar.YEAR);

		Pair<Date, Date> monthDateRange = DateUtil.getMonthDateRange(year, month);
		Date monthStartDate = monthDateRange.getKey();
		Date monthEndDate = monthDateRange.getValue();

		Pair<Date, Date> lastMonthDateRange = DateUtil.getMonthDateRange(lastMonthYear, lastMonth);
		Date lastMonthStartDate = lastMonthDateRange.getKey();
		Date lastMonthEndDate = lastMonthDateRange.getValue();

		String ibCode = request.getBody().getIb_code();

		IbSummaryByDateRangeModel valueDayIbSummary = getLastTradeDayIbSummary(ibCode);
		valueDayIbSummary.setStart_date(lastTradeDateRange.getKey());
		valueDayIbSummary.setEnd_date(lastTradeDateRange.getValue());
		IbSummaryByDateRangeModel monthIbSummary = getIbSummaryByDateRange(ibCode, monthStartDate, monthEndDate);
		IbSummaryByDateRangeModel lastMonthIbSummary = getIbSummaryByDateRange(ibCode, lastMonthStartDate, lastMonthEndDate);

		valueDayIbSummary.setDate_description(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, lastTradeDate));
		monthIbSummary.setDate_description(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_MONTH, inputTradeDate));
		lastMonthIbSummary.setDate_description(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_MONTH, cal.getTime()));

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(inputTradeDate);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, -1);
		Date lastMonthEnd = calendar.getTime();
		List<IbCommissionDetailsSupplementaryBean> commDetails = commSupplementaryDao.getIbCommissionDetailsSupplementaryByIbCodeAndDate(ibCode,
				lastMonthEnd);
		GetIbSummaryResponseDto responseDto = new GetIbSummaryResponseDto();
		responseDto.setTrade_day_ib_summary(valueDayIbSummary);
		responseDto.setMonth_ib_summary(monthIbSummary);
		responseDto.setLast_month_ib_summary(lastMonthIbSummary);
		responseDto.setEvCommissionList(commDetails);

		return responseDto;

	}

	@Override
	public GetIbSummaryTrimmedResponseDto getTrimmedIbSummary(GetIbSummaryRequest request) throws Exception {

		GetIbSummaryTrimmedResponseDto dto = new GetIbSummaryTrimmedResponseDto();

		Date tradeDate = request.getBody().getTrade_date();
		String ibCode = request.getBody().getIb_code();
		int month = DateUtil.getMonthByDate(tradeDate);
		int year = DateUtil.getYearByDate(tradeDate);
		Pair<Date, Date> monthDateRange = DateUtil.getMonthDateRange(year, month);
		Date monthStartDate = monthDateRange.getKey();
		Date monthEndDate = monthDateRange.getValue();

		List<IbProfileBean> ibProfileBeans = ibProfileDao.getIbProfileByIbCode(ibCode);

		IbMonthBalanceModel ibMonthBalanceModel = summaryDao.getTrimmedIbSummaryByDateRange(ibCode, monthStartDate, monthEndDate);
		if (ibMonthBalanceModel == null) {
			ibMonthBalanceModel = new IbMonthBalanceModel();
			ibMonthBalanceModel.setIb_code(ibCode);
		}

		IbAccountModel ibAccountModel = ibAccountDetailsDao.getIbAccountDetail(ibCode);
		List<IbClientAccountModel> ibClientAccountModels = clientDetailsDao.getIbClientAccountSummary(ibCode, tradeDate);

		// set total number of clients
		dto.setNumOfClients(ibClientAccountModels.size());
		// set total client balance
		for(IbClientAccountModel ibClientAccountModel : ibClientAccountModels){
			
			// convert rate to USD
			if(!ibClientAccountModel.getCurrency().equals("USD")){
				Double[] result = rateExchangeUtil.getExchangePrice(ibClientAccountModel.getCurrency(), "USD", ibClientAccountModel.getBalance());
				if(result[1] != null){
					dto.setTotalClientsBalance(result[1] + dto.getTotalClientsBalance());
				}
				else{
					dto.setTotalClientsBalance(ibClientAccountModel.getBalance() + dto.getTotalClientsBalance());
				}
			}
			else{
				dto.setTotalClientsBalance(ibClientAccountModel.getBalance() + dto.getTotalClientsBalance());
			}			
		}
		// trim the ib cleint account model to top 20
		if(ibClientAccountModels.size() > 20){
			ibClientAccountModels = ibClientAccountModels.subList(0, 19);
		}
		

		//get master account account balance
		List<TradeAccountModel> tradeAccountModelList = getMt4TradingAccountModel(ibCode);
		dto.setTradeAccountList(tradeAccountModelList);

		dto.setIbMonthBalanceModel(ibMonthBalanceModel);
		dto.setIbAccountModel(ibAccountModel);
		dto.setIbClientAccountModels(ibClientAccountModels);

		return dto;
	}

	@Override
	public GetTradingAccountListResponseDto getTradingAccountList(GetTradingAccountListRequest request) throws Exception {
		GetTradingAccountListResponseDto dto = new GetTradingAccountListResponseDto();

		dto.setTrade_account_list(getMt4TradingAccountModel(request.getBody().getIb_code()));

		return dto;
	}

	private List<TradeAccountModel> getMt4TradingAccountModel(String ibCode) throws Exception {
		List<TradeAccountModel> tradeAccountModelList = new ArrayList<TradeAccountModel>();

		List<IbTradingAccountMapBean> ibTradingAccountMapList = ibTradingAccountMapDao.getIbTradingAccountMapByKey(ibCode);
		if (ibTradingAccountMapList.size() > 0) {
			for (IbTradingAccountMapBean ibTradingAccountMap : ibTradingAccountMapList) {

				logger.info("IB: " + ibTradingAccountMap.getIb_code() + ", IB trading account: " + ibTradingAccountMap.getTrading_account());
				GetUserProfileRequestDto getUserProfileRequestDto = new GetUserProfileRequestDto();
				Integer tradingAcc = Integer.parseInt(ibTradingAccountMap.getTrading_account());
				getUserProfileRequestDto.setLogin_id(tradingAcc);
				Mt4WebServiceConnectionModel model = systemParamsDao.getMt4ServiceConnectionModel();
				logger.info("Getting master account user profile from mt4 web service");
				String mt4Response = mt4ServiceAdapterFactory.sendRequest(model, getUserProfileRequestDto);
				Object mt4ResModel = mt4ServiceAdapterFactory.getResponseObject(getUserProfileRequestDto, mt4Response);
				if (mt4ResModel != null) {
					if (mt4ResModel instanceof GetUserProfileResponse) {
						// massage mt4 response model
						GetUserProfileResponse getUserProfileResponse = (GetUserProfileResponse) mt4ResModel;
						TradeAccountModel tradeAccountModel = new TradeAccountModel();
						tradeAccountModel.setAccount_balance(getUserProfileResponse.getModel().getAccountBalance());
						tradeAccountModel.setCurrency(getUserProfileResponse.getModel().getCurrency());
						tradeAccountModel.setAccount(tradingAcc.toString());
						tradeAccountModelList.add(tradeAccountModel);
					}
				}
			}
		}

		return tradeAccountModelList;
	}

	private IbSummaryByDateRangeModel getLastTradeDayIbSummary(String ibCode) throws Exception {
		List<IbSummaryByDateRangeModel> summaryBeanList = summaryDao.getIbSummaryByLastTradeDate(ibCode);
		IbSummaryByDateRangeModel rtnDto = null;
		if (summaryBeanList.size() > 0) {
			rtnDto = summaryBeanList.get(0);
		}
		return rtnDto;
	}

	private IbSummaryByDateRangeModel getIbSummaryByDateRange(String ibCode, Date startDate, Date endDate) throws Exception {

		List<IbSummaryByDateRangeModel> summaryBeanList = summaryDao.getIbSummaryByDateRange(ibCode, startDate, endDate);
		IbSummaryByDateRangeModel rtnDto = null;
		if (summaryBeanList.size() > 0) {
			rtnDto = summaryBeanList.get(0);
			rtnDto.setStart_date(startDate);
			rtnDto.setEnd_date(endDate);
		}
		return rtnDto;
	}

	@Override
	public List<String> validateGetIbSummary(GetIbSummaryRequest request) throws Exception {
		List<String> errorList = new ArrayList<String>();

		return errorList;
	}

	@Override
	public List<String> validateGetIbClientSummary(GetIbClientSummaryRequest request) throws Exception {
		List<String> errorList = new ArrayList<String>();
		return errorList;
	}

	@Override
	public GetIbClientSummaryResponseDto getIbClientSummary(GetIbClientSummaryRequest request) throws Exception {
		String parentIbCode = request.getBody().getIb_code();
		Date startDate = request.getBody().getStart_date();
		Date endDate = request.getBody().getEnd_date();

		List<IbClientSummariesModel> dbDtoList = clientSummaryDao.getIbClientSummary(parentIbCode, startDate, endDate);

		List<IbClientSummariesModel> dtoList = new ArrayList<IbClientSummariesModel>();
		for (IbClientSummariesModel dbDto : dbDtoList) {

			if (dbDto.getTotal_lot() != null || dbDto.getRebate() != null || dbDto.getCommission() != null || dbDto.getPl() != null
					|| dbDto.getNet_margin_in() != null) {
				dtoList.add(dbDto);
			}
		}

		GetIbClientSummaryResponseDto responseDto = new GetIbClientSummaryResponseDto();
		responseDto.setStart_date(startDate);
		responseDto.setEnd_date(endDate);
		responseDto.setIb_client_summaries(dtoList);
		return responseDto;

	}

	@Override
	public GetIbCommissionResponseDto getIbCommissionSummary(GetIbClientSummaryRequest request) throws Exception {
		String ibCode = request.getBody().getIb_code();
		Date startDate = request.getBody().getStart_date();
		Date endDate = request.getBody().getEnd_date();

		List<IbCommissionModel> dbDtoList = clientSummaryDao.getIbCommissionSummary(ibCode, startDate, endDate);

		List<IbCommissionModel> dtoList = new ArrayList<IbCommissionModel>();
		for (IbCommissionModel dbDto : dbDtoList) {

			if (dbDto.getCommission() != 0 || dbDto.getReferral_commission() != 0 || dbDto.getNet_margin_in() != 0 || dbDto.getPl() != 0
					|| dbDto.getReferral_pl() != 0 || dbDto.getRebate() != 0 || dbDto.getReferral_rebate() != 0 || dbDto.getTotal_lot() != 0
					|| dbDto.getReferral_total_lot() != 0) {
				dtoList.add(dbDto);
			}
		}

		GetIbCommissionResponseDto responseDto = new GetIbCommissionResponseDto();
		responseDto.setStart_date(startDate);
		responseDto.setEnd_date(endDate);
		responseDto.setIb_commission_summaries(dtoList);
		return responseDto;

	}

	@Override
	public GetIbCommissionDetailsResponseDto getIbCommissionSummaryDetails(GetIbClientSummaryRequest request) throws Exception {
		String ibCode = request.getBody().getIb_code();
		Date startDate = request.getBody().getStart_date();
		Date endDate = request.getBody().getEnd_date();

		List<IbCommissionDetailsModel> dbDtoList = clientSummaryDao.getIbCommissionSummaryDetails(ibCode, startDate, endDate);

		List<IbCommissionDetailsModel> dtoList = new ArrayList<IbCommissionDetailsModel>();
		for (IbCommissionDetailsModel dbDto : dbDtoList) {

			if (dbDto.getCommission() != 0 || dbDto.getNet_margin_in() != 0 || dbDto.getPl() != 0 || dbDto.getRebate() != 0
					|| dbDto.getTotal_lot() != 0) {
				dtoList.add(dbDto);
			}
		}

		GetIbCommissionDetailsResponseDto responseDto = new GetIbCommissionDetailsResponseDto();
		responseDto.setStart_date(startDate);
		responseDto.setEnd_date(endDate);
		responseDto.setIb_commission_summary_details(dtoList);
		return responseDto;

	}

	@Override
	public List<IbCommissionDetailsSupplementaryBean> prepareCommissionSupplementary(EvDataRequest request) throws Exception {
		String sender = getSender(request.getHeader());
		evProcessor.prepareData(sender, request.getBody().getStart_date(), request.getBody().getEnd_date());
		return commSupplementaryDao.getIbCommissionDetailsSupplementaryByDateRange(request.getBody().getStart_date(),
				request.getBody().getEnd_date());
	}

	@Override
	public List<IbCommissionDetailsSupplementaryBean> getCommissionSupplementary(EvDataRequest request) throws Exception {

		return commSupplementaryDao.getIbCommissionDetailsSupplementaryByDateRange(request.getBody().getStart_date(),
				request.getBody().getEnd_date());
	}

	@Override
	public List<String> validateUpdateCommissionSupplementaryAdjustment(EvFigureUpdateRequestDto request) throws Exception {
		List<String> errMsgs = new ArrayList<String>();

		for (EvFigureUpdateDto dto : request.getData()) {
			String ib_code = dto.getIb_code();
			Date tradeDate = dto.getTrade_date();
			String groupCode = dto.getGroup_code();

			IbCommissionDetailsSupplementaryBean commModel = commSupplementaryDao.getIbCommissionDetailsSupplementaryByKey(ib_code, tradeDate,
					groupCode);

			if (commModel != null) {
				if (!commModel.getStatus().equals(Constants.IB_COMMISSION_SUPPLEMENTARY_STATUS_PENDING)) {
					errMsgs.add(Constants.ERR_REBATE_EV_STATUS_NOT_PENDING);
					break;
				}
			}
		}

		return errMsgs;
	}

	@Override
	public List<IbCommissionDetailsSupplementaryBean> updateCommissionSupplementary(EvFigureUpdateRequest request) throws Exception {

		List<IbCommissionDetailsSupplementaryBean> beanList = new ArrayList<IbCommissionDetailsSupplementaryBean>();

		for (EvFigureUpdateDto dto : request.getBody().getData()) {
			String ib_code = dto.getIb_code();
			Double adjustment = dto.getAdjustment();
			Double depositBonus = dto.getDeposit_bonus();
			Date tradeDate = dto.getTrade_date();
			String groupCode = dto.getGroup_code();
			Double credit_card_charges = dto.getCredit_card_charges();

			IbCommissionDetailsSupplementaryBean commModel = commSupplementaryDao.getIbCommissionDetailsSupplementaryByKey(ib_code, tradeDate,
					groupCode);
			if (commModel != null) {
				double net_ev = commModel.getTotal_equity_change() + commModel.getClient_fix_commission() + commModel.getClient_rebate_commission()
						+ commModel.getDeficit() + commModel.getMargin_in_bonus() + depositBonus + adjustment;
				double ev_comm = -net_ev * commModel.getEv_percentage() / 100;
				commModel.setNet_ev(net_ev);
				commModel.setEv_commission(ev_comm);
				commModel.setAdjustment(adjustment);
				commModel.setDeposit_bonus(depositBonus);
				commModel.setCredit_card_charges(credit_card_charges);
				commModel.setLast_update_user(getSender(request.getHeader()));
				commModel.setLast_update_time(new Date());
				commSupplementaryDao.updateIbCommissionDetailsSupplementary(commModel);

				// retrieve updated models from db
				IbCommissionDetailsSupplementaryBean updatedModel = commSupplementaryDao.getIbCommissionDetailsSupplementaryByKey(ib_code, tradeDate,
						groupCode);
				beanList.add(updatedModel);

			}
		}
		return beanList;
	}

	@Override
	public List<String> validateConfirmCommissionSupplementary(EvConfirmRequest request) throws Exception {
		List<String> errMsgs = new ArrayList<String>();

		for (EvConfirmDto dto : request.getBody().getData()) {
			String ib_code = dto.getIb_code();
			Date tradeDate = dto.getTrade_date();
			String groupCode = dto.getGroup_code();

			IbCommissionDetailsSupplementaryBean commModel = commSupplementaryDao.getIbCommissionDetailsSupplementaryByKey(ib_code, tradeDate,
					groupCode);

			if (commModel != null) {
				if (!commModel.getStatus().equals(Constants.IB_COMMISSION_SUPPLEMENTARY_STATUS_PENDING)) {
					errMsgs.add(Constants.ERR_REBATE_EV_STATUS_NOT_PENDING);
					break;
				} else if (commModel.getEv_commission() == null) {
					errMsgs.add(Constants.ERR_REBATE_EV_IS_NULL);
					break;
				}
			}
		}

		return errMsgs;
	}

	@Override
	public List<IbCommissionDetailsSupplementaryBean> confirmCommissionSupplementary(EvConfirmRequest request) throws Exception {

		List<IbCommissionDetailsSupplementaryBean> beanList = new ArrayList<IbCommissionDetailsSupplementaryBean>();
		EvConfirmRequestDto body = request.getBody();
		for (EvConfirmDto dto : body.getData()) {
			String ib_code = dto.getIb_code();
			Date tradeDate = dto.getTrade_date();
			String groupCode = dto.getGroup_code();
			IbCommissionDetailsSupplementaryBean commModel = commSupplementaryDao.getIbCommissionDetailsSupplementaryByKey(ib_code, tradeDate,
					groupCode);
			if (commModel != null) {
				commModel.setStatus(Constants.IB_COMMISSION_SUPPLEMENTARY_STATUS_EXECUTED);
				commModel.setLast_update_time(new Date());
				commModel.setLast_update_user(getSender(request.getHeader()));
				commSupplementaryDao.updateIbCommissionDetailsSupplementary(commModel);
			}
			IbCommissionDetailsSupplementaryBean bean = commSupplementaryDao.getIbCommissionDetailsSupplementaryByKey(ib_code, tradeDate, groupCode);
			beanList.add(bean);
		}

		// for (IbCommissionDetailsSupplementaryBean model : beanList) {
		// MarginInBean marginIn = convertToMarginInBean(sender, model);
		// marginIn = saveMarginInAccountAmount(marginIn);
		// }

		return beanList;
	}

	@Override
	public List<IbCommissionDetailsSupplementaryBean> calculateCommissionSupplementary(EvFigureUpdateRequest request) throws ServiceException {
		try {
			String sender = getSender(request.getHeader());
			return evProcessor.calculate(sender, request.getBody().getStart_date(), request.getBody().getEnd_date());
		} catch (ServiceException se) {
			throw se;
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}

	@Override
	public String valideCalculateCommissionSupplementary(EvDataRequest request, BindingResult result) throws Exception {

		List<IbCommissionDetailsSupplementaryBean> data = commSupplementaryDao
				.getIbCommissionDetailsSupplementaryByDateRange(request.getBody().getStart_date(), request.getBody().getEnd_date());
		String errIbCodes = null;
		if (data != null && data.size() > 0) {
			List<String> ibCodeList = new ArrayList<String>();
			for (IbCommissionDetailsSupplementaryBean commModel : data) {
				if (commModel.getClient_fix_commission() == null || commModel.getClient_rebate_commission() == null || commModel.getDeficit() == null
						|| commModel.getDeposit_bonus() == null || commModel.getEv_percentage() == null || commModel.getMargin_in_bonus() == null) {
					ibCodeList.add(commModel.getIb_code());
				}
			}
			if (ibCodeList.size() > 0) {
				result.rejectValue("body", Constants.ERR_COMMISSION_CALC_EV_REQUIRED_DATA_IS_NULL,
						Constants.ERR_COMMISSION_CALC_EV_REQUIRED_DATA_IS_NULL);
				errIbCodes = StringUtils.join(ibCodeList, ",");
			}
		}

		return errIbCodes;

	}

	@Override
	public CalculateIbCommissionResponseDto calculateCommission(CalculateIbCommissionRequest request, String ibTeamHead, String sender)
			throws Exception {

		// initialize data
		Date tradeDate = request.getBody().getTradeDate();
		writeLog(">>--------------------");
		writeLog("Getting data from ib terminal database.");

		detailsProcessor.init(tradeDate, ibTeamHead);

		CalculateIbCommissionResponseDto response = new CalculateIbCommissionResponseDto();

		writeLog("Start calculating ib commission, trade date: " + DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, tradeDate));

		// calculate ib commission details bean
		Pair<List<IbCommissionDetailsBean>, HashMap<String, String>> result = detailsProcessor
				.calculateIbCommissionDetails(getSender(request.getHeader()), tradeDate);
		// Calculate CPA
		// remove the current date record.
		ibCommissionCpaDao.deleteIbCommissionCpa(tradeDate);
		CpaCalculateRequest cpaRequest = new CpaCalculateRequest();
		BaseTradeDateRequestBodyDto cpaDto = new BaseTradeDateRequestBodyDto();
		cpaDto.setTrade_date(request.getBody().getTradeDate());
		cpaRequest.setHeader(request.getHeader());
		cpaRequest.setBody(cpaDto);
		List<IbCommissionCpaBean> cpaResult = calculateCpaCommission(cpaRequest);

		response.setErrorMsgs(result.getValue());
		List<IbCommissionDetailsBean> detailsBeans = result.getKey();

		int numOfDetailsBeansCreated = detailsBeans.size();
		writeLog("Total number of commission details = " + numOfDetailsBeansCreated);
		writeLog("Total number of error = " + result.getValue().size());

		String clearMarginOutComment = "Recal IB comm";
		int numOfMarginInDeleted, numOfCommissionsDeleted;
		if (ibTeamHead == null || ibTeamHead.equals("")) {
			// calculate whole ib tree commission
			numOfCommissionsDeleted = clearCommissionDetails(tradeDate, tradeDate);
			clientSummaryDao.deleteIbCommissionClientSummaryByDateRange(tradeDate, tradeDate);
			summaryDao.deleteIbCommissionSummaryByDateRange(tradeDate, tradeDate);
			clearAccountDetails(tradeDate, detailsBeans);
			numOfMarginInDeleted = clearMarginIn(tradeDate, tradeDate, clearMarginOutComment, sender);
		} else {
			// calculate the ib team commission
			List<String> relatedIbcodes = detailsProcessor.getRelatedIbCodes();
			List<String> cpaIbCodeList = getCpaIbCodeList(cpaResult);
			if (cpaIbCodeList != null && cpaIbCodeList.size() > 0) {
				relatedIbcodes.addAll(cpaIbCodeList);
			}
			numOfCommissionsDeleted = clearCommissionDetails(tradeDate, tradeDate, relatedIbcodes);
			// delete whole ib commission client summary and ib commission
			// summary. Because the stored procedure will regenerate whole trade
			// date records
			clientSummaryDao.deleteIbCommissionClientSummaryByDateRange(tradeDate, tradeDate);
			summaryDao.deleteIbCommissionSummaryByDateRange(tradeDate, tradeDate);

			// To be check
			// clearAccountDetails(tradeDate, detailsBeans);
			clearAccountDetailsByIbCodes(tradeDate, relatedIbcodes);
			numOfMarginInDeleted = clearMarginIn(tradeDate, tradeDate, relatedIbcodes, clearMarginOutComment, sender);
		}

		saveCommissionDetails(detailsBeans, tradeDate, tradeDate);
		saveCpaCommission(cpaResult);
		generateCommissionClientSummary(tradeDate, tradeDate);
		generateCommissionSummary(tradeDate, tradeDate);
		int numOfMarginInCreated = generateMarginInByCommissionDetails(getSender(request.getHeader()), detailsBeans, tradeDate,
				Constants.MARGIN_IN_STATUS_EXECUTED, Constants.MARGIN_IN_CATEGORY_REBATE, "");
		generateMarginInByCommissionCpa(getSender(request.getHeader()), cpaResult, tradeDate, Constants.MARGIN_IN_STATUS_EXECUTED,
				Constants.MARGIN_IN_CATEGORY_REBATE_CPA, "By CPA");
		response.setNumOfIbCommissionCreated(numOfDetailsBeansCreated);
		response.setNumOfMarginInCreated(numOfMarginInCreated);
		response.setNumOfIbCommissionDeleted(numOfCommissionsDeleted);
		response.setNumOfMarginInDeleted(numOfMarginInDeleted);

		writeLog("All finish!");
		writeLog("--------------------<<");

		return response;

	}

	private List<String> getCpaIbCodeList(List<IbCommissionCpaBean> list) {
		List<String> ibCodeList = new ArrayList<String>();
		if (list != null && list.size() > 0) {
			for (IbCommissionCpaBean cpa : list) {
				ibCodeList.add(cpa.getIb_code());
			}
		}
		return ibCodeList;
	}

	private int generateMarginInByCommissionDetails(String sender, List<IbCommissionDetailsBean> ibCommissionDetailsBeans, Date tradeDate,
			String status, String category, String comment) throws Exception {
		Map<String, MarginInBean> map = getMarginInMapByIbCommissionDetails(sender, ibCommissionDetailsBeans, tradeDate, status, category, comment);
		return generateMarginIn(sender, map);
	}

	private int generateMarginInByCommissionCpa(String sender, List<IbCommissionCpaBean> ibCommissionCpaBeans, Date tradeDate, String status,
			String category, String comment) throws Exception {
		Map<String, MarginInBean> map = getMarginInMapByIbCommissionCpa(sender, ibCommissionCpaBeans, tradeDate, status, category, comment);
		return generateMarginIn(sender, map);
	}

	private void saveCpaCommission(List<IbCommissionCpaBean> list) {

		if (list != null && list.size() > 0) {
			for (IbCommissionCpaBean obj : list) {
				try {
					ibCommissionCpaDao.saveIbCommissionCpa(obj);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error(e, e);
				}
			}
		}
	}

	@Override
	public List<IbCommissionDetailsSupplementaryBean> calculateEvCommissionByGroup(EvDataRequest request) throws Exception {
		String sender = getSender(request.getHeader());
		groupEvProcessor.prepareData(sender, request.getBody().getStart_date(), request.getBody().getEnd_date());
		return commSupplementaryDao.getIbCommissionDetailsSupplementaryByDateRange(request.getBody().getStart_date(),
				request.getBody().getEnd_date());

	}

	@Override
	public List<IbCommissionDetailsSupplementaryBean> confirmEvCommissionByGroup(EvFigureUpdateRequest request) throws Exception {
		String sender = getSender(request.getHeader());
		groupEvProcessor.calculateByTarget(sender, request.getBody().getData(), request.getBody().getStart_date(), request.getBody().getEnd_date());

		List<String> groups = new ArrayList<String>();
		List<String> ibs = new ArrayList<String>();
		for (EvFigureUpdateDto dto : request.getBody().getData()) {
			if (!groups.contains(dto.getGroup_code()))
				groups.add(dto.getGroup_code());

			if (!ibs.contains(dto.getIb_code()))
				ibs.add(dto.getIb_code());
		}

		return commSupplementaryDao.getIbCommissionDetailsSupplementaryByGroupCodeIbCodeAndDateRange(groups, ibs, request.getBody().getEnd_date());
	}

	@Override
	public boolean valideCalculationOfEV(EvDataRequest request) {

		try {
			List<IbCommissionDetailsSupplementaryBean> list = commSupplementaryDao.getRecordsByDateRangeStatus(request.getBody().getStart_date(),
					request.getBody().getEnd_date(), Constants.IB_COMMISSION_SUPPLEMENTARY_STATUS_EXECUTED);
			if (list == null || list.size() == 0)
				return true;
		} catch (Exception e) {
			log.error(e, e);
		}

		return false;
	}

	@Override
	public List<IbCommissionCpaBean> calculateCpaCommission(CpaCalculateRequest request) {
		try {
			String sender = getSender(request.getHeader());
			return ibCommissionCpaProcessor.calculate(sender, request.getBody().getTrade_date(), request.getBody().getTrade_date());
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;
	}

}
