package com.henyep.ib.terminal.api.dto.request.report;

import com.henyep.ib.terminal.api.dto.request.BaseDateTimeRequestBodyDto;

public class GetMarginInReportRequestDto extends BaseDateTimeRequestBodyDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2766718328953220089L;
	
	

	private String brand_code;
	private String status;
	private String account;
	private String category;
	
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getBrand_code() {
		return brand_code;
	}
	public void setBrand_code(String brand_code) {
		this.brand_code = brand_code;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
		

}
