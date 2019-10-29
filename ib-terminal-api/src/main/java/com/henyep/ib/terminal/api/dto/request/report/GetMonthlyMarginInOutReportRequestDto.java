package com.henyep.ib.terminal.api.dto.request.report;


import javax.validation.constraints.NotNull;

import com.henyep.ib.terminal.api.dto.request.BaseDateTimeRequestBodyDto;
import com.henyep.ib.terminal.api.global.Constants;

public class GetMonthlyMarginInOutReportRequestDto  extends BaseDateTimeRequestBodyDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7468844792324885014L;

	@NotNull(message = Constants.ERR_COMMON_BRAND_CODE_IS_BLANK)
	private String brand_code;
	
	private String ib_codes;
	
	public String getBrand_code() {
		return brand_code;
	}
	public void setBrand_code(String brand_code) {
		this.brand_code = brand_code;
	}
	public String getIb_codes() {
		return ib_codes;
	}
	public void setIb_codes(String ib_codes) {
		this.ib_codes = ib_codes;
	}
	
}
