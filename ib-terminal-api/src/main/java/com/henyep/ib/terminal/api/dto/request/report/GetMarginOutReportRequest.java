package com.henyep.ib.terminal.api.dto.request.report;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class GetMarginOutReportRequest extends IbAdminRequestDto<GetMarginOutReportRequestDto>{

	private static final long serialVersionUID = -4679217147021328266L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.REPORT_VIEW_MARGIN_OUT;
	}
}
