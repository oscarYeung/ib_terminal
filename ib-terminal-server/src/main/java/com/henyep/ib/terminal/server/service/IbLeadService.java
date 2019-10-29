package com.henyep.ib.terminal.server.service;

import com.henyep.ib.terminal.api.dto.response.ib.lead.GetIbLeadsResponseDto;

public interface IbLeadService {

	public GetIbLeadsResponseDto getIbLeads(String ib_code, String name, String email, String phone, Boolean withSubIbLeads);

}
