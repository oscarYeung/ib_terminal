package com.henyep.ib.terminal.api.dto.request.report;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class GetMarginInOutReportRequest extends IbAdminRequestDto<GetMarginInOutReportRequestDto>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 57926409249166528L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.REPORT_VIEW_PERFORMANCE;
	}

}
