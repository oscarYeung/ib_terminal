package com.henyep.ib.terminal.server.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.henyep.ib.terminal.api.dto.request.user.IbProfileGetByIbCodeRequest;
import com.henyep.ib.terminal.api.dto.request.user.IbProfileGetRequest;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.user.IbProfileGetResponseDto;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.server.component.HySpringUtil;
import com.henyep.ib.terminal.server.factory.DtoFactory;
import com.henyep.ib.terminal.server.service.ProfileService;
import com.henyep.ib.terminal.server.service.RegistService;

/**
 * ProfileController
 *
 * @author ShenZhong
 * @date 2016年8月17日
 */
@Controller
public class ProfileController {
	final static Logger log = Logger.getLogger(ProfileController.class);
	Gson g = new Gson();
	@Resource(name = "RegistService")
	RegistService registService;
	@Resource(name = "ProfileService")
	ProfileService profileService;
	@Resource(name = "sz_SimpleDtoFactory")
	DtoFactory dtoFactory;
	@Resource(name = "sz_HySpringUtil")
	HySpringUtil hySpringUtil;

	@RequestMapping(value = "/ibuser/getprofile", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<IbProfileGetResponseDto> get(@RequestBody @Valid IbProfileGetRequest request, BindingResult result) {
		log.info("get profile request =" + g.toJson(request));
		IbResponseDto<IbProfileGetResponseDto> response = null;
		try{
			response = dtoFactory.createWebResponse(request.getHeader());

			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				IbProfileGetResponseDto body = profileService.getProfile(request);
				if (body != null) {
					response.setBody(body);
				} else {
					hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
				}
			}
			log.info("get profile response =" + g.toJson(response));
		}catch(Exception e){
			log.error(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		
		
		return response;
	}

	@RequestMapping(value = "/ibuser/getProfileByIbCode", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<IbProfileGetResponseDto> getProfileByIbCode(@RequestBody @Valid IbProfileGetByIbCodeRequest request, BindingResult result) {
		log.info("get profile by ib code request =" + g.toJson(request));
		IbResponseDto<IbProfileGetResponseDto> response = dtoFactory.createResp();
		if (result.hasErrors()) {
			response = hySpringUtil.setDtoErrorFromResult(response, result);
		} else {
			String ibCode = request.getBody().getIb_code();
			IbProfileGetResponseDto body = profileService.getProfileByIbCode(ibCode);
			if (body != null) {
				response.setBody(body);
			} else {
				hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
			}

		}
		log.info("get profile by ib code response =" + g.toJson(response));
		return response;
	}
	
	
}
