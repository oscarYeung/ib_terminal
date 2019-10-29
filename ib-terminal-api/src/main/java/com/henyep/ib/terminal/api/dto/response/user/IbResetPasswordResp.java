package com.henyep.ib.terminal.api.dto.response.user;

/**
 * 项目名称：IBTerminalServer
 * <p>
 * 功能模块名称：
 * <p>
 * 文件名称为：IbResetPasswordResp.java
 * <p>
 * 文件功能简述：
 * <p>
 * 文件创建人：Administrator——Jm.zhang
 * @version v1.0
 * @time  2016-9-5 下午3:55:22
 * @copyright Hengyp
 */
public class IbResetPasswordResp {
	private String userName;
	private String brandCode;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
}
