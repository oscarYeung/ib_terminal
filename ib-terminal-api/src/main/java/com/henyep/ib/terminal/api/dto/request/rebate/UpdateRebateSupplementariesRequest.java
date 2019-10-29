package com.henyep.ib.terminal.api.dto.request.rebate;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class UpdateRebateSupplementariesRequest extends IbAdminRequestDto<InsertUpdateRebateSupplementariesDto>{

	private static final long serialVersionUID = 4294459582810044752L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.IB_MGT_EDIT_EV;
	}

}
