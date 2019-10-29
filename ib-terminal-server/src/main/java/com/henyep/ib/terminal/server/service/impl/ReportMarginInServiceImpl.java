package com.henyep.ib.terminal.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.stereotype.Service;

import com.henyep.ib.terminal.api.dto.db.MarginInBean;
import com.henyep.ib.terminal.api.dto.request.report.GetMarginInReportRequest;
import com.henyep.ib.terminal.api.dto.request.report.GetMonthlyMarginInOutReportRequest;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.server.dao.MarginInDao;
import com.henyep.ib.terminal.server.dao.UserRolesDao;
import com.henyep.ib.terminal.server.dto.marginInOut.MarginInOutDto;
import com.henyep.ib.terminal.server.dto.security.SenderDto;
import com.henyep.ib.terminal.server.service.ReportMarginInService;
import com.henyep.ib.terminal.server.util.DateUtil;
import com.henyep.ib.terminal.server.util.MarginInUtil;

@Service(value = "ReportMarginInService")
public class ReportMarginInServiceImpl implements ReportMarginInService {

	private final transient Log logger = LogFactory.getLog(getClass());

	@Resource(name = "MarginInDao")
	private MarginInDao marginInDao;	

	@Resource(name = "UserRolesDao")
	private UserRolesDao userRolesDao;

	@Override
	public void getMarginInReport(GetMarginInReportRequest request, HttpServletResponse response, SenderDto sender) throws Exception {

		logger.info("Start generating margin in report");

		HSSFWorkbook workbook = new HSSFWorkbook();

		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);

