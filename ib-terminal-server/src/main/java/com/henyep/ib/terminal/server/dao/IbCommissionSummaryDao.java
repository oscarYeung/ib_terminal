package com.henyep.ib.terminal.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.henyep.ib.terminal.api.dto.db.IbCommissionSummaryBean;
import com.henyep.ib.terminal.api.dto.response.ibcommission.IbMonthBalanceModel;
import com.henyep.ib.terminal.api.dto.response.ibcommission.IbSummaryByDateRangeModel;
import com.henyep.ib.terminal.api.dto.response.ibcommission.summary.IbCommissionSummaryWebModel;
import com.henyep.ib.terminal.api.dto.response.user.CurrentMothData;
import com.henyep.ib.terminal.server.dto.report.IbProductGroupSummaryDto;
import com.henyep.ib.terminal.server.dto.report.IbSummaryDto;
import com.henyep.ib.terminal.server.dto.report.OwnerIbDto;

public interface IbCommissionSummaryDao {

	public void generateIbCommissionSummarys(Date startDate, Date endDate) throws Exception;

	public List<IbCommissionSummaryBean> getAllIbCommissionSummarys() throws Exception;

	public List<IbCommissionSummaryBean> getIbCommissionSummaryByKey(String platform, Date trade_date,
			String product_code, String ib_code, String brand_code) throws Exception;

	public int updateIbCommissionSummary(IbCommissionSummaryBean ibCommissionSummay) throws Exception;

	public int deleteIbCommissionSummary(String platform, Date trade_date, String product_code, String ib_code,
			String brand_code) throws Exception;
	
	public int deleteIbCommissionSummaryByDateRange(Date start_date, Date end_date) throws Exception;
	
	public int deleteIbCommissionSummaryByDateRangeIbCodes(Date start_date, Date end_date, List<String> ib_codes) throws Exception;

	public CurrentMothData calculateIbCommissionByDate(String ibCode, String startDate, String endDate)
			throws Exception;

	public List<IbCommissionSummaryWebModel> getIbCommissionSummaryByTeamHead(String ibCode, Date startDate, Date endDate)
			throws Exception;
	
	public List<IbSummaryByDateRangeModel> getIbSummaryByDateRange(String ibCode, Date startDate, Date endDate)
			throws Exception;
	
	public IbMonthBalanceModel getTrimmedIbSummaryByDateRange(String ibCode, Date startDate, Date endDate)
			throws Exception;
	
	public List<IbSummaryByDateRangeModel> getIbSummaryByLastTradeDate(String ibCode)
			throws Exception;

	public Date getLastTradeDate();
	
	
	public List<IbCommissionSummaryBean> getIbCommissionSummarysByBrandCodeIbCodeDateRange(Date start_date, Date end_date, String brand_code, List<String> ib_codes, String user_code) throws Exception;
	
	public List<IbCommissionSummaryBean> getIbCommissionPeriodSummary(Date start_date, Date end_date, String brand_code, List<String> ib_codes, String user_code) throws Exception;
	
	public List<IbSummaryDto> getIbCommissionSummarysByIbCodeDateRange(Date start_date, Date end_date, String brand_code, String ib_code) throws Exception;
	
	public List<IbSummaryDto> getIbCommissionSummarysByUserCode(Date start_date, Date end_date, String brand_code, List<String> ib_code, String sales_code, String jurisdiction) throws Exception;
	
	public List<IbProductGroupSummaryDto> getIbCommissionSummarysProductGroup(Date start_date, Date end_date, String brand_code, List<String> ib_code, String sales_code, String jurisdiction) throws Exception;
	
	public List<OwnerIbDto> getOwnerIbDtos() throws Exception;
	
	public Map<String, String> getIbCommissionSummaryRelatedOwners(Date startDate, Date endDate, String brandCode, String sender) throws Exception;
}
