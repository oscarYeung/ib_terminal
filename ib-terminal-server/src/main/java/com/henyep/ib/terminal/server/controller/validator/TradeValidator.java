package com.henyep.ib.terminal.server.controller.validator;

import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.api.dto.request.trade.GetClientTradeHistoryRequest;

@Component(value = "TradeValidator")
public class TradeValidator extends AbstractValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		if (GetClientTradeHistoryRequest.class.isAssignableFrom(clazz))
			return true;
		else
			return false;
	}
}
