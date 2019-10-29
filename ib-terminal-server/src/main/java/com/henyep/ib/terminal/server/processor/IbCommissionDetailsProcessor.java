package com.henyep.ib.terminal.server.processor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.api.dto.db.ClientTradeDetailsBean;
import com.henyep.ib.terminal.api.dto.db.IbClientMapBean;
import com.henyep.ib.terminal.api.dto.db.IbClientRebateMapBean;
import com.henyep.ib.terminal.api.dto.db.IbCommissionDetailsBean;
import com.henyep.ib.terminal.api.dto.db.IbTreeBean;
import com.henyep.ib.terminal.api.dto.db.ProductSymbolMapBean;
import com.henyep.ib.terminal.api.dto.db.RebateBean;
import com.henyep.ib.terminal.api.dto.db.RebateDetailsBean;
import com.henyep.ib.terminal.api.dto.db.SettingsPointValueBean;
import com.henyep.ib.terminal.api.dto.db.SettingsSymbolBean;
import com.henyep.ib.terminal.api.dto.db.SettingsUsdCurrencyExchangeBean;
import com.henyep.ib.terminal.server.dao.ClientTradeDetailsDao;
import com.henyep.ib.terminal.server.dao.IbClientMapDao;
import com.henyep.ib.terminal.server.dao.IbClientRebateMapDao;
import com.henyep.ib.terminal.server.dao.IbTreeDao;
import com.henyep.ib.terminal.server.dao.ProductSymbolMapDao;
import com.henyep.ib.terminal.server.dao.RebateDao;
import com.henyep.ib.terminal.server.dao.RebateDetailsDao;
import com.henyep.ib.terminal.server.dao.SettingsPointValueDao;
import com.henyep.ib.terminal.server.dao.SettingsSymbolDao;
import com.henyep.ib.terminal.server.dao.SettingsUsdCurrencyExchangeDao;
import com.henyep.ib.terminal.server.global.Constants;
import com.henyep.ib.terminal.server.util.ClientRebateMapUtil;
import com.henyep.ib.terminal.server.util.DateUtil;
import com.henyep.ib.terminal.server.util.IbClientMapUtil;
import com.henyep.ib.terminal.server.util.IbTreeUtil;
import com.henyep.ib.terminal.server.util.ProductSymbolMapUtil;
import com.henyep.ib.terminal.server.util.RebateDetailUtil;
import com.henyep.ib.terminal.server.util.SettingsPointValueUtil;
import com.henyep.ib.terminal.server.util.SettingsSymbolUtil;

@Component("IbCommissionDetailsProcessor")
public class IbCommissionDetailsProcessor {

	final static Logger log = Logger.getLogger(IbCommissionDetailsProcessor.class);
	protected List<ClientTradeDetailsBean> trades;

	protected List<IbTreeBean> allIbTreeBeans;
	protected IbTreeUtil ibTreeUtil;

	protected List<RebateDetailsBean> allRebateDetailsBeans;
	protected List<RebateBean> allRebateBeans;
	protected RebateDetailUtil rebateDetailUtil;

	protected List<ProductSymbolMapBean> allProductSymbolMaps;
	protected ProductSymbolMapUtil productSymbolMapUtil;

	protected List<SettingsPointValueBean> allSettingsPointValueBeans;
	protected SettingsPointValueUtil settingsPointValueUtil;

	protected List<IbClientRebateMapBean> ibClientRebateMapBeans;
	protected ClientRebateMapUtil clientRebateMapUtil;

	protected List<SettingsUsdCurrencyExchangeBean> exchangeBeans;
	protected List<SettingsSymbolBean> settingSymbolBeans;
	protected SettingsSymbolUtil settingSymbolUtil;

	protected IbClientMapUtil ibClientMapUtil;

	@Resource(name = "ProductSymbolMapDao")
	protected ProductSymbolMapDao productSymbolMapDao;
	@Resource(name = "RebateDetailsDao")
	protected RebateDetailsDao rebateDetailsDao;
	@Resource(name = "RebateDao")
	protected RebateDao rebateDao;
	@Resource(name = "IbTreeDao")
	protected IbTreeDao ibTreeDao;
	@Resource(name = "SettingsPointValueDao")
	protected SettingsPointValueDao settingsPointValueDao;

	@Resource(name = "ClientTradeDetailsDao")
	protected ClientTradeDetailsDao clientTradeDetailsDao;

