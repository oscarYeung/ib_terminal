package com.henyep.ib.terminal.server.global;

public class Constants extends com.henyep.ib.terminal.api.global.Constants {

	
	public static final int TRADE_TYPE_ID_MARGIN = 1001;
	public static final int TRADE_TYPE_ID_CREDIT = 1002;
	public static final int TRADE_TYPE_ID_PL_ADJ = 1003;
	public static final int TRADE_TYPE_ID_COMMISSION_ADJ = 1004;
	public static final int TRADE_TYPE_ID_BUY = 1005;
	public static final int TRADE_TYPE_ID_SELL = 1006;
	
	public static final String PRODUCT_GROUP_OTHER = "OTHER";
				
	public static final int PLATFORM_MT4 = 401;
	public static final int PLATFORM_CURRENEX = 402;
	public static final int PLATFORM_MT4_2 = 403;
	public static final int PLATFORM_AFT = 404;
	public static final int PLATFORM_DOW_WIN = 405;
	public static final int PLATFORM_TMS = 406;
	public static final int PLATFORM_MP = 407;
	public static final int PLATFORM_SP = 408;
	public static final int PLATFORM_STAR = 410;
	
	public static final String EMAIL_SUBJECT = "subject";
	public static final String EMAIL_RECEIVER = "receiver";
	public static final String EMAIL_TEMPLATE = "email_template";
	public static final String EMAIL_SENDER = "sender";
	public static final String EMAIL_BCC = "bcc";
	public static final String EMAIL_ATTACHMENT = "attachment";
	public static final String EMAIL_ATTACHMENT_NAME = "attachment_name";
	
//	public static final String EMAIL_TEMPLATE_IB_FORGOT_PASSWORD_EN = "IbForgotPassword_en.vm";
//	public static final String EMAIL_TEMPLATE_IB_FORGOT_PASSWORD_TC = "IbForgotPassword_tc.vm";
//	public static final String EMAIL_TEMPLATE_IB_FORGOT_PASSWORD_SC = "IbForgotPassword_sc.vm";
	public static final String EMAIL_TEMPLATE_IB_FORGOT_PASSWORD_EN = "ForgotPassword_en.vm";
	public static final String EMAIL_TEMPLATE_IB_FORGOT_PASSWORD_CN = "ForgotPassword_cn.vm";
	
	public static final String EMAIL_TEMPLATE_CORN_JOB_FAIL = "CornJobFail.vm";
	public static final String EMAIL_TEMPLATE_IB_COMMISSION_SUMMARY = "IbCommissionSummaryReport.vm";
	
	public static final String EMAIL_TEMPLATE_MARGIN_OUT_REQUEST_EN = "WithdrawalRequest_en.vm";
	public static final String EMAIL_TEMPLATE_MARGIN_OUT_REQUEST_CN = "WithdrawalRequest_cn.vm";
	
	public static final String EMAIL_TEMPLATE_WELCOME_REQUEST_EN = "Welcome_en.vm";
	public static final String EMAIL_TEMPLATE_WELCOME_REQUEST_CN = "Welcome_cn.vm";
	
	public static final String EXCEL_UPLOAD_MARGIN_OUT_COL_NAME_IB_CODE = "ib_code";
	public static final String EXCEL_UPLOAD_MARGIN_OUT_COL_NAME_CURRENCY = "currency";
	public static final String EXCEL_UPLOAD_MARGIN_OUT_COL_NAME_AMOUNT = "amount";
	public static final String EXCEL_UPLOAD_MARGIN_OUT_COL_NAME_METHOD = "method";
	public static final String EXCEL_UPLOAD_MARGIN_OUT_COL_NAME_COMMENT = "comment";
	
	public static final int EXCEL_UPLOAD_MARGIN_OUT_COL_IDX_IB_CODE = 0;
	public static final int EXCEL_UPLOAD_MARGIN_OUT_COL_IDX_CURRENCY = 1;
	public static final int EXCEL_UPLOAD_MARGIN_OUT_COL_IDX_AMOUNT = 2;
	public static final int EXCEL_UPLOAD_MARGIN_OUT_COL_IDX_METHOD = 3;
	public static final int EXCEL_UPLOAD_MARGIN_OUT_COL_IDX_COMMENT = 4;
	
	public static final int EXCEL_UPLOAD_MARGIN_OUT_COL_TYPE_INT = 0;
	public static final int EXCEL_UPLOAD_MARGIN_OUT_COL_TYPE_TEXT = 1;
	
	public static final int EXCEL_UPLOAD_MARGIN_OUT_COL_TOTAL = 4;
	
	public static final String ERR_EXCEL_UPLOAD_MARGIN_OUT_COL_COUNT = "invalid.column.count";
	public static final String ERR_EXCEL_UPLOAD_MARGIN_OUT_COL_TYPE = "invalid.column.type";
	public static final String ERR_EXCEL_UPLOAD_MARGIN_OUT_CURRENCY = "invalid.currency";
	public static final String ERR_EXCEL_UPLOAD_MARGIN_OUT_AMOUNT_LESS_THAN_ZERO = "amount.less.than.zero";
	public static final String ERR_EXCEL_UPLOAD_MARGIN_OUT_METHOD = "invalid.method";
	public static final String ERR_EXCEL_UPLOAD_MARGIN_OUT_NOT_ENOUTH_MARGIN = "not.enough.margin";
	public static final String ERR_EXCEL_UPLOAD_MARGIN_OUT_NO_IB_RECORD = "no.ib.record";
	
	
	public static final String SPREAD_TYPE_FIXED = "Fixed";
	public static final String SPREAD_TYPE_VARIABLE = "Variable";
	
	
}
