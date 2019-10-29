package test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.henyep.ib.terminal.api.dto.db.IbAccountDetailsBean;
import com.henyep.ib.terminal.api.dto.db.MarginInBean;
import com.henyep.ib.terminal.api.dto.request.marginin.UpdateMarginInRequest;
import com.henyep.ib.terminal.api.dto.request.marginin.UpdateMarginInRequestDto;
import com.henyep.ib.terminal.server.dao.IbAccountDetailsDao;
import com.henyep.ib.terminal.server.dao.MarginInDao;
import com.henyep.ib.terminal.server.global.Constants;
import com.henyep.ib.terminal.server.service.MarginInService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/application-test.xml")
@Configurable
public class TestMarginInOutService {
	
	@Resource(name = "MarginInService")
	MarginInService marginInService;
	
	@Resource(name= "MarginInDao")
	MarginInDao marginInDao;
	
	@Resource(name = "IbAccountDetailsDao")
	IbAccountDetailsDao ibAccountDetailsDao;
	
	
	@Test
	public void TestInvalidIbCode() throws Exception{		
//		assertNotNull(marginInService);
//		int fakeMarginInId = -1;
//		UpdateMarginInRequest request = new UpdateMarginInRequest();
//		UpdateMarginInRequestDto requestDto = new UpdateMarginInRequestDto();
//		requestDto.setMargin_in_id(fakeMarginInId);
//		request.setBody(requestDto);
//		Gson g = new Gson();
//		System.out.println(g.toJson(request));
//		List<String> errMsgs = marginInService.validateUpdateMarginIn(request);
//		assertEquals(1, errMsgs.size());
//		assertEquals(Constants.ERR_MARGIN_IN_ID_NOT_EXIST, errMsgs.get(0));
	}
	
	
	//@Test
	public void TestExecuteMarginIn() throws Exception{
		
//		assertNotNull(marginInService);
//		List<MarginInBean> marginInBeans = marginInDao.getAllMarginIns();
//		MarginInBean pendingMarginInBean = null;
//		for(MarginInBean marginInBean : marginInBeans){
//			
//			if(marginInBean.getStatus().equals(Constants.MARGIN_IN_STATUS_PENDING)){
//				pendingMarginInBean = marginInBean;
//				break;
//			}
//				
//		}
//		
//		assertNotNull(pendingMarginInBean);
//		List<IbAccountDetailsBean> ibAccountDetailsBeans = ibAccountDetailsDao.getIbAccountDetailsByKey(pendingMarginInBean.getAccount());
//		assertTrue(ibAccountDetailsBeans.size() > 0);
//		
//		double marginInAmount = pendingMarginInBean.getAccount_amount();
//		double oriPendingCommission = ibAccountDetailsBeans.get(0).getPending_commission();
//		double targetPendingCommission = oriPendingCommission - marginInAmount;
//		double oriAccountBal = ibAccountDetailsBeans.get(0).getAccount_balance();
//		double targetAccountBal = oriAccountBal + marginInAmount;
//		
//		
//		UpdateMarginInRequest request = new UpdateMarginInRequest();
//		UpdateMarginInRequestDto requestDto = new UpdateMarginInRequestDto();
//		requestDto.setMargin_in_id(pendingMarginInBean.getId());
//		request.setBody(requestDto);
//		Gson g = new Gson();
//		System.out.println(g.toJson(request));
//		marginInService.updateMarginIn(request);
//		ibAccountDetailsBeans = ibAccountDetailsDao.getIbAccountDetailsByKey(pendingMarginInBean.getAccount());
//		double updatedAccountBal = ibAccountDetailsBeans.get(0).getAccount_balance();
//		assertEquals(targetAccountBal, updatedAccountBal, 0.001);
//		double updatedPendingCommission = ibAccountDetailsBeans.get(0).getPending_commission();
//		assertEquals(targetPendingCommission, updatedPendingCommission, 0.001);
//		
//
//		marginInBeans = marginInDao.getMarginInByKey(pendingMarginInBean.getId());
//		assertEquals(marginInBeans.get(0).getStatus(), Constants.MARGIN_IN_STATUS_EXECUTED);
	}
	
}
