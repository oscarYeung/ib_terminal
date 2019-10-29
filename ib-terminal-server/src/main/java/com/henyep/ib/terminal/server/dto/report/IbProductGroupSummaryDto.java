package com.henyep.ib.terminal.server.dto.report;

public class IbProductGroupSummaryDto {

	private String ib_code;
	private String product_group;
	private Double total_commission;
	
	public String getIb_code() {
		return ib_code;
	}
	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}
	public String getProduct_group() {
		return product_group;
	}
	public void setProduct_group(String product_group) {
		this.product_group = product_group;
	}
	public Double getTotal_commission() {
		return total_commission;
	}
	public void setTotal_commission(Double total_commission) {
		this.total_commission = total_commission;
	}
	
	
}
