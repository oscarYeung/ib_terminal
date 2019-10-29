package com.henyep.ib.terminal.api.dto.response.user;

import java.io.Serializable;

public class IbProfileEditResp implements Serializable
{
	private static final long serialVersionUID = -2567057640525127219L;
	private int recordNumberAffected;

	public int getRecordNumberAffected()
	{
		return recordNumberAffected;
	}

	public void setRecordNumberAffected(int recordNumberAffected)
	{
		this.recordNumberAffected = recordNumberAffected;
	}
}
