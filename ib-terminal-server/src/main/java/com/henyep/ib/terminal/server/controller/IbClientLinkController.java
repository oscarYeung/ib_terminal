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

import com.henyep.ib.terminal.api.dto.request.ib.client.link.AddIbClientLinkRequest;
import com.henyep.ib.terminal.api.dto.request.ib.client.link.GetIbClientLinkRequest;
import com.henyep.ib.terminal.api.dto.request.ib.client.link.UpdateIbClientLinkRequest;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.ib.client.link.GetIbClientLinkResponseDto;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.server.controller.validator.IbClientLinkValidator;
import com.henyep.ib.terminal.server.service.IbClientLinkService;

@Controller
@RequestMapping("/ibClientLink")
public class IbClientLinkController extends AbstractController {

	@Autowired
	public IbClientLinkController(IbClientLinkValidator validator) {
		super(validator);

	}

	@Resource(name = "IbClientLinkService")
	private IbClientLinkService ibClientLinkService;

	@RequestMapping(value = "/getLinks", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<GetIbClientLinkResponseDto> getLinks(@RequestBody @Valid GetIbClientLinkRequest request,
			BindingResult result) {

		logger.info("================= /ibClientLink/getLinks Start =================");
		IbResponseDto<GetIbClientLinkResponseDto> response = null;
		try {

			logger.info("request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				GetIbClientLinkResponseDto body = ibClientLinkService.getIbClientLinkList(request.getBody().getBrand_code(),
						request.getBody().getIb_code());
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
		logger.info("================= /ibClientLink/getLinks End =================");
		return response;
	}

	@RequestMapping(value = "/addLink", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<GetIbClientLinkResponseDto> addLink(@RequestBody @Valid AddIbClientLinkRequest request, BindingResult result) {

		logger.info("================= /ibClientLink/addLink Start =================");
		IbResponseDto<GetIbClientLinkResponseDto> response = null;

		try {
			logger.info("addLink request = " + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				List<String> errMsgs = ibClientLinkService.validateAddIbClientLink(request);
				if (errMsgs.size() == 0) {
					GetIbClientLinkResponseDto body = ibClientLinkService.addIbClientLink(request);
					if (body != null) {
						response.setBody(body);
					} else {
						hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
					}
				} else {
					for (String errMsg : errMsgs) {
						hySpringUtil.setDtoErrorFromErrorCode(response, errMsg);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		logger.info("addLink response = " + g.toJson(response));
		logger.info("================= /ibClientLink/addLink End =================");
		return response;
	}

	@RequestMapping(value = "/updateLink", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<GetIbClientLinkResponseDto> updateLink(@RequestBody @Valid UpdateIbClientLinkRequest request,
			BindingResult result) {

		logger.info("================= /ibClientLink/updateLink Start =================");
		IbResponseDto<GetIbClientLinkResponseDto> response = null;

		try {
			logger.info("updateLink request = " + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());

			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				List<String> errMsgs = ibClientLinkService.validateUpdateIbClientLink(request);
				if (errMsgs.size() == 0) {
					GetIbClientLinkResponseDto body = ibClientLinkService.updateIbClientLink(request);
					if (body != null) {
						response.setBody(body);
					} else {
						hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
					}
				} else {
					for (String errMsg : errMsgs) {
						hySpringUtil.setDtoErrorFromErrorCode(response, errMsg);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		logger.info("updateLink response = " + g.toJson(response));
		logger.info("================= /ibClientLink/updateLink End =================");
		return response;
	}

}
