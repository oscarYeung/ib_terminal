package com.henyep.ib.terminal.server.service;

import com.henyep.ib.terminal.api.dto.request.client.balance.summary.UpdateAccountBalanceRequest;
import com.henyep.ib.terminal.api.dto.response.client.balance.summary.UpdateAccountBalanceResponseDto;


public interface ClientBalanceSummaryService {

	public UpdateAccountBalanceResponseDto updateAccountBalance(UpdateAccountBalanceRequest request, String updatedBy) throws Exception;
	
}
