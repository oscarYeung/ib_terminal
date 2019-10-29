package com.henyep.ib.terminal.server.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.henyep.ib.terminal.api.dto.request.adminUser.AddUserRequest;
import com.henyep.ib.terminal.api.dto.request.adminUser.ResetSystemCacheRequest;
import com.henyep.ib.terminal.api.dto.request.adminUser.UpdateUserRequest;
import com.henyep.ib.terminal.api.dto.request.adminUser.UserChangePasswordRequest;
import com.henyep.ib.terminal.api.dto.request.adminUser.UserLoginRequest;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.adminUser.AddUserResponseDto;
import com.henyep.ib.terminal.api.dto.response.adminUser.ResetSystemCacheResponseDto;
import com.henyep.ib.terminal.api.dto.response.adminUser.UpdateUserResponseDto;
import com.henyep.ib.terminal.api.dto.response.adminUser.UserChangePasswordResponseDto;
import com.henyep.ib.terminal.api.dto.response.adminUser.UserLoginResponseDto;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.server.controller.validator.AdminUserValidator;
import com.henyep.ib.terminal.server.repository.UserIbTreeRepository;
import com.henyep.ib.terminal.server.repository.UserPermissionRepository;
import com.henyep.ib.terminal.server.service.UserPermissionService;
import com.henyep.ib.terminal.server.service.UserService;
import com.henyep.ib.terminal.server.util.SecurityUtil;

@Controller
@RequestMapping("/adminUser")
public class UserController extends AbstractController {

	@Autowired
	public UserController(AdminUserValidator adminUserValidator) {
		super(adminUserValidator);
	}

	@Resource(name = "UserService")
	private UserService userService;

	@Resource(name = "UserPermissionService")
	private UserPermissionService userPermissionService;

	@Resource(name = "UserPermissionRepository")
	protected UserPermissionRepository userPermissionRepository;

	@Resource(name = "UserIbTreeRepository")
	protected UserIbTreeRepository userIbTreeRepository;

	
	@Resource(name = "SecurityUtil")
	private SecurityUtil securityUtil;

