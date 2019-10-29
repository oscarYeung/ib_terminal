package com.henyep.ib.terminal.api.dto.response.rebate;

import java.io.Serializable;
import java.util.List;

public class IbClientRebateResponseDto implements Serializable {

	private static final long serialVersionUID = 3675302907231363099L;

	private String ib_code;	
	private List<IbClientRebateWithDetails> ibClientRebateList;

	public String getIb_code() {
		return ib_code;
	}

	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}

	public List<IbClientRebateWithDetails> getIbClientRebateList() {
		return ibClientRebateList;
	}

	public void setIbClientRebateList(List<IbClientRebateWithDetails> ibClientRebateList) {
		this.ibClientRebateList = ibClientRebateList;
	}

}
