package com.henyep.ib.terminal.api.dto.response.clientProfile;

import java.util.List;

public class SearchMyClientsResponseDto {

	private List<SearchMyClientsModel> beans;

	public List<SearchMyClientsModel> getBeans() {
		return beans;
	}

	public void setBeans(List<SearchMyClientsModel> beans) {
		this.beans = beans;
	}
	
}
