package com.henyep.ib.terminal.api.dto.request.ib.lead;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.henyep.ib.terminal.api.global.Constants;

public class GetIbLeadsRequestDto implements Serializable {

	private static final long serialVersionUID = -7059126672088835350L;

	@NotNull(message = Constants.ERR_COMMON_IB_CODE_IS_BLANK)
	private String ib_code;
	@NotNull(message = Constants.ERR_IB_LEAD_WITH_SUB_IB_LEAD_IS_NULL)
	private Boolean withSubIbLeads;
	
	private String email;
	private String name;
	private String phone;

	public String getIb_code() {
		return ib_code;
	}

	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}

	public Boolean getWithSubIbLeads() {
		return withSubIbLeads;
	}

	public void setWithSubIbLeads(Boolean withSubIbLeads) {
		this.withSubIbLeads = withSubIbLeads;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	

}
