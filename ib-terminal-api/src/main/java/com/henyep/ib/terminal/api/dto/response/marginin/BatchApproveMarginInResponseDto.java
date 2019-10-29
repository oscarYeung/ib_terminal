package com.henyep.ib.terminal.api.dto.response.marginin;

import java.util.List;

import com.henyep.ib.terminal.api.dto.db.MarginInBean;

public class BatchApproveMarginInResponseDto {

	private List<MarginInBean> updated_margin_ins;
	
	private List<ErrorCodeMarginInModel> not_updated_margin_ins;

	public List<MarginInBean> getUpdated_margin_ins() {
		return updated_margin_ins;
	}

	public void setUpdated_margin_ins(List<MarginInBean> updated_margin_ins) {
		this.updated_margin_ins = updated_margin_ins;
	}

	public List<ErrorCodeMarginInModel> getNot_updated_margin_ins() {
		return not_updated_margin_ins;
	}

	public void setNot_updated_margin_ins(List<ErrorCodeMarginInModel> not_updated_margin_ins) {
		this.not_updated_margin_ins = not_updated_margin_ins;
	}

	
	
	
}
