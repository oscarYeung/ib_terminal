package test.service;


import java.lang.reflect.ParameterizedType;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Configurable;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.henyep.ib.terminal.api.dto.db.IbClientRebateMapBean;
import com.henyep.ib.terminal.api.dto.db.IbTreeBean;
import com.henyep.ib.terminal.api.dto.db.MarginOutBean;
import com.henyep.ib.terminal.api.dto.db.ProductSymbolMapBean;
import com.henyep.ib.terminal.api.dto.db.RebateBean;
import com.henyep.ib.terminal.api.dto.db.RebateDetailsBean;
import com.henyep.ib.terminal.api.dto.db.SettingsPointValueBean;
import com.henyep.ib.terminal.api.dto.db.SettingsSymbolBean;
import com.henyep.ib.terminal.api.dto.request.ibcommission.CalculateIbCommissionRequest;
import com.henyep.ib.terminal.api.dto.request.ibcommission.CalculateIbCommissionRequestDto;
import com.henyep.ib.terminal.api.dto.request.marginout.ExcelUploadMarginOutRequest;
import com.henyep.ib.terminal.api.dto.request.marginout.ExcelUploadMarginOutRequestDto;
import com.henyep.ib.terminal.api.dto.request.marginout.GetMarginOutRequest;
import com.henyep.ib.terminal.api.dto.request.marginout.GetMarginOutRequestDto;
import com.henyep.ib.terminal.api.dto.request.schedule.task.BatchCalIbCommissionRequest;
import com.henyep.ib.terminal.api.dto.request.schedule.task.BatchCalIbCommissionRequestDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.CalculateIbCommissionResponseDto;
import com.henyep.ib.terminal.api.dto.response.marginout.ExcelUploadMarginOutResponseDto;
import com.henyep.ib.terminal.api.dto.response.marginout.InvalidMarginOutModel;
import com.henyep.ib.terminal.api.dto.response.schedule.task.RunCalIBCommTaskResponseDto;
import com.henyep.ib.terminal.server.adapter.MT4ServiceAdapterFactory;
import com.henyep.ib.terminal.server.controller.validator.ScheduleValidator;
import com.henyep.ib.terminal.server.dao.IbDailyFloatingDao;
import com.henyep.ib.terminal.server.dao.ClientDetailsDao;
import com.henyep.ib.terminal.server.dao.ClientGroupMappingDao;
import com.henyep.ib.terminal.server.dao.ClientTradeDetailsDao;
import com.henyep.ib.terminal.server.dao.IbClientMapDao;
import com.henyep.ib.terminal.server.dao.IbCommissionSummaryDao;
import com.henyep.ib.terminal.server.dao.IbTreeDao;
import com.henyep.ib.terminal.server.dao.ProductSymbolMapDao;
import com.henyep.ib.terminal.server.dao.RebateDao;
import com.henyep.ib.terminal.server.dao.RebateDetailsDao;
import com.henyep.ib.terminal.server.dao.ScheduleTaskDao;
import com.henyep.ib.terminal.server.dao.SettingsSymbolDao;
import com.henyep.ib.terminal.server.dao.SystemParamsDao;
import com.henyep.ib.terminal.server.dto.security.SenderDto;
import com.henyep.ib.terminal.server.factory.DtoFactory;
import com.henyep.ib.terminal.server.global.Constants;
import com.henyep.ib.terminal.server.global.EmailConfig;
import com.henyep.ib.terminal.server.processor.IbCommissionDetailsProcessor;
import com.henyep.ib.terminal.server.schedule.ScheduleTask;
import com.henyep.ib.terminal.server.service.ClientPackageDetailsService;
import com.henyep.ib.terminal.server.service.IbCommissionService;
import com.henyep.ib.terminal.server.service.MarginOutExcelUploadService;
import com.henyep.ib.terminal.server.service.MarginOutService;
import com.henyep.ib.terminal.server.service.RebateService;
import com.henyep.ib.terminal.server.service.ReportService;
import com.henyep.ib.terminal.server.util.ClientRebateMapUtil;
import com.henyep.ib.terminal.server.util.DateUtil;
import com.henyep.ib.terminal.server.util.EmailUtil;
import com.henyep.ib.terminal.server.util.IbTreeUtil;
import com.henyep.ib.terminal.server.util.ProductSymbolMapUtil;
import com.henyep.ib.terminal.server.util.RateExchangeUtil;
import com.henyep.ib.terminal.server.util.RebateDetailUtil;
import com.henyep.ib.terminal.server.util.SettingsPointValueUtil;
import com.henyep.ib.terminal.server.util.SettingsSymbolUtil;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/application-test.xml")
@Configurable
public class TestConfig {

