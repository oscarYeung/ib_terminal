package com.henyep.ib.terminal.server.service;

import java.util.List;


public interface ProductSymbolMapService  {

	public List<String> getCurrentProductGroupList(String sender, String spread_type) throws Exception;
	
}
