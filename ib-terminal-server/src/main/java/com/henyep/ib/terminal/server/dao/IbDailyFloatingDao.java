package com.henyep.ib.terminal.server.dao;
import java.util.List;
import java.util.Date;
import com.henyep.ib.terminal.api.dto.db.IbDailyFloatingBean;
import com.henyep.ib.terminal.server.dto.report.ClientFloatingPNLDto;
import com.henyep.ib.terminal.server.dto.report.IbFloatingPNLDto;

public interface IbDailyFloatingDao{
	public void saveIbDailyFloating(final IbDailyFloatingBean ibDailyFloating) throws Exception;
	
	public void saveIbDailyFloatings(List<IbDailyFloatingBean> ibDailyFloatingList, String last_update_user) throws Exception;

	public List<IbDailyFloatingBean> getAllIbDailyFloatings() throws Exception;

	public List<IbDailyFloatingBean> getIbDailyFloatingByKey(String ib_code, Date trade_date) throws Exception;

	public int updateIbDailyFloating(IbDailyFloatingBean ibDailyFloating) throws Exception;

	public int deleteIbDailyFloating(String ib_code, Date trade_date) throws Exception;
	
	public int deleteIbDailyFloatingByTradeDate(String trade_date) throws Exception;
	
	public int deleteIbDailyFloatingByLessThanTradeDate(String trade_date) throws Exception;
	
	public List<IbDailyFloatingBean> getFromSAP(Date trade_date) throws Exception;
	
	public List<IbFloatingPNLDto> getIbFloatingPNLDto(Date start_date, Date end_date) throws Exception;
	
	public List<ClientFloatingPNLDto> getIbClientFloatingPNLDto(Date start_date, Date end_date) throws Exception;
}
