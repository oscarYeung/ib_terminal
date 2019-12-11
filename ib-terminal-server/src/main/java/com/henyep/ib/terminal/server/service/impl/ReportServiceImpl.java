package com.henyep.ib.terminal.server.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;

//import org.apache.poi.xssf.usermodel.XSSFFont;
import org.springframework.stereotype.Service;

import com.henyep.ib.terminal.api.dto.db.IbCommissionClientSummaryBean;
import com.henyep.ib.terminal.api.dto.db.IbCommissionDetailsBean;
import com.henyep.ib.terminal.api.dto.db.IbCommissionSummaryBean;
import com.henyep.ib.terminal.api.dto.request.report.GetIbClientMapReportRequest;
import com.henyep.ib.terminal.api.dto.request.report.GetIbCommissionReportRequest;
import com.henyep.ib.terminal.api.dto.request.report.GetIbCommissionReportRequestDto;
import com.henyep.ib.terminal.api.dto.request.report.GetIbRebateSettingsReportRequest;
import com.henyep.ib.terminal.api.dto.request.report.GetMarginInOutReportRequest;
import com.henyep.ib.terminal.server.dao.IbClientMapDao;
import com.henyep.ib.terminal.server.dao.IbCommissionClientSummaryDao;
import com.henyep.ib.terminal.server.dao.IbCommissionDetailsDao;
import com.henyep.ib.terminal.server.dao.IbCommissionSummaryDao;
import com.henyep.ib.terminal.server.dao.IbDailyFloatingDao;
import com.henyep.ib.terminal.server.dao.RebateDetailsDao;
import com.henyep.ib.terminal.server.dao.UserRolesDao;
import com.henyep.ib.terminal.server.dto.dao.ReportIbClientMapDto;
import com.henyep.ib.terminal.server.dto.dao.ReportIbRebateDto;
import com.henyep.ib.terminal.server.dto.report.ClientMarginInOutReportDto;
import com.henyep.ib.terminal.server.dto.report.ClientSummaryPeriodDto;
import com.henyep.ib.terminal.server.dto.report.ClientFloatingPNLDto;
import com.henyep.ib.terminal.server.dto.report.IbFloatingPNLDto;
import com.henyep.ib.terminal.server.dto.report.IbMarginInOutReportDto;
import com.henyep.ib.terminal.server.dto.report.IbProductGroupSummaryDto;
import com.henyep.ib.terminal.server.dto.report.IbRebateReportDto;
import com.henyep.ib.terminal.server.dto.report.IbSummaryDto;
import com.henyep.ib.terminal.server.dto.report.IbTradeAmountReportDto;
import com.henyep.ib.terminal.server.dto.report.OwnerIbDto;
import com.henyep.ib.terminal.server.dto.security.SenderDto;
import com.henyep.ib.terminal.server.global.Constants;
import com.henyep.ib.terminal.server.service.ReportService;
import com.henyep.ib.terminal.server.util.DateUtil;

@Service(value = "ReportService")
public class ReportServiceImpl implements ReportService {

	private final transient Log logger = LogFactory.getLog(getClass());

	@Resource(name = "IbCommissionSummaryDao")
	private IbCommissionSummaryDao ibCommissionSummaryDao;

	@Resource(name = "IbCommissionClientSummaryDao")
	private IbCommissionClientSummaryDao ibCommissionClientSummaryDao;

	@Resource(name = "IbCommissionDetailsDao")
	private IbCommissionDetailsDao ibCommissionDetailsDao;

	@Resource(name = "RebateDetailsDao")
	private RebateDetailsDao rebateDetailsDao;

	@Resource(name = "IbClientMapDao")
	private IbClientMapDao ibClientMapDao;

	@Resource(name = "UserRolesDao")
	private UserRolesDao userRolesDao;
	
	@Resource(name = "IbDailyFloatingDao")
	private IbDailyFloatingDao ibDailyFloatingDao;

	
	
