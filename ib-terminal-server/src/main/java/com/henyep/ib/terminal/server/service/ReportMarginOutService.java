package com.henyep.ib.terminal.server.service;

import javax.servlet.http.HttpServletResponse;

import com.henyep.ib.terminal.api.dto.request.report.GetMarginOutReportRequest;
import com.henyep.ib.terminal.server.dto.security.SenderDto;


public interface ReportMarginOutService {

	
	public void getMarginOutReport(GetMarginOutReportRequest request, HttpServletResponse response, SenderDto sender) throws Exception;
}

