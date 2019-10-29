package com.henyep.ib.terminal.api.dto.request.schedule.task;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class RunScheduleTaskRequest extends IbAdminRequestDto<RunScheduleTaskRequestDto>{
	
	private static final long serialVersionUID = -6997495534313591360L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.COMM_REBATE_CALC_EDIT_SYNC_DATA;
	}

}
