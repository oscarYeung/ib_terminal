package test.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.henyep.ib.terminal.api.dto.request.ibcommission.CalculateIbCommissionRequestDto;
import com.henyep.ib.terminal.api.dto.request.ibcommission.GetIbSummaryRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.GetIbSummaryRequestDto;
import com.henyep.ib.terminal.server.service.impl.RebateServiceImpl;

public class TestGetIbSummary extends BaseTest {

	
	private RebateServiceImpl rebateServiceImpl;
	
	@Test
	public void testTimeOverlap() throws Exception{
		rebateServiceImpl = new RebateServiceImpl();
		List<Object> objs = new ArrayList<Object>();
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2016);
		cal.set(Calendar.MONTH, 10-1);
		CalculateIbCommissionRequestDto dto1 = new CalculateIbCommissionRequestDto();
		cal.set(Calendar.DAY_OF_MONTH, 25);
		dto1.setTradeDate(cal.getTime());
		cal.set(Calendar.DAY_OF_MONTH, 26);
		dto1.setTradeDate(cal.getTime());
		objs.add(dto1);
		
		CalculateIbCommissionRequestDto dto2 = new CalculateIbCommissionRequestDto();
		cal.set(Calendar.DAY_OF_MONTH, 22);
		dto2.setTradeDate(cal.getTime());
		cal.set(Calendar.DAY_OF_MONTH, 24);
		dto2.setTradeDate(cal.getTime());
		objs.add(dto2);
		
		Method getStartDateMethod = CalculateIbCommissionRequestDto.class.getMethod("getStartDate");
		Method getEndDateMethod = CalculateIbCommissionRequestDto.class.getMethod("getEndDate");
		
		boolean isOverlap = rebateServiceImpl.checkObjBeanDateTimeOverlap(objs, getStartDateMethod, getEndDateMethod);
		
		System.out.println(isOverlap);
	}
	
	
	//@Test
	public void testSuccessGetIbSummary(){
		
		String link = "/ibCommission/getTrimmedIbSummary";
		
		GetIbSummaryRequest request = new GetIbSummaryRequest();
		GetIbSummaryRequestDto dto = new GetIbSummaryRequestDto();
		
		dto.setIb_code("10072913");
		dto.setTrade_date(new Date());
		
		
		request.setBody(dto);
		System.out.println(gson.toJson(request));
		
		
		
	}
	
	
}
