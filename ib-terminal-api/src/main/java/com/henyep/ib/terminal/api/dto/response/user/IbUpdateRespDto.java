package com.henyep.ib.terminal.api.dto.response.user;

import java.io.Serializable;

import com.henyep.ib.terminal.api.dto.db.IbProfileBean;

public class IbUpdateRespDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4813606187844950406L;
	
	
	private IbProfileBean ibProfilebean;
	private Boolean ignore_tree_rebate;


	public Boolean getIgnore_tree_rebate() {
		return ignore_tree_rebate;
	}

	public void setIgnore_tree_rebate(Boolean ignore_tree_rebate) {
		this.ignore_tree_rebate = ignore_tree_rebate;
	}

	public IbProfileBean getIbProfilebean() {
		return ibProfilebean;
	}

	public void setIbProfilebean(IbProfileBean ibProfilebean) {
		this.ibProfilebean = ibProfilebean;
	}

}
