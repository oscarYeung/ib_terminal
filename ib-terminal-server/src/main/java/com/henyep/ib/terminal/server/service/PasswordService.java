package com.henyep.ib.terminal.server.service;

import com.henyep.ib.terminal.api.dto.request.password.ChangePasswordRequest;
import com.henyep.ib.terminal.api.dto.request.password.ForgotPasswordRequest;
import com.henyep.ib.terminal.api.dto.request.password.ResetPasswordRequest;
import com.henyep.ib.terminal.api.dto.request.password.VerifyTokenRequest;
import com.henyep.ib.terminal.api.dto.response.password.ForgotPasswordResponseDto;
import com.henyep.ib.terminal.api.dto.response.password.PasswordUpdatedResponseDto;
import com.henyep.ib.terminal.api.dto.response.password.VerifyTokenResponseDto;
import com.henyep.ib.terminal.server.exception.ServiceException;

public interface PasswordService {

	public ForgotPasswordResponseDto ibForgotPassword(ForgotPasswordRequest request) throws ServiceException;
	
	public VerifyTokenResponseDto verifyToken(VerifyTokenRequest request) throws ServiceException;
	
	public PasswordUpdatedResponseDto changePassword(ChangePasswordRequest request) throws ServiceException;
	
	public PasswordUpdatedResponseDto resetPassword(ResetPasswordRequest request) throws ServiceException;
}
