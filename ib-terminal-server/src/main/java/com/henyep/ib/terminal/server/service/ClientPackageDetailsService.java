package com.henyep.ib.terminal.server.service;

import java.util.List;

import com.henyep.ib.terminal.api.dto.db.RebateDetailsBean;
import com.henyep.ib.terminal.api.dto.response.clientPackageDetails.ClientPackageSpreadTypeDto;

public interface ClientPackageDetailsService {

	public List<String> getAllClientPackages() throws Exception;

	public List<ClientPackageSpreadTypeDto> getClientPackagesByIbCode(String ibCode) throws Exception;
	
	
}
