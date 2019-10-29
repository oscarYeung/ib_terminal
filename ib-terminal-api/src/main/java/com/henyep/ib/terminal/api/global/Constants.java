package com.henyep.ib.terminal.api.global;

public class Constants {
	
	public static final String CHANNEL_WEB = "1001";
	public static final String CHANNEL_MOBILE = "1002";
	public static final String CHANNEL_ADMIN = "1003";
	
	public static final String PROCESSOR_TYPE_EV = "EV";
	public static final String PROCESSOR_TYPE_GROUP_EV = "GROUP_EV";
	public static final String PROCESSOR_TYPE_CPA = "CPA";
	public static final String PROCESSOR_TYPE_BONUS = "Bonus";
	public static final String PROCESSOR_TYPE_REBATE = "Rebate";
	
	public static final String CACHE_TYPE_PERMISSION = "P";
	public static final String CACHE_TYPE_IB_TREE = "I";

	public static final String PLATFORM_MT4_STRING = "MT4";
	public static final String PLATFORM_STAR_STRING = "STAR";

	public static final String COMMON_BUY = "B";
	public static final String COMMON_SELL = "S";

	public static final String USER_SYSTEM = "system";

	public static final String USER_STATUS_ACTIVE = "A";
	public static final String USER_STATUS_INACTIVE = "I";

	public static final String LANG_EN = "EN";
	public static final String LANG_TC = "TC";
	public static final String LANG_SC = "SC";
	public static final String LANG_CN = "CN";
	
	public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_DATE = "yyyy-MM-dd";
	public static final String FORMAT_DATE_SHORT = "yyyyMMdd";
	public static final String FORMAT_MONTH = "yyyy-MM";
	public static final String FORMAT_DATETIME_DETAILS = "yyyyMMddHHmmssSSS";
	// user type (ib , user ...etc)
	public static final String USER_TYPE_IB = "I";
	public static final String USER_TYPE_NORMAL_USER = "N";

	public static final String TYPE_PERCENTAGE = "%";
	public static final String TYPE_FIX_AMOUNT = "$";
	public static final String TYPE_SPREAD = "S";
	public static final String TYPE_PIP = "P";
	public static final String TYPE_ALL = "*";
	
	public static final String REBATE_METHOD_ACCUMULATED = "A";
	public static final String REBATE_METHOD_SEPERATED = "S";
	
	public static final String USER_ROLE_SALES = "S";
	public static final String USER_ROLE_ADMIN = "A";
	
	// MT4 web service links
	public static final String MT4_WEB_SERVICE_LOGIN_LINK = "/authservice/login";
	public static final String MT4_WEB_SERVICE_CREATE_LINK = "/userservice/create";
	public static final String MT4_WEB_SERVICE_DEPOSIT_LINK = "/marginInOutService/deposit";
	public static final String MT4_WEB_SERVICE_WITHDRAWAL_LINK = "/marginInOutService/withdrawal";
	public static final String MT4_WEB_SERVICE_BALANCE_TRANSFER_LINK = "/marginInOutService/balanceTransfer";
	public static final String MT4_WEB_SERVICE_GET_USER_PROFILE_LINK = "/userservice/getUserProfile";
	public static final String MT4_WEB_SERVICE_GET_ALL_SYMBOL_LINK = "/symbolservice/getAll";
	
	// system param
	public static final String SYSTEM_PARAM_SUBSEQUENT_MARGIN_OUT_FEE = "subsequent_margin_out_fee";
	public static final String SYSTEM_PARAM_IS_RUNNING_DATA_SYNC = "is_running_data_sync";
	public static final String SYSTEM_PARAM_IS_UPDATING_IB_COMMISSION = "is_updating_ib_commission";
	public static final String SYSTEM_PARAM_FREE_MARGIN_OUT_COUNT = "free_margin_out_count";
	public static final String SYSTEM_PARAM_MIN_MARGIN_OUT_AMOUNT = "min_margin_out_amount";
	public static final String SYSTEM_PARAM_SEND_EMAIL_NEW_USER_REQUEST_MARGIN_OUT = "send_email_new_user_request_margin_out";
	public static final String SYSTEM_PARAM_MT4_WEB_SERVICE_CONNECTION = "mt4_web_service_connection";
	public static final String SYSTEM_PARAM_MT4_WEB_SERVICE_IP = "mt4_web_service_ip";
	public static final String SYSTEM_PARAM_MT4_WEB_SERVICE_LOGIN = "mt4_web_service_login";
	public static final String SYSTEM_PARAM_MT4_WEB_SERVICE_PASSWORD = "mt4_web_service_password";
	public static final String SYSTEM_PARAM_MT4_WEB_SERVICE_PORT = "mt4_web_service_port";
	
