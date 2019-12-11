package com.henyep.ib.terminal.server.schedule;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.api.dto.db.ClientBalanceSummaryBean;
import com.henyep.ib.terminal.api.dto.db.ClientDetailsBean;
import com.henyep.ib.terminal.api.dto.db.ClientMarginInOutBean;
import com.henyep.ib.terminal.api.dto.db.ClientTradeDetailsBean;
import com.henyep.ib.terminal.api.dto.db.IbClientMapBean;
import com.henyep.ib.terminal.api.dto.db.IbDailyFloatingBean;
import com.henyep.ib.terminal.api.dto.db.IbLeadBean;
import com.henyep.ib.terminal.api.dto.db.OpenTradeBean;
import com.henyep.ib.terminal.api.dto.db.ScheduleTaskBean;
import com.henyep.ib.terminal.api.dto.db.SettingsSymbolBean;
import com.henyep.ib.terminal.api.dto.request.ibcommission.CalculateIbCommissionRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.CalculateIbCommissionRequestDto;
import com.henyep.ib.terminal.api.dto.request.report.GetIbCommissionReportRequest;
import com.henyep.ib.terminal.api.dto.request.report.GetIbCommissionReportRequestDto;
import com.henyep.ib.terminal.api.dto.request.schedule.task.GetAllMT5SymbolRequestDto;
import com.henyep.ib.terminal.api.dto.request.schedule.task.GetAllSymbolRequestDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.CalculateIbCommissionResponseDto;
import com.henyep.ib.terminal.api.dto.response.schedule.task.DataSyncFromSAPResponseDto;
import com.henyep.ib.terminal.api.dto.response.schedule.task.RunCalIBCommTaskResponseDto;
import com.henyep.ib.terminal.api.dto.response.schedule.task.RunScheduleTaskResponseDto;
import com.henyep.ib.terminal.server.adapter.MT4ServiceAdapterFactory;
import com.henyep.ib.terminal.server.dao.ClientBalanceSummaryDao;
import com.henyep.ib.terminal.server.dao.ClientDetailsDao;
import com.henyep.ib.terminal.server.dao.ClientGroupMappingDao;
import com.henyep.ib.terminal.server.dao.ClientMarginInOutDao;
import com.henyep.ib.terminal.server.dao.ClientTradeDetailsDao;
import com.henyep.ib.terminal.server.dao.IbAccountDetailsDao;
import com.henyep.ib.terminal.server.dao.IbAccountDetailsHistoryDao;
import com.henyep.ib.terminal.server.dao.IbClientMapDao;
import com.henyep.ib.terminal.server.dao.IbCommissionSummaryDao;
import com.henyep.ib.terminal.server.dao.IbDailyFloatingDao;
import com.henyep.ib.terminal.server.dao.IbLeadDao;
import com.henyep.ib.terminal.server.dao.OpenTradeDao;
import com.henyep.ib.terminal.server.dao.ScheduleTaskDao;
import com.henyep.ib.terminal.server.dao.SettingsSymbolDao;
import com.henyep.ib.terminal.server.dao.SystemParamsDao;
import com.henyep.ib.terminal.server.dto.dao.IbClientMapDto;
import com.henyep.ib.terminal.server.dto.mt4.model.MT5SymbolRecord;
import com.henyep.ib.terminal.server.dto.mt4.model.Mt4WebServiceConnectionModel;
import com.henyep.ib.terminal.server.dto.mt4.model.SymbolRecord;
import com.henyep.ib.terminal.server.dto.mt4.response.GetAllMT5SymbolsResponse;
import com.henyep.ib.terminal.server.dto.mt4.response.GetAllSymbolsResponse;
import com.henyep.ib.terminal.server.dto.security.SenderDto;
import com.henyep.ib.terminal.server.global.Constants;
import com.henyep.ib.terminal.server.global.EmailConfig;
import com.henyep.ib.terminal.server.service.IbCommissionService;
import com.henyep.ib.terminal.server.service.ReportService;
import com.henyep.ib.terminal.server.util.DateUtil;
import com.henyep.ib.terminal.server.util.EmailUtil;

@Component
public class ScheduleTask {
	protected final transient Log logger = LogFactory.getLog(getClass());
	
	@Resource(name="ClientTradeDetailsDao")
	ClientTradeDetailsDao clientTradeDetailsDao;
	
	@Resource(name="OpenTradeDao")
	OpenTradeDao openTradeDao;
	
	@Resource(name="IbDailyFloatingDao")
	IbDailyFloatingDao ibDailyFloatingDao; 
	
	@Resource(name="ClientMarginInOutDao")
	ClientMarginInOutDao clientMarginInOutDao;
	
	@Resource(name="IbCommissionService")
	IbCommissionService ibCommissionService;
	
	@Resource(name="ClientBalanceSummaryDao")
	ClientBalanceSummaryDao clientBalanceSummaryDao;
	
	@Resource(name="ClientDetailsDao")
	ClientDetailsDao clientDetailsDao;
	
	@Resource(name="ClientGroupMappingDao")
	ClientGroupMappingDao clientGroupMappingDao;
	
	@Resource(name="IbLeadDao")
	IbLeadDao ibLeadDao;
	
	@Resource(name="SettingsSymbolDao")
	SettingsSymbolDao settingsSymbolDao;
	
	@Resource(name="IbClientMapDao")
	IbClientMapDao ibClientMapDao;
	
	@Resource(name="ScheduleTaskDao")
	ScheduleTaskDao scheduleTaskDao;
	
	@Resource(name = "EmailUtil")
	EmailUtil emailUtil;

	@Resource(name = "SystemParamsDao")
	SystemParamsDao systemParamsDao;
	
	@Resource(name = "IbAccountDetailsDao")
	IbAccountDetailsDao ibAccountDetailsDao;
	
	@Resource(name = "IbAccountDetailsHistoryDao")
	IbAccountDetailsHistoryDao ibAccountDetailsHistoryDao;

	@Resource(name = "MT4ServiceAdapterFactory")
	private MT4ServiceAdapterFactory mt4ServiceAdapterFactory;
	
	@Resource(name = "IbCommissionSummaryDao")
	private IbCommissionSummaryDao ibCommissionSummaryDao;
	
	@Resource(name = "ReportService")
	private ReportService reportService;
	
	@Resource
	EmailConfig emailConfig;
	
	private HashMap<String, String> errorTasks = new HashMap<String, String>();
	
	public ScheduleTask(){
		takeLog("Initalizing schedule task, hashCode: " + this.hashCode());
	}
	
