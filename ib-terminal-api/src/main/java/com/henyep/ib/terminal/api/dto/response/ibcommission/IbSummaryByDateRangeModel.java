package com.henyep.ib.terminal.api.dto.response.ibcommission;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class IbSummaryByDateRangeModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1790890348733331912L;
	@JsonFormat(pattern = Constants.FORMAT_DATETIME)
	private Date start_date;
	@JsonFormat(pattern = Constants.FORMAT_DATETIME)
	private Date end_date;
	
	private String date_description;
	private Double commission;
	private Double rebate;
	private Double total_lot;
	private Double net_margin_in;
	private Double margin_in;
	private Double margin_out;

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public Double getTotal_lot() {
		return total_lot;
	}

	public void setTotal_lot(Double total_lot) {
		this.total_lot = total_lot;
	}

	public Double getNet_margin_in() {
		return net_margin_in;
	}

	public void setNet_margin_in(Double net_margin_in) {
		this.net_margin_in = net_margin_in;
	}

	public Double getMargin_in() {
		return margin_in;
	}

	public void setMargin_in(Double margin_in) {
		this.margin_in = margin_in;
	}

	public Double getMargin_out() {
		return margin_out;
	}

	public void setMargin_out(Double margin_out) {
		this.margin_out = margin_out;
	}
	
	public String getDate_description() {
		return date_description;
	}

	public void setDate_description(String date_description) {
		this.date_description = date_description;
	}
	
	public Double getRebate() {
		return rebate;
	}

	public void setRebate(Double rebate) {
		this.rebate = rebate;
	}

}