	@Resource(name = "SettingsSymbolDao")
	protected SettingsSymbolDao settingSymbolDao;

	@Resource(name = "IbClientMapDao")
	protected IbClientMapDao ibClientMapDao;
	@Resource(name = "IbClientRebateMapDao")
	protected IbClientRebateMapDao ibClientRebateMapDao;

	@Resource(name = "SettingsUsdCurrencyExchangeDao")
	protected SettingsUsdCurrencyExchangeDao settingsUsdCurrencyExchangeDao;

	protected boolean isErrorExist = false;

	public boolean hasError() {
		return isErrorExist;
	}

	public void setHasError(boolean isErrorExist) {
		this.isErrorExist = isErrorExist;
	}

	/*
	 * get all required data from database first
	 */
	public void init(Date tradeDate, String ibTeamHead) throws Exception {
		allIbTreeBeans = ibTreeDao.getCurrentIbTrees(tradeDate, tradeDate);
		if (ibTeamHead != null && !ibTeamHead.equals("")) {
			allIbTreeBeans = getIbTreeBeanByTeamHead(ibTeamHead, allIbTreeBeans);
		}
		ibTreeUtil = new IbTreeUtil();

		allRebateDetailsBeans = rebateDetailsDao.getAllRebateDetailss();
		allRebateBeans = rebateDao.getAllRebates();
		rebateDetailUtil = new RebateDetailUtil(allRebateDetailsBeans, allRebateBeans, tradeDate);

		allProductSymbolMaps = productSymbolMapDao.getAllProductSymbolMaps();
		productSymbolMapUtil = new ProductSymbolMapUtil(allProductSymbolMaps);

		allSettingsPointValueBeans = settingsPointValueDao.getAllSettingsPointValues();
		settingsPointValueUtil = new SettingsPointValueUtil(allSettingsPointValueBeans);

		ibClientRebateMapBeans = ibClientRebateMapDao.getAllIbClientRebateMaps();
		clientRebateMapUtil = new ClientRebateMapUtil(ibClientRebateMapBeans);
		initClientTradeDetails(tradeDate, tradeDate);

		exchangeBeans = settingsUsdCurrencyExchangeDao.getAllSettingsUsdCurrencyExchanges();
		settingSymbolBeans = settingSymbolDao.getAllSettingsSymbols(tradeDate);
		settingSymbolUtil = new SettingsSymbolUtil(settingSymbolBeans, exchangeBeans);

		initCltCodeIbMapUtil(trades);

	}

	public List<String> getRelatedIbCodes() {
		List<String> rtnList = new ArrayList<String>();
		for (IbTreeBean ibTreeBean : allIbTreeBeans) {
			if (!rtnList.contains(ibTreeBean.getIb_code())) {
				rtnList.add(ibTreeBean.getIb_code());
			}
		}
		return rtnList;
	}

	public List<IbTreeBean> getIbTreeBeanByTeamHead(String teamHead, List<IbTreeBean> ibTreeBeans) {

		List<IbTreeBean> rtnList = new ArrayList<IbTreeBean>();

		IbTreeBean teamHeadBean = null;

		for (IbTreeBean ibTreeBean : ibTreeBeans) {
			if (ibTreeBean.getIb_code().equals(teamHead)) {
				teamHeadBean = ibTreeBean;
				break;
			}
		}
		if (teamHeadBean != null) {
			
			for (IbTreeBean ibTreeBean : ibTreeBeans) {
				if (ibTreeBean.getMin_id() >= teamHeadBean.getMin_id() && ibTreeBean.getMax_id() <= teamHeadBean.getMax_id()) {
					if(ibTreeBean.isIs_ib()){
						rtnList.add(ibTreeBean);
					}
				}
			}
			
		}
		return rtnList;

	}

	protected void initClientTradeDetails(Date startDate, Date endDate) throws Exception {

		trades = clientTradeDetailsDao.getClientTradeDetailsByDateRange(startDate, endDate);

	}

	private void initCltCodeIbMapUtil(List<ClientTradeDetailsBean> trades) throws Exception {
		List<Integer> cltCodes = new ArrayList<Integer>();

		for (ClientTradeDetailsBean trade : trades) {
			if (!cltCodes.contains(trade.getClient_code())) {
				cltCodes.add(trade.getClient_code());
			}
		}

		List<IbClientMapBean> ibClientMapBeans = ibClientMapDao.getIbClientMapByClientCodes(cltCodes);
		ibClientMapUtil = new IbClientMapUtil(ibClientMapBeans);

	}

