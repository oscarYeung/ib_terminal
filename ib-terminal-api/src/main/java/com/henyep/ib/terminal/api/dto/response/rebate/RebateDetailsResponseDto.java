package com.henyep.ib.terminal.api.dto.response.rebate;

import java.io.Serializable;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.RebateDetailsBean;

public class RebateDetailsResponseDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7297846215378443536L;
	
	private String ib_code;
	
	public String getIb_code() {
		return ib_code;
	}

	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}

	public List<RebateDetailsBean> getRebateDetailsList() {
		return rebateDetailsList;
	}

	public void setRebateDetailsList(List<RebateDetailsBean> rebateDetailsList) {
		this.rebateDetailsList = rebateDetailsList;
	}

	private List<RebateDetailsBean> rebateDetailsList;
}
