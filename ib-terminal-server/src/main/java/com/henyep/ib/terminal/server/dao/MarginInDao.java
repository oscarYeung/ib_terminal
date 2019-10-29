package com.henyep.ib.terminal.server.dao;
import java.util.List;
import java.util.Date;
import com.henyep.ib.terminal.api.dto.db.MarginInBean;
import com.henyep.ib.terminal.server.dto.marginInOut.MarginInOutDto;

public interface MarginInDao{
	public int saveMarginIn(final MarginInBean marginIn) throws Exception;
	
	public void saveMarginIns(final List<MarginInBean> marginInBeans) throws Exception;

	public List<MarginInBean> getAllMarginIns() throws Exception;

	public List<MarginInBean> getMarginInByKey(int id) throws Exception;
	
	public List<MarginInBean> getMarginInByKeys(List<String> ids) throws Exception;

	public int updateMarginIn(MarginInBean marginIn) throws Exception;

	public int deleteMarginIn(int id) throws Exception;
	
	public int deleteMarginInDateByDateRange(Date startDate, Date endDate, String comment, String updatedBy) throws Exception;
	
	public int deleteMarginInDateByDateRangeIbCodes(Date startDate, Date endDate, List<String> ibCodes, String comment, String updatedBy) throws Exception;
	
	public List<MarginInBean> getMarginInByExample(Date startDate, Date endDate, MarginInBean marginInBean) throws Exception;

	public List<MarginInBean> getMarginInByUserCodeExample(Date startDate, Date endDate, MarginInBean marginInBean, String userCode, String brandCode) throws Exception;
	
	public List<MarginInOutDto> getMonthlyMarginInOutReport(Date start_date, Date end_date, String brand_code, List<String> ib_codes) throws Exception;
}
