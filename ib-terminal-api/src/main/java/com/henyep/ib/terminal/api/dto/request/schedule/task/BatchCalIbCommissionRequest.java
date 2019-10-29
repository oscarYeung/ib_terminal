package com.henyep.ib.terminal.api.dto.request.schedule.task;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.dto.request.IbRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;



public class BatchCalIbCommissionRequest extends IbAdminRequestDto<BatchCalIbCommissionRequestDto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4303890833134075309L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.COMM_REBATE_CALC_EDIT_BATCH_RECALC;

	}

	

}

