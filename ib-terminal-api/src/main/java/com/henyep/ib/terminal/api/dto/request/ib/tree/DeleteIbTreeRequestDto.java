package com.henyep.ib.terminal.api.dto.request.ib.tree;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.henyep.ib.terminal.api.global.Constants;

public class DeleteIbTreeRequestDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4909091663896576699L;
	
	@NotNull(message = Constants.ERR_IB_TREE_ID_NOT_EXIST)
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

}
