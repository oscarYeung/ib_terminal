package com.henyep.ib.terminal.api.dto.response.ibcommission.clientSummary;

import java.io.Serializable;
import java.math.BigDecimal;

public class IbClientCommissionSummaryWebModel implements Serializable {
	private static final long serialVersionUID = -6857518030343797841L;

	private String brand_code;
	private String ib_code;
	private String client_ib_code;
	private String client_code;
	private String product_group;
	private String currency;
	private BigDecimal total_lot;
	private BigDecimal total_fix_commission;
	private BigDecimal total_rebate_commission_lot;
	private BigDecimal total_rebate_commission_pip;
	private BigDecimal total_commission;

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

	public String getClient_ib_code() {
		return client_ib_code;
	}

	public void setClient_ib_code(String client_ib_code) {
		this.client_ib_code = client_ib_code;
	}

	public String getClient_code() {
		return client_code;
	}

	public void setClient_code(String client_code) {
		this.client_code = client_code;
	}

	public String getProduct_group() {
		return product_group;
	}

	public void setProduct_group(String product_group) {
		this.product_group = product_group;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getTotal_lot() {
		return total_lot;
	}

	public void setTotal_lot(BigDecimal total_lot) {
		this.total_lot = total_lot;
	}

	public BigDecimal getTotal_fix_commission() {
		return total_fix_commission;
	}

	public void setTotal_fix_commission(BigDecimal total_fix_commission) {
		this.total_fix_commission = total_fix_commission;
	}

	public BigDecimal getTotal_rebate_commission_lot() {
		return total_rebate_commission_lot;
	}

	public void setTotal_rebate_commission_lot(BigDecimal total_rebate_commission_lot) {
		this.total_rebate_commission_lot = total_rebate_commission_lot;
	}

	public BigDecimal getTotal_rebate_commission_pip() {
		return total_rebate_commission_pip;
	}

	public void setTotal_rebate_commission_pip(BigDecimal total_rebate_commission_pip) {
		this.total_rebate_commission_pip = total_rebate_commission_pip;
	}

	public BigDecimal getTotal_commission() {
		return total_commission;
	}

	public void setTotal_commission(BigDecimal total_commission) {
		this.total_commission = total_commission;
	}

}
