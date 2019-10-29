package com.henyep.ib.terminal.api.dto.db;
import java.util.Date;

public class IbCommissionSummaryBean{

	private String brand_code;
	private String platform;
	private String ib_code;
	private String product_group;
	private String spread_type;
	private Date trade_date;
	private String currency;
	private Double total_lot;
	private Double total_fix_commission;
	private Double total_spread_commission;
	private Double total_rebate_commission_lot;
	private Double total_rebate_commission_pip;
	private Double total_ev_commission;
	private Double total_rev_commission;
	private Double total_commission;
	private Double total_trade_pl;
	private Double net_deposit;
	private Date last_update_time;
	private String last_update_user;

	public void setBrand_code(String brand_code){
		this.brand_code = brand_code;
	}
	public String getBrand_code(){
		return brand_code;
	}
	public void setPlatform(String platform){
		this.platform = platform;
	}
	public String getPlatform(){
		return platform;
	}
	public void setIb_code(String ib_code){
		this.ib_code = ib_code;
	}
	public String getIb_code(){
		return ib_code;
	}
	public void setProduct_group(String product_group){
		this.product_group = product_group;
	}
	public String getProduct_group(){
		return product_group;
	}
	public String getSpread_type() {
		return spread_type;
	}
	public void setSpread_type(String spread_type) {
		this.spread_type = spread_type;
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
	public void setTotal_fix_commission(Double total_fix_commission){
		this.total_fix_commission = total_fix_commission;
	}
	public Double getTotal_fix_commission(){
		return total_fix_commission;
	}
	public void setTotal_spread_commission(Double total_spread_commission){
		this.total_spread_commission = total_spread_commission;
	}
	public Double getTotal_spread_commission(){
		return total_spread_commission;
	}
	public void setTotal_rebate_commission_lot(Double total_rebate_commission_lot){
		this.total_rebate_commission_lot = total_rebate_commission_lot;
	}
	public Double getTotal_rebate_commission_lot(){
		return total_rebate_commission_lot;
	}
	public void setTotal_rebate_commission_pip(Double total_rebate_commission_pip){
		this.total_rebate_commission_pip = total_rebate_commission_pip;
	}
	public Double getTotal_rebate_commission_pip(){
		return total_rebate_commission_pip;
	}
	public void setTotal_ev_commission(Double total_ev_commission){
		this.total_ev_commission = total_ev_commission;
	}
	public Double getTotal_ev_commission(){
		return total_ev_commission;
	}
	public void setTotal_rev_commission(Double total_rev_commission){
		this.total_rev_commission = total_rev_commission;
	}
	public Double getTotal_rev_commission(){
		return total_rev_commission;
	}
	public void setTotal_commission(Double total_commission){
		this.total_commission = total_commission;
	}
	public Double getTotal_commission(){
		return total_commission;
	}
	public Double getTotal_trade_pl() {
		return total_trade_pl;
	}
	public void setTotal_trade_pl(Double total_trade_pl) {
		this.total_trade_pl = total_trade_pl;
	}
	public void setNet_deposit(Double net_deposit){
		this.net_deposit = net_deposit;
	}
	public Double getNet_deposit(){
		return net_deposit;
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
		if(platform != null)
			builder.append("platform: " + platform + ", ");
		if(ib_code != null)
			builder.append("ib_code: " + ib_code + ", ");
		if(product_group != null)
			builder.append("product_group: " + product_group + ", ");
		if(trade_date != null)
			builder.append("trade_date: " + trade_date.toString() + ", ");
		if(currency != null)
			builder.append("currency: " + currency + ", ");
		builder.append("total_lot: " + total_lot + ", ");
		builder.append("total_fix_commission: " + total_fix_commission + ", ");
		builder.append("total_spread_commission: " + total_spread_commission + ", ");
		builder.append("total_rebate_commission_lot: " + total_rebate_commission_lot + ", ");
		builder.append("total_rebate_commission_pip: " + total_rebate_commission_pip + ", ");
		builder.append("total_ev_commission: " + total_ev_commission + ", ");
		builder.append("total_rev_commission: " + total_rev_commission + ", ");
		builder.append("total_commission: " + total_commission + ", ");
		builder.append("net_deposit: " + net_deposit + ", ");
		if(last_update_time != null)
			builder.append("last_update_time: " + last_update_time.toString() + ", ");
		if(last_update_user != null)
			builder.append("last_update_user: " + last_update_user + ", ");
		return builder.toString();
	}
}
