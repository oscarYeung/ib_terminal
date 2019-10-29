package com.henyep.ib.terminal.server.controller.validator;

import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.api.dto.request.ibcommission.details.GetCommissionDetailsRequest;

@Component(value = "IbCommissionDetailsValidator")
public class IbCommissionDetailsValidator extends AbstractValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		if (GetCommissionDetailsRequest.class.isAssignableFrom(clazz)) {
			return true;
		} else
			return false;
	}
}
