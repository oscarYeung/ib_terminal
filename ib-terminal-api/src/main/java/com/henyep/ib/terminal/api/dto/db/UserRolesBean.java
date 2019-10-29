package com.henyep.ib.terminal.api.dto.db;
import java.util.Date;

public class UserRolesBean{

	private String user_code;
	private Integer role_id;

	public void setUser_code(String user_code){
		this.user_code = user_code;
	}
	public String getUser_code(){
		return user_code;
	}
	public void setRole_id(Integer role_id){
		this.role_id = role_id;
	}
	public Integer getRole_id(){
		return role_id;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		if(user_code != null)
			builder.append("user_code: " + user_code + ", ");
		builder.append("role_id: " + role_id + ", ");
		return builder.toString();
	}
}
