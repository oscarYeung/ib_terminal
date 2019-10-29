package com.henyep.ib.terminal.api.dto.request.ib.client.map;

import com.henyep.ib.terminal.api.global.PermissionCodes;

public class UpdateIbClientMapChangePackageRequest extends UpdateIbClientMapRequest {

	private static final long serialVersionUID = 6224981693627418056L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.IB_MGT_EDIT_CLIENT_CHANGE_PACKAGE;
	}

}
