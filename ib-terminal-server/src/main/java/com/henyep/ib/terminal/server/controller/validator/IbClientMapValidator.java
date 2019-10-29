package com.henyep.ib.terminal.server.controller.validator;

import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.api.dto.request.ib.client.map.GetIbClientMapByIbCodeClientCodeRequest;
import com.henyep.ib.terminal.api.dto.request.ib.client.map.GetIbClientMapRequest;
import com.henyep.ib.terminal.api.dto.request.ib.client.map.InsertFromWlRegistrationRequest;
import com.henyep.ib.terminal.api.dto.request.ib.client.map.InsertIbClientMapRequest;
import com.henyep.ib.terminal.api.dto.request.ib.client.map.SwitchIbClientMapRequest;
import com.henyep.ib.terminal.api.dto.request.ib.client.map.UpdateIbClientMapChangeIbRequest;
import com.henyep.ib.terminal.api.dto.request.ib.client.map.UpdateIbClientMapChangePackageRequest;
import com.henyep.ib.terminal.api.dto.request.ib.client.map.UpdateIbClientMapCloseRequest;
import com.henyep.ib.terminal.api.dto.request.ib.client.map.UpdateIbClientMapRequest;
import com.henyep.ib.terminal.api.global.Constants;

@Component
public class IbClientMapValidator extends AbstractValidator {

	protected void initSupportedChannelList() {
		super.initSupportedChannelList();
		this.supportedChannelList.add(Constants.CHANNEL_ADMIN);
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		if (GetIbClientMapRequest.class.isAssignableFrom(clazz) ||
				GetIbClientMapByIbCodeClientCodeRequest.class.isAssignableFrom(clazz) ||
				UpdateIbClientMapRequest.class.isAssignableFrom(clazz) ||
				SwitchIbClientMapRequest.class.isAssignableFrom(clazz) ||
				InsertIbClientMapRequest.class.isAssignableFrom(clazz) ||
				InsertFromWlRegistrationRequest.class.isAssignableFrom(clazz) ||
				UpdateIbClientMapCloseRequest.class.isAssignableFrom(clazz) ||
				UpdateIbClientMapChangeIbRequest.class.isAssignableFrom(clazz) ||
				UpdateIbClientMapChangePackageRequest.class.isAssignableFrom(clazz) 
				
				)
			
			return true;
		else
			return false;
	}
}
