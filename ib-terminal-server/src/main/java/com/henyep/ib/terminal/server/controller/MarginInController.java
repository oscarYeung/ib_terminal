package com.henyep.ib.terminal.server.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.henyep.ib.terminal.api.dto.request.marginin.ApproveMarginInRequest;
import com.henyep.ib.terminal.api.dto.request.marginin.BatchApproveMarginInRequest;
import com.henyep.ib.terminal.api.dto.request.marginin.CancelMarginInRequest;
import com.henyep.ib.terminal.api.dto.request.marginin.GetMarginInRequest;
import com.henyep.ib.terminal.api.dto.request.marginin.InsertMarginInRequest;
import com.henyep.ib.terminal.api.dto.request.marginin.RejectMarginInRequest;
import com.henyep.ib.terminal.api.dto.request.marginin.UpdateMarginInRequest;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.marginin.BatchApproveMarginInResponseDto;
import com.henyep.ib.terminal.api.dto.response.marginin.GetMarginInResponseDto;
import com.henyep.ib.terminal.api.dto.response.marginin.InsertMarginInResponseDto;
import com.henyep.ib.terminal.api.dto.response.marginin.UpdateMarginInResponseDto;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.server.controller.validator.MarginInValidator;
import com.henyep.ib.terminal.server.service.MarginInService;

@Controller
@RequestMapping("/marginIn")
public class MarginInController extends AbstractController {

	public MarginInController(MarginInValidator validator) {
		super(validator);
	}

	@Resource(name = "MarginInService")
	private MarginInService marginInService;

	@RequestMapping(value = "/approveMarginIn", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<UpdateMarginInResponseDto> approveMarginIn(@RequestBody @Valid ApproveMarginInRequest request,
			BindingResult result) {
		return updateMarginIn(request, result);
	}
	
	@RequestMapping(value = "/rejectMarginIn", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<UpdateMarginInResponseDto> rejectMarginIn(@RequestBody @Valid RejectMarginInRequest request,
			BindingResult result) {
		return updateMarginIn(request, result);
	}
	
	@RequestMapping(value = "/cancelMarginIn", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<UpdateMarginInResponseDto> cancelMargin(@RequestBody @Valid CancelMarginInRequest request,
			BindingResult result) {
		return updateMarginIn(request, result);
	}
	
	@RequestMapping(value = "/updateMarginIn", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<UpdateMarginInResponseDto> updateMarginIn(@RequestBody @Valid UpdateMarginInRequest request,
			BindingResult result) {
		logger.info("================= /marginIn/updateMarginIn Start =================");
		IbResponseDto<UpdateMarginInResponseDto> response = null;
		try {
			logger.info("request =" + g.toJson(request));
			response = dtoFactory.createAdminResponse(request.getHeader());

			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {

				List<String> errMsgs = marginInService.validateUpdateMarginIn(request);
				if (errMsgs.size() == 0) {
					UpdateMarginInResponseDto body = marginInService.updateMarginIn(request);
					if (body != null) {
						response.setBody(body);
					} else {
						String defaultMsg = Constants.ERR_COMMON_INTERNAL_ERROR;
						hySpringUtil.setDtoErrorFromErrorCode(response, defaultMsg);
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
		logger.info("response =" + g.toJson(response));
		logger.info("================= /marginIn/updateMarginIn End =================");
		return response;
	}

	@RequestMapping(value = "/batchApproveMarginIns", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<BatchApproveMarginInResponseDto> batchApproveMarginIns(@RequestBody @Valid BatchApproveMarginInRequest request,
			BindingResult result) {

		logger.info("================= /marginIn/batchApproveMarginIns Start =================");
		IbResponseDto<BatchApproveMarginInResponseDto> response = null;
		try {
			logger.info("batchApproveMarginIn request =" + g.toJson(request));
			response = dtoFactory.createAdminResponse(request.getHeader());

			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {

				List<String> errMsgs = marginInService.validateBatchApproveMarginInRequest(request);
				if (errMsgs.size() == 0) {
					BatchApproveMarginInResponseDto body = marginInService.batchApproveMarginIn(request);
					if (body != null) {
						response.setBody(body);
					} else {
						String defaultMsg = Constants.ERR_COMMON_INTERNAL_ERROR;
						hySpringUtil.setDtoErrorFromErrorCode(response, defaultMsg);
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
		logger.info("batchApproveMarginIn response =" + g.toJson(response));
		logger.info("================= /marginIn/batchApproveMarginIns End =================");
		return response;
	}

	@RequestMapping(value = "/getMarginIn", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<GetMarginInResponseDto> getMarginIn(@RequestBody @Valid GetMarginInRequest request, BindingResult result) {

		logger.info("================= /marginIn/getMarginIn Start =================");
		IbResponseDto<GetMarginInResponseDto> response = null;
		try {
			logger.info("request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {

				List<String> errMsgs = marginInService.validateGetMarginIn(request);
				if (errMsgs.size() == 0) {
					GetMarginInResponseDto body = marginInService.getMarginIn(request);
					if (body != null) {
						response.setBody(body);
					} else {
						String defaultMsg = Constants.ERR_COMMON_INTERNAL_ERROR;
						hySpringUtil.setDtoErrorFromErrorCode(response, defaultMsg);
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
		logger.info("response =" + g.toJson(response));
		logger.info("================= /marginIn/getMarginIn End =================");
		return response;
	}

	@RequestMapping(value = "/addMarginIn", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<InsertMarginInResponseDto> addMarginIn(@RequestBody @Valid InsertMarginInRequest request,
			BindingResult result) {

		logger.info("================= /marginIn/addMarginIn Start =================");
		IbResponseDto<InsertMarginInResponseDto> response = null;
		try {
			logger.info("request =" + g.toJson(request));
			response = dtoFactory.createAdminResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {

				List<String> errMsgs = marginInService.validateInsertMarginIn(request);
				if (errMsgs.size() == 0) {
					InsertMarginInResponseDto body = marginInService.insertMarginIn(request);
					if (body != null) {
						response.setBody(body);
					} else {
						String defaultMsg = Constants.ERR_COMMON_INTERNAL_ERROR;
						hySpringUtil.setDtoErrorFromErrorCode(response, defaultMsg);
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
		logger.info("response =" + g.toJson(response));
		logger.info("================= /marginIn/addMarginIn End =================");
		return response;
	}
}
