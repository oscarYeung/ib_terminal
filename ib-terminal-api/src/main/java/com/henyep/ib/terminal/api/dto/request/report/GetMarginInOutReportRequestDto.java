package com.henyep.ib.terminal.api.dto.request.report;

import com.henyep.ib.terminal.api.dto.request.BaseDateTimeRequestBodyDto;

public class GetMarginInOutReportRequestDto extends BaseDateTimeRequestBodyDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4229488530544210199L;
	
	private String brand_code;
	private String ib_code;
	
	private boolean has_ib_margin_in_out;
	private boolean has_client_margin_in_out;
	
	
	public String getBrand_code() {
		return brand_code;
	}
	public void setBrand_code(String brand_code) {
		this.brand_code = brand_code;
	}
	public String getIb_code() {
		return ib_code;
	}
	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}
	public boolean isHas_ib_margin_in_out() {
		return has_ib_margin_in_out;
	}
	public void setHas_ib_margin_in_out(boolean has_ib_margin_in_out) {
		this.has_ib_margin_in_out = has_ib_margin_in_out;
	}
	public boolean isHas_client_margin_in_out() {
		return has_client_margin_in_out;
	}
	public void setHas_client_margin_in_out(boolean has_client_margin_in_out) {
		this.has_client_margin_in_out = has_client_margin_in_out;
	}
}
