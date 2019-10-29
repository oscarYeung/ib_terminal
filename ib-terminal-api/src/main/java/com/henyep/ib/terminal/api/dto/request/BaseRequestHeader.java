package com.henyep.ib.terminal.api.dto.request;

import java.io.Serializable;

public class BaseRequestHeader implements Serializable {
	private static final long serialVersionUID = 8058281893349844433L;
	// 消息通道 web-001,mobile-002.other-003
	private String channelId;
	// 消息类型 ib login -001, ibregister - 002
	private String messageType;
	// 消息时间
	private String messageDate;
	// 语言
	private String language;
	// 客户 IP
	private String ipAddress;
	// 安全密匙
	private String secretKey;

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(String messageDate) {
		this.messageDate = messageDate;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

}
