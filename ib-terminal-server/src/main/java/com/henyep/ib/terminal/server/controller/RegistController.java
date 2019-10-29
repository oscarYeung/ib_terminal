package com.henyep.ib.terminal.server.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
//import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.henyep.ib.terminal.api.dto.request.IbRequestDto;
import com.henyep.ib.terminal.api.dto.request.ib.IbCreateRequestDto;
import com.henyep.ib.terminal.api.dto.request.ib.IbUpdateRequestDto;
import com.henyep.ib.terminal.api.dto.request.user.IbRegistReq;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.user.IbCreateRespDto;
import com.henyep.ib.terminal.api.dto.response.user.IbRegistResp;
import com.henyep.ib.terminal.api.dto.response.user.IbUpdateRespDto;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.server.component.HySpringUtil;
import com.henyep.ib.terminal.server.factory.DtoFactory;
import com.henyep.ib.terminal.server.service.RegistService;

/**
 * RegistController
 *
 * @author ShenZhong
 * @date 2016年8月17日
 */
@Controller
public class RegistController
{
	final static Logger log = Logger.getLogger(RegistController.class);
	Gson g = new Gson();
	@Resource(name = "RegistService")
	RegistService registService;
	@Resource(name = "sz_SimpleDtoFactory")
	DtoFactory dtoFactory;
	@Resource(name = "sz_HySpringUtil")
	HySpringUtil hySpringUtil;
//	@Resource(name = "sz_RegistValidator")
//	RegistValidator validator;

//	@InitBinder()
//	public void initBinder(DataBinder binder)
//	{
//		binder.addValidators(validator);
//	}

	@RequestMapping(value = "/ibuser/regist", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<IbRegistResp> regist(@RequestBody @Valid IbRequestDto<IbRegistReq> regReq, BindingResult result)
	{
		log.info("req=" + g.toJson(regReq));
		IbResponseDto<IbRegistResp> regResp;
		if (result.hasErrors())
		{
			regResp = dtoFactory.createResp();
			regResp = hySpringUtil.setDtoErrorFromResult(regResp, result);
		}
		else
		{
			regResp = registService.regist(regReq);
		}
		log.info("resp=" + g.toJson(regResp));
		return regResp;
	}
	
	@RequestMapping(value = "/ibuser/update", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<IbUpdateRespDto> updateIb(@RequestBody @Valid IbRequestDto<IbUpdateRequestDto> req, BindingResult result)
	{
		log.info("================= /ibuser/update Start =================");
		log.info("update ib profile req=" + g.toJson(req));
		IbResponseDto<IbUpdateRespDto> resp = dtoFactory.createResp();

		try{
			if (result.hasErrors())
			{
				resp = hySpringUtil.setDtoErrorFromResult(resp, result);
			}
			else
			{
				List<String> errors = registService.validateUpdateIbProfile(req);
				if(errors.size() == 0){
					IbUpdateRespDto body = registService.updateIbProfile(req);
					if (body != null) {
						resp.setBody(body);
					} else {
						hySpringUtil.setDtoErrorFromErrorCode(resp, Constants.ERR_COMMON_INTERNAL_ERROR);
					}

				}	
				else{
					for(String errorCode : errors){
						hySpringUtil.setDtoErrorFromErrorCode(resp, errorCode);
					}
				}
			}
		}catch (Exception e) {
			log.error(e,e);
			String defaultMsg = Constants.ERR_COMMON_INTERNAL_ERROR;
			hySpringUtil.setDtoErrorFromErrorCode(resp, defaultMsg);
		}
		
		log.info("update ib profile resp=" + g.toJson(resp));
		log.info("================= /ibuser/update Start =================");
		return resp;
	}

	@RequestMapping(value = "/ibuser/create", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<IbCreateRespDto> createIb(@RequestBody @Valid IbRequestDto<IbCreateRequestDto> req, BindingResult result)
	{
		log.info("create ib profile req=" + g.toJson(req));
		IbResponseDto<IbCreateRespDto> resp = dtoFactory.createResp();

		try{
			if (result.hasErrors())
			{
				resp = hySpringUtil.setDtoErrorFromResult(resp, result);
			}
			else
			{
				List<String> errors = registService.validateCreateIbProfile(req);
				if(errors.size() == 0){
					IbCreateRespDto body = registService.createIbProfile(req);
					if (body != null) {
						resp.setBody(body);
					} else {
						hySpringUtil.setDtoErrorFromErrorCode(resp, Constants.ERR_COMMON_INTERNAL_ERROR);
					}
				}	
				else{
					for(String errorCode : errors){
						hySpringUtil.setDtoErrorFromErrorCode(resp, errorCode);
					}
				}
			}
		}catch (Exception e) {
			log.error(e,e);
			String defaultMsg = Constants.ERR_COMMON_INTERNAL_ERROR;
			hySpringUtil.setDtoErrorFromErrorCode(resp, defaultMsg);
		}
		
		log.info("create ib profile resp=" + g.toJson(resp));
		return resp;
	}
}
