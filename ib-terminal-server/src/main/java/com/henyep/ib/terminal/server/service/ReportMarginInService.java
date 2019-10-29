package com.henyep.ib.terminal.server.service;

import javax.servlet.http.HttpServletResponse;

import com.henyep.ib.terminal.api.dto.request.report.GetMarginInReportRequest;
import com.henyep.ib.terminal.api.dto.request.report.GetMonthlyMarginInOutReportRequest;
import com.henyep.ib.terminal.server.dto.security.SenderDto;

public interface ReportMarginInService {

	public void getMarginInReport(GetMarginInReportRequest request, HttpServletResponse response, SenderDto sender) throws Exception;
	
	public void getMonthlyMarginInOutReport(GetMonthlyMarginInOutReportRequest request, HttpServletResponse response, SenderDto sender) throws Exception;
	
}
