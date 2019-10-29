package com.henyep.ib.terminal.api.dto.request.adminUser;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class ResetSystemCacheRequest extends IbAdminRequestDto<ResetSystemCacheRequestDto> {

	private static final long serialVersionUID = -4602121284037611184L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.IB_SYSTEM_EDIT_CLEAR_CACHE;

	}

}
