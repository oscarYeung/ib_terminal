package com.henyep.ib.terminal.server.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.henyep.ib.terminal.api.dto.db.IbProfileBean;
import com.henyep.ib.terminal.api.dto.db.MarginOutBean;
import com.henyep.ib.terminal.api.dto.request.marginout.AdminCancelMarginOutRequest;
import com.henyep.ib.terminal.api.dto.request.marginout.AdminUpdateMarginOutRequest;
import com.henyep.ib.terminal.api.dto.request.marginout.ApproveMarginOutRequest;
import com.henyep.ib.terminal.api.dto.request.marginout.BatchApproveMarginOutRequest;
import com.henyep.ib.terminal.api.dto.request.marginout.CancelMarginOutRequest;
import com.henyep.ib.terminal.api.dto.request.marginout.ExcelUploadMarginOutRequest;
import com.henyep.ib.terminal.api.dto.request.marginout.ExcelUploadMarginOutRequestDto;
import com.henyep.ib.terminal.api.dto.request.marginout.GetMarginOutRequest;
import com.henyep.ib.terminal.api.dto.request.marginout.GetMaxWithdrawalRequest;
import com.henyep.ib.terminal.api.dto.request.marginout.InsertMarginOutRequest;
import com.henyep.ib.terminal.api.dto.request.marginout.RejectMarginOutRequest;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.marginout.BatchApproveMarginOutsResponseDto;
import com.henyep.ib.terminal.api.dto.response.marginout.ExcelUploadMarginOutResponseDto;
import com.henyep.ib.terminal.api.dto.response.marginout.GetMarginOutResponseDto;
import com.henyep.ib.terminal.api.dto.response.marginout.GetMaxWithdrawalResponseDto;
import com.henyep.ib.terminal.api.dto.response.marginout.InsertMarginOutResponseDto;
import com.henyep.ib.terminal.api.dto.response.marginout.UpdateMarginOutResponseDto;
import com.henyep.ib.terminal.server.controller.validator.MarginOutValidator;
import com.henyep.ib.terminal.server.dao.IbProfileDao;
import com.henyep.ib.terminal.server.dao.SystemParamsDao;
import com.henyep.ib.terminal.server.dto.security.SenderDto;
import com.henyep.ib.terminal.server.global.Constants;
import com.henyep.ib.terminal.server.global.EmailConfig;
import com.henyep.ib.terminal.server.service.MarginOutExcelUploadService;
import com.henyep.ib.terminal.server.service.MarginOutService;
import com.henyep.ib.terminal.server.util.EmailUtil;

@Controller
@RequestMapping("/marginOut")
public class MarginOutController extends AbstractController {

	@Resource
	EmailConfig emailConfig;

	@Resource(name = "EmailUtil")
	EmailUtil emailUtil;

	@Resource(name = "IbProfileDao")
	IbProfileDao ibProfileDao;

	@Resource(name = "SystemParamsDao")
	SystemParamsDao systemParamsDao;

	public MarginOutController(MarginOutValidator validator) {
		super(validator);
		// TODO Auto-generated constructor stub
	}

	@Resource(name = "MarginOutService")
	private MarginOutService marginOutService;

	@Resource(name = "MarginOutExcelUploadService")
	private MarginOutExcelUploadService marginOutExcelUploadService;

