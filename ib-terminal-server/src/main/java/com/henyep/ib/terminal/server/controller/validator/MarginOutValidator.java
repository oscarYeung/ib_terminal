package com.henyep.ib.terminal.server.controller.validator;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.api.dto.request.marginout.AdminCancelMarginOutRequest;
import com.henyep.ib.terminal.api.dto.request.marginout.AdminUpdateMarginOutRequest;
import com.henyep.ib.terminal.api.dto.request.marginout.ApproveMarginOutRequest;
import com.henyep.ib.terminal.api.dto.request.marginout.BatchApproveMarginOutRequest;
import com.henyep.ib.terminal.api.dto.request.marginout.CancelMarginOutRequest;
import com.henyep.ib.terminal.api.dto.request.marginout.ExcelUploadMarginOutRequest;
import com.henyep.ib.terminal.api.dto.request.marginout.GetMarginOutRequest;
import com.henyep.ib.terminal.api.dto.request.marginout.GetMaxWithdrawalRequest;
import com.henyep.ib.terminal.api.dto.request.marginout.InsertMarginOutRequest;
import com.henyep.ib.terminal.api.dto.request.marginout.RejectMarginOutRequest;
import com.henyep.ib.terminal.api.dto.request.marginout.UpdateMarginOutRequest;
import com.henyep.ib.terminal.api.global.Constants;

@Component(value = "MarginOutValidator")
public class MarginOutValidator extends AbstractValidator {
	@Override
	protected void initSupportedChannelList() {
		super.initSupportedChannelList();
		this.supportedChannelList.add(Constants.CHANNEL_ADMIN);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		if (InsertMarginOutRequest.class.isAssignableFrom(clazz) || UpdateMarginOutRequest.class.isAssignableFrom(clazz)
				|| GetMarginOutRequest.class.isAssignableFrom(clazz) || GetMaxWithdrawalRequest.class.isAssignableFrom(clazz)
				|| BatchApproveMarginOutRequest.class.isAssignableFrom(clazz) || CancelMarginOutRequest.class.isAssignableFrom(clazz)
				|| ApproveMarginOutRequest.class.isAssignableFrom(clazz) || RejectMarginOutRequest.class.isAssignableFrom(clazz)
				|| AdminCancelMarginOutRequest.class.isAssignableFrom(clazz) || AdminUpdateMarginOutRequest.class.isAssignableFrom(clazz)
				|| ExcelUploadMarginOutRequest.class.isAssignableFrom(clazz))  {
			return true;
		} else
			return false;
	}

	@Override
	protected String customValidate(Object obj) {
		String errorCode = null;
		if (obj instanceof InsertMarginOutRequest) {
			InsertMarginOutRequest insertMarginOutRequest = (InsertMarginOutRequest) obj;
			errorCode = validateInsertMarginOutRequest(insertMarginOutRequest);

		} else if (obj instanceof UpdateMarginOutRequest) {
			UpdateMarginOutRequest executeMarginOutRequest = (UpdateMarginOutRequest) obj;
			errorCode = validateExecuteMarginOutRequets(executeMarginOutRequest);
		}

		else if (obj instanceof GetMarginOutRequest) {
			GetMarginOutRequest getMarginOutByDateRequest = (GetMarginOutRequest) obj;
			errorCode = validateGetMarginOutByDate(getMarginOutByDateRequest);
		}

		else if (obj instanceof GetMaxWithdrawalRequest) {
			GetMaxWithdrawalRequest getMaxWithdrawalRequest = (GetMaxWithdrawalRequest) obj;
			errorCode = validateGetMaxWithdrawal(getMaxWithdrawalRequest);
		}

		return errorCode;
	}

	// @Override
	// public void validate(Object obj, Errors errs) {
	// if (obj != null) {
	// String errorCode = "";
	// if (obj instanceof InsertMarginOutRequest) {
	// InsertMarginOutRequest insertMarginOutRequest = (InsertMarginOutRequest)
	// obj;
	// errorCode = validateInsertMarginOutRequest(insertMarginOutRequest);
	//
	// } else if (obj instanceof UpdateMarginOutRequest) {
	// UpdateMarginOutRequest executeMarginOutRequest = (UpdateMarginOutRequest)
	// obj;
	// errorCode = validateExecuteMarginOutRequets(executeMarginOutRequest);
	// }
	//
	// else if (obj instanceof GetMarginOutRequest) {
	// GetMarginOutRequest getMarginOutByDateRequest = (GetMarginOutRequest)
	// obj;
	// errorCode = validateGetMarginOutByDate(getMarginOutByDateRequest);
	// }
	//
	// else if (obj instanceof GetMaxWithdrawalRequest) {
	// GetMaxWithdrawalRequest getMaxWithdrawalRequest =
	// (GetMaxWithdrawalRequest) obj;
	// errorCode = validateGetMaxWithdrawal(getMaxWithdrawalRequest);
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

	private String validateInsertMarginOutRequest(InsertMarginOutRequest request) {
		String errorCode = "";
		if (request.getBody() == null) {
			errorCode = "request.body.is.null";
		}
		return errorCode;
	}

	private String validateExecuteMarginOutRequets(UpdateMarginOutRequest request) {
		String errorCode = "";
		if (request.getBody() == null) {
			errorCode = "request.body.is.null";
		}
		return errorCode;
	}

	private String validateGetMarginOutByDate(GetMarginOutRequest request) {
		String errorCode = "";
		if (request.getBody() == null) {
			errorCode = "request.body.is.null";
		} else {
			Date startDate = request.getBody().getStartDate();
			Date endDate = request.getBody().getEndDate();
			if (startDate != null && endDate != null) {
				if (endDate.before(startDate)) {
					errorCode = "common.start.date.after.end.date";
				}
			}

		}
		return errorCode;
	}

	private String validateGetMaxWithdrawal(GetMaxWithdrawalRequest request) {
		String errorCode = "";
		if (request.getBody() == null) {
			errorCode = "request.body.is.null";
		}
		return errorCode;
	}

}
