package com.henyep.ib.terminal.server.controller.validator;

import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.api.dto.request.ibcommission.summary.GetByTeamHeadRequest;

@Component(value = "IbCommissionSummaryValidator")
public class IbCommissionSummaryValidator extends AbstractValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		if (GetByTeamHeadRequest.class.isAssignableFrom(clazz)) {
			return true;
		} else
			return false;
	}
}
