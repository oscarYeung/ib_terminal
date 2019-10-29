package com.henyep.ib.terminal.api.dto.request.schedule.task;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class BatchCalIbCommissionRequestDto  implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 618328592946529977L;

	private String team_head;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern=Constants.FORMAT_DATETIME)
	private Date start_date;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern=Constants.FORMAT_DATETIME)
	private Date end_date;

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

	public String getTeam_head() {
		return team_head;
	}

	public void setTeam_head(String team_head) {
		this.team_head = team_head;
	}
	
}