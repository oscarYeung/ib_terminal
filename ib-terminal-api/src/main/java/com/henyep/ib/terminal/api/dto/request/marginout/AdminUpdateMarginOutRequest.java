package com.henyep.ib.terminal.api.dto.request.marginout;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class AdminUpdateMarginOutRequest extends IbAdminRequestDto<UpdateMarginOutRequestDto>{

	private static final long serialVersionUID = 6173473380190157215L;

	@Override	
	public Integer getPermission_code() {
		return PermissionCodes.NA;
	}

}
