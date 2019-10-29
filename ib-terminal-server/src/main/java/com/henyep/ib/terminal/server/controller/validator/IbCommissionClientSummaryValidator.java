package com.henyep.ib.terminal.server.controller.validator;

import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.api.dto.request.ibcommission.clientSummary.GetByIbCodeRequest;

@Component
public class IbCommissionClientSummaryValidator extends AbstractValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		if (GetByIbCodeRequest.class.isAssignableFrom(clazz)) {
			return true;
		} else
			return false;

	}
}
