package com.henyep.ib.terminal.api.dto.response.ib.tree;

import java.io.Serializable;

import com.henyep.ib.terminal.api.dto.response.ib.tree.web.IbTreeData;

public class IbTreeResponseDto implements Serializable {

	private static final long serialVersionUID = 37693919355424674L;
	
	private IbTreeData core;

	public IbTreeData getCore() {
		return core;
	}

	public void setCore(IbTreeData core) {
		this.core = core;
	}
}
