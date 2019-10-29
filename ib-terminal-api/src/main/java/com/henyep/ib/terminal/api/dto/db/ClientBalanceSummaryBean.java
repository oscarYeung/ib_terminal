package com.henyep.ib.terminal.api.dto.db;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class ClientBalanceSummaryBean {

	private String client_code;
	@JsonFormat(pattern = Constants.FORMAT_DATETIME)
	private Date trade_date;
	private Double balance;
	private Double equity;
	private Double floating;
	private Double deposit;
	private Double withdrawal;
	private Double credit;
	private Double pl;
	private Double pl_adjustment;
	private Double commission;
	private Double swap;
	private Double tax;
	private Double previous_credit;
	private Double previous_balance;
	private Double previous_equity;
	private Double previous_floating;
	private String currency;
	@JsonFormat(pattern = Constants.FORMAT_DATETIME)
	private Date last_update_date;
	private String last_update_user;

	public void setClient_code(String client_code) {
		this.client_code = client_code;
	}

	public String getClient_code() {
		return client_code;
	}

	public void setTrade_date(Date trade_date) {
		this.trade_date = trade_date;
	}

	public Date getTrade_date() {
		return trade_date;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getBalance() {
		return balance;
	}

	public void setEquity(Double equity) {
		this.equity = equity;
	}

	public Double getEquity() {
		return equity;
	}

	public void setFloating(Double floating) {
		this.floating = floating;
	}

	public Double getFloating() {
		return floating;
	}

	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}

	public Double getDeposit() {
		return deposit;
	}

	public void setWithdrawal(Double withdrawal) {
		this.withdrawal = withdrawal;
	}

	public Double getWithdrawal() {
		return withdrawal;
	}

	public void setCredit(Double credit) {
		this.credit = credit;
	}

	public Double getCredit() {
		return credit;
	}

	public Double getPl() {
		return pl;
	}

	public void setPl(Double pl) {
		this.pl = pl;
	}

	public void setPl_adjustment(Double pl_adjustment) {
		this.pl_adjustment = pl_adjustment;
	}

	public Double getPl_adjustment() {
		return pl_adjustment;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public Double getCommission() {
		return commission;
	}

	public void setSwap(Double swap) {
		this.swap = swap;
	}

	public Double getSwap() {
		return swap;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Double getTax() {
		return tax;
	}

	public void setPrevious_credit(Double previous_credit) {
		this.previous_credit = previous_credit;
	}

	public Double getPrevious_credit() {
		return previous_credit;
	}

	public void setPrevious_balance(Double previous_balance) {
		this.previous_balance = previous_balance;
	}

	public Double getPrevious_balance() {
		return previous_balance;
	}

	public void setPrevious_equity(Double previous_equity) {
		this.previous_equity = previous_equity;
	}

	public Double getPrevious_equity() {
		return previous_equity;
	}

	public void setPrevious_floating(Double previous_floating) {
		this.previous_floating = previous_floating;
	}

	public Double getPrevious_floating() {
		return previous_floating;
	}
	
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setLast_update_date(Date last_update_date) {
		this.last_update_date = last_update_date;
	}

	public Date getLast_update_date() {
		return last_update_date;
	}

	public void setLast_update_user(String last_update_user) {
		this.last_update_user = last_update_user;
	}

	public String getLast_update_user() {
		return last_update_user;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (client_code != null)
			builder.append("client_code: " + client_code + ", ");
		if (trade_date != null)
			builder.append("trade_date: " + trade_date.toString() + ", ");
		builder.append("balance: " + balance + ", ");
		builder.append("equity: " + equity + ", ");
		builder.append("floating: " + floating + ", ");
		builder.append("deposit: " + deposit + ", ");
		builder.append("withdrawal: " + withdrawal + ", ");
		builder.append("credit: " + credit + ", ");
		builder.append("pl_adjustment: " + pl_adjustment + ", ");
		builder.append("commission: " + commission + ", ");
		builder.append("swap: " + swap + ", ");
		builder.append("tax: " + tax + ", ");
		builder.append("previous_credit: " + previous_credit + ", ");
		builder.append("previous_balance: " + previous_balance + ", ");
		builder.append("previous_equity: " + previous_equity + ", ");
		builder.append("previous_floating: " + previous_floating + ", ");
		if (last_update_date != null)
			builder.append("last_update_date: " + last_update_date.toString() + ", ");
		if (last_update_user != null)
			builder.append("last_update_user: " + last_update_user + ", ");
		return builder.toString();
	}
}
