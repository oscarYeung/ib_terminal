package com.henyep.ib.terminal.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.henyep.ib.terminal.api.dto.db.IbClientMapBean;
import com.henyep.ib.terminal.server.dto.dao.IbClientMapDto;
import com.henyep.ib.terminal.server.dto.dao.ReportIbClientMapDto;

public interface IbClientMapDao {
	public List<String> getIbClientMapByIbCode(String ib_code) throws Exception;

	public List<IbClientMapBean> getIbClientMapBeanByIbCode(String ib_code) throws Exception;

	public List<IbClientMapBean> getIbClientMapByClientCodes(List<Integer> clientCodes) throws Exception;

	public void saveIbClientMap(IbClientMapBean ibClientMap) throws Exception;
	
	public void saveIbClientMap(List<IbClientMapBean> ibClientMaps) throws Exception;

	public int updateIbClientMap(IbClientMapBean ibClientMap) throws Exception;

	public List<IbClientMapBean> getAllIbClientMaps() throws Exception;

	public List<IbClientMapBean> getIbClientMapByKey(String ib_code, String client_code, Date start_date) throws Exception;

	public int deleteIbClientMap(String client_code, String ib_code) throws Exception;

	public List<IbClientMapBean> getByIbCodeDateRange(String ib_code, Date trade_date) throws Exception;

	public List<String> getClientCodesByIbTeamHeads(List<String> ib_team_heads) throws Exception;

	public List<ReportIbClientMapDto> getReportByExample(ReportIbClientMapDto dto) throws Exception;

	public List<ReportIbClientMapDto> getReportByUserExample(ReportIbClientMapDto dto, String user_code, String brand_code) throws Exception;
	
	public List<IbClientMapBean> getIbClientMapByIbCodeClientCode(String ib_code, String client_code) throws Exception;
	
	public List<IbClientMapDto> getIbClientMapByCreateDate(Date createDate) throws Exception;
	
	public Map<String, List<IbClientMapBean>> getIbClientListByIbCodes(List<String> ibCodeList, List<String> excludedClientList, Date start_date, Date end_date) throws Exception;
	
	public int deleteIbClientMapByTradeDate(Date trade_date) throws Exception;
	
	public Integer getClientCountByIbCodeDateRange(String ib_code, Date trade_date);
}
