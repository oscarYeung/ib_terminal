package com.henyep.ib.terminal.api.dto.response.ib.login;

import java.util.List;

import com.henyep.ib.terminal.api.dto.response.user.BaseAccountInfo;

public class IbLoginResponseDto {
	private String ibLoginName;
	private String ibStatus;
	private String ibCreateDate;
	private String ibCode;
	private List<BaseAccountInfo> accountInformations;
	private String brandCode;
	private boolean is_white_label_user;
	
	private String wl_company_code;
	private String wl_server_code;
	private String wl_registration_type;
	private String wl_platform;
	

	public String getIbLoginName() {
		return ibLoginName;
	}

	public void setIbLoginName(String ibLoginName) {
		this.ibLoginName = ibLoginName;
	}

	public String getIbStatus() {
		return ibStatus;
	}

	public void setIbStatus(String ibStatus) {
		this.ibStatus = ibStatus;
	}

	public String getIbCreateDate() {
		return ibCreateDate;
	}

	public void setIbCreateDate(String ibCreateDate) {
		this.ibCreateDate = ibCreateDate;
	}

	public String getIbCode() {
		return ibCode;
	}

	public void setIbCode(String ibCode) {
		this.ibCode = ibCode;
	}

	public List<BaseAccountInfo> getAccountInformations() {
		return accountInformations;
	}

	public void setAccountInformations(List<BaseAccountInfo> accountInformations) {
		this.accountInformations = accountInformations;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getWl_company_code() {
		return wl_company_code;
	}

	public void setWl_company_code(String wl_company_code) {
		this.wl_company_code = wl_company_code;
	}

	public String getWl_server_code() {
		return wl_server_code;
	}

	public void setWl_server_code(String wl_server_code) {
		this.wl_server_code = wl_server_code;
	}

	public String getWl_registration_type() {
		return wl_registration_type;
	}

	public void setWl_registration_type(String wl_registration_type) {
		this.wl_registration_type = wl_registration_type;
	}

	public String getWl_platform() {
		return wl_platform;
	}

	public void setWl_platform(String wl_platform) {
		this.wl_platform = wl_platform;
	}

	public boolean getIs_white_label_user() {
		return is_white_label_user;
	}

	public void setIs_white_label_user(boolean is_white_label_user) {
		this.is_white_label_user = is_white_label_user;
	}
	
	

}
