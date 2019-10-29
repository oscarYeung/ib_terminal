package com.henyep.ib.terminal.server.mail;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * 项目名称：IBTerminalServer
 * <p>
 * 功能模块名称：
 * <p>
 * 文件名称为：MailServerConfig.java
 * <p>
 * 文件功能简述：
 * <p>
 * 文件创建人：Administrator——Jm.zhang
 * @version v1.0
 * @time  2016-9-8 下午6:30:49
 * @copyright Hengyp
 */
@Component(value = "mailServer")
public class MailServerConfig {
	private boolean auth;
	/**
	 * if auth is true then need user/password
	 */
	private String user;
	private String password;	
	private Integer connect_timeout;
	private boolean debug;
	private String host;
	private Integer port;
	private Integer send_timeout;
	private String formAddressAlias;
	private String fromAddress;

	private  final Integer status_not_send=0;
	private  final Integer status_sended=1;
	private  final String mail_address_splitor=",";
	private  final Integer priority_leve_normal=0;
	private  final Integer priority_leve_high=1;
	

	public boolean isAuth() {
		return auth;
	}
	public void setAuth(boolean auth) {
		this.auth = auth;
	}
	public int getConnect_timeout() {
		return connect_timeout;
	}
	public void setConnect_timeout(int connect_timeout) {
		this.connect_timeout = connect_timeout;
	}
	public boolean isDebug() {
		return debug;
	}
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getSend_timeout() {
		return send_timeout;
	}
	public void setSend_timeout(int send_timeout) {
		this.send_timeout = send_timeout;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getStatus_not_send() {
		return status_not_send;
	}
	public Integer getStatus_sended() {
		return status_sended;
	}
	public String getMail_address_splitor() {
		return mail_address_splitor;
	}
	public Integer getPriority_leve_normal() {
		return priority_leve_normal;
	}
	public Integer getPriority_leve_high() {
		return priority_leve_high;
	}
	public void setConnect_timeout(Integer connect_timeout) {
		this.connect_timeout = connect_timeout;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public void setSend_timeout(Integer send_timeout) {
		this.send_timeout = send_timeout;
	}
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public String getFormAddressAlias() {
		return formAddressAlias;
	}
	public void setFormAddressAlias(String formAddressAlias) {
		this.formAddressAlias = formAddressAlias;
	}




}
