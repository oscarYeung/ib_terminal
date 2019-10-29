package com.henyep.ib.terminal.api.dto.request.marginin;

import com.henyep.ib.terminal.api.global.PermissionCodes;

public class CancelMarginInRequest extends UpdateMarginInRequest{
	
	private static final long serialVersionUID = 5545289478148316660L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.MARGIN_IN_EDIT_CANCEL;
	}

}
