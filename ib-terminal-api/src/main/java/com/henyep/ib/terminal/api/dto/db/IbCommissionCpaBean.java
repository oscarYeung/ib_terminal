package com.henyep.ib.terminal.api.dto.db;
import java.util.Date;

public class IbCommissionCpaBean{

	private String brand_code;
	private String ib_code;
	private String client_code;
	private Date trade_date;
	private String currency;
	private Double total_lot;
	private Double total_deposit;
	private Double amount;
	private Double min_lot;
	private Double min_deposit;
	private Date last_update_time;
	private String last_update_user;

	public void setBrand_code(String brand_code){
		this.brand_code = brand_code;
	}
	public String getBrand_code(){
		return brand_code;
	}
	public void setIb_code(String ib_code){
		this.ib_code = ib_code;
	}
	public String getIb_code(){
		return ib_code;
	}
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
	public void setAmount(Double amount){
		this.amount = amount;
	}
	public Double getAmount(){
		return amount;
	}
	public void setMin_lot(Double min_lot){
		this.min_lot = min_lot;
	}
	public Double getMin_lot(){
		return min_lot;
	}
	public void setMin_deposit(Double min_deposit){
		this.min_deposit = min_deposit;
	}
	public Double getMin_deposit(){
		return min_deposit;
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
		if(brand_code != null)
			builder.append("brand_code: " + brand_code + ", ");
		if(ib_code != null)
			builder.append("ib_code: " + ib_code + ", ");
		if(client_code != null)
			builder.append("client_code: " + client_code + ", ");
		if(trade_date != null)
			builder.append("trade_date: " + trade_date.toString() + ", ");
		if(currency != null)
			builder.append("currency: " + currency + ", ");
		builder.append("total_lot: " + total_lot + ", ");
		builder.append("total_deposit: " + total_deposit + ", ");
		builder.append("amount: " + amount + ", ");
		builder.append("min_lot: " + min_lot + ", ");
		builder.append("min_deposit: " + min_deposit + ", ");
		if(last_update_time != null)
			builder.append("last_update_time: " + last_update_time.toString() + ", ");
		if(last_update_user != null)
			builder.append("last_update_user: " + last_update_user + ", ");
		return builder.toString();
	}
}
