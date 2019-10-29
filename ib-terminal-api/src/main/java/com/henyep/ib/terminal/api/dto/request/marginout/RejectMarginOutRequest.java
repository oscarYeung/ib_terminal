package com.henyep.ib.terminal.api.dto.request.marginout;

import com.henyep.ib.terminal.api.global.PermissionCodes;

public class RejectMarginOutRequest extends AdminUpdateMarginOutRequest {

	private static final long serialVersionUID = 6319366489289565067L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.MARGIN_OUT_EDIT_REJECT;
	}

}
