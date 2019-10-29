package com.henyep.ib.terminal.server.service;

import java.util.List;

import com.henyep.ib.terminal.api.dto.db.IbClientMapBean;
import com.henyep.ib.terminal.api.dto.request.ib.client.map.GetIbClientMapByIbCodeClientCodeRequest;
import com.henyep.ib.terminal.api.dto.request.ib.client.map.GetIbClientMapRequest;
import com.henyep.ib.terminal.api.dto.request.ib.client.map.InsertFromWlRegistrationRequest;
import com.henyep.ib.terminal.api.dto.request.ib.client.map.InsertIbClientMapRequest;
import com.henyep.ib.terminal.api.dto.request.ib.client.map.SwitchIbClientMapRequest;
import com.henyep.ib.terminal.api.dto.request.ib.client.map.UpdateIbClientMapRequest;
import com.henyep.ib.terminal.api.dto.response.ib.client.map.InsertFromWlRegistrationResponseDto;

public interface IbClientMapService {

	public List<IbClientMapBean> getByIbCodeDateRange(GetIbClientMapRequest request);
	public List<IbClientMapBean> getByIbCodeClientCode(GetIbClientMapByIbCodeClientCodeRequest request) throws Exception;
	
	public Integer getClientCountByIbCodeDateRange(GetIbClientMapRequest request);
	
	public IbClientMapBean updateIbClientMap(UpdateIbClientMapRequest request, String updatedBy) throws Exception;
	public String validateUpdateIbClientMap(UpdateIbClientMapRequest request) throws Exception;
	
	public List<IbClientMapBean> swtichIb(SwitchIbClientMapRequest request, String updatedBy) throws Exception;
	public String validateSwtichIb(SwitchIbClientMapRequest request) throws Exception;
	
	public List<IbClientMapBean> insert(InsertIbClientMapRequest request, String createdBy) throws Exception;
	public String validateInsert(InsertIbClientMapRequest request) throws Exception;
	
	public InsertFromWlRegistrationResponseDto insertFromWlRegistration(InsertFromWlRegistrationRequest request, String createdBy) throws Exception;
	public String validateInsertFromWlRegistration(InsertFromWlRegistrationRequest request) throws Exception;
}
