package com.henyep.ib.terminal.api.dto.request.report;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class GetIbCommissionReportRequest extends IbAdminRequestDto<GetIbCommissionReportRequestDto> {

	private static final long serialVersionUID = -6079505376351228913L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.REPORT_VIEW_COMM;
	}

}
