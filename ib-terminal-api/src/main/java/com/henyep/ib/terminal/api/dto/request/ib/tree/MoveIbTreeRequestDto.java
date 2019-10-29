package com.henyep.ib.terminal.api.dto.request.ib.tree;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.henyep.ib.terminal.api.global.Constants;

public class MoveIbTreeRequestDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5909960798572924337L;
	
	@NotNull(message = Constants.ERR_IB_TREE_FROM_ID_NOT_EXIST)
	private Integer from_ib_id;
	@NotNull(message = Constants.ERR_IB_TREE_TO_ID_NOT_EXIST)
	private Integer to_ib_id;
	
	public Integer getFrom_ib_id() {
		return from_ib_id;
	}
	public void setFrom_ib_id(Integer from_ib_id) {
		this.from_ib_id = from_ib_id;
	}
	public Integer getTo_ib_id() {
		return to_ib_id;
	}
	public void setTo_ib_id(Integer to_ib_id) {
		this.to_ib_id = to_ib_id;
	}
	
	

}
