package com.henyep.ib.terminal.server.dao;

import java.util.List;
import java.util.Date;
import com.henyep.ib.terminal.api.dto.db.IbCommissionDetailsSupplementaryBean;

public interface IbCommissionDetailsSupplementaryDao {
	public void saveIbCommissionDetailsSupplementary(final IbCommissionDetailsSupplementaryBean ibCommissionDetailsSupplementary) throws Exception;

	public List<IbCommissionDetailsSupplementaryBean> getAllIbCommissionDetailsSupplementarys() throws Exception;

	public IbCommissionDetailsSupplementaryBean getIbCommissionDetailsSupplementaryByKey(String ib_code, Date trade_date, String group_code) throws Exception;
	
	public List<IbCommissionDetailsSupplementaryBean> getIbCommissionDetailsSupplementaryByIbCodeAndDate(String ib_code, Date trade_date) throws Exception;

	public IbCommissionDetailsSupplementaryBean updateIbCommissionDetailsSupplementary(IbCommissionDetailsSupplementaryBean ibCommissionDetailsSupplementary) throws Exception;

	public int deleteIbCommissionDetailsSupplementary(String brand_code, String ib_code, Date trade_date) throws Exception;

	public List<IbCommissionDetailsSupplementaryBean> getIbCommissionDetailsSupplementaryByDateRange(Date start_date, Date end_date) throws Exception;
	
	public List<IbCommissionDetailsSupplementaryBean> getIbCommissionDetailsSupplementaryByGroupCodeIbCodeAndDateRange(List<String> groups, List<String> ibs, Date trade_date) throws Exception;

	public List<IbCommissionDetailsSupplementaryBean> getRecordsByDateRangeStatus(Date start_date, Date end_date, String status) throws Exception;

}
