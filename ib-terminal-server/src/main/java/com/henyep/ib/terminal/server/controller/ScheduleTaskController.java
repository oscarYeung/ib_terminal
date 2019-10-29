package com.henyep.ib.terminal.server.controller;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.henyep.ib.terminal.api.dto.request.schedule.task.BatchCalIbCommissionRequest;
import com.henyep.ib.terminal.api.dto.request.schedule.task.CalIbCommissionRequest;
import com.henyep.ib.terminal.api.dto.request.schedule.task.RunScheduleTaskRequest;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.schedule.task.ClearScheduleTaskResponseDto;
import com.henyep.ib.terminal.api.dto.response.schedule.task.DataSyncFromSAPResponseDto;
import com.henyep.ib.terminal.api.dto.response.schedule.task.RunBatchCalIbComTaskResponseDto;
import com.henyep.ib.terminal.api.dto.response.schedule.task.RunCalIBCommTaskResponseDto;
import com.henyep.ib.terminal.api.dto.response.schedule.task.RunScheduleTaskResponseDto;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.server.controller.validator.ScheduleValidator;
import com.henyep.ib.terminal.server.dao.ScheduleTaskDao;
import com.henyep.ib.terminal.server.dao.SystemParamsDao;
import com.henyep.ib.terminal.server.schedule.ScheduleTask;

@Controller
@RequestMapping("/scheduleTask")
public class ScheduleTaskController extends AbstractController {

	public ScheduleTaskController(ScheduleValidator validator) {

		super(validator);
	}

	@Resource(name = "scheduleTask")
	ScheduleTask scheduleTask;
	
	@Resource(name = "ScheduleTaskDao")
	ScheduleTaskDao scheduleTaskDao;
	
	@Resource(name = "SystemParamsDao")
	SystemParamsDao systemParamsDao;

