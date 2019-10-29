package test.dao;

import java.util.List;

import org.junit.Test;

import com.henyep.ib.terminal.api.dto.db.ProductSymbolMapBean;
import com.henyep.ib.terminal.server.dao.ProductSymbolMapDao;
import com.henyep.ib.terminal.server.dao.impl.ProductSymbolMapDaoImpl;

public class TestSymbolMap {

	@Test
	public void TestGetAll() throws Exception{
		
		ProductSymbolMapDao dao = new ProductSymbolMapDaoImpl();
		
		List<ProductSymbolMapBean> productList = dao.getAllProductSymbolMaps();
		
		for(ProductSymbolMapBean product : productList){
			System.out.println(product.getSymbol() + ":" + product.getProduct_group());
		}
		
	}
	
}
