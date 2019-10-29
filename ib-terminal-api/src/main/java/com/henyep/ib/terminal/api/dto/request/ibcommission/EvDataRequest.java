package com.henyep.ib.terminal.api.dto.request.ibcommission;

import com.henyep.ib.terminal.api.dto.request.BaseDateTimeRequestBodyDto;
import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class EvDataRequest extends IbAdminRequestDto<BaseDateTimeRequestBodyDto>{
	
	private static final long serialVersionUID = 4077165245292161522L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.COMM_EV_VIEW_SEARCH;
	}

}
