package com.henyep.ib.terminal.api.dto.request.ib.client.map;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.dto.db.IbClientMapBean;
import com.henyep.ib.terminal.api.global.Constants;

public class SwitchIbClientMapRequestDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3610500399877407515L;

	private IbClientMapBean original_ib_client_map;
	
	private String ib_code;
	
	private String package_code;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern=Constants.FORMAT_DATETIME)
	private Date start_date;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern=Constants.FORMAT_DATETIME)
	private Date end_date;

	public IbClientMapBean getOriginal_ib_client_map() {
		return original_ib_client_map;
	}

	public void setOriginal_ib_client_map(IbClientMapBean original_ib_client_map) {
		this.original_ib_client_map = original_ib_client_map;
	}

	public String getIb_code() {
		return ib_code;
	}

	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}

	public String getPackage_code() {
		return package_code;
	}

	public void setPackage_code(String package_code) {
		this.package_code = package_code;
	}

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


	
}
