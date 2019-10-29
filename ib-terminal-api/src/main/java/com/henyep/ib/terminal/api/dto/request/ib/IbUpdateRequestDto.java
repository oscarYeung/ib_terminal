package com.henyep.ib.terminal.api.dto.request.ib;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.henyep.ib.terminal.api.dto.db.IbProfileBean;
import com.henyep.ib.terminal.api.global.Constants;

public class IbUpdateRequestDto implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2477065251648414959L;
	
	@Valid
	private IbProfileBean ib_profile;

	@NotNull(message = Constants.ERR_COMMON_IGNORE_TREE_REBATE_IS_BLANK )
	private Boolean ignore_tree_rebate;
	
	public IbProfileBean getIb_profile() {
		return ib_profile;
	}

	public Boolean getIgnore_tree_rebate() {
		return ignore_tree_rebate;
	}

	public void setIgnore_tree_rebate(Boolean ignore_tree_rebate) {
		this.ignore_tree_rebate = ignore_tree_rebate;
	}

	public void setIb_profile(IbProfileBean ib_profile) {
		this.ib_profile = ib_profile;
	} 
}
