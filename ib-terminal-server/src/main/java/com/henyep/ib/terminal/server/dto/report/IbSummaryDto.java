package com.henyep.ib.terminal.server.dto.report;

public class IbSummaryDto {

	private String brand_code;
	private String ib_code;
	private String first_name;
	private String last_name;
	private int min_id;
	private int max_id;
	private Double total_lot;
	private Double total_fix_commission;
	private Double total_spread_commission;
	private Double total_rebate_commission_lot;
	private Double total_rebate_commission_pip;
	private Double total_trade_swaps;
	private Double total_commission;
	private Double total_trade_pl;
	private Double floating_pl;
	private Double net_deposit;
	private Double net_adj;

	public String getBrand_code() {
		return brand_code;
	}

	public void setBrand_code(String brand_code) {
		this.brand_code = brand_code;
	}

	public String getIb_code() {
		return ib_code;
	}

	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public int getMin_id() {
		return min_id;
	}

	public void setMin_id(int min_id) {
		this.min_id = min_id;
	}

	public int getMax_id() {
		return max_id;
	}

	public void setMax_id(int max_id) {
		this.max_id = max_id;
	}

	public Double getTotal_lot() {
		return total_lot;
	}

	public void setTotal_lot(Double total_lot) {
		this.total_lot = total_lot;
	}

	public Double getTotal_fix_commission() {
		return total_fix_commission;
	}

	public void setTotal_fix_commission(Double total_fix_commission) {
		this.total_fix_commission = total_fix_commission;
	}

	public Double getTotal_spread_commission() {
		return total_spread_commission;
	}

	public void setTotal_spread_commission(Double total_spread_commission) {
		this.total_spread_commission = total_spread_commission;
	}

	public Double getTotal_rebate_commission_lot() {
		return total_rebate_commission_lot;
	}

	public void setTotal_rebate_commission_lot(Double total_rebate_commission_lot) {
		this.total_rebate_commission_lot = total_rebate_commission_lot;
	}

	public Double getTotal_rebate_commission_pip() {
		return total_rebate_commission_pip;
	}

	public void setTotal_rebate_commission_pip(Double total_rebate_commission_pip) {
		this.total_rebate_commission_pip = total_rebate_commission_pip;
	}

	public Double getTotal_commission() {
		return total_commission;
	}

	public void setTotal_commission(Double total_commission) {
		this.total_commission = total_commission;
	}
	
	public Double getTotal_trade_pl() {
		return total_trade_pl;
	}

	public void setTotal_trade_pl(Double total_trade_pl) {
		this.total_trade_pl = total_trade_pl;
	}

	public Double getNet_deposit() {
		return net_deposit;
	}

	public void setNet_deposit(Double net_deposit) {
		this.net_deposit = net_deposit;
	}

	public Double getTotal_trade_swaps() {
		return total_trade_swaps;
	}

	public void setTotal_trade_swaps(Double total_trade_swaps) {
		this.total_trade_swaps = total_trade_swaps;
	}

	public Double getNet_adj() {
		return net_adj;
	}

	public void setNet_adj(Double net_adj) {
		this.net_adj = net_adj;
	}

	public Double getFloating_pl() {
		return floating_pl;
	}

	public void setFloating_pl(Double floating_pl) {
		this.floating_pl = floating_pl;
	}
	
}
