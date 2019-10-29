package com.henyep.ib.terminal.server.service;

import java.util.List;

import com.henyep.ib.terminal.api.dto.db.ClientMarginInOutBean;
import com.henyep.ib.terminal.api.dto.request.client.margin.in.out.ClientMarginInOutRequest;

public interface ClientMarginInOutService {

	public List<ClientMarginInOutBean> getClientMarginInOut(ClientMarginInOutRequest request) throws Exception;
}