	@Resource(name = "ClientTradeDetailsDao")
	ClientTradeDetailsDao clientTradeDetailsDao;

	@Resource(name = "scheduleTask")
	ScheduleTask scheduleTask;

	
	
	@Resource(name = "IbCommissionService")
	IbCommissionService ibCommissionService;

	@Resource(name = "IbClientMapDao")
	IbClientMapDao ibClientMapDao;

	@Resource(name = "ClientDetailsDao")
	ClientDetailsDao clientDetailsDao;
	
	@Resource(name = "SystemParamsDao")
	SystemParamsDao systemParamsDao;

	@Resource(name = "MarginOutExcelUploadService")
	MarginOutExcelUploadService marginOutExcelUploadService;
	
	@Resource(name = "IbCommissionDetailsProcessor")
	IbCommissionDetailsProcessor ibCommissionDetailsProcessor;
	
	@Resource(name = "MT4ServiceAdapterFactory")
	private MT4ServiceAdapterFactory mt4ServiceAdapterFactory;
	
	@Resource(name="ClientGroupMappingDao")
	ClientGroupMappingDao clientGroupMappingDao;	
	
	@Resource(name="RebateService")
	RebateService rebateService;
	
	@Resource(name = "EmailUtil")
	EmailUtil emailUtil;

	@Resource(name = "MarginOutService")
	private MarginOutService marginOutService;
	
	@Resource(name = "IbTreeDao")
	private IbTreeDao ibTreeDao;

	@Resource(name = "SettingsSymbolDao")
	private SettingsSymbolDao settingsSymbolDao;
	
	@Resource(name = "RateExchangeUtil")
	private RateExchangeUtil rateExchangeUtil;
	
	@Resource(name = "IbDailyFloatingDao")
	private IbDailyFloatingDao ibDailyFloatingDao;
	
	@Resource(name = "IbCommissionSummaryDao")
	private IbCommissionSummaryDao ibCommissionSummaryDao;
	
	@Resource(name = "ReportService")
	private ReportService reportService;
	
	@Resource(name = "ProductSymbolMapDao")
	private ProductSymbolMapDao productSymbolMapDao;

	@Resource(name = "ClientPackageDetailsService")
	ClientPackageDetailsService clientPackageDetailsService;
	
	@Resource(name ="ScheduleValidator")
	ScheduleValidator scheduleValidator;
	
	@Resource(name = "ScheduleTaskDao")
	ScheduleTaskDao scheduleTaskDao;
	
	@Resource
	EmailConfig emailConfig;
	
	@Resource(name = "sz_SimpleDtoFactory")
	protected DtoFactory dtoFactory;