	public static final String SYSTEM_PARAM_MT5_WEB_SERVICE_CONNECTION = "mt5_web_service_connection";
	public static final String SYSTEM_PARAM_MT5_WEB_SERVICE_IP = "mt5_web_service_ip";
	public static final String SYSTEM_PARAM_MT5_WEB_SERVICE_LOGIN = "mt5_web_service_login";
	public static final String SYSTEM_PARAM_MT5_WEB_SERVICE_PASSWORD = "mt5_web_service_password";
	public static final String SYSTEM_PARAM_MT5_WEB_SERVICE_PORT = "mt5_web_service_port";

	// margin in status
	public static final String MARGIN_IN_STATUS_PENDING = "P";
	public static final String MARGIN_IN_STATUS_EXECUTED = "E";
	public static final String MARGIN_IN_STATUS_REJECTED = "R";
	public static final String MARGIN_IN_STATUS_CANCELLED = "C";
	public static final String MARGIN_IN_STATUS_DELETED = "D";

	public static final String MARGIN_IN_CATEGORY_DEPOSIT = "D";
	public static final String MARGIN_IN_CATEGORY_BONUS = "B";
	public static final String MARGIN_IN_CATEGORY_REBATE = "R";
	public static final String MARGIN_IN_CATEGORY_REBATE_CPA = "C";
	public static final String MARGIN_IN_CATEGORY_INTERNAL_TRANSFER = "T";
	public static final String MARGIN_IN_CATEGORY_ADJUSTMENT = "A";

	// margin out status
	public static final String MARGIN_OUT_STATUS_PENDING = "P";
	public static final String MARGIN_OUT_STATUS_EXECUTED = "E";
	public static final String MARGIN_OUT_STATUS_REJECTED = "R";
	public static final String MARGIN_OUT_STATUS_CANCELLED = "C";
	public static final String MARGIN_OUT_STATUS_DELETED = "D";
	
	// schedule task status
	public static final String SCHEDULE_STATUS_PENDING = "P";
	public static final String SCHEDULE_STATUS_EXECUTED = "E";
	public static final String SCHEDULE_STATUS_FAILED = "R";
	
	// schedule task type
	public static final String SCHEDULE_TYPE_UPDATE_IB_ACCOUNT_DETAILS_DAY_OPEN = "UIADDO";
	public static final String SCHEDULE_TYPE_UPDATE_CLIENT_TRADE_DETAIL = "UCTD";
	public static final String SCHEDULE_TYPE_UPDATE_OPEN_TRADE = "UOT";
	public static final String SCHEDULE_TYPE_UPDATE_CLIENT_MARGIN_IN_OUT = "UCMIO";
	public static final String SCHEDULE_TYPE_UPDATE_CLIENT_BALANCE_SUMMARY = "UCBS";
	public static final String SCHEDULE_TYPE_UPDATE_CLIENT_DETAILS = "UCD";
	public static final String SCHEDULE_TYPE_UPDATE_IB_LEADS = "UIL";
	public static final String SCHEDULE_TYPE_UPDATE_IB_CLIENT_MAP = "UICM";
	public static final String SCHEDULE_TYPE_UPDATE_CALCULATE_IB_COMMISSION = "CAL";
	public static final String SCHEDULE_TYPE_UPDATE_SETTINGS_SYMBOL = "USS";
	public static final String SCHEDULE_TYPE_UPDATE_IB_DAILY_FLOATING = "UIDF";

	// ib commission details supplementary status
	public static final String IB_COMMISSION_SUPPLEMENTARY_STATUS_PENDING = "P";
	public static final String IB_COMMISSION_SUPPLEMENTARY_STATUS_EXECUTED = "E";
	
	// ib profile status
	public static final String IB_STATUS_ACTIVE = "A";
	public static final String IB_STATUS_INACTIVE = "I";
	

