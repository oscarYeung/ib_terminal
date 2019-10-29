package com.henyep.ib.terminal.api.dto.response;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 项目名称：IBTerminalServer
 * <p>
 * 功能模块名称：
 * <p>
 * 文件名称为：IbResponseDto.java
 * <p>
 * 文件功能简述：
 * <p>
 * 文件创建人：Administrator——Jm.zhang
 * 
 * @version v1.0
 * @time 2016-8-17 下午3:33:45
 * @copyright Hengyp
 */
@JsonInclude(Include.NON_NULL)
public class IbResponseDto<T> implements Serializable
{
	private static final long serialVersionUID = -204991724184119861L;
	private BaseResponseHeader header;
	private T body;

	public BaseResponseHeader getHeader()
	{
		return header;
	}

	public void setHeader(BaseResponseHeader header)
	{
		this.header = header;
	}

	public T getBody()
	{
		return body;
	}

	public void setBody(T body)
	{
		this.body = body;
	}
}
