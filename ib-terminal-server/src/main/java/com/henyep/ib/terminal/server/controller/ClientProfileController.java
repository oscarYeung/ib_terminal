package com.henyep.ib.terminal.server.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.henyep.ib.terminal.api.dto.request.clientProfile.GetClientProfileRequest;
import com.henyep.ib.terminal.api.dto.request.clientProfile.SearchMyClientsRequest;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.clientProfile.GetClientProfileResponseDto;
import com.henyep.ib.terminal.api.dto.response.clientProfile.SearchMyClientsResponseDto;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.server.controller.validator.ClientProfileValidator;
import com.henyep.ib.terminal.server.service.ClientProfileService;

@Controller
public class ClientProfileController extends AbstractController {

	@Autowired
	public ClientProfileController(ClientProfileValidator validator) {
		super(validator);
	}

	@Resource(name = "ClientProfileService")
	ClientProfileService clientProfileService;

	@RequestMapping(value = "/clientProfile/getClientProfile", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<GetClientProfileResponseDto> getClientProfile(@RequestBody @Valid GetClientProfileRequest request,
			BindingResult result) {
		logger.info("================= /clientProfile/getClientProfile Start =================");
		IbResponseDto<GetClientProfileResponseDto> response = null;
		try {
			logger.info("get client profile request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {

				List<String> errList = clientProfileService.validateGetClientProfile(request);
				if (errList.size() == 0) {
					GetClientProfileResponseDto body = clientProfileService.getClientProfile(request);
					if (body != null) {
						response.setBody(body);
					} else {
						hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
					}
				} else {
					for (String err : errList) {
						hySpringUtil.setDtoErrorFromErrorCode(response, err);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}

		logger.info("get client profile response =" + g.toJson(response));
		logger.info("================= /clientProfile/getClientProfile End =================");
		return response;

	}

	@RequestMapping(value = "/clientProfile/searchMyClients", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<SearchMyClientsResponseDto> searchMyClients(@RequestBody @Valid SearchMyClientsRequest request,
			BindingResult result) {
		logger.info("================= /clientProfile/searchMyClients Start =================");

		IbResponseDto<SearchMyClientsResponseDto> response = null;
		try {
			logger.info("search my clients request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				SearchMyClientsResponseDto body = clientProfileService.searchMyClients(request);
				if (body != null) {
					response.setBody(body);
				} else {
					hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}

		logger.info("search my clients response =" + g.toJson(response));
		logger.info("================= /clientProfile/searchMyClients End =================");
		return response;

	}
}
