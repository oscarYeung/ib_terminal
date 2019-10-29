package com.henyep.ib.terminal.server.dao;
import java.util.List;
import java.util.Date;
import com.henyep.ib.terminal.api.dto.db.IbTradingAccountMapBean;

public interface IbTradingAccountMapDao{
	public void saveIbTradingAccountMap(final IbTradingAccountMapBean ibTradingAccountMap) throws Exception;

	public List<IbTradingAccountMapBean> getAllIbTradingAccountMaps() throws Exception;

	public List<IbTradingAccountMapBean> getIbTradingAccountMapByKey(String ib_code) throws Exception;

	public int updateIbTradingAccountMap(IbTradingAccountMapBean ibTradingAccountMap) throws Exception;

	public int deleteIbTradingAccountMap(String ib_code) throws Exception;
}
