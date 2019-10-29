package com.henyep.ib.terminal.server.service;

import java.util.List;

import com.henyep.ib.terminal.api.dto.request.marginout.AdminUpdateMarginOutRequest;
import com.henyep.ib.terminal.api.dto.request.marginout.BatchApproveMarginOutRequest;
import com.henyep.ib.terminal.api.dto.request.marginout.CancelMarginOutRequest;
import com.henyep.ib.terminal.api.dto.request.marginout.ExcelUploadMarginOutRequest;
import com.henyep.ib.terminal.api.dto.request.marginout.GetMarginOutRequest;
import com.henyep.ib.terminal.api.dto.request.marginout.GetMaxWithdrawalRequest;
import com.henyep.ib.terminal.api.dto.request.marginout.InsertMarginOutRequest;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.marginout.BatchApproveMarginOutsResponseDto;
import com.henyep.ib.terminal.api.dto.response.marginout.ExcelUploadMarginOutResponseDto;
import com.henyep.ib.terminal.api.dto.response.marginout.GetMarginOutResponseDto;
import com.henyep.ib.terminal.api.dto.response.marginout.GetMaxWithdrawalResponseDto;
import com.henyep.ib.terminal.api.dto.response.marginout.InsertMarginOutResponseDto;
import com.henyep.ib.terminal.api.dto.response.marginout.UpdateMarginOutResponseDto;
import com.henyep.ib.terminal.server.dto.security.SenderDto;


public interface MarginOutService {
 
	public IbResponseDto<InsertMarginOutResponseDto> createMarginOut(InsertMarginOutRequest request, String status) throws Exception;
	
	public GetMarginOutResponseDto getMarginOut(GetMarginOutRequest request) throws Exception;
	
	public IbResponseDto<UpdateMarginOutResponseDto> updateMarginOut(AdminUpdateMarginOutRequest request) throws Exception;
	
	public IbResponseDto<UpdateMarginOutResponseDto> cancelMarginOut(CancelMarginOutRequest request) throws Exception;
	
	public GetMaxWithdrawalResponseDto getMaxWithdrawal(GetMaxWithdrawalRequest request) throws Exception;
	
	public IbResponseDto<BatchApproveMarginOutsResponseDto> approveMarginOuts(BatchApproveMarginOutRequest request) throws Exception;
	
	public List<String> validateApproveMarginOuts(BatchApproveMarginOutRequest request) throws Exception;
	
}
