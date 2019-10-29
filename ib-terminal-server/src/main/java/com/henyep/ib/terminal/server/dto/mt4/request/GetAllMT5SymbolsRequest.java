package com.henyep.ib.terminal.server.dto.mt4.request;

import com.google.gson.annotations.SerializedName;

public class GetAllMT5SymbolsRequest extends BaseRequestModel{
	@SerializedName("SymbolPathPrefix")
	private String symbolPathPrefix;

	public String getSymbolPathPrefix() {
		return symbolPathPrefix;
	}

	public void setSymbolPathPrefix(String symbolPathPrefix) {
		this.symbolPathPrefix = symbolPathPrefix;
	}
}
