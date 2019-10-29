package com.henyep.ib.terminal.api.dto.request.ibcommission;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

public class EvConfirmRequestDto implements Serializable {

	private static final long serialVersionUID = -7866353782353673416L;

	@Valid
	private List<EvConfirmDto> data;

	public List<EvConfirmDto> getData() {
		return data;
	}

	public void setData(List<EvConfirmDto> data) {
		this.data = data;
	}

}
