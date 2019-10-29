package com.henyep.ib.terminal.server.adapter;

import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.server.dto.mt4.request.BaseRequestModel;
import com.henyep.ib.terminal.server.dto.mt4.request.DepositMT4Request;
import com.henyep.ib.terminal.server.dto.mt4.response.DepositResponse;
import com.henyep.ib.terminal.server.global.Constants;
import com.henyep.white.label.api.dto.request.balance.transfer.DepositMT4RequestDto;

@Component(value = "DepositServiceAdapter")
public class DepositServiceAdapter extends AbstractMT4ServiceAdapter<DepositMT4RequestDto, DepositResponse> {

	public DepositServiceAdapter() {
		super(DepositMT4RequestDto.class, DepositResponse.class);
	}

	@Override
	protected String getServiceLink() {
		return Constants.MT4_WEB_SERVICE_DEPOSIT_LINK;
	}

	@Override
	protected BaseRequestModel getMT4RequestModel(String token, DepositMT4RequestDto data) {	
		DepositMT4Request request = new DepositMT4Request();
		request.setLogin(data.getLogin_id());
		request.setAmount(data.getAmount());
		request.setComment(data.getComment());
		request.setToken(token);
		return request;
	}

}