package com.henyep.ib.terminal.api.dto.db;
import java.util.Date;

public class RoleBean{

	private Integer id;
	private String description;

	public void setId(Integer id){
		this.id = id;
	}
	public Integer getId(){
		return id;
	}
	public void setDescription(String description){
		this.description = description;
	}
	public String getDescription(){
		return description;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("id: " + id + ", ");
		if(description != null)
			builder.append("description: " + description + ", ");
		return builder.toString();
	}
}
