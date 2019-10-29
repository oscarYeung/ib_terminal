package com.henyep.ib.terminal.server.controller;

import java.util.ArrayList;
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

import com.henyep.ib.terminal.api.dto.db.IbClientMapBean;
import com.henyep.ib.terminal.api.dto.request.ib.client.map.GetIbClientMapByIbCodeClientCodeRequest;
import com.henyep.ib.terminal.api.dto.request.ib.client.map.GetIbClientMapRequest;
import com.henyep.ib.terminal.api.dto.request.ib.client.map.InsertFromWlRegistrationRequest;
import com.henyep.ib.terminal.api.dto.request.ib.client.map.InsertIbClientMapRequest;
import com.henyep.ib.terminal.api.dto.request.ib.client.map.SwitchIbClientMapRequest;
import com.henyep.ib.terminal.api.dto.request.ib.client.map.UpdateIbClientMapChangeIbRequest;
import com.henyep.ib.terminal.api.dto.request.ib.client.map.UpdateIbClientMapChangePackageRequest;
import com.henyep.ib.terminal.api.dto.request.ib.client.map.UpdateIbClientMapCloseRequest;
import com.henyep.ib.terminal.api.dto.request.ib.client.map.UpdateIbClientMapRequest;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.ib.client.map.GetIbClientMapCountResponseDto;
import com.henyep.ib.terminal.api.dto.response.ib.client.map.GetIbClientMapResponseDto;
import com.henyep.ib.terminal.api.dto.response.ib.client.map.InsertFromWlRegistrationResponseDto;
import com.henyep.ib.terminal.server.controller.validator.IbClientMapValidator;
import com.henyep.ib.terminal.server.global.Constants;
import com.henyep.ib.terminal.server.service.IbClientMapService;

@Controller
@RequestMapping("/ibClientMap")
public class IbClientMapController extends AbstractController {

	@Autowired
	public IbClientMapController(IbClientMapValidator validator) {
		super(validator);
	}

	@Resource(name = "IbClientMapService")
	private IbClientMapService ibClientMapService;

