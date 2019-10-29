package com.henyep.ib.terminal.api.dto.request.user;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class IbRegistReq implements Serializable
{
	private static final long serialVersionUID = 4761449657461249798L;
	private String parentIbCode;
	@NotNull(message = "{reg.brandcode.not.blank}")
	private String brandCode;// utf8.general.ci NO PRI (NULL) select,insert,update,references
	@NotNull(message = "{reg.username.not.blank}")
	private String username;// utf8.general.ci NO (NULL) select,insert,update,references
	@NotNull(message = "{reg.password.not.blank}")
	private String password;// utf8.general.ci NO (NULL) select,insert,update,references
	@NotNull(message = "{reg.firstname.not.blank}")
	private String firstName;// utf8.general.ci NO (NULL) select,insert,update,references
	@NotNull(message = "{reg.lastname.not.blank}")
	private String lastName;// utf8.general.ci NO (NULL) select,insert,update,references
	@NotNull(message = "{reg.chinesename.not.blank}")
	private String chineseName;// utf8.general.ci NO (NULL) select,insert,update,references
	@NotNull(message = "{reg.sex.not.blank}")
	private String sex;// utf8.general.ci NO (NULL) select,insert,update,references
	@NotNull(message = "{reg.mobile.not.blank}")
	private String mobile;// utf8.general.ci NO (NULL) select,insert,update,references
	@NotNull(message = "{reg.country.not.blank}")
	private String country;// utf8.general.ci NO (NULL) select,insert,update,references
	@NotNull(message = "{reg.email.not.blank}")
	private String email;// utf8.general.ci NO (NULL) select,insert,update,references
	@NotNull(message = "{reg.address.not.blank}")
	private String address;// utf8.general.ci NO (NULL) select,insert,update,references
	@NotNull(message = "{reg.language.not.blank}")
	private String language;// utf8.general.ci NO (NULL) select,insert,update,references
	@NotNull(message = "{reg.status.not.blank}")
	private String status;// utf8.general.ci NO (NULL) select,insert,update,references
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;// (NULL) NO (NULL) select,insert,update,references
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateTime;// (NULL) NO (NULL) select,insert,update,references
	@NotNull(message = "{reg.lastupdateuser.not.blank}")
	private String lastUpdateUser;// utf8.general.ci NO (NULL) select,insert,update,references

	public String getParentIbCode()
	{
		return parentIbCode;
	}

	public void setParentIbCode(String parentIbCode)
	{
		this.parentIbCode = parentIbCode;
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

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getChineseName()
	{
		return chineseName;
	}

	public void setChineseName(String chineseName)
	{
		this.chineseName = chineseName;
	}

	public String getSex()
	{
		return sex;
	}

	public void setSex(String sex)
	{
		this.sex = sex;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getLanguage()
	{
		return language;
	}

	public void setLanguage(String language)
	{
		this.language = language;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public Date getLastUpdateTime()
	{
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime)
	{
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getLastUpdateUser()
	{
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser)
	{
		this.lastUpdateUser = lastUpdateUser;
	}
}
