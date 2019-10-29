package com.henyep.ib.terminal.api.dto.request.adminUser;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class UpdateUserRequest extends IbAdminRequestDto<UpdateUserRequestDto> {

	private static final long serialVersionUID = 2114179383280128984L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.NA;
	}
}
