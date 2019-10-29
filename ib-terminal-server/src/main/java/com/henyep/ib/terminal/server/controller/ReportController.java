package com.henyep.ib.terminal.server.controller;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.henyep.ib.terminal.api.dto.request.report.GetIbClientMapReportRequest;
import com.henyep.ib.terminal.api.dto.request.report.GetIbClientMapReportRequestDto;
import com.henyep.ib.terminal.api.dto.request.report.GetIbCommissionReportRequest;
import com.henyep.ib.terminal.api.dto.request.report.GetIbCommissionReportRequestDto;
import com.henyep.ib.terminal.api.dto.request.report.GetIbRebateSettingsReportRequest;
import com.henyep.ib.terminal.api.dto.request.report.GetIbRebateSettingsReportRequestDto;
import com.henyep.ib.terminal.api.dto.request.report.GetIbTreeReportRequest;
import com.henyep.ib.terminal.api.dto.request.report.GetIbTreeReportRequestDto;
import com.henyep.ib.terminal.api.dto.request.report.GetMarginInOutReportRequest;
import com.henyep.ib.terminal.api.dto.request.report.GetMarginInOutReportRequestDto;
import com.henyep.ib.terminal.api.dto.request.report.GetMarginInReportRequest;
import com.henyep.ib.terminal.api.dto.request.report.GetMarginInReportRequestDto;
import com.henyep.ib.terminal.api.dto.request.report.GetMarginOutReportRequest;
import com.henyep.ib.terminal.api.dto.request.report.GetMarginOutReportRequestDto;
import com.henyep.ib.terminal.api.dto.request.report.GetMonthlyMarginInOutReportRequest;
import com.henyep.ib.terminal.api.dto.request.report.GetMonthlyMarginInOutReportRequestDto;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.server.controller.validator.ReportValidator;
import com.henyep.ib.terminal.server.dto.security.SenderDto;
import com.henyep.ib.terminal.server.service.ReportIbTreeService;
import com.henyep.ib.terminal.server.service.ReportMarginInService;
import com.henyep.ib.terminal.server.service.ReportMarginOutService;
import com.henyep.ib.terminal.server.service.ReportService;
import com.henyep.ib.terminal.server.util.DateUtil;

@Controller
@RequestMapping("/report")
public class ReportController extends AbstractController {

	public ReportController(ReportValidator validator) {
		super(validator);
	}

	@Resource(name = "ReportService")
	private ReportService reportService;

	@Resource(name = "ReportIbTreeService")
	private ReportIbTreeService reportIbTreeService;

	@Resource(name = "ReportMarginInService")
	private ReportMarginInService reportMarginInService;
	
	@Resource(name = "ReportMarginOutService")
	private ReportMarginOutService reportMarginOutService;
	
	@Autowired
	ServletContext context; 
	
