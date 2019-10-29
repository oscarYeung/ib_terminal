package com.henyep.ib.terminal.server.dao;
import java.util.List;
import java.util.Date;
import com.henyep.ib.terminal.api.dto.db.UserBean;

public interface UserDao{
	public void saveUser(final UserBean user) throws Exception;

	public List<UserBean> getAllUsers() throws Exception;

	public List<UserBean> getUserByKey(String user_code) throws Exception;

	public int updateUser(UserBean user) throws Exception;

	public int deleteUser(String user_code) throws Exception;
	
	public List<UserBean> getUserByUserPassword(String user_code, String password) throws Exception;
}
