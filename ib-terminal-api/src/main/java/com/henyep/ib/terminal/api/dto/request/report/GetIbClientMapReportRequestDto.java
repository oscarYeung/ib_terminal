package com.henyep.ib.terminal.api.dto.request.report;

import com.henyep.ib.terminal.api.dto.request.BaseDateTimeRequestBodyDto;

public class GetIbClientMapReportRequestDto extends BaseDateTimeRequestBodyDto {

	private static final long serialVersionUID = 6568700008548669228L;
	private String brand_code;
	private String ib_code;

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

}
