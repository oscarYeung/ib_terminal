package com.henyep.ib.terminal.api.dto.response.marginout;

import java.io.Serializable;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.MarginOutBean;

public class GetMarginOutResponseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7066971875968444227L;

	
	private List<MarginOutBean> marginOutBeans;


	public List<MarginOutBean> getMarginOutBeans() {
		return marginOutBeans;
	}


	public void setMarginOutBeans(List<MarginOutBean> marginOutBeans) {
		this.marginOutBeans = marginOutBeans;
	}
	
	
}
