package com.henyep.ib.terminal.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.henyep.ib.terminal.api.dto.db.IbTreeBean;
import com.henyep.ib.terminal.api.dto.request.report.GetIbTreeReportRequest;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.server.dao.IbTreeDao;
import com.henyep.ib.terminal.server.dao.UserRolesDao;
import com.henyep.ib.terminal.server.dto.report.IbTreeDto;
import com.henyep.ib.terminal.server.dto.security.SenderDto;
import com.henyep.ib.terminal.server.service.ReportIbTreeService;
import com.henyep.ib.terminal.server.util.DateUtil;

@SuppressWarnings("deprecation")
@Service(value = "ReportIbTreeService")
public class ReportIbTreeServiceImpl implements ReportIbTreeService {

	private final transient Log logger = LogFactory.getLog(getClass());

	@Resource(name = "IbTreeDao")
	private IbTreeDao ibTreeDao;
	
	@Resource(name = "UserRolesDao")
	private UserRolesDao userRolesDao;

	private int excelRowCounter = 0;
	private int excelColumnCounter = 0;

	@Override
	public void getIbTreeReport(GetIbTreeReportRequest request, HttpServletResponse response, SenderDto sender) throws Exception {

		logger.info("Start generating ib tree report");

		HSSFWorkbook workbook = new HSSFWorkbook();

		HSSFCellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);

