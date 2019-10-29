package com.henyep.ib.terminal.server.dto.mt4.response;

import com.google.gson.annotations.SerializedName;
import com.henyep.ib.terminal.server.dto.mt4.model.TradeTransInfo;

public class DepositResponse extends BaseResponseModel {
	@SerializedName("TradeTransInfo")
	private TradeTransInfo tradeTransInfo;

	public TradeTransInfo getTradeTransInfo() {
		return tradeTransInfo;
	}

	public void setTradeTransInfo(TradeTransInfo tradeTransInfo) {
		this.tradeTransInfo = tradeTransInfo;
	}

}
