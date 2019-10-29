package com.henyep.ib.terminal.server.controller;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.henyep.ib.terminal.api.dto.db.IbCommissionDetailsSupplementaryBean;
import com.henyep.ib.terminal.api.dto.request.ibcommission.CpaCalculateRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.EvCalculateRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.EvConfirmRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.EvDataRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.EvFigureUpdateRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.EvSearchRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.GetIbClientSummaryRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.GetIbSummaryRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.GetLastTradeDateRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.GetTradingAccountListRequest;
import com.henyep.ib.terminal.api.dto.request.marginin.InsertMarginInRequest;
import com.henyep.ib.terminal.api.dto.request.marginin.InsertMarginInRequestDto;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.CpaResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.EvResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.GetIbClientSummaryResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.GetIbCommissionDetailsResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.GetIbCommissionResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.GetIbSummaryResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.GetIbSummaryTrimmedResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.GetLastTradeDateResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.GetTradingAccountListResponseDto;
import com.henyep.ib.terminal.api.dto.response.marginin.InsertMarginInResponseDto;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.server.controller.validator.IbCommissionValidator;
import com.henyep.ib.terminal.server.service.IbCommissionService;
import com.henyep.ib.terminal.server.service.MarginInService;

@Controller
@RequestMapping("/ibCommission")
public class IbCommissionController extends AbstractController {

	public IbCommissionController(IbCommissionValidator validator) {
		super(validator);
	}

	@Resource(name = "IbCommissionService")
	IbCommissionService ibCommissionService;

	@Resource(name = "MarginInService")
	private MarginInService marginInService;

	// @RequestMapping(value = "/calculateEv", method = RequestMethod.POST,
	// produces = "application/json", consumes = "application/json")
	// public @ResponseBody IbResponseDto<EvResponseDto>
	// calculateEv(@RequestBody @Valid EvDataRequest request, BindingResult
	// result) {
	// IbResponseDto<EvResponseDto> response = null;
	// logger.info("================= /ibCommission/calculateEv Start
	// =================");
	// try {
	// logger.info("request =" + g.toJson(request));
	// response = dtoFactory.createAdminResponse(request.getHeader());
	// if (result.hasErrors()) {
	// response = hySpringUtil.setDtoErrorFromResult(response, result);
	// } else {
	// EvResponseDto body = new EvResponseDto();
	// String err =
	// ibCommissionService.valideCalculateCommissionSupplementary(request,
	// result);
	// if (err != null) {
	// hySpringUtil.setDtoErrorFromResult(response, result, new Object[] { err
	// });
	// } else {
	// body.setData(ibCommissionService.calculateCommissionSupplementary(request));
	// response.setBody(body);
	// }
	// }
	//
	// } catch (ServiceException se) {
	// logger.error("Error " + se.getErrorCode());
	// response = dtoFactory.createResp();
	// hySpringUtil.setDtoErrorFromErrorCode(response, se.getErrorCode());
	// } catch (Exception e) {
	// logger.error(e, e);
	// response = dtoFactory.createResp();
	// hySpringUtil.setDtoErrorFromErrorCode(response,
	// Constants.ERR_COMMON_INTERNAL_ERROR);
	// }
	// logger.info("response =" + g.toJson(response));
	// logger.info("================= /ibCommission/calculateEv End
	// =================");
	// return response;
	// }

