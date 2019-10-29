package com.henyep.ib.terminal.api.dto.request.report;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class GetMarginInReportRequest extends IbAdminRequestDto<GetMarginInReportRequestDto>{

	private static final long serialVersionUID = -3120888339032696657L;
	@Override
	public Integer getPermission_code() {
		return PermissionCodes.REPORT_VIEW_MARGIN_IN;
	}
}
