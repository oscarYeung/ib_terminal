package com.henyep.ib.terminal.server.adapter;

import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.api.dto.request.schedule.task.GetAllSymbolRequestDto;
import com.henyep.ib.terminal.server.dto.mt4.request.BaseRequestModel;
import com.henyep.ib.terminal.server.dto.mt4.request.GetAllSymbolsRequest;
import com.henyep.ib.terminal.server.dto.mt4.response.GetAllSymbolsResponse;
import com.henyep.ib.terminal.server.dto.mt4.response.GetUserProfileResponse;
import com.henyep.ib.terminal.server.global.Constants;


@Component(value = "GetAllSymbolsServiceAdapter")
public class GetAllSymbolsServiceAdapter extends AbstractMT4ServiceAdapter<GetAllSymbolRequestDto, GetAllSymbolsResponse> {

	public GetAllSymbolsServiceAdapter() {	
		super(GetAllSymbolRequestDto.class, GetAllSymbolsResponse.class);
	}
	
	@Override
	protected BaseRequestModel getMT4RequestModel(String token, GetAllSymbolRequestDto data) {

		GetAllSymbolsRequest request = new GetAllSymbolsRequest();
		return request;
	}

	@Override
	protected String getServiceLink() {
		return Constants.MT4_WEB_SERVICE_GET_ALL_SYMBOL_LINK;
	}






}
