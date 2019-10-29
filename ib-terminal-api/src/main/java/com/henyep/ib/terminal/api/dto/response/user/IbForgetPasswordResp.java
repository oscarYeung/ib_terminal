package com.henyep.ib.terminal.api.dto.response.user;

/**
 * 项目名称：IBTerminalServer
 * <p>
 * 功能模块名称：
 * <p>
 * 文件名称为：IbForgetPasswordResp.java
 * <p>
 * 文件功能简述：
 * <p>
 * 文件创建人：Administrator——Jm.zhang
 * @version v1.0
 * @time  2016-9-5 下午2:52:42
 * @copyright Hengyp
 */
public class IbForgetPasswordResp {
	private String ibLoginName;
	private String identifyingCode; 
	private String brandCode;
	public String getIbLoginName() {
		return ibLoginName;
	}
	public void setIbLoginName(String ibLoginName) {
		this.ibLoginName = ibLoginName;
	}
	public String getIdentifyingCode() {
		return identifyingCode;
	}
	public void setIdentifyingCode(String identifyingCode) {
		this.identifyingCode = identifyingCode;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
}
