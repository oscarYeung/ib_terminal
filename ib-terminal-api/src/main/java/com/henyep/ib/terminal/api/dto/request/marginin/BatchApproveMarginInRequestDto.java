package com.henyep.ib.terminal.api.dto.request.marginin;

import java.io.Serializable;
import java.util.List;

public class BatchApproveMarginInRequestDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private List<String> margin_in_ids;

	public List<String> getMargin_in_ids() {
		return margin_in_ids;
	}

	public void setMargin_in_ids(List<String> margin_in_ids) {
		this.margin_in_ids = margin_in_ids;
	}
	
}
