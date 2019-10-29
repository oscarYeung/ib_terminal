package com.henyep.ib.terminal.server.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.henyep.ib.terminal.api.dto.db.RebateDetailsBean;
import com.henyep.ib.terminal.api.dto.request.clientPackageDetails.GetAllClientPackagesRequest;
import com.henyep.ib.terminal.api.dto.request.clientPackageDetails.GetClientPackagesByIbCodeRequest;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.clientPackageDetails.GetAllClientPackagesResponse;
import com.henyep.ib.terminal.api.dto.response.clientPackageDetails.GetClientPackagesByIbCodeResponse;
import com.henyep.ib.terminal.api.dto.response.clientPackageDetails.ClientPackageSpreadTypeDto;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.server.controller.validator.ClientPackageDetailsValidator;
import com.henyep.ib.terminal.server.service.ClientPackageDetailsService;

@Controller
public class ClientPackageDetailsController extends AbstractController {

	public ClientPackageDetailsController(ClientPackageDetailsValidator validator) {
		super(validator);

	}

	@Resource(name = "ClientPackageDetailsService")
	ClientPackageDetailsService clientPackageDetailsService;

	@RequestMapping(value = "/clientPackageDetails/getAllClientPackages", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<GetAllClientPackagesResponse> getAllClientPackages(@RequestBody @Valid GetAllClientPackagesRequest request,
			BindingResult result) {
		logger.info("================= /clientPackageDetails/getAllClientPackages Start =================");
		IbResponseDto<GetAllClientPackagesResponse> response = null;
		try {
			logger.info("get all client packages =" + g.toJson(request));
			response = dtoFactory.createAdminResponse(request.getHeader());
			GetAllClientPackagesResponse dto = new GetAllClientPackagesResponse();
			response.setBody(dto);
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				List<String> clientPackages = clientPackageDetailsService.getAllClientPackages();
				if (clientPackages != null) {
					response.getBody().setClient_packages(clientPackages);
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}

		logger.info("get all client packages =" + g.toJson(response));
		logger.info("================= /clientPackageDetails/getAllClientPackages End =================");
		return response;
	}

	@RequestMapping(value = "/clientPackageDetails/getClientPackagesByIbCode", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<GetClientPackagesByIbCodeResponse> getClientPackagesByIbCode(
			@RequestBody @Valid GetClientPackagesByIbCodeRequest request, BindingResult result) {
		logger.info("================= /clientPackageDetails/getClientPackagesByIbCode Start =================");
		IbResponseDto<GetClientPackagesByIbCodeResponse> response = null;
		try {
			logger.info("get client packages by ib code =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());
			GetClientPackagesByIbCodeResponse dto = new GetClientPackagesByIbCodeResponse();
			response.setBody(dto);

			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				List<ClientPackageSpreadTypeDto> insertedClientPackages = clientPackageDetailsService.getClientPackagesByIbCode(request.getBody().getIb_code());
				List<String> allClientPackages = clientPackageDetailsService.getAllClientPackages();
				if (insertedClientPackages != null) {
					response.getBody().setInserted_client_packages(insertedClientPackages);
				} else {
					response.getBody().setInserted_client_packages(new ArrayList<ClientPackageSpreadTypeDto>());
				}
				List<ClientPackageSpreadTypeDto> remindClientPackages = new ArrayList<ClientPackageSpreadTypeDto>();
				
				
				List<String> availableSpreadTypes = new ArrayList<String>();
				availableSpreadTypes.add(com.henyep.ib.terminal.server.global.Constants.SPREAD_TYPE_FIXED);
				availableSpreadTypes.add(com.henyep.ib.terminal.server.global.Constants.SPREAD_TYPE_VARIABLE);
				
				for (String clientPackage : allClientPackages) {
					for(String spreadType : availableSpreadTypes){
						boolean isExist = false;
						for(ClientPackageSpreadTypeDto insertedClientPackage : insertedClientPackages){
							if(insertedClientPackage.getSpread_type().equalsIgnoreCase(spreadType) 
									&& insertedClientPackage.getClient_package_code().equalsIgnoreCase(clientPackage)){
								isExist = true;
							}
						}
						if(!isExist){
							ClientPackageSpreadTypeDto responseDto = new ClientPackageSpreadTypeDto();
							responseDto.setClient_package_code(clientPackage);
							responseDto.setSpread_type(spreadType);
							remindClientPackages.add(responseDto);
						}
					}
				}
				response.getBody().setAvailable_client_packages(remindClientPackages);
			}
		} catch (Exception e) {
			logger.error(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}

		logger.info("get client packages by ib code =" + g.toJson(response));
		logger.info("================= /clientPackageDetails/getClientPackagesByIbCode End =================");
		return response;
	}
}