	@RequestMapping(value = "/getClientCountByIbCodeDateRange", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<GetIbClientMapCountResponseDto> getClientCountByIbCodeDateRange(@RequestBody @Valid GetIbClientMapRequest request,
			BindingResult result) {
		IbResponseDto<GetIbClientMapCountResponseDto> response = null;
		logger.info("================= /ibClientMap/getClientCountByIbCodeDateRange Start =================");
		try {
			logger.info("request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				GetIbClientMapCountResponseDto body = new GetIbClientMapCountResponseDto();
				body.setCount(ibClientMapService.getClientCountByIbCodeDateRange(request));
				response.setBody(body);
			}
		} catch (Exception e) {
			logger.error(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		logger.info("response =" + g.toJson(response));
		logger.info("================= /ibClientMap/getClientCountByIbCodeDateRange End =================");
		return response;
	}
	
	@RequestMapping(value = "/getByIbCodeDateRange", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<GetIbClientMapResponseDto> getByIbCodeDateRange(@RequestBody @Valid GetIbClientMapRequest request,
			BindingResult result) {
		IbResponseDto<GetIbClientMapResponseDto> response = null;
		logger.info("================= /ibClientMap/getByIbCodeDateRange Start =================");
		try {
			logger.info("request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				GetIbClientMapResponseDto body = new GetIbClientMapResponseDto();
				body.setData(ibClientMapService.getByIbCodeDateRange(request));
				response.setBody(body);
			}
		} catch (Exception e) {
			logger.error(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		logger.info("response =" + g.toJson(response));
		logger.info("================= /ibClientMap/getByIbCodeDateRange End =================");
		return response;
	}
	
	
	@RequestMapping(value = "/getByIbCodeClientCode", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<GetIbClientMapResponseDto> getByIbCodeClientCode(@RequestBody @Valid GetIbClientMapByIbCodeClientCodeRequest request,
			BindingResult result) {
		IbResponseDto<GetIbClientMapResponseDto> response = null;
		logger.info("================= /ibClientMap/getByIbCodeClientCode Start =================");
		try {
			logger.info("request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				GetIbClientMapResponseDto body = new GetIbClientMapResponseDto();
				body.setData(ibClientMapService.getByIbCodeClientCode(request));
				response.setBody(body);
			}
			
		} catch (Exception e) {
			logger.error(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		logger.info("response =" + g.toJson(response));
		logger.info("================= /ibClientMap/getByIbCodeClientCode End =================");
		return response;
	}

	
	@RequestMapping(value = "/closeIb", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<GetIbClientMapResponseDto> closeIb(@RequestBody @Valid UpdateIbClientMapCloseRequest request,
			BindingResult result) {
		logger.info("================= /ibClientMap/closeIb Start =================");
		IbResponseDto<GetIbClientMapResponseDto> res = update(request, result);
		logger.info("================= /ibClientMap/closeIb End =================");
		return res;
	}
	@RequestMapping(value = "/changeIb", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<GetIbClientMapResponseDto> changeIb(@RequestBody @Valid UpdateIbClientMapChangeIbRequest request,
			BindingResult result) {
		logger.info("================= /ibClientMap/changeIb Start =================");
		IbResponseDto<GetIbClientMapResponseDto> res = update(request, result);
		logger.info("================= /ibClientMap/changeIb End =================");
		return res;
	}
	
	@RequestMapping(value = "/changePackage", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<GetIbClientMapResponseDto> changePackage(@RequestBody @Valid UpdateIbClientMapChangePackageRequest request,
			BindingResult result) {
		logger.info("================= /ibClientMap/changePackage Start =================");
		IbResponseDto<GetIbClientMapResponseDto> res = update(request, result);
		logger.info("================= /ibClientMap/changePackage End =================");
		return res;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<GetIbClientMapResponseDto> update(@RequestBody @Valid UpdateIbClientMapRequest request,
			BindingResult result) {
		IbResponseDto<GetIbClientMapResponseDto> response = null;
		logger.info("================= /ibClientMap/update Start =================");
		try {
			logger.info("request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				String errorCode = ibClientMapService.validateUpdateIbClientMap(request);
				if(errorCode == null){
					GetIbClientMapResponseDto body = new GetIbClientMapResponseDto();
					IbClientMapBean bean = ibClientMapService.updateIbClientMap(request, getSender(request));
					ArrayList<IbClientMapBean> beans = new ArrayList<IbClientMapBean>();
					beans.add(bean);
					body.setData(beans);
					response.setBody(body);
				}
				else{
					hySpringUtil.setDtoErrorFromErrorCode(response, errorCode);
				}
			}
			
		} catch (Exception e) {
			logger.error(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		logger.info("response =" + g.toJson(response));
		logger.info("================= /ibClientMap/update End =================");
		return response;
	}
	
	@RequestMapping(value = "/switchIb", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<GetIbClientMapResponseDto> switchIb(@RequestBody @Valid SwitchIbClientMapRequest request,
			BindingResult result) {
		IbResponseDto<GetIbClientMapResponseDto> response = null;
		logger.info("================= /ibClientMap/switchIb Start =================");
		try {
			logger.info("request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				String errorCode = ibClientMapService.validateSwtichIb(request);
				if(errorCode == null){
					GetIbClientMapResponseDto body = new GetIbClientMapResponseDto();
					List<IbClientMapBean> beans = ibClientMapService.swtichIb(request, getSender(request));
					body.setData(beans);
					response.setBody(body);
				}
				else{
					hySpringUtil.setDtoErrorFromErrorCode(response, errorCode);
				}
			}
			
		} catch (Exception e) {
			logger.error(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		logger.info("response =" + g.toJson(response));
		logger.info("================= /ibClientMap/switchIb End =================");
		return response;
	}
	

	@RequestMapping(value = "/insert", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<GetIbClientMapResponseDto> insert(@RequestBody @Valid InsertIbClientMapRequest request,
			BindingResult result) {
		IbResponseDto<GetIbClientMapResponseDto> response = null;
		logger.info("================= /ibClientMap/insert Start =================");
		try {
			logger.info("request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				String errorCode = ibClientMapService.validateInsert(request);
				if(errorCode == null){
					GetIbClientMapResponseDto body = new GetIbClientMapResponseDto();
					List<IbClientMapBean> beans = ibClientMapService.insert(request, getSender(request));
					body.setData(beans);
					response.setBody(body);
				}
				else{
					hySpringUtil.setDtoErrorFromErrorCode(response, errorCode);
				}
			}
			
		} catch (Exception e) {
			logger.error(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		logger.info("response =" + g.toJson(response));
		logger.info("================= /ibClientMap/insert End =================");
		return response;
	}

	@RequestMapping(value = "/insertFromWlRegistration", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<InsertFromWlRegistrationResponseDto> insertFromWlRegistration(@RequestBody @Valid InsertFromWlRegistrationRequest request,
			BindingResult result) {
		IbResponseDto<InsertFromWlRegistrationResponseDto> response = null;
		logger.info("================= /ibClientMap/insertFromWlRegistration Start =================");
		try {
			logger.info("request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				String errorCode = ibClientMapService.validateInsertFromWlRegistration(request);
				if(errorCode == null){
					InsertFromWlRegistrationResponseDto body = ibClientMapService.insertFromWlRegistration(request, getSender(request));
					response.setBody(body);
				}
				else{
					hySpringUtil.setDtoErrorFromErrorCode(response, errorCode);
				}
			}
			
		} catch (Exception e) {
			logger.error(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		logger.info("response =" + g.toJson(response));
		logger.info("================= /ibClientMap/insertFromWlRegistration End =================");
		return response;
	}

	
}