	// margin out categories
	public static final String MARGIN_OUT_CATEGORY_USER_REQUEST = "U";
	public static final String MARGIN_OUT_CATEGORY_ADJUSTMENT = "A";
	public static final String MARGIN_OUT_CATEGORY_FEE = "F";
	public static final String MARGIN_OUT_CATEGORY_EXCEL_UPLOAD = "E";

	// margin out methods
	public static final String MARGIN_OUT_METHOD_ADJUSTMENT = "AD";
	public static final String MARGIN_OUT_METHOD_BANK_TRANSFER = "BK";
	public static final String MARGIN_OUT_METHOD_PAYMENT_GATEWAY = "PG";
	public static final String MARGIN_OUT_METHOD_TO_TRADE_ACCOUNT = "AC";
	public static final String MARGIN_OUT_METHOD_INTERNAL_ACCOUNT_TRANSFER = "TR";
	public static final String MARGIN_OUT_METHOD_FEE = "FE";
	
	// bonus type
	public static final String BONUS_TYPE_ACCUMLATED = "A";
	public static final String BONUS_TYPE_MONTHLY = "M";
	
	// Ib Lead Register Type
	public static final String IB_LEAD_REG_TYPE_LIVE = "L";
	public static final String IB_LEAD_REG_TYPE_DEMO = "D";
	public static final String IB_LEAD_REG_TYPE_BOTH = "B";

	// public static final String ERR_REQ_COMMON_BODY_IS_NULL =
	// "common.request.body.is.null";
	// public static final String ERR_REQ_COMMON_IB_CODE_IS_BLANK =
	// "common.ib.code.is.blank";
	// public static final String ERR_REQ_COMMON_START_DATE_IS_NULL =
	// "common.start.date.is.null";
	// public static final String ERR_REQ_COMMON_INVALID_DATE_RANGE =
	// "common.invalid.date.range";
	// public static final String ERR_RES_COMMON_START_DATE_AFTER_END_DATE =
	// "common.start.date.after.end.date";
	// public static final String ERR_RES_COMMON_INTERNAL_ERROR =
	// "common.internal.error";

	
	/* RESPONSE HEADER CODE */
	
	public static final String RES_STATUS_SUCCESS = "SUCCESS";
	public static final String RES_STATUS_FAIL = "FAIL";
	
	/* ERROR CODE */
	
	public static final String ERR_COMMON_INTERNAL_ERROR = "99999";
	
	public static final String ERR_COMMON_BODY_IS_NULL = "10001";
	public static final String ERR_COMMON_IB_CODE_IS_BLANK = "10002";
	public static final String ERR_COMMON_BRAND_CODE_IS_BLANK = "10003";
	public static final String ERR_COMMON_START_DATE_IS_NULL = "10004";
	public static final String ERR_COMMON_INVALID_DATE_RANGE = "10005";
	public static final String ERR_COMMON_TRADE_DATE_IS_NULL = "10006";
	public static final String ERR_COMMON_START_DATE_AFTER_END_DATE= "10007";

	public static final String ERR_COMMON_END_DATE_IS_NULL = "10008";
	public static final String ERR_COMMON_CLIENT_CODE_IS_BLANK = "10009";
	public static final String ERR_COMMON_ACCOUNT_IS_BLANK = "10010";
	public static final String ERR_COMMON_IB_PROFILE_NOT_EXIST = "10011";
	public static final String ERR_COMMON_ACCOUNT_DETAIL_NOT_EXIST = "10012";
	public static final String ERR_COMMON_CURRENCY_NULL = "10013";
	public static final String ERR_COMMON_IB_CODE_NOT_EXIST = "10014";
	public static final String ERR_COMMON_SECRET_KEY_EMPTY = "10015";
	public static final String ERR_COMMON_DECRYPT_SECRET_KEY = "10016";
	public static final String ERR_COMMON_REBATE_CODE_IS_BLANK = "10017";
	public static final String ERR_COMMON_PRODUCT_GROUP_IS_BLANK = "10018";
	public static final String ERR_COMMON_CLIENT_PACKAGE_CODE_IS_BLANK = "10019";


	public static final String ERR_COMMON_EV_PRECENTAGE_IS_BLANK = "10020";
	public static final String ERR_COMMON_USER_NAME_IS_BLANK_ = "10021";

