package com.henyep.ib.terminal.server.controller.validator;

import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.api.dto.request.rebate.GetRebateByIbCodeRequest;
import com.henyep.ib.terminal.api.dto.request.rebate.GetRebateByIbCodeWithDateRequest;
import com.henyep.ib.terminal.api.dto.request.rebate.GetRebateByRebateCodeWithDateRequest;
import com.henyep.ib.terminal.api.dto.request.rebate.InsertRebateRequest;
import com.henyep.ib.terminal.api.dto.request.rebate.InsertRebateSupplementariesRequest;
import com.henyep.ib.terminal.api.dto.request.rebate.UpdateRebateRequest;
import com.henyep.ib.terminal.api.dto.request.rebate.UpdateRebateSupplementariesRequest;
import com.henyep.ib.terminal.server.global.Constants;

@Component
public class RebateValidator extends AbstractValidator {

	@Override
	protected void initSupportedChannelList() {
		super.initSupportedChannelList();
		this.supportedChannelList.add(Constants.CHANNEL_ADMIN);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		if (GetRebateByIbCodeWithDateRequest.class.isAssignableFrom(clazz) || GetRebateByRebateCodeWithDateRequest.class.isAssignableFrom(clazz)
				|| UpdateRebateRequest.class.isAssignableFrom(clazz) || InsertRebateRequest.class.isAssignableFrom(clazz) 
				|| UpdateRebateSupplementariesRequest.class.isAssignableFrom(clazz) || InsertRebateSupplementariesRequest.class.isAssignableFrom(clazz)
				|| GetRebateByIbCodeRequest.class.isAssignableFrom(clazz))
			return true;
		else
			return false;
	}
}
