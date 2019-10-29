package com.henyep.ib.terminal.server.dao;
import java.util.List;
import java.util.Date;
import com.henyep.ib.terminal.api.dto.db.RebateCpaBean;
import com.henyep.ib.terminal.server.dto.dao.IbRebateCpaDto;

public interface RebateCpaDao{
	public void saveRebateCpa(final RebateCpaBean rebateCpa) throws Exception;

	public List<RebateCpaBean> getAllRebateCpas() throws Exception;
	
	public List<RebateCpaBean> getRebateCpasByDateRange(Date startDate, Date endDate) throws Exception;
	
	public List<IbRebateCpaDto> getIbRebateCpaByDateRange(Date start_date, Date end_date) throws Exception;
	
	public List<RebateCpaBean> getRebateCpaByKey(String rebate_code, Date start_date) throws Exception;

	public int updateRebateCpa(RebateCpaBean rebateCpa) throws Exception;

	public int deleteRebateCpa(String rebate_code, Date start_date) throws Exception;
}
