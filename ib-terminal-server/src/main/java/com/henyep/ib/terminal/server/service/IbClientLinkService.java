package com.henyep.ib.terminal.server.service;

import java.util.List;

import com.henyep.ib.terminal.api.dto.request.ib.client.link.AddIbClientLinkRequest;
import com.henyep.ib.terminal.api.dto.request.ib.client.link.UpdateIbClientLinkRequest;
import com.henyep.ib.terminal.api.dto.response.ib.client.link.GetIbClientLinkResponseDto;

public interface IbClientLinkService {

	public GetIbClientLinkResponseDto getIbClientLinkList(String brand_code, String ib_code);

	public List<String> validateAddIbClientLink(AddIbClientLinkRequest request) throws Exception;
	public GetIbClientLinkResponseDto addIbClientLink(AddIbClientLinkRequest request);
	
	public List<String> validateUpdateIbClientLink(UpdateIbClientLinkRequest request) throws Exception;
	public GetIbClientLinkResponseDto updateIbClientLink(UpdateIbClientLinkRequest request);
}
