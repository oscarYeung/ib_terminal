package com.henyep.ib.terminal.server.factory.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.api.dto.request.BaseRequestHeader;
import com.henyep.ib.terminal.api.dto.request.IbRequestDto;
import com.henyep.ib.terminal.api.dto.response.BaseResponseHeader;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.api.global.MessageType;
import com.henyep.ib.terminal.server.dto.security.SenderDto;
import com.henyep.ib.terminal.server.exception.ServiceException;
import com.henyep.ib.terminal.server.factory.DtoFactory;
import com.henyep.ib.terminal.server.util.DateUtil;

import com.henyep.ib.terminal.server.util.SecurityUtil;

/**
 * Simple DTO creating factory
 *
 * @author ShenZhong
 * @date 2016年8月18日
 */
@Component(value = "sz_SimpleDtoFactory")
public class SimpleDtoFactory implements DtoFactory {
	protected final transient Log logger = LogFactory.getLog(getClass());

	@Resource(name = "SecurityUtil")
	private SecurityUtil securityUtil;

	@Override
	public <T> IbRequestDto<T> createReq(Object... objects) {
		IbRequestDto<T> req = new IbRequestDto<T>();
		BaseRequestHeader header = new BaseRequestHeader();
		req.setHeader(header);
		return req;
	}

	@Override
	public <T> IbResponseDto<T> createResp(Object... objects) {
		IbResponseDto<T> resp = new IbResponseDto<T>();
		BaseResponseHeader header = new BaseResponseHeader();
		header.setStatus(Constants.RES_STATUS_SUCCESS);
		resp.setHeader(header);
		return resp;
	}

	private String getSecretKey(BaseRequestHeader header) throws ServiceException, Exception {
		String secretKey = null;
		if (header != null && StringUtils.isNotBlank(header.getSecretKey())) {
			try {
				SenderDto sender = this.securityUtil.getSenderDto(header.getSecretKey());
				secretKey = securityUtil.getSecretKey(sender.getSender());
			} catch (ServiceException e) {
				throw e;
			} catch (Exception e) {
				throw e;
			}
		}
		return secretKey;
	}

	@Override
	public <T> IbResponseDto<T> createWebResponse(BaseRequestHeader header) throws ServiceException, Exception {
		return createResponse(header, Constants.CHANNEL_WEB, MessageType.DUMMY, true);
	}

	@Override
	public <T> IbResponseDto<T> createAdminResponse(BaseRequestHeader header) throws ServiceException, Exception {
		return createResponse(header, Constants.CHANNEL_ADMIN, MessageType.DUMMY, true);
	}

	@Override
	public <T> IbResponseDto<T> createResponse(BaseRequestHeader header, String channel, String messageType, boolean genKey)
			throws ServiceException, Exception {
		IbResponseDto<T> response = new IbResponseDto<T>();
		BaseResponseHeader responseHeader = new BaseResponseHeader();
		if (genKey) {
			String secretKey = getSecretKey(header);
			responseHeader.setSecretKey(secretKey);
		}
		responseHeader.setChannelId(channel);
		responseHeader.setMessageType(messageType);
		responseHeader.setMessageDate(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE_TIME, new Date()));
		responseHeader.setStatus(Constants.RES_STATUS_SUCCESS);
		response.setHeader(responseHeader);
		return response;
	}
}
