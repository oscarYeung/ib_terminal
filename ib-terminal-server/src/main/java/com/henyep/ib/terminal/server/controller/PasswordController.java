package com.henyep.ib.terminal.server.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.henyep.ib.terminal.api.dto.request.password.ChangePasswordRequest;
import com.henyep.ib.terminal.api.dto.request.password.ForgotPasswordRequest;
import com.henyep.ib.terminal.api.dto.request.password.ResetPasswordRequest;
import com.henyep.ib.terminal.api.dto.request.password.VerifyTokenRequest;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.password.ForgotPasswordResponseDto;
import com.henyep.ib.terminal.api.dto.response.password.PasswordUpdatedResponseDto;
import com.henyep.ib.terminal.api.dto.response.password.VerifyTokenResponseDto;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.server.controller.validator.PasswordValidator;
import com.henyep.ib.terminal.server.exception.ServiceException;
import com.henyep.ib.terminal.server.service.PasswordService;

@Controller
@RequestMapping("/password")
public class PasswordController extends AbstractController {

	@Autowired
	public PasswordController(PasswordValidator validator) {
		super(validator);
	}

	@Resource(name = "PasswordService")
	private PasswordService passwordService;

	@RequestMapping(value = "/ibForgotPassword", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<ForgotPasswordResponseDto> ibForgotPassword(@RequestBody @Valid ForgotPasswordRequest request,
			BindingResult result) {

		logger.info("================= /password/ibForgotPassword Start =================");
		IbResponseDto<ForgotPasswordResponseDto> response = null;
		try {
			logger.info("request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());

			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				ForgotPasswordResponseDto dto = passwordService.ibForgotPassword(request);
				if (dto != null) {
					response.setBody(dto);
				} else {
					hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
				}
			}

		} catch (ServiceException se) {
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, se.getErrorCode());
		} catch (Exception e) {
			logger.error(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		logger.info("response =" + g.toJson(response));
		logger.info("================= /password/ibForgotPassword End =================");
		return response;
	}

	@RequestMapping(value = "/verifyToken", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<VerifyTokenResponseDto> verifyToken(@RequestBody @Valid VerifyTokenRequest request, BindingResult result) {

		logger.info("================= /password/verifyToken Start =================");
		IbResponseDto<VerifyTokenResponseDto> response = null;
		try {
			logger.info("request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				VerifyTokenResponseDto dto = passwordService.verifyToken(request);
				if (dto != null) {
					response.setBody(dto);
				} else {
					hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
				}
			}
		} catch (ServiceException se) {
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, se.getErrorCode());
		} catch (Exception e) {
			logger.error(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		logger.info("response =" + g.toJson(response));
		logger.info("================= /password/verifyToken End =================");
		return response;
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<PasswordUpdatedResponseDto> changePassword(@RequestBody @Valid ChangePasswordRequest request,
			BindingResult result) {

		logger.info("================= /password/changePassword Start =================");
		IbResponseDto<PasswordUpdatedResponseDto> response = null;
		try {
			logger.info("request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				PasswordUpdatedResponseDto dto = passwordService.changePassword(request);
				if (dto != null) {
					response.setBody(dto);
				} else {
					hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
				}
			}

		} catch (ServiceException se) {
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, se.getErrorCode());
		} catch (Exception e) {
			logger.error(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		logger.info("response =" + g.toJson(response));
		logger.info("================= /password/changePassword End =================");
		return response;
	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<PasswordUpdatedResponseDto> changePassword(@RequestBody @Valid ResetPasswordRequest request,
			BindingResult result) {

		logger.info("================= /password/resetPassword Start =================");
		IbResponseDto<PasswordUpdatedResponseDto> response = null;
		try {
			logger.info("request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				PasswordUpdatedResponseDto dto = passwordService.resetPassword(request);
				if (dto != null) {
					response.setBody(dto);
				} else {
					hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
				}
			}
		} catch (ServiceException se) {
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, se.getErrorCode());
		} catch (Exception e) {
			logger.error(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		logger.info("response =" + g.toJson(response));
		logger.info("================= /password/resetPassword End =================");
		return response;
	}
}
