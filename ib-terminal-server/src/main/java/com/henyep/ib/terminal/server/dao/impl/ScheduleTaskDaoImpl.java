package com.henyep.ib.terminal.server.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.ScheduleTaskBean;
import com.henyep.ib.terminal.server.dao.ScheduleTaskDao;
import com.henyep.ib.terminal.server.global.Constants;
import com.henyep.ib.terminal.server.util.DateUtil;

@Repository(value = "ScheduleTaskDao")
public class ScheduleTaskDaoImpl implements ScheduleTaskDao{

	protected final transient Log logger = LogFactory.getLog(getClass());
	
	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "ScheduleTaskDao_SQLMap")
	Map<String, String> map;
	public ScheduleTaskDaoImpl() throws Exception{
		super();
	}
	@Override
	public void saveScheduleTask(ScheduleTaskBean scheduleTask) throws Exception {
		final String sql = map.get("create");
		
		String tradeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, scheduleTask.getTrade_date());
		
		Object[] objs = new Object[]{
				tradeDateString,
				scheduleTask.getType(),
				scheduleTask.getSpend_time(),
				scheduleTask.getStatus()
		};
		int res = this.jdbcTemplate.update(sql, objs);
	}
	
	private ArrayList<String> getScheduleTaskTypeList(){
		ArrayList<String> scheduleTasks = new ArrayList<String>();
		scheduleTasks.add(Constants.SCHEDULE_TYPE_UPDATE_CLIENT_TRADE_DETAIL);
		scheduleTasks.add(Constants.SCHEDULE_TYPE_UPDATE_CLIENT_MARGIN_IN_OUT);
		scheduleTasks.add(Constants.SCHEDULE_TYPE_UPDATE_CLIENT_BALANCE_SUMMARY);
		scheduleTasks.add(Constants.SCHEDULE_TYPE_UPDATE_CLIENT_DETAILS);
		scheduleTasks.add(Constants.SCHEDULE_TYPE_UPDATE_IB_LEADS);
		scheduleTasks.add(Constants.SCHEDULE_TYPE_UPDATE_IB_CLIENT_MAP);
		scheduleTasks.add(Constants.SCHEDULE_TYPE_UPDATE_SETTINGS_SYMBOL);
		
		return scheduleTasks;
	}
	
	@Override
	public void initDataSyncScheduleTask(Date tradeDate) throws Exception {
		
		ArrayList<String> scheduleTasks = getScheduleTaskTypeList();
		
		initScheduleTaskByType(tradeDate, scheduleTasks);
	}
	
	@Override
	public void initCalIbCommScheduleTask(Date tradeDate) throws Exception{
		
		ArrayList<String> scheduleTasks = new ArrayList<String>();
		scheduleTasks.add(Constants.SCHEDULE_TYPE_UPDATE_CALCULATE_IB_COMMISSION);
		
		initScheduleTaskByType(tradeDate, scheduleTasks);
		
	}
	
	private void initScheduleTaskByType(Date tradeDate, ArrayList<String> scheduleTasks) throws Exception{
		
		final String sql = map.get("create");
		
		for(String scheduleTaskType : scheduleTasks){
			
			String tradeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, tradeDate);
			
			Object[] objs = new Object[]{
					tradeDateString,
					scheduleTaskType,
					0,
					Constants.SCHEDULE_STATUS_PENDING
			};
			int res = this.jdbcTemplate.update(sql, objs);		
		}
	}


	@Override
	public int updateScheduleTask(ScheduleTaskBean scheduleTask) throws Exception {
		
		String tradeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, scheduleTask.getTrade_date());
		
		logger.info("Update schedule task status");
		logger.info(scheduleTask.toString());
		
		final String sql = map.get("update");
		Object[] objs = new Object[]{
				scheduleTask.getSpend_time(),
				scheduleTask.getStatus(),
				tradeDateString,
				scheduleTask.getType()
		};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<ScheduleTaskBean> getAllScheduleTasks() throws Exception{
		String sql = map.get("selectAll");
		List<ScheduleTaskBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ScheduleTaskBean>(ScheduleTaskBean.class));
		return beans;
	}

	@Override
	public List<ScheduleTaskBean> getScheduleTaskByKey(Date trade_date, String taskType) throws Exception{
		
		String tradeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, trade_date);
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[]{tradeDateString, taskType};
		List<ScheduleTaskBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ScheduleTaskBean>(ScheduleTaskBean.class), objs);
		return beans;
	}

	@Override
	public int deleteScheduleTask(Date trade_date) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[]{trade_date};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
	
	@Override
	public int deleteDataSyncScheduleTask(Date trade_date) throws Exception{
		
		ArrayList<String> scheduleTasks = getScheduleTaskTypeList();
		
		return deleteTaskByTypes(trade_date, scheduleTasks);
	}
	
	public int deleteCalIbCommTask(Date trade_date) throws Exception{
		
		ArrayList<String> scheduleTasks = new ArrayList<String>();
		scheduleTasks.add(Constants.SCHEDULE_TYPE_UPDATE_CALCULATE_IB_COMMISSION);
		
		return deleteTaskByTypes(trade_date, scheduleTasks);
	}
	
	private int deleteTaskByTypes(Date trade_date, List<String> scheduleTasks) throws Exception{
		
		String sql = map.get("delete");
		
		sql += " AND type in ('" + StringUtils.join(scheduleTasks.toArray(), "','") + "')";
		
		logger.debug(sql);
		logger.info(sql);
		Object[] objs = new Object[]{trade_date};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
	
	
	@Override
	public int searchScheduleTask(Date trade_date) throws Exception {
		String sql = map.get("searchScheduleTask");
		String tradeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, trade_date);
		Object[] objs = new Object[] { tradeDateString};
		Integer rtn = this.jdbcTemplate.queryForObject(sql, objs, Integer.class);
		return rtn;
	}
}
