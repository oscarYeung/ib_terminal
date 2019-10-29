package com.henyep.ib.terminal.api.dto.request.ibcommission;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class EvConfirmRequest extends IbAdminRequestDto<EvConfirmRequestDto> {

	private static final long serialVersionUID = 6492785714879252634L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.COMM_EV_EDIT_CONFIRM;
	}

}