	public boolean supports(Class<?> clazz) {
		if (clazz.equals(ArrayList.class)) {
			System.out.println(clazz.getSuperclass());
			System.out.println(((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments());
		}
		return false;
	}	
	
	
	
	@Test
	public void TestBatchScheduleTask(){
		try{
			System.out.println("Hello");
//			
//			BatchCalIbCommissionRequest request = new BatchCalIbCommissionRequest(); 
//			BatchCalIbCommissionRequestDto body = new BatchCalIbCommissionRequestDto();
//		    String date="2018-08-08";  
//		    Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
//		    date="2018-08-10";
//		    Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
//			
//			body.setStart_date(startDate);
//			body.setEnd_date(endDate);
//			body.setTeam_head("10072678");
//			request.setBody(body);
//			
//			
//			boolean isUpdatingIbComm = systemParamsDao.isUpdatingIbCommission();
//			
//			if(!isUpdatingIbComm){ 
//				
//				if(startDate.compareTo(endDate) <= 0){
//					Date tradeDate = startDate;
//					while(tradeDate.compareTo(endDate) <= 0){
//						
//						System.out.println("Delete calculate ib commission schedule task");
//						scheduleTaskDao.deleteCalIbCommTask(tradeDate);
//						System.out.println("Init calculate ib commission schedule task");
//						scheduleTaskDao.initCalIbCommScheduleTask(tradeDate);
//						String teamHead = request.getBody().getTeam_head();
//						if(teamHead != null && !teamHead.equals(""))
//							System.out.println("Calculate ib commission, Team head: " + request.getBody().getTeam_head());
//						else
//							System.out.println("Calculate ib commission");
//						RunCalIBCommTaskResponseDto dto = scheduleTask.doCalculateIbCommission(tradeDate, request.getBody().getTeam_head(), "system");
//						
//						Calendar cal = Calendar.getInstance();
//						cal.setTime(tradeDate);
//						cal.add(Calendar.DATE, 1);
//						tradeDate = cal.getTime();
//					}
//				}
//			}
			
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		
	}
	
	@Resource(name = "RebateDetailsDao")
	protected RebateDetailsDao rebateDetailsDao;
	
	
	@Resource(name = "RebateDao")
	protected RebateDao rebateDao;
	
	@Resource(name = "SettingsSymbolDao")
	protected SettingsSymbolDao settingSymbolDao;
	
	protected List<IbTreeBean> allIbTreeBeans;
	protected IbTreeUtil ibTreeUtil;

	protected List<RebateDetailsBean> allRebateDetailsBeans;
	protected List<RebateBean> allRebateBeans;
	protected RebateDetailUtil rebateDetailUtil;

	protected List<ProductSymbolMapBean> allProductSymbolMaps;
	protected ProductSymbolMapUtil productSymbolMapUtil;

	protected List<SettingsPointValueBean> allSettingsPointValueBeans;
	protected SettingsPointValueUtil settingsPointValueUtil;

	protected List<IbClientRebateMapBean> ibClientRebateMapBeans;
	protected ClientRebateMapUtil clientRebateMapUtil;

	protected List<SettingsSymbolBean> settingSymbolBeans;
	protected SettingsSymbolUtil settingSymbolUtil;
	
	//@Test
	public void Test() throws Exception{
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 01);
		cal.set(Calendar.MONTH, 4);
		Date tradeDate = cal.getTime();
		
		CalculateIbCommissionRequestDto body = new CalculateIbCommissionRequestDto();
		body.setTradeDate(tradeDate);
		CalculateIbCommissionRequest request = new CalculateIbCommissionRequest();
		request.setBody(body);
		String ibTeamHead = "888881";
		String sender = "test";
		CalculateIbCommissionResponseDto responseDto = ibCommissionService.calculateCommission(request, ibTeamHead, sender);
		
		
		StringBuilder builder = new StringBuilder();
		for(String key : responseDto.getErrorMsgs().keySet()){
			String errorMsg = responseDto.getErrorMsgs().get(key);
			builder.append(key + " " + errorMsg + "\n");
		}
		builder.append("Number of records deleted " + responseDto.getNumOfIbCommissionDeleted() + "\n"); 
		builder.append("Number of records created " + responseDto.getNumOfIbCommissionCreated() + "\n");
		System.out.println(builder.toString());
	}
	
	
	public void Test1() throws Exception{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 01);
		cal.set(Calendar.MONTH, 4);
		Date tradeDate = cal.getTime();
		
	}
	
	private void sendReportEmail(ByteArrayResource inputStream, String receiver, Date startDate, Date endDate){
		
		Map<String, Object> emailModel = new HashMap<String, Object>();
		String template = Constants.EMAIL_TEMPLATE_IB_COMMISSION_SUMMARY;
		String emailSubject = emailConfig.getIbCommissionSummaryReportSubject();
		
		emailModel.put(Constants.EMAIL_RECEIVER, receiver);
		emailModel.put(Constants.EMAIL_SUBJECT, emailSubject);
		emailModel.put(Constants.EMAIL_TEMPLATE, template);
		emailModel.put(Constants.EMAIL_BCC, "oscar.yeung@henyep.com");
		emailModel.put(Constants.EMAIL_SENDER, emailConfig.getIbCommissionSummaryReportSender());
		SimpleDateFormat dateTimeformater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentDateTimeString = DateUtil.dateToStringByFormat(dateTimeformater, new Date());
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat simpleDateFormater = new SimpleDateFormat("yyyyMMdd");
		String startDateString = DateUtil.dateToStringByFormat(dateFormater, startDate);
		String endDateString = DateUtil.dateToStringByFormat(dateFormater, endDate);
		String currentDateSimpleString = DateUtil.dateToStringByFormat(simpleDateFormater, new Date());
		emailModel.put("StartDate", startDateString);
		emailModel.put("EndDate", endDateString);
		emailModel.put("CreateReportTime", currentDateTimeString);
		emailModel.put(Constants.EMAIL_ATTACHMENT, inputStream);
		emailModel.put(Constants.EMAIL_ATTACHMENT_NAME, currentDateSimpleString + ".xls");
		
		emailUtil.sendEmail(emailModel);
	}
	
	
	
	
	public void test1() throws Exception{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 16);
		cal.set(Calendar.MONTH, 7);
		Date tradeDate = cal.getTime();
		scheduleTask.doDataSyncFromSAP(tradeDate, "oscar");
	}
	
	
	private void printBytes(byte[] bytes){
		for(byte b : bytes){
			System.out.print(b);
			System.out.print(",");
		}
		System.out.println("");
	}
	
