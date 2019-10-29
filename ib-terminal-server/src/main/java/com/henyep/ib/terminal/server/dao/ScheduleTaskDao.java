package com.henyep.ib.terminal.server.dao;
import java.util.List;
import java.util.Date;
import com.henyep.ib.terminal.api.dto.db.ScheduleTaskBean;

public interface ScheduleTaskDao{
	public void saveScheduleTask(final ScheduleTaskBean scheduleTask) throws Exception;

	public List<ScheduleTaskBean> getAllScheduleTasks() throws Exception;

	public List<ScheduleTaskBean> getScheduleTaskByKey(Date trade_date, String taskType) throws Exception;

	public int updateScheduleTask(ScheduleTaskBean scheduleTask) throws Exception;
	
	public int deleteScheduleTask(Date trade_date) throws Exception;
	
	public int deleteDataSyncScheduleTask(Date trade_date) throws Exception;
	
	public int deleteCalIbCommTask(Date trade_date) throws Exception;

	public int searchScheduleTask(Date trade_date) throws Exception;
	
	public void initDataSyncScheduleTask(Date tradeDate) throws Exception;
	
	public void initCalIbCommScheduleTask(Date tradeDate) throws Exception;
}
