package com.henyep.ib.terminal.api.dto.request.schedule.task;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class CalIbCommissionRequest extends IbAdminRequestDto<CalIbCommissionRequestDto> {
	private static final long serialVersionUID = -8829012249582089387L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.COMM_REBATE_CALC_EDIT_RECALC;
	}

}
