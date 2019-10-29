package com.henyep.ib.terminal.api.dto.request.rebate;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class InsertRebateSupplementariesRequest extends IbAdminRequestDto<InsertUpdateRebateSupplementariesDto> {

	private static final long serialVersionUID = -2742529547768247494L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.IB_MGT_ADD_EV;
	}

}
