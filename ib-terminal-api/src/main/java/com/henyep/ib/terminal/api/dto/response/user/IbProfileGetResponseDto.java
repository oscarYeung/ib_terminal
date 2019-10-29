package com.henyep.ib.terminal.api.dto.response.user;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class IbProfileGetResponseDto implements Serializable
{
	private static final long serialVersionUID = 5277954560736074086L;
	private String ibCode;
	private String brandCode;// utf8.general.ci NO PRI (NULL) select,insert,update,references
	private String username;// utf8.general.ci NO (NULL) select,insert,update,references
	private String password;// utf8.general.ci NO (NULL) select,insert,update,references
	private String firstName;// utf8.general.ci NO (NULL) select,insert,update,references
	private String lastName;// utf8.general.ci NO (NULL) select,insert,update,references
	private String chineseName;// utf8.general.ci NO (NULL) select,insert,update,references
	private String sex;// utf8.general.ci NO (NULL) select,insert,update,references
	private String mobile;// utf8.general.ci NO (NULL) select,insert,update,references
	private String country;// utf8.general.ci NO (NULL) select,insert,update,references
	private String email;// utf8.general.ci NO (NULL) select,insert,update,references
	private String address;// utf8.general.ci NO (NULL) select,insert,update,references
	private String language;// utf8.general.ci NO (NULL) select,insert,update,references
	private String status;// utf8.general.ci NO (NULL) select,insert,update,references
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;// (NULL) NO (NULL) select,insert,update,references
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateTime;// (NULL) NO (NULL) select,insert,update,references
	private String lastUpdateUser;// utf8.general.ci NO (NULL) select,insert,update,references
	
	private String rebateCode;
	
	private Boolean ignore_tree_rebate;

	public Boolean getIgnore_tree_rebate() {
		return ignore_tree_rebate;
	}

	public void setIgnore_tree_rebate(Boolean ignore_tree_rebate) {
		this.ignore_tree_rebate = ignore_tree_rebate;
	}

	public String getRebateCode() {
		return rebateCode;
	}

	public void setRebateCode(String rebateCode) {
		this.rebateCode = rebateCode;
	}

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
