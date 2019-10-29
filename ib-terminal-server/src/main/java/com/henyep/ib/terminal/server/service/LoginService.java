package com.henyep.ib.terminal.server.service;

import com.henyep.ib.terminal.api.dto.request.ib.IbLoginRequest;
import com.henyep.ib.terminal.api.dto.response.ib.login.IbLoginResponseDto;

public interface LoginService {

	public IbLoginResponseDto validateIbLogin(IbLoginRequest request);

	
}
