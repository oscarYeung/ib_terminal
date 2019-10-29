package com.henyep.ib.terminal.api.dto.request.ib.client.map;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class InsertIbClientMapRequest extends IbAdminRequestDto<InsertIbClientMapRequestDto>{

	private static final long serialVersionUID = 2729742174287807916L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.IB_MGT_ADD_CLIENT;
	}

}
