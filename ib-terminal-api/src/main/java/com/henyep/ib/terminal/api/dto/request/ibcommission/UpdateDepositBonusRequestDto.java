package com.henyep.ib.terminal.api.dto.request.ibcommission;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class UpdateDepositBonusRequestDto implements Serializable {

	private static final long serialVersionUID = -5708055917873558502L;

	private String ib_code;
	@NotNull(message = Constants.ERR_COMMON_START_DATE_IS_NULL)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern=Constants.FORMAT_DATETIME)
	private Date trade_date;
	private BigDecimal deposit_bonus;

	public String getIb_code() {
		return ib_code;
	}

	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}

	public Date getTrade_date() {
		return trade_date;
	}

	public void setTrade_date(Date trade_date) {
		this.trade_date = trade_date;
	}

	public BigDecimal getDeposit_bonus() {
		return deposit_bonus;
	}

	public void setDeposit_bonus(BigDecimal deposit_bonus) {
		this.deposit_bonus = deposit_bonus;
	}

}
