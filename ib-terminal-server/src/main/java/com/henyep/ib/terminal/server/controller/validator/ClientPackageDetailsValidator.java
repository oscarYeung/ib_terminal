package com.henyep.ib.terminal.server.controller.validator;

import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.api.dto.request.clientPackageDetails.GetAllClientPackagesRequest;
import com.henyep.ib.terminal.api.dto.request.clientPackageDetails.GetClientPackagesByIbCodeRequest;
import com.henyep.ib.terminal.api.global.Constants;

@Component(value = "ClientPackageDetailsValidator")
public class ClientPackageDetailsValidator extends AbstractValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		if (GetAllClientPackagesRequest.class.isAssignableFrom(clazz) || GetClientPackagesByIbCodeRequest.class.isAssignableFrom(clazz))
			return true;
		else
			return false;
	}

	@Override
	protected void initSupportedChannelList() {
		super.initSupportedChannelList();
		this.supportedChannelList.add(Constants.CHANNEL_ADMIN);
	}

}
