package com.henyep.ib.terminal.server.controller.validator;

import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.api.dto.request.product.symbol.GetCurrentProductGroupsRequest;
import com.henyep.ib.terminal.server.global.Constants;

@Component(value = "ProductSymbolMapValidator")
public class ProductSymbolMapValidator extends AbstractValidator {

	
	
	@Override
	protected void initSupportedChannelList() {		
		super.initSupportedChannelList();
		this.supportedChannelList.add(Constants.CHANNEL_ADMIN);
		
	}

	@Override
	public boolean supports(Class<?> clazz) {
		if (GetCurrentProductGroupsRequest.class.isAssignableFrom(clazz)) {
			return true;
		} else
			return false;
	}
}
