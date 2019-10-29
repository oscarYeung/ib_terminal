package com.henyep.ib.terminal.api.dto.response.trade;

import java.io.Serializable;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.ClientTradeDetailsBean;

public class GetClientTradeHistoryResponseDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1827040739900541515L;

	
	private Double total_pl;
	private Double total_commission;
	private Double total_swap;
	private List<ClientTradeDetailsBean> trades;


	public Double getTotal_pl() {
		return total_pl;
	}


	public void setTotal_pl(Double total_pl) {
		this.total_pl = total_pl;
	}


	public Double getTotal_commission() {
		return total_commission;
	}


	public void setTotal_commission(Double total_commission) {
		this.total_commission = total_commission;
	}


	public Double getTotal_swap() {
		return total_swap;
	}


	public void setTotal_swap(Double total_swap) {
		this.total_swap = total_swap;
	}


	public List<ClientTradeDetailsBean> getTrades() {
		return trades;
	}


	public void setTrades(List<ClientTradeDetailsBean> trades) {
		this.trades = trades;
	}
}
