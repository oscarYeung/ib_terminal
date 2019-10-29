package com.henyep.ib.terminal.server.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.henyep.ib.terminal.api.dto.request.ibcommission.summary.GetByTeamHeadRequest;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.summary.GetByTeamHeadResponseDto;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.server.controller.validator.IbCommissionSummaryValidator;
import com.henyep.ib.terminal.server.service.IbCommissionSummaryService;

@Controller
@RequestMapping("/ibCommissionSummary")
public class IbCommissionSummaryController extends AbstractController {

	@Resource(name = "IbCommissionSummaryService")
	IbCommissionSummaryService ibCommissionSummaryService;

	public IbCommissionSummaryController(IbCommissionSummaryValidator validator) {
		super(validator);

	}	

	@RequestMapping(value = "/getByTeamHead", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<GetByTeamHeadResponseDto> getByTeamHead(@RequestBody @Valid GetByTeamHeadRequest request,
			BindingResult result) {

		logger.info("================= /ibCommissionSummary/getByTeamHead Start =================");
		IbResponseDto<GetByTeamHeadResponseDto> response = null;
		try {
			logger.info("request=" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());

			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				GetByTeamHeadResponseDto body = ibCommissionSummaryService.GetIbCommissionSummary(request);
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

		logger.info("response =" + g.toJson(response));
		logger.info("================= /ibCommissionSummary/getByTeamHead End =================");
		return response;

	}

}
