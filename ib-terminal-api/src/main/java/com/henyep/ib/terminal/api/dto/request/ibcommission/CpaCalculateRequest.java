package com.henyep.ib.terminal.api.dto.request.ibcommission;

import com.henyep.ib.terminal.api.dto.request.BaseTradeDateRequestBodyDto;
import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class CpaCalculateRequest extends IbAdminRequestDto<BaseTradeDateRequestBodyDto>{

	private static final long serialVersionUID = 6176908637178872072L;

	@Override
	public Integer getPermission_code() { 
	return	PermissionCodes.COMM_REBATE_CALC_EDIT_RECALC_CPA;
	}

}
