package com.henyep.ib.terminal.server.service;

import com.henyep.ib.terminal.api.dto.request.trade.GetClientTradeHistoryRequest;
import com.henyep.ib.terminal.api.dto.response.trade.GetClientTradeHistoryResponseDto;

public interface TradeService {

	
	public GetClientTradeHistoryResponseDto getClientTradeHistory(GetClientTradeHistoryRequest request) throws Exception;
}
