package test.service;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.henyep.ib.terminal.api.dto.request.ibcommission.CalculateIbCommissionRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.CalculateIbCommissionRequestDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.CalculateIbCommissionResponse;
import com.henyep.ib.terminal.server.service.IbCommissionService;
import com.henyep.ib.terminal.server.util.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/application-test.xml")
@Configurable
public class TestIbCommissionCalculation extends BaseTest {

	@Resource(name = "IbCommissionDetailsDao_SQLMap")
	Map<String, String> map;

	@Resource(name = "IbCommissionService")
	IbCommissionService ibCommissionService;

	protected String getGsonDateFormatter() {
		return "yyyy-MM-dd";
	}

	@Test
	public void testIbCommissionCalculation() throws Exception {

		String link = "/ibCommission/calculate";
		CalculateIbCommissionRequest request = new CalculateIbCommissionRequest();
		CalculateIbCommissionRequestDto dto = new CalculateIbCommissionRequestDto();
		int month = 7;
		int year = 2016;
		Pair<Date, Date> dateRange = DateUtil.getMonthDateRange(year, month);
		dto.setTradeDate(dateRange.getKey());
		dto.setTradeDate(dateRange.getValue());
		request.setBody(dto);
		String jsonString = gson.toJson(request);

		CalculateIbCommissionResponse response = postJson(CalculateIbCommissionResponse.class, link, jsonString);

	}

}
