package com.henyep.ib.terminal.server.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.IbClientRebateMapBean;

public class ClientRebateMapUtil {

	private List<IbClientRebateMapBean> ibClientRebateMaps;
	private HashMap<String, List<IbClientRebateMapBean>> ibClientRebateMapsHashMap;
	private String ANY_CLIENT_CODE = "*";
	
	public ClientRebateMapUtil(List<IbClientRebateMapBean> ibClientRebateMaps){
		this.ibClientRebateMaps = ibClientRebateMaps;
		init();
	}
	
	private void init(){
		ibClientRebateMapsHashMap = new HashMap<String, List<IbClientRebateMapBean>>();
		for(IbClientRebateMapBean mapBean : ibClientRebateMaps){
			List<IbClientRebateMapBean> valList;
			if(!ibClientRebateMapsHashMap.containsKey(mapBean.getIb_code())){
				ibClientRebateMapsHashMap.put(mapBean.getIb_code(), new ArrayList<IbClientRebateMapBean>());
			}
			valList = ibClientRebateMapsHashMap.get(mapBean.getIb_code());
			valList.add(mapBean);
		}
	}
	
	public String getClientRebateCode(String cltCode, String ibCode, Date tradeDate){
		
		if(ibClientRebateMapsHashMap.containsKey(ibCode)){
			List<IbClientRebateMapBean> mapBeans = ibClientRebateMapsHashMap.get(ibCode);
			List<IbClientRebateMapBean> anyCltCodeMapBeans = new ArrayList<IbClientRebateMapBean>();
			for(IbClientRebateMapBean mapBean : mapBeans){
				if(mapBean.getClient_code().equals(ANY_CLIENT_CODE)){
					anyCltCodeMapBeans.add(mapBean);
				}
				// exactly match find
				if(mapBean.getClient_code().equals(cltCode)){
					if((mapBean.getEnd_date() == null && mapBean.getStart_date().compareTo(tradeDate) <= 0) ||
						(mapBean.getEnd_date() != null && mapBean.getStart_date().compareTo(tradeDate) <= 0 && mapBean.getEnd_date().compareTo(tradeDate) >= 0)){
						return mapBean.getRebate_code();
					}
				}
			}
			
			// any cltcode map bean find
			if(anyCltCodeMapBeans.size() > 0){
				for(IbClientRebateMapBean anyCltCodeMapBean : anyCltCodeMapBeans){
					if((anyCltCodeMapBean.getEnd_date() == null && anyCltCodeMapBean.getStart_date().compareTo(tradeDate) <= 0) ||
							(anyCltCodeMapBean.getEnd_date() != null && anyCltCodeMapBean.getStart_date().compareTo(tradeDate) <= 0 && anyCltCodeMapBean.getEnd_date().compareTo(tradeDate) >= 0)){
						return anyCltCodeMapBean.getRebate_code();
					}
				}
			}
			
		}	
		return null;
	}
}
