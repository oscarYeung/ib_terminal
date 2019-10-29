package com.henyep.ib.terminal.server.controller.validator;

import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.api.dto.request.client.balance.summary.UpdateAccountBalanceRequest;


@Component(value = "ClientBalanceSummaryValidator")
public class ClientBalanceSummaryValidator extends AbstractValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		if(UpdateAccountBalanceRequest.class.isAssignableFrom(clazz)){
			return true;
		} else
			return false;
	}

	@Override
	protected String customValidate(Object obj) {
	
		return null;
	}
	
}
