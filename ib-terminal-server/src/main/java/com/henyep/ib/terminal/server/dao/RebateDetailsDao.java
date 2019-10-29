package com.henyep.ib.terminal.server.dao;

import java.util.Date;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.RebateDetailsBean;
import com.henyep.ib.terminal.api.dto.response.clientPackageDetails.ClientPackageSpreadTypeDto;
import com.henyep.ib.terminal.server.dto.dao.ReportIbRebateDto;

public interface RebateDetailsDao {
	public void saveRebateDetails(final RebateDetailsBean rebateDetails) throws Exception;
	public void saveRebateDetails(List<RebateDetailsBean> rebateDetails) throws Exception;

	public List<RebateDetailsBean> getAllRebateDetailss() throws Exception;

	public List<RebateDetailsBean> getRebateDetailsByKey(String rebate_code, String client_package_code,
			Date start_date, String product_group) throws Exception;

	public List<RebateDetailsBean> getRebateDetailsByRebateCodeWithDateRange(String rebate_code, Date start_date,
			Date end_date) throws Exception;

	public int updateRebateDetails(RebateDetailsBean rebateDetails) throws Exception;
	
	public int updateRebateDetails(List<RebateDetailsBean> rebateDetails) throws Exception;

	public int deleteRebateDetails(String rebate_code, String spread_type, double min_lot, String client_package_code, Date start_date,
			String product_group) throws Exception;	
	
	public int deleteRebateDetailsByRebateCodeClientPackageCode(String rebateCode, String clientPackageCode, String spreadType) throws Exception;
	
	public int deleteRebateDetails(List<RebateDetailsBean> rebateDetails) throws Exception;
	
	public List<RebateDetailsBean> getRebateDetailsByRebateCode(String rebate_code) throws Exception;
	
	public List<ClientPackageSpreadTypeDto> getClientPackageCodesByRebateCode(String rebate_code) throws Exception;
	
	public List<ReportIbRebateDto> getReportByExample(ReportIbRebateDto dto);
	
	public List<ReportIbRebateDto> getReportByUserExample(ReportIbRebateDto dto, String userCode, String brandCode) throws Exception;
}
