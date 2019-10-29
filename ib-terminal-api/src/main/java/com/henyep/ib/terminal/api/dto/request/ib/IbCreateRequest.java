package com.henyep.ib.terminal.api.dto.request.ib;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class IbCreateRequest extends IbAdminRequestDto<IbCreateRequestDto> {
	
	private static final long serialVersionUID = -7455908899754463674L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.IB_MGT_ADD_PROFILE;
	}
	 
}
