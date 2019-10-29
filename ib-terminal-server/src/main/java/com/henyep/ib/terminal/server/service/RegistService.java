package com.henyep.ib.terminal.server.service;

import java.util.List;

import com.henyep.ib.terminal.api.dto.request.IbRequestDto;
import com.henyep.ib.terminal.api.dto.request.ib.IbCreateRequestDto;
import com.henyep.ib.terminal.api.dto.request.ib.IbUpdateRequestDto;
import com.henyep.ib.terminal.api.dto.request.user.IbRegistReq;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.user.IbCreateRespDto;
import com.henyep.ib.terminal.api.dto.response.user.IbRegistResp;
import com.henyep.ib.terminal.api.dto.response.user.IbUpdateRespDto;

public interface RegistService
{
	public IbResponseDto<IbRegistResp> regist(IbRequestDto<IbRegistReq> registerDto);
	
	// create ib profile
	public IbCreateRespDto createIbProfile(IbRequestDto<IbCreateRequestDto> ibCreateReq) throws Exception;
	
	public List<String> validateCreateIbProfile(IbRequestDto<IbCreateRequestDto> ibCreateReq) throws Exception;
	
	
	// update ib profile
	public IbUpdateRespDto updateIbProfile(IbRequestDto<IbUpdateRequestDto> ibUpdateReq) throws Exception;
	
	public List<String> validateUpdateIbProfile(IbRequestDto<IbUpdateRequestDto> ibUpdateReq) throws Exception;
}
