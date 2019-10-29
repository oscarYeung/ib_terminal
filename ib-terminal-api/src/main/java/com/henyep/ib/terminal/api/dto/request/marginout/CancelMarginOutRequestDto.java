package com.henyep.ib.terminal.api.dto.request.marginout;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.henyep.ib.terminal.api.global.Constants;

public class CancelMarginOutRequestDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3792805305293240449L;

	
	@NotNull(message = Constants.ERR_MARGIN_OUT_ID_NOT_BLANK)
	private Integer margin_out_id;


	public Integer getMargin_out_id() {
		return margin_out_id;
	}


	public void setMargin_out_id(Integer margin_out_id) {
		this.margin_out_id = margin_out_id;
	}
}
