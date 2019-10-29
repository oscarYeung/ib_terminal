package com.henyep.ib.terminal.server.dao;
import java.util.List;
import java.util.Date;
import com.henyep.ib.terminal.api.dto.db.IbProfileHistoryBean;

public interface IbProfileHistoryDao{
	public void saveIbProfileHistory(final IbProfileHistoryBean ibProfileHistory) throws Exception;

	public List<IbProfileHistoryBean> getAllIbProfileHistorys() throws Exception;

	public List<IbProfileHistoryBean> getIbProfileHistoryByKey() throws Exception;

	public int updateIbProfileHistory(IbProfileHistoryBean ibProfileHistory) throws Exception;

	public int deleteIbProfileHistory() throws Exception;
}
