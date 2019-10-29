package com.henyep.ib.terminal.api.dto.db;
import java.util.Date;

public class SystemParamsBean{

	private String name;
	private String description;
	private String value;
	private String last_update_time;
	private String last_update_user;

	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
	public void setDescription(String description){
		this.description = description;
	}
	public String getDescription(){
		return description;
	}
	public void setValue(String value){
		this.value = value;
	}
	public String getValue(){
		return value;
	}
	public void setLast_update_time(String last_update_time){
		this.last_update_time = last_update_time;
	}
	public String getLast_update_time(){
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
		if(name != null)
			builder.append("name: " + name + ", ");
		if(description != null)
			builder.append("description: " + description + ", ");
		if(value != null)
			builder.append("value: " + value + ", ");
		if(last_update_time != null)
			builder.append("last_update_time: " + last_update_time + ", ");
		if(last_update_user != null)
			builder.append("last_update_user: " + last_update_user + ", ");
		return builder.toString();
	}
}
