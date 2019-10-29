package com.henyep.ib.terminal.api.dto.db;
import java.util.Date;

public class ExternalMt4ClientBean{

	private Integer client_account;
	private String mt4_group;
	private String currency;
	private Date last_update_time;
	private String last_update_user;

	public void setClient_account(Integer client_account){
		this.client_account = client_account;
	}
	public Integer getClient_account(){
		return client_account;
	}
	public void setMt4_group(String mt4_group){
		this.mt4_group = mt4_group;
	}
	public String getMt4_group(){
		return mt4_group;
	}
	public void setCurrency(String currency){
		this.currency = currency;
	}
	public String getCurrency(){
		return currency;
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
		builder.append("client_account: " + client_account + ", ");
		if(mt4_group != null)
			builder.append("mt4_group: " + mt4_group + ", ");
		if(currency != null)
			builder.append("currency: " + currency + ", ");
		if(last_update_time != null)
			builder.append("last_update_time: " + last_update_time.toString() + ", ");
		if(last_update_user != null)
			builder.append("last_update_user: " + last_update_user + ", ");
		return builder.toString();
	}
}
