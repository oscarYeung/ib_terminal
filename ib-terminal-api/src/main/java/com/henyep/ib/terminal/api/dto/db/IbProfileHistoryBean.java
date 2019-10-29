package com.henyep.ib.terminal.api.dto.db;
import java.util.Date;

public class IbProfileHistoryBean{

	private String brand_code;
	private Integer ib_code;
	private String username;
	private String password;
	private String first_name;
	private String last_name;
	private String chinese_name;
	private String sex;
	private String mobile;
	private String country;
	private String email;
	private String address;
	private String language;
	private String status;
	private Date create_time;
	private String last_update_user;
	private Integer version;

	public void setBrand_code(String brand_code){
		this.brand_code = brand_code;
	}
	public String getBrand_code(){
		return brand_code;
	}
	public void setIb_code(Integer ib_code){
		this.ib_code = ib_code;
	}
	public Integer getIb_code(){
		return ib_code;
	}
	public void setUsername(String username){
		this.username = username;
	}
	public String getUsername(){
		return username;
	}
	public void setPassword(String password){
		this.password = password;
	}
	public String getPassword(){
		return password;
	}
	public void setFirst_name(String first_name){
		this.first_name = first_name;
	}
	public String getFirst_name(){
		return first_name;
	}
	public void setLast_name(String last_name){
		this.last_name = last_name;
	}
	public String getLast_name(){
		return last_name;
	}
	public void setChinese_name(String chinese_name){
		this.chinese_name = chinese_name;
	}
	public String getChinese_name(){
		return chinese_name;
	}
	public void setSex(String sex){
		this.sex = sex;
	}
	public String getSex(){
		return sex;
	}
	public void setMobile(String mobile){
		this.mobile = mobile;
	}
	public String getMobile(){
		return mobile;
	}
	public void setCountry(String country){
		this.country = country;
	}
	public String getCountry(){
		return country;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public String getEmail(){
		return email;
	}
	public void setAddress(String address){
		this.address = address;
	}
	public String getAddress(){
		return address;
	}
	public void setLanguage(String language){
		this.language = language;
	}
	public String getLanguage(){
		return language;
	}
	public void setStatus(String status){
		this.status = status;
	}
	public String getStatus(){
		return status;
	}
	public void setCreate_time(Date create_time){
		this.create_time = create_time;
	}
	public Date getCreate_time(){
		return create_time;
	}
	public void setLast_update_user(String last_update_user){
		this.last_update_user = last_update_user;
	}
	public String getLast_update_user(){
		return last_update_user;
	}
	public void setVersion(Integer version){
		this.version = version;
	}
	public Integer getVersion(){
		return version;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		if(brand_code != null)
			builder.append("brand_code: " + brand_code + ", ");
		builder.append("ib_code: " + ib_code + ", ");
		if(username != null)
			builder.append("username: " + username + ", ");
		if(password != null)
			builder.append("password: " + password + ", ");
		if(first_name != null)
			builder.append("first_name: " + first_name + ", ");
		if(last_name != null)
			builder.append("last_name: " + last_name + ", ");
		if(chinese_name != null)
			builder.append("chinese_name: " + chinese_name + ", ");
		if(sex != null)
			builder.append("sex: " + sex + ", ");
		if(mobile != null)
			builder.append("mobile: " + mobile + ", ");
		if(country != null)
			builder.append("country: " + country + ", ");
		if(email != null)
			builder.append("email: " + email + ", ");
		if(address != null)
			builder.append("address: " + address + ", ");
		if(language != null)
			builder.append("language: " + language + ", ");
		if(status != null)
			builder.append("status: " + status + ", ");
		if(create_time != null)
			builder.append("create_time: " + create_time.toString() + ", ");
		if(last_update_user != null)
			builder.append("last_update_user: " + last_update_user + ", ");
		builder.append("version: " + version + ", ");
		return builder.toString();
	}
}
