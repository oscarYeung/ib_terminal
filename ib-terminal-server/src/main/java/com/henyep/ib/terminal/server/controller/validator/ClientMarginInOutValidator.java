package com.henyep.ib.terminal.server.controller.validator;

import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.api.dto.request.client.margin.in.out.ClientMarginInOutRequest;

@Component
public class ClientMarginInOutValidator extends AbstractValidator {	

	@Override
	public boolean supports(Class<?> clazz) {
		if (ClientMarginInOutRequest.class.isAssignableFrom(clazz))
			return true;
		else
			return false;
	}
}
