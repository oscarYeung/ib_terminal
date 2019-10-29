package com.henyep.ib.terminal.server.dao;
import java.util.List;
import java.util.Date;
import com.henyep.ib.terminal.api.dto.db.RebateBean;

public interface RebateDao{
	public void saveRebate(final RebateBean rebate) throws Exception;

	public List<RebateBean> getAllRebates() throws Exception;

	public RebateBean getRebateByKey(int id) throws Exception;
	
	public RebateBean getRebateByRebateCode(String rebate_code) throws Exception;
	
	public RebateBean getRebateByIbCodeWithDateRange(String ib_code, Date start_date, Date end_date) throws Exception;

	public int updateRebate(RebateBean rebate) throws Exception;

	public int deleteRebate(int id) throws Exception;
}
