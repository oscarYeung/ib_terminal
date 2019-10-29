package com.henyep.ib.terminal.server.controller.validator;

import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.api.dto.request.ib.lead.GetIbLeadsRequest;

@Component
public class IbLeadValidator extends AbstractValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		if (GetIbLeadsRequest.class.isAssignableFrom(clazz))
			return true;
		else
			return false;
	}
}
