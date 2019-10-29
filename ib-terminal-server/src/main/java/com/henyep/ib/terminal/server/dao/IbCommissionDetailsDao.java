package com.henyep.ib.terminal.server.dao;

import java.util.Date;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.IbCommissionDetailsBean;
import com.henyep.ib.terminal.api.dto.response.ibcommission.details.IbCommissionDetailsWebModel;
import com.henyep.ib.terminal.server.dto.report.ClientMarginInOutReportDto;
import com.henyep.ib.terminal.server.dto.report.IbMarginInOutReportDto;
import com.henyep.ib.terminal.server.dto.report.IbRebateReportDto;
import com.henyep.ib.terminal.server.dto.report.IbTradeAmountReportDto;

public interface IbCommissionDetailsDao {
	public void saveIbCommissionDetails(List<IbCommissionDetailsBean> ibCommissionDetails) throws Exception;

	public List<IbCommissionDetailsBean> getAllIbCommissionDetailss() throws Exception;

	public int updateIbCommissionDetails(IbCommissionDetailsBean ibCommissionDetails) throws Exception;

	public int deleteIbCommissionDetails(Date startDate, Date endDate) throws Exception;

	public List<IbCommissionDetailsBean> getIbCommissionDetailsByKey(String platform, String client_code, String ticket, String ib_code,
			String brand_code) throws Exception;

	public List<IbCommissionDetailsWebModel> getIbCommissionDetailsByDateRange(String ib_code, String client_code, Date start_date, Date end_date)
			throws Exception;

	public List<IbCommissionDetailsBean> getSummaryByIbCodeListDateRange(List<String> ibCodeList, Date start_date, Date end_date) throws Exception;

	public int deleteIbCommissionDetails(Date startDate, Date endDate, List<String> ibCodes) throws Exception;
	
	public List<IbCommissionDetailsBean> getSummaryByBrandCodeIbCodeDateRange(Date start_date, Date end_date, String brandCode, List<String> ibCodes, String sender) throws Exception;
	
	public List<IbCommissionDetailsBean> getIbCommissionSummaryWithGroupCode(List<String> ibCodeList, Date start_date, Date end_date);
	
	public List<IbRebateReportDto> getIbRebateReport(List<String> ib_codes, String user, Date start_date, Date end_date, String brand_code);
	
	public List<IbTradeAmountReportDto> getIbTradeAccountReport(List<String> ib_codes, String user, Date start_date, Date end_date, String brand_code);
	
	public List<IbMarginInOutReportDto> getIbMarginInOutReport(List<String> ib_codes, String user, Date start_date, Date end_date, String brand_code);
	
	public List<ClientMarginInOutReportDto> getClientMarginInOutReport(List<String> ib_codes, String user, Date start_date, Date end_date, String brand_code);
}
