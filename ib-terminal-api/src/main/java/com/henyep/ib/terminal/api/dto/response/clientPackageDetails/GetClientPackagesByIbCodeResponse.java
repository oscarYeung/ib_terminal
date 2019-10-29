package com.henyep.ib.terminal.api.dto.response.clientPackageDetails;

import java.io.Serializable;
import java.util.List;

public class GetClientPackagesByIbCodeResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9205572369570622758L;
	
	
	private List<ClientPackageSpreadTypeDto> inserted_client_packages;
	
	private List<ClientPackageSpreadTypeDto> available_client_packages;

	public List<ClientPackageSpreadTypeDto> getInserted_client_packages() {
		return inserted_client_packages;
	}

	public void setInserted_client_packages(List<ClientPackageSpreadTypeDto> inserted_client_packages) {
		this.inserted_client_packages = inserted_client_packages;
	}

	public List<ClientPackageSpreadTypeDto> getAvailable_client_packages() {
		return available_client_packages;
	}

	public void setAvailable_client_packages(List<ClientPackageSpreadTypeDto> available_client_packages) {
		this.available_client_packages = available_client_packages;
	}

}
