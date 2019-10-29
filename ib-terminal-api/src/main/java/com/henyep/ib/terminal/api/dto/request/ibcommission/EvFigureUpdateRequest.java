package com.henyep.ib.terminal.api.dto.request.ibcommission;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class EvFigureUpdateRequest extends IbAdminRequestDto<EvFigureUpdateRequestDto> {

	private static final long serialVersionUID = 3164447403076989054L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.COMM_EV_EDIT_SAVE;
	}

}
