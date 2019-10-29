package com.henyep.ib.terminal.api.dto.request.marginout;

import com.henyep.ib.terminal.api.global.PermissionCodes;

public class AdminCancelMarginOutRequest extends AdminUpdateMarginOutRequest{

	private static final long serialVersionUID = 2445064906580432957L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.MARGIN_OUT_EDIT_CANCEL;
	}

}
