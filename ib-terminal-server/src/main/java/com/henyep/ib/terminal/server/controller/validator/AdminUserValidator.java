package com.henyep.ib.terminal.server.controller.validator;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.henyep.ib.terminal.api.dto.db.UserBean;
import com.henyep.ib.terminal.api.dto.request.adminUser.AddUserRequest;
import com.henyep.ib.terminal.api.dto.request.adminUser.ResetSystemCacheRequest;
import com.henyep.ib.terminal.api.dto.request.adminUser.UpdateUserRequest;
import com.henyep.ib.terminal.api.dto.request.adminUser.UserChangePasswordRequest;
import com.henyep.ib.terminal.api.dto.request.adminUser.UserLoginRequest;
import com.henyep.ib.terminal.api.dto.request.rebate.GetRebateByIbCodeWithDateRequest;
import com.henyep.ib.terminal.api.dto.request.rebate.UpdateRebateRequest;
import com.henyep.ib.terminal.api.global.Constants;

@Component
public class AdminUserValidator extends AbstractValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		if (AddUserRequest.class.isAssignableFrom(clazz) || UpdateUserRequest.class.isAssignableFrom(clazz)
				|| UserChangePasswordRequest.class.isAssignableFrom(clazz) || UserLoginRequest.class.isAssignableFrom(clazz)
				|| ResetSystemCacheRequest.class.isAssignableFrom(clazz))
			return true;
		else
			return false;
	}

	@Override
	protected void initSupportedChannelList() {
		this.supportedChannelList = new ArrayList<String>();
		this.supportedChannelList.add(Constants.CHANNEL_ADMIN);
	}

	@Override
	protected String customValidate(Object obj) {
		String errorCode = null;
		if (obj != null) {
			if (obj instanceof GetRebateByIbCodeWithDateRequest) {
				errorCode = validateAddUser((AddUserRequest) obj);
			} else if (obj instanceof UpdateRebateRequest) {
				errorCode = validateUpdateUser((UpdateUserRequest) obj);
			} else if (obj instanceof UpdateRebateRequest) {
				errorCode = validateUserChangePassword((UserChangePasswordRequest) obj);
			} else if (obj instanceof UpdateRebateRequest) {
				errorCode = validateUserLogin((UserLoginRequest) obj);
			}
		}
		return errorCode;
	}

	@Override
	public void validate(Object obj, Errors errs) {

		if (obj != null) {
			// Skip reset password check
			if (!(obj instanceof UserLoginRequest)) {
				super.validate(obj, errs);
			}
		}
	}

	// @Override
	// public void validate(Object obj, Errors errs) {
	// if (obj != null) {
	// String errorCode = "";
	// if (obj instanceof GetRebateByIbCodeWithDateRequest) {
	// errorCode = validateAddUser((AddUserRequest) obj);
	// } else if (obj instanceof InsertUpdateRebateRequest) {
	// errorCode = validateUpdateUser((UpdateUserRequest) obj);
	// } else if (obj instanceof InsertUpdateRebateRequest) {
	// errorCode = validateUserChangePassword((UserChangePasswordRequest) obj);
	// } else if (obj instanceof InsertUpdateRebateRequest) {
	// errorCode = validateUserLogin((UserLoginRequest) obj);
	// }
	// if (StringUtils.isNotBlank(errorCode)) {
	// String defaultMsg = messageSource.getMessage(errorCode, null,
	// Locale.ROOT);
	// errs.rejectValue("body", errorCode, defaultMsg);
	// }
	// }
	//
	// }

	private String validateAddUser(AddUserRequest request) {
		String errorCode = "";
		if (request.getBody() == null) {
			errorCode = Constants.ERR_COMMON_BODY_IS_NULL;

		} else {
			UserBean newUserBean = request.getBody().getNewUser();
			errorCode = validateUserBean(newUserBean);
		}
		return errorCode;
	}

	private String validateUpdateUser(UpdateUserRequest request) {
		String errorCode = "";
		if (request.getBody() == null) {
			errorCode = Constants.ERR_COMMON_BODY_IS_NULL;

		} else {
			UserBean newUserBean = request.getBody().getUpdateUserBean();
			errorCode = validateUserBean(newUserBean);
		}
		return errorCode;
	}

	private String validateUserChangePassword(UserChangePasswordRequest request) {
		String errorCode = "";
		if (request.getBody().getOldPassword().equals(request.getBody().getNewPassword())) {
			errorCode = Constants.ERR_USER_NEW_PASSWORD_NO_CHANGE;

		}
		return errorCode;
	}

	private String validateUserLogin(UserLoginRequest request) {
		String errorCode = "";

		return errorCode;
	}

	private String validateUserBean(UserBean userBean) {
		String errorCode = "";

		if (userBean == null) {
			errorCode = Constants.ERR_USER_NULL;
		} else if (StringUtils.isBlank(userBean.getUser_code())) {
			errorCode = Constants.ERR_USER_CODE_BLANK;

		} else if (StringUtils.isBlank(userBean.getPassword())) {
			errorCode = Constants.ERR_USER_PASSWORD_BLANK;

		} else if (StringUtils.isBlank(userBean.getStatus())) {
			errorCode = Constants.ERR_USER_STATUS_BLANK;

		} else if (!userBean.getStatus().equals(Constants.USER_STATUS_ACTIVE) && !userBean.getStatus().equals(Constants.USER_STATUS_INACTIVE)) {
			errorCode = Constants.ERR_USER_STATUS_INCORRECT;

		}

		return errorCode;
	}

}
