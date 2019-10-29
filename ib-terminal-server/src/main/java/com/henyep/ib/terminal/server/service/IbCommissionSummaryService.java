package com.henyep.ib.terminal.server.service;

import java.util.List;

import com.henyep.ib.terminal.api.dto.request.ibcommission.summary.GetByTeamHeadRequest;
import com.henyep.ib.terminal.api.dto.response.ibcommission.summary.GetByTeamHeadResponseDto;


public interface IbCommissionSummaryService {

	public GetByTeamHeadResponseDto GetIbCommissionSummary(GetByTeamHeadRequest request) throws Exception;
	
	public List<String> ValidateGetIbCommissionSummary(GetByTeamHeadRequest request) throws Exception;
	
}
