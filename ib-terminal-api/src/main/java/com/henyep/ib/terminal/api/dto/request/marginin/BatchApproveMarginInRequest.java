package com.henyep.ib.terminal.api.dto.request.marginin;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class BatchApproveMarginInRequest extends IbAdminRequestDto<BatchApproveMarginInRequestDto> {

	private static final long serialVersionUID = 2711920820004369802L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.MARGIN_IN_EDIT_APPROVE_ALL;
	}

}
