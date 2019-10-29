package com.henyep.ib.terminal.api.dto.response.ibcommission.summary;

import java.io.Serializable;
import java.util.List;

public class GetByTeamHeadResponseDto implements Serializable {

	private static final long serialVersionUID = 5795451183266221556L;

	private List<IbCommissionSummaryWebModel> list;

	public List<IbCommissionSummaryWebModel> getList() {
		return list;
	}

	public void setList(List<IbCommissionSummaryWebModel> list) {
		this.list = list;
	}

}
