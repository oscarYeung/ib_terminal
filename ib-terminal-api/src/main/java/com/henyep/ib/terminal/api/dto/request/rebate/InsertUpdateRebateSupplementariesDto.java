package com.henyep.ib.terminal.api.dto.request.rebate;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.henyep.ib.terminal.api.dto.db.RebateSupplementaryBean;
import com.henyep.ib.terminal.api.global.Constants;

public class InsertUpdateRebateSupplementariesDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5548661145249062558L;

	@NotNull(message = Constants.ERR_COMMON_IB_CODE_IS_BLANK)
	private String ib_code;
	
	@Valid
	private List<RebateSupplementaryBean> rebateSupplementaries;	

	public List<RebateSupplementaryBean> getRebateSupplementaries() {
		return rebateSupplementaries;
	}

	public void setRebateSupplementaries(List<RebateSupplementaryBean> rebateSupplementaries) {
		this.rebateSupplementaries = rebateSupplementaries;
	}

	public String getIb_code() {
		return ib_code;
	}

	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}	
}
