package com.henyep.ib.terminal.api.dto.request.report;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class GetIbClientMapReportRequest extends IbAdminRequestDto<GetIbClientMapReportRequestDto> {
	
	private static final long serialVersionUID = 5261228980069779453L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.REPORT_VIEW_IB_CLIENT_MAP;
	}

	
}
