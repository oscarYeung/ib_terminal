package com.henyep.ib.terminal.api.dto.request.adminUser;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class UserChangePasswordRequest extends IbAdminRequestDto<UserChangePasswordRequestDto> {

	private static final long serialVersionUID = 6984047695923228739L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.NA;
	}

}
