package com.henyep.ib.terminal.server.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 项目名称：IBTerminalServer
 * <p>
 * 功能模块名称：
 * <p>
 * 文件名称为：PwdAuthentificator.java
 * <p>
 * 文件功能简述：
 * <p>
 * 文件创建人：Administrator——Jm.zhang
 * @version v1.0
 * @time  2016-9-16 下午2:34:10
 * @copyright Hengyp
 */
public class PwdAuthentificator extends Authenticator{

	private PasswordAuthentication pwdAuth;
	
	PwdAuthentificator(String username,String password){
		pwdAuth = new PasswordAuthentication(username, password);
	}
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		// TODO Auto-generated method stub
		return pwdAuth;
	}

}
