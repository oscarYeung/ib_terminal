package com.henyep.ib.terminal.api.dto.response.marginin;

import java.io.Serializable;

import com.henyep.ib.terminal.api.dto.db.MarginInBean;

public class InsertMarginInResponseDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1325827697982693035L;
	private MarginInBean marginInBean;


	public MarginInBean getMarginInBean() {
		return marginInBean;
	}

	public void setMarginInBean(MarginInBean marginInBean) {
		this.marginInBean = marginInBean;
	}
	
	
	
}
