package com.henyep.ib.terminal.api.dto.request.report;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.global.PermissionCodes;

public class GetMonthlyMarginInOutReportRequest extends IbAdminRequestDto<GetMonthlyMarginInOutReportRequestDto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -373113314459525432L;

	@Override
	public Integer getPermission_code() {
		return PermissionCodes.REPORT_VIEW_MONTHLY_MARGIN_IN_OUT;
	}

}
