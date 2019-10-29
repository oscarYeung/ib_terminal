package com.henyep.ib.terminal.api.dto.request.marginin;

import com.henyep.ib.terminal.api.global.PermissionCodes;

public class RejectMarginInRequest extends UpdateMarginInRequest{

	private static final long serialVersionUID = 9015086559863075636L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.MARGIN_IN_EDIT_REJECT;
	}

}
