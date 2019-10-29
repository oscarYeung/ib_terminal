package com.henyep.ib.terminal.server.dto.mt4.model;

import com.google.gson.annotations.SerializedName;

public class UserProfileRecord {
	
	@SerializedName("AccountBalance")
	private Double accountBalance;
	@SerializedName("Commission")
	private Double commission;
	@SerializedName("Credit")
	private Double credit;
	@SerializedName("Currency")
	private String currency;
	@SerializedName("Equity")
	private Double equity;
	@SerializedName("Group")
	private String group;
	@SerializedName("Login")
	private Integer login;
	@SerializedName("Margin")
	private Double margin;
	@SerializedName("MaxWithDrawal")
	private Double maxWithDrawal;
	@SerializedName("PL")
	private Double pl;
	@SerializedName("Swaps")
	private Double swaps;
	public Double getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}
	public Double getCommission() {
		return commission;
	}
	public void setCommission(Double commission) {
		this.commission = commission;
	}
	public Double getCredit() {
		return credit;
	}
	public void setCredit(Double credit) {
		this.credit = credit;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Double getEquity() {
		return equity;
	}
	public void setEquity(Double equity) {
		this.equity = equity;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public Integer getLogin() {
		return login;
	}
	public void setLogin(Integer login) {
		this.login = login;
	}
	public Double getMargin() {
		return margin;
	}
	public void setMargin(Double margin) {
		this.margin = margin;
	}
	public Double getMaxWithDrawal() {
		return maxWithDrawal;
	}
	public void setMaxWithDrawal(Double maxWithDrawal) {
		this.maxWithDrawal = maxWithDrawal;
	}
	public Double getPl() {
		return pl;
	}
	public void setPl(Double pl) {
		this.pl = pl;
	}
	public Double getSwaps() {
		return swaps;
	}
	public void setSwaps(Double swaps) {
		this.swaps = swaps;
	}
	
	
	
}