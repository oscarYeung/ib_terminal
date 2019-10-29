package com.henyep.ib.terminal.server.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.henyep.ib.terminal.api.dto.db.ClientMarginInOutBean;
import com.henyep.ib.terminal.api.dto.request.client.margin.in.out.ClientMarginInOutRequest;
import com.henyep.ib.terminal.server.dao.ClientMarginInOutDao;
import com.henyep.ib.terminal.server.global.Constants;
import com.henyep.ib.terminal.server.service.ClientMarginInOutService;

@Service(value = "ClientMarginInOutService")
public class ClientMarginInOutServiceImpl implements ClientMarginInOutService {
	@Resource(name = "ClientMarginInOutDao")
	private ClientMarginInOutDao clientMarginInOutDao;

	@Override
	public List<ClientMarginInOutBean> getClientMarginInOut(ClientMarginInOutRequest request) throws Exception {

		String ibCode = request.getBody().getIb_code();
		String clientCode = request.getBody().getClient_code();
		Date startDate = request.getBody().getStart_date();
		Date endDate = request.getBody().getEnd_date();
		if (clientCode.equals(Constants.TYPE_ALL)) {
			// get by ib code
			return clientMarginInOutDao.getByIbCode(ibCode, startDate, endDate);
		} else {
			// get by client code
			return clientMarginInOutDao.getByClientCode(ibCode, clientCode, startDate, endDate);
		}
	}

}
