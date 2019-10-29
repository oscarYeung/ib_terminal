package com.henyep.ib.terminal.server.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.IbTreeBean;

public class IbTreeUtil {

	public List<IbTreeBean> getByIbCode(List<IbTreeBean> ibTreeBeans, String ibCode, Date tradeDate){
		
		IbTreeBean currentIb = null;
		for(IbTreeBean ib : ibTreeBeans){
			if(ib.getIb_code().equals(ibCode)){
				if((ib.getEnd_date() == null && ib.getStart_date().compareTo(tradeDate) <= 0) ||
						(ib.getEnd_date() != null && ib.getStart_date().compareTo(tradeDate) <= 0 && ib.getEnd_date().compareTo(tradeDate) >= 0)){
					currentIb = ib;
					break;
				}				
			}
		}
		List<IbTreeBean> parentIbs = new ArrayList<IbTreeBean>();
		
		if(currentIb != null){
			parentIbs.add(currentIb);
			for(IbTreeBean ib : ibTreeBeans){
				if(ib.getMin_id() < currentIb.getMin_id() && ib.getMax_id() > currentIb.getMax_id()){
					if(ib.isIs_ib()){
						parentIbs.add(ib);
					}
				}
			}
		}
	
		Collections.sort(parentIbs, new Comparator<IbTreeBean>(){
			@Override
			public int compare(IbTreeBean o1, IbTreeBean o2) {
				if(o1.getMin_id() < o2.getMin_id()){
					return 1;
				}
				else
					return -1;
			}
		});
		
//		// remove the ib head (CN or TMP)
//		if(parentIbs.size() >= 1){
//			parentIbs.remove(parentIbs.size() - 1);
//		}
		return parentIbs;
	}
	
	
	
	
	public List<IbTreeBean> getIbChilds(List<IbTreeBean> allIbTreeBeans, String ibCode, Date tradeDate){
		
		IbTreeBean currentIb = null;
		for(IbTreeBean ib : allIbTreeBeans){
			if(ib.getIb_code().equals(ibCode)){
				if((ib.getEnd_date() == null && ib.getStart_date().compareTo(tradeDate) <= 0) ||
						(ib.getEnd_date() != null && ib.getStart_date().compareTo(tradeDate) <= 0 && ib.getEnd_date().compareTo(tradeDate) >= 0)){
					currentIb = ib;
					break;
				}				
			}
		}
		List<IbTreeBean> childIbs = new ArrayList<IbTreeBean>();
		
		if(currentIb != null){
			childIbs.add(currentIb);
			for(IbTreeBean ib : allIbTreeBeans){
				if(currentIb.getMin_id() < ib.getMin_id() && currentIb.getMax_id() > ib.getMax_id()){
					if(ib.getMin_id() != 1){
						childIbs.add(ib);
					}
				}
			}
		}
	
		Collections.sort(childIbs, new Comparator<IbTreeBean>(){
			@Override
			public int compare(IbTreeBean o1, IbTreeBean o2) {
				if(o1.getMin_id() < o2.getMin_id()){
					return 1;
				}
				else
					return -1;
			}
		});
		return childIbs;
	}
}
