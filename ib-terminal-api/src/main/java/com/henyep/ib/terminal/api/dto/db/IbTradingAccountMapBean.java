package com.henyep.ib.terminal.api.dto.db;
import java.util.Date;

public class IbTradingAccountMapBean{

	private String ib_code;
	private String trading_account;
	private String trading_platform;
	private Date last_update_time;
	private String last_update_user;

	public void setIb_code(String ib_code){
		this.ib_code = ib_code;
	}
	public String getIb_code(){
		return ib_code;
	}
	public void setTrading_account(String trading_account){
		this.trading_account = trading_account;
	}
	public String getTrading_account(){
		return trading_account;
	}
	public void setTrading_platform(String trading_platform){
		this.trading_platform = trading_platform;
	}
	public String getTrading_platform(){
		return trading_platform;
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
		if(ib_code != null)
			builder.append("ib_code: " + ib_code + ", ");
		if(trading_account != null)
			builder.append("trading_account: " + trading_account + ", ");
		if(trading_platform != null)
			builder.append("trading_platform: " + trading_platform + ", ");
		if(last_update_time != null)
			builder.append("last_update_time: " + last_update_time.toString() + ", ");
		if(last_update_user != null)
			builder.append("last_update_user: " + last_update_user + ", ");
		return builder.toString();
	}
}
