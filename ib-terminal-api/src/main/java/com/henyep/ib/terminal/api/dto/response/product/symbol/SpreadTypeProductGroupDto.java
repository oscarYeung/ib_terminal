package com.henyep.ib.terminal.api.dto.response.product.symbol;

import java.io.Serializable;
import java.util.List;

public class SpreadTypeProductGroupDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6603817983785996432L;

	private String spread_type;

	private List<String> product_groups;

	public List<String> getProduct_groups() {
		return product_groups;
	}

	public void setProduct_groups(List<String> product_groups) {
		this.product_groups = product_groups;
	}

	public String getSpread_type() {
		return spread_type;
	}

	public void setSpread_type(String spread_type) {
		this.spread_type = spread_type;
	}
	
	

}
