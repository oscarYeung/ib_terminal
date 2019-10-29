package com.henyep.ib.terminal.api.dto.request.marginout;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class InsertMarginOutRequestDto {

	@NotNull(message = Constants.ERR_MARGIN_OUT_CATEGORY_NOT_BLANK)
	@NotBlank(message = Constants.ERR_MARGIN_OUT_CATEGORY_NOT_BLANK)
	private String category;
	
	@NotNull(message = Constants.ERR_MARGIN_OUT_METHOD_NOT_BLANK)
	@NotBlank(message = Constants.ERR_MARGIN_OUT_METHOD_NOT_BLANK)
	private String method; 
	
	@NotNull(message = Constants.ERR_MARGIN_OUT_ACCOUNT_NOT_BLANK)
	@NotBlank(message = Constants.ERR_MARGIN_OUT_ACCOUNT_NOT_BLANK)
	private String account;
	
	@NotNull(message = Constants.ERR_COMMON_CURRENCY_NULL)
	@NotBlank(message = Constants.ERR_COMMON_CURRENCY_NULL)
	private String currency; 
	
	@NotNull(message = Constants.ERR_MARGIN_OUT_AMOUNT_NOT_BLANK)
	private Double amount; 
	
	@NotNull(message = Constants.ERR_COMMON_TRADE_DATE_IS_NULL)
	@JsonFormat(pattern=Constants.FORMAT_DATETIME)
	private Date trade_date;

	private String comment;

	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getCurrency() {
		return currency;
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
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	
}
