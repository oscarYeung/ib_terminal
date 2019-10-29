package com.henyep.ib.terminal.server.mail;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;


/**
 * 项目名称：IBTerminalServer
 * <p>
 * 功能模块名称：
 * <p>
 * 文件名称为：MailContextDto.java
 * <p>
 * 文件功能简述：
 * <p>
 * 文件创建人：Administrator——Jm.zhang
 * @version v1.0
 * @time  2016-9-9 下午5:26:29
 * @copyright Hengyp
 */
public class MailContextDto {
	MailServerConfig mailServerCfg;
	
	public MailContextDto(String subject,List<String> tl,List<String> bcl,String content){
		this.setSubject(subject);
		this.setToAddressList(tl);
		this.setBccAddressList(bcl);
		this.setContent(content);
	}
	public MailContextDto(String subject,List<String> tl,String content){
		this.setSubject(subject);
		this.setToAddressList(tl);
		this.setContent(content);
	}
	private String subject;
	private List<String> toAddressList;
	private List<String> bccAddressList;
	private String content;
	private Integer priority_leve = 0;
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getToAddressList() {
		if(this.toAddressList!=null && this.toAddressList.size()>0){
			StringBuffer touserlist = new StringBuffer();
			for(int i=0;i<toAddressList.size();i++){
				touserlist.append(toAddressList.get(i));
				touserlist.append(mailServerCfg.getMail_address_splitor());
			}
			return touserlist.subSequence(0, touserlist.length()-1).toString();
		}else{
			return null;
		}
		
	}
	public void setToAddressList(List<String> toAddressList) {
		this.toAddressList = toAddressList;
	}
	public String getBccAddressList() {
		if(this.bccAddressList!=null && this.bccAddressList.size()>0){
			StringBuffer bccUserList = new StringBuffer();
			for(int i=0;i<this.bccAddressList.size();i++){
				bccUserList.append(this.bccAddressList.get(i));
				bccUserList.append(mailServerCfg.getMail_address_splitor());
			}
			return bccUserList.subSequence(0, bccUserList.length()-1).toString();
		}else {
			return "";
		}
		
	}
	public void setBccAddressList(List<String> bccAddressList) {
		this.bccAddressList = bccAddressList;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public MailServerConfig getMailServerCfg() {
		return mailServerCfg;
	}
	public void setMailServerCfg(MailServerConfig mailServerCfg) {
		this.mailServerCfg = mailServerCfg;
	}
	public Integer getPriority_leve() {
		return priority_leve;
	}
	public void setPriority_leve(Integer priority_leve) {
		this.priority_leve = priority_leve;
	}
}
