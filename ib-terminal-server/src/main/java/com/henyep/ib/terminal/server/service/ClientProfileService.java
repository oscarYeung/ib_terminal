package com.henyep.ib.terminal.server.service;

import java.util.List;

import com.henyep.ib.terminal.api.dto.request.clientProfile.GetClientProfileRequest;
import com.henyep.ib.terminal.api.dto.request.clientProfile.SearchMyClientsRequest;
import com.henyep.ib.terminal.api.dto.response.clientProfile.GetClientProfileResponseDto;
import com.henyep.ib.terminal.api.dto.response.clientProfile.SearchMyClientsResponseDto;

public interface ClientProfileService {

	
	public GetClientProfileResponseDto getClientProfile(GetClientProfileRequest request) throws Exception;
	public List<String> validateGetClientProfile(GetClientProfileRequest request) throws Exception;
	public SearchMyClientsResponseDto searchMyClients(SearchMyClientsRequest request) throws Exception;
}
