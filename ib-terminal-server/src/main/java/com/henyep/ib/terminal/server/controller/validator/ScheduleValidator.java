package com.henyep.ib.terminal.server.controller.validator;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.api.dto.request.schedule.task.BatchCalIbCommissionRequest;
import com.henyep.ib.terminal.api.dto.request.schedule.task.CalIbCommissionRequest;
import com.henyep.ib.terminal.api.dto.request.schedule.task.RunScheduleTaskRequest;
import com.henyep.ib.terminal.api.global.Constants;

@Component(value = "ScheduleValidator")
public class ScheduleValidator extends AbstractValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		if (RunScheduleTaskRequest.class.isAssignableFrom(clazz) ||
				CalIbCommissionRequest.class.isAssignableFrom(clazz) ||
				BatchCalIbCommissionRequest.class.isAssignableFrom(clazz)
				)
			return true;
		else
			return false;
	}

	@Override
	protected void initSupportedChannelList() {
		this.supportedChannelList = new ArrayList<String>();
		this.supportedChannelList.add(Constants.CHANNEL_ADMIN);
	}

	
}
