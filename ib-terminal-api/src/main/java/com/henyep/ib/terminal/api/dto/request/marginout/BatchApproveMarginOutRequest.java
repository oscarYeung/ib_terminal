package com.henyep.ib.terminal.api.dto.request.marginout;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class BatchApproveMarginOutRequest extends IbAdminRequestDto<BatchApproveMarginOutRequestDto> {

	
	private static final long serialVersionUID = 6420075841981206300L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.MARGIN_OUT_EDIT_APPROVE_ALL;
	}

}
