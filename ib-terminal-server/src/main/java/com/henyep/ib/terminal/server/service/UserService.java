package com.henyep.ib.terminal.server.service;

import java.util.List;

import com.henyep.ib.terminal.api.dto.request.adminUser.AddUserRequest;
import com.henyep.ib.terminal.api.dto.request.adminUser.UpdateUserRequest;
import com.henyep.ib.terminal.api.dto.request.adminUser.UserChangePasswordRequest;
import com.henyep.ib.terminal.api.dto.request.adminUser.UserLoginRequest;
import com.henyep.ib.terminal.api.dto.response.adminUser.AddUserResponseDto;
import com.henyep.ib.terminal.api.dto.response.adminUser.UpdateUserResponseDto;
import com.henyep.ib.terminal.api.dto.response.adminUser.UserChangePasswordResponseDto;
import com.henyep.ib.terminal.api.dto.response.adminUser.UserLoginResponseDto;

public interface UserService {

	public String validateUserPassword(UserLoginRequest request) throws Exception;
	public String validateUserPassword(String userCode, String password) throws Exception;
	
	public UserLoginResponseDto loginUser(UserLoginRequest request) throws Exception;
	
	public UserChangePasswordResponseDto changePassword(UserChangePasswordRequest request) throws Exception;
	
	public AddUserResponseDto addUser(AddUserRequest request) throws Exception;
	public List<String> validateAddUser(AddUserRequest request) throws Exception;
	
	public UpdateUserResponseDto updateUser(UpdateUserRequest request) throws Exception;
	public List<String> validateUpdateUser(UpdateUserRequest request) throws Exception;
	
}
