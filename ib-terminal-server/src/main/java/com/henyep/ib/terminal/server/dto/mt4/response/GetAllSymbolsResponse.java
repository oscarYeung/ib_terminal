package com.henyep.ib.terminal.server.dto.mt4.response;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.henyep.ib.terminal.server.dto.mt4.model.SymbolRecord;

public class GetAllSymbolsResponse extends BaseResponseModel{

	@SerializedName("Symbols")
	private List<SymbolRecord> symbols;

	public List<SymbolRecord> getSymbols() {
		return symbols;
	}

	public void setSymbols(List<SymbolRecord> symbols) {
		this.symbols = symbols;
	}
	
	
}