	public static final String ERR_COMMON_PASSWORD_IS_BLANK = "10022";
	public static final String ERR_COMMON_FIRST_NAME_IS_BLANK = "10023";
	public static final String ERR_COMMON_LAST_NAME_IS_BLANK = "10024";
	public static final String ERR_COMMON_MOBILE_IS_BLANK = "10025";
	public static final String ERR_COMMON_EMAIL_IS_BLANK = "10026";
	public static final String ERR_COMMON_ADDRESS_IS_BLANK = "10027";
	public static final String ERR_COMMON_LANGUAGE_IS_BLANK = "10028";
	public static final String ERR_COMMON_STATUS_IS_BLANK = "10029";
	public static final String ERR_COMMON_IGNORE_TREE_REBATE_IS_BLANK = "10030";
	public static final String ERR_COMMON_DEPOSIT_BONUS_IS_BLANK = "10031";
	public static final String ERR_COMMON_ADJUSTMENT_IS_BLANK = "10032";
	public static final String ERR_COMMON_API_SESSION_TIME_OUT = "10033";
	public static final String ERR_COMMON_REQUEST_HEADER_IS_NULL = "10034";
	public static final String ERR_COMMON_REQUEST_CHANNEL_INVALID = "10035";
	public static final String ERR_COMMON_SCHEDULE_TASK_NOT_EXIST = "10036";
	public static final String ERR_COMMON_SCHEDULE_TASK_ALREADY_DONE = "10037";
	public static final String ERR_COMMON_SCHEDULE_TASK_ERROR = "10038";
	public static final String ERR_COMMON_DATA_SYNC_RUNNING = "10039";
	public static final String ERR_COMMON_IB_COMMISSION_UPDATING = "10040";
	public static final String ERR_COMMON_CREDIT_CARD_CHARGES_IS_BLANK = "10041";
	public static final String ERR_COMMON_GROUP_CODE_IS_BLANK = "10042";
	public static final String ERR_COMMON_ACCOUNT_BALANCE_IS_BLANK = "10043";
	public static final String ERR_COMMON_PERMISSION_DENIED = "10044";
	public static final String ERR_COMMON_TYPE_IS_NULL = "10045";
	public static final String ERR_COMMON_USER_IB_ACTION_DENIED = "10046";
	public static final String ERR_COMMON_SPERAD_TYPE_IS_BLANK = "10047";
	
	
		
	public static final String ERR_USER_NOT_EXIST  = "20001";	
	public static final String ERR_USER_INCORRECT_PASSWORD = "20002";
	public static final String ERR_USER_STATUS_INACTIVE = "20003";
	public static final String ERR_USER_NULL = "20004";
	public static final String ERR_USER_CODE_BLANK = "20005";
	public static final String ERR_USER_PASSWORD_BLANK = "20006";
	public static final String ERR_USER_STATUS_BLANK = "20007";
	public static final String ERR_USER_STATUS_INCORRECT = "20008";
	public static final String ERR_USER_NEW_PASSWORD_NO_CHANGE = "20009";
	public static final String ERR_USER_ALREADY_EXIST = "20010";
	public static final String ERR_USER_NEW_PASSWORD_BLANK = "20011";
	public static final String ERR_USER_OLD_PASSWORD_BLANK = "20012";
	public static final String ERR_USER_NAME_BLANK = "20013";
	public static final String ERR_USER_BRAND_CODE_BLANK = "20014";
	public static final String ERR_USER_UPDATE_NOT_NULL = "20015";

	public static final String ERR_REBATE_CODE_IS_BLANK = "30001";
	public static final String ERR_REBATE_DETAILS_IS_NULL = "30002";

