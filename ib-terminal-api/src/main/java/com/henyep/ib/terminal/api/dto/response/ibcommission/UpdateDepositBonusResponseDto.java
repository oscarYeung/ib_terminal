package com.henyep.ib.terminal.api.dto.response.ibcommission;

import java.io.Serializable;

import com.henyep.ib.terminal.api.dto.db.IbCommissionDetailsSupplementaryBean;

public class UpdateDepositBonusResponseDto implements Serializable {

	private static final long serialVersionUID = 8984273490849574460L;
	private IbCommissionDetailsSupplementaryBean data;

	public IbCommissionDetailsSupplementaryBean getData() {
		return data;
	}

	public void setData(IbCommissionDetailsSupplementaryBean data) {
		this.data = data;
	}

}