	public void testRun(){
		takeLog("Client Details hash code: " + clientDetailsDao.hashCode());
		// for test case use
		takeLog("Run schedule task");
		
		for(int i = 6; i <= 31; i++){
			try{	
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.DAY_OF_MONTH, i);
				cal.set(Calendar.MONTH, 7 - 1);
				cal.set(Calendar.YEAR, 2016);
				Date tradeDate = DateUtil.trimTime(cal.getTime());

				updateClientTradeDetail(tradeDate);
				updateClientMarginInOut(tradeDate);

			}catch(Exception ex){
				
				
				System.out.println(ex.getMessage());
				System.out.println(ex.getStackTrace());
				logger.error(ex, ex);
			}
		}
	}
	
	// schedule task
	public void doTask() throws Exception{
		takeLog("");
		takeLog("Running corn job, " + this.hashCode());
		Date tradeDate = DateUtil.getLastDay();
		doTaskByTradeDate(tradeDate);
	}
	
	// schedule task
	public void updateIbLeads() throws Exception{
		
		try{
		
			if(!systemParamsDao.isDataSyncRunning()){
				takeLog("");
				takeLog("Update system param: is_running_data_sync -> true");
				systemParamsDao.updateSystemParamValue(Constants.SYSTEM_PARAM_IS_RUNNING_DATA_SYNC, "true", "System");
				String currenctTimestamp = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE_TIME, new Date());
				takeLog("Update ib lead at " + currenctTimestamp);
				Date tradeDate = DateUtil.getLastDay();
				updateIbLeads(tradeDate);
				takeLog("Update system param: is_running_data_sync -> false");
				takeLog("");
				systemParamsDao.updateSystemParamValue(Constants.SYSTEM_PARAM_IS_RUNNING_DATA_SYNC, "false", "System");
			}
			else{
				takeLog("Exit updateIbLeads, data sync is running.");
			}
		
		}catch(Exception ex){
			logger.error(ex, ex);
		}
	}
	
	
    public RunScheduleTaskResponseDto doTaskByTradeDate(Date tradeDate) throws Exception{
    	    	
    	int rtn = scheduleTaskDao.searchScheduleTask(tradeDate);
    	
    	RunScheduleTaskResponseDto response = new RunScheduleTaskResponseDto();
    	
		if(rtn == 0){
			takeLog("Run schedule task");
			errorTasks.clear();
			
			try{
				
				String[] updateInfos = updateIbAccountDetails(tradeDate);
		    	response.setUpdate_ib_account_details_day_open_status(updateInfos[0]);
				response.setUpdate_ib_account_details_day_open_time_spend(updateInfos[1]);
				
				DataSyncFromSAPResponseDto dataSyncFromSAPResponse = doDataSyncFromSAP(tradeDate, "system");
				response.setDataSyncFromSAP(dataSyncFromSAPResponse);
				
				RunCalIBCommTaskResponseDto runCalIbCommTaskResponse = doCalculateIbCommission(tradeDate, null, "system");
				response.setCalIbComm(runCalIbCommTaskResponse);
				
				Calendar startDate = Calendar.getInstance();
				startDate.setTime(tradeDate);
			    int year = startDate.get(Calendar.YEAR);
			    int month = startDate.get(Calendar.MONTH);
			    int day = 1;
			    startDate.set(year, month, day);
				SendIbCommissionReport(startDate.getTime(), tradeDate, "INT", "intadmin", true);
				
			}catch(Exception ex){
				
				System.out.println(ex.getMessage());
				System.out.println(ex.getStackTrace());
				response.setComment(Constants.ERR_COMMON_SCHEDULE_TASK_ERROR);
				logger.error(ex, ex);
			}
			
			if(errorTasks.size() > 0){
				sendCornJobFailEmail(errorTasks);
			}
			
		}
		else{
			String tradeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, tradeDate);
			String message = ("Corn job has been already done, trade date: " + tradeDateString);
			System.out.println(message);
			takeLog(message);
			response.setComment(Constants.ERR_COMMON_SCHEDULE_TASK_ALREADY_DONE);
		}

		return response;
    }
    
    public DataSyncFromSAPResponseDto doDataSyncFromSAP(Date tradeDate, String updatedBy) throws Exception{
    	
    	systemParamsDao.updateSystemParamValue(Constants.SYSTEM_PARAM_IS_RUNNING_DATA_SYNC, "true", updatedBy);
    	
    	DataSyncFromSAPResponseDto response = new DataSyncFromSAPResponseDto();
    	
    	String[] updateInfos;
    	
    	updateInfos = updateClientTradeDetail(tradeDate);
		response.setUpdate_client_trade_detail_status(updateInfos[0]);
		response.setUpdate_client_trade_detail_time_spend(updateInfos[1]);
		
		updateInfos = updateOpenTrade(tradeDate);
		response.setUpdate_open_trade_status(updateInfos[0]);
		response.setUpdate_open_trade_time_spend(updateInfos[1]);
		
		updateInfos = updateClientMarginInOut(tradeDate);
		response.setUpdate_client_margin_in_out_status(updateInfos[0]);
		response.setUpdate_client_margin_in_out_time_spend(updateInfos[1]);
		
		updateInfos = updateClientBalanceSummary(tradeDate);
		response.setUpdate_client_balance_summary_status(updateInfos[0]);
		response.setUpdate_client_balance_summary_time_spend(updateInfos[1]);
		
		updateInfos = updateClientDetails(tradeDate);
		response.setUpdate_client_details_status(updateInfos[0]);
		response.setUpdate_client_details_time_spend(updateInfos[1]);
		
		updateInfos = updateIbClientMap(tradeDate);
		response.setUpdate_ib_client_map_status(updateInfos[0]);
		response.setUpdate_ib_client_map_time_spend(updateInfos[1]);
		
		updateInfos = updateSettingsSymbol(tradeDate);
		response.setUpdate_settings_symbol_status(updateInfos[0]);
		response.setUpdate_settings_symbol_time_spend(updateInfos[1]);
		
		updateInfos = updateIbLeads(tradeDate);
		response.setUpdate_ib_leads_status(updateInfos[0]);
		response.setUpdate_ib_leads_time_spend(updateInfos[1]);
		
		updateInfos = updateIbDailyFloating(tradeDate);
		response.setUpdate_ib_daily_floating_status(updateInfos[0]);
		response.setUpdate_ib_daily_floating_time_spend(updateInfos[1]);
		
		systemParamsDao.updateSystemParamValue(Constants.SYSTEM_PARAM_IS_RUNNING_DATA_SYNC, "false", updatedBy);
    	
		return response;
    }
    
    
    public RunCalIBCommTaskResponseDto doCalculateIbCommission(Date tradeDate, String ibTeamHead, String updatedBy) throws Exception{
    	
    	systemParamsDao.updateSystemParamValue(Constants.SYSTEM_PARAM_IS_UPDATING_IB_COMMISSION, "true", updatedBy);
    	
    	RunCalIBCommTaskResponseDto response = new RunCalIBCommTaskResponseDto();
    	
    	String[] updateInfos; 
    
    	updateInfos = calculateIbCommission(tradeDate, ibTeamHead, updatedBy);
		response.setUpdate_calculate_ib_commission_status(updateInfos[0]);
		response.setUpdate_calculate_ib_commission_time_spend(updateInfos[1]);
	
		systemParamsDao.updateSystemParamValue(Constants.SYSTEM_PARAM_IS_UPDATING_IB_COMMISSION, "false", updatedBy);
		
		return response;
    	
    } 
    
    private String[] updateIbAccountDetails(Date tradeDate) throws Exception{
		takeLog("----------------------------------");
		
		
		Date startTime = new Date();
		String scheduleTaskStatus = Constants.SCHEDULE_STATUS_EXECUTED;
		String scheduleTaskType = Constants.SCHEDULE_TYPE_UPDATE_IB_ACCOUNT_DETAILS_DAY_OPEN;
		
		try{
			Calendar cal = Calendar.getInstance();
		    cal.setTime(tradeDate);
		    cal.add(Calendar.DATE, -1);
		    Date lastDate = DateUtil.trimTime(cal.getTime());
			Date threeMonthsBeforeDate = DateUtil.getMonthsBeforeDate(lastDate, 3);
			String currentDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, lastDate);
			String threeMonthsBeforeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, threeMonthsBeforeDate);
			
			takeLog("Delete IB account details History, trade date = " + currentDateString);
			ibAccountDetailsHistoryDao.deleteByTradeDate(lastDate);
			// delete the history 3 months before (house keeping)
			takeLog("Delete IB account details History, trade date = " + threeMonthsBeforeDateString);
			ibAccountDetailsHistoryDao.deleteByTradeDate(threeMonthsBeforeDate);
			takeLog("Insert to IB account details History, trade date = " + currentDateString);
			ibAccountDetailsHistoryDao.insertFromIbAccountDetails(lastDate);
			takeLog("Update IB account details day open");
			ibAccountDetailsDao.updateDayOpen();
			
		}catch(Exception ex){
			logger.error(ex, ex);
			scheduleTaskStatus = Constants.SCHEDULE_STATUS_FAILED;
		}
		Date finishTime = new Date();
		int diff = (int)((finishTime.getTime() - startTime.getTime()) / 1000);
		
		ScheduleTaskBean bean = new ScheduleTaskBean();
		bean.setTrade_date(tradeDate);
		bean.setSpend_time(diff);
		bean.setStatus(scheduleTaskStatus);
		bean.setType(scheduleTaskType);
		scheduleTaskDao.updateScheduleTask(bean);
		
		String[] rtn = new String[2];
		rtn[0] = scheduleTaskStatus;
		rtn[1] = diff + "";
		return rtn;
    }
    
    
	
	public String[] updateClientTradeDetail(Date tradeDate) throws Exception{
		
		takeLog("----------------------------------");
		takeLog("Update client trade detail");
		
		
		Date startTime = new Date();
		String scheduleTaskStatus = Constants.SCHEDULE_STATUS_EXECUTED;
		String scheduleTaskType = Constants.SCHEDULE_TYPE_UPDATE_CLIENT_TRADE_DETAIL;
		
		try{
			
			Date currentDate = DateUtil.trimTime(tradeDate);
			Date threeMonthsBeforeDate = DateUtil.getMonthsBeforeDate(currentDate, 3);
			
			String lastDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, currentDate);
			String threeMonthsBeforeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, threeMonthsBeforeDate);
			
			
			// clear current date client trade detail
			int recordDeleted = 0;
			takeLog("Clearing data, trade date: " + lastDateString);
			recordDeleted = clientTradeDetailsDao.clearClientTradeDetails(lastDateString);
			takeLog("Deleted: " + recordDeleted);
			takeLog("Clearing data, trade date: " + threeMonthsBeforeDateString);
			recordDeleted = clientTradeDetailsDao.clearClientTradeDetails(threeMonthsBeforeDateString);
			takeLog("Deleted: " + recordDeleted);
			
			// clear 3 month ago client trade detail
			takeLog("Getting data from SAP... trade date: " + lastDateString);
			List<ClientTradeDetailsBean> beanList = clientTradeDetailsDao.getClientTradeDetailsFromSAP(lastDateString);
			takeLog(beanList.size() + " records return.");
			takeLog("Upload data to ib terminal database");
		
			clientTradeDetailsDao.saveClientTradeDetailsList(beanList);
			takeLog("Finish updating client trade detail");
			
						
			
		}
		catch(Exception ex){
			logger.error(ex, ex);
			scheduleTaskStatus = Constants.SCHEDULE_STATUS_FAILED;
			errorTasks.put(scheduleTaskType, ex.getMessage());
		}
		Date finishTime = new Date();
		int diff = (int)((finishTime.getTime() - startTime.getTime()) / 1000);
		
		ScheduleTaskBean bean = new ScheduleTaskBean();
		bean.setTrade_date(tradeDate);
		bean.setSpend_time(diff);
		bean.setStatus(scheduleTaskStatus);
		bean.setType(scheduleTaskType);
		scheduleTaskDao.updateScheduleTask(bean);
		
		String[] rtn = new String[2];
		rtn[0] = scheduleTaskStatus;
		rtn[1] = diff + "";
		return rtn;
	}
	
	
	
	public String[] updateIbDailyFloating(Date tradeDate) throws Exception{
		takeLog("----------------------------------");
		takeLog("Update ib daily floating");
		
		
		Date startTime = new Date();
		String scheduleTaskStatus = Constants.SCHEDULE_STATUS_EXECUTED;
		String scheduleTaskType = Constants.SCHEDULE_TYPE_UPDATE_IB_DAILY_FLOATING;
		
		try{
			
			Date currentDate = DateUtil.trimTime(tradeDate);
			Date threeMonthsBeforeDate = DateUtil.getMonthsBeforeDate(currentDate, 3);
			
			String lastDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, currentDate);
			String threeMonthsBeforeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, threeMonthsBeforeDate);
			
			
			// clear current date open trade
			int recordDeleted = 0;
			takeLog("Clearing data, trade date: " + lastDateString);
			recordDeleted = ibDailyFloatingDao.deleteIbDailyFloatingByTradeDate(lastDateString);
			takeLog("Deleted: " + recordDeleted);
			takeLog("Clearing data, trade date: " + threeMonthsBeforeDateString);
			recordDeleted = ibDailyFloatingDao.deleteIbDailyFloatingByLessThanTradeDate(threeMonthsBeforeDateString);
			takeLog("Deleted: " + recordDeleted);
			
			// clear 3 month ago open trade
			takeLog("Getting data from SAP... trade date: " + lastDateString);
			List<IbDailyFloatingBean> beanList = ibDailyFloatingDao.getFromSAP(currentDate);
			takeLog(beanList.size() + " records return.");
			takeLog("Upload data to ib terminal database");
		
			ibDailyFloatingDao.saveIbDailyFloatings(beanList, "system");
			takeLog("Finish updating open trade");	
		}
		catch(Exception ex){
			logger.error(ex, ex);
			scheduleTaskStatus = Constants.SCHEDULE_STATUS_FAILED;
			errorTasks.put(scheduleTaskType, ex.getMessage());
		}
		Date finishTime = new Date();
		int diff = (int)((finishTime.getTime() - startTime.getTime()) / 1000);
		
		ScheduleTaskBean bean = new ScheduleTaskBean();
		bean.setTrade_date(tradeDate);
		bean.setSpend_time(diff);
		bean.setStatus(scheduleTaskStatus);
		bean.setType(scheduleTaskType);
		scheduleTaskDao.updateScheduleTask(bean);
		
		String[] rtn = new String[2];
		rtn[0] = scheduleTaskStatus;
		rtn[1] = diff + "";
		return rtn;
	}
	
	
	public String[] updateOpenTrade(Date tradeDate) throws Exception{
		takeLog("----------------------------------");
		takeLog("Update open trade ");
		
		
		Date startTime = new Date();
		String scheduleTaskStatus = Constants.SCHEDULE_STATUS_EXECUTED;
		String scheduleTaskType = Constants.SCHEDULE_TYPE_UPDATE_OPEN_TRADE;
		
		try{
			
			Date currentDate = DateUtil.trimTime(tradeDate);
			Date threeMonthsBeforeDate = DateUtil.getMonthsBeforeDate(currentDate, 3);
			
			String lastDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, currentDate);
			String threeMonthsBeforeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, threeMonthsBeforeDate);
			
			
			// clear current date open trade
			int recordDeleted = 0;
			takeLog("Clearing data, trade date: " + lastDateString);
			recordDeleted = openTradeDao.deleteOpenTrade(lastDateString);
			takeLog("Deleted: " + recordDeleted);
			takeLog("Clearing data, trade date: " + threeMonthsBeforeDateString);
			recordDeleted = openTradeDao.deleteOpenTrade(threeMonthsBeforeDateString);
			takeLog("Deleted: " + recordDeleted);
			
			// clear 3 month ago open trade
			takeLog("Getting data from SAP... trade date: " + lastDateString);
			List<OpenTradeBean> beanList = openTradeDao.getOpenTradeFromSAP(lastDateString);
			takeLog(beanList.size() + " records return.");
			takeLog("Upload data to ib terminal database");
		
			openTradeDao.saveOpenTrades(beanList);
			takeLog("Finish updating open trade");	
		}
		catch(Exception ex){
			logger.error(ex, ex);
			scheduleTaskStatus = Constants.SCHEDULE_STATUS_FAILED;
			errorTasks.put(scheduleTaskType, ex.getMessage());
		}
		Date finishTime = new Date();
		int diff = (int)((finishTime.getTime() - startTime.getTime()) / 1000);
		
		ScheduleTaskBean bean = new ScheduleTaskBean();
		bean.setTrade_date(tradeDate);
		bean.setSpend_time(diff);
		bean.setStatus(scheduleTaskStatus);
		bean.setType(scheduleTaskType);
		scheduleTaskDao.updateScheduleTask(bean);
		
		String[] rtn = new String[2];
		rtn[0] = scheduleTaskStatus;
		rtn[1] = diff + "";
		return rtn;
	}
	
	public String[] updateClientMarginInOut(Date tradeDate) throws Exception{
		
		takeLog("----------------------------------");
		takeLog("Update client margin in out");
		String scheduleTaskType = Constants.SCHEDULE_TYPE_UPDATE_CLIENT_MARGIN_IN_OUT;
		
		Date startTime = new Date();
		String scheduleTaskStatus = Constants.SCHEDULE_STATUS_EXECUTED;
		
		try{
			Date currentDate = DateUtil.trimTime(tradeDate);
			Date threeMonthsBeforeDate = DateUtil.getMonthsBeforeDate(currentDate, 3);
			
			String lastDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, currentDate);
			String threeMonthsBeforeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, threeMonthsBeforeDate);
			
			
			// clear current date client trade detail
			int recordDeleted = 0;
			takeLog("Clearing data, trade date: " + lastDateString);
			recordDeleted = clientMarginInOutDao.clearClientMarginInOut(lastDateString);
			takeLog("Deleted: " + recordDeleted);
			takeLog("Clearing data, trade date: " + threeMonthsBeforeDateString);
			recordDeleted = clientMarginInOutDao.clearClientMarginInOut(threeMonthsBeforeDateString);
			takeLog("Deleted: " + recordDeleted);
			
			// clear 3 month ago client trade detail
			takeLog("Getting data from SAP... trade date: " + lastDateString);
			List<ClientMarginInOutBean> beanList = clientMarginInOutDao.getClientMarginInOutFromSAP(lastDateString);
			takeLog(beanList.size() + " records return.");
			takeLog("Upload data to ib terminal database");
		
			clientMarginInOutDao.saveClientMarginInOuts(beanList);
			
			takeLog("Finish updating client margin in out");

			
		}catch(Exception ex){
			logger.error(ex, ex);
			scheduleTaskStatus = Constants.SCHEDULE_STATUS_FAILED;
			errorTasks.put(scheduleTaskType, ex.getMessage());
		}
		
		Date finishTime = new Date();
		int diff = (int)((finishTime.getTime() - startTime.getTime()) / 1000);
		
		ScheduleTaskBean bean = new ScheduleTaskBean();
		bean.setTrade_date(tradeDate);
		bean.setSpend_time(diff);
		bean.setStatus(scheduleTaskStatus);
		bean.setType(scheduleTaskType);
		scheduleTaskDao.updateScheduleTask(bean);
		
		String[] rtn = new String[2];
		rtn[0] = scheduleTaskStatus;
		rtn[1] = diff + "";
		return rtn;
	}
	
	private String[] updateClientBalanceSummary(Date tradeDate) throws Exception{
		takeLog("----------------------------------");
		takeLog("Update client balance summary");
		
		Date startTime = new Date();
		String scheduleTaskStatus = Constants.SCHEDULE_STATUS_EXECUTED;
		String scheduleTaskType = Constants.SCHEDULE_TYPE_UPDATE_CLIENT_BALANCE_SUMMARY;
		try{
		
			Date currentDate = DateUtil.trimTime(tradeDate);
			Date twoMonthsBeforeDate = DateUtil.getMonthsBeforeDate(currentDate, 2);
			
			String lastDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, currentDate);
			String twoMonthsBeforeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, twoMonthsBeforeDate);
			
			
			// clear current date client trade detail
			int recordDeleted = 0;
			takeLog("Clearing data, trade date: " + lastDateString);
			recordDeleted = clientBalanceSummaryDao.deleteClientBalanceSummaryByTradeDate(currentDate);
			takeLog("Deleted: " + recordDeleted);
			takeLog("Clearing data, trade date: " + twoMonthsBeforeDateString);
			recordDeleted = clientBalanceSummaryDao.deleteClientBalanceSummaryByLessThanTradeDate(twoMonthsBeforeDate);
			takeLog("Deleted: " + recordDeleted);
			
			// clear 3 month ago client trade detail
			takeLog("Getting data from SAP... trade date: " + lastDateString);
			List<ClientBalanceSummaryBean> beanList = clientBalanceSummaryDao.getClientBalanceSummaryFromSAP(currentDate);
			takeLog(beanList.size() + " records return.");
			takeLog("Upload data to ib terminal database");
		
			clientBalanceSummaryDao.saveClientBalanceSummaryList(beanList);
			
			takeLog("Finish updating client balance summary");

			
		}catch(Exception ex){
			logger.error(ex, ex);
			scheduleTaskStatus = Constants.SCHEDULE_STATUS_FAILED;
			errorTasks.put(scheduleTaskType, ex.getMessage());
		}
		
		Date finishTime = new Date();
		int diff = (int)((finishTime.getTime() - startTime.getTime()) / 1000);
		
		ScheduleTaskBean bean = new ScheduleTaskBean();
		bean.setTrade_date(tradeDate);
		bean.setSpend_time(diff);
		bean.setStatus(scheduleTaskStatus);
		bean.setType(scheduleTaskType);
		scheduleTaskDao.updateScheduleTask(bean);
		
		String[] rtn = new String[2];
		rtn[0] = scheduleTaskStatus;
		rtn[1] = diff + "";
		return rtn;
	}
	
	public String[] updateClientDetails(Date tradeDate) throws Exception{
		takeLog("----------------------------------");
		takeLog("Update client details");
		
		Date startTime = new Date();
		String scheduleTaskStatus = Constants.SCHEDULE_STATUS_EXECUTED;
		String scheduleTaskType = Constants.SCHEDULE_TYPE_UPDATE_CLIENT_DETAILS;
		try{
			// get data from SAP first
			takeLog("Getting data from SAP...");
			List<ClientDetailsBean> beanList = clientDetailsDao.getClientDetailsFromSAP();
			takeLog(beanList.size() + " records return.");
			if(beanList.size() > 0){
				// clear current date client trade detail
				int recordDeleted = 0;
				takeLog("Get manually insert client details");
				List<ClientDetailsBean> manuallyAddedBeans = clientDetailsDao.getManuallyAddedClientDetails();
				
				takeLog("Clearing data");
				recordDeleted = clientDetailsDao.clearClientDetails();
				takeLog("Deleted: " + recordDeleted);
				
				takeLog("Upload data to ib terminal database");
				clientDetailsDao.saveClientDetails(beanList);
				
				if(manuallyAddedBeans.size() > 0){
					takeLog("Add back the manually added client details");
					List<String> manuallyAddedClientTradingIds = new ArrayList<String>();
					HashMap<String, ClientDetailsBean> manuallyAddedBeanMap = new HashMap<String, ClientDetailsBean>(); 
					for(ClientDetailsBean manuallyAddedBean : manuallyAddedBeans){
						manuallyAddedClientTradingIds.add(manuallyAddedBean.getClient_trading_id());
						manuallyAddedBeanMap.put(manuallyAddedBean.getClient_trading_id(), manuallyAddedBean);
					}
					// search any manually added client details has been added from SAP 
					List<ClientDetailsBean> dbBeans = clientDetailsDao.getClientDetailsByKeys(manuallyAddedClientTradingIds);
					takeLog("Total: " + dbBeans.size() + " client details beans have been added from SAP");
					
					// remove the duplicate client details
					for(ClientDetailsBean dbBean: dbBeans){
						manuallyAddedBeans.remove(manuallyAddedBeanMap.get(dbBean.getClient_trading_id()));
					}
					// add the remain client details
					takeLog("Total: " + manuallyAddedBeans.size() + " manaully added client details add back to Database");
					if(manuallyAddedBeans.size() > 0){
						clientDetailsDao.saveClientDetails(manuallyAddedBeans);
					}
					
				}
				
//				// update mt4 trading account's group
//				String last_update_user = "SAP";
//				takeLog("Clearing current day client group mapping");
//				clientGroupMappingDao.deleteClientGroupMappingByCreateDate(tradeDate);
//				takeLog("Getting client group mapping from SAP");
//				List<ClientGroupMappingBean> clientGroupMappingBeans = clientGroupMappingDao.getFromSAP(tradeDate);
//				takeLog("Uploading mapping to db");
//				clientGroupMappingDao.saveClientGroupMapping(clientGroupMappingBeans, last_update_user);
				
				
				
			}
			else{
				takeLog("ERROR OCCUR! no client details retrieved from SAP");
			}
			
			takeLog("Finish updating client details");

			
		}catch(Exception ex){
			logger.error(ex, ex);
			scheduleTaskStatus = Constants.SCHEDULE_STATUS_FAILED;
			System.out.println(ex.getMessage());
			System.out.println(ex.getStackTrace());
			errorTasks.put(scheduleTaskType, ex.getMessage());
		}
		
		
		Date finishTime = new Date();
		int diff = (int)((finishTime.getTime() - startTime.getTime()) / 1000);
		
		ScheduleTaskBean bean = new ScheduleTaskBean();
		bean.setTrade_date(tradeDate);
		bean.setSpend_time(diff);
		bean.setStatus(scheduleTaskStatus);
		bean.setType(scheduleTaskType);
		scheduleTaskDao.updateScheduleTask(bean);
		
		String[] rtn = new String[2];
		rtn[0] = scheduleTaskStatus;
		rtn[1] = diff + "";
		return rtn;
	}
	
	
	
	public String[] updateIbLeads(Date tradeDate) throws Exception{
		takeLog("----------------------------------");
		takeLog("Update IB leads");
		
		Date startTime = new Date();
		String scheduleTaskStatus = Constants.SCHEDULE_STATUS_EXECUTED;
		String scheduleTaskType = Constants.SCHEDULE_TYPE_UPDATE_IB_LEADS;
		try{
			takeLog("Get removed ib leads from SAP...");
			List<String> removedSalesforcedIDs = ibLeadDao.getDeletedLeadFromSAP(tradeDate);
			takeLog("Total: " + removedSalesforcedIDs.size());
			if(removedSalesforcedIDs.size() > 0){
				takeLog("Delete ib leads by sales forced ids in IB terminal...");
				int deletedLeads = ibLeadDao.deleteIbLeads(removedSalesforcedIDs);
				takeLog("Total: " + deletedLeads);
			}
			String tradeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, tradeDate);
			takeLog("Delete ib leads in IB terminal, trade date: " + tradeDateString);
			int deletedLeads = ibLeadDao.deleteIbLeadsByTradeDate(tradeDate);
			takeLog("Total: " + deletedLeads);
			
			// get data from SAP first
			takeLog("Getting new ib leads from SAP...");
			List<IbLeadBean> beanList = ibLeadDao.getNewLeadFromSAP(tradeDate);
			takeLog("Total: "+ beanList.size());
			takeLog("Inserting new ib leads to IB terminal...");
			ibLeadDao.saveIbLeads(beanList);
			
			takeLog("Finish updating IB leads");

			
		}catch(Exception ex){
			logger.error(ex, ex);
			scheduleTaskStatus = Constants.SCHEDULE_STATUS_FAILED;
			errorTasks.put(scheduleTaskType, ex.getMessage());
		}
		
		
		Date finishTime = new Date();
		int diff = (int)((finishTime.getTime() - startTime.getTime()) / 1000);
		
		ScheduleTaskBean bean = new ScheduleTaskBean();
		bean.setTrade_date(tradeDate);
		bean.setSpend_time(diff);
		bean.setStatus(scheduleTaskStatus);
		bean.setType(scheduleTaskType);
		scheduleTaskDao.updateScheduleTask(bean);
		
		String[] rtn = new String[2];
		rtn[0] = scheduleTaskStatus;
		rtn[1] = diff + "";
		return rtn;
	}
	
	
	public String[] updateSettingsSymbol(Date tradeDate) throws Exception{
		takeLog("----------------------------------");
		takeLog("Update Settings Symbol");
		
		Date startTime = new Date();
		String scheduleTaskStatus = Constants.SCHEDULE_STATUS_EXECUTED;
		String scheduleTaskType = Constants.SCHEDULE_TYPE_UPDATE_SETTINGS_SYMBOL;
		try{
			Date currentDate = DateUtil.trimTime(tradeDate);
			Date threeMonthsBeforeDate = DateUtil.getMonthsBeforeDate(currentDate, 3);
			
			String lastDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, currentDate);
			String threeMonthsBeforeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, threeMonthsBeforeDate);
			
			
			// clear current date settings symbol
			int recordDeleted = 0;
			takeLog("Clearing data, trade date: " + lastDateString);
			recordDeleted = settingsSymbolDao.deleteByTradeDate(currentDate);
			takeLog("Deleted: " + recordDeleted);
			takeLog("Clearing data, trade date: " + threeMonthsBeforeDateString);
			recordDeleted = settingsSymbolDao.deleteByTradeDate(threeMonthsBeforeDate);
			takeLog("Deleted: " + recordDeleted);
			
			// clear 3 month ago client trade detail
			takeLog("Getting data from SAP... trade date: " + lastDateString);
			List<SettingsSymbolBean> beanList = settingsSymbolDao.getSettingsSymbolFromSAP(currentDate);
			takeLog(beanList.size() + " records return.");
			takeLog("Upload data to ib terminal database");
			settingsSymbolDao.saveSettingsSymbol(beanList, currentDate);
			
			takeLog("Update spread from MT5");
			updateDefaultSpreadFromMT4(currentDate, beanList);
		
			
			takeLog("Finish updating settings symbol");

			
		}catch(Exception ex){
			logger.error(ex, ex);
			scheduleTaskStatus = Constants.SCHEDULE_STATUS_FAILED;
			errorTasks.put(scheduleTaskType, ex.getMessage());
		}
		
		Date finishTime = new Date();
		int diff = (int)((finishTime.getTime() - startTime.getTime()) / 1000);
		
		ScheduleTaskBean bean = new ScheduleTaskBean();
		bean.setTrade_date(tradeDate);
		bean.setSpend_time(diff);
		bean.setStatus(scheduleTaskStatus);
		bean.setType(scheduleTaskType);
		scheduleTaskDao.updateScheduleTask(bean);
		
		String[] rtn = new String[2];
		rtn[0] = scheduleTaskStatus;
		rtn[1] = diff + "";
		return rtn;
	}
	
	

	
	


	public String[] updateIbClientMap(Date tradeDate) throws Exception{
		takeLog("----------------------------------");
		takeLog("Update IB client maps");
		
		Date startTime = new Date();
		String scheduleTaskStatus = Constants.SCHEDULE_STATUS_EXECUTED;
		String scheduleTaskType = Constants.SCHEDULE_TYPE_UPDATE_IB_CLIENT_MAP;
		try{
			
			takeLog("Getting ib client map from SAP...");
			List<IbClientMapDto> sapDtos = ibClientMapDao.getIbClientMapByCreateDate(tradeDate);
			takeLog("Total: " + sapDtos.size() + " ib client maps return");
			List<IbClientMapBean> sapBeans = new ArrayList<IbClientMapBean>();
			List<Integer> sapClientCodes = new ArrayList<Integer>();
			HashMap<String, IbClientMapBean> sapMap = new HashMap<String, IbClientMapBean>();
			
			for(IbClientMapDto dto : sapDtos){
				IbClientMapBean bean = dto.convertToIbClientMapBean(); 
				sapBeans.add(bean);
				sapClientCodes.add(Integer.parseInt(bean.getClient_code()));
				if(!sapMap.containsKey(bean.getClient_code())){
					sapMap.put(bean.getClient_code(), bean);
				}
			}
			
			takeLog("Checking any ib client map has been already imported in IB terminal...");
			List<IbClientMapBean> dbBeans = ibClientMapDao.getIbClientMapByClientCodes(sapClientCodes);
			if(dbBeans.size() > 0){
				List<String> errMsgs = new ArrayList<String>();
				for(IbClientMapBean dbBean : dbBeans){
					IbClientMapBean sapBean = sapMap.get(dbBean.getClient_code());
					
					if(dbBean.getEnd_date() != null){
						if(sapBean.getStart_date().compareTo(dbBean.getEnd_date()) <= 0){
							errMsgs.add(Constants.ERR_IB_CLIENT_MAP_ALREADY_EXIST + "|" + dbBean.getClient_code() + "<br/>");
							sapBeans.remove(sapBean);
						}
					}
					else{
						sapBeans.remove(sapBean);
						if(dbBean.getStart_date().after(sapBean.getStart_date())){
							errMsgs.add(Constants.ERR_IB_CLIENT_MAP_ALREADY_EXIST + "|" + dbBean.getClient_code() + "<br/>");
						}
					}	
				}
				if(sapBeans.size() > 0){
					ibClientMapDao.saveIbClientMap(sapBeans);	
				}
				
				if(errMsgs.size() > 0){
					scheduleTaskStatus = Constants.SCHEDULE_STATUS_FAILED;
					errorTasks.put(scheduleTaskType, StringUtils.join(errMsgs.toArray()));
					takeLog("Update ib client map error: " + errMsgs);
				}
				else{
					takeLog("Finish updating IB client maps");
				}
				// error client code already exist
			}
			else{
				
				ibClientMapDao.saveIbClientMap(sapBeans);
				takeLog("Finish updating IB client maps");
			}
				
			

		}catch(Exception ex){
			logger.error(ex, ex);
			takeLog(ex.getMessage());
			scheduleTaskStatus = Constants.SCHEDULE_STATUS_FAILED;
			errorTasks.put(scheduleTaskType, ex.getMessage());
		}
		
		Date finishTime = new Date();
		int diff = (int)((finishTime.getTime() - startTime.getTime()) / 1000);
		
		ScheduleTaskBean bean = new ScheduleTaskBean();
		bean.setTrade_date(tradeDate);
		bean.setSpend_time(diff);
		bean.setStatus(scheduleTaskStatus);
		bean.setType(scheduleTaskType);
		
		scheduleTaskDao.updateScheduleTask(bean);
		
		String[] rtn = new String[2];
		rtn[0] = scheduleTaskStatus;
		rtn[1] = diff + "";
		return rtn;
	}
	
	public String[] calculateIbCommission(Date tradeDate, String ibTeamHead, String sender) throws Exception{
		Date startTime = new Date();
		String scheduleTaskStatus = Constants.SCHEDULE_STATUS_EXECUTED;
		String scheduleTaskType = Constants.SCHEDULE_TYPE_UPDATE_CALCULATE_IB_COMMISSION;
		try{
			CalculateIbCommissionRequestDto dto = new CalculateIbCommissionRequestDto();
			CalculateIbCommissionRequest request = new CalculateIbCommissionRequest();
			
			dto.setTradeDate(tradeDate);
			
			request.setBody(dto);
			
			CalculateIbCommissionResponseDto responseDto = ibCommissionService.calculateCommission(request, ibTeamHead, sender);
			
			if(responseDto.getErrorMsgs().values().size() > 0){
				takeLog("Error occur when calculating the ib commission.");
				String errMsgs = "";
				for(String errMsg : responseDto.getErrorMsgs().values()){
					takeLog(errMsg);
					System.out.println(errMsg);
					errMsgs += errMsg + "<br/>";
				}
				scheduleTaskStatus = Constants.SCHEDULE_STATUS_FAILED;
				errorTasks.put(scheduleTaskType, errMsgs);
			}

			
		}catch(Exception ex){
			logger.error(ex, ex);
			takeLog(ex.getLocalizedMessage());
			scheduleTaskStatus = Constants.SCHEDULE_STATUS_FAILED;
			errorTasks.put(scheduleTaskType, ex.getMessage());
		}
		
		Date finishTime = new Date();
		int diff = (int)((finishTime.getTime() - startTime.getTime()) / 1000);
		
		ScheduleTaskBean bean = new ScheduleTaskBean();
		bean.setTrade_date(tradeDate);
		bean.setSpend_time(diff);
		bean.setStatus(scheduleTaskStatus);
		bean.setType(scheduleTaskType);
		
		scheduleTaskDao.updateScheduleTask(bean);
		
		String[] rtn = new String[2];
		rtn[0] = scheduleTaskStatus;
		rtn[1] = diff + "";
		return rtn;
	}
	
	private void takeLog(String logMsg){
		logger.info(logMsg);
		System.out.println(logMsg);
	}
	
	
	private void sendCornJobFailEmail(HashMap<String, String> errorTasksMap){
		takeLog("Sending corn job email start");
		
		Map<String, Object> emailModel = new HashMap<String, Object>();
		String template = Constants.EMAIL_TEMPLATE_CORN_JOB_FAIL;
		String emailSubject = emailConfig.getCornJobFailSubject();
		
		emailModel.put(Constants.EMAIL_RECEIVER, emailConfig.getCornJobRecipients());
		emailModel.put(Constants.EMAIL_SUBJECT, emailSubject);
		emailModel.put(Constants.EMAIL_TEMPLATE, template);
		emailModel.put(Constants.EMAIL_BCC, emailConfig.getCornJobRecipientsBcc());
		emailModel.put(Constants.EMAIL_SENDER, emailConfig.getCornJobFailSender());
		SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String dateString = DateUtil.dateToStringByFormat(formater, new Date());
		emailModel.put("Time", dateString);
		
		// set host
		String content = "";
		for(String taskType : errorTasksMap.keySet()){

			String description = "Unknown error.";
			if(taskType.equals(Constants.SCHEDULE_TYPE_UPDATE_CALCULATE_IB_COMMISSION)){
				description = "Calculate IB Commmission";
			}
			else if(taskType.equals(Constants.SCHEDULE_TYPE_UPDATE_CLIENT_BALANCE_SUMMARY)){
				description = "Update Cilent Balance Summary";
			}
			else if(taskType.equals(Constants.SCHEDULE_TYPE_UPDATE_CLIENT_DETAILS)){
				description = "Update Cilent Details";
			}
			else if(taskType.equals(Constants.SCHEDULE_TYPE_UPDATE_CLIENT_MARGIN_IN_OUT)){
				description = "Update Client Margin In Out";
			}
			else if(taskType.equals(Constants.SCHEDULE_TYPE_UPDATE_CLIENT_TRADE_DETAIL)){
				description = "Update Client Trade Details";
			}
			else if(taskType.equals(Constants.SCHEDULE_TYPE_UPDATE_IB_ACCOUNT_DETAILS_DAY_OPEN)){
				description = "Update IB Account Details Day Open";
			}
			else if(taskType.equals(Constants.SCHEDULE_TYPE_UPDATE_IB_CLIENT_MAP)){
				description = "Update IB Client Map";
			}
			else if(taskType.equals(Constants.SCHEDULE_TYPE_UPDATE_IB_LEADS)){
				description = "Update IB Leads";
			}
			else if(taskType.equals(Constants.SCHEDULE_TYPE_UPDATE_SETTINGS_SYMBOL)){
				description = "Update Settings Symbol";
			}
			content += "<b>" + description + "</b><br/>" + errorTasksMap.get(taskType) + "<br/><br/>";
		}
		emailModel.put("Content", content);
		
		// set host
		try{
			InetAddress address = InetAddress.getLocalHost();
			emailModel.put("Host", address.getHostAddress());
		}catch(Exception ex){
			logger.error(ex, ex);
			emailModel.put("Host", "Unknown");
		}
		
		emailUtil.sendEmail(emailModel);
		
		takeLog("Sending corn job email finish");
	}
	

	public void updateSpreadFromMT5() throws Exception {
		List<SettingsSymbolBean> symbolList = new ArrayList<SettingsSymbolBean>();
		
		GetAllMT5SymbolRequestDto getAllMT5SymbolRequestDto = new GetAllMT5SymbolRequestDto();
		Mt4WebServiceConnectionModel model = systemParamsDao.getMt5ServiceConnectionModel();
		getAllMT5SymbolRequestDto.setSymbolPathPrefix("IE");
		String mtResponse = mt4ServiceAdapterFactory.sendRequest(model, getAllMT5SymbolRequestDto);
		Object mtResModel = mt4ServiceAdapterFactory.getResponseObject(getAllMT5SymbolRequestDto, mtResponse);
		
		if (mtResModel != null) {
			if (mtResModel instanceof GetAllMT5SymbolsResponse) {

				List<MT5SymbolRecord> symbols = ((GetAllMT5SymbolsResponse) mtResModel).getSymbols();
				if(symbols.size() > 0){
					takeLog("Deleting default spreads.");
					int res = settingsSymbolDao.deleteDefaultSpreads();
					takeLog("Deleted " + res + " default spreads.");
					settingsSymbolDao.saveMT5Spread(symbols);
					takeLog("Uploaded " + symbols.size() + " default spread to db");
				}
			}
		}
	}

	public void updateSpreadFromMT4() throws Exception {
		
		GetAllSymbolRequestDto getAllSymbolRequestDto = new GetAllSymbolRequestDto();
		Mt4WebServiceConnectionModel model = systemParamsDao.getMt4ServiceConnectionModel();
		
		String mt4Response = mt4ServiceAdapterFactory.sendRequest(model, getAllSymbolRequestDto);
		Object mt4ResModel = mt4ServiceAdapterFactory.getResponseObject(getAllSymbolRequestDto, mt4Response);
		
		if(mt4ResModel != null){
			if(mt4ResModel instanceof GetAllSymbolsResponse){
				// massage mt4 response model
				GetAllSymbolsResponse getAllSymbolsResponse = (GetAllSymbolsResponse)mt4ResModel;
				List<SymbolRecord> symbolRecords = getAllSymbolsResponse.getSymbols();
				if(symbolRecords.size() > 0){
					takeLog("Deleting default spreads.");
					int res = settingsSymbolDao.deleteDefaultSpreads();
					takeLog("Deleted " + res + " default spreads.");
					settingsSymbolDao.saveDefaultSpread(symbolRecords);
					takeLog("Uploaded " + symbolRecords.size() + " default spread to db");
				}	
			}
		} 
	}
	
	

	
	public void updateDefaultSpreadFromMT4(Date trade_date, List<SettingsSymbolBean> insertedBeans) throws Exception{
		
		String last_update_user = "MT4";
		
		
		List<String> symbols = new ArrayList<String>();
		for(SettingsSymbolBean insertedBean : insertedBeans){
			symbols.add(insertedBean.getSymbol());
		}
		
		List<SymbolRecord> symbolRecords = new ArrayList<SymbolRecord>();
		List<SettingsSymbolBean> remainedSymbolRecords = new ArrayList<SettingsSymbolBean>();
		for(SymbolRecord symbolRecord : settingsSymbolDao.getAllDefaultSpreads()){
			if(symbols.contains(symbolRecord.getSymbol())){
				symbolRecords.add(symbolRecord);
			}
			else{
				SettingsSymbolBean bean = symbolRecord.getSettingsSymbolBean(trade_date);
				remainedSymbolRecords.add(bean);
			}
		}
		// update to IB terminal db
		settingsSymbolDao.updateSettingsSymbolSpreads(symbolRecords, trade_date, last_update_user);
		
		// insert remained symbols to ib terminal db
		settingsSymbolDao.saveSettingsSymbol(remainedSymbolRecords, trade_date); 
		
	}
	
	
	
	public void Test() throws Exception{
	    
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(DateUtil.getLastDay());
	    int year = cal.get(Calendar.YEAR);
	    int month = cal.get(Calendar.MONTH);
	    int day = 1;
	    cal.set(year, month, day);
		
		Date startDate = cal.getTime();
		Date endDate = DateUtil.getLastDay();
		String brandCode = "INT";
		String sender = "intadmin";
		SendIbCommissionReport(startDate, endDate, brandCode, sender, true);
	}
	
	public void SendIbCommissionReport(Date startDate, Date endDate, String brandCode, String intadmin, boolean isSendChrisReport) throws Exception{
		
		takeLog("Start SendIbCommissionReport, StartDate:" + startDate.toString() + " EndDate:" + endDate.toString());
		takeLog("Getting ib owner");
		Map<String, String> ibOwnerMap = ibCommissionSummaryDao.getIbCommissionSummaryRelatedOwners(startDate, endDate, brandCode, intadmin);
		
		if(isSendChrisReport){
			// generating chris report
			HSSFWorkbook workbook = new HSSFWorkbook();
			takeLog("Generating all ib commission summary report");
			addChrisReportSheet2WorkBook(workbook, brandCode, startDate, endDate, ibOwnerMap.keySet().iterator());
			
			// send chris report
			takeLog("sending report to chris");
			ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
			workbook.write(byteOutput);
			sendReportEmail(new ByteArrayResource(byteOutput.toByteArray()), emailConfig.getIbCommissionSummaryReportAdminEmail(), startDate, endDate, emailConfig.getIbCommissionSummaryReportAdminName());
			//sendReportEmail(new ByteArrayResource(byteOutput.toByteArray()), "oscar.yeung@henyep.com", startDate, endDate, "oscar.yeung@henyep.com");
						
			
		}
		
		// generating sales reports
		takeLog("Generating sales reports");
		Calendar cal = Calendar.getInstance();
		if(cal.get(Calendar.DAY_OF_WEEK) == 2){
			takeLog("Monday, so send report to all intl sales ");
			for(String ibOwner : ibOwnerMap.keySet()){
				HSSFWorkbook salesWorkbook = new HSSFWorkbook();
				takeLog(ibOwner + " " + ibOwnerMap.get(ibOwner));
				String email = ibOwnerMap.get(ibOwner);
				if(email != null){
					email += ",chris.hinde@hycm.com";
					takeLog("sending report to " + email);
					addReportSheet2WorkBook(salesWorkbook, brandCode, startDate, endDate, ibOwner, ibOwner);
					// send sales eamil
					ByteArrayOutputStream salesByteOutput = new ByteArrayOutputStream();
					salesWorkbook.write(salesByteOutput);
					sendReportEmail(new ByteArrayResource(salesByteOutput.toByteArray()), email, startDate, endDate, ibOwner);
				}		
			}
		}
		else{
			takeLog("Not Monday, no need to send sales report");
		}
		takeLog("End SendIbCommissionReport");
	}
	
	private void addChrisReportSheet2WorkBook(HSSFWorkbook workbook, String brandCode, Date startDate, Date endDate, Iterator<String> ibOwners) throws Exception{
//		HSSFCellStyle style = workbook.createCellStyle();
//		style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
//		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//	
//		
//		GetIbCommissionReportRequest request = new GetIbCommissionReportRequest();
//		GetIbCommissionReportRequestDto dto = new GetIbCommissionReportRequestDto();
//		dto.setBrand_code(brandCode);
//		dto.setStart_date(startDate);
//		dto.setEnd_date(endDate);
//		SenderDto senderDto = new SenderDto();
//		senderDto.setSender(sender);
//		request.setBody(dto);
//		reportService.setIBCommissionSummaryToWorkSheet(request, workbook, style, senderDto, sheetName);
		
		
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		
		GetIbCommissionReportRequest request = new GetIbCommissionReportRequest();
		GetIbCommissionReportRequestDto dto = new GetIbCommissionReportRequestDto();
		request.setBody(dto);
		
		dto.setBrand_code(brandCode);
		dto.setEnd_date(endDate);
		
		
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.MONTH, 0);
		dto.setStart_date(cal.getTime());
		
		SenderDto senderDto = new SenderDto();
		
		String ibOwner = "intadmin";
		
		senderDto.setSender(ibOwner);
		reportService.setIBCommissionSummaryToWorkSheet(request, workbook, style, senderDto, "All IBs - YTD");
		dto.setStart_date(startDate);
		reportService.setIBCommissionSummaryToWorkSheet(request, workbook, style, senderDto, "All IBs - MTD");
		
		senderDto.setSender("dubaiHead");
		reportService.setIBCommissionSummaryToWorkSheet(request, workbook, style, senderDto, "All HYCM INT IBs - YTD");
		dto.setStart_date(startDate);
		reportService.setIBCommissionSummaryToWorkSheet(request, workbook, style, senderDto, "All HYCM INT IBs - MTD");
		
		senderDto.setSender("asiyaHead");
		reportService.setIBCommissionSummaryToWorkSheet(request, workbook, style, senderDto, "All HYCM KW IBs - YTD");
		dto.setStart_date(startDate);
		reportService.setIBCommissionSummaryToWorkSheet(request, workbook, style, senderDto, "All HYCM KW IBs - MTD");	
		
		
		Map<String, List<String>> ytdSalesSummaryDict = new HashMap<String, List<String>>();
		Map<String, List<String>> mtdSalesSummaryDict = new HashMap<String, List<String>>();
		
		while(ibOwners.hasNext()){
			ibOwner = ibOwners.next();
			senderDto = new SenderDto();
			senderDto.setSender(ibOwner);
			
			cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.MONTH, 0);
			dto.setStart_date(cal.getTime());
			List<String> ytdSalesSummaryList = reportService.setIBCommissionSummaryToWorkSheet(request, workbook, style, senderDto, ibOwner + " - YTD");
			ytdSalesSummaryDict.put(ibOwner, ytdSalesSummaryList);
			dto.setStart_date(startDate);
			List<String> mtdSalesSummaryList = reportService.setIBCommissionSummaryToWorkSheet(request, workbook, style, senderDto, ibOwner + " - MTD");
			mtdSalesSummaryDict.put(ibOwner, mtdSalesSummaryList);
		}		
		
		reportService.setSummaryToWorkSheet(workbook, style, ytdSalesSummaryDict, mtdSalesSummaryDict);
		
		
		senderDto = new SenderDto();
		senderDto.setSender("intadmin");
		dto.setStart_date(startDate);
		reportService.setClientCommissionSummaryPeriodToWorkSheet(request, workbook, style, senderDto, "Client Summary (Period) - MTD");
	}

	
	
	private void addReportSheet2WorkBook(HSSFWorkbook workbook, String brandCode, Date startDate, Date endDate, String sheetName, String sender) throws Exception{
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	
		
		GetIbCommissionReportRequest request = new GetIbCommissionReportRequest();
		GetIbCommissionReportRequestDto dto = new GetIbCommissionReportRequestDto();
		dto.setBrand_code(brandCode);
		dto.setEnd_date(endDate);
		SenderDto senderDto = new SenderDto();
		senderDto.setSender(sender);
		request.setBody(dto);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.MONTH, 0);
		dto.setStart_date(cal.getTime());
		reportService.setIBCommissionSummaryToWorkSheet(request, workbook, style, senderDto, sheetName + " - YTD");
		dto.setStart_date(startDate);
		reportService.setIBCommissionSummaryToWorkSheet(request, workbook, style, senderDto, sheetName + " - MTD");
		reportService.setClientCommissionSummaryPeriodToWorkSheet(request, workbook, style, senderDto, "Client Summary (Period) - MTD");
	}
	
	private void sendReportEmail(ByteArrayResource inputStream, String receiver, Date startDate, Date endDate, String receiverName){
		
		Map<String, Object> emailModel = new HashMap<String, Object>();
		String template = Constants.EMAIL_TEMPLATE_IB_COMMISSION_SUMMARY;
		String emailSubject = emailConfig.getIbCommissionSummaryReportSubject();
		
		emailModel.put(Constants.EMAIL_RECEIVER, receiver);
		emailModel.put(Constants.EMAIL_SUBJECT, emailSubject);
		emailModel.put(Constants.EMAIL_TEMPLATE, template);
		emailModel.put(Constants.EMAIL_BCC, "oscar.yeung@henyep.com");
		emailModel.put(Constants.EMAIL_SENDER, emailConfig.getIbCommissionSummaryReportSender());
		SimpleDateFormat dateTimeformater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentDateTimeString = DateUtil.dateToStringByFormat(dateTimeformater, new Date());
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat simpleDateFormater = new SimpleDateFormat("yyyyMMdd");
		String startDateString = DateUtil.dateToStringByFormat(dateFormater, startDate);
		String endDateString = DateUtil.dateToStringByFormat(dateFormater, endDate);
		String currentDateSimpleString = DateUtil.dateToStringByFormat(simpleDateFormater, new Date());
		emailModel.put("StartDate", startDateString);
		emailModel.put("EndDate", endDateString);
		emailModel.put("CreateReportTime", currentDateTimeString);
		emailModel.put("ReceiverName", receiverName);
		emailModel.put(Constants.EMAIL_ATTACHMENT, inputStream);
		emailModel.put(Constants.EMAIL_ATTACHMENT_NAME, currentDateSimpleString + ".xls");
		
		emailUtil.sendEmail(emailModel);
	}
	
	
	
	
	
	
}