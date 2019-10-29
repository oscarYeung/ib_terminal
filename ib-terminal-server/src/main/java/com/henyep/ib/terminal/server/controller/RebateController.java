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

import com.henyep.ib.terminal.api.dto.request.rebate.GetRebateByIbCodeRequest;
import com.henyep.ib.terminal.api.dto.request.rebate.GetRebateByIbCodeWithDateRequest;
import com.henyep.ib.terminal.api.dto.request.rebate.GetRebateByRebateCodeWithDateRequest;
import com.henyep.ib.terminal.api.dto.request.rebate.InsertRebateRequest;
import com.henyep.ib.terminal.api.dto.request.rebate.InsertRebateSupplementariesRequest;
import com.henyep.ib.terminal.api.dto.request.rebate.UpdateRebateRequest;
import com.henyep.ib.terminal.api.dto.request.rebate.UpdateRebateSupplementariesRequest;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.rebate.IbClientRebateResponseDto;
import com.henyep.ib.terminal.api.dto.response.rebate.RebateDetailsResponseDto;
import com.henyep.ib.terminal.api.dto.response.rebate.RebateResponseDto;
import com.henyep.ib.terminal.api.dto.response.rebate.RebateSupplementasriesResponseDto;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.server.controller.validator.RebateValidator;
import com.henyep.ib.terminal.server.service.RebateService;

@Controller
@RequestMapping("/rebate")
public class RebateController extends AbstractController {

	@Autowired
	public RebateController(RebateValidator validator) {
		super(validator);
	}

	@Resource(name = "RebateService")
	private RebateService rebateService;

	@RequestMapping(value = "/insertRebateDetails", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<RebateDetailsResponseDto> insertRebateDetails(@RequestBody @Valid InsertRebateRequest request,
			BindingResult result) {

		logger.info("================= /rebate/insertRebateDetails Start =================");
		IbResponseDto<RebateDetailsResponseDto> response = null;
		try {
			logger.info("insert rebate detail request =" + g.toJson(request));
			response = dtoFactory.createAdminResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				List<String> errList = rebateService.validateInsertRebateDetailsRequest(request);
				if (errList.size() == 0) {
					RebateDetailsResponseDto body = rebateService.updateRebateDetailsRequest(request);
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
		logger.info("insert rebate detail response =" + g.toJson(response));
		logger.info("================= /rebate/insertRebateDetails End =================");
		return response;
	}

	@RequestMapping(value = "/updateRebateDetails", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<RebateDetailsResponseDto> updateRebateDetails(@RequestBody @Valid UpdateRebateRequest request,
			BindingResult result) {
		logger.info("================= /rebate/updateRebateDetails Start =================");
		IbResponseDto<RebateDetailsResponseDto> response = null;
		try {
			logger.info("update rebate detail request =" + g.toJson(request));
			response = dtoFactory.createAdminResponse(request.getHeader());

			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				List<String> errList = rebateService.validateUpdateRebateDetailsRequest(request);
				if (errList.size() == 0) {
					RebateDetailsResponseDto body = rebateService.updateRebateDetailsRequest(request);
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
		logger.info("update rebate detail response =" + g.toJson(response));
		logger.info("================= /rebate/updateRebateDetails End =================");
		return response;
	}

	@RequestMapping(value = "/insertRebateSupplementaries", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<RebateSupplementasriesResponseDto> insertRebateSupplementaries(
			@RequestBody @Valid InsertRebateSupplementariesRequest request, BindingResult result) {

		logger.info("================= /rebate/insertRebateSupplementaries Start =================");
		IbResponseDto<RebateSupplementasriesResponseDto> response = null;
		try {
			logger.info("insert rebate supplementaries request =" + g.toJson(request));
			response = dtoFactory.createAdminResponse(request.getHeader());

			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				List<String> errList = rebateService.validateInsertRebateSupplementariesRequest(request);
				if (errList.size() == 0) {
					RebateSupplementasriesResponseDto body = rebateService.updateRebateSupplementariesRequest(request);
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
		logger.info("insert rebate supplementaries response =" + g.toJson(response));
		logger.info("================= /rebate/insertRebateSupplementaries End =================");
		return response;
	}

	@RequestMapping(value = "/updateRebateSupplementaries", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<RebateSupplementasriesResponseDto> updateRebateSupplementaries(
			@RequestBody @Valid UpdateRebateSupplementariesRequest request, BindingResult result) {
		logger.info("================= /rebate/updateRebateSupplementaries Start =================");
		IbResponseDto<RebateSupplementasriesResponseDto> response = null;
		try {
			logger.info("update rebate supplementaries request =" + g.toJson(request));
			response = dtoFactory.createAdminResponse(request.getHeader());

			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				List<String> errList = rebateService.validateUpdateRebateSupplementariesRequest(request);
				if (errList.size() == 0) {
					RebateSupplementasriesResponseDto body = rebateService.updateRebateSupplementariesRequest(request);
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
		logger.info("update rebate supplementaries response =" + g.toJson(response));
		logger.info("================= /rebate/updateRebateSupplementaries End =================");
		return response;
	}

	@RequestMapping(value = "/getRebateByRebateCode", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<RebateResponseDto> getRebateByRebateCode(@RequestBody @Valid GetRebateByRebateCodeWithDateRequest request,
			BindingResult result) {

		logger.info("================= /rebate/getRebateByRebateCode Start =================");
		IbResponseDto<RebateResponseDto> response = null;
		try {
			logger.info("getRebateByRebateCode request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				RebateResponseDto body = rebateService.getRebateByRebateCodeRequest(request);
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
		logger.info("getRebateByRebateCode response =" + g.toJson(response));
		logger.info("================= /rebate/getRebateByRebateCode End =================");
		return response;
	}

	@RequestMapping(value = "/getRebateByIbCode", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<RebateResponseDto> getRebateByIbCode(@RequestBody @Valid GetRebateByIbCodeRequest request,
			BindingResult result) {
		logger.info("================= /rebate/getRebateByIbCode Start =================");
		IbResponseDto<RebateResponseDto> response = null;
		try {
			logger.info("get rebate by ibCode request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				RebateResponseDto body = rebateService.getRebateByIbCodeRequest(request);
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
		logger.info("getRebateByRebateCode response =" + g.toJson(response));
		logger.info("================= /rebate/getRebateByIbCode End =================");
		return response;
	}

	@RequestMapping(value = "/getRebateByIbCodeWithDateRange", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<IbClientRebateResponseDto> getRebateByIbCodeWithDateRange(
			@RequestBody @Valid GetRebateByIbCodeWithDateRequest request, BindingResult result) {

		logger.info("================= /rebate/getRebateByIbCodeWithDateRange Start =================");
		IbResponseDto<IbClientRebateResponseDto> response = null;
		try {
			logger.info("getRebateByIbCodeWithDateRange request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {

				IbClientRebateResponseDto body = rebateService.getRebateByIbCodeWithDateRangeRequest(request);
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
		logger.info("getRebateByIbCodeWithDateRange response =" + g.toJson(response));
		logger.info("================= /rebate/getRebateByIbCodeWithDateRange End =================");
		return response;
	}
}
