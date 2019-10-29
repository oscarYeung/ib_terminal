package com.henyep.ib.terminal.api.dto.request.marginin;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class UpdateMarginInRequestDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8636622423974250043L;
	
	@NotNull(message = Constants.ERR_MARGIN_IN_NULL_ID_INPUT)
	private Integer margin_in_id;

	private String currency;
	
	private Double amount;
	
	@JsonFormat(pattern=Constants.FORMAT_DATETIME)
	private Date trade_date;
	
	private String category;

	private String status;
	
	private String comment;
	
	
	
	

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


	public Integer getMargin_in_id() {
		return margin_in_id;
	}

	public void setMargin_in_id(Integer marginInId) {
		this.margin_in_id = marginInId;
	}
}
