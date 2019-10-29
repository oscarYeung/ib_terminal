package com.henyep.ib.terminal.server.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.henyep.ib.terminal.api.dto.request.ib.IbCreateRequest;
import com.henyep.ib.terminal.api.dto.request.ib.IbEditRequest;
import com.henyep.ib.terminal.api.dto.request.ib.IbLoginRequest;
import com.henyep.ib.terminal.api.dto.request.ib.IbSendWelcomeEmailRequest;
import com.henyep.ib.terminal.api.dto.request.ib.IbSendWelcomeRandomPwdEmailRequest;
import com.henyep.ib.terminal.api.dto.request.ib.IbUpdateRequest;
import com.henyep.ib.terminal.api.dto.request.user.IbProfileGetByIbCodeRequest;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.ib.IbSendWelcomeEmailResponseDto;
import com.henyep.ib.terminal.api.dto.response.ib.login.IbLoginResponseDto;
import com.henyep.ib.terminal.api.dto.response.user.IbCreateRespDto;
import com.henyep.ib.terminal.api.dto.response.user.IbProfileEditResp;
import com.henyep.ib.terminal.api.dto.response.user.IbProfileGetResponseDto;
import com.henyep.ib.terminal.api.dto.response.user.IbUpdateRespDto;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.api.global.MessageType;
import com.henyep.ib.terminal.server.controller.validator.IbValidator;
import com.henyep.ib.terminal.server.service.EditService;
import com.henyep.ib.terminal.server.service.LoginService;
import com.henyep.ib.terminal.server.service.ProfileService;
import com.henyep.ib.terminal.server.service.RegistService;
import com.henyep.ib.terminal.server.util.SecurityUtil;

@Controller
@RequestMapping("/ib")
public class IbController extends AbstractController {
	@Autowired
	public IbController(IbValidator validator) {
		super(validator);
	}

	@Resource(name = "LoginService")
	LoginService loginService;

	@Resource(name = "EditService")
	EditService editService;

	@Resource(name = "SecurityUtil")
	private SecurityUtil securityUtil;

	@Resource(name = "ProfileService")
	ProfileService profileService;
	
	@Resource(name = "RegistService")
	RegistService registService;
	
