package com.henyep.ib.terminal.api.dto.request.marginin;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class InsertMarginInRequestDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2103128425794272720L;

	
	@NotNull(message = Constants.ERR_COMMON_CURRENCY_NULL)
	private String currency;
	
	@NotNull(message = Constants.ERR_MARGIN_IN_INSERT_AMOUNT_NULL)
	private Double amount;
	
	@NotNull(message = Constants.ERR_COMMON_TRADE_DATE_IS_NULL)
	@JsonFormat(pattern=Constants.FORMAT_DATETIME)
	private Date trade_date;
	
	@NotNull(message = Constants.ERR_COMMON_TRADE_DATE_IS_NULL)
	private String account;
	
	@NotNull(message = Constants.ERR_MARGIN_IN_CATEGORY_NOT_BLANK)
	private String category;

	private String comment;
	
	
	
	public String getCurrency() {
		return currency;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}


	public Date getTrade_date() {
		return trade_date;
	}


	public void setTrade_date(Date trade_date) {
		this.trade_date = trade_date;
	}


	public String getAccount() {
		return account;
	}


	public void setAccount(String account) {
		this.account = account;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}

	
}
