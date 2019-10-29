package com.henyep.ib.terminal.api.dto.request.ibcommission;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import com.henyep.ib.terminal.api.dto.request.BaseDateTimeRequestBodyDto;

public class EvFigureUpdateRequestDto extends BaseDateTimeRequestBodyDto implements Serializable {

	private static final long serialVersionUID = -6526790905600250793L;

	@Valid
	private List<EvFigureUpdateDto> data;

	public List<EvFigureUpdateDto> getData() {
		return data;
	}

	public void setData(List<EvFigureUpdateDto> data) {
		this.data = data;
	}

}