	public static final String ERR_MARGIN_OUT_INVALID_METHOD = "40001";
	public static final String ERR_MARGIN_OUT_IB_PROFILE_NOT_EXIST = "40002";
	public static final String ERR_MARGIN_OUT_ACCOUNT_DETAIL_NOT_EXIST = "40003";
	public static final String ERR_MARGIN_OUT_INVALID_CATEGORY = "40004";
	public static final String ERR_MARGIN_OUT_INVALID_ADJUSTMENT_METHOD = "40005";
	public static final String ERR_MARGIN_OUT_INVALID_USER_REQUEST_METHOD = "40006";
	public static final String ERR_MARGIN_OUT_CATEGORY_NOT_BLANK = "40007";
	public static final String ERR_MARGIN_OUT_METHOD_NOT_BLANK = "40008";
	public static final String ERR_MARGIN_OUT_ACCOUNT_NOT_BLANK = "40009";
	public static final String ERR_MARGIN_OUT_CURRENCY_NOT_BLANK = "40010";
	public static final String ERR_MARGIN_OUT_AMOUNT_NOT_BLANK = "40011";
	public static final String ERR_MARGIN_OUT_TRADE_DATE_NOT_BLANK = "40012";
	public static final String ERR_MARGIN_OUT_ID_NOT_BLANK = "40013";
	public static final String ERR_MARGIN_OUT_ID_NOT_EXIST = "40014";
	public static final String ERR_MARGIN_OUT_ACCOUNT_NOT_EXIST = "40015";
	public static final String ERR_MARGIN_OUT_STATUS_NOT_PENDING = "40016";
	public static final String ERR_MARGIN_OUT_ACCOUNT_NOT_ENOUGH_ACCOUNT_BALANCE = "40017";
	public static final String ERR_MARGIN_OUT_START_DATE_NOT_BLANK = "40018";
	public static final String ERR_MARGIN_OUT_END_DATE_NOT_BLANK = "40019";
	public static final String ERR_MARGIN_OUT_START_DATE_AFTER_END_DATE = "40020";
	public static final String ERR_MARGIN_OUT_NULL = "40021";
	public static final String ERR_MARGIN_OUT_BELOW_MIN_AMOUNT = "40022";

	public static final String ERR_MARGIN_IN_NULL_ID_INPUT = "50001";
	public static final String ERR_MARGIN_IN_ID_NOT_EXIST = "50002";
	public static final String ERR_MARGIN_IN_STATUS_NOT_PENDING = "50003";
	public static final String ERR_MARGIN_IN_IB_NOT_EXIST = "50004";
	public static final String ERR_MARGIN_IN_IB_NOT_ENOUGH_PENDING_COMMISSION = "50005";
	public static final String ERR_MARGIN_IN_END_DATE_NOT_BLANK = "50006";
	public static final String ERR_MARGIN_IN_START_DATE_NOT_BLANK = "50007";
	public static final String ERR_MARGIN_IN_NULL = "50008";
	public static final String ERR_MARGIN_IN_CATEGORY_NOT_BLANK = "50009";
	public static final String ERR_MARGIN_IN_CATEGORY_NOT_EXIST = "50010";
	public static final String ERR_MARGIN_IN_INSERT_AMOUNT_NULL = "50011";
	public static final String ERR_MARGIN_IN_INVALID_STATUS = "50012";

	public static final String ERR_REBATE_SYMBOL_NO_PRODUCT_GROUP = "60001";
	public static final String ERR_REBATE_ACCOUNT_NO_CLIENT_PACKAGE = "60002";
	public static final String ERR_REBATE_ACCOUNT_NO_REBATE_CODE = "60003";
	public static final String ERR_REBATE_IB_TREE_HAS_MULTIPLE_REBATE_TYPES = "60004";
	public static final String ERR_REBATE_IB_NO_IB_TREE_FOUND = "60005";
	public static final String ERR_REBATE_IB_NO_REBATE_FOUND = "60006";
	public static final String ERR_REBATE_NO_REBATE_IGNORE_IB_TREE_REBATE_FOUND = "60007";
	public static final String ERR_REBATE_DETAIL_DATE_RANGE_OVERLAP = "60008";
	public static final String ERR_REBATE_SUPPLEMENTARY_DATE_RANGE_OVERLAP = "60009";
	public static final String ERR_REBATE_CODE_NOT_SAME = "60010";
	public static final String ERR_REBATE_ALREADY_EXIST = "60011";
	public static final String ERR_REBATE_DETAILS_ALREADY_EXIST = "60012";
	public static final String ERR_REBATE_SUPPLEMENTARY_ALREADY_EXIST = "60013";
	public static final String ERR_REBATE_CLIENT_REBATE_MAP_ALREADY_EXIST = "60014";	
	public static final String ERR_REBATE_DETAILS_CLIENT_PACKAGE_SPREAD_CODE_ALREADY_INSERTED = "60015";	
	public static final String ERR_REBATE_EV_STATUS_NOT_PENDING = "60016";
	public static final String ERR_REBATE_NO_SYMBOL_SETTING = "60017";
	public static final String ERR_REBATE_EV_IS_NULL = "60018";

