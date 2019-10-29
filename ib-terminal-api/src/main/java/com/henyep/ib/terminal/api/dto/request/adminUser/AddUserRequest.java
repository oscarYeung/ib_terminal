package com.henyep.ib.terminal.api.dto.request.adminUser;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class AddUserRequest extends IbAdminRequestDto<AddUserRequestDto> {

	private static final long serialVersionUID = -4602121284037611184L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.NA;

	}

}