	// calculate ib commission by input date range
	public Pair<List<IbCommissionDetailsBean>, HashMap<String, String>> calculateIbCommissionDetails(String user , Date tradeDate) throws Exception {

		List<IbCommissionDetailsBean> result = new ArrayList<IbCommissionDetailsBean>();

		HashMap<String, String> ibErrors = new HashMap<String, String>();

		for (ClientTradeDetailsBean trade : trades) {

			// set cltCode
			Integer cltCode = trade.getClient_code();
			// set ibCode
			String clientIbCode = null;
			IbClientMapBean ibClientMapBean = ibClientMapUtil.getIbClientMapByCltCode(cltCode.toString(), trade.getTrade_date());
			List<IbTreeBean> ibTreeBeans = null;
			
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, 2018);
			cal.set(Calendar.DAY_OF_MONTH, 31);
			cal.set(Calendar.MONTH, 06);
		    cal.set(Calendar.HOUR_OF_DAY, 23);
		    cal.set(Calendar.MINUTE, 59);
		    cal.set(Calendar.SECOND, 59);
		    cal.set(Calendar.MILLISECOND, 59);
		    

			if (ibClientMapBean != null) {
				// client's ib exist
				clientIbCode = ibClientMapBean.getIb_code();

				ibTreeBeans = ibTreeUtil.getByIbCode(allIbTreeBeans, clientIbCode, trade.getTrade_date());

				if (ibTreeBeans != null && ibTreeBeans.size() > 0) {

					// ib on tree
					double lastCommissionLot = 0;
					double toMasterAmount = 0;
					double lastFixCommission = 0;
					double lastCommissionPip = 0;

					for (IbTreeBean ibTreeBean : ibTreeBeans) {
						
						boolean isOnlyFixCommission =
								( 
										trade.getJurisdiction().equalsIgnoreCase("FCA") && trade.getTrade_date().after(cal.getTime()) && ibTreeBean.getBrand_code().equalsIgnoreCase("CN")  
								);

						String ibCode = ibTreeBean.getIb_code();
						String symbol = trade.getSymbol();

						String rebateCode = clientRebateMapUtil.getClientRebateCode(cltCode.toString(), ibCode, trade.getTrade_date());
						List<String> availableProductGroups = rebateDetailUtil.getAvailableProductGroups(rebateCode, trade.getSpread_type());
						String productGroup = productSymbolMapUtil.getProductGroup(symbol, trade.getTrade_date(), availableProductGroups);

						// product group exist?
						if (productGroup == null) {
							if (!ibErrors.containsKey(ibTreeBean.getIb_code())) {
								String tradeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, trade.getTrade_date());
								ibErrors.put(ibTreeBean.getIb_code(),
										Constants.ERR_REBATE_SYMBOL_NO_PRODUCT_GROUP + "|" + symbol + "|" + tradeDateString);
							}

						}
						// rebate code exist?
						else if (rebateCode == null) {
							if (!ibErrors.containsKey(ibTreeBean.getIb_code())) {
								String tradeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, trade.getTrade_date());
								ibErrors.put(ibTreeBean.getIb_code(),
										Constants.ERR_REBATE_ACCOUNT_NO_REBATE_CODE + "|" + ibCode + "|" + tradeDateString);
							}
						}

						String clientPackageCode = ibClientMapBean.getClient_package_code();
						int accountIdentify = trade.getAccount_identify();

						String platform = "";
						if (accountIdentify == Constants.PLATFORM_STAR)
							platform = "STAR";
						else if (accountIdentify == Constants.PLATFORM_MT4)
							platform = "MT4";

						boolean isIgnoreIbTreeRebate = false;
						if (!ibCode.equals(clientIbCode)) {
							isIgnoreIbTreeRebate = rebateDetailUtil.getIsIgoureIbTreeRebate(rebateCode);
						}

						if (!isIgnoreIbTreeRebate) {
							RebateDetailsBean rebateDetail = rebateDetailUtil.getRebateDetails(rebateCode, trade.getSpread_type(), clientPackageCode, productGroup,
									trade.getTrade_date());
							if (rebateDetail == null) {
								// try other rebate Detail
								if (!productGroup.equals(Constants.PRODUCT_GROUP_OTHER)) {
									rebateDetail = rebateDetailUtil.getRebateDetails(rebateCode, trade.getSpread_type(), clientPackageCode, Constants.PRODUCT_GROUP_OTHER,
											trade.getTrade_date());
									productGroup = Constants.PRODUCT_GROUP_OTHER;
								}
							}

							if (rebateDetail == null) {

								if (!ibErrors.containsKey(ibTreeBean.getIb_code())) {
									String tradeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, trade.getTrade_date());
									String errorCode = Constants.ERR_REBATE_IB_NO_REBATE_FOUND + "|" + rebateCode + "|" + trade.getSpread_type() + "|" + clientPackageCode + "|"
											+ productGroup + "|" + tradeDateString;
									ibErrors.put(ibTreeBean.getIb_code(), errorCode);
								}
							} else if (trade.getBs().equals(Constants.COMMON_BUY) || trade.getBs().equals(Constants.COMMON_SELL)) {

								if ((rebateDetail.getGroup_code() == null || rebateDetail.getGroup_code().equals(Constants.TYPE_ALL))
										|| (trade.getGroup_code() != null && trade.getGroup_code().equals(rebateDetail.getGroup_code()))) {

									IbCommissionDetailsBean ibCommissionDetailsBean = new IbCommissionDetailsBean();

									String bs = trade.getBs();

									ibCommissionDetailsBean.setProduct_group(productGroup);
									ibCommissionDetailsBean.setSymbol(trade.getSymbol());
									ibCommissionDetailsBean.setDeposit(0.0);

									ibCommissionDetailsBean.setClient_ib_code(clientIbCode);
									ibCommissionDetailsBean.setGroup_code(trade.getGroup_code());
									ibCommissionDetailsBean.setRebate_code(rebateDetail.getRebate_code());

									ibCommissionDetailsBean.setCurrency(rebateDetail.getCurrency());

									ibCommissionDetailsBean.setTrade_swaps(trade.getSwap());
									ibCommissionDetailsBean.setTicket(trade.getTicket());
									ibCommissionDetailsBean.setLast_update_user(user);
									ibCommissionDetailsBean.setBuy_sell(bs);
									ibCommissionDetailsBean.setPlatform(platform);
									ibCommissionDetailsBean.setLast_update_time(new Date());

									ibCommissionDetailsBean.setTrade_pl(trade.getPl());

									ibCommissionDetailsBean.setBrand_code(ibTreeBean.getBrand_code());
									ibCommissionDetailsBean.setClient_code(trade.getClient_code() + "");
									ibCommissionDetailsBean.setTrade_date(trade.getTrade_date());
									ibCommissionDetailsBean.setClose_trade_time(trade.getClose_time());

									ibCommissionDetailsBean.setLot(trade.getLot());
									ibCommissionDetailsBean.setIb_code(ibCode);
									ibCommissionDetailsBean.setOpen_trade_time(trade.getOpen_time());
									ibCommissionDetailsBean.setSpread_type(trade.getSpread_type());
									ibCommissionDetailsBean.setJurisdiction(trade.getJurisdiction());
									// product_group, symbol, buy_sell,
									// rebate_type_lot = null, rebate_per_lot,
									// -----------------------------------------------------------------------------------
									// client fix commission
									
									log.debug("ticket: " + trade.getTicket() + " ibCode: " + ibCode);
									
									double clientFixCommission = 0;
									if (rebateDetail.getClient_fix_commission_type().equals(Constants.TYPE_FIX_AMOUNT)) {
										clientFixCommission = (rebateDetail.getClient_fix_commission() * trade.getLot()) - lastFixCommission;
										lastFixCommission += clientFixCommission;
									} else if (rebateDetail.getClient_fix_commission_type().equals(Constants.TYPE_PERCENTAGE)) {
										Double clientFixCommissionPrecentage = rebateDetail.getClient_fix_commission();
										clientFixCommission = trade.getCommission() * -1 * clientFixCommissionPrecentage / 100.0 - lastFixCommission;
										lastFixCommission += clientFixCommission;
									}
									ibCommissionDetailsBean.setClient_fix_commission(clientFixCommission);
									// -----------------------------------------------------------------------------------
									// rebate commission
									if(rebateDetail.getRebate_method().equals(Constants.REBATE_METHOD_SEPERATED)){		
										toMasterAmount = handleSeperatedRebateCommission(rebateDetail, ibCommissionDetailsBean, trade, ibTreeBean, ibClientMapBean, ibErrors, toMasterAmount);
									}
									else{
										lastCommissionLot = handleAccumulatedRebateCommission(rebateDetail, ibCommissionDetailsBean, trade, ibTreeBean, ibClientMapBean, ibErrors, lastCommissionLot);
									}
								
									// CN client with FCA jurisdiction -> no rebate only fix commission 
									if(isOnlyFixCommission){
										ibCommissionDetailsBean.setRebate_per_lot(0);
										ibCommissionDetailsBean.setRebate_commission_lot(0);
										toMasterAmount = 0;
										lastCommissionLot = 0;
									}
									
									result.add(ibCommissionDetailsBean);
									log.debug(ibCommissionDetailsBean.toString());
								}
							}
						}
					}
				}
			}
		}

		// remove error ib tree team details bean
		List<String> errorIbCodes = new ArrayList<String>();
		for (String errorIbCode : ibErrors.keySet()) {
			List<IbTreeBean> errorIbTreeBeans = ibTreeUtil.getByIbCode(allIbTreeBeans, errorIbCode, tradeDate);
			if (errorIbTreeBeans != null && errorIbTreeBeans.size() > 0) {
				// get the ib head first
				IbTreeBean ibHead = errorIbTreeBeans.get(0);
				// get ib head's all childs
				errorIbTreeBeans = ibTreeUtil.getIbChilds(allIbTreeBeans, ibHead.getIb_code(), tradeDate);
				for (IbTreeBean errorIbTreeBean : errorIbTreeBeans) {
					if (!errorIbCodes.contains(errorIbTreeBean.getIb_code())) {
						errorIbCodes.add(errorIbTreeBean.getIb_code());
					}
				}
			}

		}

		List<IbCommissionDetailsBean> finalResult = new ArrayList<IbCommissionDetailsBean>();
		if (errorIbCodes.size() > 0) {
			for (IbCommissionDetailsBean detailsBean : result) {
				if (!errorIbCodes.contains(detailsBean.getIb_code())) {
					finalResult.add(detailsBean);
				}
			}
		} else {
			finalResult = result;
		}

		return Pair.of(finalResult, ibErrors);
	}
		
	private Double getRebatePerLotFromSettingBean(String symbol, Double spreadPrecentage){
		SettingsSymbolBean settingSymbolBean = settingSymbolUtil.GetSettingBean(symbol);
		if (settingSymbolBean != null) {
			Double rebatePerLot = settingSymbolBean.getContract_size() 
					* settingSymbolBean.getSpread()
					* settingSymbolBean.getTick_size() 
					* settingSymbolBean.getBase_currency_rate() 
					* spreadPrecentage / 100;
			return rebatePerLot;
		}else{
			return null;
		}
	}
	
	private Double getRebatePipPerLotFromSettingBean(String symbol, Double pips){
		SettingsSymbolBean settingSymbolBean = settingSymbolUtil.GetSettingBean(symbol);
		if (settingSymbolBean != null) {
			Double exchangeRate = settingSymbolUtil.GetExchangeRate(settingSymbolBean.getCurrency());
			
			if(exchangeRate != null){
				Double rebatePerLot = settingSymbolBean.getContract_size()
						* settingSymbolBean.getTick_size() 
						* exchangeRate 
						* pips;
				return rebatePerLot;
			}
		}
		return null;
	}
	
	private Double handleAccumulatedRebateCommission(RebateDetailsBean rebateDetail, IbCommissionDetailsBean ibCommissionDetailsBean, 
			ClientTradeDetailsBean trade, IbTreeBean ibTreeBean, IbClientMapBean ibClientMapBean, HashMap<String, String> ibErrors, Double lastCommissionLot){
		
		
		String ibCode = ibTreeBean.getIb_code();
		
		
		// Accumulated commission
		double packageRebateCommissionVal = rebateDetail.getRebate_commission();
		String packageRebateCommissionType = rebateDetail.getRebate_type();
		// fix amount
		if (packageRebateCommissionType.equals(Constants.TYPE_FIX_AMOUNT)) {
			double rebateCommissionLot = trade.getLot() * packageRebateCommissionVal - lastCommissionLot;
			ibCommissionDetailsBean.setRebate_commission_lot(rebateCommissionLot);
			ibCommissionDetailsBean.setRebate_type_lot(Constants.TYPE_FIX_AMOUNT);
			ibCommissionDetailsBean.setRebate_per_lot(packageRebateCommissionVal);
			lastCommissionLot += rebateCommissionLot;
		}
		// Percentage
		else if (packageRebateCommissionType.equals(Constants.TYPE_PERCENTAGE)) {
			// TODO
			ibCommissionDetailsBean.setRebate_commission_lot(trade.getLot() * packageRebateCommissionVal / 100);
			ibCommissionDetailsBean.setRebate_type_lot(Constants.TYPE_PERCENTAGE);
			ibCommissionDetailsBean.setRebate_per_lot(packageRebateCommissionVal);
		}
		// spread
		else if (packageRebateCommissionType.equals(Constants.TYPE_SPREAD) || packageRebateCommissionType.equals(Constants.TYPE_PIP)) {
			Double rebatePerLot = null;
			if(packageRebateCommissionType.equals(Constants.TYPE_SPREAD)){
				// spread
				rebatePerLot = getRebatePerLotFromSettingBean(trade.getSymbol(), packageRebateCommissionVal);
			}
			else{
				// spread pip
				rebatePerLot = getRebatePipPerLotFromSettingBean(trade.getSymbol(), packageRebateCommissionVal);
			}
			
			if(rebatePerLot != null){
				double rebateCommissionLot = rebatePerLot * trade.getLot() - lastCommissionLot;

				ibCommissionDetailsBean.setRebate_per_lot(rebatePerLot);
				ibCommissionDetailsBean.setRebate_commission_lot(rebateCommissionLot);
				ibCommissionDetailsBean.setRebate_type_lot(packageRebateCommissionType);
				lastCommissionLot += rebateCommissionLot;
			}
			else{
				// no symbol settings
				if (!ibErrors.containsKey(ibTreeBean.getIb_code())) {
					String tradeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE,
							trade.getTrade_date());
					ibErrors.put(ibTreeBean.getIb_code(), Constants.ERR_REBATE_NO_SYMBOL_SETTING + "|" + ibCode + "|"
							+ trade.getSymbol() + "|" + tradeDateString);
				}
			}
		}
		// spread pips
