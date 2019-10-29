package com.henyep.ib.terminal.server.dto.mt4.response;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.henyep.ib.terminal.server.dto.mt4.model.MT5SymbolRecord;

public class GetAllMT5SymbolsResponse extends BaseResponseModel {

	@SerializedName("Symbols")
	private List<MT5SymbolRecord> symbols;

	public List<MT5SymbolRecord> getSymbols() {
		return symbols;
	}

	public void setSymbols(List<MT5SymbolRecord> symbols) {
		this.symbols = symbols;
	}

}
