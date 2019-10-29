package com.henyep.ib.terminal.server.service;

import javax.servlet.http.HttpServletResponse;

import com.henyep.ib.terminal.api.dto.request.report.GetIbTreeReportRequest;
import com.henyep.ib.terminal.server.dto.security.SenderDto;

public interface ReportIbTreeService {

	public void getIbTreeReport(GetIbTreeReportRequest request, HttpServletResponse response, SenderDto sender) throws Exception;
}
