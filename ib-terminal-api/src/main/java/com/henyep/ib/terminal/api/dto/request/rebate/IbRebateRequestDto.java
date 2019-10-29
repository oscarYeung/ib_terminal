package com.henyep.ib.terminal.api.dto.request.rebate;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.henyep.ib.terminal.api.dto.request.BaseDateTimeRequestBodyDto;
import com.henyep.ib.terminal.api.global.Constants;

public class IbRebateRequestDto extends BaseDateTimeRequestBodyDto implements Serializable {

	private static final long serialVersionUID = -7557230034743888958L;
	@NotNull(message = Constants.ERR_COMMON_BRAND_CODE_IS_BLANK)
	private String brand_code;
	@NotNull(message = Constants.ERR_COMMON_IB_CODE_IS_BLANK)
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
