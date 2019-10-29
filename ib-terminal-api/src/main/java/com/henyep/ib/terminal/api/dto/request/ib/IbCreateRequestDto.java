package com.henyep.ib.terminal.api.dto.request.ib;

import java.io.Serializable;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.henyep.ib.terminal.api.dto.db.IbProfileBean;
import com.henyep.ib.terminal.api.global.Constants;

public class IbCreateRequestDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 336990398068126713L;

	@NotNull(message = Constants.ERR_COMMON_CURRENCY_NULL)
	private String currency;
	@Valid
	private IbProfileBean ib_profile;

	@NotNull(message = Constants.ERR_IB_TREE_PARENT_CODE_IS_NULL)
	private String ib_parent_code;	

	public Boolean getIgnore_tree_rebate() {
		return ignore_tree_rebate;
	}

	public void setIgnore_tree_rebate(Boolean ignore_tree_rebate) {
		this.ignore_tree_rebate = ignore_tree_rebate;
	}

	@NotNull(message = Constants.ERR_COMMON_IGNORE_TREE_REBATE_IS_BLANK)
	private Boolean ignore_tree_rebate;

	public IbProfileBean getIb_profile() {
		return ib_profile;
	}

	public void setIb_profile(IbProfileBean ib_profile) {
		this.ib_profile = ib_profile;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getIb_parent_code() {
		return ib_parent_code;
	}

	public void setIb_parent_code(String ib_parent_code) {
		this.ib_parent_code = ib_parent_code;
	}

}