	@RequestMapping(value = "/clearScheduleTask", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<ClearScheduleTaskResponseDto> clearScheduleTask(@RequestBody @Valid RunScheduleTaskRequest request,
			BindingResult result) {
		logger.info("================= /scheduleTask/clearScheduleTask Start =================");
		logger.info("Clear schedule task request =" + g.toJson(request));
		IbResponseDto<ClearScheduleTaskResponseDto> response = null;
		try {
			response = dtoFactory.createAdminResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				Date tradeDate = request.getBody().getTrade_date();
				int isSuccess = scheduleTaskDao.deleteScheduleTask(tradeDate);
				ClearScheduleTaskResponseDto body = new ClearScheduleTaskResponseDto();
				body.setTrade_date(tradeDate);
				response.setBody(body);
				if (isSuccess <= 0) {
					hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_SCHEDULE_TASK_NOT_EXIST);
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		logger.info("Clear schedule task response =" + g.toJson(response));
		logger.info("================= /scheduleTask/clearScheduleTask End =================");
		return response;
	}
	
	
	@RequestMapping(value = "/runScheduleTask", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<RunScheduleTaskResponseDto> runScheduleTask(@RequestBody @Valid RunScheduleTaskRequest request,
			BindingResult result) {
		logger.info("================= /scheduleTask/runScheduleTask Start =================");
		logger.info("Run schedule task request =" + g.toJson(request));
		IbResponseDto<RunScheduleTaskResponseDto> response = null;
		try {
			response = dtoFactory.createAdminResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				Date tradeDate = request.getBody().getTrade_date();
				RunScheduleTaskResponseDto body = scheduleTask.doTaskByTradeDate(tradeDate);
				
				if (body.getComment() == null) {
					response.setBody(body);
				} else {
					hySpringUtil.setDtoErrorFromErrorCode(response, body.getComment());
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		logger.info("Run schedule task response =" + g.toJson(response));
		logger.info("================= /scheduleTask/runScheduleTask End =================");
		return response;
	}
	
	
	
	@RequestMapping(value = "/dataSyncFromSAP", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<DataSyncFromSAPResponseDto> dataSyncFromSAP(@RequestBody @Valid RunScheduleTaskRequest request,
			BindingResult result) {
		logger.info("================= /scheduleTask/dataSyncFromSAP =================");
		logger.info("Data sync from SAP request =" + g.toJson(request));
		IbResponseDto<DataSyncFromSAPResponseDto> response = null;
		try {
			response = dtoFactory.createAdminResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				
				boolean isDataSynRunning = systemParamsDao.isDataSyncRunning();
				
				if(!isDataSynRunning){

					Date tradeDate = request.getBody().getTrade_date();
					logger.info("Delete data sync schedule task");
					scheduleTaskDao.deleteDataSyncScheduleTask(tradeDate);
					logger.info("Init data sync schedule task");
					scheduleTaskDao.initDataSyncScheduleTask(tradeDate);
					logger.info("Update data from SAP");
					DataSyncFromSAPResponseDto body = scheduleTask.doDataSyncFromSAP(tradeDate, getSender(request));
					response.setBody(body);
					
				}
				else{
					hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_DATA_SYNC_RUNNING);
				}	
			}
		} catch (Exception e) {
			logger.error(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		logger.info("Data sync from SAP response =" + g.toJson(response));
	
		logger.info("================= /scheduleTask/dataSyncFromSAP End =================");
		return response;
	}
	
	@RequestMapping(value = "/batchCalIbComm", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<RunBatchCalIbComTaskResponseDto> batchCalIbComm(@RequestBody @Valid BatchCalIbCommissionRequest request,
			BindingResult result) {
		logger.info("================= /scheduleTask/batchCalIbComm Start =================");
		logger.info("Batch Calculate IB commission request =" + g.toJson(request));
		IbResponseDto<RunBatchCalIbComTaskResponseDto> response = null;
		try {
			response = dtoFactory.createAdminResponse(request.getHeader());
			System.out.println("5");
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				
				boolean isUpdatingIbComm = systemParamsDao.isUpdatingIbCommission();
				
				if(!isUpdatingIbComm){
					
					Date startDate = request.getBody().getStart_date();
					Date endDate = request.getBody().getEnd_date();
					RunBatchCalIbComTaskResponseDto body = new RunBatchCalIbComTaskResponseDto(); 
					
					if(startDate.compareTo(endDate) <= 0){
						Date tradeDate = startDate;
						while(tradeDate.compareTo(endDate) <= 0){
							Calendar cal = Calendar.getInstance();
							logger.info("Trade Date: " + tradeDate.toString());
							logger.info("Delete calculate ib commission schedule task");
							scheduleTaskDao.deleteCalIbCommTask(tradeDate);
							logger.info("Init calculate ib commission schedule task");
							scheduleTaskDao.initCalIbCommScheduleTask(tradeDate);
							String teamHead = request.getBody().getTeam_head();
							if(teamHead != null && !teamHead.equals(""))
								logger.info("Calculate ib commission, Team head: " + request.getBody().getTeam_head());
							else
								logger.info("Calculate ib commission");
							RunCalIBCommTaskResponseDto dto = scheduleTask.doCalculateIbCommission(tradeDate, request.getBody().getTeam_head(), getSender(request));
							body.addTaskResponse(dto, tradeDate);
							
							cal.setTime(tradeDate);
							cal.add(Calendar.DATE, 1);
							tradeDate = cal.getTime();
						}
						response.setBody(body);
					}
				}
				else{
					hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_IB_COMMISSION_UPDATING);
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		logger.info("Batch Calculate IB commission response =" + g.toJson(response));
		logger.info("================= /scheduleTask/batchCalIbComm End =================");
		return response;
	}
	
	
	@RequestMapping(value = "/calIbComm", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<RunCalIBCommTaskResponseDto> calIbComm(@RequestBody @Valid CalIbCommissionRequest request,
			BindingResult result) {
		logger.info("================= /scheduleTask/calIbComm Start =================");
		logger.info("Calculate IB commission request =" + g.toJson(request));
		IbResponseDto<RunCalIBCommTaskResponseDto> response = null;
		try {
			response = dtoFactory.createAdminResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				
				boolean isUpdatingIbComm = systemParamsDao.isUpdatingIbCommission();
				
				if(!isUpdatingIbComm){
					
					
					Date tradeDate = request.getBody().getTrade_date();
					logger.info("Delete calculate ib commission schedule task");
					scheduleTaskDao.deleteCalIbCommTask(tradeDate);
					logger.info("Init calculate ib commission schedule task");
					scheduleTaskDao.initCalIbCommScheduleTask(tradeDate);
					String teamHead = request.getBody().getTeam_head();
					if(teamHead != null && !teamHead.equals(""))
						logger.info("Calculate ib commission, Team head: " + request.getBody().getTeam_head());
					else
						logger.info("Calculate ib commission");
					
					RunCalIBCommTaskResponseDto body = scheduleTask.doCalculateIbCommission(tradeDate, request.getBody().getTeam_head(), getSender(request));
					response.setBody(body);
					
					
				}
				else{
					hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_IB_COMMISSION_UPDATING);
				}
				
			}
		} catch (Exception e) {
			logger.error(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		logger.info("Calculate IB commission response =" + g.toJson(response));
		logger.info("================= /scheduleTask/calIbComm End =================");
		return response;
	}
	
	
	
	

}
