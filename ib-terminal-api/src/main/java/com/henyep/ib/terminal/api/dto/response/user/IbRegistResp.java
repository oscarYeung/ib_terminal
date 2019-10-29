package com.henyep.ib.terminal.api.dto.response.user;

import java.io.Serializable;

public class IbRegistResp implements Serializable
{
	private static final long serialVersionUID = -2567057640525127219L;
	private Long ibCode;

	public Long getIbCode()
	{
		return ibCode;
	}

	public void setIbCode(Long ibCode)
	{
		this.ibCode = ibCode;
	}
}
