package com.henyep.ib.terminal.api.dto.db;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class IbClientMapBean{

	private String ib_code;
	private String client_code;
	private String client_package_code;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern=Constants.FORMAT_DATETIME)
	private Date start_date;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern=Constants.FORMAT_DATETIME)
	private Date end_date;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern=Constants.FORMAT_DATETIME)
	private Date last_update_time;
	private String last_update_user;

	public void setIb_code(String ib_code){
		this.ib_code = ib_code;
	}
	public String getIb_code(){
		return ib_code;
	}
	public void setClient_code(String client_code){
		this.client_code = client_code;
	}
	public String getClient_code(){
		return client_code;
	}
	public void setClient_package_code(String client_package_code){
		this.client_package_code = client_package_code;
	}
	public String getClient_package_code(){
		return client_package_code;
	}
	public void setStart_date(Date start_date){
		this.start_date = start_date;
	}
	public Date getStart_date(){
		return start_date;
	}
	public void setEnd_date(Date end_date){
		this.end_date = end_date;
	}
	public Date getEnd_date(){
		return end_date;
	}
	public void setLast_update_time(Date last_update_time){
		this.last_update_time = last_update_time;
	}
	public Date getLast_update_time(){
		return last_update_time;
	}
	public void setLast_update_user(String last_update_user){
		this.last_update_user = last_update_user;
	}
	public String getLast_update_user(){
		return last_update_user;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		if(ib_code != null)
			builder.append("ib_code: " + ib_code + ", ");
		if(client_code != null)
			builder.append("client_code: " + client_code + ", ");
		if(client_package_code != null)
			builder.append("client_package_code: " + client_package_code + ", ");
		if(start_date != null)
			builder.append("start_date: " + start_date.toString() + ", ");
		if(end_date != null)
			builder.append("end_date: " + end_date.toString() + ", ");
		if(last_update_time != null)
			builder.append("last_update_time: " + last_update_time.toString() + ", ");
		if(last_update_user != null)
			builder.append("last_update_user: " + last_update_user + ", ");
		return builder.toString();
	}
}
