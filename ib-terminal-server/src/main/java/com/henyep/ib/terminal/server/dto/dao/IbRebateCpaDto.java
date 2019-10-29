package com.henyep.ib.terminal.server.dto.dao;

import com.henyep.ib.terminal.api.dto.db.RebateCpaBean;

public class IbRebateCpaDto extends RebateCpaBean {

	private String brand_code;
	private String ib_code;

	public String getIb_code() {
		return ib_code;
	}

	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}

	public String getBrand_code() {
		return brand_code;
	}

	public void setBrand_code(String brand_code) {
		this.brand_code = brand_code;
	}

}
