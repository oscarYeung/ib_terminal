package com.henyep.ib.terminal.server.controller.validator;

import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.api.dto.request.IbAdminRequestDto;
import com.henyep.ib.terminal.api.dto.request.marginin.ApproveMarginInRequest;
import com.henyep.ib.terminal.api.dto.request.marginin.BatchApproveMarginInRequest;
import com.henyep.ib.terminal.api.dto.request.marginin.CancelMarginInRequest;
import com.henyep.ib.terminal.api.dto.request.marginin.GetMarginInRequest;
import com.henyep.ib.terminal.api.dto.request.marginin.InsertMarginInRequest;
import com.henyep.ib.terminal.api.dto.request.marginin.RejectMarginInRequest;
import com.henyep.ib.terminal.api.dto.request.marginin.UpdateMarginInRequest;
import com.henyep.ib.terminal.api.global.Constants;

@Component(value = "MarginInValidator")
public class MarginInValidator extends AbstractValidator {

	@Override
	protected void initSupportedChannelList() {
		super.initSupportedChannelList();
		this.supportedChannelList.add(Constants.CHANNEL_ADMIN);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		if (UpdateMarginInRequest.class.isAssignableFrom(clazz) || GetMarginInRequest.class.isAssignableFrom(clazz)
				|| InsertMarginInRequest.class.isAssignableFrom(clazz) || BatchApproveMarginInRequest.class.isAssignableFrom(clazz)
				|| ApproveMarginInRequest.class.isAssignableFrom(clazz) || CancelMarginInRequest.class.isAssignableFrom(clazz)
				|| RejectMarginInRequest.class.isAssignableFrom(clazz)
				) {
			return true;
		} else
			return false;
	}

	@Override
	protected String customValidate(Object obj) {
		String errorCode = null;
		if (obj instanceof IbAdminRequestDto<?>) {
			errorCode = validateNotNullRequestBody((IbAdminRequestDto<?>) obj);
			if (obj instanceof GetMarginInRequest) {
				GetMarginInRequest getMarginInRequest = (GetMarginInRequest) obj;
				errorCode = validateGetMarginInRequest(getMarginInRequest);
			}
		}
//			UpdateMarginInRequest executeMarginInRequest = (UpdateMarginInRequest) obj;
//			errorCode = validateExecuteMarginInRequest(executeMarginInRequest);
//		} else if (obj instanceof GetMarginInRequest) {
//			GetMarginInRequest getMarginInRequest = (GetMarginInRequest) obj;
//			errorCode = validateGetMarginInRequest(getMarginInRequest);
//		} 
//		else if (obj instanceof InsertMarginInRequest) {
//			InsertMarginInRequest insertMarginInRequest = (InsertMarginInRequest) obj;
//			errorCode = validateInsertMarginInRequest(insertMarginInRequest);
//		}
		return errorCode;
	}

	// @Override
	// public void validate(Object obj, Errors errs) {
	// if (obj != null) {
	// String errorCode = "";
	// if (obj instanceof UpdateMarginInRequest) {
	// UpdateMarginInRequest executeMarginInRequest = (UpdateMarginInRequest)
	// obj;
	// errorCode = validateExecuteMarginInRequest(executeMarginInRequest);
	// } else if (obj instanceof GetMarginInRequest) {
	// GetMarginInRequest getMarginInRequest = (GetMarginInRequest) obj;
	// errorCode = validateGetMarginInRequest(getMarginInRequest);
	// } else if (obj instanceof InsertMarginInRequest) {
	// InsertMarginInRequest insertMarginInRequest = (InsertMarginInRequest)
	// obj;
	// errorCode = validateInsertMarginInRequest(insertMarginInRequest);
	// }
	//
	// if (StringUtils.isNotBlank(errorCode)) {
	// String defaultMsg = messageSource.getMessage(errorCode, null,
	// Locale.ROOT);
	// errs.rejectValue("body", errorCode, defaultMsg);
	// }
	//
	// }
	// }

	
	
//	private String validateInsertMarginInRequest(InsertMarginInRequest request) {
//		String errorCode = "";
//		if (request.getBody() == null) {
//			errorCode = "request.body.is.null";
//		}
//		return errorCode;
//	}
//
//	private String validateExecuteMarginInRequest(UpdateMarginInRequest request) {
//		String errorCode = "";
//		if (request.getBody() == null) {
//			errorCode = "request.body.is.null";
//		}
//		return errorCode;
//	}
	
	private String validateNotNullRequestBody(IbAdminRequestDto<?> request) {
		String errorCode = "";
		if (request.getBody() == null) {
			errorCode = "request.body.is.null";
		}
		return errorCode;
	}

	private String validateGetMarginInRequest(GetMarginInRequest request) {
		String errorCode = "";
		if (request.getBody() == null) {
			errorCode = "request.body.is.null";
		} else if (request.getBody().getStartDate() != null && request.getBody().getEndDate() != null) {
			if (request.getBody().getStartDate().after(request.getBody().getEndDate())) {
				errorCode = "common.start.date.after.end.date";
			}
		}

		return errorCode;
	}

}
