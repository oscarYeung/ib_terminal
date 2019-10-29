package com.henyep.ib.terminal.api.dto.request.ib.client.link;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class AddIbClientLinkRequest extends IbAdminRequestDto<AddIbClientLinkDto>{

	private static final long serialVersionUID = 8899429013468922946L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.IB_MGT_ADD_LINK;
	}

}
