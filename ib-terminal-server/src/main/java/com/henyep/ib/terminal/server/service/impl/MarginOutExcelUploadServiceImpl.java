package com.henyep.ib.terminal.server.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.henyep.ib.terminal.api.dto.db.MarginOutBean;
import com.henyep.ib.terminal.api.dto.request.marginout.ExcelUploadMarginOutRequest;
import com.henyep.ib.terminal.api.dto.request.marginout.MaxWithdrawalModel;
import com.henyep.ib.terminal.api.dto.response.marginout.ExcelUploadMarginOutResponseDto;
import com.henyep.ib.terminal.api.dto.response.marginout.InvalidMarginOutModel;
import com.henyep.ib.terminal.server.adapter.MT4ServiceAdapterFactory;
import com.henyep.ib.terminal.server.dao.IbAccountDetailsDao;
import com.henyep.ib.terminal.server.dao.MarginOutDao;
import com.henyep.ib.terminal.server.dto.mt4.response.DepositResponse;
import com.henyep.ib.terminal.server.dto.security.SenderDto;
import com.henyep.ib.terminal.server.global.Constants;
import com.henyep.ib.terminal.server.service.MarginOutExcelUploadService;
import com.henyep.ib.terminal.server.util.DateUtil;
import com.henyep.ib.terminal.server.util.RateExchangeUtil;
import com.henyep.white.label.api.dto.request.balance.transfer.DepositMT4RequestDto;

@Service("MarginOutExcelUploadService")
public class MarginOutExcelUploadServiceImpl implements MarginOutExcelUploadService {
	final static Logger log = Logger.getLogger(MarginOutExcelUploadServiceImpl.class);
	private MarginOutDao marginOutDao;
	private IbAccountDetailsDao ibAccountDetailsDao;

	@Resource(name = "RateExchangeUtil")
	private RateExchangeUtil rateExchangeUtil;

	@Resource(name = "MT4ServiceAdapterFactory")
	private MT4ServiceAdapterFactory mt4ServiceAdapterFactory;

	@Autowired
	public MarginOutExcelUploadServiceImpl(MarginOutDao marginOutDao, IbAccountDetailsDao ibAccountDetailsDao) {
		this.marginOutDao = marginOutDao;
		this.ibAccountDetailsDao = ibAccountDetailsDao;
	}

	@Override
	public ExcelUploadMarginOutResponseDto excelUploadMarginOuts(ExcelUploadMarginOutRequest request, SenderDto sender) throws Exception {

		log.info("-------------------------------------------------");
		log.info("Start excelUploadMarginOuts");

		ExcelUploadMarginOutResponseDto dto = new ExcelUploadMarginOutResponseDto();
		dto.setInvalid_margin_outs(new ArrayList<InvalidMarginOutModel>());
		dto.setValid_margin_outs(new ArrayList<MarginOutBean>());
		String batchFileId = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE_DETAIL, new Date());
		log.info("Batch file id: " + batchFileId);
		ByteArrayInputStream bis = new ByteArrayInputStream(request.getBody().getExcelBytes());

