package com.henyep.ib.terminal.api.dto.response.marginout;

import java.io.Serializable;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.MarginOutBean;

public class ExcelUploadMarginOutResponseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5439285762429931468L;	
	
	private List<InvalidMarginOutModel> invalid_margin_outs;
	
	private boolean is_success;
	
	private List<MarginOutBean> valid_margin_outs;

	public List<InvalidMarginOutModel> getInvalid_margin_outs() {
		return invalid_margin_outs;
	}

	public void setInvalid_margin_outs(List<InvalidMarginOutModel> invalid_margin_outs) {
		this.invalid_margin_outs = invalid_margin_outs;
	}

	public List<MarginOutBean> getValid_margin_outs() {
		return valid_margin_outs;
	}

	public void setValid_margin_outs(List<MarginOutBean> valid_margin_outs) {
		this.valid_margin_outs = valid_margin_outs;
	}

	public boolean getIs_success() {
		return is_success;
	}

	public void setIs_success(boolean is_success) {
		this.is_success = is_success;
	}
	
	
	
	
}
