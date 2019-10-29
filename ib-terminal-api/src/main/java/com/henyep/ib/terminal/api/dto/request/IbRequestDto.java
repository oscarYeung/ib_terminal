package com.henyep.ib.terminal.api.dto.request;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class IbRequestDto<T> implements Serializable {
	private static final long serialVersionUID = -8945262131606982344L;
	protected BaseRequestHeader header;
	
	@NotNull(message = "{dto.request.body.not.null}")
	@Valid
	protected T body;

	public BaseRequestHeader getHeader() {
		return header;
	}

	public void setHeader(BaseRequestHeader header) {
		this.header = header;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}		
	
	
}
