package com.henyep.ib.terminal.server.component;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.server.global.Constants;

/**
 * Spring helper
 *
 * @author ShenZhong
 * @date 2016年8月18日
 */
@Component(value = "sz_HySpringUtil")
public class HySpringUtil {
	@Resource(name = "messageSource")
	private MessageSource messageSource;
	protected final transient Log logger = LogFactory.getLog(getClass());

	public <T> IbResponseDto<T> setDtoErrorWithCode(IbResponseDto<T> regResp, String errorCode) {
		Map<String, String> errMap = regResp.getHeader().getErrorMap();
		String[] errMsg = messageSource.getMessage(errorCode, null, Locale.ROOT).split("~");
		String code = errMsg[0];
		String msg = errMsg[1];
		errMap.put(code, msg);
		regResp.getHeader().setStatus(Constants.RES_STATUS_FAIL);
		regResp.getHeader().setSecretKey("");
		return regResp;
	}

	// public <T> IbResponseDto<T> setDtoErrorFromResult(IbResponseDto<T>
	// regResp, BindingResult result) {
	// if (result.hasErrors()) {
	// Map<String, String> errMap = regResp.getHeader().getErrorMap();
	// errMap.remove("001");
	// List<ObjectError> errors = result.getAllErrors();
	// for (ObjectError err : errors) {
	// String[] errMsg = err.getDefaultMessage().split("~");
	// String code = errMsg[0];
	// String msg = errMsg[1];
	// errMap.put(code, msg);
	// }
	// }
	// return regResp;
	// }

	public <T> IbResponseDto<T> setDtoErrorFromResult(IbResponseDto<T> regResp, BindingResult result, Object[] args) {
		if (result.hasErrors()) {
			Map<String, String> errMap = regResp.getHeader().getErrorMap();
			List<ObjectError> errors = result.getAllErrors();
			for (ObjectError err : errors) {
				String code = err.getDefaultMessage();
				String msg = messageSource.getMessage(code, args, Locale.ROOT);
				errMap.put(code, msg);
			}
		}
		regResp.getHeader().setStatus(Constants.RES_STATUS_FAIL);
		regResp.getHeader().setSecretKey("");
		return regResp;
	}

	public <T> IbResponseDto<T> setDtoErrorFromResult(IbResponseDto<T> regResp, BindingResult result) {
		return setDtoErrorFromResult(regResp, result, null);
	}

	public <T> IbResponseDto<T> setDtoErrorFromErrorCode(IbResponseDto<T> response, String errorCode) {
		return setDtoErrorFromErrorCode(response, errorCode, "");
	}

	public <T> IbResponseDto<T> setDtoErrorFromErrorCode(IbResponseDto<T> response, String errorCode, String complementraryMsg) {
		Map<String, String> errMap = response.getHeader().getErrorMap();
		try {
			String msg = messageSource.getMessage(errorCode, null, Locale.ROOT) + complementraryMsg;
			errMap.put(errorCode, msg);
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		response.getHeader().setStatus(Constants.RES_STATUS_FAIL);
		response.getHeader().setSecretKey("");
		return response;
	}

}
