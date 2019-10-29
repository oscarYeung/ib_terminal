package com.test;

public class ReqBody {
	public String getIbLoginName() {
		return ibLoginName;
	}
	public void setIbLoginName(String ibLoginName) {
		this.ibLoginName = ibLoginName;
	}
	public String getIbStatus() {
		return ibStatus;
	}
	public void setIbStatus(String ibStatus) {
		this.ibStatus = ibStatus;
	}
	public String getIbCreateDate() {
		return ibCreateDate;
	}
	public void setIbCreateDate(String ibCreateDate) {
		this.ibCreateDate = ibCreateDate;
	}
	private String ibLoginName;
	private String ibStatus;
	private String ibCreateDate;
}
