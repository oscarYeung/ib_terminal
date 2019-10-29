package com.henyep.ib.terminal.server.service;

import com.henyep.ib.terminal.api.dto.request.marginout.ExcelUploadMarginOutRequest;
import com.henyep.ib.terminal.api.dto.response.marginout.ExcelUploadMarginOutResponseDto;
import com.henyep.ib.terminal.server.dto.security.SenderDto;

public interface MarginOutExcelUploadService {
	public ExcelUploadMarginOutResponseDto excelUploadMarginOuts(ExcelUploadMarginOutRequest request, SenderDto sender) throws Exception;
}
