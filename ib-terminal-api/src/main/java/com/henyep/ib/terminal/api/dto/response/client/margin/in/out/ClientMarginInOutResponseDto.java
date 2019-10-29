package com.henyep.ib.terminal.api.dto.response.client.margin.in.out;

import java.util.List;

import com.henyep.ib.terminal.api.dto.db.ClientMarginInOutBean;

public class ClientMarginInOutResponseDto {

	List<ClientMarginInOutBean> list;

	public List<ClientMarginInOutBean> getList() {
		return list;
	}

	public void setList(List<ClientMarginInOutBean> list) {
		this.list = list;
	}

}
