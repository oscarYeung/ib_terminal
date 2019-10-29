package test.dao;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.gson.Gson;
import com.henyep.ib.terminal.api.dto.db.UserBean;
import com.henyep.ib.terminal.server.dao.UserDao;
import com.henyep.ib.terminal.server.dao.impl.UserDaoImpl;

public class TestUser {
	
	@Rule
	public ExpectedException thrown= ExpectedException.none();
	@Test
	public void TestAll() throws Exception{
		
		UserBean userBean = new UserBean();
		userBean.setBrand_code("brandCode");
		userBean.setUser_code("testUser");
		userBean.setUser_name("測試 User Name");
		userBean.setPassword("Abc123456");
		userBean.setStatus("A");
		userBean.setCreate_time(new Date());
		userBean.setLast_update_user("system");
		Gson gson = new Gson();
		System.out.println(gson.toJson(userBean));
	}
	
	
	public void TestInsert(){
		
		
	}
	
	public void TestGetAllUsers() throws Exception{
		
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		System.out.println(path);
		UserDao dao = new UserDaoImpl();
		List<UserBean> userBeans = dao.getAllUsers();
		
		assertTrue(userBeans.size() > 0);
		
		for(UserBean userBean : userBeans){
			Gson gson = new Gson();
			System.out.println(gson.toJson(userBean));
		}
	}
	
	

}
