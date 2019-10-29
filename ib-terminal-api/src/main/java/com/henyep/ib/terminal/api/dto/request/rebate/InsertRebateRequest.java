package com.henyep.ib.terminal.api.dto.request.rebate;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class InsertRebateRequest extends IbAdminRequestDto<InsertUpdateRebateDetailsDto> {

	private static final long serialVersionUID = 9034385384365582509L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.IB_MGT_ADD_REBATE;
	}

}
