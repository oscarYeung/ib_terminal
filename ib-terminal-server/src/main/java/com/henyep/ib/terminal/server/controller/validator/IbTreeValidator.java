package com.henyep.ib.terminal.server.controller.validator;

import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.api.dto.request.ib.tree.AddIbTreeByIbCodeRequest;
import com.henyep.ib.terminal.api.dto.request.ib.tree.AddIbTreeRequest;
import com.henyep.ib.terminal.api.dto.request.ib.tree.DeleteIbTreeRequest;
import com.henyep.ib.terminal.api.dto.request.ib.tree.GetIbTreeRequest;
import com.henyep.ib.terminal.api.dto.request.ib.tree.MoveIbTreeRequest;
import com.henyep.ib.terminal.api.dto.request.ib.tree.SearchIbRequest;
import com.henyep.ib.terminal.api.global.Constants;

@Component
public class IbTreeValidator extends AbstractValidator {

	@Override
	protected void initSupportedChannelList() {
		super.initSupportedChannelList();
		this.supportedChannelList.add(Constants.CHANNEL_ADMIN);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		if (GetIbTreeRequest.class.isAssignableFrom(clazz) || AddIbTreeRequest.class.isAssignableFrom(clazz)
				|| SearchIbRequest.class.isAssignableFrom(clazz) || DeleteIbTreeRequest.class.isAssignableFrom(clazz)
				|| MoveIbTreeRequest.class.isAssignableFrom(clazz) || AddIbTreeByIbCodeRequest.class.isAssignableFrom(clazz))
			return true;
		else
			return false;
	}
}
