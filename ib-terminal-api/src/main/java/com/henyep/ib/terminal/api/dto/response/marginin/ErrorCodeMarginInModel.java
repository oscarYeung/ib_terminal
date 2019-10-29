package com.henyep.ib.terminal.api.dto.response.marginin;

import java.util.HashMap;
import java.util.Map;

import com.henyep.ib.terminal.api.dto.db.MarginInBean;

public class ErrorCodeMarginInModel {

	private Map<String, String> errorMap = new HashMap<String, String>();
	
	private MarginInBean margin_in_bean;

	public Map<String, String> getErrorMap() {
		return errorMap;
	}

	public void setErrorMap(Map<String, String> errorMap) {
		this.errorMap = errorMap;
	}

	public MarginInBean getMargin_in_bean() {
		return margin_in_bean;
	}

	public void setMargin_in_bean(MarginInBean margin_in_bean) {
		this.margin_in_bean = margin_in_bean;
	}
}
