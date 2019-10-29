package com.henyep.ib.terminal.server.service;

import java.util.Date;
import java.util.List;

import org.springframework.validation.BindingResult;

import com.henyep.ib.terminal.api.dto.db.IbCommissionCpaBean;
import com.henyep.ib.terminal.api.dto.db.IbCommissionDetailsSupplementaryBean;
import com.henyep.ib.terminal.api.dto.request.ibcommission.CalculateIbCommissionRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.CpaCalculateRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.EvConfirmRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.EvDataRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.EvFigureUpdateRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.EvFigureUpdateRequestDto;
import com.henyep.ib.terminal.api.dto.request.ibcommission.GetIbClientSummaryRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.GetIbSummaryRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.GetTradingAccountListRequest;
import com.henyep.ib.terminal.api.dto.response.ibcommission.CalculateIbCommissionResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.GetIbClientSummaryResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.GetIbCommissionDetailsResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.GetIbCommissionResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.GetIbSummaryResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.GetIbSummaryTrimmedResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.GetTradingAccountListResponseDto;
import com.henyep.ib.terminal.api.dto.response.user.CurrentMothData;
import com.henyep.ib.terminal.server.exception.ServiceException;

public interface IbCommissionService {

	/*
	 * Calculate commission by specific date range Start Date and End Date are
	 * inclusive
	 * 
	 */

	public CalculateIbCommissionResponseDto calculateCommission(CalculateIbCommissionRequest request, String ibTeamHead, String sender)
			throws Exception;

	public CurrentMothData calculateIbCommissionByDate(String ibCode, String startDate, String endDate) throws Exception;

	public List<String> validateGetIbSummary(GetIbSummaryRequest request) throws Exception;

	public GetIbSummaryResponseDto getIbSummary(GetIbSummaryRequest request) throws Exception;

	public GetIbSummaryTrimmedResponseDto getTrimmedIbSummary(GetIbSummaryRequest request) throws Exception;

	public List<String> validateGetIbClientSummary(GetIbClientSummaryRequest request) throws Exception;

	public GetIbClientSummaryResponseDto getIbClientSummary(GetIbClientSummaryRequest request) throws Exception;

	public List<IbCommissionDetailsSupplementaryBean> prepareCommissionSupplementary(EvDataRequest request) throws Exception;

	public List<IbCommissionDetailsSupplementaryBean> getCommissionSupplementary(EvDataRequest request) throws Exception;

	public List<IbCommissionDetailsSupplementaryBean> updateCommissionSupplementary(EvFigureUpdateRequest request) throws Exception;

	public List<String> validateUpdateCommissionSupplementaryAdjustment(EvFigureUpdateRequestDto request) throws Exception;

	public List<IbCommissionDetailsSupplementaryBean> calculateCommissionSupplementary(EvFigureUpdateRequest request) throws ServiceException;

	public String valideCalculateCommissionSupplementary(EvDataRequest request, BindingResult result) throws Exception;

	public List<IbCommissionDetailsSupplementaryBean> confirmCommissionSupplementary(EvConfirmRequest request) throws Exception;

	public List<String> validateConfirmCommissionSupplementary(EvConfirmRequest request) throws Exception;

	public GetIbCommissionResponseDto getIbCommissionSummary(GetIbClientSummaryRequest request) throws Exception;

	public GetIbCommissionDetailsResponseDto getIbCommissionSummaryDetails(GetIbClientSummaryRequest request) throws Exception;

	public Date getLastTradeDate() throws Exception;

	public List<IbCommissionDetailsSupplementaryBean> calculateEvCommissionByGroup(EvDataRequest request) throws Exception;

	public List<IbCommissionDetailsSupplementaryBean> confirmEvCommissionByGroup(EvFigureUpdateRequest request) throws Exception;

	public List<IbCommissionCpaBean> calculateCpaCommission(CpaCalculateRequest request)  throws Exception;
	
	public boolean valideCalculationOfEV(EvDataRequest request);
	
	public GetTradingAccountListResponseDto getTradingAccountList(GetTradingAccountListRequest request)throws Exception;
}
