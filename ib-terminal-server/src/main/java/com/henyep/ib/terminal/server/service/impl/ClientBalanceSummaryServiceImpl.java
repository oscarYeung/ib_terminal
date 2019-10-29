package com.henyep.ib.terminal.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.henyep.ib.terminal.api.dto.db.ClientBalanceSummaryBean;
import com.henyep.ib.terminal.api.dto.request.client.balance.summary.UpdateAccountBalanceRequest;
import com.henyep.ib.terminal.api.dto.response.client.balance.summary.UpdateAccountBalanceResponseDto;
import com.henyep.ib.terminal.server.dao.ClientBalanceSummaryDao;
import com.henyep.ib.terminal.server.service.ClientBalanceSummaryService;

@Service(value = "ClientBalanceSummaryService")
public class ClientBalanceSummaryServiceImpl implements ClientBalanceSummaryService{

	@Resource(name = "ClientBalanceSummaryDao")
	private ClientBalanceSummaryDao clientBalanceSummaryDao;
	
	@Override
	public UpdateAccountBalanceResponseDto updateAccountBalance(UpdateAccountBalanceRequest request, String updatedBy) throws Exception {

		String clientCode = request.getBody().getClient_code();
		Double accountBalance = request.getBody().getAccount_balance();
		
		clientBalanceSummaryDao.updateAccountBalance(clientCode, accountBalance, updatedBy);
		
		UpdateAccountBalanceResponseDto responseDto = new UpdateAccountBalanceResponseDto();
			
		List<ClientBalanceSummaryBean> beanList = clientBalanceSummaryDao.getLatestClientBalanceSummary(clientCode);
		if(beanList.size() > 0){
			responseDto.setClient_balance_summary(beanList.get(0));
		}
		else {
			responseDto.setClient_balance_summary(null);
		}
		return responseDto;
	}
	
}
