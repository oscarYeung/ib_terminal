package com.henyep.ib.terminal.api.dto.response.schedule.task;

import java.io.Serializable;

public class RunCalIBCommTaskResponseDto implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5686136062738170776L;
	
	private String update_calculate_ib_commission_status;
	private String update_calculate_ib_commission_time_spend;
	

	public String getUpdate_calculate_ib_commission_status() {
		return update_calculate_ib_commission_status;
	}
	public void setUpdate_calculate_ib_commission_status(String update_calculate_ib_commission_status) {
		this.update_calculate_ib_commission_status = update_calculate_ib_commission_status;
	}
	public String getUpdate_calculate_ib_commission_time_spend() {
		return update_calculate_ib_commission_time_spend;
	}
	public void setUpdate_calculate_ib_commission_time_spend(String update_calculate_ib_commission_time_spend) {
		this.update_calculate_ib_commission_time_spend = update_calculate_ib_commission_time_spend;
	}
}
