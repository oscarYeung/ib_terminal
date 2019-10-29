package com.henyep.ib.terminal.server.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.henyep.ib.terminal.api.dto.request.ibcommission.clientSummary.GetByIbCodeRequest;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.clientSummary.GetByIbCodeResponseDto;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.server.controller.validator.IbCommissionClientSummaryValidator;
import com.henyep.ib.terminal.server.service.IbCommissionClientSummaryService;

@Controller
@RequestMapping("/ibCommissionClientSummary")
public class IbCommissionClientSummaryController extends AbstractController {

	public IbCommissionClientSummaryController(IbCommissionClientSummaryValidator validator) {
		super(validator);
	}

	@Resource(name = "IbCommissionClientSummaryService")
	private IbCommissionClientSummaryService ibCommissionClientSummaryService;

	@RequestMapping(value = "/getByIbCode", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<GetByIbCodeResponseDto> getByIbCode(@RequestBody @Valid GetByIbCodeRequest request, BindingResult result) {

		logger.info("================= /ibCommissionClientSummary/getByIbCode Start =================");
		logger.info("request=" + g.toJson(request));
		IbResponseDto<GetByIbCodeResponseDto> response = null;
		try {
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				GetByIbCodeResponseDto body = ibCommissionClientSummaryService.getIbCommissionClientSummaryByDate(request);
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
		logger.info("================= /ibCommissionClientSummary/getByIbCode Start =================");
		return response;

	}

}
