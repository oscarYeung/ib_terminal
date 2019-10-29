package com.henyep.ib.terminal.server.controller.validator;

import com.henyep.ib.terminal.api.dto.request.ibcommission.GetIbSummaryRequest;

public class IbSummaryValidator extends AbstractValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		if (GetIbSummaryRequest.class.isAssignableFrom(clazz))
			return true;
		else
			return false;
	}

}
