package com.henyep.ib.terminal.api.dto.response.clientPackageDetails;

import java.io.Serializable;
import java.util.List;

public class GetAllClientPackagesResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8844947779265273226L;
	
	private List<String> client_packages;

	public List<String> getClient_packages() {
		return client_packages;
	}

	public void setClient_packages(List<String> client_packages) {
		this.client_packages = client_packages;
	}
	
}
