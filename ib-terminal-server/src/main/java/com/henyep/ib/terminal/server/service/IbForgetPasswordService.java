package com.henyep.ib.terminal.server.service;

import com.henyep.ib.terminal.api.dto.request.IbRequestDto;
import com.henyep.ib.terminal.api.dto.request.user.IbForgetPwdDto;
import com.henyep.ib.terminal.api.dto.request.user.IbResetPwdReqDto;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.user.IbForgetPasswordResp;
import com.henyep.ib.terminal.api.dto.response.user.IbResetPasswordResp;

/**
 * 项目名称：IBTerminalServer
 * <p>
 * 功能模块名称：
 * <p>
 * 文件名称为：IbForgetPasswordService.java
 * <p>
 * 文件功能简述：
 * <p>
 * 文件创建人：Administrator——Jm.zhang
 * @version v1.0
 * @time  2016-9-6 下午4:01:02
 * @copyright Hengyp
 */
public interface IbForgetPasswordService {
	IbResponseDto<IbForgetPasswordResp> forgetPwdResp(IbRequestDto<IbForgetPwdDto> forgetpwdDto);
	IbResponseDto<IbResetPasswordResp> resetPwdResp(IbRequestDto<IbResetPwdReqDto> resetPwdDto);

}
