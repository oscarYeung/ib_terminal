package com.henyep.ib.terminal.server.dto.mt4.response;

import com.google.gson.annotations.SerializedName;

public class BaseResponseModel {
	@SerializedName("Mt4ReturnCode")
	private Integer mt4ReturnCode;
	@SerializedName("ReturnMessage")
	private String returnMessage;
	@SerializedName("IsSuccess")
	private boolean isSuccess;
	@SerializedName("ServiceErrorCode")
	private String serviceErrorCode;

	public Integer getMt4ReturnCode() {
		return mt4ReturnCode;
	}

	public void setMt4ReturnCode(Integer mt4ReturnCode) {
		this.mt4ReturnCode = mt4ReturnCode;
	}

	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getServiceErrorCode() {
		return serviceErrorCode;
	}

	public void setServiceErrorCode(String serviceErrorCode) {
		this.serviceErrorCode = serviceErrorCode;
	}

}
