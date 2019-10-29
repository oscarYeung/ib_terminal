package com.henyep.ib.terminal.api.dto.request.user;

import javax.validation.constraints.NotNull;

/**
 * 项目名称：IBTerminalServer
 * <p>
 * 功能模块名称：
 * <p>
 * 文件名称为：IbResetPwdReqDto.java
 * <p>
 * 文件功能简述：
 * <p>
 * 文件创建人：Administrator——Jm.zhang
 * @version v1.0
 * @time  2016-9-5 下午3:49:24
 * @copyright Hengyp
 */
public class IbResetPwdReqDto {
	@NotNull(message = "{login.username.not.blank}")
	private String ibLoginName;
	@NotNull(message = "{login.password.not.blank}")
	private String ibNewPassword;
	private String identifyingCode;
	@NotNull(message = "{common.brand.code.is.blank}")
	private String brandCode;
	public String getIbLoginName() {
		return ibLoginName;
	}
	public void setIbLoginName(String ibLoginName) {
		this.ibLoginName = ibLoginName;
	}
	public String getIbNewPassword() {
		return ibNewPassword;
	}
	public void setIbNewPassword(String ibNewPassword) {
		this.ibNewPassword = ibNewPassword;
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
