package com.henyep.ib.terminal.api.dto.db;
import java.util.Date;

public class OpenTradeBean{

	private Date trade_date;
	private String ticket;
	private Integer client_code;
	private String platform;
	private String bs;
	private Double lot;
	private String symbol;
	private Double pl;
	private Double commission;
	private Double swap;
	private Integer account_idenify;
	private Double open_price;
	private Date open_time;

	public void setTrade_date(Date trade_date){
		this.trade_date = trade_date;
	}
	public Date getTrade_date(){
		return trade_date;
	}
	public void setTicket(String ticket){
		this.ticket = ticket;
	}
	public String getTicket(){
		return ticket;
	}
	public void setClient_code(Integer client_code){
		this.client_code = client_code;
	}
	public Integer getClient_code(){
		return client_code;
	}
	public void setPlatform(String platform){
		this.platform = platform;
	}
	public String getPlatform(){
		return platform;
	}
	public void setBs(String bs){
		this.bs = bs;
	}
	public String getBs(){
		return bs;
	}
	public void setLot(Double lot){
		this.lot = lot;
	}
	public Double getLot(){
		return lot;
	}
	public void setSymbol(String symbol){
		this.symbol = symbol;
	}
	public String getSymbol(){
		return symbol;
	}
	public void setPl(Double pl){
		this.pl = pl;
	}
	public Double getPl(){
		return pl;
	}
	public void setCommission(Double commission){
		this.commission = commission;
	}
	public Double getCommission(){
		return commission;
	}
	public void setSwap(Double swap){
		this.swap = swap;
	}
	public Double getSwap(){
		return swap;
	}
	public void setAccount_idenify(Integer account_idenify){
		this.account_idenify = account_idenify;
	}
	public Integer getAccount_idenify(){
		return account_idenify;
	}
	public void setOpen_price(Double open_price){
		this.open_price = open_price;
	}
	public Double getOpen_price(){
		return open_price;
	}
	public void setOpen_time(Date open_time){
		this.open_time = open_time;
	}
	public Date getOpen_time(){
		return open_time;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		if(trade_date != null)
			builder.append("trade_date: " + trade_date.toString() + ", ");
		if(ticket != null)
			builder.append("ticket: " + ticket + ", ");
		builder.append("client_code: " + client_code + ", ");
		if(platform != null)
			builder.append("platform: " + platform + ", ");
		if(bs != null)
			builder.append("bs: " + bs + ", ");
		builder.append("lot: " + lot + ", ");
		if(symbol != null)
			builder.append("symbol: " + symbol + ", ");
		builder.append("pl: " + pl + ", ");
		builder.append("commission: " + commission + ", ");
		builder.append("swap: " + swap + ", ");
		builder.append("account_idenify: " + account_idenify + ", ");
		builder.append("open_price: " + open_price + ", ");
		if(open_time != null)
			builder.append("open_time: " + open_time.toString() + ", ");
		return builder.toString();
	}
}
