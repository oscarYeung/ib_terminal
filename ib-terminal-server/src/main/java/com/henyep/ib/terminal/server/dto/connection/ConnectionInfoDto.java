package com.henyep.ib.terminal.server.dto.connection;

public class ConnectionInfoDto {

	private String url;
	private String path;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getEndPoint(){
		return url + path;
	}
}
