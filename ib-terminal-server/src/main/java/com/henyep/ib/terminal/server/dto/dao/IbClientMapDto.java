package com.henyep.ib.terminal.server.dto.dao;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.henyep.ib.terminal.api.dto.db.IbClientMapBean;

public class IbClientMapDto {

	private String ib_code;
	private String client_code;
	private String client_package_code;
	private Date start_date;	
	private Date last_update_time;

	public Date getLast_update_time() {
		return last_update_time;
	}

	public void setLast_update_time(Date last_update_time) {
		this.last_update_time = last_update_time;
	}

	public String getIb_code() {
		return ib_code;
	}

	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}

	public String getClient_code() {
		return client_code;
	}

	public void setClient_code(String client_code) {
		this.client_code = client_code;
	}

	public String getClient_package_code() {
		return client_package_code;
	}

	public void setClient_package_code(String client_package_code) {
		this.client_package_code = client_package_code;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	
	public IbClientMapBean convertToIbClientMapBean() {
		IbClientMapBean bean = new IbClientMapBean();
		bean.setClient_code(client_code);
		bean.setIb_code(ib_code);
		bean.setStart_date(start_date);		

		Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(client_package_code);
		StringBuilder sb = new StringBuilder();
		while (m.find()) {
			String clientPackageCode = m.group(1).toUpperCase();
			if(!clientPackageCode.equals("AF")){
				
				if(clientPackageCode.equals("V01")){
					clientPackageCode = "W01";
				}
				sb.append(clientPackageCode);
			}
		}
		
		
		bean.setClient_package_code(sb.toString());
		bean.setEnd_date(null);
		bean.setLast_update_time(last_update_time);
		bean.setLast_update_user("system");

		return bean;
	}
}
