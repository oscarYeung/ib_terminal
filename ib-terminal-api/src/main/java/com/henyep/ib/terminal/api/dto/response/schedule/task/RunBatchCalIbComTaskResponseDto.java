package com.henyep.ib.terminal.api.dto.response.schedule.task;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



public class RunBatchCalIbComTaskResponseDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5686136062738170776L;
	
	private Map<Date, RunCalIBCommTaskResponseDto> statusList = new HashMap<Date, RunCalIBCommTaskResponseDto>();
	
	public Map<Date, RunCalIBCommTaskResponseDto> getStatusList() {
		return statusList;
	}
	
	public void addTaskResponse(RunCalIBCommTaskResponseDto dto, Date tradeDate){
		
		if(statusList.containsKey(tradeDate)){
			statusList.remove(tradeDate);
		}
		statusList.put(tradeDate, dto);
	}
}
