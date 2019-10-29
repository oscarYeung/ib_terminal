package com.henyep.ib.terminal.api.dto.response.marginout;

import com.henyep.ib.terminal.api.dto.db.MarginOutBean;

public class InvalidMarginOutModel{

	private int line_num;
	
	private String error;
	
	private MarginOutBean margin_out;

	public int getLine_num() {
		return line_num;
	}

	public void setLine_num(int line_num) {
		this.line_num = line_num;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public MarginOutBean getMargin_out() {
		return margin_out;
	}

	public void setMargin_out(MarginOutBean margin_out) {
		this.margin_out = margin_out;
	}
	
	
	
	
}
