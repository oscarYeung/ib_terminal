package com.henyep.ib.terminal.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.henyep.ib.terminal.api.dto.db.ClientTradeDetailsBean;
import com.henyep.ib.terminal.api.dto.request.trade.GetClientTradeHistoryRequest;
import com.henyep.ib.terminal.api.dto.response.trade.GetClientTradeHistoryResponseDto;
import com.henyep.ib.terminal.server.dao.ClientTradeDetailsDao;
import com.henyep.ib.terminal.server.dao.IbClientMapDao;
import com.henyep.ib.terminal.server.service.TradeService;

@Service(value = "TradeService")
public class TradeServiceImpl implements TradeService {

	@Resource(name = "ClientTradeDetailsDao")
	private ClientTradeDetailsDao clientTradeDetailDao;

	@Resource(name = "IbClientMapDao")
	private IbClientMapDao ibClientMapDao;

	@Override
	public GetClientTradeHistoryResponseDto getClientTradeHistory(GetClientTradeHistoryRequest request) throws Exception {
		Date startDate = request.getBody().getStart_date();
		Date endDate = request.getBody().getEnd_date();

		String clientCode = request.getBody().getClient_code();
		String ibCode = request.getBody().getIb_code();

		List<String> clientCodes = new ArrayList<String>();

		if (ibCode != null) {
			List<String> ibClientCodes = ibClientMapDao.getIbClientMapByIbCode(ibCode);
			if (clientCode == null || clientCode.equals("*")) {
				clientCodes.addAll(ibClientCodes);
			} else {
				if (ibClientCodes.contains(clientCode)) {
					clientCodes.add(clientCode);
				}
			}
		} else {
			clientCodes.add(clientCode);
		}

		List<ClientTradeDetailsBean> beanList = new ArrayList<ClientTradeDetailsBean>();
		if (clientCodes.size() > 0) {
			beanList = clientTradeDetailDao.getClientTradeDetailsByCltCodesDateRange(clientCodes, startDate, endDate);
		}

		GetClientTradeHistoryResponseDto dto = new GetClientTradeHistoryResponseDto();
		dto.setTrades(beanList);

		double totalPl = 0;
		double totalCommission = 0;
		double totalSwap = 0;
		for (ClientTradeDetailsBean bean : beanList) {
			totalCommission += bean.getCommission();
			totalPl += bean.getPl();
			totalSwap += bean.getSwap();
		}
		dto.setTotal_commission(totalCommission);
		dto.setTotal_pl(totalPl);
		dto.setTotal_swap(totalSwap);

		return dto;
	}

}
