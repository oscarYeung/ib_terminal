package com.henyep.ib.terminal.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.server.dao.ProductSymbolMapDao;
import com.henyep.ib.terminal.server.service.ProductSymbolMapService;

@Component("ProductSymbolMapService")
public class ProductSymbolMapServiceImpl implements ProductSymbolMapService{

	private ProductSymbolMapDao productSymbolMapDao;
	
	@Autowired
	public ProductSymbolMapServiceImpl(ProductSymbolMapDao productSymbolMapDao) {
		this.productSymbolMapDao = productSymbolMapDao;
	}

	
	@Override
	public List<String> getCurrentProductGroupList(String sender, String spread_type) throws Exception {
		List<String> productSymbols = this.productSymbolMapDao.getCurrentProductGroupList(sender, spread_type);
		return productSymbols;
	}

}
