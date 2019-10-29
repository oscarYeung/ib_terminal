package com.henyep.ib.terminal.server.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.henyep.ib.terminal.api.dto.request.BaseRequestHeader;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.server.dto.security.SenderDto;
import com.henyep.ib.terminal.server.exception.ServiceException;
import com.henyep.ib.terminal.server.util.SecurityUtil;

public abstract class AbstractServiceImpl {

	protected final transient Log logger = LogFactory.getLog(getClass());

	@Resource(name = "SecurityUtil")
	private SecurityUtil securityUtil;

	protected String getSender(BaseRequestHeader header) {
		String sender = null;
		try {
			if (header != null && header.getSecretKey() != null) {
				SenderDto dto = securityUtil.getSenderDto(header.getSecretKey());
				if (dto != null) {
					sender = dto.getSender();
				}
			} else {
				logger.debug("NULL header / SecretKey");
				sender = Constants.USER_SYSTEM;
			}
		} catch (ServiceException e) {
			logger.error("SecretKey:" + header.getSecretKey());
			logger.error(e, e);
		}
		return sender;
	}
}
