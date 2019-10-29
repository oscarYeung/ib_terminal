package com.henyep.ib.terminal.api.dto.request.report;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class GetIbRebateSettingsReportRequest extends IbAdminRequestDto<GetIbRebateSettingsReportRequestDto> {

	private static final long serialVersionUID = -2734940805108191950L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.REPORT_VIEW_REBATE_SETTINGS;
	}
}
