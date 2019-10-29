package com.henyep.ib.terminal.api.dto.request.ib.client.link;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class UpdateIbClientLinkRequest extends IbAdminRequestDto<UpdateIbClientLinkDto> {

	private static final long serialVersionUID = -7845988013969495796L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.IB_MGT_EDIT_LINK;
	}

}
