package com.henyep.ib.terminal.api.dto.request.report;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class GetIbTreeReportRequest extends IbAdminRequestDto<GetIbTreeReportRequestDto> {

	private static final long serialVersionUID = 8138143993353063173L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.REPORT_VIEW_IB_TREE;
	}
}