	public static final String ERR_IB_CLIENT_LINK_IS_NULL = "70001";
	public static final String ERR_IB_CLIENT_LINK_ALREADY_EXIST = "70002";
	public static final String ERR_IB_CLIENT_LINK_NOT_EXIST = "70003";
	
	public static final String ERR_IB_TREE_PARENT_ID_IS_NULL = "71001";
	public static final String ERR_IB_TREE_TEAM_IS_NULL = "71002";
	public static final String ERR_IB_TREE_PARENT_ID_NOT_EXIST = "71003";
	public static final String ERR_IB_TREE_NEW_IB_CODE_ALREADY_EXIST = "71004";
	public static final String ERR_IB_TREE_START_DATE_NOT_IN_PARENT_START_END_DATE = "71005";
	public static final String ERR_IB_TREE_END_DATE_NOT_IN_PARENT_START_END_DATE = "71006";
	public static final String ERR_IB_TREE_ID_NOT_EXIST = "71007";
	
	
	public static final String ERR_IB_TREE_SAME_FROM_ID_TO_ID = "71008";
	public static final String ERR_IB_TREE_FROM_ID_NOT_EXIST = "71009";
	public static final String ERR_IB_TREE_TO_ID_NOT_EXIST = "71010";
	public static final String ERR_IB_TREE_TO_ID_IS_CHILD_OF_FROM_ID = "71011";
	public static final String ERR_IB_TREE_FROM_ID_DATE_RANGE_NOT_WITHIN_TO_ID = "71012";
	
	public static final String ERR_IB_TREE_PARENT_IB_NOT_EXIST = "71013";
	public static final String ERR_IB_TREE_TARGET_IB_ALREADY_ON_TREE = "71014";
	public static final String ERR_IB_TREE_TARGET_IB_NO_IB_PROFILE = "71015";
	public static final String ERR_IB_TREE_TARGET_IB_PARENT_IB_NOT_SAME_BRAND_CODE = "71016";
	public static final String ERR_IB_TREE_TARGET_IB_START_DATE_BEFORE_PARENT_IB_START_DATE = "71017";
	public static final String ERR_IB_TREE_PARENT_CODE_IS_NULL = "71018";
	
	
	public static final String ERR_VERIFICATION_IB_CODE_NOT_FOUND = "72001";
	public static final String ERR_VERIFICATION_IB_CODE_EMAIL_NOT_MATCH = "72002";
	public static final String ERR_VERIFICATION_INVALID_TOKEN = "72003";
	public static final String ERR_VERIFICATION_INCORRECT_PASSWORD = "72004";
	public static final String ERR_VERIFICATION_IB_CODE_NOT_MATCH = "72005";
	
	public static final String ERR_IB_LEAD_WITH_SUB_IB_LEAD_IS_NULL = "73001";
	
	public static final String ERR_COMMISSION_CALC_EV_REQUIRED_DATA_IS_NULL = "80001";

	public static final String ERR_IB_CODE_ALREADY_EXIST = "90001";
	public static final String ERR_IB_INVALID_USER_STATUS = "90002";
	public static final String ERR_IB_LOGIN_INCORRECT_USERNAME_PASSWORD = "90003";
	public static final String ERR_IB_ACCOUNT_IS_CLOSED = "90004";
	public static final String ERR_IB_LOGIN_NAME_IS_BLANK = "90005";
	public static final String ERR_IB_LOGIN_PASSWORD_IS_BLANK = "90006";
	
	public static final String ERR_IB_CLIENT_MAP_DATE_RANGE_OVERLAP = "100001";
	public static final String ERR_IB_CLIENT_MAP_NOT_EXIST = "100002";
	public static final String ERR_IB_CLIENT_MAP_NEW_START_DATE_BEFORE_ORIGINAL_START_DATE = "100003";
	public static final String ERR_IB_CLIENT_MAP_ALREADY_EXIST = "100004";
	
	public static final String ERR_CLIENT_DETAIS_ALREADY_EXIST = "110001";
	
	public static final String ERR_CLIENT_BALANCE_SUMMARY_ALREADY_EXIST = "120001";
	
	
	

}
