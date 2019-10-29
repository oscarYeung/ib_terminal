package com.henyep.ib.terminal.entity;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.henyep.ib.terminal.api.global.Constants;

public class InsertMarginOutDto implements Serializable {

	@NotNull(message = "{margin.out.amount.not.blank}")
	private Double amount;
	@NotEmpty(message = "{margin.out.currency.not.blank}")
	@NotBlank(message = "{margin.out.currency.not.blank}")
	private String currency;
	@NotEmpty(message = "{margin.out.id.not.blank}")
	@NotBlank(message = "{margin.out.id.not.blank}")
	private String ib_code;
	
	@NotNull(message = "{InsertMarginOut.max_withdrawal.not.null}")
	private Double max_withdrawal;
	
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getIb_code() {
		return ib_code;
	}

	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}

	public Double getMax_withdrawal() {
		return max_withdrawal;
	}

	public void setMax_withdrawal(Double max_withdrawal) {
		this.max_withdrawal = max_withdrawal;
	} 
	
	
}
