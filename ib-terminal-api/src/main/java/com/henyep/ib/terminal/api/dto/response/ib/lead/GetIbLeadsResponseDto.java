package com.henyep.ib.terminal.api.dto.response.ib.lead;

import java.io.Serializable;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.IbLeadBean;

public class GetIbLeadsResponseDto implements Serializable {

	private static final long serialVersionUID = -3505293491270770474L;

	private List<IbLeadBean> leadList;

	public List<IbLeadBean> getLeadList() {
		return leadList;
	}

	public void setLeadList(List<IbLeadBean> leadList) {
		this.leadList = leadList;
	}

}