	@Override
	public void getIbCommissionReport(GetIbCommissionReportRequest request, HttpServletResponse response, SenderDto sender) throws Exception {

		logger.info("Start generating report");

		HSSFWorkbook workbook = new HSSFWorkbook();

		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		
		HSSFCellStyle totalRowStyle = workbook.createCellStyle();
		totalRowStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		totalRowStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		if (request.getBody().isHas_ib_page()) {
			setIBCommissionSummaryToWorkSheet(request, workbook, style, sender, "IB Summary");
		}
		if (request.getBody().isHas_ib_product_page()) {
			setIBProductCommissionSummaryToWorkSheet(request, workbook, style, sender);
		}
		if (request.getBody().isHas_ib_product_period_page()) {
			setIBProductPeriodCommissionSummaryToWorkSheet(request, workbook, style, sender);
		}
		if (request.getBody().isHas_client_page()) {
			setClientCommissionSummaryToWorkSheet(request, workbook, style, sender);
		}
		if (request.getBody().isHas_client_period_page()) {
			setClientCommissionSummaryPeriodToWorkSheet(request, workbook, style, sender, "Client Summary (Period)");
		}
		if (request.getBody().isHas_trade_page()) {
			setTradeCommissionSummaryToWorkSheet(request, workbook, style, sender);
		}
		if (request.getBody().isHas_ib_rebate_page()){
			setIbRebateReportToWorkSheet(request, workbook, style, sender, totalRowStyle);
		}
		if (request.getBody().isHas_ib_trade_amount_page()){
			setIbTradeAmountReportToWorkSheet(request, workbook, style, sender, totalRowStyle);
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String fileName = DateUtil.dateToStringByFormat(format, new Date());
		response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
		workbook.write(response.getOutputStream());
		System.out.println("Your excel file has been generated!");
		logger.info("Finish generating report");
	}

	private List<String> getIbCodes(String ibCodesString) {
		ArrayList<String> ibCodes = new ArrayList<String>();
		if (ibCodesString != null && !ibCodesString.trim().equals("")) {
			String[] snipples = ibCodesString.split(",");
			for (String snipple : snipples) {
				ibCodes.add(snipple.trim());
			}
		}
		return ibCodes;
	}

	private void addDateTimeRowToWorkSheet(HSSFWorkbook workbook, HSSFSheet sheet, Date startDate, Date endDate, int lastColIndex) {

		HSSFFont defaultFont = workbook.createFont();
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

	private void addTradeDateRowToWorkSheet(HSSFWorkbook workbook, HSSFSheet sheet, Date tradeDate, int lastColIndex){

		HSSFFont defaultFont= workbook.createFont();
	    defaultFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		HSSFCellStyle dateTimeStyle = workbook.createCellStyle();
		dateTimeStyle.setFont(defaultFont);
		
		HSSFRow rowDateTime = sheet.createRow((short) 0);
		rowDateTime.createCell(0).setCellValue("Trade date:");
		rowDateTime.getCell(0).setCellStyle(dateTimeStyle);
		rowDateTime.createCell(1).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, tradeDate));
		rowDateTime.createCell(lastColIndex - 1).setCellValue("Create at:");
		rowDateTime.getCell(lastColIndex - 1).setCellStyle(dateTimeStyle);
		rowDateTime.createCell(lastColIndex).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE_TIME, new Date()));
	}
	
	@Override
	public void setSummaryToWorkSheet(HSSFWorkbook workbook, HSSFCellStyle style, Map<String, List<String>> ytdSalesSummaryDict, Map<String, List<String>> mtdSalesSummaryDict)
			throws Exception {
		logger.info("Generate summary");
		
		HSSFSheet sheet = workbook.createSheet("SUMMARY");
		workbook.setSheetOrder("SUMMARY", 0);
		
		HSSFRow rowhead = sheet.createRow((short) 2);
		rowhead.createCell(5).setCellValue("MTD");
		rowhead.createCell(13).setCellValue("YTD");
		
		rowhead = sheet.createRow((short) 4);
		rowhead.createCell(1).setCellValue("Net deposit");
		rowhead.createCell(2).setCellValue("Total commission");
		rowhead.createCell(3).setCellValue("Fixed trade P/L");
		rowhead.createCell(4).setCellValue("Floating trade P/L"); 
		rowhead.createCell(5).setCellValue("Total trade P/L");
		rowhead.createCell(6).setCellValue("Comm% of Fixed PNL");
		rowhead.createCell(7).setCellValue("Comm% of Total PNL");
		
		rowhead.createCell(9).setCellValue("Net deposit");
		rowhead.createCell(10).setCellValue("Total commission");
		rowhead.createCell(11).setCellValue("Fixed trade P/L");
		rowhead.createCell(12).setCellValue("Floating trade P/L"); 
		rowhead.createCell(13).setCellValue("Total trade P/L");
		rowhead.createCell(14).setCellValue("Comm% of Fixed PNL");
		rowhead.createCell(15).setCellValue("Comm% of Total PNL");
		
		for (int i = 1; i <= 15; i++) {
			HSSFCell cell = rowhead.getCell(i);
			if(cell != null){
				cell.setCellStyle(style);
			}
		}
		
		rowhead = sheet.createRow((short) 6);
		rowhead.createCell(0).setCellValue("ALL HYCM INT IBs");
		
		setSalesSummaryRow("shekho", 8, sheet, ytdSalesSummaryDict, mtdSalesSummaryDict);
		setSalesSummaryRow("konstantinos", 9, sheet, ytdSalesSummaryDict, mtdSalesSummaryDict);
		setSalesSummaryRow("zaher", 10, sheet, ytdSalesSummaryDict, mtdSalesSummaryDict);
		setSalesSummaryRow("ilona", 11, sheet, ytdSalesSummaryDict, mtdSalesSummaryDict);
		
		setSalesSummaryRow("muzakir", 13, sheet, ytdSalesSummaryDict, mtdSalesSummaryDict);
		setSalesSummaryRow("husam", 14, sheet, ytdSalesSummaryDict, mtdSalesSummaryDict);
		setSalesSummaryRow("fayez", 15, sheet, ytdSalesSummaryDict, mtdSalesSummaryDict);
		setSalesSummaryRow("ahmed", 16, sheet, ytdSalesSummaryDict, mtdSalesSummaryDict);
		
		
		rowhead = sheet.createRow((short) 19);
		rowhead.createCell(0).setCellValue("ALL HYCM KW IBs");
		setSalesSummaryRow("binod", 21, sheet, ytdSalesSummaryDict, mtdSalesSummaryDict);
		setSalesSummaryRow("muzammil", 22, sheet, ytdSalesSummaryDict, mtdSalesSummaryDict);
		
		
		for (int x = 0; x <= 15; x++) {
			sheet.autoSizeColumn(x);
		}
	}
	
	private void setSalesSummaryRow(String salesName, int row, HSSFSheet sheet, Map<String, List<String>> ytdSalesSummaryDict, Map<String, List<String>> mtdSalesSummaryDict){
			
		HSSFRow rowhead = sheet.createRow((short) row);
		rowhead.createCell(0).setCellValue(salesName);
		
		if(mtdSalesSummaryDict.containsKey(salesName)){
			int col = 1;
			
			for(String item: mtdSalesSummaryDict.get(salesName)){
				if(item != null){
					try {
				        double d = Double.parseDouble(item);
				        rowhead.createCell(col).setCellValue(d);
				    } catch (NumberFormatException nfe) {
				    	rowhead.createCell(col).setCellValue(item);
				    }
				}
				
				col++;
			}
		}
		
		if(ytdSalesSummaryDict.containsKey(salesName)){
			int col = 9;
			
			for(String item: ytdSalesSummaryDict.get(salesName)){
				if(item != null){
					try {
				        double d = Double.parseDouble(item);
				        rowhead.createCell(col).setCellValue(d);
				    } catch (NumberFormatException nfe) {
				    	rowhead.createCell(col).setCellValue(item);
				    }
				}
				col++;
			}
		}
	}
	
	@Override
	public List<String> setIBCommissionSummaryToWorkSheet(GetIbCommissionReportRequest request, HSSFWorkbook workbook, HSSFCellStyle style, SenderDto sender, String sheetName)
			throws Exception {
		logger.info("Generating IB summary report");
		HSSFSheet sheet = workbook.createSheet(sheetName);
		sheet.createFreezePane(0, 3);
		Date startDate = request.getBody().getStart_date();
		Date endDate = request.getBody().getEnd_date();
		
		String jurisdiction = null;
		List<String> ibCodes = new ArrayList<String>();
		if(request.getBody().getIb_code() != null && request.getBody().getIb_code().equalsIgnoreCase("cima")){
			jurisdiction = "CIMA";
		}
		else if(request.getBody().getIb_code() != null && request.getBody().getIb_code().equalsIgnoreCase("fca")){
			jurisdiction = "FCA";
		}
		else{
			ibCodes = getIbCodes(request.getBody().getIb_code());
		}
			
		
		String brandCode = request.getBody().getBrand_code();

		List<IbProductGroupSummaryDto> ibSummaryProductGroupBeanList = ibCommissionSummaryDao.getIbCommissionSummarysProductGroup(startDate, endDate, brandCode, ibCodes,
				sender.getSender(), jurisdiction);
		
		
		
		
		List<String> summaryProductGroupList = new ArrayList<String>();
		// ib_code --> product group --> total commission
		HashMap<String, HashMap<String,Double>> ibCommissionGroupByProductGroupMap = new HashMap<String, HashMap<String,Double>>();
		// init mapping
		for (IbProductGroupSummaryDto ibSummaryProductGroupBean : ibSummaryProductGroupBeanList){
			String ib_code = ibSummaryProductGroupBean.getIb_code();
			String product_group = ibSummaryProductGroupBean.getProduct_group();
			Double commission = ibSummaryProductGroupBean.getTotal_commission();
			// update product group list
			if(!summaryProductGroupList.contains(product_group)){
				summaryProductGroupList.add(product_group);
			}
			// update ib product group commission mapping
			// check contains ib code
			if(!ibCommissionGroupByProductGroupMap.containsKey(ib_code)){
				ibCommissionGroupByProductGroupMap.put(ib_code, new HashMap<String, Double>());
			}
			// check contains product group
			if(!ibCommissionGroupByProductGroupMap.get(ib_code).containsKey(product_group)){
				ibCommissionGroupByProductGroupMap.get(ib_code).put(product_group, 0.0);
			}
			// update commission
			Double ori_commission = ibCommissionGroupByProductGroupMap.get(ib_code).get(product_group);
			ibCommissionGroupByProductGroupMap.get(ib_code).put(product_group, ori_commission + commission);
		}
		logger.info("New Number of product group: " + summaryProductGroupList.size());
		
		// add column name
		HSSFRow rowhead = sheet.createRow((short) 2);
		rowhead.createCell(0).setCellValue("Brand code");
		rowhead.createCell(1).setCellValue("IB code");
		rowhead.createCell(2).setCellValue("IB First Name");
		rowhead.createCell(3).setCellValue("IB Last Name");
		rowhead.createCell(4).setCellValue("IB Owner");
		int colIdx = 5;
		for (String productGroup : summaryProductGroupList){
			
			rowhead.createCell(colIdx).setCellValue(productGroup);
			colIdx ++;
		}
		
		rowhead.createCell(colIdx + 0).setCellValue("Total lot");
		colIdx ++;
		if(request.getBody().getBrand_code().equals("CN")){
			rowhead.createCell(colIdx + 0).setCellValue("Total fix comission");
			rowhead.createCell(colIdx + 1).setCellValue("Total rebate comission");
			colIdx = colIdx + 2;
		}
		
		rowhead.createCell(colIdx + 0).setCellValue("Net deposit");
		rowhead.createCell(colIdx + 1).setCellValue("Total commission");
		rowhead.createCell(colIdx + 2).setCellValue("Fixed trade P/L");
		rowhead.createCell(colIdx + 3).setCellValue("Floating trade P/L");
		HSSFCell totalTradeCell = rowhead.createCell(colIdx + 4); 
		totalTradeCell.setCellValue("Total trade P/L");
		rowhead.createCell(colIdx + 5).setCellValue("Comm% of Fixed PNL");
		rowhead.createCell(colIdx + 6).setCellValue("Comm% of Total PNL");
		
		int totalNumOfCol = 4 + summaryProductGroupList.size() + 8;
		if(request.getBody().getBrand_code().equals("CN")){
			totalNumOfCol += 2; 
		}
		
		
		for (int i = 0; i <= totalNumOfCol; i++) {
			rowhead.getCell(i).setCellStyle(style);
		}
		// set total trade style
		Font font= workbook.createFont();
	    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    HSSFCellStyle totalTradeStyle = workbook.createCellStyle();
	    totalTradeStyle.setFont(font);
	    
	    totalTradeStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
	    totalTradeStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		totalTradeStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		totalTradeStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		totalTradeStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		totalTradeStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		totalTradeCell.setCellStyle(totalTradeStyle);
		// end
		

		

		addDateTimeRowToWorkSheet(workbook, sheet, startDate, endDate, totalNumOfCol);

		List<IbSummaryDto> beanList = ibCommissionSummaryDao.getIbCommissionSummarysByUserCode(startDate, endDate, brandCode, ibCodes, sender.getSender(), jurisdiction);
		List<IbFloatingPNLDto> floatingPNLList = ibDailyFloatingDao.getIbFloatingPNLDto(startDate, endDate);
		HashMap<String, Double> ibFloatingPnlDict = new HashMap<String, Double>(); 
		for (IbFloatingPNLDto ibFloatingPNLDto : floatingPNLList){
			if(!ibFloatingPnlDict.containsKey(ibFloatingPNLDto.getIb_code())){
				ibFloatingPnlDict.put(ibFloatingPNLDto.getIb_code(), ibFloatingPNLDto.getFloating_pnl());
			}
		}
		
		
		Double sum_total_commission = 0.0;
		Double sum_net_deposit = 0.0;
		Double sum_net_adj = 0.0;
		Double sum_total_trade_pl = 0.0;
		Double sum_total_floating_pl = 0.0;
		Double sum_fixed_PNL = 0.0;
		
		List<OwnerIbDto> ownerIbDtoList = ibCommissionSummaryDao.getOwnerIbDtos();
		
		int i = 3;
		for (IbSummaryDto bean : beanList) {
			HSSFRow row = sheet.createRow((short) i);
			row.createCell(0).setCellValue(bean.getBrand_code());
			row.createCell(1).setCellValue(bean.getIb_code());
			row.createCell(2).setCellValue(bean.getFirst_name());
			row.createCell(3).setCellValue(bean.getLast_name());
			
			// get ib owner
			String ibOwner = "";
			for(OwnerIbDto ownerIbDto : ownerIbDtoList){
				if(ownerIbDto.getIb_max_id() >= bean.getMax_id() && ownerIbDto.getIb_min_id() <= bean.getMin_id()){
					ibOwner = ownerIbDto.getUser_code();
					break;
				}
			}
			row.createCell(4).setCellValue(ibOwner);
			
			colIdx = 5;
			for (String productGroup : summaryProductGroupList){
				
				row.createCell(colIdx).setCellValue(0);
				if(ibCommissionGroupByProductGroupMap.containsKey(bean.getIb_code())){
					if(ibCommissionGroupByProductGroupMap.get(bean.getIb_code()).containsKey(productGroup)){
						Double productGroupCommission = ibCommissionGroupByProductGroupMap.get(bean.getIb_code()).get(productGroup);
						row.createCell(colIdx).setCellValue(productGroupCommission);
					}
				}
				colIdx ++;
			}
			
			
			row.createCell(colIdx + 0).setCellValue(bean.getTotal_lot());
			colIdx += 1;
			if(request.getBody().getBrand_code().equals("CN")){
				
				row.createCell(colIdx + 0).setCellValue(bean.getTotal_fix_commission());
				row.createCell(colIdx + 1).setCellValue(bean.getTotal_rebate_commission_lot());
				colIdx = colIdx + 2;
			}
			
			row.createCell(colIdx + 0).setCellValue(bean.getNet_deposit());
			row.createCell(colIdx + 1).setCellValue(bean.getTotal_commission());
			Double fixedPNL = bean.getTotal_trade_pl() + bean.getTotal_fix_commission() + bean.getTotal_trade_swaps() + bean.getNet_adj();
			row.createCell(colIdx + 2).setCellValue(fixedPNL);
			Double floatingPNL = 0.0;
			if(ibFloatingPnlDict.containsKey(bean.getIb_code())){
				floatingPNL = ibFloatingPnlDict.get(bean.getIb_code()); 
			}
			row.createCell(colIdx + 3).setCellValue(floatingPNL);
			
			row.createCell(colIdx + 4).setCellValue(fixedPNL + floatingPNL);
			Double commissPrecentFixedPNL = bean.getTotal_commission() / (fixedPNL) * 100 * -1;
			row.createCell(colIdx + 5).setCellValue(String.format("%.2f", commissPrecentFixedPNL) + "%");
			Double commissPrecentPNL = bean.getTotal_commission() / (fixedPNL + floatingPNL) * 100 * -1;
			row.createCell(colIdx + 6).setCellValue(String.format("%.2f", commissPrecentPNL) + "%");
			
			i++;
			sum_total_commission += bean.getTotal_commission();
			sum_net_deposit += bean.getNet_deposit();
			sum_net_adj += bean.getNet_adj();
			sum_total_floating_pl += floatingPNL;
			sum_fixed_PNL += fixedPNL;
			sum_total_trade_pl += fixedPNL + floatingPNL;
		}
		
		Double totalCommissPrecentFixedPnl = sum_total_commission / sum_fixed_PNL * 100 * -1;
		Double totalCommissPrecentPnl = sum_total_commission / sum_total_trade_pl * 100 * -1;
		
		HSSFRow totalRow = sheet.createRow((short) 1);
		int fixedColSize = 5;
		if(request.getBody().getBrand_code().equals("CN")){
			fixedColSize += 2;
		}
		totalRow.createCell(fixedColSize + summaryProductGroupList.size() + 1).setCellValue(sum_net_deposit);
		totalRow.createCell(fixedColSize + summaryProductGroupList.size() + 2).setCellValue(sum_total_commission);
		totalRow.createCell(fixedColSize + summaryProductGroupList.size() + 3).setCellValue(sum_fixed_PNL);
		totalRow.createCell(fixedColSize+ summaryProductGroupList.size() + 4).setCellValue(sum_total_floating_pl);
		totalRow.createCell(fixedColSize + summaryProductGroupList.size() + 5).setCellValue(sum_total_trade_pl);
		totalRow.createCell(fixedColSize + summaryProductGroupList.size() + 6).setCellValue(String.format("%.2f",totalCommissPrecentFixedPnl) + "%");
		totalRow.createCell(fixedColSize + summaryProductGroupList.size() + 7).setCellValue(String.format("%.2f",totalCommissPrecentPnl) + "%");
		
		List<String> summaryList = new ArrayList<String>();
		summaryList.add(sum_net_deposit.toString());
		summaryList.add(sum_total_commission.toString());
		summaryList.add(sum_fixed_PNL.toString());
		summaryList.add(sum_total_floating_pl.toString());
		summaryList.add(sum_total_trade_pl.toString());
		summaryList.add(String.format("%.2f",totalCommissPrecentFixedPnl) + "%");
		summaryList.add(String.format("%.2f",totalCommissPrecentPnl) + "%");
		
		for (int x = 0; x < sheet.getRow(2).getPhysicalNumberOfCells(); x++) {
			sheet.autoSizeColumn(x);
		}
		
		return summaryList;
	}
	

	private void setIBProductCommissionSummaryToWorkSheet(GetIbCommissionReportRequest request, HSSFWorkbook workbook, HSSFCellStyle style,
			SenderDto sender) throws Exception {
		logger.info("Generating IB product summary (Daily) report");
		HSSFSheet sheet = workbook.createSheet("IB Product Summary (Daily)");
		sheet.createFreezePane(0, 2);
		Date startDate = request.getBody().getStart_date();
		Date endDate = request.getBody().getEnd_date();
		List<String> ibCodes = getIbCodes(request.getBody().getIb_code());
		String brandCode = request.getBody().getBrand_code();

		HSSFRow rowhead = sheet.createRow((short) 1);
		rowhead.createCell(0).setCellValue("Brand code");
		rowhead.createCell(1).setCellValue("Platform");
		rowhead.createCell(2).setCellValue("IB code");
		rowhead.createCell(3).setCellValue("Product group");
		rowhead.createCell(4).setCellValue("Trade date");
		rowhead.createCell(5).setCellValue("Currency");
		rowhead.createCell(6).setCellValue("Total lot");
		rowhead.createCell(7).setCellValue("Total fix commission");
		rowhead.createCell(8).setCellValue("Total spread commission");
		rowhead.createCell(9).setCellValue("Total rebate commission lot");
		rowhead.createCell(10).setCellValue("Total rebate commission pip");
		rowhead.createCell(11).setCellValue("Total ev commission");
		rowhead.createCell(12).setCellValue("Total rev commission");
		rowhead.createCell(13).setCellValue("Total commission");
		rowhead.createCell(14).setCellValue("Total trade pl");
		rowhead.createCell(15).setCellValue("Net deposit");
		rowhead.createCell(16).setCellValue("Last update user");
		rowhead.createCell(17).setCellValue("Last update time");

		for (int i = 0; i <= 17; i++) {
			rowhead.getCell(i).setCellStyle(style);
		}
		addDateTimeRowToWorkSheet(workbook, sheet, startDate, endDate, 17);

		List<IbCommissionSummaryBean> beanList = ibCommissionSummaryDao.getIbCommissionSummarysByBrandCodeIbCodeDateRange(startDate, endDate,
				brandCode, ibCodes, sender.getSender());

		int i = 2;
		for (IbCommissionSummaryBean bean : beanList) {
			HSSFRow row = sheet.createRow((short) i);
			row.createCell(0).setCellValue(bean.getBrand_code());
			row.createCell(1).setCellValue(bean.getPlatform());
			row.createCell(2).setCellValue(bean.getIb_code());
			row.createCell(3).setCellValue(bean.getProduct_group());
			row.createCell(4).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, bean.getTrade_date()));
			row.createCell(5).setCellValue(bean.getCurrency());
			row.createCell(6).setCellValue(bean.getTotal_lot());
			row.createCell(7).setCellValue(bean.getTotal_fix_commission());
			row.createCell(8).setCellValue(bean.getTotal_spread_commission());
			row.createCell(9).setCellValue(bean.getTotal_rebate_commission_lot());
			row.createCell(10).setCellValue(bean.getTotal_rebate_commission_pip());
			row.createCell(11).setCellValue(bean.getTotal_ev_commission());
			row.createCell(12).setCellValue(bean.getTotal_rev_commission());
			row.createCell(13).setCellValue(bean.getTotal_commission());
			row.createCell(14).setCellValue(bean.getTotal_trade_pl());
			row.createCell(15).setCellValue(bean.getNet_deposit());
			row.createCell(16).setCellValue(bean.getLast_update_user());
			row.createCell(17).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE_TIME, bean.getLast_update_time()));
			i++;
		}

		for (int x = 0; x < sheet.getRow(1).getPhysicalNumberOfCells(); x++) {
			sheet.autoSizeColumn(x);
		}

	}

	private void setIBProductPeriodCommissionSummaryToWorkSheet(GetIbCommissionReportRequest request, HSSFWorkbook workbook, HSSFCellStyle style,
			SenderDto sender) throws Exception {
		logger.info("Generating IB product summary (Period) report");
		HSSFSheet sheet = workbook.createSheet("IB Product Summary (Period)");
		sheet.createFreezePane(0, 2);
		Date startDate = request.getBody().getStart_date();
		Date endDate = request.getBody().getEnd_date();
		List<String> ibCodes = getIbCodes(request.getBody().getIb_code());
		String brandCode = request.getBody().getBrand_code();

		HSSFRow rowhead = sheet.createRow((short) 1);
		rowhead.createCell(0).setCellValue("Brand code");
		rowhead.createCell(1).setCellValue("Platform");
		rowhead.createCell(2).setCellValue("IB code");
		rowhead.createCell(3).setCellValue("Product group");
		rowhead.createCell(4).setCellValue("Currency");
		rowhead.createCell(5).setCellValue("Total lot");
		rowhead.createCell(6).setCellValue("Total fix commission");
		rowhead.createCell(7).setCellValue("Total spread commission");
		rowhead.createCell(8).setCellValue("Total rebate commission lot");
		rowhead.createCell(9).setCellValue("Total rebate commission pip");
		rowhead.createCell(10).setCellValue("Total ev commission");
		rowhead.createCell(11).setCellValue("Total rev commission");
		rowhead.createCell(12).setCellValue("Total commission");
		rowhead.createCell(13).setCellValue("Total trade pl");
		rowhead.createCell(14).setCellValue("Net deposit");

		for (int i = 0; i <= 14; i++) {
			rowhead.getCell(i).setCellStyle(style);
		}
		addDateTimeRowToWorkSheet(workbook, sheet, startDate, endDate, 14);

		List<IbCommissionSummaryBean> beanList = ibCommissionSummaryDao.getIbCommissionPeriodSummary(startDate, endDate, brandCode, ibCodes,
				sender.getSender());

		int i = 2;
		for (IbCommissionSummaryBean bean : beanList) {
			HSSFRow row = sheet.createRow((short) i);
			row.createCell(0).setCellValue(bean.getBrand_code());
			row.createCell(1).setCellValue(bean.getPlatform());
			row.createCell(2).setCellValue(bean.getIb_code());
			row.createCell(3).setCellValue(bean.getProduct_group());
			row.createCell(4).setCellValue(bean.getCurrency());
			row.createCell(5).setCellValue(bean.getTotal_lot());
			row.createCell(6).setCellValue(bean.getTotal_fix_commission());
			row.createCell(7).setCellValue(bean.getTotal_spread_commission());
			row.createCell(8).setCellValue(bean.getTotal_rebate_commission_lot());
			row.createCell(9).setCellValue(bean.getTotal_rebate_commission_pip());
			row.createCell(10).setCellValue(bean.getTotal_ev_commission());
			row.createCell(11).setCellValue(bean.getTotal_rev_commission());
			row.createCell(12).setCellValue(bean.getTotal_commission());
			row.createCell(13).setCellValue(bean.getTotal_trade_pl());
			row.createCell(14).setCellValue(bean.getNet_deposit());
			i++;
		}

		for (int x = 0; x < sheet.getRow(1).getPhysicalNumberOfCells(); x++) {
			sheet.autoSizeColumn(x);
		}

	}

	private void setClientCommissionSummaryToWorkSheet(GetIbCommissionReportRequest request, HSSFWorkbook workbook, HSSFCellStyle style,
			SenderDto sender) throws Exception {

		logger.info("Generating client summary (Daily) report");
		HSSFSheet sheet = workbook.createSheet("Client Summary (Daily)");
		sheet.createFreezePane(0, 2);
		Date startDate = request.getBody().getStart_date();
		Date endDate = request.getBody().getEnd_date();
		String brandCode = request.getBody().getBrand_code();
		List<String> ibCodes = getIbCodes(request.getBody().getIb_code());

		HSSFRow rowhead = sheet.createRow((short) 1);
		rowhead.createCell(0).setCellValue("Brand code");
		rowhead.createCell(1).setCellValue("Platform");
		rowhead.createCell(2).setCellValue("IB code");
		rowhead.createCell(3).setCellValue("Client code");
		rowhead.createCell(4).setCellValue("Client IB code");
		rowhead.createCell(5).setCellValue("Product group");
		rowhead.createCell(6).setCellValue("Trade date");
		rowhead.createCell(7).setCellValue("Currency");
		rowhead.createCell(8).setCellValue("Total lot");
		rowhead.createCell(9).setCellValue("Total fix commission");
		rowhead.createCell(10).setCellValue("Total spread commission");
		rowhead.createCell(11).setCellValue("Total rebate commission lot");
		rowhead.createCell(12).setCellValue("Total rebate commission pip");
		rowhead.createCell(13).setCellValue("Total commission");
		rowhead.createCell(14).setCellValue("Net deposit");
		rowhead.createCell(15).setCellValue("Total trade P/L");
		rowhead.createCell(16).setCellValue("Total trade swaps");
		rowhead.createCell(17).setCellValue("Last update user");
		rowhead.createCell(18).setCellValue("Last update time");

		for (int i = 0; i <= 18; i++) {
			rowhead.getCell(i).setCellStyle(style);
		}
		addDateTimeRowToWorkSheet(workbook, sheet, startDate, endDate, 18);

		List<IbCommissionClientSummaryBean> beanList = ibCommissionClientSummaryDao.getIbCommissionClientSummaryByBrandCodeIbCodeDateRange(startDate,
				endDate, brandCode, ibCodes, sender.getSender());

		int i = 2;
		for (IbCommissionClientSummaryBean bean : beanList) {
			HSSFRow row = sheet.createRow((short) i);

			row.createCell(0).setCellValue(bean.getBrand_code());
			row.createCell(1).setCellValue(bean.getPlatform());
			row.createCell(2).setCellValue(bean.getIb_code());
			row.createCell(3).setCellValue(bean.getClient_code());
			row.createCell(4).setCellValue(bean.getClient_ib_code());
			row.createCell(5).setCellValue(bean.getProduct_group());
			row.createCell(6).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, bean.getTrade_date()));
			row.createCell(7).setCellValue(bean.getCurrency());
			row.createCell(8).setCellValue(bean.getTotal_lot());
			row.createCell(9).setCellValue(bean.getTotal_fix_commission());
			row.createCell(10).setCellValue(bean.getTotal_spread_commission());
			row.createCell(11).setCellValue(bean.getTotal_rebate_commission_lot());
			row.createCell(12).setCellValue(bean.getTotal_rebate_commission_pip());
			row.createCell(13).setCellValue(bean.getTotal_commission());
			row.createCell(14).setCellValue(bean.getNet_deposit());
			row.createCell(15).setCellValue(bean.getTotal_trade_pl());
			row.createCell(16).setCellValue(bean.getTotal_trade_swaps());
			row.createCell(17).setCellValue(bean.getLast_update_user());
			row.createCell(18).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE_TIME, bean.getLast_update_time()));
			i++;
		}

		for (int x = 0; x < sheet.getRow(1).getPhysicalNumberOfCells(); x++) {
			sheet.autoSizeColumn(x);
		}

	}

	public void setClientCommissionSummaryPeriodToWorkSheet(GetIbCommissionReportRequest request, HSSFWorkbook workbook, HSSFCellStyle style,
			SenderDto sender, String sheetName) throws Exception {

		logger.info("Generating client summary (Period) report");
		HSSFSheet sheet = workbook.createSheet(sheetName);
		sheet.createFreezePane(0, 2);
		Date startDate = request.getBody().getStart_date();
		Date endDate = request.getBody().getEnd_date();
		String brandCode = request.getBody().getBrand_code();
		List<String> ibCodes = getIbCodes(request.getBody().getIb_code());

		HSSFRow rowhead = sheet.createRow((short) 1);
		rowhead.createCell(0).setCellValue("Brand code");
		rowhead.createCell(1).setCellValue("Platform");
		rowhead.createCell(2).setCellValue("IB code");
		rowhead.createCell(3).setCellValue("Client code");
		rowhead.createCell(4).setCellValue("Client IB code");
		rowhead.createCell(5).setCellValue("Currency");
		rowhead.createCell(6).setCellValue("Total lot");
		rowhead.createCell(7).setCellValue("Total fix commission");
		rowhead.createCell(8).setCellValue("Total spread commission");
		rowhead.createCell(9).setCellValue("Total rebate commission lot");
		rowhead.createCell(10).setCellValue("Total rebate commission pip");
		rowhead.createCell(11).setCellValue("Total commission");
		rowhead.createCell(12).setCellValue("Net deposit");
		//rowhead.createCell(13).setCellValue("Total trade P/L");
		//rowhead.createCell(14).setCellValue("Total trade swaps");
		rowhead.createCell(13).setCellValue("Fixed trade P/L");
		rowhead.createCell(14).setCellValue("Floating trade P/L");
		rowhead.createCell(15).setCellValue("Total trade P/L");

		
		
		for (int i = 0; i <= 15; i++) {
			rowhead.getCell(i).setCellStyle(style);
		}

		addDateTimeRowToWorkSheet(workbook, sheet, startDate, endDate, 14);

		List<ClientSummaryPeriodDto> beanList = ibCommissionClientSummaryDao.getIbCommissionClientPeriodSummary(startDate, endDate, brandCode,
				ibCodes, sender.getSender());

		List<ClientFloatingPNLDto> floatingPNLList = ibDailyFloatingDao.getIbClientFloatingPNLDto(startDate, endDate);
		HashMap<String, Double> ibFloatingPnlDict = new HashMap<String, Double>(); 
		for (ClientFloatingPNLDto ibClientFloatingPNLDto : floatingPNLList){
			if(!ibFloatingPnlDict.containsKey(ibClientFloatingPNLDto.getAccount_number())){
				ibFloatingPnlDict.put(ibClientFloatingPNLDto.getAccount_number(), ibClientFloatingPNLDto.getFloating_pnl());
			}
		}
		
		int i = 2;
		for (ClientSummaryPeriodDto bean : beanList) {
			HSSFRow row = sheet.createRow((short) i);

			row.createCell(0).setCellValue(bean.getBrand_code());
			row.createCell(1).setCellValue(bean.getPlatform());
			row.createCell(2).setCellValue(bean.getIb_code());
			row.createCell(3).setCellValue(bean.getClient_code());
			row.createCell(4).setCellValue(bean.getClient_ib_code());
			row.createCell(5).setCellValue(bean.getCurrency());
			row.createCell(6).setCellValue(bean.getTotal_lot());
			row.createCell(7).setCellValue(bean.getTotal_fix_commission());
			row.createCell(8).setCellValue(bean.getTotal_spread_commission());
			row.createCell(9).setCellValue(bean.getTotal_rebate_commission_lot());
			row.createCell(10).setCellValue(bean.getTotal_rebate_commission_pip());
			row.createCell(11).setCellValue(bean.getTotal_commission());
			row.createCell(12).setCellValue(bean.getNet_deposit());

			// fixed PNL
			Double fixedPNL = bean.getTotal_trade_pl() + bean.getTotal_fix_commission() + bean.getTotal_trade_swaps() + bean.getNet_adj();
			row.createCell(13).setCellValue(fixedPNL);
			
			// floating PNL
			Double floatingPNL = 0.0;
			if(bean.getIb_code().equals(bean.getClient_ib_code()) && ibFloatingPnlDict.containsKey(bean.getClient_code())){
				floatingPNL = ibFloatingPnlDict.get(bean.getClient_code()); 
			}
			row.createCell(14).setCellValue(floatingPNL);
			
			// total trade PNL
			row.createCell(15).setCellValue(floatingPNL + fixedPNL);
			i++;
		}

		for (int x = 0; x < sheet.getRow(1).getPhysicalNumberOfCells(); x++) {
			sheet.autoSizeColumn(x);
		}

	}

	public void setTradeCommissionSummaryToWorkSheet(GetIbCommissionReportRequest request, HSSFWorkbook workbook, HSSFCellStyle style,
			SenderDto sender) throws Exception {

		logger.info("Generating trade details report");
		HSSFSheet sheet = workbook.createSheet("Trade Details");
		sheet.createFreezePane(0, 2);
		Date startDate = request.getBody().getStart_date();
		Date endDate = request.getBody().getEnd_date();
		String brandCode = request.getBody().getBrand_code();
		List<String> ibCodes = getIbCodes(request.getBody().getIb_code());

		HSSFRow rowhead = sheet.createRow((short) 1);
		rowhead.createCell(0).setCellValue("Brand code");
		rowhead.createCell(1).setCellValue("Platform");
		rowhead.createCell(2).setCellValue("Ticket");
		rowhead.createCell(3).setCellValue("IB code");
		rowhead.createCell(4).setCellValue("Client code");
		rowhead.createCell(5).setCellValue("Client IB code");
		rowhead.createCell(6).setCellValue("Product group");
		rowhead.createCell(7).setCellValue("Spread type");
		rowhead.createCell(8).setCellValue("Jurisdiction");
		rowhead.createCell(9).setCellValue("Trade date");
		rowhead.createCell(10).setCellValue("Symbol");
		rowhead.createCell(11).setCellValue("Buy/Sell");
		rowhead.createCell(12).setCellValue("Lot");
		rowhead.createCell(13).setCellValue("Currency");
		rowhead.createCell(14).setCellValue("Deposit");
		rowhead.createCell(15).setCellValue("Client fix commission");
		rowhead.createCell(16).setCellValue("Client spread commission");
		rowhead.createCell(17).setCellValue("Rebate commission lot");
		rowhead.createCell(18).setCellValue("Rebate commission pip");
		rowhead.createCell(19).setCellValue("Trade swaps");
		rowhead.createCell(20).setCellValue("Trade P/L");
		rowhead.createCell(21).setCellValue("Open trade time");
		rowhead.createCell(22).setCellValue("Close trade time");
		rowhead.createCell(23).setCellValue("Rebate code");
		rowhead.createCell(24).setCellValue("Rebate lot type");
		rowhead.createCell(25).setCellValue("Rebate per lot");
		rowhead.createCell(26).setCellValue("Rebate pip type");
		rowhead.createCell(27).setCellValue("Rebate per pip");
		rowhead.createCell(28).setCellValue("Last update user");
		rowhead.createCell(29).setCellValue("Last update time");

		for (int i = 0; i <= 29; i++) {
			rowhead.getCell(i).setCellStyle(style);
		}

		addDateTimeRowToWorkSheet(workbook, sheet, startDate, endDate, 29);

		List<IbCommissionDetailsBean> beanList = ibCommissionDetailsDao.getSummaryByBrandCodeIbCodeDateRange(startDate, endDate, brandCode, ibCodes,
				sender.getSender());

		int i = 2;
		for (IbCommissionDetailsBean bean : beanList) {
			HSSFRow row = sheet.createRow((short) i);

			row.createCell(0).setCellValue(bean.getBrand_code());
			row.createCell(1).setCellValue(bean.getPlatform());
			row.createCell(2).setCellValue(bean.getTicket());
			row.createCell(3).setCellValue(bean.getIb_code());
			row.createCell(4).setCellValue(bean.getClient_code());
			row.createCell(5).setCellValue(bean.getClient_ib_code());
			row.createCell(6).setCellValue(bean.getProduct_group());
			row.createCell(7).setCellValue(bean.getSpread_type());
			row.createCell(8).setCellValue(bean.getJurisdiction());
			row.createCell(9).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, bean.getTrade_date()));
			row.createCell(10).setCellValue(bean.getSymbol());
			row.createCell(11).setCellValue(bean.getBuy_sell());
			row.createCell(12).setCellValue(bean.getLot());
			row.createCell(13).setCellValue(bean.getCurrency());
			row.createCell(14).setCellValue(bean.getDeposit());
			row.createCell(15).setCellValue(bean.getClient_fix_commission());
			row.createCell(16).setCellValue(bean.getClient_spread_commission());
			row.createCell(17).setCellValue(bean.getRebate_commission_lot());
			row.createCell(18).setCellValue(bean.getRebate_commission_pip());
			row.createCell(19).setCellValue(bean.getTrade_swaps());
			row.createCell(20).setCellValue(bean.getTrade_pl());
			row.createCell(21).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE_TIME, bean.getOpen_trade_time()));
			row.createCell(22).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE_TIME, bean.getClose_trade_time()));
			row.createCell(23).setCellValue(bean.getRebate_code());
			row.createCell(24).setCellValue(bean.getRebate_type_lot());
			row.createCell(25).setCellValue(bean.getRebate_per_lot());
			row.createCell(26).setCellValue(bean.getRebate_type_pip());
			row.createCell(27).setCellValue(bean.getRebate_per_pip());
			row.createCell(28).setCellValue(bean.getLast_update_user());
			row.createCell(29).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE_TIME, bean.getLast_update_time()));
			i++;
		}

		for (int x = 0; x < sheet.getRow(1).getPhysicalNumberOfCells(); x++) {
			sheet.autoSizeColumn(x);
		}

	}

	private HSSFWorkbook populateWorkSheet(String sheetName, String[] columns, List<String[]> dataList, Date startDate, Date endDate) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(sheetName);
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);

		sheet.createFreezePane(0, 2);
		
		addDateTimeRowToWorkSheet(workbook, sheet, startDate, endDate, columns.length - 1);
		
		// populate header
		HSSFRow header = sheet.createRow((short) 1);
		Integer count = 0;
		for (String column : columns) {
			header.createCell(count).setCellValue(column);
			// set style
			header.getCell(count).setCellStyle(style);
			count += 1;
		}
		// populate data
		count = 2; // Start index = 1
		for (String[] data : dataList) {
			HSSFRow row = sheet.createRow(count);
			for (int i = 0; i < data.length; i++) {
				row.createCell(i).setCellValue(data[i]);
			}
			count += 1;
		}

		for (int x = 0; x < sheet.getRow(1).getPhysicalNumberOfCells(); x++) {
			sheet.autoSizeColumn(x);
		}

		return workbook;
	}

	private void populateResponse(HSSFWorkbook workbook, HttpServletResponse response) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat(Constants.FORMAT_DATETIME_DETAILS);
		String fileName = DateUtil.dateToStringByFormat(format, new Date());
		response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
		workbook.write(response.getOutputStream());
	}

	private String[] getProperties(final Object obj, String[] columns) {
		String[] data = new String[columns.length];
		try {
			int count = 0;
			for (String col : columns) {
				Object value = PropertyUtils.getProperty(obj, col);
				data[count] = value != null ? value.toString() : "";
				count += 1;
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	// private Map<String, String> getProperties(final Object obj) {
	// final Map<String, String> allProperties = new TreeMap<String, String>();
	// try {
	// final BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
	// for (final PropertyDescriptor descriptor :
	// beanInfo.getPropertyDescriptors()) {
	// try {
	// final Object propertyValue = descriptor.getReadMethod().invoke(obj);
	// allProperties.put(descriptor.getName(), propertyValue != null ?
	// propertyValue.toString() : "");
	// } catch (final IllegalArgumentException e) {
	// // handle this please
	// logger.error(e, e);
	// } catch (final IllegalAccessException e) {
	// // and this also
	// logger.error(e, e);
	// } catch (final InvocationTargetException e) {
	// // and this, too
	// logger.error(e, e);
	// }
	// }
	// } catch (final IntrospectionException e) {
	// // do something sensible here
	// }
	// return allProperties;
	// }

	// private String[] getPropertiesFieldName(Object model) {
	// List<String> list = new ArrayList<String>();
	// String[] columns = null;
	// try {
	// final BeanInfo beanInfo = Introspector.getBeanInfo(model.getClass());
	//
	// for (final PropertyDescriptor descriptor :
	// beanInfo.getPropertyDescriptors()) {
	// list.add(descriptor.getName());
	// }
	// columns = new String[list.size()];
	// columns = list.toArray(columns);
	// } catch (Exception e) {
	// logger.error(e, e);
	// }
	// return columns;
	// }

	@Override
	public void getIbRebateSettingsReport(GetIbRebateSettingsReportRequest request, HttpServletResponse response, SenderDto sender) throws Exception {

		logger.info("getIbRebateSettingsReport Start");
		ReportIbRebateDto dto = new ReportIbRebateDto();
		dto.setStart_date(request.getBody().getStart_date());
		dto.setEnd_date(request.getBody().getEnd_date());
		dto.setIb_code(request.getBody().getIb_code());
		dto.setBrand_code(request.getBody().getBrand_code());

		List<ReportIbRebateDto> list = rebateDetailsDao.getReportByUserExample(dto, sender.getSender(), request.getBody().getBrand_code());

		List<String[]> data = new ArrayList<String[]>();
		String[] columns = new String[] { "brand_code", "ib_code", "product_group", "client_package_code", "currency", "min_lot",
				"client_fix_commission_type", "client_fix_commission", "rebate_type", "rebate_commission", "start_date", "end_date",
				"last_update_time", "last_update_user" };

		if (list != null && list.size() > 0) {
			for (ReportIbRebateDto model : list) {
				data.add(getProperties(model, columns));
			}
		}
		if (columns != null && data != null) {
			HSSFWorkbook workbook = populateWorkSheet("Rebate Details", columns, data, dto.getStart_date(), dto.getEnd_date());
			populateResponse(workbook, response);
		}
		logger.info("getIbRebateSettingsReport End");
	}

	public void getIbClientMapReport(GetIbClientMapReportRequest request, HttpServletResponse response, SenderDto sender) throws Exception {

		logger.info("getIbClientMapReport Start");
		ReportIbClientMapDto dto = new ReportIbClientMapDto();
		dto.setStart_date(request.getBody().getStart_date());
		dto.setEnd_date(request.getBody().getEnd_date());
		dto.setIb_code(request.getBody().getIb_code());
		dto.setBrand_code(request.getBody().getBrand_code());

		List<ReportIbClientMapDto> list = ibClientMapDao.getReportByUserExample(dto, sender.getSender(), request.getBody().getBrand_code());

		List<String[]> data = new ArrayList<String[]>();
		String[] columns = new String[] { "brand_code", "ib_code", "client_code", "client_package_code", "start_date", "end_date", "last_update_time",
				"last_update_user" };

		if (list != null && list.size() > 0) {
			for (ReportIbClientMapDto model : list) {
				data.add(getProperties(model, columns));
			}
		}
		if (columns != null && data != null) {
			HSSFWorkbook workbook = populateWorkSheet("Ib Client Map", columns, data, dto.getStart_date(), dto.getEnd_date());
			populateResponse(workbook, response);
		}
		logger.info("getIbClientMapReport End");
	}
	
	
	
	
	
	

	private void setIbRebateReportToWorkSheet(GetIbCommissionReportRequest request, HSSFWorkbook workbook, HSSFCellStyle style, SenderDto sender, HSSFCellStyle totalRowStyle)
			throws Exception {

		logger.info("Generating ib rebate report");
		HSSFSheet sheet = workbook.createSheet("IB Rebate");
		sheet.createFreezePane(0, 2);
		Date startDate = request.getBody().getStart_date();
		Date endDate = request.getBody().getEnd_date();
		String brandCode = request.getBody().getBrand_code();
		List<String> ibCodes = getIbCodes(request.getBody().getIb_code());

		
		HSSFRow rowhead = sheet.createRow((short) 1);
		rowhead.createCell(0).setCellValue("IB code");
		rowhead.createCell(1).setCellValue("IB name");
		rowhead.createCell(2).setCellValue("Brand code");
		rowhead.createCell(3).setCellValue("Start date");
		rowhead.createCell(4).setCellValue("End date");
		rowhead.createCell(5).setCellValue("Accumulated rebate");
		rowhead.createCell(6).setCellValue("Accumulated trade P/L");
		rowhead.createCell(7).setCellValue("Rebate P/L ratio");
		rowhead.createCell(8).setCellValue("Total margin out");
		rowhead.createCell(9).setCellValue("Margin out count");
		rowhead.createCell(10).setCellValue("Account balance");
		rowhead.createCell(11).setCellValue("Trade date");
		rowhead.createCell(12).setCellValue("Trade date rebate");
		rowhead.createCell(13).setCellValue("Last trade date");
		rowhead.createCell(14).setCellValue("Last trade date rebate");
		rowhead.createCell(15).setCellValue("Last trade rebate ratio");
		
		for (int i = 0; i <= 15; i++) {
			rowhead.getCell(i).setCellStyle(style);
		}
		
		addDateTimeRowToWorkSheet(workbook, sheet, startDate, endDate, 15);

		List<IbRebateReportDto> beanList = ibCommissionDetailsDao.getIbRebateReport(ibCodes,  sender.getSender(), startDate, endDate, brandCode);

		int i = 2;
		double sum_accumulated_rebate = 0;
		double sum_accumulated_trade_pl = 0;
		double sum_margin_out_total = 0;
		double sum_margin_out_count = 0;
		double sum_account_balance = 0;
		double sum_trade_date_rebate = 0;
		double sum_last_trade_date_rebate = 0;
		
		for (IbRebateReportDto bean : beanList) {
			HSSFRow row = sheet.createRow((short) i);

			row.createCell(0).setCellValue(bean.getIb_code());
			row.createCell(1).setCellValue(bean.getIb_name());
			row.createCell(2).setCellValue(bean.getBrand_code());
			row.createCell(3).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, bean.getStart_date()));
			row.createCell(4).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, bean.getEnd_date()));
			row.createCell(5).setCellValue(bean.getAccumulated_rebate());
			row.createCell(6).setCellValue(bean.getAccumulated_trade_pl());
			if(bean.getRebate_pl_ratio() == null){
				row.createCell(7).setCellValue(0);
			}
			else{
				row.createCell(7).setCellValue(bean.getRebate_pl_ratio() + "%");
			}
			row.createCell(8).setCellValue(bean.getMargin_out_total());
			row.createCell(9).setCellValue(bean.getMargin_out_count());
			row.createCell(10).setCellValue(bean.getAccount_balance());
			row.createCell(11).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, bean.getTrade_date()));
			row.createCell(12).setCellValue(bean.getTrade_date_rebate());
			row.createCell(13).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, bean.getLast_trade_date()));
			row.createCell(14).setCellValue(bean.getLast_trade_date_rebate());
			row.createCell(15).setCellValue(bean.getRebate_diff() + "%");
			i++;
			sum_accumulated_rebate += bean.getAccumulated_rebate();
			sum_accumulated_trade_pl += bean.getAccumulated_trade_pl();
			sum_margin_out_total += bean.getMargin_out_total();
			sum_margin_out_count += bean.getMargin_out_count();
			sum_account_balance += bean.getAccount_balance();
			sum_trade_date_rebate += bean.getTrade_date_rebate();
			sum_last_trade_date_rebate += bean.getLast_trade_date_rebate();
		}

		// ADD TOTAL ROW
		HSSFRow row = sheet.createRow((short) i);
		row.createCell(0).setCellValue("");
		row.createCell(1).setCellValue("");
		row.createCell(2).setCellValue("");
		row.createCell(3).setCellValue("");
		row.createCell(4).setCellValue("");
		row.createCell(5).setCellValue(sum_accumulated_rebate);
		row.createCell(6).setCellValue(sum_accumulated_trade_pl);
		row.createCell(7).setCellValue("");
		row.createCell(8).setCellValue(sum_margin_out_total);
		row.createCell(9).setCellValue(sum_margin_out_count);
		row.createCell(10).setCellValue(sum_account_balance);
		row.createCell(11).setCellValue("");
		row.createCell(12).setCellValue(sum_trade_date_rebate);
		row.createCell(13).setCellValue("");
		row.createCell(14).setCellValue(sum_last_trade_date_rebate);
		row.createCell(15).setCellValue("");
		
		for (int j = 0; j <= 15; j++) {
			row.getCell(j).setCellStyle(totalRowStyle);
		}
		
		for (int x = 0; x < sheet.getRow(1).getPhysicalNumberOfCells() + 1; x++) {
			sheet.autoSizeColumn(x);
		}

	}

	private void setIbTradeAmountReportToWorkSheet(GetIbCommissionReportRequest request, HSSFWorkbook workbook, HSSFCellStyle style, SenderDto sender, HSSFCellStyle totalRowStyle)
			throws Exception {

		logger.info("Generating ib trade amount report");
		HSSFSheet sheet = workbook.createSheet("IB Trade Amount");
		sheet.createFreezePane(0, 2);
		Date startDate = request.getBody().getStart_date();
		Date endDate = request.getBody().getEnd_date();
		String brandCode = request.getBody().getBrand_code();
		List<String> ibCodes = getIbCodes(request.getBody().getIb_code());
		
		HSSFRow rowhead = sheet.createRow((short) 1);
		rowhead.createCell(0).setCellValue("IB code");
		rowhead.createCell(1).setCellValue("IB name");
		rowhead.createCell(2).setCellValue("Brand code");
		rowhead.createCell(3).setCellValue("Start date");
		rowhead.createCell(4).setCellValue("End date");
		rowhead.createCell(5).setCellValue("Accumulated lot");
		rowhead.createCell(6).setCellValue("Frequency trading count");
		rowhead.createCell(7).setCellValue("Frequency trading lot");
		rowhead.createCell(8).setCellValue("Frequency trading ratio");
		rowhead.createCell(9).setCellValue("Trade date");
		rowhead.createCell(10).setCellValue("Trade date lot");
		rowhead.createCell(11).setCellValue("Last trade date");
		rowhead.createCell(12).setCellValue("Last trade date lot");
		rowhead.createCell(13).setCellValue("Last date lot ratio");
		
		for (int i = 0; i <= 13; i++) {
			rowhead.getCell(i).setCellStyle(style);
		}
		
		addDateTimeRowToWorkSheet(workbook, sheet, startDate, endDate, 13);

		List<IbTradeAmountReportDto> beanList = ibCommissionDetailsDao.getIbTradeAccountReport(ibCodes,  sender.getSender(), startDate, endDate, brandCode);

		double sum_accumulated_lot = 0;
		double sum_freq_trading_count = 0;
		double sum_freq_trading_lot = 0;
		double trade_date_lot = 0;
		double last_trade_date_lot = 0;
		
		int i = 2;
		for (IbTradeAmountReportDto bean : beanList) {
			HSSFRow row = sheet.createRow((short) i);

			row.createCell(0).setCellValue(bean.getIb_code());
			row.createCell(1).setCellValue(bean.getIb_name());
			row.createCell(2).setCellValue(bean.getBrand_code());
			row.createCell(3).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, bean.getStart_date()));
			row.createCell(4).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, bean.getEnd_date()));
			row.createCell(5).setCellValue(bean.getAccumulated_lot());
			row.createCell(6).setCellValue(bean.getFreq_trading_count());
			row.createCell(7).setCellValue(bean.getFreq_trading_lot());
			row.createCell(8).setCellValue(bean.getFreq_trading_precentage() + "%");
			row.createCell(9).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, bean.getTrade_date()));
			row.createCell(10).setCellValue(bean.getTrade_date_lot());
			row.createCell(11).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, bean.getLast_trade_date()));
			row.createCell(12).setCellValue(bean.getLast_trade_date_lot());
			row.createCell(13).setCellValue(bean.getLot_diff() + "%");
			i++;
			
			sum_accumulated_lot += bean.getAccumulated_lot();
			sum_freq_trading_count += bean.getFreq_trading_count();
			sum_freq_trading_lot += bean.getFreq_trading_lot();
			trade_date_lot += bean.getTrade_date_lot();
			last_trade_date_lot += bean.getLast_trade_date_lot();
		}

		
		// ADD TOTAL ROW
		HSSFRow row = sheet.createRow((short) i);
		row.createCell(0).setCellValue("");
		row.createCell(1).setCellValue("");
		row.createCell(2).setCellValue("");
		row.createCell(3).setCellValue("");
		row.createCell(4).setCellValue("");
		row.createCell(5).setCellValue(sum_accumulated_lot);
		row.createCell(6).setCellValue(sum_freq_trading_count);
		row.createCell(7).setCellValue(sum_freq_trading_lot);
		row.createCell(8).setCellValue("");
		row.createCell(9).setCellValue("");
		row.createCell(10).setCellValue(trade_date_lot);
		row.createCell(11).setCellValue("");
		row.createCell(12).setCellValue(last_trade_date_lot);
		row.createCell(13).setCellValue("");
		
		for (int j = 0; j <= 13; j++) {
			row.getCell(j).setCellStyle(totalRowStyle);
		}
		
		
		for (int x = 0; x < sheet.getRow(1).getPhysicalNumberOfCells(); x++) {
			sheet.autoSizeColumn(x);
		}

	}


	public void getMarginInOutReport(GetMarginInOutReportRequest request, HttpServletResponse response, SenderDto sender) throws Exception {

		logger.info("Start generating margin in out report");

		HSSFWorkbook workbook = new HSSFWorkbook();

		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		

		HSSFCellStyle totalRowStyle = workbook.createCellStyle();
		totalRowStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		totalRowStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		
		if (request.getBody().isHas_ib_margin_in_out()) {
			setIbMarginInOutReportToWorkSheet(request, workbook, style, sender, totalRowStyle);
		}
		if (request.getBody().isHas_client_margin_in_out()) {
			setClientMarginInOutReportToWorkSheet(request, workbook, style, sender, totalRowStyle);
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String fileName = DateUtil.dateToStringByFormat(format, new Date());
		response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
		workbook.write(response.getOutputStream());
		System.out.println("Your excel file has been generated!");
		logger.info("Finish generating report");
	}
	
	
	private void setIbMarginInOutReportToWorkSheet(GetMarginInOutReportRequest request, HSSFWorkbook workbook, HSSFCellStyle style, SenderDto sender, HSSFCellStyle totalRowStyle)
			throws Exception {

		logger.info("Generating IB margin in out report");
		HSSFSheet sheet = workbook.createSheet("IB Margin In Out");
		sheet.createFreezePane(0, 2);
		
		Date startDate = request.getBody().getStart_date();
		Date endDate = request.getBody().getEnd_date();
		String brandCode = request.getBody().getBrand_code();
		List<String> ibCodes = getIbCodes(request.getBody().getIb_code());
		
		HSSFRow rowhead = sheet.createRow((short) 1);
		rowhead.createCell(0).setCellValue("Brand code");
		rowhead.createCell(1).setCellValue("Ib code");
		rowhead.createCell(2).setCellValue("Name");
		rowhead.createCell(3).setCellValue("Start date");
		rowhead.createCell(4).setCellValue("End date");
		rowhead.createCell(5).setCellValue("Total margin in");
		rowhead.createCell(6).setCellValue("Total margin out");
		rowhead.createCell(7).setCellValue("Net total margin in");
		rowhead.createCell(8).setCellValue("Trade P/L");
		rowhead.createCell(9).setCellValue("Trade date");
		rowhead.createCell(10).setCellValue("Trade date P/L");
		rowhead.createCell(11).setCellValue("Last trade date");
		rowhead.createCell(12).setCellValue("Last trade date P/L");
		
		
		for (int i = 0; i <= 12; i++) {
			rowhead.getCell(i).setCellStyle(style);
		}
		
		double sum_total_margin_in = 0;
		double sum_total_margin_out = 0;
		double sum_net_total_margin_in = 0;
		double sum_trade_pl = 0;
		double sum_trade_date_pl = 0;
		double sum_last_trade_date_pl = 0;
		
		
		addDateTimeRowToWorkSheet(workbook, sheet, startDate, endDate, 12);

		List<IbMarginInOutReportDto> beanList = ibCommissionDetailsDao.getIbMarginInOutReport(ibCodes, sender.getSender(), startDate, endDate, brandCode);

		int i = 2;
		for (IbMarginInOutReportDto bean : beanList) {
			HSSFRow row = sheet.createRow((short) i);

			row.createCell(0).setCellValue(bean.getBrand_code());
			row.createCell(1).setCellValue(bean.getIb_code());
			row.createCell(2).setCellValue(bean.getName());
			row.createCell(3).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, bean.getStart_date()));
			row.createCell(4).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, bean.getEnd_date()));
			row.createCell(5).setCellValue(bean.getTotal_margin_in());
			row.createCell(6).setCellValue(bean.getTotal_margin_out());
			row.createCell(7).setCellValue(bean.getNet_total_margin_in());
			row.createCell(8).setCellValue(bean.getTrade_pl());
			
			row.createCell(9).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE,bean.getTrade_date()));
			row.createCell(10).setCellValue(bean.getTrade_date_pl());
			row.createCell(11).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE,bean.getLast_trade_date()));
			row.createCell(12).setCellValue(bean.getLast_trade_date_pl());
			i++;
			
			sum_total_margin_in += bean.getTotal_margin_in();
			sum_total_margin_out += bean.getTotal_margin_out();
			sum_net_total_margin_in += bean.getNet_total_margin_in();
			sum_trade_pl += bean.getTrade_pl();
			sum_trade_date_pl += bean.getTrade_date_pl();
			sum_last_trade_date_pl += bean.getLast_trade_date_pl();
		}

		// ADD TOTAL ROW
		HSSFRow row = sheet.createRow((short) i);
		row.createCell(0).setCellValue("");
		row.createCell(1).setCellValue("");
		row.createCell(2).setCellValue("");
		row.createCell(3).setCellValue("");
		row.createCell(4).setCellValue("");
		row.createCell(5).setCellValue(sum_total_margin_in);
		row.createCell(6).setCellValue(sum_total_margin_out);
		row.createCell(7).setCellValue(sum_net_total_margin_in);
		row.createCell(8).setCellValue(sum_trade_pl);
		row.createCell(9).setCellValue("");
		row.createCell(10).setCellValue(sum_trade_date_pl);
		row.createCell(11).setCellValue("");
		row.createCell(12).setCellValue(sum_last_trade_date_pl);
		
		for (int j = 0; j <= 12; j++) {
			row.getCell(j).setCellStyle(totalRowStyle);
		}
		
		for (int x = 0; x < sheet.getRow(1).getPhysicalNumberOfCells(); x++) {
			sheet.autoSizeColumn(x);
		}

	}
	
	

	private void setClientMarginInOutReportToWorkSheet(GetMarginInOutReportRequest request, HSSFWorkbook workbook, HSSFCellStyle style, SenderDto sender, HSSFCellStyle totalRowStyle)
			throws Exception {

		logger.info("Generating client margin in out report");
		HSSFSheet sheet = workbook.createSheet("Client Margin In Out");
		sheet.createFreezePane(0, 2);
		
		Date startDate = request.getBody().getStart_date();
		Date endDate = request.getBody().getEnd_date();
		String brandCode = request.getBody().getBrand_code();
		List<String> ibCodes = getIbCodes(request.getBody().getIb_code());
		
		HSSFRow rowhead = sheet.createRow((short) 1);
		rowhead.createCell(0).setCellValue("Ib code");
		rowhead.createCell(1).setCellValue("Client code");
		rowhead.createCell(2).setCellValue("Client name");
		rowhead.createCell(3).setCellValue("Start date");
		rowhead.createCell(4).setCellValue("End date");
		rowhead.createCell(5).setCellValue("Total margin in");
		rowhead.createCell(6).setCellValue("Total margin out");
		rowhead.createCell(7).setCellValue("Net total margin in");
		rowhead.createCell(8).setCellValue("Trade P/L");
		rowhead.createCell(9).setCellValue("Trade date");
		rowhead.createCell(10).setCellValue("Trade date P/L");
		rowhead.createCell(11).setCellValue("Last trade date");
		rowhead.createCell(12).setCellValue("Last trade date P/L");
		
		
		for (int i = 0; i <= 12; i++) {
			rowhead.getCell(i).setCellStyle(style);
		}
		
		double sum_total_margin_in = 0;
		double sum_total_margin_out = 0;
		double sum_net_total_margin_in = 0;
		double sum_trade_pl = 0;
		double sum_trade_date_pl = 0;
		double sum_last_trade_date_pl = 0;
		
		
		addDateTimeRowToWorkSheet(workbook, sheet, startDate, endDate, 12);

		List<ClientMarginInOutReportDto> beanList = ibCommissionDetailsDao.getClientMarginInOutReport(ibCodes,  sender.getSender(), startDate, endDate, brandCode);

		int i = 2;
		for (ClientMarginInOutReportDto bean : beanList) {
			HSSFRow row = sheet.createRow((short) i);

			row.createCell(0).setCellValue(bean.getIb_code());
			row.createCell(1).setCellValue(bean.getClient_code());
			row.createCell(2).setCellValue(bean.getClient_name());
			row.createCell(3).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, bean.getStart_date()));
			row.createCell(4).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, bean.getEnd_date()));
			row.createCell(5).setCellValue(bean.getTotal_margin_in());
			row.createCell(6).setCellValue(bean.getTotal_margin_out());
			row.createCell(7).setCellValue(bean.getNet_total_margin_in());
			row.createCell(8).setCellValue(bean.getTrade_pl());
			
			row.createCell(9).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE,bean.getTrade_date()));
			row.createCell(10).setCellValue(bean.getTrade_date_pl());
			row.createCell(11).setCellValue(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE,bean.getLast_trade_date()));
			row.createCell(12).setCellValue(bean.getLast_trade_date_pl());
			i++;
			
			sum_total_margin_in += bean.getTotal_margin_in();
			sum_total_margin_out += bean.getTotal_margin_out();
			sum_net_total_margin_in += bean.getNet_total_margin_in();
			sum_trade_pl += bean.getTrade_pl();
			sum_trade_date_pl += bean.getTrade_date_pl();
			sum_last_trade_date_pl += bean.getLast_trade_date_pl();
		}
		// ADD TOTAL ROW
		HSSFRow row = sheet.createRow((short) i);
		row.createCell(0).setCellValue("");
		row.createCell(1).setCellValue("");
		row.createCell(2).setCellValue("");
		row.createCell(3).setCellValue("");
		row.createCell(4).setCellValue("");
		row.createCell(5).setCellValue(sum_total_margin_in);
		row.createCell(6).setCellValue(sum_total_margin_out);
		row.createCell(7).setCellValue(sum_net_total_margin_in);
		row.createCell(8).setCellValue(sum_trade_pl);
		row.createCell(9).setCellValue("");
		row.createCell(10).setCellValue(sum_trade_date_pl);
		row.createCell(11).setCellValue("");
		row.createCell(12).setCellValue(sum_last_trade_date_pl);
		
		
		for (int j = 0; j <= 12; j++) {
			row.getCell(j).setCellStyle(totalRowStyle);
		}
		
		
		for (int x = 0; x < sheet.getRow(1).getPhysicalNumberOfCells(); x++) {
			sheet.autoSizeColumn(x);
		}

	}
	
	
	
	
	
}
