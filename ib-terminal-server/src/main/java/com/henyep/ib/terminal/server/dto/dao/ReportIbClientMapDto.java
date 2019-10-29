package com.henyep.ib.terminal.server.dto.dao;

import com.henyep.ib.terminal.api.dto.db.IbClientMapBean;

public class ReportIbClientMapDto extends IbClientMapBean {
	private String brand_code;

	public String getBrand_code() {
		return brand_code;
	}

	public void setBrand_code(String brand_code) {
		this.brand_code = brand_code;
	}

}