	@RequestMapping(value = "/test/getIbCommissionReport")
	// , @RequestBody @Valid GetIbCommissionReportRequest request
	public void getTestIbCommissionReport(HttpServletResponse response) {
		
		try {
			 
//			GetIbCommissionReportRequest request = new GetIbCommissionReportRequest();
//			GetIbCommissionReportRequestDto dto = new GetIbCommissionReportRequestDto();
//			dto.setBrand_code("CN");
//			dto.setHas_ib_page(true);
//			dto.setHas_client_page(false);
//			dto.setHas_ib_product_page(false);
//			dto.setHas_trade_page(false);
//			dto.setHas_client_period_page(false);
//			dto.setHas_ib_product_period_page(false);
//			dto.setHas_ib_rebate_page(false);
//			dto.setHas_ib_trade_amount_page(false);
//			
//			dto.setIb_code(null);
//			dto.setStart_date(DateUtil.str2Date("2017-08-01"));
//			dto.setEnd_date(DateUtil.str2Date("2017-08-31"));
//			
//			
//			request.setBody(dto);
//			SenderDto sender = new SenderDto();
//			sender.setSender("admin");
//			logger.info("Generate ib commission report request = " + g.toJson(request));
//			reportService.getIbCommissionReport(request, response, sender);
			
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFCellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		
			
			GetIbCommissionReportRequest request = new GetIbCommissionReportRequest();
			GetIbCommissionReportRequestDto dto = new GetIbCommissionReportRequestDto();
			dto.setBrand_code("INT");
			dto.setStart_date(DateUtil.str2Date("2018-08-01"));
			dto.setEnd_date(DateUtil.str2Date("2018-08-31"));
			//dto.setIb_code("CIMA");
			SenderDto senderDto = new SenderDto();
			senderDto.setSender("intadmin");
			request.setBody(dto);
			reportService.setIBCommissionSummaryToWorkSheet(request, workbook, style, senderDto, senderDto.getSender() + " - MTD");
			reportService.setTradeCommissionSummaryToWorkSheet(request, workbook, style, senderDto);
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String fileName = DateUtil.dateToStringByFormat(format, new Date());
			response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
			workbook.write(response.getOutputStream());
			System.out.println("Your excel file has been generated!");
			logger.info("Finish generating report");
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "/downloadLog", method = RequestMethod.POST)
	public void getIbTerminalLog(HttpServletResponse response) {
		try{
			
			String realPath = context.getRealPath("WEB-INF");
			
			OutputStream out = response.getOutputStream();
			File file = new File(realPath + "/log4jlogs/ib_terminal_server.log");
			Path path = file.toPath();
		    Files.copy(path, out);
		    response.setHeader("Content-disposition", "attachment; filename=ib_terminal_server.log");
			out.flush();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value = "/getIbCommissionReport", method = RequestMethod.POST)
	// , @RequestBody @Valid GetIbCommissionReportRequest request
	public void getIbCommissionReport(@RequestBody GetIbCommissionReportRequest request, HttpServletResponse response) throws Exception{
		
		SenderDto sender = this.getSenderDto(request);
		
		logger.info("Generate ib commission report request = " + g.toJson(request));
		try {
			reportService.getIbCommissionReport(request, response, sender);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e, e);
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/test/getMarginInOutReport")
	// , @RequestBody @Valid GetIbCommissionReportRequest request
	public void getTestMarginInOutReport(HttpServletResponse response) {
		
		try {
			 
			GetMarginInOutReportRequest request = new GetMarginInOutReportRequest();
			GetMarginInOutReportRequestDto dto = new GetMarginInOutReportRequestDto();
			dto.setBrand_code("CN");
			dto.setHas_client_margin_in_out(true);
			dto.setHas_ib_margin_in_out(true);
			
			dto.setIb_code(null);
			dto.setStart_date(DateUtil.str2Date("2017-04-01"));
			dto.setEnd_date(DateUtil.str2Date("2017-05-30"));
			
			request.setBody(dto);
			SenderDto sender = new SenderDto();
			sender.setSender("admin");
			logger.info("Generate ib commission report request = " + g.toJson(request));
			reportService.getMarginInOutReport(request, response, sender);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/getMarginInOutReport", method = RequestMethod.POST)
	public void getMarginInOutReport(@RequestBody GetMarginInOutReportRequest request, HttpServletResponse response) {
		try {
			
			SenderDto sender = getSenderDto(request);
			logger.info("Generate margin in out report = " + g.toJson(request));
			reportService.getMarginInOutReport(request, response, sender);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e,e);
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/test/getLastTradeDayCnIbCommissionReport")
	// , @RequestBody @Valid GetIbCommissionReportRequest request
	public void getLastTradeDayCnIbCommissionReport(HttpServletResponse response) {
		try {

			GetIbCommissionReportRequest request = new GetIbCommissionReportRequest();
			GetIbCommissionReportRequestDto dto = new GetIbCommissionReportRequestDto();
			dto.setBrand_code("CN");
			dto.setIb_code("10071766");

			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_MONTH, 10);

			// dto.setStart_date(DateUtil.getLastDay());
			// dto.setEnd_date(DateUtil.getLastDay());

			dto.setStart_date(cal.getTime());
			dto.setEnd_date(cal.getTime());
			dto.setHas_client_period_page(true);
			dto.setHas_client_page(true);
			dto.setHas_ib_page(true);
			dto.setHas_trade_page(true);
			dto.setHas_ib_product_page(true);
			dto.setHas_ib_product_period_page(true);

			request.setBody(dto);
			reportService.getIbCommissionReport(request, response, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e, e);
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/getIbTreeReport", method = RequestMethod.POST)
	public void getIbTreeReport(@RequestBody GetIbTreeReportRequest request, HttpServletResponse response) {
		try {
			
			SenderDto sender = getSenderDto(request);
			logger.info("Generate ib tree report = " + g.toJson(request));
			reportIbTreeService.getIbTreeReport(request, response, sender);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e,e);
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/test/getCurrentIbTreeReport")
	public void getCurrentIbTreeReport(HttpServletResponse response) {
		try {

			GetIbTreeReportRequest request = new GetIbTreeReportRequest();
			GetIbTreeReportRequestDto dto = new GetIbTreeReportRequestDto();
			dto.setTrade_date(new Date());
			dto.setBrand_code("INT");
			
			SenderDto sender = new SenderDto();
			sender.setSender("sales1");
			
			request.setBody(dto);
            reportIbTreeService.getIbTreeReport(request, response, sender);
        } catch (Exception e) {
            // TODO Auto-generated catch block
        	logger.error(e, e);
            e.printStackTrace();
        }
	}
	@RequestMapping(value = "/getMarginInReport", method = RequestMethod.POST)
	public void getMarginInReport(@RequestBody GetMarginInReportRequest request, HttpServletResponse response) {
		try {
			SenderDto sender = this.getSenderDto(request);
			logger.info("Generate margin in report = " + g.toJson(request));
			reportMarginInService.getMarginInReport(request, response, sender);
        } catch (Exception e) {
            // TODO Auto-generated catch block
        	logger.error(e, e);
            e.printStackTrace();
        }
	}

	@RequestMapping(value = "/test/getMarginInReport")
	public void getTestMarginInReport(HttpServletResponse response) {
		try {

			GetMarginInReportRequest request = new GetMarginInReportRequest();
			GetMarginInReportRequestDto dto = new GetMarginInReportRequestDto();
			dto.setBrand_code("INT");
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MONTH, 2);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			dto.setStart_date(cal.getTime());
			cal.set(Calendar.DAY_OF_MONTH, 28);
			dto.setEnd_date(cal.getTime());
			
			request.setBody(dto);
			SenderDto sender = new SenderDto();
			
			//sender.setUserRole(Constants.USER_ROLE_ADMIN);
			sender.setSender("sales1");
			logger.info("Generate margin in report = " + g.toJson(request));
			reportMarginInService.getMarginInReport(request, response, sender);
        } catch (Exception e) {
            // TODO Auto-generated catch block
        	logger.error(e, e);
            e.printStackTrace();
        }
	}
	

	@RequestMapping(value = "/getMarginOutReport", method = RequestMethod.POST)
	public void getMarginOutReport(@RequestBody GetMarginOutReportRequest request, HttpServletResponse response) {
		try {

//			GetMarginOutReportRequest request = new GetMarginOutReportRequest();
//			GetMarginOutReportRequestDto dto = new GetMarginOutReportRequestDto();
//			dto.setBrand_code("CN");
//			Calendar cal = Calendar.getInstance();
//			cal.set(Calendar.DAY_OF_MONTH, 01);
//			dto.setStart_date(cal.getTime());
//			cal.set(Calendar.DAY_OF_MONTH, 30);
//			dto.setEnd_date(cal.getTime());
			
//			request.setBody(dto);
			
			SenderDto sender = getSenderDto(request);
			logger.info("Generate margin out report = " + g.toJson(request));
			reportMarginOutService.getMarginOutReport(request, response, sender);
        } catch (Exception e) {
            // TODO Auto-generated catch block
        	logger.error(e, e);
            e.printStackTrace();
        }
	}
	
	@RequestMapping(value = "/test/getMarginOutReport")
	public void getTestMarginOutReport(HttpServletResponse response) {
		try {

			GetMarginOutReportRequest request = new GetMarginOutReportRequest();
			GetMarginOutReportRequestDto dto = new GetMarginOutReportRequestDto();
			dto.setBrand_code("INT");
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MONTH, 2);
			cal.set(Calendar.DAY_OF_MONTH, 01);
			dto.setStart_date(cal.getTime());
			cal.set(Calendar.DAY_OF_MONTH, 30);
			dto.setEnd_date(cal.getTime());
			
			request.setBody(dto);
			
			SenderDto sender = new SenderDto();
			
			//sender.setUserRole(Constants.USER_ROLE_ADMIN);
			sender.setSender("sales1");
			logger.info("Generate margin out report = " + g.toJson(request));
			reportMarginOutService.getMarginOutReport(request, response, sender);
        } catch (Exception e) {
            // TODO Auto-generated catch block
        	logger.error(e, e);
            e.printStackTrace();
        }
	}
	
	
	@RequestMapping(value = "/test/getIbRebateSettingsReport")
	public void testGetIbRebateSettingsReport(HttpServletResponse response) {
		logger.info("================= /report/test/getIbRebateSettingsReport Start =================");
		try {						
			
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			Date end_date = calendar.getTime();
			calendar.add(Calendar.DATE, -1);
			Date start_date = calendar.getTime(); 
			
			GetIbRebateSettingsReportRequest request = new GetIbRebateSettingsReportRequest();
			request.setBody(new GetIbRebateSettingsReportRequestDto());
			request.getBody().setBrand_code("INT");
			//request.getBody().setIb_code("");
			request.getBody().setStart_date(start_date);
			request.getBody().setEnd_date(end_date);
			
			SenderDto sender = new SenderDto();
			
			sender.setSender("sales1");
			
			reportService.getIbRebateSettingsReport(request, response, sender);
		} catch (Exception e) {
			logger.error(e, e);
		}
		logger.info("================= /report/test/getIbRebateSettingsReport End =================");
	}
	
	@RequestMapping(value = "/getIbRebateSettingsReport")
	public void getIbRebateSettingsReport(@RequestBody GetIbRebateSettingsReportRequest request, HttpServletResponse response) {
		logger.info("================= /report/getIbRebateSettingsReport Start =================");
		try {
			logger.info("getIbRebateSettingsReport request =" + g.toJson(request));
			SenderDto sender = this.getSenderDto(request);
			reportService.getIbRebateSettingsReport(request, response, sender);
		} catch (Exception e) {
			logger.error(e, e);
		}
		logger.info("================= /report/getIbRebateSettingsReport End =================");
	}
	
	
	@RequestMapping(value = "/test/getIbClientMapReport")
	public void testGetIbClientMapReport(HttpServletResponse response) {
		logger.info("================= /report/test/getIbClientMapReport Start =================");
		try {						
			
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			Date end_date = calendar.getTime();
			calendar.add(Calendar.DATE, -1);
			Date start_date = calendar.getTime(); 
			
			GetIbClientMapReportRequest request = new GetIbClientMapReportRequest();
			request.setBody(new GetIbClientMapReportRequestDto());
			request.getBody().setBrand_code("INT");
			//request.getBody().setIb_code("10072463");
			request.getBody().setStart_date(start_date);
			request.getBody().setEnd_date(end_date);
			
			SenderDto sender = new SenderDto();
			sender.setSender("sales1");
			
			//sender.setUserRole(Constants.USER_ROLE_SALES);
			reportService.getIbClientMapReport(request, response, sender);
		} catch (Exception e) {
			logger.error(e, e);
		}
		logger.info("================= /report/test/getIbClientMapReport End =================");
	}
	
	@RequestMapping(value = "/getIbClientMapReport")
	public void getIbClientMapReport(@RequestBody GetIbClientMapReportRequest request, HttpServletResponse response) {
		logger.info("================= /report/getIbClientMapReport Start =================");
		try {
			logger.info("getIbClientMapReport request =" + g.toJson(request));
			SenderDto sender = this.getSenderDto(request);
			reportService.getIbClientMapReport(request, response, sender);
		} catch (Exception e) {
			logger.error(e, e);
		}
		logger.info("================= /report/getIbClientMapReport End =================");
	}
	

	@RequestMapping(value = "/getMonthlyMarginInOutReport", method = RequestMethod.POST)
	public void getMonthlyMarginInOutReport(@RequestBody GetMonthlyMarginInOutReportRequest request, HttpServletResponse response) {
		logger.info("================= /report/getMonthlyMarginInOutReport Start =================");
		try {
			logger.info("getMonthlyMarginInOutReport request =" + g.toJson(request));
			SenderDto sender = this.getSenderDto(request);
			reportMarginInService.getMonthlyMarginInOutReport(request, response, sender);
		} catch (Exception e) {
			logger.error(e, e);
		}
		logger.info("================= /report/getMonthlyMarginInOutReport End =================");
	}
	
	@RequestMapping(value = "/test/getMonthlyMarginInOutReport")
	public void getTestMonthlyMarginInOutReport(HttpServletResponse response) {
		try {

			GetMonthlyMarginInOutReportRequest request = new GetMonthlyMarginInOutReportRequest();
			GetMonthlyMarginInOutReportRequestDto dto = new GetMonthlyMarginInOutReportRequestDto();
			Calendar calendar = Calendar.getInstance();
			Date end_date = calendar.getTime();
			calendar.add(Calendar.MONTH, -3);
			Date start_date = calendar.getTime();
			dto.setStart_date(start_date);
			dto.setEnd_date(end_date);
			dto.setBrand_code("CN");
			dto.setIb_codes("10072463");
			
			SenderDto sender = new SenderDto();
			sender.setSender("sales1");
			
			request.setBody(dto);
			
			
			reportMarginInService.getMonthlyMarginInOutReport(request, response, sender);
        } catch (Exception e) {
            // TODO Auto-generated catch block
        	logger.error(e, e);
            e.printStackTrace();
        }
	}
	

}

