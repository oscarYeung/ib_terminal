package com.henyep.ib.terminal.api.dto.request.ib.tree;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class DeleteIbTreeRequest extends IbAdminRequestDto<DeleteIbTreeRequestDto> {
	
	private static final long serialVersionUID = -468907934962607232L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.IB_TREE_MGT_EDIT_CLOSE_IB;
	}
	
	

}
