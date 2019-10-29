package com.henyep.ib.terminal.api.dto.request.marginin;

import com.henyep.ib.terminal.api.global.PermissionCodes;

public class ApproveMarginInRequest extends UpdateMarginInRequest{

	
	private static final long serialVersionUID = 7417687533812110510L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.MARGIN_IN_EDIT_APPROVE;
	}

}
