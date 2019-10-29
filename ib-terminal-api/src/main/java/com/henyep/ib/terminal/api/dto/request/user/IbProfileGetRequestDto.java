package com.henyep.ib.terminal.api.dto.request.user;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

public class IbProfileGetRequestDto implements Serializable
{
	private static final long serialVersionUID = 5870324152629674822L;
	@NotNull(message = "{profile.get.brandCode.not.null}")
	private String brandCode;// utf8.general.ci NO PRI (NULL) select,insert,update,references
	private String ibCode;
	private String username;// utf8.general.ci NO (NULL) select,insert,update,references

	public String getIbCode()
	{
		return ibCode;
	}

	public void setIbCode(String ibCode)
	{
		this.ibCode = ibCode;
	}

	public String getBrandCode()
	{
		return brandCode;
	}

	public void setBrandCode(String brandCode)
	{
		this.brandCode = brandCode;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}
}
