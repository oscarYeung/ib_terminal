package com.henyep.ib.terminal.api.dto.response.marginin;

import java.io.Serializable;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.MarginInBean;

public class GetMarginInResponseDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1380472148127717536L;

	
	private List<MarginInBean> marginInBeans;


	public List<MarginInBean> getMarginInBeans() {
		return marginInBeans;
	}


	public void setMarginInBeans(List<MarginInBean> marginInBeans) {
		this.marginInBeans = marginInBeans;
	}
	
}
