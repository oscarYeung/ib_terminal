package com.henyep.ib.terminal.server.service;

import com.henyep.ib.terminal.api.dto.request.IbRequestDto;
import com.henyep.ib.terminal.api.dto.request.ib.IbEditRequestDto;
import com.henyep.ib.terminal.api.dto.response.user.IbProfileEditResp;

public interface EditService {
	IbProfileEditResp edit(IbRequestDto<IbEditRequestDto> editDto);
}
