package com.henyep.ib.terminal.server.dto.processor;

import java.math.BigDecimal;
import java.util.Date;

public class IbClientDeficitDto {

	private String ib_code;
	private Date last_month_end;
	private Date current_month_end;
	private BigDecimal last_deficit;
	private BigDecimal current_deficit;

	public String getIb_code() {
		return ib_code;
	}

	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}

	public Date getLast_month_end() {
		return last_month_end;
	}

	public void setLast_month_end(Date last_month_end) {
		this.last_month_end = last_month_end;
	}

	public Date getCurrent_month_end() {
		return current_month_end;
	}

	public void setCurrent_month_end(Date current_month_end) {
		this.current_month_end = current_month_end;
	}

	public BigDecimal getLast_deficit() {
		return last_deficit;
	}

	public void setLast_deficit(BigDecimal last_deficit) {
		this.last_deficit = last_deficit;
	}

	public BigDecimal getCurrent_deficit() {
		return current_deficit;
	}

	public void setCurrent_deficit(BigDecimal current_deficit) {
		this.current_deficit = current_deficit;
	}

	public BigDecimal getDeficit_change() {
		return this.current_deficit.subtract(this.last_deficit);
	}

}
