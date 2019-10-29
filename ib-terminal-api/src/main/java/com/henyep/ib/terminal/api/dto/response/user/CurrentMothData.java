package com.henyep.ib.terminal.api.dto.response.user;

/**
 * 项目名称：IBTerminalServer
 * <p>
 * 功能模块名称：
 * <p>
 * 文件名称为：CurrentMothData.java
 * <p>
 * 文件功能简述：
 * <p>
 * 文件创建人：Administrator——Jm.zhang
 * @version v1.0
 * @time  2016-8-17 下午3:35:12
 * @copyright Hengyp
 */
public class CurrentMothData {
	private String commissions;
	private String rebates;
	private String spreadWidening;
	public String getCommissions() {
		return commissions;
	}
	public void setCommissions(String commissions) {
		this.commissions = commissions==null?"0.00":commissions;
	}
	public String getRebates() {
		return rebates;
	}
	public void setRebates(String rebates) {
		this.rebates = rebates==null?"0.00":rebates;
	}
	public String getSpreadWidening() {
		return spreadWidening;
	}
	public void setSpreadWidening(String spreadWidening) {
		this.spreadWidening = spreadWidening==null?"0.00":spreadWidening;
	}
}
