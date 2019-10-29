package com.henyep.ib.terminal.server.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.RebateBean;
import com.henyep.ib.terminal.api.dto.db.RebateDetailsBean;

public class RebateDetailUtil {
	
	private final String KEY_SEPERATOR = "@@";

	private HashMap<String, List<RebateDetailsBean>> RebateDetailsMap;
	private HashMap<String, Boolean> IgnoreIbTreeRebateMap;
	
	private HashMap<String, List<String>> AvailableProductGroupMap;
	
	public RebateDetailUtil(List<RebateDetailsBean> rebateDetailsBeans, List<RebateBean> rebateBeans, Date tradeDate){
		
		initRebateDetailsMap(rebateDetailsBeans, tradeDate);
		
		initIgnoreIbTreeRebateMap(rebateBeans);
	}
	
	private void initRebateDetailsMap(List<RebateDetailsBean> rebateDetailsBeans, Date tradeDate){
		RebateDetailsMap = new HashMap<String, List<RebateDetailsBean>>();
		AvailableProductGroupMap = new HashMap<String, List<String>>();
		
		for(RebateDetailsBean packageDetail : rebateDetailsBeans){
			String productGroup = packageDetail.getProduct_group();
			String clientPackageCode = packageDetail.getClient_package_code();
			String rebateCode = packageDetail.getRebate_code();
			String spreadType = packageDetail.getSpread_type();
			Date startDate = packageDetail.getStart_date();
			Date endDate = packageDetail.getEnd_date();
			
			String key = getRebateDetailsMapKey(rebateCode, clientPackageCode, productGroup, spreadType);
			
			if(!RebateDetailsMap.containsKey(key)){
				RebateDetailsMap.put(key, new ArrayList<RebateDetailsBean>());
			}
			RebateDetailsMap.get(key).add(packageDetail);
			
			boolean isValidDate = false;
			if(endDate != null){
				if(startDate.compareTo(tradeDate) <= 0 && endDate.compareTo(tradeDate) >= 0)
					isValidDate = true;
			}
			else{
				if(startDate.compareTo(tradeDate) <= 0){
					isValidDate = true;
				}
			}
			if(isValidDate){
						
				String availableProductGroupKey = getAvailableProductGroupKey(rebateCode, spreadType);
				if(!AvailableProductGroupMap.containsKey(availableProductGroupKey)){
					AvailableProductGroupMap.put(availableProductGroupKey, new ArrayList<String>());
				}
				
				if(!AvailableProductGroupMap.get(availableProductGroupKey).contains(productGroup)){
					AvailableProductGroupMap.get(availableProductGroupKey).add(productGroup);
				}
			}
		
			
		}
	}
	
	private void initIgnoreIbTreeRebateMap(List<RebateBean> rebateBeans){
		IgnoreIbTreeRebateMap = new HashMap<String, Boolean>();
		
		for(RebateBean rebateBean : rebateBeans){
			String rebateCode = rebateBean.getRebate_code();
			Boolean isIgnoreRebateMap = rebateBean.getIgnore_tree_rebate();
			if(!IgnoreIbTreeRebateMap.containsKey(rebateCode)){
				IgnoreIbTreeRebateMap.put(rebateCode, isIgnoreRebateMap);
			}
		}
	}
	
	public List<String> getAvailableProductGroups(String rebateCode, String spreadType){
		String availableProductGroupKey = getAvailableProductGroupKey(rebateCode, spreadType);
		
		if(AvailableProductGroupMap.containsKey(availableProductGroupKey)){
			return AvailableProductGroupMap.get(availableProductGroupKey);
		}
		else{
			return new ArrayList<String>();
		}
		
	}
	
	public Boolean getIsIgoureIbTreeRebate(String rebateCode){
		if(IgnoreIbTreeRebateMap.containsKey(rebateCode)){
			return IgnoreIbTreeRebateMap.get(rebateCode);
		}
		else{
			return false;
		}
			
	}

	public RebateDetailsBean getRebateDetails(String rebateCode, String spreadType, String clientPackageCode, String productGroup, Date tradeDate){
		String key = getRebateDetailsMapKey(rebateCode, clientPackageCode, productGroup, spreadType);
		if(RebateDetailsMap.containsKey(key)){
			List<RebateDetailsBean> beans = RebateDetailsMap.get(key);
			for(RebateDetailsBean bean : beans){
				if(bean.getEnd_date() != null){
					if(bean.getStart_date().compareTo(tradeDate) <= 0 && bean.getEnd_date().compareTo(tradeDate) >= 0)
						return bean;
				}
				else{
					if(bean.getStart_date().compareTo(tradeDate) <= 0){
						return bean;
					}
				}
			}
		}
		return null;
	}
	
	private String getRebateDetailsMapKey(String packageCode, String clientPackageCode, String productGroup, String spreadType){
		return packageCode + KEY_SEPERATOR + clientPackageCode + KEY_SEPERATOR + productGroup + KEY_SEPERATOR + spreadType; 
	}
	
	private String getAvailableProductGroupKey(String rebateCode, String spreadType){
		return rebateCode + KEY_SEPERATOR + spreadType; 
	}
}