//		else if (packageRebateCommissionType.equals(Constants.TYPE_PIP)) {
			// TODO
//			SettingsPointValueBean settingPointValue = settingsPointValueUtil.getSettingsPoint(symbol,
//					trade.getTrade_date());
//			if (settingPointValue != null) {
//				double rebateCommissionPip = trade.getLot() * packageRebateCommissionVal * settingPointValue.getAmount()
//						- lastCommissionPip;
//				ibCommissionDetailsBean.setRebate_commission_pip(rebateCommissionPip);
//				ibCommissionDetailsBean.setRebate_type_pip(Constants.TYPE_PIP);
//				ibCommissionDetailsBean.setRebate_per_pip(packageRebateCommissionVal);
//				lastCommissionPip += rebateCommissionPip;
//			} else {
//				ibCommissionDetailsBean.setRebate_type_pip(Constants.TYPE_PIP);
//				ibCommissionDetailsBean.setRebate_per_pip(packageRebateCommissionVal);
//			}

//		}		
		
		return lastCommissionLot;
	}
	
	private Double handleSeperatedRebateCommission(RebateDetailsBean rebateDetail, IbCommissionDetailsBean ibCommissionDetailsBean, 
			ClientTradeDetailsBean trade, IbTreeBean ibTreeBean, IbClientMapBean ibClientMapBean, HashMap<String, String> ibErrors, Double toMasterAmount){
		
		// Separated direct and to master
		double packageRebateDirectVal = rebateDetail.getRebate_commission();
		String packageRebateDirectType = rebateDetail.getRebate_type();
		double packageRabateToMasterVal = rebateDetail.getRebate_to_master();
		String packageRabateToMasterType = rebateDetail.getRebate_to_master_type();
		
		if(ibTreeBean.getIb_code().equals(ibClientMapBean.getIb_code())){
			// direct
			if(packageRebateDirectType.equals(Constants.TYPE_FIX_AMOUNT)){
				// fix
				ibCommissionDetailsBean.setRebate_commission_lot(packageRebateDirectVal * trade.getLot());
				ibCommissionDetailsBean.setRebate_type_lot(Constants.TYPE_FIX_AMOUNT);
				ibCommissionDetailsBean.setRebate_per_lot(packageRebateDirectVal);
				toMasterAmount = packageRabateToMasterVal * trade.getLot();
			}
			else{
				Double directRebatePerLot = null;
				Double toMasterRebatePerLot = null;
				if(packageRebateDirectType.equals(Constants.TYPE_SPREAD)){
					directRebatePerLot = getRebatePerLotFromSettingBean(trade.getSymbol(), packageRebateDirectVal);
					toMasterRebatePerLot = getRebatePerLotFromSettingBean(trade.getSymbol(), packageRabateToMasterVal);
				}
				else{
					directRebatePerLot = getRebatePipPerLotFromSettingBean(trade.getSymbol(), packageRebateDirectVal);
					toMasterRebatePerLot = getRebatePipPerLotFromSettingBean(trade.getSymbol(), packageRabateToMasterVal);
				}
				// spread
				
				if(directRebatePerLot != null && toMasterRebatePerLot != null){
					double rebateCommissionLot = directRebatePerLot * trade.getLot();
					ibCommissionDetailsBean.setRebate_per_lot(directRebatePerLot);
					ibCommissionDetailsBean.setRebate_commission_lot(rebateCommissionLot);
					ibCommissionDetailsBean.setRebate_type_lot(packageRebateDirectType);
					toMasterAmount = toMasterRebatePerLot * trade.getLot();
				}
				else{
					// no symbol settings
					if (!ibErrors.containsKey(ibTreeBean.getIb_code())) {
						String tradeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE,
								trade.getTrade_date());
						ibErrors.put(ibTreeBean.getIb_code(), Constants.ERR_REBATE_NO_SYMBOL_SETTING + "|" + ibTreeBean.getIb_code() + "|"
								+ trade.getSymbol() + "|" + tradeDateString);
					}
				}
			}
			
		}
		else{
			// to master
			if(packageRabateToMasterType.equals(Constants.TYPE_FIX_AMOUNT)){
				// fix
				ibCommissionDetailsBean.setRebate_commission_lot(toMasterAmount - packageRabateToMasterVal * trade.getLot());
				ibCommissionDetailsBean.setRebate_type_lot(Constants.TYPE_FIX_AMOUNT);
				if(trade.getLot() != 0){
					ibCommissionDetailsBean.setRebate_per_lot((toMasterAmount - packageRabateToMasterVal * trade.getLot()) / trade.getLot());
				}else{
					ibCommissionDetailsBean.setRebate_per_lot(0);
				}
				
				toMasterAmount = packageRabateToMasterVal * trade.getLot();
			}
			else{
				Double directRebatePerLot = null;
				Double toMasterRebatePerLot = null;
				if(packageRebateDirectType.equals(Constants.TYPE_SPREAD)){
					directRebatePerLot = getRebatePerLotFromSettingBean(trade.getSymbol(), packageRebateDirectVal);
					toMasterRebatePerLot = getRebatePerLotFromSettingBean(trade.getSymbol(), packageRabateToMasterVal);
				}
				else{
					directRebatePerLot = getRebatePipPerLotFromSettingBean(trade.getSymbol(), packageRebateDirectVal);
					toMasterRebatePerLot = getRebatePipPerLotFromSettingBean(trade.getSymbol(), packageRabateToMasterVal);
				}
				if(directRebatePerLot != null && toMasterRebatePerLot != null){
					double rebateCommissionLot = directRebatePerLot * trade.getLot();
					ibCommissionDetailsBean.setRebate_per_lot(directRebatePerLot);
					ibCommissionDetailsBean.setRebate_commission_lot(rebateCommissionLot);
					ibCommissionDetailsBean.setRebate_type_lot(packageRebateDirectType);
					toMasterAmount = toMasterRebatePerLot * trade.getLot();
				}
				else{
					// no symbol settings
					if (!ibErrors.containsKey(ibTreeBean.getIb_code())) {
						String tradeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE,
								trade.getTrade_date());
						ibErrors.put(ibTreeBean.getIb_code(), Constants.ERR_REBATE_NO_SYMBOL_SETTING + "|" + ibTreeBean.getIb_code() + "|"
								+ trade.getSymbol() + "|" + tradeDateString);
					}
				}
			}
			
		}
		return toMasterAmount;
	}

}