		Workbook workbook;
		try {
			if (request.getBody().getExtension().equals("xls")) {
				workbook = new HSSFWorkbook(bis);
			} else {
				log.info("Invalid excel format");
				throw new IllegalArgumentException("Received file does not have a standard excel extension.");
			}
			log.info("Read margin out data from excel...");
			Sheet sheet = workbook.getSheetAt(0);
			HashMap<MarginOutBean, Row> validBeansMap = new HashMap<MarginOutBean, Row>();

			for (Row row : sheet) {

				if (row.getRowNum() > 0) {
					String errorCode = validateRowFormat(row);
					MarginOutBean bean = null;
					if (errorCode == null) {
						// validate row content
						bean = createMarginOutFromRow(row, request, sender);
						bean.setBatch_file_id(batchFileId);
						errorCode = validateRowContent(bean);

					}

					if (errorCode == null) {
						validBeansMap.put(bean, row);
					} else {
						InvalidMarginOutModel model = new InvalidMarginOutModel();
						model.setMargin_out(bean);
						model.setError(errorCode);
						model.setLine_num(row.getRowNum() + 1);
						dto.getInvalid_margin_outs().add(model);
					}
				}
			}

			// validate row from db max withdrawal
			log.info("Checking ib accounts have enough margin to perform margin out...");
			HashMap<String, Double> withdrawalMap = new HashMap<String, Double>();
			for (MarginOutBean bean : validBeansMap.keySet()) {
				if (!withdrawalMap.containsKey(bean.getAccount())) {
					withdrawalMap.put(bean.getAccount(), bean.getAccount_amount());
				} else {
					Double originalAmount = withdrawalMap.get(bean.getAccount());
					withdrawalMap.remove(bean.getAccount());
					withdrawalMap.put(bean.getAccount(), originalAmount + bean.getAccount_amount());
				}
			}
			List<MaxWithdrawalModel> maxWithdrawalModels = marginOutDao.getAllMaxWithdrawal();
			HashMap<String, Double> maxWithdrawalMap = new HashMap<String, Double>();
			for (MaxWithdrawalModel maxWithdrawalModel : maxWithdrawalModels) {
				if (!maxWithdrawalMap.containsKey(maxWithdrawalModel.getIb_code())) {
					maxWithdrawalMap.put(maxWithdrawalModel.getIb_code(),
							maxWithdrawalModel.getAccount_balance() - maxWithdrawalModel.getPending_margin());
				}
			}

			for (MarginOutBean bean : validBeansMap.keySet()) {
				Double totalWithdrawal = withdrawalMap.get(bean.getAccount());
				Double maxWithdrawal = maxWithdrawalMap.get(bean.getAccount());
				if (maxWithdrawal == null) {
					InvalidMarginOutModel model = new InvalidMarginOutModel();
					model.setMargin_out(bean);
					model.setError(Constants.ERR_EXCEL_UPLOAD_MARGIN_OUT_NO_IB_RECORD + ":" + bean.getAccount().toString());
					model.setLine_num(validBeansMap.get(bean).getRowNum() + 1);
					dto.getInvalid_margin_outs().add(model);
				} else if (totalWithdrawal > maxWithdrawal) {
					log.info(totalWithdrawal + ">" + maxWithdrawal);
					InvalidMarginOutModel model = new InvalidMarginOutModel();
					model.setMargin_out(bean);
					model.setError(Constants.ERR_EXCEL_UPLOAD_MARGIN_OUT_NOT_ENOUTH_MARGIN);
					model.setLine_num(validBeansMap.get(bean).getRowNum() + 1);
					dto.getInvalid_margin_outs().add(model);
				} else {
					dto.getValid_margin_outs().add(bean);
				}
			}

			log.info("Num of rows are invalid: " + dto.getInvalid_margin_outs().size());
			log.info("Num of rows are valid: " + dto.getValid_margin_outs().size());

			// insert margin out to db
			if (dto.getInvalid_margin_outs().size() == 0) {
				// no error
				// insert margin out and update ib account balance to db
				marginOutDao.saveMarginOuts(dto.getValid_margin_outs());
				//marginInForMT4Accounts(batchFileId);
				ibAccountDetailsDao.updateAccountBalanceByMarginOutBatchFileId(batchFileId);
				// set db beans to response
				dto.getValid_margin_outs().clear();
				List<MarginOutBean> dbBeans = marginOutDao.getMarginOutsByBatchFileId(batchFileId);
				dto.getValid_margin_outs().addAll(dbBeans);
				dto.setIs_success(true);
			} else {
				dto.setIs_success(false);
			}

			if (dto.getIs_success()) {
				log.info("Excel upload result: Success");
			} else {
				log.info("Excel upload result: Failure");
				for (InvalidMarginOutModel invalidModel : dto.getInvalid_margin_outs()) {
					log.info("Row: " + invalidModel.getLine_num() + " Error: " + invalidModel.getError());
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			dto.setIs_success(false);
			log.error(e, e);
			throw e;
		}

		log.info("-------------------------------------------------");

		return dto;
	}

	private List<MarginOutBean> marginInForMT4Accounts(String batchFileId) throws Exception {
		MarginOutBean search = new MarginOutBean();
		search.setBatch_file_id(batchFileId);
		search.setMethod(Constants.MARGIN_OUT_METHOD_TO_TRADE_ACCOUNT);
		Date today = new Date();
		List<MarginOutBean> toMT4List = marginOutDao.getMarginOutByExample(today, today, search);
		if (toMT4List != null) {
			for (MarginOutBean model : toMT4List) {
				if (model.getMethod().equals(Constants.MARGIN_OUT_METHOD_TO_TRADE_ACCOUNT)) {
					DepositMT4RequestDto mt4ReqModel = new DepositMT4RequestDto();
					String comment = model.getComment();
					if (comment != null) {
						comment = comment.replaceAll("AC-", "");
						int sep = comment.indexOf("-");
						String mt4IdStr = comment.substring(0, sep);
						comment = comment.substring(sep + 1, comment.length());
						if (StringUtils.isNumeric(mt4IdStr)) {
							Integer mt4Id = Integer.parseInt(mt4IdStr);
							mt4ReqModel.setLogin_id(mt4Id);
							// assume the currency is USD
							mt4ReqModel.setAmount(model.getAmount());
							mt4ReqModel.setComment(comment);
							String json = mt4ServiceAdapterFactory.sendRequest(mt4ReqModel);
							Object mt4ResModel = mt4ServiceAdapterFactory.getResponseObject(mt4ReqModel, json);
							if (mt4ResModel != null && mt4ResModel instanceof DepositResponse) {
								if (((DepositResponse) mt4ResModel).getTradeTransInfo().getOrder() > 0) {
									model.setStatus(Constants.MARGIN_OUT_STATUS_EXECUTED);
									model.setLast_update_time(new Date());
									marginOutDao.updateMarginOut(model);
								}
							}
						}
					}
				}
			}
		}

		return null;
	}

	private String validateRowFormat(Row row) {

		// check number of columns
		int firstCellNum = row.getFirstCellNum();
		int lastCellNum = row.getLastCellNum();

		if (firstCellNum != 0 || lastCellNum < Constants.EXCEL_UPLOAD_MARGIN_OUT_COL_TOTAL - 1) {
			return Constants.ERR_EXCEL_UPLOAD_MARGIN_OUT_COL_COUNT;
		}

		// check column type
		HashMap<Integer, Integer> colTypeMap = new HashMap<Integer, Integer>();
		colTypeMap.put(Constants.EXCEL_UPLOAD_MARGIN_OUT_COL_IDX_IB_CODE, Constants.EXCEL_UPLOAD_MARGIN_OUT_COL_TYPE_INT);
		colTypeMap.put(Constants.EXCEL_UPLOAD_MARGIN_OUT_COL_IDX_CURRENCY, Constants.EXCEL_UPLOAD_MARGIN_OUT_COL_TYPE_TEXT);
		colTypeMap.put(Constants.EXCEL_UPLOAD_MARGIN_OUT_COL_IDX_AMOUNT, Constants.EXCEL_UPLOAD_MARGIN_OUT_COL_TYPE_INT);
		colTypeMap.put(Constants.EXCEL_UPLOAD_MARGIN_OUT_COL_IDX_METHOD, Constants.EXCEL_UPLOAD_MARGIN_OUT_COL_TYPE_TEXT);

		Iterator<Cell> cellIterator = row.cellIterator();
		while (cellIterator.hasNext()) {
			Cell cell = cellIterator.next();
			int colIndex = cell.getColumnIndex();
			Integer requiredColType = colTypeMap.get(colIndex);
			if (requiredColType != null && requiredColType != cell.getCellType()) {
				return Constants.ERR_EXCEL_UPLOAD_MARGIN_OUT_COL_TYPE + ":" + getColumnName(colIndex);
			}
		}
		return null;
	}

	private MarginOutBean createMarginOutFromRow(Row row, ExcelUploadMarginOutRequest request, SenderDto sender) throws Exception {
		MarginOutBean bean = new MarginOutBean();
		Iterator<Cell> cellIterator = row.cellIterator();
		while (cellIterator.hasNext()) {
			Cell cell = cellIterator.next();
			int colIndex = cell.getColumnIndex();
			if (colIndex == Constants.EXCEL_UPLOAD_MARGIN_OUT_COL_IDX_IB_CODE) {
				DecimalFormat decimalFormat = new DecimalFormat("##################");
				bean.setAccount(decimalFormat.format(cell.getNumericCellValue()));
			} else if (colIndex == Constants.EXCEL_UPLOAD_MARGIN_OUT_COL_IDX_CURRENCY) {
				bean.setCurrency(cell.getStringCellValue());
				bean.setAccount_currency(bean.getCurrency());
			} else if (colIndex == Constants.EXCEL_UPLOAD_MARGIN_OUT_COL_IDX_AMOUNT) {
				bean.setAmount(cell.getNumericCellValue());
			} else if (colIndex == Constants.EXCEL_UPLOAD_MARGIN_OUT_COL_IDX_COMMENT) {
				if (cell.getCellType() == Constants.EXCEL_UPLOAD_MARGIN_OUT_COL_TYPE_INT) {
					bean.setComment(String.valueOf(cell.getNumericCellValue()));
				} else if (cell.getCellType() == Constants.EXCEL_UPLOAD_MARGIN_OUT_COL_TYPE_TEXT) {
					bean.setComment(cell.getStringCellValue());
				}
			} else if (colIndex == Constants.EXCEL_UPLOAD_MARGIN_OUT_COL_IDX_METHOD) {
				bean.setMethod(cell.getStringCellValue());
			}
		}
		bean.setBrand_code(request.getBody().getBrand_code());
		bean.setCategory(Constants.MARGIN_OUT_CATEGORY_EXCEL_UPLOAD);
		Double[] snipples = rateExchangeUtil.getExchangePrice(bean.getCurrency(), bean.getAccount_currency(), bean.getAmount());
		bean.setExchange_rate(snipples[0]);
		bean.setAccount_amount(snipples[1]);
		bean.setTrade_date(new Date());
//		if (bean.getMethod().equals(Constants.MARGIN_OUT_METHOD_TO_TRADE_ACCOUNT))
//			bean.setStatus(Constants.MARGIN_OUT_STATUS_PENDING);
//		else
			bean.setStatus(Constants.MARGIN_OUT_STATUS_EXECUTED);
		bean.setCreate_user(sender.getSender());
		bean.setLast_update_user(sender.getSender());
		bean.setCreate_time(new Date());
		bean.setLast_update_time(new Date());

		return bean;
	}

	private String validateRowContent(MarginOutBean bean) throws Exception {

		if (!bean.getCurrency().equals("USD")) {
			return Constants.ERR_EXCEL_UPLOAD_MARGIN_OUT_CURRENCY + ":" + bean.getCurrency();
		}
		if (bean.getAmount() <= 0) {
			return Constants.ERR_EXCEL_UPLOAD_MARGIN_OUT_AMOUNT_LESS_THAN_ZERO + ":" + bean.getAmount();
		}
		String method = bean.getMethod();
		if (!method.equals(Constants.MARGIN_OUT_METHOD_BANK_TRANSFER) && !method.equals(Constants.MARGIN_OUT_METHOD_PAYMENT_GATEWAY)
				&& !method.equals(Constants.MARGIN_OUT_METHOD_TO_TRADE_ACCOUNT)
				&& !method.equals(Constants.MARGIN_OUT_METHOD_INTERNAL_ACCOUNT_TRANSFER)) {
			return Constants.ERR_EXCEL_UPLOAD_MARGIN_OUT_METHOD + ":" + method;
		}
		return null;
	}

	private String getColumnName(Integer colIndex) {
		if (colIndex == Constants.EXCEL_UPLOAD_MARGIN_OUT_COL_IDX_IB_CODE) {
			return Constants.EXCEL_UPLOAD_MARGIN_OUT_COL_NAME_IB_CODE;
		} else if (colIndex == Constants.EXCEL_UPLOAD_MARGIN_OUT_COL_IDX_CURRENCY) {
			return Constants.EXCEL_UPLOAD_MARGIN_OUT_COL_NAME_CURRENCY;
		} else if (colIndex == Constants.EXCEL_UPLOAD_MARGIN_OUT_COL_IDX_AMOUNT) {
			return Constants.EXCEL_UPLOAD_MARGIN_OUT_COL_NAME_AMOUNT;
		} else if (colIndex == Constants.EXCEL_UPLOAD_MARGIN_OUT_COL_IDX_COMMENT) {
			return Constants.EXCEL_UPLOAD_MARGIN_OUT_COL_NAME_COMMENT;
		} else if (colIndex == Constants.EXCEL_UPLOAD_MARGIN_OUT_COL_IDX_METHOD) {
			return Constants.EXCEL_UPLOAD_MARGIN_OUT_COL_NAME_METHOD;
		}
		return colIndex.toString();
	}
}
