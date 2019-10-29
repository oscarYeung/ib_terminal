package com.henyep.ib.terminal.server.service;

import com.henyep.ib.terminal.api.dto.request.ibcommission.details.GetCommissionDetailsRequest;
import com.henyep.ib.terminal.api.dto.response.ibcommission.details.GetCommissionDetailsResponseDto;

public interface IbCommissionDetailsService {

	public GetCommissionDetailsResponseDto GetIbCommissionDetails(GetCommissionDetailsRequest request) throws Exception;

}
