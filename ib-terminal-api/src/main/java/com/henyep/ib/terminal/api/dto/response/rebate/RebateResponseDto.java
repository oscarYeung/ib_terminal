package com.henyep.ib.terminal.api.dto.response.rebate;

import java.io.Serializable;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.IbClientRebateMapBean;
import com.henyep.ib.terminal.api.dto.db.RebateBean;
import com.henyep.ib.terminal.api.dto.db.RebateDetailsBean;
import com.henyep.ib.terminal.api.dto.db.RebateSupplementaryBean;

public class RebateResponseDto implements Serializable {

	private static final long serialVersionUID = -6031056533797648499L;
	
	private List<IbClientRebateMapBean> ibClientRebateMaps;
	private RebateBean rebate;
	private List<RebateDetailsBean> rebateDetailsList;
	private List<RebateSupplementaryBean> rebateSupplementaryList;
	

	public List<RebateSupplementaryBean> getRebateSupplementaryList() {
		return rebateSupplementaryList;
	}

	public void setRebateSupplementaryList(List<RebateSupplementaryBean> rebateSupplementaryList) {
		this.rebateSupplementaryList = rebateSupplementaryList;
	}
	
	public List<IbClientRebateMapBean> getIbClientRebateMaps() {
		return ibClientRebateMaps;
	}

	public void setIbClientRebateMaps(List<IbClientRebateMapBean> ibClientRebateMap) {
		this.ibClientRebateMaps = ibClientRebateMap;
	}

	public RebateBean getRebate() {
		return rebate;
	}

	public void setRebate(RebateBean rebate) {
		this.rebate = rebate;
	}

	public List<RebateDetailsBean> getRebateDetailsList() {
		return rebateDetailsList;
	}

	public void setRebateDetailsList(List<RebateDetailsBean> rebateDetailsList) {
		this.rebateDetailsList = rebateDetailsList;
	}

}
