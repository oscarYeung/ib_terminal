package com.henyep.ib.terminal.api.dto.response.ib.tree;

import java.io.Serializable;

import com.henyep.ib.terminal.api.dto.db.IbTreeBean;

public class SearchIbResponseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5638315037766027158L;

	private IbTreeBean ib_tree;

	private Boolean in_ib_tree;

	public Boolean getIn_ib_tree() {
		return in_ib_tree;
	}

	public IbTreeBean getIb_tree() {
		return ib_tree;
	}

	public void setIb_tree(IbTreeBean ib_tree) {
		this.ib_tree = ib_tree;
	}

	public void setIn_ib_tree(Boolean in_ib_tree) {
		this.in_ib_tree = in_ib_tree;
	}

}
