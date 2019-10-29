package com.henyep.ib.terminal.api.dto.request.adminUser;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class UserLoginRequest extends IbAdminRequestDto<UserLoginRequestDto> {

	private static final long serialVersionUID = -5070998232057757206L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.NA;

	}

}
