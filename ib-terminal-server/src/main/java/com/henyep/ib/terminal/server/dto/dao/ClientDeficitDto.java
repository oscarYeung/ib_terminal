package com.henyep.ib.terminal.server.dto.dao;

import java.math.BigDecimal;
import java.util.Date;

public class ClientDeficitDto {

	protected Date trade_date;
	protected BigDecimal deficit;

	public Date getTrade_date() {
		return trade_date;
	}

	public void setTrade_date(Date trade_date) {
		this.trade_date = trade_date;
	}

	public BigDecimal getDeficit() {
		return deficit;
	}

	public void setDeficit(BigDecimal deficit) {
		this.deficit = deficit;
	}

}
