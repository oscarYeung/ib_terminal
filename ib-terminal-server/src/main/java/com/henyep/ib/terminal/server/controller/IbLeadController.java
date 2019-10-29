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

import com.henyep.ib.terminal.api.dto.request.ib.lead.GetIbLeadsRequest;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.ib.lead.GetIbLeadsResponseDto;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.server.controller.validator.IbLeadValidator;
import com.henyep.ib.terminal.server.service.IbLeadService;

@Controller
@RequestMapping("/ibLead")
public class IbLeadController extends AbstractController {

	@Autowired
	public IbLeadController(IbLeadValidator validator) {
		super(validator);

	}

	@Resource(name = "IbLeadService")
	private IbLeadService ibLeadService;

	@RequestMapping(value = "/getLeads", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<GetIbLeadsResponseDto> getLeads(@RequestBody @Valid GetIbLeadsRequest request, BindingResult result) {

		logger.info("================= /ibLead/getLeads Start =================");
		IbResponseDto<GetIbLeadsResponseDto> response = null;
		try {
			logger.info("/ibLead/getLeads request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				String ibCode = request.getBody().getIb_code();
				String name = request.getBody().getName();
				String phone = request.getBody().getPhone();
				String email = request.getBody().getEmail();
				boolean isWithSubIbLeads = request.getBody().getWithSubIbLeads();
				
				GetIbLeadsResponseDto body = ibLeadService.getIbLeads(ibCode, name, email, phone, isWithSubIbLeads);
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
		logger.info("================= /ibLead/getLeads End =================");
		return response;
	}
}
