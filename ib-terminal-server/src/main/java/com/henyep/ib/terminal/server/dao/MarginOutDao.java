package com.henyep.ib.terminal.server.dao;
import java.util.Date;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.MarginOutBean;
import com.henyep.ib.terminal.api.dto.request.marginout.MaxWithdrawalModel;

public interface MarginOutDao{
	public int saveMarginOut(final MarginOutBean marginOut) throws Exception;
	
	public void saveMarginOuts(List<MarginOutBean> marginOuts) throws Exception;

	public List<MarginOutBean> getAllMarginOuts() throws Exception;

	public List<MarginOutBean> getMarginOutByKey(int id) throws Exception;
	
	public List<MarginOutBean> getMarginOutByIds(List<String> ids) throws Exception;
	
	public List<MarginOutBean> getMarginOutsByBatchFileId(String batchFileId) throws Exception;	
	
	public List<MarginOutBean> getMarginOutByIbCodeStatus(String ibCode, String status) throws Exception;
	
	public List<MarginOutBean> getMarginOut(Date startDate, Date endDate, MarginOutBean searchBean) throws Exception;

	public int updateMarginOut(MarginOutBean marginOut) throws Exception;
	
	public MaxWithdrawalModel getMaxWithdrawal(String ib_code) throws Exception;
	
	public List<MaxWithdrawalModel> getAllMaxWithdrawal() throws Exception;

	public int deleteMarginOut(int id) throws Exception;
	
	public List<MarginOutBean> getMarginOutByExample(Date startDate, Date endDate, MarginOutBean marginOutBean) throws Exception;
	
	public List<MarginOutBean> getMarginOutBySalesExample(Date startDate, Date endDate, MarginOutBean marginOutBean, String userCode, String brandCode) throws Exception;
}
