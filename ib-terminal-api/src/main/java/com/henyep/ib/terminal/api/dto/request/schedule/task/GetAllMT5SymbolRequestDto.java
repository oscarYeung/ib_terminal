package com.henyep.ib.terminal.api.dto.request.schedule.task;

import com.henyep.white.label.api.dto.request.BaseRequestBodyDto;

public class GetAllMT5SymbolRequestDto extends BaseRequestBodyDto {

	private static final long serialVersionUID = -7362358129260923160L;

	private String symbolPathPrefix;

	public String getSymbolPathPrefix() {
		return symbolPathPrefix;
	}

	public void setSymbolPathPrefix(String symbolPathPrefix) {
		this.symbolPathPrefix = symbolPathPrefix;
	}
}
