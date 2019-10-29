package com.henyep.ib.terminal.server.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.henyep.ib.terminal.api.dto.response.ib.lead.GetIbLeadsResponseDto;
import com.henyep.ib.terminal.server.dao.IbLeadDao;
import com.henyep.ib.terminal.server.service.IbLeadService;

@Service(value = "IbLeadService")
public class IbLeadServiceImpl implements IbLeadService {
	@Resource
	private IbLeadDao ibLeadDao;

	@Override
	public GetIbLeadsResponseDto getIbLeads(String ib_code, String name, String email, String phone, Boolean withSubIbLeads) {
		GetIbLeadsResponseDto response = new GetIbLeadsResponseDto();
		if (withSubIbLeads)
			response.setLeadList(ibLeadDao.getLeadsWithSubIb(ib_code, name, email, phone));
		else
			response.setLeadList(ibLeadDao.getLeads(ib_code, name, email, phone));

		return response;
	}
	 

}
