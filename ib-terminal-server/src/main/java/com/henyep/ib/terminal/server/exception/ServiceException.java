package com.henyep.ib.terminal.server.exception;

public class ServiceException extends Exception {

	private static final long serialVersionUID = -3729820346444888742L;

	public String errorCode;

	public ServiceException(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