	// @RequestMapping(value = "/ibuser/login", method = { RequestMethod.POST },
	// produces = "application/json")
	@RequestMapping(value = "/login", method = { RequestMethod.POST }, produces = "application/json")
	public @ResponseBody IbResponseDto<IbLoginResponseDto> login(@RequestBody @Valid IbLoginRequest request, BindingResult result) throws Exception {

		logger.info("================= /ib/login Start =================");
		IbResponseDto<IbLoginResponseDto> response = null;
		try {
			logger.info("/ibLogin/login request =" + g.toJson(request));
			response = dtoFactory.createResponse(request.getHeader(), Constants.CHANNEL_WEB, MessageType.DUMMY, false);
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				IbLoginResponseDto body = loginService.validateIbLogin(request);
				if (body != null) {
					response.setBody(body);
					if (body.getIbStatus().equals(Constants.IB_STATUS_INACTIVE)) {
						hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_IB_ACCOUNT_IS_CLOSED);
					} else {
						// Gen SecretKey
						String secretKey = securityUtil.getSecretKey(request.getBody().getIbLoginName());
						response.getHeader().setSecretKey(secretKey);
					}
				} else
					hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_IB_LOGIN_INCORRECT_USERNAME_PASSWORD);

			}
		} catch (Exception e) {
			logger.error(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		logger.info("response =" + g.toJson(response));
		logger.info("================= /ib/login End =================");
		return response;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<IbProfileEditResp> edit(@RequestBody @Valid IbEditRequest request, BindingResult result) {
		logger.info("================= /ib/edit Start =================");

		IbResponseDto<IbProfileEditResp> response = null;

		try {
			response = dtoFactory.createAdminResponse(request.getHeader());
			logger.info("/ib/edit request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				IbProfileEditResp body = editService.edit(request);
				if (body != null) {
					response.setBody(body);
				} else {
					hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		logger.info("response =" + g.toJson(response));
		logger.info("================= /ib/edit End =================");
		return response;
	}

	@RequestMapping(value = "/getProfileByIbCode", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<IbProfileGetResponseDto> getProfileByIbCode(@RequestBody @Valid IbProfileGetByIbCodeRequest request,
			BindingResult result) {
		logger.info("================= /ib/getProfileByIbCode Start =================");
		logger.info("get profile by ib code request =" + g.toJson(request));
		IbResponseDto<IbProfileGetResponseDto> response = dtoFactory.createResp();
		if (result.hasErrors()) {
			response = hySpringUtil.setDtoErrorFromResult(response, result);
		} else {
			String ibCode = request.getBody().getIb_code();
			IbProfileGetResponseDto body = profileService.getProfileByIbCode(ibCode);
			if (body != null) {
				response.setBody(body);
			} else {
				hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
			}
		}
		logger.info("get profile by ib code response =" + g.toJson(response));
		logger.info("================= /ib/getProfileByIbCode End =================");
		return response;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<IbCreateRespDto> createIb(@RequestBody @Valid IbCreateRequest request, BindingResult result) {
		logger.info("================= /ib/create Start =================");
		logger.info("create ib profile req=" + g.toJson(request));
		IbResponseDto<IbCreateRespDto> resp = null;
		try {
			resp = dtoFactory.createAdminResponse(request.getHeader());
			if (result.hasErrors()) {
				resp = hySpringUtil.setDtoErrorFromResult(resp, result);
			} else {
				List<String> errors = registService.validateCreateIbProfile(request);
				if (errors.size() == 0) {
					IbCreateRespDto body = registService.createIbProfile(request);
					if (body != null) {
						resp.setBody(body);
					} else {
						hySpringUtil.setDtoErrorFromErrorCode(resp, Constants.ERR_COMMON_INTERNAL_ERROR);
					}
				} else {
					for (String errorCode : errors) {
						hySpringUtil.setDtoErrorFromErrorCode(resp, errorCode);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			String defaultMsg = Constants.ERR_COMMON_INTERNAL_ERROR;
			hySpringUtil.setDtoErrorFromErrorCode(resp, defaultMsg);
		}

		logger.info("create ib profile resp=" + g.toJson(resp));
		logger.info("================= /ib/create End =================");
		return resp;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<IbUpdateRespDto> updateIb(@RequestBody @Valid IbUpdateRequest request, BindingResult result) {
		logger.info("================= /ib/update Start =================");
		logger.info("update ib profile req=" + g.toJson(request));
		IbResponseDto<IbUpdateRespDto> resp = null;

		try {
			resp = dtoFactory.createAdminResponse(request.getHeader());
			if (result.hasErrors()) {
				resp = hySpringUtil.setDtoErrorFromResult(resp, result);
			} else {
				List<String> errors = registService.validateUpdateIbProfile(request);
				if (errors.size() == 0) {
					IbUpdateRespDto body = registService.updateIbProfile(request);
					if (body != null) {
						resp.setBody(body);
					} else {
						hySpringUtil.setDtoErrorFromErrorCode(resp, Constants.ERR_COMMON_INTERNAL_ERROR);
					}

				} else {
					for (String errorCode : errors) {
						hySpringUtil.setDtoErrorFromErrorCode(resp, errorCode);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			String defaultMsg = Constants.ERR_COMMON_INTERNAL_ERROR;
			hySpringUtil.setDtoErrorFromErrorCode(resp, defaultMsg);
		}

		logger.info("update ib profile resp=" + g.toJson(resp));
		logger.info("================= /ib/update End =================");
		return resp;
	}
	
	@RequestMapping(value = "/sendWelcomeEmail", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<IbSendWelcomeEmailResponseDto> sendWelcomeEmail(@RequestBody @Valid IbSendWelcomeEmailRequest request, BindingResult result) {
		logger.info("================= /ib/IbSendWelcomeEmail =================");
		logger.info("IB send welcome email request=" + g.toJson(request));
		IbResponseDto<IbSendWelcomeEmailResponseDto> resp = null;

		try {
			String sender = this.getSender(request);
			resp = dtoFactory.createAdminResponse(request.getHeader());
			if (result.hasErrors()) {
				resp = hySpringUtil.setDtoErrorFromResult(resp, result);
			} else {
				boolean generateRandomPassword = false;
				String errorCode = profileService.sendWelcomeEmail(request.getBody().getIb_code(), generateRandomPassword, sender);
				if(errorCode == null){
					IbSendWelcomeEmailResponseDto dto = new IbSendWelcomeEmailResponseDto();
					dto.setIb_code(request.getBody().getIb_code());
					resp.setBody(dto);
				}
				else{
					hySpringUtil.setDtoErrorFromErrorCode(resp, errorCode);
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			String defaultMsg = Constants.ERR_COMMON_INTERNAL_ERROR;
			hySpringUtil.setDtoErrorFromErrorCode(resp, defaultMsg);
		}

		logger.info("IB send welcome email resp=" + g.toJson(resp));
		logger.info("================= /ib/IbSendWelcomeEmail End =================");
		return resp;
	}
	
	@RequestMapping(value = "/sendWelcomeRandomPwdEmail", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<IbSendWelcomeEmailResponseDto> sendWelcomeRandomPwdEmail(@RequestBody @Valid IbSendWelcomeRandomPwdEmailRequest request, BindingResult result) {
		logger.info("================= /ib/IbSendWelcomeRandomPwdEmailRequest =================");
		logger.info("IB send welcome email with random password request=" + g.toJson(request));
		IbResponseDto<IbSendWelcomeEmailResponseDto> resp = null;

		try {
			
			String sender = this.getSender(request);
			resp = dtoFactory.createAdminResponse(request.getHeader());
			if (result.hasErrors()) {
				resp = hySpringUtil.setDtoErrorFromResult(resp, result);
			} else {
				boolean generateRandomPassword = true;
				String errorCode = profileService.sendWelcomeEmail(request.getBody().getIb_code(), generateRandomPassword, sender);
				if(errorCode == null){
					IbSendWelcomeEmailResponseDto dto = new IbSendWelcomeEmailResponseDto();
					dto.setIb_code(request.getBody().getIb_code());
					resp.setBody(dto);
				}
				else{
					hySpringUtil.setDtoErrorFromErrorCode(resp, errorCode);
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			String defaultMsg = Constants.ERR_COMMON_INTERNAL_ERROR;
			hySpringUtil.setDtoErrorFromErrorCode(resp, defaultMsg);
		}

		logger.info("IB send welcome email with random password resp=" + g.toJson(resp));
		logger.info("================= /ib/IbSendWelcomeRandomPwdEmailRequest End =================");
		return resp;
	}
	
	
}
