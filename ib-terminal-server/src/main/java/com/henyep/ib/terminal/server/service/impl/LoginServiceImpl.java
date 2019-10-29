package com.henyep.ib.terminal.server.service.impl;

import java.lang.reflect.Type;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.google.gson.reflect.TypeToken;
import com.henyep.ib.terminal.api.dto.db.IbProfileBean;
import com.henyep.ib.terminal.api.dto.request.ib.IbLoginRequest;
import com.henyep.ib.terminal.api.dto.response.ib.login.IbLoginResponseDto;
import com.henyep.ib.terminal.server.dao.IbProfileDao;
import com.henyep.ib.terminal.server.dto.connection.ConnectionInfoDto;
import com.henyep.ib.terminal.server.service.LoginService;
import com.henyep.ib.terminal.server.util.HttpClientUtil;
import com.henyep.white.label.api.dto.request.user.LoginRequest;
import com.henyep.white.label.api.dto.request.user.UserRequestDto;
import com.henyep.white.label.api.dto.response.BaseResponseDto;
import com.henyep.white.label.api.dto.response.user.UserResponseDto;

@Service(value = "LoginService")
public class LoginServiceImpl implements LoginService {
	protected final transient Log logger = LogFactory.getLog(getClass());

	@Resource(name = "IbProfileDao")
	IbProfileDao ibProfileDao;

	@Resource(name = "HttpClientUtil")
	HttpClientUtil httpClientUtil;

	@Resource(name = "whiteLabel_connectionInfo")
	ConnectionInfoDto whiteLabelConnectionInfo;

	@Override
	public IbLoginResponseDto validateIbLogin(IbLoginRequest request) {
		IbLoginResponseDto response = null;
		try {

			IbProfileBean ibProfile = this.ibProfileDao.getProfileByIbCodeAndPassword(request.getBody().getIbLoginName(),
					request.getBody().getIbLoginPassWord());
			if (ibProfile != null) {
				response = new IbLoginResponseDto();
				response.setBrandCode(ibProfile.getBrand_code());
				response.setIbCode(ibProfile.getIb_code());
				response.setIbLoginName(ibProfile.getUsername());
				response.setIbStatus(ibProfile.getStatus());
				response.setIs_white_label_user(ibProfile.getIs_white_label_user());

				if (ibProfile.getIs_white_label_user()) {
					try {
						// is white label user
						logger.info("Is white label user");
						LoginRequest wl_LoginRequest = new LoginRequest();
						UserRequestDto dto = new UserRequestDto();
						dto.setUser_code(request.getBody().getIbLoginName());
						dto.setPassword(request.getBody().getIbLoginPassWord());
						wl_LoginRequest.setBody(dto);
						whiteLabelConnectionInfo.setPath("/user/login");
						String wl_LoginResponseString = httpClientUtil.sendRestfulPostRequest(whiteLabelConnectionInfo.getEndPoint(),
								wl_LoginRequest);
						Type type = new TypeToken<BaseResponseDto<UserResponseDto>>() {
						}.getType();
						BaseResponseDto<UserResponseDto> wl_LoginResponse = httpClientUtil.convertToResponseObj(wl_LoginResponseString, type);

						if (wl_LoginResponse != null) {
							response.setWl_company_code(wl_LoginResponse.getBody().getUser().getCompany_code());
							response.setWl_platform(wl_LoginResponse.getBody().getPlatform());
							response.setWl_registration_type(wl_LoginResponse.getBody().getRegistration_type());
							response.setWl_server_code(wl_LoginResponse.getBody().getServer_code());
						}

						logger.info(wl_LoginResponse.getBody().getServer_code());
					} catch (Exception e) {
						logger.error(e, e);
						response.setIs_white_label_user(false);
					}
				} else {
					logger.info("NOT white label user");
				}

			}

		} catch (Exception e) {
			logger.error(e, e);
			response = null;
		}

		return response;
	}

}
