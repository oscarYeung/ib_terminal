package com.henyep.ib.terminal.server.controller;

import java.util.Date;
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

import com.henyep.ib.terminal.api.dto.request.ib.tree.AddIbTreeByIbCodeRequest;
import com.henyep.ib.terminal.api.dto.request.ib.tree.AddIbTreeRequest;
import com.henyep.ib.terminal.api.dto.request.ib.tree.DeleteIbTreeRequest;
import com.henyep.ib.terminal.api.dto.request.ib.tree.GetIbTreeRequest;
import com.henyep.ib.terminal.api.dto.request.ib.tree.MoveIbTreeRequest;
import com.henyep.ib.terminal.api.dto.request.ib.tree.SearchIbRequest;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.ib.tree.IbTreeResponseDto;
import com.henyep.ib.terminal.api.dto.response.ib.tree.SearchIbResponseDto;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.server.controller.validator.IbTreeValidator;
import com.henyep.ib.terminal.server.dto.security.SenderDto;
import com.henyep.ib.terminal.server.service.IbTreeService;

@Controller
@RequestMapping("/ibTree")
public class IbTreeController extends AbstractController {

	@Autowired
	public IbTreeController(IbTreeValidator validator) {
		super(validator);
	}

	@Resource(name = "IbTreeService")
	private IbTreeService ibTreeService;

