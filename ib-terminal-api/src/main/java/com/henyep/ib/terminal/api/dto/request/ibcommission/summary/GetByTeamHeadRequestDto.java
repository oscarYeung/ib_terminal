package com.henyep.ib.terminal.api.dto.request.ibcommission.summary;

import javax.validation.constraints.NotNull;

import com.henyep.ib.terminal.api.dto.request.BaseDateTimeRequestBodyDto;
import com.henyep.ib.terminal.api.global.Constants;

public class GetByTeamHeadRequestDto extends BaseDateTimeRequestBodyDto {

	private static final long serialVersionUID = 8907796980237010935L;

	@NotNull(message = Constants.ERR_COMMON_IB_CODE_IS_BLANK)
	private String ib_code;

	public String getIb_code() {
		return ib_code;
	}

	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}

}