	@RequestMapping(value = "/insertMarginOut", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<InsertMarginOutResponseDto> insertExecutedMarginOut(@RequestBody @Valid InsertMarginOutRequest request,
			BindingResult result) {
		logger.info("================= /marginOut/insertMarginOut Start =================");
		IbResponseDto<InsertMarginOutResponseDto> response = insertMarginOut(request, result, Constants.MARGIN_OUT_STATUS_EXECUTED);
		logger.info("================= /marginOut/insertMarginOut End =================");
		return response;
	}

	@RequestMapping(value = "/insertPendingMarginOut", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<InsertMarginOutResponseDto> insertPendingMarginOut(@RequestBody @Valid InsertMarginOutRequest request,
			BindingResult result) {
		logger.info("================= /marginOut/insertPendingMarginOut Start =================");
		IbResponseDto<InsertMarginOutResponseDto> response = insertMarginOut(request, result, Constants.MARGIN_OUT_STATUS_PENDING);
		logger.info("================= /marginOut/insertPendingMarginOut End =================");
		return response;
	}

	private IbResponseDto<InsertMarginOutResponseDto> insertMarginOut(InsertMarginOutRequest request, BindingResult result, String status) {

		IbResponseDto<InsertMarginOutResponseDto> response = null;
		try {
			logger.info("insertMarginOut request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				response = marginOutService.createMarginOut(request, status);

				if (response.getBody() != null && status.equals(Constants.MARGIN_OUT_STATUS_PENDING)) {
					// check sending email or not from system param
					boolean isSendEmail = systemParamsDao.sendEmailNewUserRequestMarginOut();
					if (isSendEmail) {
						String receiverEmail = null;

						List<IbProfileBean> ibProfileBeans = ibProfileDao.getIbProfileByIbCode(response.getBody().getMarginOut().getAccount());
						if (ibProfileBeans.size() > 0) {
							IbProfileBean ibProfileBean = ibProfileBeans.get(0);
							receiverEmail = ibProfileBean.getEmail();
							sendEmail(response.getBody().getMarginOut(), response.getBody().getMarginOutFee(), receiverEmail, ibProfileBean);
						}

					}
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		logger.info("insertMarginOut response =" + g.toJson(response));
		return response;
	}

	@RequestMapping(value = "/approveMarginOut", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<UpdateMarginOutResponseDto> approveMarginOut(@RequestBody @Valid ApproveMarginOutRequest request,
			BindingResult result) {
		return executeMarginOut(request, result);
	}

	@RequestMapping(value = "/rejectMarginOut", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<UpdateMarginOutResponseDto> rejectMarginOut(@RequestBody @Valid RejectMarginOutRequest request,
			BindingResult result) {
		return executeMarginOut(request, result);
	}

	@RequestMapping(value = "/adminCancelMarginOut", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<UpdateMarginOutResponseDto> adminCancelMarginOut(@RequestBody @Valid AdminCancelMarginOutRequest request,
			BindingResult result) {
		return executeMarginOut(request, result);
	}

	@RequestMapping(value = "/updateMarginOut", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<UpdateMarginOutResponseDto> executeMarginOut(@RequestBody @Valid AdminUpdateMarginOutRequest request,
			BindingResult result) {
		logger.info("================= /marginOut/updateMarginOut Start =================");
		IbResponseDto<UpdateMarginOutResponseDto> response = null;
		try {
			logger.info("update margin out request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				response = marginOutService.updateMarginOut(request);
			}
		} catch (Exception e) {
			logger.error(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		logger.info("update margin out response =" + g.toJson(response));
		logger.info("================= /marginOut/updateMarginOut End =================");
		return response;
	}

	@RequestMapping(value = "/cancelMarginOut", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<UpdateMarginOutResponseDto> cancelMarginOut(@RequestBody @Valid CancelMarginOutRequest request,
			BindingResult result) {
		IbResponseDto<UpdateMarginOutResponseDto> response = null;
		logger.info("================= /marginOut/cancelMarginOut Start =================");
		try {
			logger.info("update margin out request =" + g.toJson(request));
			response = dtoFactory.createAdminResponse(request.getHeader());

			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				response = marginOutService.cancelMarginOut(request);
			}
		} catch (Exception e) {
			logger.error(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		logger.info("update margin out response =" + g.toJson(response));
		logger.info("================= /marginOut/cancelMarginOut End =================");
		return response;
	}

	@RequestMapping(value = "/batchApproveMarginOuts", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<BatchApproveMarginOutsResponseDto> batchApproveMarginOuts(
			@RequestBody @Valid BatchApproveMarginOutRequest request, BindingResult result) {

		logger.info("================= /marginOut/batchApproveMarginOuts Start =================");
		IbResponseDto<BatchApproveMarginOutsResponseDto> response = null;
		try {
			logger.info("batch approve margin outs request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());

			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {

				List<String> errMsgs = marginOutService.validateApproveMarginOuts(request);
				if (errMsgs.size() == 0) {
					response = marginOutService.approveMarginOuts(request);
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
		logger.info("batch approve margin outs response =" + g.toJson(response));
		logger.info("================= /marginOut/batchApproveMarginOuts End =================");
		return response;
	}

	@RequestMapping(value = "/getMarginOut", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<GetMarginOutResponseDto> getMarginOutByDate(@RequestBody @Valid GetMarginOutRequest request,
			BindingResult result) {

		logger.info("================= /marginOut/getMarginOut Start =================");
		IbResponseDto<GetMarginOutResponseDto> response = null;
		try {
			logger.info("get margin out request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());

			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {

				GetMarginOutResponseDto body = marginOutService.getMarginOut(request);
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
		logger.info("get margin out response =" + g.toJson(response));
		logger.info("================= /marginOut/getMarginOut End =================");
		return response;
	}

	@RequestMapping(value = "/getMaxWithdrawal", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<GetMaxWithdrawalResponseDto> getMaxWithdrawal(@RequestBody @Valid GetMaxWithdrawalRequest request,
			BindingResult result) {

		logger.info("================= /marginOut/getMaxWithdrawal Start =================");
		IbResponseDto<GetMaxWithdrawalResponseDto> response = null;
		try {
			logger.info("get max withdrawal request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());

			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {

				GetMaxWithdrawalResponseDto body = marginOutService.getMaxWithdrawal(request);
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
		logger.info("get max withdrawal response =" + g.toJson(response));
		logger.info("================= /marginOut/getMaxWithdrawal End =================");
		return response;
	}

	@RequestMapping(value = "/test", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody String test(@RequestBody @Valid GetMarginOutRequest request, BindingResult result) {

		// Test
		ExcelUploadMarginOutRequest req = new ExcelUploadMarginOutRequest();
		req.setHeader(request.getHeader());
		ExcelUploadMarginOutRequestDto dto = new ExcelUploadMarginOutRequestDto();
		dto.setBrand_code("CN");

		// "C:\\Test\\Test-UploadMarginOutTemplate.xls"
		Path fileLocation = Paths.get("C:\\Test\\Test-UploadMarginOutTemplate.xls");
		try {
			byte[] data = Files.readAllBytes(fileLocation);
			dto.setExcelBytes(data);

			dto.setExtension("xls");
			req.setBody(dto);
			SenderDto sender = this.getSenderDto(request);
			marginOutExcelUploadService.excelUploadMarginOuts(req, sender);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String jsonString = "'core':{'data':[" + "{\"id\":\"ajson1\",\"parent\":\"#\",\"text\":\"Simple root node\"},"
				+ "{\"id\":\"ajson2\",\"parent\":\"#\",\"text\":\"Root node 2\"},"
				+ "{\"id\":\"ajson3\",\"parent\":\"ajson2\",\"text\":\"Child 1eeee\"},"
				+ "{\"id\":\"ajson4\",\"parent\":\"ajson2\",\"text\":\"Child 2\",'state':{'opened' : true,'selected' : true}}" + "]}";

		return jsonString;

	}

	private void sendEmail(MarginOutBean marginOut, MarginOutBean marginOutFee, String receiverEmail, IbProfileBean ibProfile) {

		logger.debug("Sending margin out email start");

		Map<String, Object> emailModel = new HashMap<String, Object>();
		String template = Constants.EMAIL_TEMPLATE_MARGIN_OUT_REQUEST_EN;
		String emailSubject = emailConfig.getMarginOutSubject_en();

		if (ibProfile.getLanguage() != null && ibProfile.getLanguage().equals(Constants.LANG_CN)) {
			template = Constants.EMAIL_TEMPLATE_MARGIN_OUT_REQUEST_CN;
			emailSubject = emailConfig.getMarginOutSubject_cn();
		}

		// receiver email is null, it will directly send to BCC email
		if (receiverEmail == null)
			receiverEmail = emailConfig.getMarginOutBcc();

		emailModel.put(Constants.EMAIL_RECEIVER, receiverEmail);

		emailModel.put(Constants.EMAIL_SUBJECT, emailSubject);
		emailModel.put(Constants.EMAIL_TEMPLATE, template);
		emailModel.put(Constants.EMAIL_BCC, emailConfig.getMarginOutBcc());
		emailModel.put(Constants.EMAIL_SENDER, emailConfig.getMarginOutSender());

		emailModel.put("account", marginOut.getAccount());
		emailModel.put("amount", marginOut.getAccount_amount().toString());
		emailModel.put("id", marginOut.getId().toString());
		emailModel.put("ib_name", ibProfile.getFirst_name() + " " + ibProfile.getLast_name());

		emailUtil.sendEmail(emailModel);

		logger.debug("Sending new margin out email finish");

	}

	@RequestMapping(value = "/excelUploadMarginOut", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<ExcelUploadMarginOutResponseDto> ExcelUploadMarginOut(@RequestBody @Valid ExcelUploadMarginOutRequest request,
			BindingResult result) {

		logger.info("================= /marginOut/excelUploadMarginOut Start =================");
		IbResponseDto<ExcelUploadMarginOutResponseDto> response = null;

		try {
			logger.info("Excel upload margin outs request =" + g.toJson(request));
			response = dtoFactory.createAdminResponse(request.getHeader());

			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				SenderDto sender = this.getSenderDto(request);
				ExcelUploadMarginOutResponseDto body = marginOutExcelUploadService.excelUploadMarginOuts(request, sender);
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
		logger.info("Excel upload margin outs response =" + g.toJson(response));
		logger.info("================= /marginOut/excelUploadMarginOut End =================");
		return response;

	}

}
