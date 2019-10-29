package com.henyep.ib.terminal.api.dto.response.ibcommission;

import java.io.Serializable;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.IbCommissionDetailsSupplementaryBean;

public class EvResponseDto implements Serializable {

	private static final long serialVersionUID = -4882937632166141220L;
	private List<IbCommissionDetailsSupplementaryBean> data;

	public List<IbCommissionDetailsSupplementaryBean> getData() {
		return data;
	}

	public void setData(List<IbCommissionDetailsSupplementaryBean> data) {
		this.data = data;
	}

}
