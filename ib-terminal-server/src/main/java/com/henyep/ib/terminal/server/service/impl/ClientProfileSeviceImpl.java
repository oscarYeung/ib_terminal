package com.henyep.ib.terminal.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.henyep.ib.terminal.api.dto.db.ClientDetailsBean;
import com.henyep.ib.terminal.api.dto.db.ClientMarginInOutBean;
import com.henyep.ib.terminal.api.dto.request.clientProfile.GetClientProfileRequest;
import com.henyep.ib.terminal.api.dto.request.clientProfile.SearchMyClientsRequest;
import com.henyep.ib.terminal.api.dto.response.clientProfile.ClientMarginModel;
import com.henyep.ib.terminal.api.dto.response.clientProfile.GetClientProfileResponseDto;
import com.henyep.ib.terminal.api.dto.response.clientProfile.SearchMyClientsModel;
import com.henyep.ib.terminal.api.dto.response.clientProfile.SearchMyClientsResponseDto;
import com.henyep.ib.terminal.server.dao.ClientDetailsDao;
import com.henyep.ib.terminal.server.dao.ClientMarginInOutDao;
import com.henyep.ib.terminal.server.service.ClientProfileService;

@Service(value = "ClientProfileService")
public class ClientProfileSeviceImpl extends AbstractServiceImpl implements ClientProfileService {

	private ClientDetailsDao clientDetailsDao;
	private ClientMarginInOutDao clientMarginInOutDao;

	@Autowired
	public ClientProfileSeviceImpl(ClientDetailsDao clientDetailsDao, ClientMarginInOutDao clientMarginInOutDao) {
		this.clientDetailsDao = clientDetailsDao;
		this.clientMarginInOutDao = clientMarginInOutDao;
	}

	@Override
	public GetClientProfileResponseDto getClientProfile(GetClientProfileRequest request) throws Exception {

		GetClientProfileResponseDto dto = new GetClientProfileResponseDto();
		List<ClientMarginModel> clientMarginList = new ArrayList<ClientMarginModel>();

		String clientCode = request.getBody().getClient_code();
		Date startDate = request.getBody().getStart_date();
		Date endDate = request.getBody().getEnd_date();

		List<ClientDetailsBean> clientDetails = clientDetailsDao.getClientDetailsByKey(clientCode);
		if (clientDetails.size() > 0) {
			dto.setClient_details(clientDetails.get(0));
	
		}

		List<ClientMarginInOutBean> clientMarginInOutBeans = clientMarginInOutDao.getClientMarginGroupByDate(clientCode, startDate, endDate);
		for (ClientMarginInOutBean clientMarginInOutBean : clientMarginInOutBeans) {
			ClientMarginModel clientMarginResponseDto = new ClientMarginModel();
			clientMarginResponseDto.setTrade_date(clientMarginInOutBean.getTrade_date());
			clientMarginResponseDto.setNet_margin(clientMarginInOutBean.getAmount());
			clientMarginList.add(clientMarginResponseDto);
		}
		dto.setClient_margins(clientMarginList);

		return dto;
	}

	@Override
	public List<String> validateGetClientProfile(GetClientProfileRequest request) throws Exception {
		List<String> errorList = new ArrayList<String>();

		return errorList;
	}

	@Override
	public SearchMyClientsResponseDto searchMyClients(SearchMyClientsRequest request) throws Exception {

		SearchMyClientsResponseDto dto = new SearchMyClientsResponseDto();
		String ibCode = getSender(request.getHeader());
		String searchIbCode = request.getBody().getIb_code();
		String searchTradingID = request.getBody().getTrading_id();
		String searchName = request.getBody().getName();
		String searchEmail = request.getBody().getEmail();
		String searchPhone = request.getBody().getPhone();

		List<SearchMyClientsModel> models = clientDetailsDao.searchMyClients(ibCode, searchTradingID, searchIbCode, searchName, searchPhone, searchEmail);
		dto.setBeans(models);

		return dto;

	}

}
