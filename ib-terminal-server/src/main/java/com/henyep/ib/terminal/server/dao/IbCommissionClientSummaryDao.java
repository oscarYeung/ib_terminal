package com.henyep.ib.terminal.server.dao;

import java.util.Date;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.IbCommissionClientSummaryBean;
import com.henyep.ib.terminal.api.dto.response.ibcommission.IbClientSummariesModel;
import com.henyep.ib.terminal.api.dto.response.ibcommission.IbCommissionDetailsModel;
import com.henyep.ib.terminal.api.dto.response.ibcommission.IbCommissionModel;
import com.henyep.ib.terminal.api.dto.response.ibcommission.clientSummary.IbClientCommissionSummaryWebModel;
import com.henyep.ib.terminal.server.dto.report.ClientSummaryPeriodDto;
public interface IbCommissionClientSummaryDao {

	public void saveIbCommissionClientSummary(IbCommissionClientSummaryBean ibCommissionClientSummary) throws Exception;

	public void generateIbCommissionClientSummarys(Date startDate, Date endDate) throws Exception;

	public List<IbCommissionClientSummaryBean> getAllIbCommissionClientSummarys() throws Exception;

	public List<IbCommissionClientSummaryBean> getIbCommissionClientSummaryByKey(String client_ib_code, String platform, String client_code,
			Date trade_date, String ib_code, String product_group, String brand_code) throws Exception;

	public int updateIbCommissionClientSummary(IbCommissionClientSummaryBean ibCommissionClientSummary) throws Exception;

	public int deleteIbCommissionClientSummary(String client_ib_code, String platform, String client_code, Date trade_date, String ib_code,
			String product_group, String brand_code) throws Exception;

	public List<IbClientCommissionSummaryWebModel> getIbClientCommissionSummaryByIbCodeDate(String ib_code, Date start_date, Date end_date)
			throws Exception;

	public List<IbCommissionClientSummaryBean> getIbCommissionClientSummaryByIbCodeClientIbCode(String ib_code, String client_ib_code, Date startDate, Date endDate) throws Exception;
	
	public List<IbCommissionClientSummaryBean> getIbCommissionClientSummaryByBrandCodeIbCodeDateRange(Date startDate, Date endDate, String brand_code, List<String> ib_codes, String sender) throws Exception;
	
	public List<ClientSummaryPeriodDto> getIbCommissionClientPeriodSummary(Date startDate, Date endDate, String brand_code, List<String> ib_codes, String sender) throws Exception;
	
	public List<IbClientSummariesModel> getIbClientSummary(String ibCode, Date startDate, Date endDate) throws Exception;

	public int deleteIbCommissionClientSummaryByDateRange(Date start_date, Date end_date) throws Exception;

	public int deleteIbCommissionClientSummaryByDateRangeIbCodes(Date start_date, Date end_date, List<String> ib_Codes) throws Exception;
	
	public List<IbCommissionModel> getIbCommissionSummary(String ibCode, Date startDate, Date endDate) throws Exception;
	
	public List<IbCommissionDetailsModel> getIbCommissionSummaryDetails(String ibCode, Date startDate, Date endDate) throws Exception;
	
}
