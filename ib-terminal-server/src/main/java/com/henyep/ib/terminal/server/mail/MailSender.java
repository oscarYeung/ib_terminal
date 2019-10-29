package com.henyep.ib.terminal.server.mail;

/**
 * 项目名称：IBTerminalServer
 * <p>
 * 功能模块名称：
 * <p>
 * 文件名称为：MailSender.java
 * <p>
 * 文件功能简述：
 * <p>
 * 文件创建人：Administrator——Jm.zhang
 * @version v1.0
 * @time  2016-9-16 下午4:20:12
 * @copyright Hengyp
 */
public class MailSender extends Thread{

	private MailContextDto mailDto;

	public MailSender(MailContextDto dto) {
		this.mailDto=dto;
	}
	@Override
	public void run() {
		SmtpClient smtpClient = new SmtpClient(this.mailDto);
		try {
			smtpClient.sendMail();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