	@RequestMapping(value = "/resetSystemCache", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<ResetSystemCacheResponseDto> resetSystemCache(@RequestBody @Valid ResetSystemCacheRequest request, BindingResult result) {
		logger.info("================= /adminUser/resetSystemCache Start =================");
		IbResponseDto<ResetSystemCacheResponseDto> response = null;
		try {
			logger.info("reset system cache request =" + g.toJson(request));
			response = dtoFactory.createAdminResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				ResetSystemCacheResponseDto body = new ResetSystemCacheResponseDto();
				body.setType(request.getBody().getType());
				
				if(request.getBody().getType().equals(Constants.CACHE_TYPE_PERMISSION)){
					userPermissionRepository.clearMap();					
				}else if(request.getBody().getType().equals(Constants.CACHE_TYPE_IB_TREE)){
					userIbTreeRepository.clearMap();					
				}				
				body.setSuccess(true);
				response.setBody(body);				
			}
		}
		catch(Exception e) {
			Logger.getRootLogger().fatal(e, e);
			logger.error(e,e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		logger.info("reset system cache response =" + g.toJson(response));
		logger.info("================= /adminUser/resetSystemCache End =================");
		return response;	
	}
	
	@RequestMapping(value = "/loginUser", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<UserLoginResponseDto> login(@RequestBody @Valid UserLoginRequest request, BindingResult result) {
		logger.info("================= /adminUser/loginUser Start =================");
		IbResponseDto<UserLoginResponseDto> response = null;
		try {
			logger.info("login user request =" + g.toJson(request));
			response = dtoFactory.createAdminResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				String errorCode = userService.validateUserPassword(request);

				if (errorCode == "") {

					UserLoginResponseDto body = userService.loginUser(request);
					if (body != null) {
						// Get Permission List
						List<Integer> permissionList = userPermissionRepository.getPermission(request.getBody().getUserCode());
						body.setPermissionList(permissionList);
						response.setBody(body);
						String key = securityUtil.getSecretKey(request.getBody().getUserCode());
						response.getHeader().setSecretKey(key);
					} else {
						String defaultMsg = Constants.ERR_COMMON_INTERNAL_ERROR;
						hySpringUtil.setDtoErrorFromErrorCode(response, defaultMsg);
					}
				} else {
					hySpringUtil.setDtoErrorFromErrorCode(response, errorCode);
				}
			}
		} catch (Exception e) {
			Logger.getRootLogger().fatal(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		logger.info("login user response =" + g.toJson(response));
		logger.info("================= /adminUser/loginUser End =================");
		return response;
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<UserChangePasswordResponseDto> changePassword(@RequestBody @Valid UserChangePasswordRequest request,
			BindingResult result) {
		logger.info("================= /adminUser/changePassword Start =================");
		IbResponseDto<UserChangePasswordResponseDto> response = null;
		try {
			logger.info("change user password =" + g.toJson(request));
			response = dtoFactory.createAdminResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				String errorCode = userService.validateUserPassword(request.getBody().getUserCode(), request.getBody().getOldPassword());

				if (errorCode == "") {

					UserChangePasswordResponseDto body = userService.changePassword(request);
					if (body != null) {
						response.setBody(body);
					} else {
						String defaultMsg = Constants.ERR_COMMON_INTERNAL_ERROR;
						hySpringUtil.setDtoErrorFromErrorCode(response, defaultMsg);
					}
				} else {
					hySpringUtil.setDtoErrorFromErrorCode(response, errorCode);
				}
			}
		} catch (Exception e) {
			Logger.getRootLogger().fatal(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		logger.info("change user password =" + g.toJson(response));
		logger.info("================= /adminUser/changePassword End =================");
		return response;
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<AddUserResponseDto> addUser(@RequestBody @Valid AddUserRequest request, BindingResult result) {
		logger.info("================= /adminUser/addUser Start =================");
		IbResponseDto<AddUserResponseDto> response = null;
		try {
			logger.info("add user =" + g.toJson(request));
			response = dtoFactory.createAdminResponse(request.getHeader());

			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				List<String> errorCodes = userService.validateAddUser(request);

				if (errorCodes.size() == 0) {

					AddUserResponseDto body = userService.addUser(request);
					if (body != null) {
						response.setBody(body);
					} else {
						String defaultMsg = Constants.ERR_COMMON_INTERNAL_ERROR;
						hySpringUtil.setDtoErrorFromErrorCode(response, defaultMsg);
					}
				} else {
					for (String errorCode : errorCodes) {
						hySpringUtil.setDtoErrorFromErrorCode(response, errorCode);
					}
				}
			}
		} catch (Exception e) {
			Logger.getRootLogger().fatal(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		logger.info("add user =" + g.toJson(response));
		logger.info("================= /adminUser/addUser End =================");
		return response;
	}

	@RequestMapping(value = "/updateUser", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<UpdateUserResponseDto> updateUser(@RequestBody @Valid UpdateUserRequest request, BindingResult result) {
		logger.info("================= /adminUser/updateUser Start =================");
		IbResponseDto<UpdateUserResponseDto> response = null;
		try {
			logger.info("update user =" + g.toJson(request));
			response = dtoFactory.createAdminResponse(request.getHeader());

			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {

				List<String> errorCodes = userService.validateUpdateUser(request);

				if (errorCodes.size() == 0) {

					UpdateUserResponseDto body = userService.updateUser(request);
					if (body != null) {
						response.setBody(body);
					} else {
						String defaultMsg = Constants.ERR_COMMON_INTERNAL_ERROR;
						hySpringUtil.setDtoErrorFromErrorCode(response, defaultMsg);
					}
				} else {
					for (String errorCode : errorCodes) {
						hySpringUtil.setDtoErrorFromErrorCode(response, errorCode);
					}
				}
			}
		} catch (Exception e) {
			Logger.getRootLogger().fatal(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		logger.info("update user =" + g.toJson(response));
		logger.info("================= /adminUser/updateUser End =================");
		return response;
	}

}
