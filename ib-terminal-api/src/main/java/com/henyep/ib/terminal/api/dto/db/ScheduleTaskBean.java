package com.henyep.ib.terminal.api.dto.db;
import java.util.Date;

public class ScheduleTaskBean{

	private Date trade_date;
	private Integer spend_time;
	private String status;
	private String type;
	private Date create_on;
	private Date update_on;

	public void setTrade_date(Date trade_date){
		this.trade_date = trade_date;
	}
	public Date getTrade_date(){
		return trade_date;
	}
	public void setSpend_time(Integer spend_time){
		this.spend_time = spend_time;
	}
	public Integer getSpend_time(){
		return spend_time;
	}
	public void setStatus(String status){
		this.status = status;
	}
	public String getStatus(){
		return status;
	}
	public void setCreate_on(Date create_on){
		this.create_on = create_on;
	}
	public Date getCreate_on(){
		return create_on;
	}
	public void setUpdate_on(Date update_on){
		this.update_on = update_on;
	}
	public Date getUpdate_on(){
		return update_on;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		if(trade_date != null)
			builder.append("trade_date: " + trade_date.toString() + ", ");
		builder.append("spend_time: " + spend_time + ", ");
		if(status != null)
			builder.append("status: " + status + ", ");
		if(create_on != null)
			builder.append("create_on: " + create_on.toString() + ", ");
		if(update_on != null)
			builder.append("update_on: " + update_on.toString() + ", ");
		return builder.toString();
	}
}
