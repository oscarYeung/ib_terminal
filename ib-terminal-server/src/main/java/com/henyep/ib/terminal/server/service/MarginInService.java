package com.henyep.ib.terminal.server.service;

import java.util.List;

import com.henyep.ib.terminal.api.dto.request.marginin.UpdateMarginInRequest;
import com.henyep.ib.terminal.api.dto.request.marginin.BatchApproveMarginInRequest;
import com.henyep.ib.terminal.api.dto.request.marginin.GetMarginInRequest;
import com.henyep.ib.terminal.api.dto.request.marginin.InsertMarginInRequest;
import com.henyep.ib.terminal.api.dto.response.marginin.UpdateMarginInResponseDto;
import com.henyep.ib.terminal.api.dto.response.marginin.BatchApproveMarginInResponseDto;
import com.henyep.ib.terminal.api.dto.response.marginin.GetMarginInResponseDto;
import com.henyep.ib.terminal.api.dto.response.marginin.InsertMarginInResponseDto;


public interface MarginInService {

	public UpdateMarginInResponseDto updateMarginIn(UpdateMarginInRequest request) throws Exception;
	
	public List<String> validateUpdateMarginIn(UpdateMarginInRequest request) throws Exception;
	
	public List<String> validateGetMarginIn(GetMarginInRequest request) throws Exception;
	
	public GetMarginInResponseDto getMarginIn(GetMarginInRequest request) throws Exception;
	
	public InsertMarginInResponseDto insertMarginIn(InsertMarginInRequest request) throws Exception;
	
	public List<String> validateInsertMarginIn(InsertMarginInRequest request) throws Exception;
	
	public List<String> validateBatchApproveMarginInRequest(BatchApproveMarginInRequest request) throws Exception;
	
	public BatchApproveMarginInResponseDto batchApproveMarginIn(BatchApproveMarginInRequest request) throws Exception;
}
