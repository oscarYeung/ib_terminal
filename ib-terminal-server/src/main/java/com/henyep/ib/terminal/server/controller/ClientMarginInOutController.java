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

import com.henyep.ib.terminal.api.dto.request.client.margin.in.out.ClientMarginInOutRequest;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.client.margin.in.out.ClientMarginInOutResponseDto;
import com.henyep.ib.terminal.server.controller.validator.ClientMarginInOutValidator;
import com.henyep.ib.terminal.server.global.Constants;
import com.henyep.ib.terminal.server.service.ClientMarginInOutService;

@Controller
@RequestMapping("/clientMarginInOut")
public class ClientMarginInOutController extends AbstractController {

	@Autowired
	public ClientMarginInOutController(ClientMarginInOutValidator validator) {
		super(validator);
	}

	@Resource(name = "ClientMarginInOutService")
	private ClientMarginInOutService clientMarginInOutService;

	@RequestMapping(value = "/getByDateRange", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<ClientMarginInOutResponseDto> getByDateRange(@RequestBody @Valid ClientMarginInOutRequest request,
			BindingResult result) {
		logger.info("================= /clientMarginInOut/getByDateRange Start =================");
		IbResponseDto<ClientMarginInOutResponseDto> response = null;
		try {			
			logger.info("request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				ClientMarginInOutResponseDto body = new ClientMarginInOutResponseDto();
				body.setList(clientMarginInOutService.getClientMarginInOut(request));
				response.setBody(body);
			}
		} catch (Exception e) {
			logger.error(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		logger.info("response =" + g.toJson(response));
		logger.info("================= /clientMarginInOut/getByDateRange End =================");
		return response;
	}

}
