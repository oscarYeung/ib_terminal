package com.henyep.ib.terminal.api.dto.response.ib.tree.web;

import java.io.Serializable;
import java.util.List;

public class IbTreeData implements Serializable{
	
	private static final long serialVersionUID = -5799979815252587552L;

	private List<IbModel> data;

	public List<IbModel> getData() {
		return data;
	}

	public void setData(List<IbModel> data) {
		this.data = data;
	}
}
