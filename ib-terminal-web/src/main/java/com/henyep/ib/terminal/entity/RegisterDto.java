package com.henyep.ib.terminal.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class RegisterDto implements Serializable{

	private static final long serialVersionUID = 4761449657461249798L;
	private String brandCode;
	@NotEmpty(message = "{reg.username.not.blank}")
	private String username;
	@NotEmpty(message = "{reg.password.not.blank}")
	private String password;
	@NotEmpty(message = "{reg.firstname.not.blank}")
	private String firstName;
	@NotEmpty(message = "{reg.lastname.not.blank}")
	private String lastName;
	private String chineseName;
	@NotNull(message = "{reg.sex.not.blank}")
	private String sex;
	@NotEmpty(message = "{reg.mobile.not.blank}")
	private String mobile;
	@NotEmpty(message = "{reg.country.not.blank}")
	private String country;
	@NotEmpty(message = "{reg.email.not.blank}")
	private String email;
	@NotEmpty(message = "{reg.address.not.blank}")
	private String address;
	private String language;
	private String status;
	private Date createTime;
	private String ibCode;
	public String getIbCode() {
		return ibCode;
	}

	public void setIbCode(String ibCode) {
		this.ibCode = ibCode;
	}

	private Date lastUpdateTime;
	private String lastUpdateUser;
	
	private String rePassword;
	private String parentIbCode;
	
	public String getParentIbCode() {
		return parentIbCode;
	}

	public void setParentIbCode(String parentIbCode) {
		this.parentIbCode = parentIbCode;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
