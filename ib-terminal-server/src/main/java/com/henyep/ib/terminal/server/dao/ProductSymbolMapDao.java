package com.henyep.ib.terminal.server.dao;
import java.util.List;
import java.util.Date;
import com.henyep.ib.terminal.api.dto.db.ProductSymbolMapBean;

public interface ProductSymbolMapDao{
	public void saveProductSymbolMap(final ProductSymbolMapBean productSymbolMap) throws Exception;

	public List<ProductSymbolMapBean> getAllProductSymbolMaps() throws Exception;

	public List<ProductSymbolMapBean> getProductSymbolMapByKey(String symbol, Date start_date, String product_group) throws Exception;

	public int updateProductSymbolMap(ProductSymbolMapBean productSymbolMap) throws Exception;

	public int deleteProductSymbolMap(String symbol, Date start_date, String product_group) throws Exception;
	
	public List<String> getCurrentProductGroupList(String sender, String spread_type) throws Exception;
}
