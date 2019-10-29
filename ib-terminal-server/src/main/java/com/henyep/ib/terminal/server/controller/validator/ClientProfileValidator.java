package com.henyep.ib.terminal.server.controller.validator;

import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.api.dto.request.clientProfile.GetClientProfileRequest;
import com.henyep.ib.terminal.api.dto.request.clientProfile.SearchMyClientsRequest;

@Component(value = "ClientProfileValidator")
public class ClientProfileValidator extends AbstractValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		if (GetClientProfileRequest.class.isAssignableFrom(clazz) ||
				SearchMyClientsRequest.class.isAssignableFrom(clazz)) {
			return true;
		} else
			return false;
	}

	@Override
	protected String customValidate(Object obj) {
		if (obj instanceof GetClientProfileRequest) {
			GetClientProfileRequest request = (GetClientProfileRequest) obj;
			return validateGetClientProfileRequest(request);
		}
		return null;
	}

	// @Override
	// public void validate(Object obj, Errors errs) {
	// if (obj != null) {
	// String errorCode = "";
	// if(obj instanceof GetClientProfileRequest){
	// GetClientProfileRequest request = (GetClientProfileRequest) obj;
	// errorCode = validateGetClientProfileRequest(request);
	//
	// }
	//
	// if (StringUtils.isNotBlank(errorCode)) {
	// String defaultMsg = messageSource.getMessage(errorCode, null,
	// Locale.ROOT);
	// errs.rejectValue("body", errorCode, defaultMsg);
	// }
	//
	// }
	//
	// }

	private String validateGetClientProfileRequest(GetClientProfileRequest request) {
		String errorCode = "";
		if (request.getBody() == null) {
			errorCode = "request.body.is.null";
		} else if (request.getBody().getStart_date() != null && request.getBody().getEnd_date() != null) {
			if (request.getBody().getStart_date().after(request.getBody().getEnd_date())) {
				errorCode = "common.start.date.after.end.date";
			}
		}
		return errorCode;
	}

}
