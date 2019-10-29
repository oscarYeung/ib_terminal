package com.henyep.ib.terminal.api.dto.request.marginin;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class InsertMarginInRequest extends IbAdminRequestDto<InsertMarginInRequestDto> {
	
	private static final long serialVersionUID = -4320298023334767627L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.MARGIN_IN_ADD_ADJ;
	}

	
	
}
