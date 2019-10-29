package com.henyep.ib.terminal.api.dto.request.rebate;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class UpdateRebateRequest extends IbAdminRequestDto<InsertUpdateRebateDetailsDto> {

	private static final long serialVersionUID = 7766788344672043113L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.IB_MGT_EDIT_REBATE;
	}

	
	
}
