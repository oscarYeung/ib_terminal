package com.henyep.ib.terminal.server.service;

import java.util.List;

import com.henyep.ib.terminal.api.dto.request.ibcommission.clientSummary.GetByIbCodeRequest;
import com.henyep.ib.terminal.api.dto.response.ibcommission.clientSummary.GetByIbCodeResponseDto;


public interface IbCommissionClientSummaryService {

	public GetByIbCodeResponseDto getIbCommissionClientSummaryByDate(GetByIbCodeRequest request) throws Exception;
	
	public List<String> validateIbCommissionClientSummaryByDate(GetByIbCodeRequest request) throws Exception;
	
	
}
