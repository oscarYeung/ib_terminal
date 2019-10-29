package com.henyep.ib.terminal.api.dto.request.ib.tree;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class AddIbTreeRequest extends IbAdminRequestDto<AddIbTreeRequestDto> {

	private static final long serialVersionUID = -2730334196680808732L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.IB_TREE_MGT_ADD_IB;
	}

}
