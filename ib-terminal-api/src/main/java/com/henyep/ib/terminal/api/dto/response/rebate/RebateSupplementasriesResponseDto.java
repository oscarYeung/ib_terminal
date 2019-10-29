package com.henyep.ib.terminal.api.dto.response.rebate;

import java.io.Serializable;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.RebateSupplementaryBean;

public class RebateSupplementasriesResponseDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9138100683536644531L;
	
	private String ib_code;
	private List<RebateSupplementaryBean> rebateSupplementaryList;
	
	public String getIb_code() {
		return ib_code;
	}
	
	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}

	public List<RebateSupplementaryBean> getRebateSupplementaryList() {
		return rebateSupplementaryList;
	}

	public void setRebateSupplementaryList(List<RebateSupplementaryBean> rebateSupplementaryList) {
		this.rebateSupplementaryList = rebateSupplementaryList;
	}
}
