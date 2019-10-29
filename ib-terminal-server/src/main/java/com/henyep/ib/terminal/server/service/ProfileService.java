package com.henyep.ib.terminal.server.service;

import com.henyep.ib.terminal.api.dto.request.IbRequestDto;
import com.henyep.ib.terminal.api.dto.request.user.IbProfileGetRequestDto;
import com.henyep.ib.terminal.api.dto.response.user.IbProfileGetResponseDto;

public interface ProfileService {
	IbProfileGetResponseDto getProfile(IbRequestDto<IbProfileGetRequestDto> editDto);

	IbProfileGetResponseDto getProfileByIbCode(String ibCode);

	String sendWelcomeEmail(String ibCode, boolean generateRandomPassword, String sender) throws Exception;
}
