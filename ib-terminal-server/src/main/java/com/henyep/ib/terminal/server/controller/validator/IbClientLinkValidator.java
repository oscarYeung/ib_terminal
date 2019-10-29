package com.henyep.ib.terminal.server.controller.validator;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.api.dto.request.ib.client.link.AddIbClientLinkRequest;
import com.henyep.ib.terminal.api.dto.request.ib.client.link.GetIbClientLinkRequest;
import com.henyep.ib.terminal.api.dto.request.ib.client.link.UpdateIbClientLinkRequest;
import com.henyep.ib.terminal.api.global.Constants;

@Component
public class IbClientLinkValidator extends AbstractValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		if (GetIbClientLinkRequest.class.isAssignableFrom(clazz) ||
				AddIbClientLinkRequest.class.isAssignableFrom(clazz) ||
				UpdateIbClientLinkRequest.class.isAssignableFrom(clazz) )
			return true;
		else
			return false;
	}
	
	@Override
	protected void initSupportedChannelList(){
		supportedChannelList = new ArrayList<String>();
		supportedChannelList.add(Constants.CHANNEL_WEB);
		supportedChannelList.add(Constants.CHANNEL_ADMIN);
	}
}