	@RequestMapping(value = "/getByTeamHead", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<IbTreeResponseDto> getByTeamHead(@RequestBody @Valid GetIbTreeRequest request, BindingResult result) {

		logger.info("================= /ibTree/getByTeamHead Start =================");
		IbResponseDto<IbTreeResponseDto> response = null;
		try {
			logger.info("request =" + g.toJson(request));
			SenderDto sender = this.getSenderDto(request);
			response = dtoFactory.createWebResponse(request.getHeader());

			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				IbTreeResponseDto body = new IbTreeResponseDto();
				body.setCore(ibTreeService.getByTeamHeadWebFormat(request, sender));
				if (body.getCore() != null) {
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
		logger.info("================= /ibTree/getByTeamHead End =================");
		return response;
	}
	
	@RequestMapping(value = "/getIbTreeByIb", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<IbTreeResponseDto> getIbTreeByIb(@RequestBody @Valid GetIbTreeRequest request, BindingResult result) {

		logger.info("================= /ibTree/getIbTreeByIb Start =================");
		IbResponseDto<IbTreeResponseDto> response = null;
		try {
			logger.info("request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());

			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				IbTreeResponseDto body = new IbTreeResponseDto();
				body.setCore(ibTreeService.getIbTreeByIb(request.getBody().getIb_code(), request.getBody().getTrade_date()));
				if (body.getCore() != null) {
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
		logger.info("================= /ibTree/getIbTreeByIb End =================");
		return response;
	}
	

	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<IbTreeResponseDto> add(@RequestBody @Valid AddIbTreeRequest request, BindingResult result) {

		logger.info("================= /ibTree/add Start =================");
		IbResponseDto<IbTreeResponseDto> response = null;
		try {
			logger.info("request =" + g.toJson(request));
			response = dtoFactory.createAdminResponse(request.getHeader());

			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {				
				int rtn = ibTreeService.addIbToTree(request);

				if (rtn == -1) {
					hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_IB_TREE_PARENT_ID_NOT_EXIST);
				} else if (rtn == -2) {
					hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_IB_TREE_NEW_IB_CODE_ALREADY_EXIST);
				} else if (rtn == -3) {
					hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_IB_TREE_START_DATE_NOT_IN_PARENT_START_END_DATE);
				} else if (rtn == -4) {
					hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_IB_TREE_END_DATE_NOT_IN_PARENT_START_END_DATE);
				}
			}
		} catch (Exception e) {
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
			response = dtoFactory.createResp();
			logger.error(e, e);
		}
		logger.info("response =" + g.toJson(response));
		logger.info("================= /ibTree/add End =================");
		return response;
	}
	
	@RequestMapping(value = "/addByIbCode", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<IbTreeResponseDto> addByIbCode(@RequestBody @Valid AddIbTreeByIbCodeRequest request, BindingResult result) {

		logger.info("================= /ibTree/addByIbCode Start =================");
		IbResponseDto<IbTreeResponseDto> response = null;
		try {
			logger.info("request =" + g.toJson(request));
			response = dtoFactory.createAdminResponse(request.getHeader());

			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {				
				
				String user = this.getSender(request);
				
				List<String> errorCodes = ibTreeService.validateAddByIbCode(request.getBody().getParent_ib_code(), request.getBody().getTarget_ib_code(), request.getBody().getStart_date());
				if(errorCodes.size() > 0){
					hySpringUtil.setDtoErrorFromErrorCode(response, errorCodes.get(0));
				}
				else{
					int rtn = ibTreeService.addByIbCode(request.getBody().getParent_ib_code(), request.getBody().getTarget_ib_code(), request.getBody().getStart_date(), user);

					if (rtn == -1) {
						hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_IB_TREE_PARENT_ID_NOT_EXIST);
					} else if (rtn == -2) {
						hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_IB_TREE_NEW_IB_CODE_ALREADY_EXIST);
					} else if (rtn == -3) {
						hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_IB_TREE_START_DATE_NOT_IN_PARENT_START_END_DATE);
					} else if (rtn == -4) {
						hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_IB_TREE_END_DATE_NOT_IN_PARENT_START_END_DATE);
					}
				}
			}
		} catch (Exception e) {
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
			response = dtoFactory.createResp();
			logger.error(e, e);
		}
		logger.info("response =" + g.toJson(response));
		logger.info("================= /ibTree/addByIbCode End =================");
		return response;
	}
	
	

	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<IbTreeResponseDto> delete(@RequestBody @Valid DeleteIbTreeRequest request, BindingResult result) {

		logger.info("================= /ibTree/delete Start =================");
		IbResponseDto<IbTreeResponseDto> response = null;
		try {
			logger.info("/ibTree/delete request =" + g.toJson(request));
			response = dtoFactory.createAdminResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {				
				int rtn = ibTreeService.deteleIbFromTree(request);

				if (rtn == -1) {
					hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_IB_TREE_ID_NOT_EXIST);
				}
			}
		} catch (Exception e) {
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
			response = dtoFactory.createResp();
			logger.error(e, e);
		}
		logger.info("/ibTree/delete response =" + g.toJson(response));
		logger.info("================= /ibTree/delete End =================");
		return response;
	}

	@RequestMapping(value = "/move", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<IbTreeResponseDto> move(@RequestBody @Valid MoveIbTreeRequest request, BindingResult result) {
		logger.info("================= /ibTree/move Start =================");
		IbResponseDto<IbTreeResponseDto> response = null;
		try {
			logger.info("/ibTree/move request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());
			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {				
				int rtn = ibTreeService.moveIb(request);

				if (rtn == -1) {
					hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_IB_TREE_SAME_FROM_ID_TO_ID);
				} else if (rtn == -2) {
					hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_IB_TREE_FROM_ID_NOT_EXIST);
				} else if (rtn == -3) {
					hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_IB_TREE_TO_ID_NOT_EXIST);
				} else if (rtn == -4) {
					hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_IB_TREE_TO_ID_IS_CHILD_OF_FROM_ID);
				} else if (rtn == -5) {
					hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_IB_TREE_FROM_ID_DATE_RANGE_NOT_WITHIN_TO_ID);
				}
			}
		} catch (Exception e) {
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
			response = dtoFactory.createResp();
			logger.error(e, e);
		}
		logger.info("/ibTree/move response =" + g.toJson(response));
		logger.info("================= /ibTree/move End =================");
		return response;
	}

	@RequestMapping(value = "/searchIb", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody IbResponseDto<SearchIbResponseDto> searchIb(@RequestBody @Valid SearchIbRequest request, BindingResult result) {

		logger.info("================= /ibTree/searchIb Start =================");
		IbResponseDto<SearchIbResponseDto> response = null;
		try {
			logger.info("/ibTree/search ib request =" + g.toJson(request));
			response = dtoFactory.createWebResponse(request.getHeader());

			if (result.hasErrors()) {
				response = hySpringUtil.setDtoErrorFromResult(response, result);
			} else {
				String ibCode = request.getBody().getIb_code();
				Date tradeDate = request.getBody().getTrade_date();
				SearchIbResponseDto body = ibTreeService.searchIb(ibCode, tradeDate);
				response.setBody(body);

			}
		} catch (Exception e) {
			hySpringUtil.setDtoErrorFromErrorCode(response, Constants.ERR_COMMON_INTERNAL_ERROR);
			response = dtoFactory.createResp();
			logger.error(e, e);
		}
		logger.info("/ibTree/search ib response =" + g.toJson(response));
		logger.info("================= /ibTree/searchIb End =================");
		return response;
	}

}