		setMarginInToWorkSheet(request, workbook, style, sender);

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String fileName = DateUtil.dateToStringByFormat(format, new Date());
		response.setHeader("Content-disposition", "attachment; filename=" + fileName + "_marginIn.xls");
		workbook.write(response.getOutputStream());
		System.out.println("Your excel file has been generated!");
		logger.info("Finish generating margin in report");
	}

	private void addDateTimeRowToWorkSheet(HSSFWorkbook workbook, HSSFSheet sheet, Date startDate, Date endDate, int lastColIndex){

		HSSFFont defaultFont= workbook.createFont();
	    defaultFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		HSSFCellStyle dateTimeStyle = workbook.createCellStyle();
		dateTimeStyle.setFont(defaultFont);
		
		HSSFRow rowDateTime = sheet.createRow((short) 0);
		rowDateTime.createCell(0).setCellValue("From:");
		rowDateTime.getCell(0).setCellStyle(dateTimeStyle);
		rowDateTime.createCell(1).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, startDate));
		rowDateTime.createCell(2).setCellValue("To:");
		rowDateTime.getCell(2).setCellStyle(dateTimeStyle);
		rowDateTime.createCell(3).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, endDate));
		rowDateTime.createCell(lastColIndex - 1).setCellValue("Create at:");
		rowDateTime.getCell(lastColIndex - 1).setCellStyle(dateTimeStyle);
		rowDateTime.createCell(lastColIndex).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE_TIME, new Date()));
	}

	private void setMarginInToWorkSheet(GetMarginInReportRequest request, HSSFWorkbook workbook, HSSFCellStyle style, SenderDto sender) throws Exception {

		Date startDate = request.getBody().getStart_date();
		Date endDate = request.getBody().getEnd_date();
		String account = request.getBody().getAccount();
		String status = request.getBody().getStatus();
		String brandCode = request.getBody().getBrand_code();
		String category = request.getBody().getCategory();
		MarginInBean exampleMarginInBean = new MarginInBean();
		exampleMarginInBean.setAccount(account);
		exampleMarginInBean.setStatus(status);
		exampleMarginInBean.setBrand_code(brandCode);
		exampleMarginInBean.setCategory(category);

		HSSFSheet sheet = workbook.createSheet("Margin In");
		sheet.createFreezePane(0, 2);

		
		
		HSSFRow rowhead = sheet.createRow((short) 1);
		rowhead.createCell(0).setCellValue("ID");
		rowhead.createCell(1).setCellValue("Brand Code");
		rowhead.createCell(2).setCellValue("Category");
		rowhead.createCell(3).setCellValue("Account");
		rowhead.createCell(4).setCellValue("Currency");
		rowhead.createCell(5).setCellValue("Amount");
		rowhead.createCell(6).setCellValue("Account Currency");
		rowhead.createCell(7).setCellValue("Exchange Rate");
		rowhead.createCell(8).setCellValue("Account Amount");
		rowhead.createCell(9).setCellValue("Transfer ID");
		rowhead.createCell(10).setCellValue("Trade Date");
		rowhead.createCell(11).setCellValue("Status");
		rowhead.createCell(12).setCellValue("Comment");
		rowhead.createCell(13).setCellValue("Create Time");
		rowhead.createCell(14).setCellValue("Create User");
		rowhead.createCell(15).setCellValue("Last Update Time");
		rowhead.createCell(16).setCellValue("Last Update User");
		
		for (int i = 0; i <= 16; i++) {
			rowhead.getCell(i).setCellStyle(style);
		}
		
		addDateTimeRowToWorkSheet(workbook, sheet, startDate, endDate, 16);

		List<MarginInBean> beanList = marginInDao.getMarginInByUserCodeExample(startDate, endDate, exampleMarginInBean, sender.getSender(), brandCode);		

		int i = 2;
		for (MarginInBean bean : beanList) {
			HSSFRow row = sheet.createRow((short) i);
			row.createCell(0).setCellValue(bean.getId());
			row.createCell(1).setCellValue(bean.getBrand_code());
			row.createCell(2).setCellValue(MarginInUtil.GetCategoryDescription(bean.getCategory()));
			row.createCell(3).setCellValue(bean.getAccount());
			row.createCell(4).setCellValue(bean.getCurrency());
			row.createCell(5).setCellValue(bean.getAmount());
			row.createCell(6).setCellValue(bean.getAccount_currency());
			row.createCell(7).setCellValue(bean.getExchange_rate());
			row.createCell(8).setCellValue(bean.getAccount_amount());
			row.createCell(9).setCellValue(bean.getTransfer_id());
			row.createCell(10).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, bean.getTrade_date()));
			row.createCell(11).setCellValue(MarginInUtil.GetStatusDescription(bean.getStatus()));
			row.createCell(12).setCellValue(bean.getComment());
			if (bean.getCreate_time() != null) {
				row.createCell(13).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE_TIME, bean.getCreate_time()));
			} else {
				row.createCell(13).setCellValue("");
			}
			row.createCell(14).setCellValue(bean.getCreate_user());
			if (bean.getLast_update_time() != null) {
				row.createCell(15).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE_TIME, bean.getLast_update_time()));
			} else {
				row.createCell(15).setCellValue("");
			}
			row.createCell(16).setCellValue(bean.getLast_update_user());
			i++;
		}

		for (int x = 0; x < sheet.getRow(1).getPhysicalNumberOfCells(); x++) {
			sheet.autoSizeColumn(x);
		}

	}

	@Override
	public void getMonthlyMarginInOutReport(GetMonthlyMarginInOutReportRequest request, HttpServletResponse response,
			SenderDto sender) throws Exception {
		
		logger.info("Start generating monthly margin in out report");

		HSSFWorkbook workbook = new HSSFWorkbook();

		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);

		Date startDate = request.getBody().getStart_date();
		Date endDate = request.getBody().getEnd_date();
		String ibCodeString = request.getBody().getIb_codes();
		String brandCode = request.getBody().getBrand_code();
		
		ArrayList<String> ibCodes = new ArrayList<String>();
		if(ibCodeString != null){
			ibCodes.addAll(Arrays.asList(StringUtils.split(ibCodeString,',')));
		}
		
		HSSFSheet sheet = workbook.createSheet("Margin In Out");
		sheet.createFreezePane(0, 2);
		
		HSSFRow rowhead = sheet.createRow((short) 1);
		rowhead.createCell(0).setCellValue("Brand Code");
		rowhead.createCell(1).setCellValue("IB Code");
		rowhead.createCell(2).setCellValue("Date");
		rowhead.createCell(3).setCellValue("Margin In");
		rowhead.createCell(4).setCellValue("Margin Out");
		rowhead.createCell(5).setCellValue("Balance");
		
		for (int i = 0; i <= 5; i++) {
			rowhead.getCell(i).setCellStyle(style);
		}
		addDateTimeRowToWorkSheet(workbook, sheet, startDate, endDate, 5);
		
// 
		List<MarginInOutDto> beanList = marginInDao.getMonthlyMarginInOutReport(startDate, endDate, brandCode, ibCodes);
		int i = 2;
		for (MarginInOutDto bean : beanList) {
			HSSFRow row = sheet.createRow((short) i);
			row.createCell(0).setCellValue(bean.getBrand_code());
			row.createCell(1).setCellValue(bean.getAccount());
			row.createCell(2).setCellValue(bean.getTrade_date());
			
			if(bean.getMargin_in() != null)
				row.createCell(3).setCellValue(bean.getMargin_in());
			else
				row.createCell(3).setCellValue(0);
			
			if(bean.getMargin_out() != null)
				row.createCell(4).setCellValue(bean.getMargin_out());
			else
				row.createCell(4).setCellValue(0);
			
			if(bean.getAccount_balance() != null)
				row.createCell(5).setCellValue(bean.getAccount_balance());
			else
				row.createCell(5).setCellValue(0);
			
			i++;
		}

		for (int x = 0; x < sheet.getRow(1).getPhysicalNumberOfCells(); x++) {
			sheet.autoSizeColumn(x);
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String fileName = DateUtil.dateToStringByFormat(format, new Date());
		response.setHeader("Content-disposition", "attachment; filename=" + fileName + "_marginInOut.xls");
		workbook.write(response.getOutputStream());
		System.out.println("Your excel file has been generated!");
		logger.info("Finish generating monthly margin in out report");
	}

}
