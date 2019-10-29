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

import com.henyep.ib.terminal.api.dto.request.product.symbol.GetCurrentProductGroupsRequest;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.product.symbol.GetCurrentProductGroupsResponseDto;
import com.henyep.ib.terminal.api.dto.response.product.symbol.SpreadTypeProductGroupDto;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.server.controller.validator.ProductSymbolMapValidator;
import com.henyep.ib.terminal.server.service.ProductSymbolMapService;

@Controller
@RequestMapping("/productSymbolMap")
public class ProductSymbolMapController extends AbstractController {

	public ProductSymbolMapController(ProductSymbolMapValidator validator) {
		super(validator);
	}

	@Resource(name = "ProductSymbolMapService")
	ProductSymbolMapService productSymbolMapService;

	@RequestMapping(value = "/getCurrentProductGroups", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<GetCurrentProductGroupsResponseDto> getCurrentProductGroups(
			@RequestBody @Valid GetCurrentProductGroupsRequest request, BindingResult result) {
		logger.info("================= /productSymbolMap/getCurrentProductGroups Start =================");
		IbResponseDto<GetCurrentProductGroupsResponseDto> response = null;

		
		try {
			logger.info("get current product groups request =" + g.toJson(request));
			String sender = this.getSender(request);
			
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {

				GetCurrentProductGroupsResponseDto dto = new GetCurrentProductGroupsResponseDto();
				List<SpreadTypeProductGroupDto> spreadTypeProductGroupList = new ArrayList<SpreadTypeProductGroupDto>();
				
				
				List<String> productGroups = productSymbolMapService.getCurrentProductGroupList(sender, com.henyep.ib.terminal.server.global.Constants.SPREAD_TYPE_FIXED);
				if (productGroups != null) {
					SpreadTypeProductGroupDto fixedDto = new SpreadTypeProductGroupDto();
					fixedDto.setProduct_groups(productGroups);
					fixedDto.setSpread_type(com.henyep.ib.terminal.server.global.Constants.SPREAD_TYPE_FIXED);
					spreadTypeProductGroupList.add(fixedDto);
				}
				
				productGroups = productSymbolMapService.getCurrentProductGroupList(sender, com.henyep.ib.terminal.server.global.Constants.SPREAD_TYPE_VARIABLE);
				if (productGroups != null) {
					SpreadTypeProductGroupDto variableDto = new SpreadTypeProductGroupDto();
					variableDto.setProduct_groups(productGroups);
					variableDto.setSpread_type(com.henyep.ib.terminal.server.global.Constants.SPREAD_TYPE_VARIABLE);
					spreadTypeProductGroupList.add(variableDto);
				} else {
					hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
				}
				dto.setSpreadTypeProductGroupList(spreadTypeProductGroupList);
				response.setBody(dto);
			}
		} catch (Exception e) {
			logger.error(e, e);
			response = dtoFactory.createResp();
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
		}

		logger.info("get current product groups response =" + g.toJson(response));
		logger.info("================= /productSymbolMap/getCurrentProductGroups End =================");
		return response;

	}

}
