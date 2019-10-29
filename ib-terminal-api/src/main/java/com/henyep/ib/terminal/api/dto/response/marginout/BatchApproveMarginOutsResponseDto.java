package com.henyep.ib.terminal.api.dto.response.marginout;

import java.util.List;

import com.henyep.ib.terminal.api.dto.db.MarginOutBean;

public class BatchApproveMarginOutsResponseDto {

	private List<MarginOutBean> marginOuts;

	public List<MarginOutBean> getMarginOuts() {
		return marginOuts;
	}

	public void setMarginOuts(List<MarginOutBean> marginOuts) {
		this.marginOuts = marginOuts;
	}
}
