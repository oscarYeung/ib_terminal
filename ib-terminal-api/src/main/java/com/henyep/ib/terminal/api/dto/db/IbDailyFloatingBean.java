package com.henyep.ib.terminal.api.dto.db;
import java.util.Date;



public class IbDailyFloatingBean{

	private String ib_code;
	private String account_number;
	private Date trade_date;
	private Double floating_pnl;
	private Double swap;
	private Double commission;
	private Double profit;
	private Double pnl_adj;
	private Date last_update_time;
	private String last_update_user;

	public void setIb_code(String ib_code){
		this.ib_code = ib_code;
	}
	public String getIb_code(){
		return ib_code;
	}
	public String getAccount_number() {
		return account_number;
	}
	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}
	public void setTrade_date(Date trade_date){
		this.trade_date = trade_date;
	}
	public Date getTrade_date(){
		return trade_date;
	}
	public void setFloating_pnl(Double floating_pnl){
		this.floating_pnl = floating_pnl;
	}
	public Double getFloating_pnl(){
		return floating_pnl;
	}
	public void setSwap(Double swap){
		this.swap = swap;
	}
	public Double getSwap(){
		return swap;
	}
	public void setCommission(Double commission){
		this.commission = commission;
	}
	public Double getCommission(){
		return commission;
	}
	public void setProfit(Double profit){
		this.profit = profit;
	}
	public Double getProfit(){
		return profit;
	}
	public void setPnl_adj(Double pnl_adj){
		this.pnl_adj = pnl_adj;
	}
	public Double getPnl_adj(){
		return pnl_adj;
	}
	public Date getLast_update_time() {
		return last_update_time;
	}
	public void setLast_update_time(Date last_update_time) {
		this.last_update_time = last_update_time;
	}
	public String getLast_update_user() {
		return last_update_user;
	}
	public void setLast_update_user(String last_update_user) {
		this.last_update_user = last_update_user;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		if(ib_code != null)
			builder.append("ib_code: " + ib_code + ", ");
		if(trade_date != null)
			builder.append("trade_date: " + trade_date.toString() + ", ");
		builder.append("floating_pnl: " + floating_pnl + ", ");
		builder.append("swap: " + swap + ", ");
		builder.append("commission: " + commission + ", ");
		builder.append("profit: " + profit + ", ");
		builder.append("pnl_adj: " + pnl_adj + ", ");
		return builder.toString();
	}
}
