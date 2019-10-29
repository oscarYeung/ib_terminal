package test.other;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.henyep.ib.terminal.api.dto.db.RebateBean;
import com.henyep.ib.terminal.api.dto.db.RebateDetailsBean;
import com.henyep.ib.terminal.server.dao.ProductSymbolMapDao;
import com.henyep.ib.terminal.server.dao.RebateDao;
import com.henyep.ib.terminal.server.dao.RebateDetailsDao;
import com.henyep.ib.terminal.server.util.ProductSymbolMapUtil;
import com.henyep.ib.terminal.server.util.RebateDetailUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/application-test.xml")
@Configurable
public class TestProductSymbolMap {

	@Resource(name="ProductSymbolMapDao")
	ProductSymbolMapDao dao;
	
	@Resource(name = "RebateDetailsDao")
	protected RebateDetailsDao rebateDetailsDao;
	
	@Resource(name = "RebateDao")
	protected RebateDao rebateDao;
	
	@Test
	public void test() throws Exception{
		
		List<RebateDetailsBean> allRebateDetailsBeans = rebateDetailsDao.getAllRebateDetailss();
		List<RebateBean> allRebateBeans = rebateDao.getAllRebates();
		RebateDetailUtil rebateDetailUtil = new RebateDetailUtil(allRebateDetailsBeans, allRebateBeans, null);
		
		String rebateCode = "R10072463";
		
		List<String> availableProductGroups = rebateDetailUtil.getAvailableProductGroups(rebateCode, "Fixed");
		ProductSymbolMapUtil util = new ProductSymbolMapUtil(dao.getAllProductSymbolMaps());
		String result =  util.getProductGroup("AUDJPY", new Date(), availableProductGroups);
		assertEquals("FOREX", result);
		
		result =  util.getProductGroup("C_EURUSD", new Date(), availableProductGroups);
		assertEquals("EURUSD", result);
		
		result =  util.getProductGroup("SPT_GLD", new Date(), availableProductGroups);
		assertEquals("GOLD", result);
		
		result =  util.getProductGroup("CN300may16", new Date(), availableProductGroups);
		assertEquals("OTHER", result);
		
		result =  util.getProductGroup("US30jun16", new Date(), availableProductGroups);
		assertEquals("US30", result);
	}
	
}
