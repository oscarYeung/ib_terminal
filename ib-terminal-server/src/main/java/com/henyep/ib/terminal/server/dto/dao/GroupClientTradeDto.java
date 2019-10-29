package com.henyep.ib.terminal.server.dto.dao;

import java.math.BigDecimal;

public class GroupClientTradeDto {
	private String group_code;
	private String client_code;
	private String symbol;
	private BigDecimal total_lot;

	public String getGroup_code() {
		return group_code;
	}

	public void setGroup_code(String group_code) {
		this.group_code = group_code;
	}

	public String getClient_code() {
		return client_code;
	}

	public void setClient_code(String client_code) {
		this.client_code = client_code;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public BigDecimal getTotal_lot() {
		return total_lot;
	}

	public void setTotal_lot(BigDecimal total_lot) {
		this.total_lot = total_lot;
	}

}
