package com.henyep.ib.terminal.server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.henyep.ib.terminal.api.dto.db.ClientBalanceSummaryBean;
import com.henyep.ib.terminal.api.dto.db.ClientDetailsBean;
import com.henyep.ib.terminal.api.dto.db.IbClientMapBean;
import com.henyep.ib.terminal.api.dto.request.ib.client.map.GetIbClientMapByIbCodeClientCodeRequest;
import com.henyep.ib.terminal.api.dto.request.ib.client.map.GetIbClientMapRequest;
import com.henyep.ib.terminal.api.dto.request.ib.client.map.InsertFromWlRegistrationRequest;
import com.henyep.ib.terminal.api.dto.request.ib.client.map.InsertIbClientMapRequest;
import com.henyep.ib.terminal.api.dto.request.ib.client.map.SwitchIbClientMapRequest;
import com.henyep.ib.terminal.api.dto.request.ib.client.map.UpdateIbClientMapRequest;
import com.henyep.ib.terminal.api.dto.response.ib.client.map.InsertFromWlRegistrationResponseDto;
import com.henyep.ib.terminal.server.dao.ClientBalanceSummaryDao;
import com.henyep.ib.terminal.server.dao.ClientDetailsDao;
import com.henyep.ib.terminal.server.dao.IbClientMapDao;
import com.henyep.ib.terminal.server.global.Constants;
import com.henyep.ib.terminal.server.service.IbClientMapService;
import com.henyep.ib.terminal.server.util.DateUtil;


@Service(value = "IbClientMapService")
public class IbClientMapServiceImpl implements IbClientMapService {
	private final transient Log logger = LogFactory.getLog(getClass());

	@Resource
	private IbClientMapDao ibClientMapDao;
	
	@Resource
	private ClientDetailsDao clientDetailsDao;
	
	@Resource
	private ClientBalanceSummaryDao clientBalanceSummaryDao; 

	
	@Override
	public List<IbClientMapBean> getByIbCodeDateRange(GetIbClientMapRequest request) {

		try {
			return ibClientMapDao.getByIbCodeDateRange(request.getBody().getIb_code(), request.getBody().getTrade_date());
		} catch (Exception e) {
			logger.error(e, e);
		}

		return null;

	}


	@Override
	public List<IbClientMapBean> getByIbCodeClientCode(GetIbClientMapByIbCodeClientCodeRequest request) throws Exception{
		String ibCode = request.getBody().getIb_code();
		String clientCode = request.getBody().getClient_code();
		return ibClientMapDao.getIbClientMapByIbCodeClientCode(ibCode, clientCode);
	}
	
	
	@Override
	public IbClientMapBean updateIbClientMap(UpdateIbClientMapRequest request, String updatedBy) throws Exception{
		
		IbClientMapBean updatedIbClientMapBean = request.getBody().getIb_client_map();
		updatedIbClientMapBean.setLast_update_user(updatedBy);
		// update ib client map
		ibClientMapDao.updateIbClientMap(updatedIbClientMapBean);
		
		String ibCode = updatedIbClientMapBean.getIb_code();
		String clientCode = updatedIbClientMapBean.getClient_code();
		Date startDate = updatedIbClientMapBean.getStart_date();
		List<IbClientMapBean> beans = ibClientMapDao.getIbClientMapByKey(ibCode, clientCode, startDate);
		
		if(beans != null && beans.size() > 0){
			return beans.get(0);
		}
		else 
			return null;
	}
	
	@Override
	public String validateUpdateIbClientMap(UpdateIbClientMapRequest request) throws Exception{
		
		
		IbClientMapBean updatedIbClientMapBean = request.getBody().getIb_client_map();
		String clientCode = updatedIbClientMapBean.getClient_code();
		
		ArrayList<Integer> clientCodes = new ArrayList<Integer>();
		clientCodes.add(Integer.parseInt(clientCode));
		List<IbClientMapBean> beans = ibClientMapDao.getIbClientMapByClientCodes(clientCodes);
		
		IbClientMapBean originalBean = null;
		for(IbClientMapBean bean : beans){
			
			String beanStartDate = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, bean.getStart_date());
			String updatedBeanStartDate = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, updatedIbClientMapBean.getStart_date());
			
