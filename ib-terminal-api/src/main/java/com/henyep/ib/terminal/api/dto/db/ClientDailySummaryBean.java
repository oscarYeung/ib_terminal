package com.henyep.ib.terminal.api.dto.db;
import java.util.Date;

public class ClientDailySummaryBean{

	private String client_code;
	private Date trade_date;
	private String currency;
	private Double total_lot;
	private Double total_deposit;
	private Double total_deposit_usd;
	private Double total_withdrawal;
	private Date last_update_time;
	private String last_update_user;

	public void setClient_code(String client_code){
		this.client_code = client_code;
	}
	public String getClient_code(){
		return client_code;
	}
	public void setTrade_date(Date trade_date){
		this.trade_date = trade_date;
	}
	public Date getTrade_date(){
		return trade_date;
	}
	public void setCurrency(String currency){
		this.currency = currency;
	}
	public String getCurrency(){
		return currency;
	}
	public void setTotal_lot(Double total_lot){
		this.total_lot = total_lot;
	}
	public Double getTotal_lot(){
		return total_lot;
	}
	public void setTotal_deposit(Double total_deposit){
		this.total_deposit = total_deposit;
	}
	public Double getTotal_deposit(){
		return total_deposit;
	}
	public void setTotal_deposit_usd(Double total_deposit_usd){
		this.total_deposit_usd = total_deposit_usd;
	}
	public Double getTotal_deposit_usd(){
		return total_deposit_usd;
	}
	public void setTotal_withdrawal(Double total_withdrawal){
		this.total_withdrawal = total_withdrawal;
	}
	public Double getTotal_withdrawal(){
		return total_withdrawal;
	}
	public void setLast_update_time(Date last_update_time){
		this.last_update_time = last_update_time;
	}
	public Date getLast_update_time(){
		return last_update_time;
	}
	public void setLast_update_user(String last_update_user){
		this.last_update_user = last_update_user;
	}
	public String getLast_update_user(){
		return last_update_user;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		if(client_code != null)
			builder.append("client_code: " + client_code + ", ");
		if(trade_date != null)
			builder.append("trade_date: " + trade_date.toString() + ", ");
		if(currency != null)
			builder.append("currency: " + currency + ", ");
		builder.append("total_lot: " + total_lot + ", ");
		builder.append("total_deposit: " + total_deposit + ", ");
		builder.append("total_deposit_usd: " + total_deposit_usd + ", ");
		builder.append("total_withdrawal: " + total_withdrawal + ", ");
		if(last_update_time != null)
			builder.append("last_update_time: " + last_update_time.toString() + ", ");
		if(last_update_user != null)
			builder.append("last_update_user: " + last_update_user + ", ");
		return builder.toString();
	}
}