		HSSFCellStyle style = workbook.createCellStyle();
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);

		HSSFCellStyle teamHeadStyle = workbook.createCellStyle();
		teamHeadStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		teamHeadStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		teamHeadStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		teamHeadStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		teamHeadStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		Font font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		teamHeadStyle.setFont(font);
		
		HSSFCellStyle salesStyle = workbook.createCellStyle();
		salesStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		salesStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		salesStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		salesStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		salesStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		Font salesFont = workbook.createFont();
		salesFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		salesFont.setColor(IndexedColors.BLUE.getIndex());
		salesStyle.setFont(salesFont);

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String fileName = DateUtil.dateToStringByFormat(format, new Date());
		
		
		setSalesIBTreeToWorkSheet(request, workbook, headerStyle, salesStyle, style, sender);
		fileName += "_ibTree.xls";
	

		
		response.setHeader("Content-disposition", "attachment; filename=" + fileName);
		workbook.write(response.getOutputStream());
		logger.info("Finish generating report");
	}

	private void setSalesIBTreeToWorkSheet(GetIbTreeReportRequest request, HSSFWorkbook workbook, HSSFCellStyle salesStyle, HSSFCellStyle teamHeadStyle,
			HSSFCellStyle style, SenderDto sender) throws Exception {
		excelRowCounter = 0;
		List<IbTreeBean> ibTreeBeans = ibTreeDao.getByTeamHead(sender.getSender(), request.getBody().getTrade_date());
		
		
		// get min id, max id and ibTreeMap
		int minId = Integer.MAX_VALUE;
		int maxId = Integer.MIN_VALUE;
		HashMap<Integer, IbTreeBean> ibTreeMap = new HashMap<Integer, IbTreeBean>();
		for (IbTreeBean ibTreeBean : ibTreeBeans) {
			if (!ibTreeMap.containsKey(ibTreeBean.getMin_id())) {
				ibTreeMap.put(ibTreeBean.getMin_id(), ibTreeBean);
			}
			if (ibTreeBean.getMin_id() < minId) {
				minId = ibTreeBean.getMin_id();
			}
			if (ibTreeBean.getMax_id() > maxId) {
				maxId = ibTreeBean.getMax_id();
			}
		}

		// get ib tree teams
		List<IbTreeDto> ibTreeDtos = new ArrayList<IbTreeDto>();
		while (maxId - minId > 1) {
			IbTreeDto ibTreeDto = getIbTreeDto(ibTreeMap, minId);
			if (ibTreeDto != null) {
				ibTreeDtos.add(ibTreeDto);
				minId = ibTreeDto.getIbTreeBean().getMax_id() + 1;
			} else {
				minId += 1;
			}
		}

		// create excel work sheet
		HSSFSheet sheet = workbook.createSheet("Users IB Tree");
		PaintSalesTreeOnSheet(sheet, ibTreeDtos.get(0), salesStyle, 0);
		
		if(sheet.getRow(0) != null){
			for (int x = 0; x < sheet.getRow(0).getPhysicalNumberOfCells(); x++) {
				sheet.autoSizeColumn(x);
			}
		}
	}
	
	
	private void setIBTreeToWorkSheet(GetIbTreeReportRequest request, HSSFWorkbook workbook, HSSFCellStyle headerStyle, HSSFCellStyle teamHeadStyle,
			HSSFCellStyle style, SenderDto sender) throws Exception {

		excelRowCounter = 1;
		Date tradeDate = request.getBody().getTrade_date();
		String targetBrandCode = request.getBody().getBrand_code();
		List<IbTreeBean> ibTreeBeans = ibTreeDao.getCurrentIbTrees(tradeDate, tradeDate);	

		// get min id, max id and ibTreeMap
		int minId = Integer.MAX_VALUE;
		int maxId = Integer.MIN_VALUE;
		HashMap<Integer, IbTreeBean> ibTreeMap = new HashMap<Integer, IbTreeBean>();
		for (IbTreeBean ibTreeBean : ibTreeBeans) {
			if (targetBrandCode.equals("*") || targetBrandCode.equals(ibTreeBean.getBrand_code())) {
				if (!ibTreeMap.containsKey(ibTreeBean.getMin_id())) {
					ibTreeMap.put(ibTreeBean.getMin_id(), ibTreeBean);
				}
				if (ibTreeBean.getMin_id() < minId) {
					minId = ibTreeBean.getMin_id();
				}
				if (ibTreeBean.getMax_id() > maxId) {
					maxId = ibTreeBean.getMax_id();
				}
			}
		}

		// get ib tree teams
		List<IbTreeDto> ibTreeDtos = new ArrayList<IbTreeDto>();
		while (maxId - minId > 1) {
			IbTreeDto ibTreeDto = getIbTreeDto(ibTreeMap, minId);
			if (ibTreeDto != null) {
				ibTreeDtos.add(ibTreeDto);
				minId = ibTreeDto.getIbTreeBean().getMax_id() + 1;
			} else {
				minId += 1;
			}

		}

		// create excel work sheet
		HSSFSheet sheet = workbook.createSheet("IB Tree");

		excelColumnCounter = 1;
		List<HSSFRow> teamHeadRows = new ArrayList<HSSFRow>();
		for (IbTreeDto brandCodeDto : ibTreeDtos) {
			String brandCode = brandCodeDto.getIbTreeBean().getIb_code();
			for (IbTreeDto teamDto : brandCodeDto.getChilds()) {
				HSSFRow teamHeadRow = sheet.createRow((short) excelRowCounter);
				teamHeadRow.createCell(0).setCellValue(teamDto.getIbTreeBean().getIb_code() + "(" + brandCode + ")");
				teamHeadRows.add(teamHeadRow);
				excelRowCounter++;

				HSSFRow row = sheet.createRow((short) excelRowCounter);
				row.createCell(0).setCellValue(teamDto.getIbTreeBean().getIb_code());
				excelRowCounter++;
				PaintTreeOnSheet(sheet, teamDto, 1);
				excelRowCounter++;
			}
		}

		// paint header
		HSSFRow row = sheet.createRow((short) 0);
		for (int i = 0; i < excelColumnCounter; i++) {
			HSSFCell cell = row.createCell(i);
			String level = new Integer(i + 1).toString();
			cell.setCellValue("Level " + level + " Agent");
			cell.setCellStyle(headerStyle);
		}

		for (HSSFRow teamHeadRow : teamHeadRows) {
			sheet.addMergedRegion(new CellRangeAddress(teamHeadRow.getRowNum(), teamHeadRow.getRowNum(), 0, excelColumnCounter - 1));
			for (int x = 0; x < excelColumnCounter; x++) {
				if (teamHeadRow.getCell(x) == null) {
					teamHeadRow.createCell(x);
				}
				teamHeadRow.getCell(x).setCellStyle(teamHeadStyle);
			}
		}

		// set header as freeze row
		sheet.createFreezePane(0, 1);
		// resize column width
		for (int x = 0; x < sheet.getRow(0).getPhysicalNumberOfCells(); x++) {
			sheet.autoSizeColumn(x);
		}

		// set cell style
		for (int y = 1; y < excelRowCounter; y++) {
			for (int x = 0; x < sheet.getRow(0).getPhysicalNumberOfCells(); x++) {
				HSSFRow targetRow = sheet.getRow(y);
				if (targetRow != null) {
					if (!teamHeadRows.contains(targetRow)) {
						HSSFCell targetCell = targetRow.getCell(x);
						if (targetCell == null) {
							targetCell = targetRow.createCell(x);
						}
						targetCell.setCellStyle(style);
					}

				}

			}
		}

	}

	// recursive function to generate ibTreeDto
	private IbTreeDto getIbTreeDto(HashMap<Integer, IbTreeBean> ibTreeMap, int currentId) {

		IbTreeBean currentIbTreeBean = ibTreeMap.get(currentId);

		if (currentIbTreeBean != null) {
			int minId = currentIbTreeBean.getMin_id();
			int maxId = currentIbTreeBean.getMax_id();

			IbTreeDto currentDto = new IbTreeDto();
			currentDto.setIbTreeBean(currentIbTreeBean);

			while (maxId - minId >= 1) {
				IbTreeDto child = getIbTreeDto(ibTreeMap, minId + 1);
				if (child != null) {
					minId = child.getIbTreeBean().getMax_id();
					System.out.println(
							"Parent: " + currentIbTreeBean.getIb_code() + " Child: " + child.getIbTreeBean().getIb_code() + " min iD: " + minId);
					currentDto.getChilds().add(child);
					child.setParent(currentDto);
				} else {
					minId += 1;
				}
			}
			return currentDto;
		} else {
			return null;
		}
	}

	// recursive function to paint all ib tree member
	private void PaintTreeOnSheet(HSSFSheet sheet, IbTreeDto team, int column) {

		for (IbTreeDto child : team.getChilds()) {

			if (column + 1 > excelColumnCounter) {
				excelColumnCounter = column + 1;
			}
			HSSFRow row = sheet.createRow((short) excelRowCounter);
			HSSFCell cell = row.createCell(column);
			cell.setCellValue(child.getIbTreeBean().getIb_code());
			excelRowCounter++;
			PaintTreeOnSheet(sheet, child, column + 1);
		}
	}
	
	
	private void PaintSalesTreeOnSheet(HSSFSheet sheet, IbTreeDto team, HSSFCellStyle salesStyle, int column) {

		HSSFRow row = sheet.createRow((short) excelRowCounter);
		HSSFCell cell = row.createCell(column);
		cell.setCellValue(team.getIbTreeBean().getIb_code());
		
		
		if(team.getIbTreeBean().getId() < 0){
			cell.setCellStyle(salesStyle);
		}
		
		for (IbTreeDto child : team.getChilds()) {

			if (column + 1 > excelColumnCounter) {
				excelColumnCounter = column + 1;
			}
			
			excelRowCounter++;
			PaintSalesTreeOnSheet(sheet, child, salesStyle, column + 1);
		}
	}

}
