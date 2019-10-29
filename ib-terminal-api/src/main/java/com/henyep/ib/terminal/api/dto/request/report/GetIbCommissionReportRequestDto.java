package com.henyep.ib.terminal.api.dto.request.report;

import com.henyep.ib.terminal.api.dto.request.BaseDateTimeRequestBodyDto;

public class GetIbCommissionReportRequestDto extends BaseDateTimeRequestBodyDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4793202934791459547L;
	
	
	private String brand_code;
	private String ib_code;
	private boolean has_ib_page;
	private boolean has_ib_product_page;
	private boolean has_ib_product_period_page;
	private boolean has_client_page;
	private boolean has_client_period_page;
	private boolean has_trade_page;
	private boolean has_ib_rebate_page;
	private boolean has_ib_trade_amount_page;
	
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
	public boolean isHas_ib_page() {
		return has_ib_page;
	}
	public void setHas_ib_page(boolean has_ib_page) {
		this.has_ib_page = has_ib_page;
	}
	public boolean isHas_ib_product_period_page() {
		return has_ib_product_period_page;
	}
	public void setHas_ib_product_period_page(boolean has_ib_product_period_page) {
		this.has_ib_product_period_page = has_ib_product_period_page;
	}
	public boolean isHas_ib_product_page() {
		return has_ib_product_page;
	}
	public void setHas_ib_product_page(boolean has_ib_product_page) {
		this.has_ib_product_page = has_ib_product_page;
	}
	public boolean isHas_client_period_page() {
		return has_client_period_page;
	}
	public void setHas_client_period_page(boolean has_client_period_page) {
		this.has_client_period_page = has_client_period_page;
	}
	public boolean isHas_client_page() {
		return has_client_page;
	}
	public void setHas_client_page(boolean has_client_page) {
		this.has_client_page = has_client_page;
	}
	public boolean isHas_trade_page() {
		return has_trade_page;
	}
	public void setHas_trade_page(boolean has_trade_page) {
		this.has_trade_page = has_trade_page;
	}
	public boolean isHas_ib_rebate_page() {
		return has_ib_rebate_page;
	}
	public void setHas_ib_rebate_page(boolean has_ib_rebate_page) {
		this.has_ib_rebate_page = has_ib_rebate_page;
	}
	public boolean isHas_ib_trade_amount_page() {
		return has_ib_trade_amount_page;
	}
	public void setHas_ib_trade_amount_page(boolean has_ib_trade_amount_page) {
		this.has_ib_trade_amount_page = has_ib_trade_amount_page;
	}
}
