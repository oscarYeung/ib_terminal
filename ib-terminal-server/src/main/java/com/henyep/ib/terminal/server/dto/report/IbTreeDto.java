package com.henyep.ib.terminal.server.dto.report;

import java.util.ArrayList;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.IbTreeBean;

public class IbTreeDto {

	private IbTreeBean ibTreeBean;
	
	private List<IbTreeDto> childs = new ArrayList<IbTreeDto>();
	
	private IbTreeDto parent;

	public IbTreeBean getIbTreeBean() {
		return ibTreeBean;
	}

	public void setIbTreeBean(IbTreeBean ibTreeBean) {
		this.ibTreeBean = ibTreeBean;
	}

	public List<IbTreeDto> getChilds() {
		return childs;
	}

	public void setChilds(List<IbTreeDto> childs) {
		this.childs = childs;
	}

	public IbTreeDto getParent() {
		return parent;
	}

	public void setParent(IbTreeDto parent) {
		this.parent = parent;
	}
	
	
	
}