			if(bean.getIb_code().equals(updatedIbClientMapBean.getIb_code()) &&
					bean.getClient_code().equals(updatedIbClientMapBean.getClient_code()) &&
					updatedBeanStartDate.equals(beanStartDate)){
				originalBean = bean;
			}
		}
		
		if(originalBean != null){
			beans.remove(originalBean);
		}
		else{
			return Constants.ERR_IB_CLIENT_MAP_NOT_EXIST;
		}
		
		for(IbClientMapBean dbBean: beans){
			boolean isOverlap = DateUtil.checkDateRangeOverlap(dbBean.getStart_date(), dbBean.getEnd_date(), updatedIbClientMapBean.getStart_date(), updatedIbClientMapBean.getEnd_date());
			if(isOverlap){
				return Constants.ERR_IB_CLIENT_MAP_DATE_RANGE_OVERLAP;	
			}
		}
		
		
		
		return null;
	}
	
	
	
	
	
	// switch ib
	@Override
	public List<IbClientMapBean> swtichIb(SwitchIbClientMapRequest request, String updatedBy) throws Exception{
		
		IbClientMapBean originalIbClientMapBean = request.getBody().getOriginal_ib_client_map();
		String newIbCode = request.getBody().getIb_code();
		String newPackageCode = request.getBody().getPackage_code();
		Date newStartDate = request.getBody().getStart_date();
		Date newEndDate = request.getBody().getEnd_date();
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(newStartDate);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		originalIbClientMapBean.setEnd_date(cal.getTime());
		originalIbClientMapBean.setLast_update_user(updatedBy);
		
		// update the original ib client map bean 
		ibClientMapDao.updateIbClientMap(originalIbClientMapBean);
		
		// insert the new ib client map bean
		IbClientMapBean newIbClientMapBean = new IbClientMapBean();
		newIbClientMapBean.setClient_code(originalIbClientMapBean.getClient_code());
		newIbClientMapBean.setIb_code(newIbCode);
		newIbClientMapBean.setClient_package_code(newPackageCode);
		newIbClientMapBean.setStart_date(newStartDate);
		newIbClientMapBean.setEnd_date(newEndDate);
		newIbClientMapBean.setLast_update_user(updatedBy);
		ibClientMapDao.saveIbClientMap(newIbClientMapBean);
		
		List<IbClientMapBean> beans = ibClientMapDao.getIbClientMapByKey(originalIbClientMapBean.getIb_code(), originalIbClientMapBean.getClient_code(), originalIbClientMapBean.getStart_date());
		beans.addAll(ibClientMapDao.getIbClientMapByKey(newIbClientMapBean.getIb_code(), newIbClientMapBean.getClient_code(), newIbClientMapBean.getStart_date()));
		return beans;
	}
	
	
	@Override
	public String validateSwtichIb(SwitchIbClientMapRequest request) throws Exception{
		
		IbClientMapBean originalIbClientMapBean = request.getBody().getOriginal_ib_client_map();
		
		List<IbClientMapBean> dbBeans = ibClientMapDao.getIbClientMapByKey(originalIbClientMapBean.getIb_code(), originalIbClientMapBean.getClient_code(), originalIbClientMapBean.getStart_date());
		if(dbBeans == null || dbBeans.size() == 0){
			return Constants.ERR_IB_CLIENT_MAP_NOT_EXIST;
		}
		
		ArrayList<Integer> clientCodes = new ArrayList<Integer>();
		clientCodes.add(Integer.parseInt(originalIbClientMapBean.getClient_code()));
		List<IbClientMapBean> beans = ibClientMapDao.getIbClientMapByClientCodes(clientCodes);
		String originalBeanStartDate = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, originalIbClientMapBean.getStart_date());
		
		IbClientMapBean originalBean = null;
		for(IbClientMapBean bean : beans){
			
			String beanStartDate = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, bean.getStart_date());
			
			
			if(bean.getIb_code().equals(originalIbClientMapBean.getIb_code()) &&
					bean.getClient_code().equals(originalIbClientMapBean.getClient_code()) &&
					originalBeanStartDate.equals(beanStartDate)){
				originalBean = bean;
			}
		}
		
		if(originalBean != null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(request.getBody().getStart_date());
			cal.add(Calendar.DAY_OF_MONTH, -1);
			originalBean.setEnd_date(cal.getTime());
			String originalBeanEndDate = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, originalBean.getEnd_date());
			
			if(!originalBean.getStart_date().before(originalBean.getEnd_date()) && !originalBeanStartDate.equals(originalBeanEndDate)){
				return Constants.ERR_IB_CLIENT_MAP_NEW_START_DATE_BEFORE_ORIGINAL_START_DATE;
			}
		}
		else{
			return Constants.ERR_IB_CLIENT_MAP_NOT_EXIST;
		}
		
		for(IbClientMapBean dbBean: beans){
			boolean isOverlap = DateUtil.checkDateRangeOverlap(dbBean.getStart_date(), dbBean.getEnd_date(), request.getBody().getStart_date(), request.getBody().getEnd_date());
			if(isOverlap){
				return Constants.ERR_IB_CLIENT_MAP_DATE_RANGE_OVERLAP;	
			}
		}
		
		return null;
		
	}
	
	

	// insert ib client map
	@Override
	public List<IbClientMapBean> insert(InsertIbClientMapRequest request, String createdBy) throws Exception{
		
		IbClientMapBean newIbClientMapBean = request.getBody().getIb_client_map();
		newIbClientMapBean.setLast_update_user(createdBy);
		ibClientMapDao.saveIbClientMap(newIbClientMapBean);
		
		List<IbClientMapBean> beans = ibClientMapDao.getIbClientMapByKey(newIbClientMapBean.getIb_code(), newIbClientMapBean.getClient_code(), newIbClientMapBean.getStart_date());
		return beans;
	}

	@Override
	public String validateInsert(InsertIbClientMapRequest request) throws Exception{
		
		IbClientMapBean newIbClientMapBean = request.getBody().getIb_client_map();
		
		List<IbClientMapBean> dbBeans = ibClientMapDao.getIbClientMapByKey(newIbClientMapBean.getIb_code(), newIbClientMapBean.getClient_code(), newIbClientMapBean.getStart_date());
		if(dbBeans != null && dbBeans.size() > 0){
			return Constants.ERR_IB_CLIENT_MAP_ALREADY_EXIST;
		}
		
		ArrayList<Integer> clientCodes = new ArrayList<Integer>();
		clientCodes.add(Integer.parseInt( newIbClientMapBean.getClient_code()));
		List<IbClientMapBean> beans = ibClientMapDao.getIbClientMapByClientCodes(clientCodes);
		
		
		for(IbClientMapBean dbBean: beans){
			boolean isOverlap = DateUtil.checkDateRangeOverlap(dbBean.getStart_date(), dbBean.getEnd_date(), request.getBody().getIb_client_map().getStart_date(), request.getBody().getIb_client_map().getEnd_date());
			if(isOverlap){
				return Constants.ERR_IB_CLIENT_MAP_DATE_RANGE_OVERLAP;	
			}
		}
		
		return null;
		
	}
	
	
	public String validateInsertFromWlRegistration(InsertFromWlRegistrationRequest request) throws Exception{
		
		List<Integer> clientCodes = new ArrayList<Integer>();
		String clientCode = request.getBody().getClient_code();
		clientCodes.add(Integer.parseInt(clientCode));
		List<IbClientMapBean> dbIbClientMapBeans = ibClientMapDao.getIbClientMapByClientCodes(clientCodes);
		if(dbIbClientMapBeans.size() > 0){
			return Constants.ERR_IB_CLIENT_MAP_ALREADY_EXIST;
		}
		
		List<ClientDetailsBean> clientDetailsBeans = clientDetailsDao.getClientDetailsByKey(clientCode);
		if(clientDetailsBeans.size() > 0){
			return Constants.ERR_CLIENT_DETAIS_ALREADY_EXIST;
		}		
		List<ClientBalanceSummaryBean> clientBalanceSummaryBeans = clientBalanceSummaryDao.getClientBalanceSummaryByKey(clientCode, new Date());
		if(clientBalanceSummaryBeans.size() > 0){
			return Constants.ERR_CLIENT_BALANCE_SUMMARY_ALREADY_EXIST;
		}
		
		return null;
		
	}
	
	
	public InsertFromWlRegistrationResponseDto insertFromWlRegistration(InsertFromWlRegistrationRequest request, String createdBy) throws Exception{
		
		InsertFromWlRegistrationResponseDto dto = new InsertFromWlRegistrationResponseDto();
		
		String clientCode = request.getBody().getClient_code();
		String ibCode = request.getBody().getIb_code();
		
		// prepare ib client map bean
		IbClientMapBean ibClientMap = new IbClientMapBean();
		ibClientMap.setClient_code(clientCode);
		ibClientMap.setIb_code(ibCode);
		ibClientMap.setClient_package_code(request.getBody().getClient_package());
		ibClientMap.setStart_date(DateUtil.getCurrentDate());
		ibClientMap.setEnd_date(null);
		ibClientMap.setLast_update_user(createdBy);
		// save ib client map bean		
		ibClientMapDao.saveIbClientMap(ibClientMap);

		// prepare ib client details
		ClientDetailsBean clientDetails = new ClientDetailsBean();
		clientDetails.setAccount_balance(0.0);
		clientDetails.setChinese_name("");
		clientDetails.setClient_trading_id(clientCode);
		clientDetails.setCurrency("USD");
		clientDetails.setEmail(request.getBody().getEmail());
		clientDetails.setFirst_name(request.getBody().getFirst_name());
		clientDetails.setLast_name(request.getBody().getLast_name());
		clientDetails.setLast_update_user(createdBy);
		clientDetails.setMobile(request.getBody().getPhone());
		clientDetails.setRegister_date(new Date());
		clientDetails.setSex(request.getBody().getGender());
		clientDetails.setTrading_platform(Constants.PLATFORM_MT4_STRING);
		// save client details
		clientDetailsDao.saveClientDetails(clientDetails);
		
		// prepare client account balance
		ClientBalanceSummaryBean clientBalanceSummary = new ClientBalanceSummaryBean();
		clientBalanceSummary.setBalance(0.0);
		clientBalanceSummary.setClient_code(clientCode);
		clientBalanceSummary.setCommission(0.0);
		clientBalanceSummary.setCredit(0.0);
		clientBalanceSummary.setDeposit(0.0);
		clientBalanceSummary.setEquity(0.0);
		clientBalanceSummary.setFloating(0.0);
		clientBalanceSummary.setLast_update_date(new Date());
		clientBalanceSummary.setLast_update_user(createdBy);
		clientBalanceSummary.setPl(0.0);
		clientBalanceSummary.setPl_adjustment(0.0);
		clientBalanceSummary.setPrevious_balance(0.0);
		clientBalanceSummary.setPrevious_credit(0.0);
		clientBalanceSummary.setPrevious_equity(0.0);
		clientBalanceSummary.setPrevious_floating(0.0);
		clientBalanceSummary.setSwap(0.0);
		clientBalanceSummary.setTax(0.0);
		clientBalanceSummary.setTrade_date(new Date());
		clientBalanceSummary.setWithdrawal(0.0);
		clientBalanceSummary.setCurrency("USD");
		// save client balance summary
		clientBalanceSummaryDao.saveClientBalanceSummary(clientBalanceSummary);
		
		// get beans form db server
		List<IbClientMapBean> ibClientMaps = ibClientMapDao.getIbClientMapByIbCodeClientCode(ibCode, clientCode);
		if(ibClientMaps.size() > 0){
			dto.setIb_client_map(ibClientMaps.get(0));
		}
		List<ClientDetailsBean> clientDetailsBeans = clientDetailsDao.getClientDetailsByKey(clientCode);
		if(clientDetailsBeans.size() > 0){
			dto.setClient_details(clientDetailsBeans.get(0));
		}		
		List<ClientBalanceSummaryBean> clientBalanceSummaryBeans = clientBalanceSummaryDao.getClientBalanceSummaryByKey(clientCode, clientBalanceSummary.getTrade_date());
		if(clientBalanceSummaryBeans.size() > 0){
			dto.setClient_balance_summary(clientBalanceSummaryBeans.get(0));
		}
		return dto;
	}


	@Override
	public Integer getClientCountByIbCodeDateRange(GetIbClientMapRequest request) {
		return this.ibClientMapDao.getClientCountByIbCodeDateRange(request.getBody().getIb_code(), request.getBody().getTrade_date());
	}
	
	

}