	@RequestMapping(value = "/calculateCpa", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<CpaResponseDto> calculateCpa(@RequestBody @Valid CpaCalculateRequest request, BindingResult result) {
		logger.info("================= /ibCommission/calculateCpa Start =================");
		IbResponseDto<CpaResponseDto> response = null;
		try {
			logger.info("request =" + g.toJson(request));
			response = dtoFactory.createAdminResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				CpaResponseDto body = new CpaResponseDto();
				body.setData(ibCommissionService.calculateCpaCommission(request));
				response.setBody(body);
			}
		} catch (Exception e) {
			logger.error(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		logger.info("response =" + g.toJson(response));
		logger.info("================= /ibCommission/calculateCpa End =================");
		return response;
	}

	
	@RequestMapping(value = "/calculateEv", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<EvResponseDto> calculateEv(@RequestBody @Valid EvCalculateRequest request, BindingResult result) {

		logger.info("================= /ibCommission/calculateEv Start =================");
		IbResponseDto<EvResponseDto> response = null;
		try {
			logger.info("request =" + g.toJson(request));
			response = dtoFactory.createAdminResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				EvResponseDto body = new EvResponseDto();
				if (!ibCommissionService.valideCalculationOfEV(request)) {
					hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_REBATE_EV_STATUS_NOT_PENDING);
				} else {
					logger.info("Calculate Indivdual");
					ibCommissionService.prepareCommissionSupplementary(request);
					logger.info("Calculate By Group");
					body.setData(ibCommissionService.calculateEvCommissionByGroup(request));
					response.setBody(body);
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		logger.info("response =" + g.toJson(response));
		logger.info("================= /ibCommission/calculateEv End =================");
		return response;

	}

	@RequestMapping(value = "/saveCustomEvFigure", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<EvResponseDto> saveCustomEvFigure(@RequestBody @Valid EvFigureUpdateRequest request, BindingResult result) {

		logger.info("================= /ibCommission/saveCustomEvFigure Start =================");
		IbResponseDto<EvResponseDto> response = null;
		try {
			logger.info("request =" + g.toJson(request));
			response = dtoFactory.createAdminResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {

				List<String> errList = ibCommissionService.validateUpdateCommissionSupplementaryAdjustment(request.getBody());
				if (errList.size() == 0) {
					EvResponseDto body = new EvResponseDto();
					List<IbCommissionDetailsSupplementaryBean> beanList = ibCommissionService.updateCommissionSupplementary(request);
					if (beanList != null && beanList.size() > 0) {
						// List<IbCommissionDetailsSupplementaryBean> list = new
						// ArrayList<IbCommissionDetailsSupplementaryBean>();
						// List<IbCommissionDetailsSupplementaryBean> list_1 =
						// ibCommissionService.calculateCommissionSupplementary(request);
						List<IbCommissionDetailsSupplementaryBean> list_2 = ibCommissionService.confirmEvCommissionByGroup(request);
						// if (list_2 != null && list_2.size() > 0)
						// list.addAll(list_2);
						body.setData(list_2);
						response.setBody(body);
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
		logger.info("response =" + g.toJson(response));
		logger.info("================= /ibCommission/saveCustomEvFigure End =================");
		return response;

	}

	private boolean marginInIbAccount(IbCommissionDetailsSupplementaryBean commModel) {
		try {
			InsertMarginInRequest marginInRequest = new InsertMarginInRequest();
			InsertMarginInRequestDto dto = new InsertMarginInRequestDto();
			dto.setAmount(commModel.getEv_commission());
			dto.setAccount(commModel.getIb_code());
			dto.setCategory(Constants.MARGIN_IN_CATEGORY_REBATE);
			dto.setCurrency(commModel.getCurrency());
			dto.setTrade_date(commModel.getTrade_date());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(commModel.getTrade_date());
			Integer year = calendar.get(Calendar.YEAR);
			Integer month = calendar.get(Calendar.MONTH);
			month += 1;
			String settledMonth = year.toString() + "-" + month.toString();
			dto.setComment("EV commission for" + settledMonth);
			InsertMarginInResponseDto response = marginInService.insertMarginIn(marginInRequest);
			if (response != null)
				return true;
		} catch (Exception e) {
			logger.error(e, e);
		}
		return false;
	}

	@RequestMapping(value = "/confirmEvData", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<EvResponseDto> confirmEvData(@RequestBody @Valid EvConfirmRequest request, BindingResult result) {

		logger.info("================= /ibCommission/confirmEvData Start =================");
		IbResponseDto<EvResponseDto> response = null;
		try {
			logger.info("request =" + g.toJson(request));
			response = dtoFactory.createAdminResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {

				List<String> errList = ibCommissionService.validateConfirmCommissionSupplementary(request);
				if (errList.size() == 0) {
					EvResponseDto body = new EvResponseDto();
					List<IbCommissionDetailsSupplementaryBean> beanList = ibCommissionService.confirmCommissionSupplementary(request);
					for (IbCommissionDetailsSupplementaryBean model : beanList) {
						marginInIbAccount(model);
					}
					body.setData(beanList);
					response.setBody(body);
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
		logger.info("response =" + g.toJson(response));
		logger.info("================= /ibCommission/confirmEvData End =================");
		return response;

	}

	// @RequestMapping(value = "/submitEvData", method = RequestMethod.POST,
	// produces = "application/json", consumes = "application/json")
	// public @ResponseBody IbResponseDto<EvResponseDto>
	// submitEvData(@RequestBody @Valid EvSubmitRequest request, BindingResult
	// result) {
	//
	// logger.info("================= /ibCommission/submitEvData Start
	// =================");
	// IbResponseDto<EvResponseDto> response = null;
	// try {
	// logger.info("request =" + g.toJson(request));
	// response = dtoFactory.createAdminResponse(request.getHeader());
	// if (result.hasErrors()) {
	// response = hySpringUtil.setDtoErrorFromResult(response, result);
	// } else {
	//
	// List<String> errList =
	// ibCommissionService.validateSubmitCommissionSupplementary(request.getBody());
	// if (errList.size() == 0) {
	// EvResponseDto body = new EvResponseDto();
	// List<IbCommissionDetailsSupplementaryBean> beanList =
	// ibCommissionService.submitCommissionSupplementary(request.getBody());
	// body.setData(beanList);
	// response.setBody(body);
	// } else {
	// for (String err : errList) {
	// hySpringUtil.setDtoErrorFromErrorCode(response, err);
	// }
	// }
	//
	// }
	// } catch (Exception e) {
	// logger.error(e, e);
	// response = dtoFactory.createResp();
	// hySpringUtil.setDtoErrorFromErrorCode(response,
	// Constants.ERR_COMMON_INTERNAL_ERROR);
	// }
	// logger.info("response =" + g.toJson(response));
	// logger.info("================= /ibCommission/submitEvData End
	// =================");
	// return response;
	//
	// }

	// @RequestMapping(value = "/updateEvAdjustment", method =
	// RequestMethod.POST, produces = "application/json", consumes =
	// "application/json")
	// public @ResponseBody IbResponseDto<EvResponseDto>
	// updateEvAdjustment(@RequestBody @Valid EvFigureUpdateRequest request,
	// BindingResult result) {
	//
	// logger.info("================= /ibCommission/updateEvAdjustment Start
	// =================");
	// IbResponseDto<EvResponseDto> response = null;
	// try {
	// logger.info("request =" + g.toJson(request));
	// response = dtoFactory.createAdminResponse(request.getHeader());
	// if (result.hasErrors()) {
	// response = hySpringUtil.setDtoErrorFromResult(response, result);
	// } else {
	//
	// List<String> errList =
	// ibCommissionService.validateUpdateCommissionSupplementaryAdjustment(request.getBody());
	// if (errList.size() == 0) {
	// EvResponseDto body = new EvResponseDto();
	// List<IbCommissionDetailsSupplementaryBean> beanList = ibCommissionService
	// .updateCommissionSupplementaryAdjustment(request.getBody());
	// body.setData(beanList);
	// response.setBody(body);
	// } else {
	// for (String err : errList) {
	// hySpringUtil.setDtoErrorFromErrorCode(response, err);
	// }
	// }
	//
	// }
	// } catch (Exception e) {
	// logger.error(e, e);
	// response = dtoFactory.createResp();
	// hySpringUtil.setDtoErrorFromErrorCode(response,
	// Constants.ERR_COMMON_INTERNAL_ERROR);
	// }
	// logger.info("response =" + g.toJson(response));
	// logger.info("================= /ibCommission/updateEvAdjustment End
	// =================");
	// return response;
	//
	// }

	@RequestMapping(value = "/prepareEvData", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<EvResponseDto> prepareEvData(@RequestBody @Valid EvDataRequest request, BindingResult result) {

		logger.info("================= /ibCommission/prepareEvData Start =================");
		IbResponseDto<EvResponseDto> response = null;
		try {
			logger.info("request =" + g.toJson(request));
			response = dtoFactory.createAdminResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				EvResponseDto body = new EvResponseDto();
				body.setData(ibCommissionService.prepareCommissionSupplementary(request));
				response.setBody(body);
			}
		} catch (Exception e) {
			logger.error(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		logger.info("response =" + g.toJson(response));
		logger.info("================= /ibCommission/prepareEvData End =================");
		return response;

	}

	@RequestMapping(value = "/getEvData", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<EvResponseDto> getEvData(@RequestBody @Valid EvSearchRequest request, BindingResult result) {

		logger.info("================= /ibCommission/getEvData Start =================");
		IbResponseDto<EvResponseDto> response = null;
		try {
			logger.info("request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				EvResponseDto body = new EvResponseDto();
				body.setData(ibCommissionService.getCommissionSupplementary(request));
				response.setBody(body);
			}
		} catch (Exception e) {
			logger.error(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		logger.info("response =" + g.toJson(response));
		logger.info("================= /ibCommission/getEvData End =================");
		return response;

	}

	@RequestMapping(value = "/getTrimmedIbSummary", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<GetIbSummaryTrimmedResponseDto> getTrimmedIbSummary(@RequestBody @Valid GetIbSummaryRequest request,
			BindingResult result) {
		logger.info("================= /ibCommission/getTrimmedIbSummary Start =================");

		IbResponseDto<GetIbSummaryTrimmedResponseDto> response = null;
		try {
			logger.info("get trimmed ib summary request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {

				GetIbSummaryTrimmedResponseDto body = ibCommissionService.getTrimmedIbSummary(request);
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
		logger.info("================= /ibCommission/getTrimmedIbSummary End =================");
		return response;
	}
	
	@RequestMapping(value = "/getTradingAccountList", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<GetTradingAccountListResponseDto> getTradingAccountList(@RequestBody @Valid GetTradingAccountListRequest request,
			BindingResult result) {
		logger.info("================= /ibCommission/getTradingAccountList Start =================");

		
		
		
		IbResponseDto<GetTradingAccountListResponseDto> response = null;
		try {
			String sender = this.getSender(request);
			
			logger.info("get trading account list request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				GetTradingAccountListResponseDto body = new GetTradingAccountListResponseDto();
				if(sender.equals(request.getBody().getIb_code())){
					body = ibCommissionService.getTradingAccountList(request);
				}
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
		logger.info("================= /ibCommission/getTradingAccountList End =================");
		return response;
	}
	
	

	@RequestMapping(value = "/getIbSummary", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<GetIbSummaryResponseDto> getIbSummary(@RequestBody @Valid GetIbSummaryRequest request, BindingResult result) {

		logger.info("================= /ibCommission/getIbSummary Start =================");

		IbResponseDto<GetIbSummaryResponseDto> response = null;
		try {
			logger.info("get getIbSummary request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {

				List<String> errList = ibCommissionService.validateGetIbSummary(request);
				if (errList.size() == 0) {
					GetIbSummaryResponseDto body = ibCommissionService.getIbSummary(request);
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
			Logger.getRootLogger().fatal(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}

		logger.info("response =" + g.toJson(response));
		logger.info("================= /ibCommission/getIbSummary End =================");
		return response;
	}

	@RequestMapping(value = "/getIbClientSummary", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<GetIbClientSummaryResponseDto> getIbClientSummary(@RequestBody @Valid GetIbClientSummaryRequest request,
			BindingResult result) {

		logger.info("================= /ibCommission/getIbClientSummary Start =================");
		IbResponseDto<GetIbClientSummaryResponseDto> response = null;
		try {
			logger.info("request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {

				List<String> errList = ibCommissionService.validateGetIbClientSummary(request);
				if (errList.size() == 0) {
					GetIbClientSummaryResponseDto body = ibCommissionService.getIbClientSummary(request);
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
			Logger.getRootLogger().fatal(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}

		logger.info("response =" + g.toJson(response));
		logger.info("================= /ibCommission/getIbClientSummary End =================");
		return response;

	}

	@RequestMapping(value = "/getIbCommissionSummary", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<GetIbCommissionResponseDto> getIbCommissionSummary(@RequestBody @Valid GetIbClientSummaryRequest request,
			BindingResult result) {

		logger.info("================= /ibCommission/getIbCommissionSummary Start =================");
		logger.info("request =" + g.toJson(request));
		IbResponseDto<GetIbCommissionResponseDto> response = null;
		try {
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				GetIbCommissionResponseDto body = ibCommissionService.getIbCommissionSummary(request);
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
		logger.info("================= /ibCommission/getIbCommissionSummary End =================");
		return response;

	}

	@RequestMapping(value = "/getIbCommissionSummaryDetails", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<GetIbCommissionDetailsResponseDto> getIbCommissionSummaryDetails(
			@RequestBody @Valid GetIbClientSummaryRequest request, BindingResult result) {

		logger.info("================= /ibCommission/getIbCommissionDetailsSummary Start =================");
		logger.info("request =" + g.toJson(request));
		IbResponseDto<GetIbCommissionDetailsResponseDto> response = null;
		try {
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				GetIbCommissionDetailsResponseDto body = ibCommissionService.getIbCommissionSummaryDetails(request);
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
		logger.info("================= /ibCommission/getIbCommissionDetailsSummary End =================");
		return response;

	}

	@RequestMapping(value = "/getLastTradeDate", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<GetLastTradeDateResponseDto> getLastTradeDate(@RequestBody @Valid GetLastTradeDateRequest request,
			BindingResult result) {
		logger.info("================= /ibCommission/getLastTradeDate Start =================");

		IbResponseDto<GetLastTradeDateResponseDto> response = dtoFactory.createResp();
		try {
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				GetLastTradeDateResponseDto responseDto = new GetLastTradeDateResponseDto();
				responseDto.setLastTradeDate(ibCommissionService.getLastTradeDate());
				response.setBody(responseDto);
			}
		} catch (Exception e) {
			Logger.getRootLogger().fatal(e, e);
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}

		logger.info("response =" + g.toJson(response));
		logger.info("================= /ibCommission/getLastTradeDate End =================");
		return response;
	}

}
