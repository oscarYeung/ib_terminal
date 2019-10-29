package com.henyep.ib.terminal.api.dto.response.schedule.task;

import java.io.Serializable;

public class RunScheduleTaskResponseDto implements Serializable{

	private static final long serialVersionUID = -9072733446831071909L;
	
	private DataSyncFromSAPResponseDto dataSyncFromSAP;
	private RunCalIBCommTaskResponseDto calIbComm;
	private String comment;
	private String update_ib_account_details_day_open_status;
	private String update_ib_account_details_day_open_time_spend;
	
	
	public DataSyncFromSAPResponseDto getDataSyncFromSAP() {
		return dataSyncFromSAP;
	}
	public void setDataSyncFromSAP(DataSyncFromSAPResponseDto dataSyncFromSAP) {
		this.dataSyncFromSAP = dataSyncFromSAP;
	}
	public RunCalIBCommTaskResponseDto getCalIbComm() {
		return calIbComm;
	}
	public void setCalIbComm(RunCalIBCommTaskResponseDto calIbComm) {
		this.calIbComm = calIbComm;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getUpdate_ib_account_details_day_open_status() {
		return update_ib_account_details_day_open_status;
	}
	public void setUpdate_ib_account_details_day_open_status(String update_ib_account_details_day_open_status) {
		this.update_ib_account_details_day_open_status = update_ib_account_details_day_open_status;
	}
	public String getUpdate_ib_account_details_day_open_time_spend() {
		return update_ib_account_details_day_open_time_spend;
	}
	public void setUpdate_ib_account_details_day_open_time_spend(String update_ib_account_details_day_open_time_spend) {
		this.update_ib_account_details_day_open_time_spend = update_ib_account_details_day_open_time_spend;
	}
	
	
}
