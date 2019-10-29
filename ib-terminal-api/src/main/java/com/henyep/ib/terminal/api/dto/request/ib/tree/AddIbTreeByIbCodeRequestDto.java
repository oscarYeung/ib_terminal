package com.henyep.ib.terminal.api.dto.request.ib.tree;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class AddIbTreeByIbCodeRequestDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7347314403009083748L;
	
	private String parent_ib_code;
	private String target_ib_code;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern=Constants.FORMAT_DATETIME)
	private Date start_date;
	
	public String getParent_ib_code() {
		return parent_ib_code;
	}
	public void setParent_ib_code(String parent_ib_code) {
		this.parent_ib_code = parent_ib_code;
	}
	public String getTarget_ib_code() {
		return target_ib_code;
	}
	public void setTarget_ib_code(String target_ib_code) {
		this.target_ib_code = target_ib_code;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	
	
}
