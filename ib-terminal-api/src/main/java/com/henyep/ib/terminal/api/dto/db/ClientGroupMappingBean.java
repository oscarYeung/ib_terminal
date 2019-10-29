package com.henyep.ib.terminal.api.dto.db;
import java.util.Date;

public class ClientGroupMappingBean{

	private String client_trading_id;
	private String group;
	private Date last_update_time;
	private String last_update_user;
	private Date create_date;

	public void setClient_trading_id(String client_trading_id){
		this.client_trading_id = client_trading_id;
	}
	public String getClient_trading_id(){
		return client_trading_id;
	}
	public void setGroup(String group){
		this.group = group;
	}
	public String getGroup(){
		return group;
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
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder();
		if(client_trading_id != null)
			builder.append("client_trading_id: " + client_trading_id + ", ");
		if(group != null)
			builder.append("group: " + group + ", ");
		if(last_update_time != null)
			builder.append("last_update_time: " + last_update_time.toString() + ", ");
		if(last_update_user != null)
			builder.append("last_update_user: " + last_update_user + ", ");
		return builder.toString();
	}
}
