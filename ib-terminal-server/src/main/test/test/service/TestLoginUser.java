package test.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.henyep.ib.terminal.api.dto.db.ClientMarginInOutBean;
import com.henyep.ib.terminal.api.dto.request.client.margin.in.out.ClientMarginInOutRequest;
import com.henyep.ib.terminal.api.dto.request.client.margin.in.out.ClientMarginInOutRequestDto;
import com.henyep.ib.terminal.api.dto.response.client.margin.in.out.ClientMarginInOutResponseDto;
import com.henyep.ib.terminal.server.service.ClientMarginInOutService;
import com.henyep.ib.terminal.server.util.DateUtil;


@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/application-test.xml")
@Configurable

public class TestLoginUser {
	
	@Resource(name = "ClientMarginInOutService")
	private ClientMarginInOutService clientMarginInOutService;
	
	@Test
	public void generateJson() throws Exception{
		ClientMarginInOutRequest request = new ClientMarginInOutRequest();
		ClientMarginInOutRequestDto dto = new ClientMarginInOutRequestDto();
		dto.setClient_code("366367");
		dto.setIb_code("18002559");
		Date startDate = DateUtil.str2Date("2016-11-01");
		Date endDate = DateUtil.str2Date("2016-11-30");
		dto.setStart_date(startDate);
		dto.setEnd_date(endDate);
		request.setBody(dto);
		
		List<ClientMarginInOutBean> beans = clientMarginInOutService.getClientMarginInOut(request);
		ClientMarginInOutResponseDto response = new ClientMarginInOutResponseDto();
		response.setList(beans);
		
		Gson gson = new Gson();
		System.out.println(gson.toJson(response));
		
	}
	
	
	
}
