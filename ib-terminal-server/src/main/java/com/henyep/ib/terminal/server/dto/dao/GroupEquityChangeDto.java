package com.henyep.ib.terminal.server.dto.dao;

public class GroupEquityChangeDto extends ClientEquityChangeDto {

	protected String group_code;
	protected String brand_code;

	
	public String getBrand_code() {
		return brand_code;
	}

	public void setBrand_code(String brand_code) {
		this.brand_code = brand_code;
	}

	public String getGroup_code() {
		return group_code;
	}

	public void setGroup_code(String group_code) {
		this.group_code = group_code;
	}

}
