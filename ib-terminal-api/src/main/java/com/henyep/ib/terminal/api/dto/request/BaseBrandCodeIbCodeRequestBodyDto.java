package com.henyep.ib.terminal.api.dto.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.henyep.ib.terminal.api.global.Constants;

public class BaseBrandCodeIbCodeRequestBodyDto extends BaseRequestBodyDto implements Serializable {

	private static final long serialVersionUID = 5080748944685619556L;

	@NotNull(message = Constants.ERR_COMMON_BRAND_CODE_IS_BLANK)
	protected String brand_code;

	@NotNull(message = Constants.ERR_COMMON_IB_CODE_IS_BLANK)
	protected String ib_code;

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
