package test.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.henyep.ib.terminal.api.dto.db.IbClientMapBean;
import com.henyep.ib.terminal.server.dao.IbClientMapDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/application-test.xml")
@Configurable
public class TestIbClientMap {

	@Resource(name = "IbClientMapDao")
	IbClientMapDao ibClientMapDao;
	
	@Test
	public void Test() throws Exception{
		List<IbClientMapBean> beans = ibClientMapDao.getAllIbClientMaps();
		
		
		System.out.println(beans.size());
		
		
	}
	
}
