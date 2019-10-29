package com.henyep.ib.terminal.api.dto.db;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class IbClientLinkBean {


	private String brand_code;
	private String ib_code;
	private String client_package_code;
	private String campaign_id;
	private String url;
	private String description;
	private Integer seq_id;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=Constants.FORMAT_DATETIME)
	private Date last_update_time;
	private String last_update_user;

	public void setBrand_code(String brand_code){
		this.brand_code = brand_code;
	}
	public String getBrand_code(){
		return brand_code;
	}
	public void setIb_code(String ib_code){
		this.ib_code = ib_code;
	}
	public String getIb_code(){
		return ib_code;
	}
	public void setClient_package_code(String client_package_code){
		this.client_package_code = client_package_code;
	}
	public String getClient_package_code(){
		return client_package_code;
	}
	public void setCampaign_id(String campaign_id){
		this.campaign_id = campaign_id;
	}
	public String getCampaign_id(){
		return campaign_id;
	}
	public void setUrl(String url){
		this.url = url;
	}
	public String getUrl(){
		return url;
	}
	public void setDescription(String description){
		this.description = description;
	}
	public String getDescription(){
		return description;
	}
	public void setSeq_id(Integer seq_id){
		this.seq_id = seq_id;
	}
	public Integer getSeq_id(){
		return seq_id;
	}
	public void setLast_update_time(Date last_update_time){
		this.last_update_time = last_update_time;
	}
	public Date getLast_update_time(){
		return last_update_time;
	}
	public void setLast_update_user(String last_update_user){
		this.last_update_user = last_update_user;
	}
	public String getLast_update_user(){
		return last_update_user;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		if(brand_code != null)
			builder.append("brand_code: " + brand_code + ", ");
		if(ib_code != null)
			builder.append("ib_code: " + ib_code + ", ");
		if(client_package_code != null)
			builder.append("client_package_code: " + client_package_code + ", ");
		if(campaign_id != null)
			builder.append("campaign_id: " + campaign_id + ", ");
		if(url != null)
			builder.append("url: " + url + ", ");
		if(description != null)
			builder.append("description: " + description + ", ");
		builder.append("seq_id: " + seq_id + ", ");
		if(last_update_time != null)
			builder.append("last_update_time: " + last_update_time.toString() + ", ");
		if(last_update_user != null)
			builder.append("last_update_user: " + last_update_user + ", ");
		return builder.toString();
	}
}
