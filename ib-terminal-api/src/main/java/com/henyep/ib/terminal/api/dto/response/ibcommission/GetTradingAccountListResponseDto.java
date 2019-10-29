package com.henyep.ib.terminal.api.dto.response.ibcommission;

import java.io.Serializable;
import java.util.List;

public class GetTradingAccountListResponseDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2451197094164992214L;

	
	private List<TradeAccountModel> trade_account_list;


	public List<TradeAccountModel> getTrade_account_list() {
		return trade_account_list;
	}


	public void setTrade_account_list(List<TradeAccountModel> trade_account_list) {
		this.trade_account_list = trade_account_list;
	}
	
	
	
	
}
