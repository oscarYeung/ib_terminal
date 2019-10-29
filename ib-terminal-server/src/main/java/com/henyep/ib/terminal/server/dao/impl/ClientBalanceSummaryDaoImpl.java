package com.henyep.ib.terminal.server.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.ClientBalanceSummaryBean;
import com.henyep.ib.terminal.server.dao.ClientBalanceSummaryDao;
import com.henyep.ib.terminal.server.dto.dao.ClientDeficitDto;
import com.henyep.ib.terminal.server.dto.dao.ClientEquityChangeDto;
import com.henyep.ib.terminal.server.dto.dao.GroupClientTradeDto;
import com.henyep.ib.terminal.server.dto.dao.GroupDeficitDto;
import com.henyep.ib.terminal.server.dto.dao.GroupEquityChangeDto;
import com.henyep.ib.terminal.server.util.DateUtil;

@Repository(value = "ClientBalanceSummaryDao")
public class ClientBalanceSummaryDaoImpl implements ClientBalanceSummaryDao {

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "sapJdbcTemplate")
	JdbcTemplate sapJdbcTemplate;

	@Resource(name = "ClientBalanceSummaryDao_SQLMap")
	Map<String, String> map;

	public ClientBalanceSummaryDaoImpl() throws Exception {
		super();
	}

	@Override
	public void saveClientBalanceSummary(ClientBalanceSummaryBean clientBalanceSummary) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[] { clientBalanceSummary.getClient_code(), clientBalanceSummary.getCurrency(), clientBalanceSummary.getTrade_date(), clientBalanceSummary.getBalance(),
				clientBalanceSummary.getEquity(), clientBalanceSummary.getFloating(), clientBalanceSummary.getDeposit(),
				clientBalanceSummary.getWithdrawal(), clientBalanceSummary.getCredit(), clientBalanceSummary.getPl(),
				clientBalanceSummary.getPl_adjustment(), clientBalanceSummary.getCommission(), clientBalanceSummary.getSwap(),
				clientBalanceSummary.getTax(), clientBalanceSummary.getPrevious_credit(), clientBalanceSummary.getPrevious_balance(),
				clientBalanceSummary.getPrevious_equity(), clientBalanceSummary.getPrevious_floating() };
		int res = this.jdbcTemplate.update(sql, objs);

	}

	@Override
	public void saveClientBalanceSummaryList(List<ClientBalanceSummaryBean> clientBalanceSummaryList) throws Exception {

		int batchSize = 5000;
		List<List<ClientBalanceSummaryBean>> beanBatchList = new ArrayList<List<ClientBalanceSummaryBean>>();

		while (clientBalanceSummaryList.size() > batchSize) {
			List<ClientBalanceSummaryBean> subList = clientBalanceSummaryList.subList(0, batchSize);
			beanBatchList.add(subList);
			clientBalanceSummaryList = clientBalanceSummaryList.subList(batchSize, clientBalanceSummaryList.size());

		}
		if (clientBalanceSummaryList.size() > 0) {
			beanBatchList.add(clientBalanceSummaryList);
		}

		for (List<ClientBalanceSummaryBean> beanList : beanBatchList) {

			String sql = map.get("create");

			int contentPos = sql.indexOf("(?");
			String insertStatement = sql.substring(0, contentPos);
			String valueContent = sql.substring(contentPos, sql.length());

			List<String> valueContentList = new ArrayList<String>();
			ArrayList<Object> objList = new ArrayList<Object>();

			for (ClientBalanceSummaryBean clientBalanceSummary : beanList) {
				Object[] objs = new Object[] { clientBalanceSummary.getClient_code(), clientBalanceSummary.getCurrency(), clientBalanceSummary.getTrade_date(),
						clientBalanceSummary.getBalance(), clientBalanceSummary.getEquity(), clientBalanceSummary.getFloating(),
						clientBalanceSummary.getDeposit(), clientBalanceSummary.getWithdrawal(), clientBalanceSummary.getCredit(),
						clientBalanceSummary.getPl(), clientBalanceSummary.getPl_adjustment(), clientBalanceSummary.getCommission(),
						clientBalanceSummary.getSwap(), clientBalanceSummary.getTax(), clientBalanceSummary.getPrevious_credit(),
						clientBalanceSummary.getPrevious_balance(), clientBalanceSummary.getPrevious_equity(),
						clientBalanceSummary.getPrevious_floating() };
				objList.addAll(Arrays.asList(objs));
				valueContentList.add(valueContent);
			}

			sql = insertStatement + StringUtils.join(valueContentList, ",");
			int res = this.jdbcTemplate.update(sql, objList.toArray());
		}
	}

	@Override
	public int updateClientBalanceSummary(ClientBalanceSummaryBean clientBalanceSummary) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[] { clientBalanceSummary.getBalance(), clientBalanceSummary.getCurrency(), clientBalanceSummary.getEquity(), clientBalanceSummary.getFloating(),
				clientBalanceSummary.getDeposit(), clientBalanceSummary.getWithdrawal(), clientBalanceSummary.getCredit(),
				clientBalanceSummary.getPl_adjustment(), clientBalanceSummary.getCommission(), clientBalanceSummary.getSwap(),
				clientBalanceSummary.getTax(), clientBalanceSummary.getPrevious_credit(), clientBalanceSummary.getPrevious_balance(),
				clientBalanceSummary.getPrevious_equity(), clientBalanceSummary.getPrevious_floating(), clientBalanceSummary.getLast_update_date(),
				clientBalanceSummary.getLast_update_user(), clientBalanceSummary.getClient_code(), clientBalanceSummary.getTrade_date() };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<ClientBalanceSummaryBean> getAllClientBalanceSummarys() throws Exception {
		String sql = map.get("selectAll");
		List<ClientBalanceSummaryBean> beans = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<ClientBalanceSummaryBean>(ClientBalanceSummaryBean.class));
		return beans;
	}

	@Override
	public List<ClientBalanceSummaryBean> getClientBalanceSummaryByKey(String client_code, Date trade_date) throws Exception {
		final String sql = map.get("selectByKey");
		String tradeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, trade_date);
		Object[] objs = new Object[] { client_code, tradeDateString };
		List<ClientBalanceSummaryBean> beans = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<ClientBalanceSummaryBean>(ClientBalanceSummaryBean.class), objs);
		return beans;
	}

	@Override
	public int deleteClientBalanceSummary(String client_code, Date trade_date) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[] { client_code, trade_date };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public ClientEquityChangeDto getEquityChangeByIbDayRange(String ib_code, Date start_date, Date end_date) throws Exception {
		final String sql = map.get("getEquityChange");
		Object[] objs = new Object[] { start_date, end_date, ib_code, start_date, end_date, end_date };
		List<ClientEquityChangeDto> list = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ClientEquityChangeDto>(ClientEquityChangeDto.class),
				objs);
		return list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public List<ClientDeficitDto> getClientDeficit(String ib_code, Date start_date, Date end_date) throws Exception {
		final String sql = map.get("getDeficit");
		// Calendar calendar = Calendar.getInstance();
		// calendar.setTime(start_date);
		// calendar.set(Calendar.DAY_OF_MONTH, 1);
		// calendar.add(Calendar.DATE, -1);
		// Date lastMonthEnd = calendar.getTime();
		Object[] objs = new Object[] { start_date, end_date, ib_code, start_date, end_date, end_date };
		List<ClientDeficitDto> list = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ClientDeficitDto>(ClientDeficitDto.class), objs);
		return list;
	}

	@Override
	public List<ClientBalanceSummaryBean> getClientBalanceSummaryFromSAP(Date trade_date) {
		final String sql = map.get("getFromSAP");
		String dateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, trade_date);
		Object[] objs = new Object[] { dateString };
		List<ClientBalanceSummaryBean> beans = this.sapJdbcTemplate.query(sql,
				new BeanPropertyRowMapper<ClientBalanceSummaryBean>(ClientBalanceSummaryBean.class), objs);
		return beans;
	}

	@Override
	public int deleteClientBalanceSummaryByTradeDate(Date trade_date) {
		final String sql = map.get("deleteByTradeDate");
		String dateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, trade_date);
		Object[] objs = new Object[] { dateString };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
	
	@Override
	public int deleteClientBalanceSummaryByLessThanTradeDate(Date trade_date) {
		final String sql = map.get("deleteByLessThanTradeDate");
		String dateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, trade_date);
		Object[] objs = new Object[] { dateString };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<GroupDeficitDto> getGroupDeficitFromSAP(List<String> groups, Date last_month_end, Date current_month_end) throws Exception {
		String sql = map.get("getGroupDeficitFromSAP");				
		String groupCause =  " and groups.group_code in ('" + StringUtils.join(groups, "', '") + "')";
		sql = sql.replace("#CUSTOM#", groupCause);
		Object[] objs = new Object[] { last_month_end, current_month_end };
		List<GroupDeficitDto> beans = this.sapJdbcTemplate.query(sql, new BeanPropertyRowMapper<GroupDeficitDto>(GroupDeficitDto.class), objs);
		return beans;
	}

	@Override
	public List<GroupEquityChangeDto> getGroupEquityChangeFromSAP(List<String> groups, Date start_date, Date end_date) throws Exception {
		String sql = map.get("getGroupEquityChangeFromSAP");
		String groupCause = " and groups.group_code in ('" + StringUtils.join(groups, "', '") + "')";			
		Object[] objs = new Object[] { start_date, end_date};
		sql = sql.replace("#CUSTOM#", groupCause);
		List<GroupEquityChangeDto> beans = this.sapJdbcTemplate.query(sql,
				new BeanPropertyRowMapper<GroupEquityChangeDto>(GroupEquityChangeDto.class), objs);

		return beans;
	}

	@Override
	public List<GroupClientTradeDto> getGroupClientTradeFromSAP(List<String> groups, Date start_date, Date end_date) throws Exception {
		String sql = map.get("getGroupClientTradeFromSAP");
		String groupCause =  " and groups.group_code in ('" + StringUtils.join(groups, "', '") + "')";
		sql = sql.replace("#CUSTOM#", groupCause);
		Object[] objs = new Object[] { start_date, end_date};
		List<GroupClientTradeDto> beans = this.sapJdbcTemplate.query(sql, new BeanPropertyRowMapper<GroupClientTradeDto>(GroupClientTradeDto.class),
				objs);

		return beans;
	}
	
	@Override
	public int updateAccountBalance(String clientCode, Double accountBalance, String updatedBy) throws Exception {
		
		final String sql = map.get("updateAccountBalance");
		Object[] objs = new Object[] { accountBalance, updatedBy, clientCode };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
	
	@Override
	public List<ClientBalanceSummaryBean> getLatestClientBalanceSummary(String clientCode) throws Exception {
		final String sql = map.get("getlatestClientBalanceSummaryByClientCode");
		Object[] objs = new Object[] { clientCode };
		List<ClientBalanceSummaryBean> beans = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<ClientBalanceSummaryBean>(ClientBalanceSummaryBean.class), objs);
		return beans;		
	}
}
