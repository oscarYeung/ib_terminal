package com.henyep.ib.terminal.server.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.henyep.ib.terminal.api.dto.request.ib.IbCreateRequest;
import com.henyep.ib.terminal.api.dto.request.ib.IbEditRequest;
import com.henyep.ib.terminal.api.dto.request.ib.IbLoginRequest;
import com.henyep.ib.terminal.api.dto.request.ib.IbSendWelcomeEmailRequest;
import com.henyep.ib.terminal.api.dto.request.ib.IbSendWelcomeRandomPwdEmailRequest;
import com.henyep.ib.terminal.api.dto.request.ib.IbUpdateRequest;
import com.henyep.ib.terminal.api.dto.request.user.IbProfileGetByIbCodeRequest;
import com.henyep.ib.terminal.api.global.Constants;

@Component
public class IbValidator extends AbstractValidator {

	// @Override
	// public boolean supports(Class<?> clazz) {
	// // TODO Auto-generated method stub
	// return clazz.equals(IbRequestDto.class);
	// }

	@Override
	public boolean supports(Class<?> clazz) {
		if (IbLoginRequest.class.isAssignableFrom(clazz) || IbProfileGetByIbCodeRequest.class.isAssignableFrom(clazz)
				|| IbUpdateRequest.class.isAssignableFrom(clazz) || IbCreateRequest.class.isAssignableFrom(clazz)
				|| IbEditRequest.class.isAssignableFrom(clazz) || IbSendWelcomeEmailRequest.class.isAssignableFrom(clazz)
				|| IbSendWelcomeRandomPwdEmailRequest.class.isAssignableFrom(clazz))
			return true;
		else
			return false;
	}
	
	protected void initSupportedChannelList() {
		super.initSupportedChannelList();
		this.supportedChannelList.add(Constants.CHANNEL_ADMIN);
		this.supportedChannelList.add(Constants.CHANNEL_WEB);
	}

	// @Override
	// public void validate(Object target, Errors errors) {
	// @SuppressWarnings("unchecked")
	// IbRequestDto<IbLoginRequestDto> loginReqDto =
	// (IbRequestDto<IbLoginRequestDto>) target;
	// BaseRequestHeader header = loginReqDto.getHeader();
	// if (!header.getChannelId().equals(ConstantDefineCode.CHANNEL_WEB) &&
	// !header.getChannelId().equals(ConstantDefineCode.CHANNEL_MOBILE)) {
	// errors.reject("login.error.incorrect.channel",
	// "login.error.incorrect.channel");
	// }
	// if
	// (!header.getMessageType().equals(ConstantDefineCode.MESSAGE_TYPE_IB_LOGIN))
	// {
	// errors.reject("login.error.incorrect.messagetype",
	// "login.error.incorrect.messagetype");
	// }
	//
	// }

	@Override
	public void validate(Object obj, Errors errs) {

		if (obj != null) {
			// Skip reset password check
			if (!(obj instanceof IbLoginRequest)) {
				super.validate(obj, errs);
			}
		}
	}
	// public IbResponseDto<IbLoginResponseDto> initLoginResp(DtoFactory
	// dtoFactory) {
	// IbResponseDto<IbLoginResponseDto> loginResp = dtoFactory.createResp();
	// loginResp.setBody(new IbLoginResponseDto());
	// loginResp.getHeader().setMessageType(ConstantDefineCode.MESSAGE_TYPE_IB_FORGET_PASSWORD);
	// loginResp.getHeader().setChannelId(ConstantDefineCode.CHANNEL_WEB);
	// loginResp.getHeader().setMessageDate(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE_TIME,
	// new Date()));
	// return loginResp;
	// }

	// public void validateIbProfie(IbProfileBean ibBean, Errors errors) {
	// try {
	// if (ibBean.getStatus().equals(ConstantDefineCode.IB_USER_STATUS_CLOSE)) {
	// errors.reject("login.error.user.is.closed",
	// "login.error.user.is.closed");
	// }
	// } catch (Exception e) {
	// errors.reject("login.error.user.unknow.exception",
	// "login.error.user.unknow.exception");
	// }
	//
	// }

}
