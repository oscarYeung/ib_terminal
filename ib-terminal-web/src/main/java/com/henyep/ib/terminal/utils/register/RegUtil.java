package com.henyep.ib.terminal.utils.register;

import java.util.Date;

import com.henyep.ib.terminal.entity.Profiledto;
import com.henyep.ib.terminal.entity.RegisterDto;
import com.henyep.ib.terminal.api.dto.communal.ConstantDefineCode;
import com.henyep.ib.terminal.api.dto.request.ib.IbEditRequestDto;
import com.henyep.ib.terminal.api.dto.request.user.IbRegistReq;
import com.henyep.ib.terminal.api.dto.response.user.IbProfileGetResponseDto;

public class RegUtil {

	
	public IbRegistReq changeToRegRequest(RegisterDto dto){
		IbRegistReq req= new IbRegistReq();
		req.setBrandCode(dto.getBrandCode());
		req.setAddress(dto.getAddress());
		req.setChineseName(dto.getChineseName());
		req.setCountry(dto.getCountry());
		req.setCreateTime(new Date());
		req.setEmail(dto.getEmail());
		req.setFirstName(dto.getFirstName());
		req.setLanguage(ConstantDefineCode.SYS_LANGUAGE_ENGLISH);
		req.setLastName(dto.getLastName());
		req.setLastUpdateTime(dto.getCreateTime());
		req.setMobile(dto.getMobile());
		req.setPassword(dto.getPassword());
		req.setSex(dto.getSex());
		req.setStatus(ConstantDefineCode.IB_USER_STATUS_INITIAL);
		req.setLastUpdateUser(dto.getLastUpdateUser());
		req.setUsername(dto.getUsername());
		req.setParentIbCode(dto.getParentIbCode());
		return req;
	}
	public IbEditRequestDto changeToProfileRequest(Profiledto dto,IbProfileGetResponseDto profile){
		IbEditRequestDto req= new IbEditRequestDto();
		req.setIbCode(profile.getIbCode());
		req.setBrandCode(profile.getBrandCode());
		req.setAddress(dto.getAddress());
		req.setChineseName(dto.getChineseName());
		req.setCountry(dto.getCountry());
		req.setCreateTime(profile.getCreateTime().getTime());
		req.setEmail(dto.getEmail());
		req.setFirstName(dto.getFirstName());
		req.setLanguage(profile.getLanguage());
		req.setLastName(profile.getLastName());
		req.setLastUpdateTime(new Date().getTime());
		req.setMobile(dto.getMobile());
		req.setPassword(profile.getPassword());
		req.setSex(dto.getSex());
		req.setStatus(profile.getStatus());
		req.setLastUpdateUser(dto.getLastUpdateUser());
		req.setUsername(profile.getUsername());
		return req;
	}
	public IbEditRequestDto changeToProfileRequest(Profiledto dto){
		IbEditRequestDto req= new IbEditRequestDto();
		req.setBrandCode(dto.getBrandCode());
		req.setAddress(dto.getAddress());
		req.setChineseName(dto.getChineseName());
		req.setCountry(dto.getCountry());
		req.setCreateTime(new Date().getTime());
		req.setEmail(dto.getEmail());
		req.setFirstName(dto.getFirstName());
		req.setLanguage(ConstantDefineCode.SYS_LANGUAGE_ENGLISH);
		req.setLastName(dto.getLastName());
		req.setLastUpdateTime(dto.getCreateTime());
		req.setMobile(dto.getMobile());
		req.setPassword(dto.getPassword());
		req.setSex(dto.getSex());
		req.setStatus(ConstantDefineCode.IB_USER_STATUS_INITIAL);
		req.setLastUpdateUser(dto.getLastUpdateUser());
		//req.setUsername(dto.getUsername());
		return req;
	}
	
	public RegisterDto changeToRegisterDto(IbProfileGetResponseDto resq){
		RegisterDto dto = new RegisterDto();
		dto.setBrandCode(resq.getBrandCode());
		dto.setAddress(resq.getAddress());
		dto.setChineseName(resq.getChineseName());
		dto.setCountry(resq.getCountry());
		dto.setCreateTime(new Date());
		dto.setEmail(resq.getEmail());
		dto.setFirstName(resq.getFirstName());
		dto.setLanguage(resq.getLanguage());
		dto.setLastName(resq.getLastName());
		dto.setLastUpdateTime(resq.getCreateTime());
		dto.setMobile(resq.getMobile());
		//dto.setPassword(resq.getPassword());
		dto.setSex(resq.getSex());
		dto.setStatus(resq.getStatus());
		dto.setLastUpdateUser(resq.getLastUpdateUser());
		dto.setUsername(resq.getUsername());
		dto.setIbCode(resq.getIbCode());
		return dto;
	}
	
}
