package com.henyep.ib.terminal.server.controller.validator;

import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.api.dto.request.ibcommission.CalculateIbCommissionRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.CpaCalculateRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.EvConfirmRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.EvDataRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.EvFigureUpdateRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.GetIbClientSummaryRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.GetIbSummaryRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.GetLastTradeDateRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.GetTradingAccountListRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.UpdateDepositBonusRequest;
import com.henyep.ib.terminal.api.global.Constants;

@Component(value = "IbCommissionValidator")
public class IbCommissionValidator extends AbstractValidator {

	@Override
	protected void initSupportedChannelList() {

		super.initSupportedChannelList();
		this.supportedChannelList.add(Constants.CHANNEL_ADMIN);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		if (CalculateIbCommissionRequest.class.isAssignableFrom(clazz) || GetIbSummaryRequest.class.isAssignableFrom(clazz)
				|| EvDataRequest.class.isAssignableFrom(clazz) || UpdateDepositBonusRequest.class.isAssignableFrom(clazz)
				|| GetIbClientSummaryRequest.class.isAssignableFrom(clazz) || EvFigureUpdateRequest.class.isAssignableFrom(clazz)
				|| EvConfirmRequest.class.isAssignableFrom(clazz) || GetLastTradeDateRequest.class.isAssignableFrom(clazz)
				|| GetTradingAccountListRequest.class.isAssignableFrom(clazz) || CpaCalculateRequest.class.isAssignableFrom(clazz)) {
			return true;
		} else
			return false;
	}

}
