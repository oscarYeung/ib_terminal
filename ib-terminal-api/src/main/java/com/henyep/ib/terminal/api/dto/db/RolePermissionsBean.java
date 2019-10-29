package com.henyep.ib.terminal.api.dto.db;
import java.util.Date;

public class RolePermissionsBean{

	private Integer role_id;
	private Integer permission_id;

	public void setRole_id(Integer role_id){
		this.role_id = role_id;
	}
	public Integer getRole_id(){
		return role_id;
	}
	public void setPermission_id(Integer permission_id){
		this.permission_id = permission_id;
	}
	public Integer getPermission_id(){
		return permission_id;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("role_id: " + role_id + ", ");
		builder.append("permission_id: " + permission_id + ", ");
		return builder.toString();
	}
}
