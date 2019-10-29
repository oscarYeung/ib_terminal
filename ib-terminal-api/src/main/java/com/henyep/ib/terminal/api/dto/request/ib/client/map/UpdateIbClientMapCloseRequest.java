package com.henyep.ib.terminal.api.dto.request.ib.client.map;

import com.henyep.ib.terminal.api.global.PermissionCodes;

public class UpdateIbClientMapCloseRequest extends UpdateIbClientMapRequest {

	private static final long serialVersionUID = 2158078628282980714L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.IB_MGT_EDIT_CLIENT_CLOSE;
	}

}