	//@Test
	public void TestInsertIbClientMap() throws Exception{
		String filePos = "C:\\Users\\oscaryeung\\Desktop\\temp\\UploadMarginOut_template.xls";
		Path filePath = Paths.get(filePos);
		byte[] bytes = Files.readAllBytes(filePath);
		ExcelUploadMarginOutRequest request = new ExcelUploadMarginOutRequest();
		ExcelUploadMarginOutRequestDto dto = new ExcelUploadMarginOutRequestDto();
		dto.setExtension("xls");
		request.setBody(dto);
		request.getBody().setExcelBytes(bytes);
		request.getBody().setBrand_code("INT");
		SenderDto sender = new SenderDto();
		sender.setSender("tseting");
		sender.setLast_request_time(new Date());
		ExcelUploadMarginOutResponseDto body = marginOutExcelUploadService.excelUploadMarginOuts(request, sender);
		
		System.out.println("Invalid: ");
		for(InvalidMarginOutModel model : body.getInvalid_margin_outs()){
			System.out.println(model.getLine_num() + ">>" + model.getError());
		}
		
		System.out.println("");
		System.out.println("Valid: ");
		for(MarginOutBean bean : body.getValid_margin_outs()){
			System.out.println(bean.toString());
		}
		
		
	}
	

	// @Test
	public void TestRequest() throws Exception {

		GetMarginOutRequest req = new GetMarginOutRequest();
		GetMarginOutRequestDto dto = new GetMarginOutRequestDto();

		dto.setStartDate(new Date());
		dto.setEndDate(new Date());

		MarginOutBean bean = new MarginOutBean();
		bean.setStatus(Constants.MARGIN_OUT_STATUS_PENDING);
		bean.setAccount("10071766");
		bean.setBrand_code("CN");
		bean.setId(3);
		dto.setMarginOutBean(bean);

		req.setBody(dto);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		System.out.println(gson.toJson(req));
	}

}
