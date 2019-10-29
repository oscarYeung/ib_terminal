package com.henyep.ib.terminal.server.service;

import java.util.List;

import com.henyep.ib.terminal.api.dto.request.IbRequestDto;
import com.henyep.ib.terminal.api.dto.request.rebate.GetRebateByIbCodeRequestDto;
import com.henyep.ib.terminal.api.dto.request.rebate.IbRebateRequestDto;
import com.henyep.ib.terminal.api.dto.request.rebate.InsertUpdateRebateDetailsDto;
import com.henyep.ib.terminal.api.dto.request.rebate.InsertUpdateRebateSupplementariesDto;
import com.henyep.ib.terminal.api.dto.request.rebate.RebateRequestDto;
import com.henyep.ib.terminal.api.dto.response.rebate.IbClientRebateResponseDto;
import com.henyep.ib.terminal.api.dto.response.rebate.RebateDetailsResponseDto;
import com.henyep.ib.terminal.api.dto.response.rebate.RebateResponseDto;
import com.henyep.ib.terminal.api.dto.response.rebate.RebateSupplementasriesResponseDto;

public interface RebateService {

	public RebateResponseDto getRebateByRebateCodeRequest(IbRequestDto<RebateRequestDto> request);
	
	public RebateResponseDto getRebateByIbCodeRequest(IbRequestDto<GetRebateByIbCodeRequestDto> request);
	
	public IbClientRebateResponseDto getRebateByIbCodeWithDateRangeRequest(IbRequestDto<IbRebateRequestDto> request);
	
	
	public RebateDetailsResponseDto updateRebateDetailsRequest(IbRequestDto<InsertUpdateRebateDetailsDto> request) throws Exception;
	public List<String> validateInsertRebateDetailsRequest(IbRequestDto<InsertUpdateRebateDetailsDto> request) throws Exception;
	public List<String> validateUpdateRebateDetailsRequest(IbRequestDto<InsertUpdateRebateDetailsDto> request) throws Exception;
	
	public RebateSupplementasriesResponseDto updateRebateSupplementariesRequest(IbRequestDto<InsertUpdateRebateSupplementariesDto> request) throws Exception;
	public List<String> validateInsertRebateSupplementariesRequest(IbRequestDto<InsertUpdateRebateSupplementariesDto> request) throws Exception;
	public List<String> validateUpdateRebateSupplementariesRequest(IbRequestDto<InsertUpdateRebateSupplementariesDto> request) throws Exception;

	
}
