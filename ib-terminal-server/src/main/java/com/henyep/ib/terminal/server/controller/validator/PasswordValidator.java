package com.henyep.ib.terminal.server.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.henyep.ib.terminal.api.dto.request.password.ChangePasswordRequest;
import com.henyep.ib.terminal.api.dto.request.password.ForgotPasswordRequest;
import com.henyep.ib.terminal.api.dto.request.password.ResetPasswordRequest;
import com.henyep.ib.terminal.api.dto.request.password.VerifyTokenRequest;

@Component
public class PasswordValidator extends AbstractValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		if (ForgotPasswordRequest.class.isAssignableFrom(clazz) || VerifyTokenRequest.class.isAssignableFrom(clazz)
				|| ChangePasswordRequest.class.isAssignableFrom(clazz) || ResetPasswordRequest.class.isAssignableFrom(clazz))
			return true;
		else
			return false;
	}

	@Override
	public void validate(Object obj, Errors errs) {

		if (obj != null) {
			
			// Skip reset password check 
			if (!(obj instanceof ForgotPasswordRequest) && !(obj instanceof VerifyTokenRequest) && !(obj instanceof ResetPasswordRequest)) {
				super.validate(obj, errs);
			}
		}
	}

}
