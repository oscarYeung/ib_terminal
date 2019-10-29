package com.henyep.ib.terminal.server.adapter;

import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.api.dto.request.schedule.task.GetAllMT5SymbolRequestDto;
import com.henyep.ib.terminal.server.dto.mt4.request.BaseRequestModel;
import com.henyep.ib.terminal.server.dto.mt4.request.GetAllMT5SymbolsRequest;
import com.henyep.ib.terminal.server.dto.mt4.response.GetAllMT5SymbolsResponse;
import com.henyep.ib.terminal.server.global.Constants;

@Component(value = "GetAllMT5SymbolsServiceAdapter")
public class GetAllMT5SymbolsServiceAdapter extends AbstractMT4ServiceAdapter<GetAllMT5SymbolRequestDto, GetAllMT5SymbolsResponse> {

	public GetAllMT5SymbolsServiceAdapter() {
		super(GetAllMT5SymbolRequestDto.class, GetAllMT5SymbolsResponse.class);
	}

	@Override
	protected BaseRequestModel getMT4RequestModel(String token, GetAllMT5SymbolRequestDto data) {
		GetAllMT5SymbolsRequest request = new GetAllMT5SymbolsRequest();
		request.setSymbolPathPrefix(data.getSymbolPathPrefix());
		return request;
	}

	@Override
	protected String getServiceLink() {
		return Constants.MT4_WEB_SERVICE_GET_ALL_SYMBOL_LINK;
	}

}
