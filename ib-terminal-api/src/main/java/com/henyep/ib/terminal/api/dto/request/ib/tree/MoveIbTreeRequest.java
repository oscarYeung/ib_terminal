package com.henyep.ib.terminal.api.dto.request.ib.tree;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class MoveIbTreeRequest extends IbAdminRequestDto<MoveIbTreeRequestDto>{
	
	private static final long serialVersionUID = -6897450426975429530L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.IB_TREE_MGT_EDIT_CLOSE_IB;
	}

}
