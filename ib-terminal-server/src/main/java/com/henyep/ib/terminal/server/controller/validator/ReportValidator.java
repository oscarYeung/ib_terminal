package com.henyep.ib.terminal.server.controller.validator;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.api.dto.request.report.GetIbClientMapReportRequest;
import com.henyep.ib.terminal.api.dto.request.report.GetIbCommissionReportRequest;
import com.henyep.ib.terminal.api.dto.request.report.GetIbRebateSettingsReportRequest;
import com.henyep.ib.terminal.api.dto.request.report.GetIbTreeReportRequest;
import com.henyep.ib.terminal.api.dto.request.report.GetMarginInOutReportRequest;
import com.henyep.ib.terminal.api.dto.request.report.GetMarginInReportRequest;
import com.henyep.ib.terminal.api.dto.request.report.GetMarginOutReportRequest;
import com.henyep.ib.terminal.api.dto.request.report.GetMonthlyMarginInOutReportRequest;
import com.henyep.ib.terminal.api.global.Constants;

@Component(value = "ReportValidator")
public class ReportValidator extends AbstractValidator {

	@Override
	protected void initSupportedChannelList() {
		this.supportedChannelList = new ArrayList<String>();
		this.supportedChannelList.add(Constants.CHANNEL_ADMIN);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		if (GetIbCommissionReportRequest.class.isAssignableFrom(clazz) || GetIbTreeReportRequest.class.isAssignableFrom(clazz)
				|| GetMarginInReportRequest.class.isAssignableFrom(clazz) || GetMarginOutReportRequest.class.isAssignableFrom(clazz)
				|| GetIbRebateSettingsReportRequest.class.isAssignableFrom(clazz) || GetIbClientMapReportRequest.class.isAssignableFrom(clazz)
				|| GetMarginInOutReportRequest.class.isAssignableFrom(clazz) || GetMonthlyMarginInOutReportRequest.class.isAssignableFrom(clazz))
			return true;
		else
			return false;
	}

}
