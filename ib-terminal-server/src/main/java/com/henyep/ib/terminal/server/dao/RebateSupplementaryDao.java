	package com.henyep.ib.terminal.server.dao;
import java.util.List;
import java.util.Date;
import com.henyep.ib.terminal.api.dto.db.RebateSupplementaryBean;

public interface RebateSupplementaryDao{
	public void saveRebateSupplementary(final RebateSupplementaryBean rebateSupplementary) throws Exception;
	public void saveRebateSupplementary(List<RebateSupplementaryBean> rebateSupplementaries) throws Exception;

	public List<RebateSupplementaryBean> getAllRebateSupplementarys() throws Exception;

	public List<RebateSupplementaryBean> getRebateSupplementaryByKey(String rebate_code, Date start_date) throws Exception;
	
	public List<RebateSupplementaryBean> getRebateSupplementaryByRebateCode(String rebate_code) throws Exception;
	public List<RebateSupplementaryBean> getRebateSupplementaryByRebateCodeWithDateRange(String rebate_code, Date start_date, Date end_date) throws Exception;

	public int updateRebateSupplementary(RebateSupplementaryBean rebateSupplementary) throws Exception;
	public int updateRebateSupplementary(List<RebateSupplementaryBean> rebateSupplementaries) throws Exception;

	public int deleteRebateSupplementary(String rebate_code, Date start_date) throws Exception;
	public int deleteRebateSupplementary(List<RebateSupplementaryBean> rebateSupplementaries) throws Exception;public List<RebateSupplementaryBean> getRebateSupplementaryByDateRange(Date start_date, Date end_date);
	
	public int deleteRebateSupplementary(String rebate_code) throws Exception;
}

