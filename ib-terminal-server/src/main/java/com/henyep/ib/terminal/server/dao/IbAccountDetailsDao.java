package com.henyep.ib.terminal.server.dao;
import java.util.Date;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.IbAccountDetailsBean;
import com.henyep.ib.terminal.api.dto.response.ibcommission.IbAccountModel;

public interface IbAccountDetailsDao{
	public void saveIbAccountDetails(final IbAccountDetailsBean ibAccountDetails) throws Exception;

	public List<IbAccountDetailsBean> getAllIbAccountDetailss() throws Exception;

	public List<IbAccountDetailsBean> getIbAccountDetailsByKey(String ib_code) throws Exception;

	public int updateIbAccountDetails(IbAccountDetailsBean ibAccountDetails) throws Exception;

	public int deleteIbAccountDetails(String ib_code, String currency) throws Exception;
	
	public int resetPendingCommissions(Date trade_date, List<String> ibCodes) throws Exception;
	
	public int resetAccBalance(Date trade_date, List<String> ibCodes) throws Exception;
	
	public IbAccountModel getIbAccountDetail(String ib_code) throws Exception;
	
	public void updateIbAccountAccumlatedNetMargin(String ib_code, double accumlatedNetMargin) throws Exception;
	
	public void updateDayOpen() throws Exception;
	
	public void updateAccountBalanceByMarginOutBatchFileId(String batchFileId) throws Exception;
	
}
