package com.henyep.ib.terminal.api.dto.request.marginout;

import java.io.Serializable;
import java.util.List;

public class BatchApproveMarginOutRequestDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4320867281011631073L;
	
	private List<String> margin_out_ids;

	public List<String> getMargin_out_ids() {
		return margin_out_ids;
	}

	public void setMargin_out_ids(List<String> margin_out_ids) {
		this.margin_out_ids = margin_out_ids;
	}
}
