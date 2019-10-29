package com.henyep.ib.terminal.server.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.henyep.ib.terminal.api.dto.request.trade.GetClientTradeHistoryRequest;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.trade.GetClientTradeHistoryResponseDto;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.server.controller.validator.TradeValidator;
import com.henyep.ib.terminal.server.service.TradeService;

@Controller
@RequestMapping("/trade")
public class TradeController extends AbstractController {

	public TradeController(TradeValidator validator) {
		super(validator);
	}

	@Resource(name = "TradeService")
	private TradeService tradeService;

	@RequestMapping(value = "/getTradeHistory", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<GetClientTradeHistoryResponseDto> getTradeHistory(@RequestBody @Valid GetClientTradeHistoryRequest request,
			BindingResult result) {
		logger.info("================= /trade/getTradeHistory Start =================");
		IbResponseDto<GetClientTradeHistoryResponseDto> response = null;
		try {
			logger.info("Get trade history request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				GetClientTradeHistoryResponseDto body = tradeService.getClientTradeHistory(request);
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
		logger.info("Get trade history response =" + g.toJson(response));
		logger.info("================= /trade/getTradeHistory End =================");
		return response;
	}
	

}
