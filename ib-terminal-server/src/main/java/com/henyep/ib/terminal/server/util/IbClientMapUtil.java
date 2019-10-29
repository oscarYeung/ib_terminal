package com.henyep.ib.terminal.server.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.IbClientMapBean;

public class IbClientMapUtil {

	private HashMap<String, List<IbClientMapBean>> cltCodeIbHashMap;
	private HashMap<String, List<IbClientMapBean>> ibCltCodeHashMap;
	
	private List<IbClientMapBean> ibClientMapBeans;
	
	public IbClientMapUtil(List<IbClientMapBean> ibClientMapBeans){
		this.ibClientMapBeans = ibClientMapBeans;
		Init();
	}
	
	
	private void Init(){
		InitCltCodeIbHashMap();
		InitIbCltCodeHashMap();
	}
	
	// client code as key
	private void InitCltCodeIbHashMap(){
		cltCodeIbHashMap = new HashMap<String, List<IbClientMapBean>>();
		
		for(IbClientMapBean ibClientMapBean : ibClientMapBeans){
			String key = ibClientMapBean.getClient_code();
			
			if(!cltCodeIbHashMap.containsKey(key)){
				cltCodeIbHashMap.put(key, new ArrayList<IbClientMapBean>());
			}
			
			List<IbClientMapBean> ibClientMapBeanList = cltCodeIbHashMap.get(key);
			ibClientMapBeanList.add(ibClientMapBean);
		}
	}
	
	// ib code as key
	private void InitIbCltCodeHashMap(){
		ibCltCodeHashMap = new HashMap<String, List<IbClientMapBean>>();
		
		for(IbClientMapBean ibClientMapBean : ibClientMapBeans){
			String key = ibClientMapBean.getIb_code();
			
			if(!ibCltCodeHashMap.containsKey(key)){
				ibCltCodeHashMap.put(key, new ArrayList<IbClientMapBean>());
			}
			
			List<IbClientMapBean> ibClientMapBeanList = ibCltCodeHashMap.get(key);
			ibClientMapBeanList.add(ibClientMapBean);
		}
	}
	
	
	public IbClientMapBean getIbClientMapByCltCode(String cltCode, Date tradeDate){
		if(cltCodeIbHashMap.containsKey(cltCode)){
			List<IbClientMapBean> ibClientMapBeanList = cltCodeIbHashMap.get(cltCode);
			for(IbClientMapBean ibClientMapBean : ibClientMapBeanList){
				if(ibClientMapBean.getEnd_date() == null){
					if(ibClientMapBean.getStart_date().before(tradeDate) || ibClientMapBean.getStart_date().equals(tradeDate))
						return ibClientMapBean;
				}
				else{
					if((ibClientMapBean.getStart_date().before(tradeDate) || ibClientMapBean.getStart_date().equals(tradeDate)) && 
							(ibClientMapBean.getEnd_date().after(tradeDate) || ibClientMapBean.getEnd_date().equals(tradeDate)))
						return ibClientMapBean;
				}
					
			}
		}
		return null;
		
	}
	
	public List<IbClientMapBean> getIbClientMapByIbCode(String ibCode, Date startDate, Date endDate){
		List<IbClientMapBean> rtnList = new ArrayList<IbClientMapBean>();
		
		if(ibCltCodeHashMap.containsKey(ibCode)){
			List<IbClientMapBean> ibClientMapBeanList = ibCltCodeHashMap.get(ibCode);
			for(IbClientMapBean ibClientMapBean : ibClientMapBeanList){
				if(ibClientMapBean.getEnd_date() == null){
					if(ibClientMapBean.getStart_date().before(endDate))
						rtnList.add(ibClientMapBean);
				}
				else{
					if(!(ibClientMapBean.getEnd_date().before(startDate) || endDate.before(ibClientMapBean.getStart_date()))){
						rtnList.add(ibClientMapBean);
					}
				}
					
			}
		}
		return rtnList;
	}

}
