package com.henyep.ib.terminal.api.dto.request.marginout;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class UpdateMarginOutRequestDto implements Serializable{
	
	private static final long serialVersionUID = -7580905357428581667L;


	@NotNull(message = Constants.ERR_MARGIN_OUT_ID_NOT_BLANK)
	private Integer margin_out_id;

	private String currency;

	private Double amount;
	
	@JsonFormat(pattern=Constants.FORMAT_DATETIME)
	private Date trade_date;
	
	private String category;
	
	private String method;

	private String status;
	
	private String comment;

	
	
	
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Integer getMargin_out_id() {
		return margin_out_id;
	}

	public void setMargin_out_id(Integer margin_out_id) {
		this.margin_out_id = margin_out_id;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
