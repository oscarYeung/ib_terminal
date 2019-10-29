package com.henyep.ib.terminal.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.api.dto.request.ibcommission.details.GetCommissionDetailsRequest;
import com.henyep.ib.terminal.api.dto.response.ibcommission.details.GetCommissionDetailsResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.details.IbCommissionDetailsWebModel;
import com.henyep.ib.terminal.server.dao.IbCommissionDetailsDao;
import com.henyep.ib.terminal.server.service.IbCommissionDetailsService;

@Component("IbCommissionDetailsService")
public class IbCommissionDetailsServiceImpl implements IbCommissionDetailsService {

	private IbCommissionDetailsDao ibCommissionDetailsDao;

	@Autowired
	public IbCommissionDetailsServiceImpl(IbCommissionDetailsDao ibCommissionDetailsDao) {
		this.ibCommissionDetailsDao = ibCommissionDetailsDao;
	}

	@Override
	public GetCommissionDetailsResponseDto GetIbCommissionDetails(GetCommissionDetailsRequest request) throws Exception {
		List<IbCommissionDetailsWebModel> list = ibCommissionDetailsDao.getIbCommissionDetailsByDateRange(request.getBody().getIb_code(),
				request.getBody().getClient_code(), request.getBody().getStart_date(), request.getBody().getEnd_date());
		GetCommissionDetailsResponseDto dto = new GetCommissionDetailsResponseDto();
		dto.setList(list);
		return dto;
	}
}
