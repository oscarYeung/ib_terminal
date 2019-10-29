package com.henyep.ib.terminal.api.dto.response.user;

import java.io.Serializable;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.IbAccountDetailsBean;
import com.henyep.ib.terminal.api.dto.db.IbClientRebateMapBean;
import com.henyep.ib.terminal.api.dto.db.IbProfileBean;
import com.henyep.ib.terminal.api.dto.db.RebateBean;

public class IbCreateRespDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -515113420788363985L;
	
	private IbProfileBean ibProfilebean;
	private IbAccountDetailsBean ibAccountDetailsBean;
	private RebateBean rebateBean;
	private List<IbClientRebateMapBean> ibClientRebateMapBeans;

	public RebateBean getRebateBean() {
		return rebateBean;
	}

	public void setRebateBean(RebateBean rebateBean) {
		this.rebateBean = rebateBean;
	}

	public List<IbClientRebateMapBean> getIbClientRebateMapBean() {
		return ibClientRebateMapBeans;
	}

	public void setIbClientRebateMapBean(List<IbClientRebateMapBean> ibClientRebateMapBeans) {
		this.ibClientRebateMapBeans = ibClientRebateMapBeans;
	}

	public IbProfileBean getIbProfilebean() {
		return ibProfilebean;
	}

	public void setIbProfilebean(IbProfileBean ibProfilebean) {
		this.ibProfilebean = ibProfilebean;
	}
	
	


	public IbAccountDetailsBean getIbAccountDetailsBean() {
		return ibAccountDetailsBean;
	}

	public void setIbAccountDetailsBean(IbAccountDetailsBean ibAccountDetailsBean) {
		this.ibAccountDetailsBean = ibAccountDetailsBean;
	}
	
	
	
}
