package com.henyep.ib.terminal.api.dto.request.marginin;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class GetMarginInRequest extends IbAdminRequestDto<GetMarginInRequestDto> {

	private static final long serialVersionUID = -4872817426861344074L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.MARGIN_IN_VIEW_SEARCH;
	}

}
