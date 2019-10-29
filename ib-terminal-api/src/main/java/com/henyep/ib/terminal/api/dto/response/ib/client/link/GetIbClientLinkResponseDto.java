package com.henyep.ib.terminal.api.dto.response.ib.client.link;

import java.io.Serializable;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.IbClientLinkBean;

public class GetIbClientLinkResponseDto implements Serializable {

	private static final long serialVersionUID = -1372139526145028716L;

	private List<IbClientLinkBean> ibClientLinkList;

	public List<IbClientLinkBean> getIbClientLinkList() {
		return ibClientLinkList;
	}

	public void setIbClientLinkList(List<IbClientLinkBean> ibClientLinkList) {
		this.ibClientLinkList = ibClientLinkList;
	}

}
