package com.henyep.ib.terminal.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.stereotype.Service;

import com.henyep.ib.terminal.api.dto.db.MarginOutBean;
import com.henyep.ib.terminal.api.dto.request.report.GetMarginOutReportRequest;
import com.henyep.ib.terminal.server.dao.MarginOutDao;
import com.henyep.ib.terminal.server.dao.UserRolesDao;
import com.henyep.ib.terminal.server.dto.security.SenderDto;
import com.henyep.ib.terminal.server.global.Constants;
import com.henyep.ib.terminal.server.service.ReportMarginOutService;
import com.henyep.ib.terminal.server.util.DateUtil;
import com.henyep.ib.terminal.server.util.MarginOutUtil;

@Service(value = "ReportMarginOutService")
public class ReportMarginOutServiceImpl implements ReportMarginOutService {

	private final transient Log logger = LogFactory.getLog(getClass());

	@Resource(name = "MarginOutDao")
	private MarginOutDao marginOutDao;

	@Resource(name = "UserRolesDao")
	private UserRolesDao userRolesDao;
	
	@Override
	public void getMarginOutReport(GetMarginOutReportRequest request, HttpServletResponse response, SenderDto sender) throws Exception {

		logger.info("Start generating margin out report");

		HSSFWorkbook workbook = new HSSFWorkbook();

		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);

		setMarginOutToWorkSheet(request, workbook, style, sender);

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String fileName = DateUtil.dateToStringByFormat(format, new Date());
		response.setHeader("Content-disposition", "attachment; filename=" + fileName + "_marginOut.xls");
		workbook.write(response.getOutputStream());
		System.out.println("Your excel file has been generated!");
		logger.info("Finish generating margin out report");
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


	private void setMarginOutToWorkSheet(GetMarginOutReportRequest request, HSSFWorkbook workbook, HSSFCellStyle style, SenderDto sender) throws Exception {

		Date startDate = request.getBody().getStart_date();
		Date endDate = request.getBody().getEnd_date();
		String account = request.getBody().getAccount();
		String status = request.getBody().getStatus();
		String brandCode = request.getBody().getBrand_code();
		String category = request.getBody().getCategory();
		String method = request.getBody().getMethod();

		MarginOutBean exampleMarginOutBean = new MarginOutBean();
		exampleMarginOutBean.setAccount(account);
		exampleMarginOutBean.setStatus(status);
		exampleMarginOutBean.setBrand_code(brandCode);
		exampleMarginOutBean.setCategory(category);
		exampleMarginOutBean.setMethod(method);

		HSSFSheet sheet = workbook.createSheet("Margin Out");
		sheet.createFreezePane(0, 2);

		HSSFRow rowhead = sheet.createRow((short) 1);
		rowhead.createCell(0).setCellValue("Id");
		rowhead.createCell(1).setCellValue("Brand Code");
		rowhead.createCell(2).setCellValue("Category");
		rowhead.createCell(3).setCellValue("Method");
		rowhead.createCell(4).setCellValue("Account");
		rowhead.createCell(5).setCellValue("Currency");
		rowhead.createCell(6).setCellValue("Amount");
		rowhead.createCell(7).setCellValue("Account Currency");
		rowhead.createCell(8).setCellValue("Exchange Rate");
		rowhead.createCell(9).setCellValue("Account Amount");
		rowhead.createCell(10).setCellValue("Trade Date");
		rowhead.createCell(11).setCellValue("Status");
		rowhead.createCell(12).setCellValue("Comment");
		rowhead.createCell(13).setCellValue("Create User");
		rowhead.createCell(14).setCellValue("Create Time");
		rowhead.createCell(15).setCellValue("Last Update User");
		rowhead.createCell(16).setCellValue("Last Update Time");

		for (int i = 0; i <= 16; i++) {
			rowhead.getCell(i).setCellStyle(style);
		}
		
		addDateTimeRowToWorkSheet(workbook, sheet, startDate, endDate, 16);

		List<MarginOutBean> beanList = marginOutDao.getMarginOutBySalesExample(startDate, endDate, exampleMarginOutBean, sender.getSender(), brandCode);

		int i = 2;
		for (MarginOutBean bean : beanList) {
			HSSFRow row = sheet.createRow((short) i);
			row.createCell(0).setCellValue(bean.getId());
			row.createCell(1).setCellValue(bean.getBrand_code());
			row.createCell(2).setCellValue(MarginOutUtil.GetCategoryDescription(bean.getCategory()));
			row.createCell(3).setCellValue(MarginOutUtil.GetMethodDescription(bean.getMethod()));
			row.createCell(4).setCellValue(bean.getAccount());
			row.createCell(5).setCellValue(bean.getCurrency());
			row.createCell(6).setCellValue(bean.getAmount());
			row.createCell(7).setCellValue(bean.getAccount_currency());
			row.createCell(8).setCellValue(bean.getExchange_rate());
			row.createCell(9).setCellValue(bean.getAccount_amount());
			row.createCell(10).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, bean.getTrade_date()));
			row.createCell(11).setCellValue(MarginOutUtil.GetStatusDescription(bean.getStatus()));
			row.createCell(12).setCellValue(bean.getComment());
			row.createCell(13).setCellValue(bean.getCreate_user());
			if (bean.getCreate_time() != null) {
				row.createCell(14).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE_TIME, bean.getCreate_time()));
			} else {
				row.createCell(14).setCellValue("");
			}
			row.createCell(15).setCellValue(bean.getLast_update_user());
			if (bean.getLast_update_time() != null) {
				row.createCell(16).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE_TIME, bean.getLast_update_time()));
			} else {
				row.createCell(16).setCellValue("");
			}
			i++;
		}

		for (int x = 0; x < sheet.getRow(1).getPhysicalNumberOfCells(); x++) {
			sheet.autoSizeColumn(x);
		}

	}

}
