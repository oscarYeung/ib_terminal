package com.henyep.ib.terminal.server.controller.validator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.henyep.ib.terminal.api.dto.request.BaseDateTimeRequestBodyDto;
import com.henyep.ib.terminal.api.dto.request.BaseIbCodeRequestBodyDto;
import com.henyep.ib.terminal.api.dto.request.BaseRequestHeader;
import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.dto.request.IbRequestDto;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.api.global.PermissionCodes;
import com.henyep.ib.terminal.server.dto.security.SenderDto;
import com.henyep.ib.terminal.server.exception.ServiceException;
import com.henyep.ib.terminal.server.repository.UserIbTreeRepository;
import com.henyep.ib.terminal.server.repository.UserPermissionRepository;
import com.henyep.ib.terminal.server.util.SecurityUtil;

public abstract class AbstractValidator implements Validator {

	@Resource(name = "SecurityUtil")
	private SecurityUtil securityUtil;

	@Resource(name = "config")
	private Properties config;
	@Resource(name = "UserPermissionRepository")
	protected UserPermissionRepository userPermissionRepository;

	@Resource(name = "UserIbTreeRepository")
	protected UserIbTreeRepository userIbTreeRepository;

	protected List<String> supportedChannelList;

	public AbstractValidator() {
		initSupportedChannelList();
	}

	protected void initSupportedChannelList() {
		supportedChannelList = new ArrayList<String>();
		supportedChannelList.add(Constants.CHANNEL_WEB);
	}

	protected String validateBody(Object body) {
		String errorCode = null;
		if (body == null) {
			errorCode = Constants.ERR_COMMON_BODY_IS_NULL;
		}
		return errorCode;
	}

	protected String validateSecretKey(BaseRequestHeader header) {
		String errorCode = null;
		try {
			if (!StringUtils.isBlank(header.getSecretKey())) {
				SenderDto sender = securityUtil.getSenderDto(header.getSecretKey());
				// validate last request time
				Calendar calendar = Calendar.getInstance();
				String timeoutString = config.getProperty("system.api.time.out", "10");
				Integer timeout = Integer.parseInt(timeoutString);
				calendar.add(Calendar.MINUTE, -timeout);
				if (sender.getLast_request_time().compareTo(calendar.getTime()) < 0) {
					errorCode = Constants.ERR_COMMON_API_SESSION_TIME_OUT;
				}
			} else {
				errorCode = Constants.ERR_COMMON_SECRET_KEY_EMPTY;
			}
		} catch (ServiceException e) {
			errorCode = Constants.ERR_COMMON_DECRYPT_SECRET_KEY;
		}

		return errorCode;
	}

	protected String validateChannel(BaseRequestHeader header) {
		if (!supportedChannelList.contains(header.getChannelId())) {
			return Constants.ERR_COMMON_REQUEST_CHANNEL_INVALID;
		}
		return null;
	}

	protected String validateHeader(BaseRequestHeader header) {
		String errorCode = null;
		if (header != null) {
			errorCode = validateChannel(header);
			if (errorCode == null)
				errorCode = validateSecretKey(header);
		} else {
			errorCode = Constants.ERR_COMMON_REQUEST_HEADER_IS_NULL;
		}

		return errorCode;
	}

	private Integer getTypePermissionCode(Integer permissionCode) {
		// The type permission code is a higher level control
		// Code Pattern : X-YYYY-ZZ (e.g. 1000100)
		// X = 1 for All, 0 for nothing
		// YYYY = type (e.g. 0001 = margin in)
		// ZZ = functional permission
		// if X = 1 , ZZ should be 00

		Integer type = (permissionCode / 100);
		type = type * 100;
		Integer typePermissionCode = 1000000 + type;
		return typePermissionCode;
	}

	protected String validateUserPermission(IbAdminRequestDto<?> request, String user_code) {
		String errorCode = null;
		List<Integer> permissionList = userPermissionRepository.getPermission(user_code);
		Integer permissionCode = request.getPermission_code();
		Integer typePermissionCode = getTypePermissionCode(permissionCode);
		if (permissionCode != PermissionCodes.NA) {
			if (permissionList != null) {
				if (!permissionList.contains(permissionCode) && !permissionList.contains(typePermissionCode)) {
					errorCode = Constants.ERR_COMMON_PERMISSION_DENIED;
				}
			} else {
				errorCode = Constants.ERR_COMMON_PERMISSION_DENIED;
			}
		}

		return errorCode;
	}

	protected String validateDateTime(BaseDateTimeRequestBodyDto body) {
		String errorCode = null;
		if (body.getEnd_date() != null && body.getStart_date().compareTo(body.getEnd_date()) > 0) {
			errorCode = Constants.ERR_COMMON_INVALID_DATE_RANGE;
		}
		return errorCode;
	}

	protected String validateUserIbAccessRight(String user_code, String ib_code) {
		boolean valid = userIbTreeRepository.validateUserIbMap(user_code, ib_code);
		if (!valid) {
			return Constants.ERR_COMMON_USER_IB_ACTION_DENIED;
		}
		return null;
	}

	protected String customValidate(Object obj) {
		return null;
	}

	@SuppressWarnings("rawtypes")
	public void validate(Object obj, Errors errs) {
		if (obj != null) {
			String errorCode = null;
			if (obj instanceof IbRequestDto) {
				Object body = ((IbRequestDto) obj).getBody();
				BaseRequestHeader header = ((IbRequestDto) obj).getHeader();
				
				
				errorCode = validateHeader(header);
				
				
				
				
				
				
				if (errorCode == null) {
					
					SenderDto sender = null;				
					try {
						sender = securityUtil.getSenderDto(header.getSecretKey());					
					} catch (ServiceException e) {
						errorCode = e.errorCode;
					}
					
					if (errorCode == null && obj instanceof IbAdminRequestDto) {													
						errorCode = validateUserPermission((IbAdminRequestDto) obj, sender.getSender());						
					}

					if (errorCode == null) {
						errorCode = validateBody(body);

						if (errorCode == null && header.getChannelId().equals(Constants.CHANNEL_ADMIN) && body instanceof BaseIbCodeRequestBodyDto) {
							errorCode = validateUserIbAccessRight(sender.getSender(), ((BaseIbCodeRequestBodyDto) body).getIb_code());
						}

						if (errorCode == null && body instanceof BaseDateTimeRequestBodyDto) {
							errorCode = validateDateTime((BaseDateTimeRequestBodyDto) body);
						}
						if (errorCode == null) {
							errorCode = customValidate(obj);
						}
					}
				}
			}

			if (StringUtils.isNotBlank(errorCode)) {
				errs.rejectValue("body", errorCode, errorCode);
			}
		}
	}
}
