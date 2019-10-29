package com.henyep.ib.terminal.api.dto.db;

public class PermissionBean{

	private Integer id;
	private String key;

	public void setId(Integer id){
		this.id = id;
	}
	public Integer getId(){
		return id;
	}
	public void setKey(String key){
		this.key = key;
	}
	public String getKey(){
		return key;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("id: " + id + ", ");
		if(key != null)
			builder.append("key: " + key + ", ");
		return builder.toString();
	}
}
