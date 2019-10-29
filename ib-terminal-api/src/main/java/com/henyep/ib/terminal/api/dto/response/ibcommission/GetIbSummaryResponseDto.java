package com.henyep.ib.terminal.api.dto.response.ibcommission;

import java.io.Serializable;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.IbCommissionDetailsSupplementaryBean;

public class GetIbSummaryResponseDto implements Serializable {

	private static final long serialVersionUID = -7802175435255177339L;

	private IbSummaryByDateRangeModel trade_day_ib_summary;

	private IbSummaryByDateRangeModel month_ib_summary;

	private IbSummaryByDateRangeModel last_month_ib_summary;

	private List<IbCommissionDetailsSupplementaryBean> evCommissionList;

	public IbSummaryByDateRangeModel getTrade_day_ib_summary() {
		return trade_day_ib_summary;
	}

	public void setTrade_day_ib_summary(IbSummaryByDateRangeModel trade_day_ib_summary) {
		this.trade_day_ib_summary = trade_day_ib_summary;
	}

	public IbSummaryByDateRangeModel getMonth_ib_summary() {
		return month_ib_summary;
	}

	public void setMonth_ib_summary(IbSummaryByDateRangeModel month_ib_summary) {
		this.month_ib_summary = month_ib_summary;
	}

	public IbSummaryByDateRangeModel getLast_month_ib_summary() {
		return last_month_ib_summary;
	}

	public void setLast_month_ib_summary(IbSummaryByDateRangeModel last_month_ib_summary) {
		this.last_month_ib_summary = last_month_ib_summary;
	}

	public List<IbCommissionDetailsSupplementaryBean> getEvCommissionList() {
		return evCommissionList;
	}

	public void setEvCommissionList(List<IbCommissionDetailsSupplementaryBean> evCommissionList) {
		this.evCommissionList = evCommissionList;
	}

}
