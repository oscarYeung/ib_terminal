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

import com.henyep.ib.terminal.api.dto.request.ibcommission.details.GetCommissionDetailsRequest;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.details.GetCommissionDetailsResponseDto;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.server.controller.validator.IbCommissionDetailsValidator;
import com.henyep.ib.terminal.server.service.IbCommissionDetailsService;

@Controller
@RequestMapping("/ibCommissionDetails")
public class IbCommissionDetailsController extends AbstractController {

	@Resource(name = "IbCommissionDetailsService")
	IbCommissionDetailsService ibCommissionDetailsService;

	public IbCommissionDetailsController(IbCommissionDetailsValidator validator) {
		super(validator);

	}

	@RequestMapping(value = "/getDetails", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<GetCommissionDetailsResponseDto> getSummary(@RequestBody @Valid GetCommissionDetailsRequest request,
			BindingResult result) {

		logger.info("================= /ibCommissionDetails/getDetails Start =================");
		IbResponseDto<GetCommissionDetailsResponseDto> response = null;
		try {
			logger.info("request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());

			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				GetCommissionDetailsResponseDto body = ibCommissionDetailsService.GetIbCommissionDetails(request);
				if (body != null) {
					response.setBody(body);
				} else {
					hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
				}
			}
		} catch (Exception e) {
			Logger.getRootLogger().fatal(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}

		logger.info("response =" + g.toJson(response));
		logger.info("================= /ibCommissionDetails/getDetails End =================");
		return response;

	}

}
