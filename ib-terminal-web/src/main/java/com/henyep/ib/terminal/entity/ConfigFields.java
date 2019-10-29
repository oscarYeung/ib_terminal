package com.henyep.ib.terminal.entity;

public class ConfigFields {
	private String request_url;
	private String white_label_request_url;
	private String downloadTempFolder;
	
	public String getWhite_label_request_url() {
		return white_label_request_url;
	}
	public void setWhite_label_request_url(String white_label_request_url) {
		this.white_label_request_url = white_label_request_url;
	}
	
	public String getRequest_url() {
		return request_url;
	}
	public void setRequest_url(String request_url) {
		this.request_url = request_url;
	}
	
	public String getDownloadTempFolder() {
		return downloadTempFolder;
	}
	public void setDownloadTempFolder(String downloadTempFolder) {
		this.downloadTempFolder = downloadTempFolder;
	}
	
	
}