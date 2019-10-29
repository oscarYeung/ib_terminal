package com.henyep.ib.terminal.api.dto.request.ib.tree;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class AddIbTreeByIbCodeRequest extends IbAdminRequestDto<AddIbTreeByIbCodeRequestDto> {

	private static final long serialVersionUID = 2272206352588381383L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.IB_MGT_ADD_TREE;
	}

}
