package com.henyep.ib.terminal.api.dto.response.ibcommission.clientSummary;

import java.io.Serializable;
import java.util.List;

public class GetByIbCodeResponseDto implements Serializable {

	private static final long serialVersionUID = -3490503646621243334L;

	private List<IbClientCommissionSummaryWebModel> list;

	public List<IbClientCommissionSummaryWebModel> getList() {
		return list;
	}

	public void setList(List<IbClientCommissionSummaryWebModel> list) {
		this.list = list;
	}
}
