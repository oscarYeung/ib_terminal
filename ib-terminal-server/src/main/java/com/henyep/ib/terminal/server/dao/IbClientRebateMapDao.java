package com.henyep.ib.terminal.server.dao;

import java.util.List;
import java.util.Date;
import com.henyep.ib.terminal.api.dto.db.IbClientRebateMapBean;
import com.henyep.ib.terminal.api.dto.db.IbProfileBean;
import com.henyep.ib.terminal.server.dto.dao.IbRebateGroupCodeDto;

public interface IbClientRebateMapDao {

	public void saveIbClientRebateMap(IbProfileBean ibProfileBean, Date startDate, Date endDate) throws Exception;

	public void saveIbClientRebateMap(final IbClientRebateMapBean ibClientRebateMap) throws Exception;

	public List<IbClientRebateMapBean> getAllIbClientRebateMaps() throws Exception;

	public List<IbClientRebateMapBean> getIbClientRebateMapByKey(String client_code, String ib_code, Date start_date, String brand_code)
			throws Exception;

	public int updateIbClientRebateMap(IbClientRebateMapBean ibClientRebateMap) throws Exception;

	public int deleteIbClientRebateMap(String client_code, String ib_code, Date start_date, String brand_code) throws Exception;

	public List<IbClientRebateMapBean> getIbClientRebateMapByIbCodeWithDateRange(String brand_code, String ib_code, Date start_date, Date end_date)
			throws Exception;

	public List<IbClientRebateMapBean> getIbClientRebateMapByRebateCodeWithDateRange(String rebate_code, Date start_date, Date end_date)
			throws Exception;

	public List<IbClientRebateMapBean> getByRebateCodeList(List<String> rebateCodeList) throws Exception;

	public List<IbClientRebateMapBean> getByRebateCodeList(List<String> rebateCodeList, Date start_date, Date end_date) throws Exception;

	public List<IbClientRebateMapBean> getByIbCode(String ibCode) throws Exception;

	public List<IbRebateGroupCodeDto> getIbRebateGroupCodeList(Date start_date, Date end_date) throws Exception;

}
