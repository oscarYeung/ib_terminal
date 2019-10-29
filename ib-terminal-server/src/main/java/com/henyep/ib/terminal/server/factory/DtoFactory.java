package com.henyep.ib.terminal.server.factory;

import com.henyep.ib.terminal.api.dto.request.BaseRequestHeader;
import com.henyep.ib.terminal.api.dto.request.IbRequestDto;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.server.exception.ServiceException;

public interface DtoFactory {
	<T> IbRequestDto<T> createReq(Object... objects);

	<T> IbResponseDto<T> createResp(Object... objects);

	<T> IbResponseDto<T> createWebResponse(BaseRequestHeader header) throws ServiceException, Exception;

	<T> IbResponseDto<T> createResponse(BaseRequestHeader header, String channel, String messageType, boolean genKey)
			throws ServiceException, Exception;

	<T> IbResponseDto<T> createAdminResponse(BaseRequestHeader header) throws ServiceException, Exception;
}
