package com.henyep.ib.terminal.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.henyep.ib.terminal.api.dto.db.UserBean;
import com.henyep.ib.terminal.api.dto.request.adminUser.AddUserRequest;
import com.henyep.ib.terminal.api.dto.request.adminUser.UpdateUserRequest;
import com.henyep.ib.terminal.api.dto.request.adminUser.UserChangePasswordRequest;
import com.henyep.ib.terminal.api.dto.request.adminUser.UserLoginRequest;
import com.henyep.ib.terminal.api.dto.response.adminUser.AddUserResponseDto;
import com.henyep.ib.terminal.api.dto.response.adminUser.UpdateUserResponseDto;
import com.henyep.ib.terminal.api.dto.response.adminUser.UserChangePasswordResponseDto;
import com.henyep.ib.terminal.api.dto.response.adminUser.UserLoginResponseDto;
import com.henyep.ib.terminal.server.dao.UserDao;
import com.henyep.ib.terminal.server.global.Constants;
import com.henyep.ib.terminal.server.service.UserService;
import com.henyep.ib.terminal.server.util.EncryptUtil;

@Service("UserService")
public class UserServiceImpl extends AbstractServiceImpl implements UserService {

	final static Logger log = Logger.getLogger(UserServiceImpl.class);
	private UserDao userDao;

	@Autowired
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public String validateUserPassword(UserLoginRequest request) throws Exception {

		String password = request.getBody().getPassword();
		String userCode = request.getBody().getUserCode();
		return validateUserPassword(userCode, password);

	}

	@Override
	public String validateUserPassword(String userCode, String password) throws Exception {
		String encodedPassword = EncryptUtil.encrypt(password);
		List<UserBean> rtnUserBeans = userDao.getUserByKey(userCode);
		String errorCode = "";

		UserBean targetUserBean = null;
		if (rtnUserBeans.size() >= 1) {
			targetUserBean = rtnUserBeans.get(0);

			if (!targetUserBean.getStatus().equals(Constants.USER_STATUS_ACTIVE)) {
				errorCode = Constants.ERR_USER_STATUS_INACTIVE;
			} else if (!targetUserBean.getPassword().equals(encodedPassword)) {
				errorCode = Constants.ERR_USER_INCORRECT_PASSWORD;
			}
		} else {
			errorCode = Constants.ERR_USER_NOT_EXIST;
		}

		return errorCode;
	}

	@Override
	public UserChangePasswordResponseDto changePassword(UserChangePasswordRequest request) throws Exception {

		UserChangePasswordResponseDto dto = new UserChangePasswordResponseDto();
		String userCode = request.getBody().getUserCode();
		String newPassword = request.getBody().getNewPassword();

		List<UserBean> rtnUsers = userDao.getUserByKey(userCode);
		if (rtnUsers.size() >= 0) {
			UserBean user = rtnUsers.get(0);
			String encryptedPassword = EncryptUtil.encrypt(newPassword);
			user.setPassword(encryptedPassword);
			String sender = this.getSender(request.getHeader());
			user.setLast_update_user(sender);

			int updateUserCount = userDao.updateUser(user);
			if (updateUserCount == 1) {
				List<UserBean> updatedUsers = userDao.getUserByKey(userCode);

				dto.setUserBean(updatedUsers.get(0));
				return dto;

			}
		}

		return dto;
	}

	@Override
	public AddUserResponseDto addUser(AddUserRequest request) throws Exception {

		UserBean newUserBean = request.getBody().getNewUser();
		AddUserResponseDto dto = new AddUserResponseDto();
		String password = newUserBean.getPassword();
		String encryptedPassword = EncryptUtil.encrypt(password);
		newUserBean.setPassword(encryptedPassword);

		userDao.saveUser(newUserBean);

		List<UserBean> userBeans = userDao.getUserByKey(newUserBean.getUser_code());
		if (userBeans.size() > 0) {
			dto.setAddedUserBean(userBeans.get(0));
		}
		return dto;
	}

	@Override
	public UpdateUserResponseDto updateUser(UpdateUserRequest request) throws Exception {

		UpdateUserResponseDto dto = new UpdateUserResponseDto();
		UserBean updateUser = request.getBody().getUpdateUserBean();
		String password = updateUser.getPassword();
		String encryptedPassword = EncryptUtil.encrypt(password);
		updateUser.setPassword(encryptedPassword);
		String sender = this.getSender(request.getHeader());
		updateUser.setLast_update_user(sender);
		int updatedUserCount = userDao.updateUser(updateUser);
		

		if (updatedUserCount >= 1) {
			List<UserBean> userBeans = userDao.getUserByKey(request.getBody().getUpdateUserBean().getUser_code());
			if (userBeans.size() >= 1) {
				dto.setUserBean(userBeans.get(0));
			}
		}

		return dto;
	}

	@Override
	public UserLoginResponseDto loginUser(UserLoginRequest request) throws Exception {

		UserLoginResponseDto dto = new UserLoginResponseDto();
		String userCode = request.getBody().getUserCode();

		List<UserBean> userBeans = userDao.getUserByKey(userCode);
		if (userBeans.size() >= 1) {
			dto.setUserBean(userBeans.get(0));
		}

		return dto;
	}

	@Override
	public List<String> validateAddUser(AddUserRequest request) throws Exception {

		List<String> errorCodes = new ArrayList<String>();
		String newUserCode = request.getBody().getNewUser().getUser_code();
		List<UserBean> userBeans = userDao.getUserByKey(newUserCode);
		if (userBeans.size() > 0) {
			errorCodes.add(Constants.ERR_USER_ALREADY_EXIST);
		}

		String newUserStatus = request.getBody().getNewUser().getStatus();
		if (!newUserStatus.equals(Constants.USER_STATUS_ACTIVE) && !newUserStatus.equals(Constants.USER_STATUS_INACTIVE)) {
			errorCodes.add(Constants.ERR_USER_STATUS_INCORRECT);
		}

		return errorCodes;
	}

	public List<String> validateUpdateUser(UpdateUserRequest request) throws Exception {

		List<String> errorCodes = new ArrayList<String>();
		String updateUserCode = request.getBody().getUpdateUserBean().getUser_code();
		List<UserBean> userBeans = userDao.getUserByKey(updateUserCode);
		if (userBeans.size() == 0) {
			errorCodes.add(Constants.ERR_USER_NOT_EXIST);
		}

		String updateUserStatus = request.getBody().getUpdateUserBean().getStatus();
		if (!updateUserStatus.equals(Constants.USER_STATUS_ACTIVE) && !updateUserStatus.equals(Constants.USER_STATUS_INACTIVE)) {
			errorCodes.add(Constants.ERR_USER_STATUS_INCORRECT);
		}

		return errorCodes;
	}

}
