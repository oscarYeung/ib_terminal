package com.henyep.ib.terminal.server.service;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.henyep.ib.terminal.api.dto.request.report.GetIbClientMapReportRequest;
import com.henyep.ib.terminal.api.dto.request.report.GetIbCommissionReportRequest;
import com.henyep.ib.terminal.api.dto.request.report.GetIbRebateSettingsReportRequest;
import com.henyep.ib.terminal.api.dto.request.report.GetMarginInOutReportRequest;
import com.henyep.ib.terminal.server.dto.security.SenderDto;

public interface ReportService {

	public void getIbCommissionReport(GetIbCommissionReportRequest request, HttpServletResponse response, SenderDto sender) throws Exception;

	public void getIbRebateSettingsReport(GetIbRebateSettingsReportRequest request, HttpServletResponse response, SenderDto sender) throws Exception;
	
	public void getIbClientMapReport(GetIbClientMapReportRequest request, HttpServletResponse response, SenderDto sender) throws Exception ;
	
	public void getMarginInOutReport(GetMarginInOutReportRequest request, HttpServletResponse response, SenderDto sender) throws Exception;
	
	public void setIBCommissionSummaryToWorkSheet(GetIbCommissionReportRequest request, HSSFWorkbook workbook, HSSFCellStyle style, SenderDto sender, String sheetName)
			throws Exception;
	
	public void setClientCommissionSummaryPeriodToWorkSheet(GetIbCommissionReportRequest request, HSSFWorkbook workbook, HSSFCellStyle style,
			SenderDto sender, String sheetName) throws Exception;
	
	
	public void setTradeCommissionSummaryToWorkSheet(GetIbCommissionReportRequest request, HSSFWorkbook workbook, HSSFCellStyle style,
			SenderDto sender) throws Exception;
}
