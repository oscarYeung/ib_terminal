package com.henyep.ib.terminal.api.dto.db;
import java.util.Date;

public class UserPermissionsBean{

	private String user_code;
	private Integer permission_id;

	public void setUser_code(String user_code){
		this.user_code = user_code;
	}
	public String getUser_code(){
		return user_code;
	}
	public void setPermission_id(Integer permission_id){
		this.permission_id = permission_id;
	}
	public Integer getPermission_id(){
		return permission_id;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		if(user_code != null)
			builder.append("user_code: " + user_code + ", ");
		builder.append("permission_id: " + permission_id + ", ");
		return builder.toString();
	}
}
