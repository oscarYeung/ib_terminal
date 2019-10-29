package com.henyep.ib.terminal.server.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.henyep.ib.terminal.api.dto.request.client.balance.summary.UpdateAccountBalanceRequest;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.client.balance.summary.UpdateAccountBalanceResponseDto;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.server.controller.validator.ClientBalanceSummaryValidator;
import com.henyep.ib.terminal.server.service.ClientBalanceSummaryService;

@Controller
public class ClientBalanceSummaryController extends AbstractController {

	@Autowired
	public ClientBalanceSummaryController(ClientBalanceSummaryValidator validator) {
		super(validator);
	}
	
	@Resource(name = "ClientBalanceSummaryService")
	ClientBalanceSummaryService clientBalanceSummaryService;
	
	@RequestMapping(value = "/clientBalanceSummary/updateAccountBalance", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<UpdateAccountBalanceResponseDto> updateAccountBalance(@RequestBody @Valid UpdateAccountBalanceRequest request,
			BindingResult result) {
		logger.info("================= /clientBalanceSummary/updateAccountBalance Start =================");
		IbResponseDto<UpdateAccountBalanceResponseDto> response = null;
		try {
			logger.info("update account balance request =" + g.toJson(request));
			String sender = this.getSender(request);
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {	
				UpdateAccountBalanceResponseDto body = clientBalanceSummaryService.updateAccountBalance(request, sender);
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

		logger.info("update account balance response =" + g.toJson(response));
		logger.info("================= /clientBalanceSummary/updateAccountBalance End =================");
		return response;

	}

	
}
