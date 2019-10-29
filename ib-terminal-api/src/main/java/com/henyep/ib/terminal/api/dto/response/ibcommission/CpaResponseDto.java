package com.henyep.ib.terminal.api.dto.response.ibcommission;

import java.io.Serializable;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.IbCommissionCpaBean;

public class CpaResponseDto implements Serializable {

	private static final long serialVersionUID = -6527448162327987117L;
	private List<IbCommissionCpaBean> data;

	public List<IbCommissionCpaBean> getData() {
		return data;
	}

	public void setData(List<IbCommissionCpaBean> data) {
		this.data = data;
	}

}
