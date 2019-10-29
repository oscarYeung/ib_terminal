package com.henyep.ib.terminal.utils;

public class ConstantFields {

	//post parameter
	public static final String SERVER_TIMEOUT="2000";
	public static final String SERVERAPI_NAME="ibserver.henyep.com";//ibserver.hycm.com
	//public static final String SERVERAPI_NAME="192.168.1.62:8080";
	/*public static final String Login_PATH="IbTerminalWeb/login.hyml";*/
	public static final String Login_PATH="ib/login";
	public static final String Reg_PATH="ibuser/regist";
	public static final String PROFILE_PATH="ibuser/getprofile";
	public static final String PROFILEEDIT_PATH="ib/edit";
	
	public static final String IB_RABATE_PATH="rebate/getRebateByIbCodeWithDateRange";
	public static final String CLIENT_LINK_PATH="ibClientLink/getLinks";
	public static final String IB_TREE_PATH="ibTree/getByTeamHead";
	public static final String IB_TRIMMED_SUMMARY_PATH="ibCommission/getTrimmedIbSummary";

	public static final String IB_WITHDRAWAL_MAX_PATH="marginOut/getMaxWithdrawal";
	public static final String IB_WITHDRAWAL_INSERT_PATH="marginOut/insertPendingMarginOut";

	public static final String FORGETPASSWORD_PATH="password/ibForgotPassword";
	public static final String PASSWORD_TOKEN_PATH="password/verifyToken";
	public static final String SETPASSWORD_PATH="password/resetPassword";
	public static final String CHANGE_PASSWORD_PATH="password/changePassword";

	public static final String IB_COMMISSION_SUMMARY_PATH="ibCommissionSummary/getByTeamHead";
	public static final String IB_COMMISSION_CLIENT_SUMMARY_PATH="ibCommissionClientSummary/getByIbCode";
	public static final String IB_COMMISSION_CLIENT_DETAILS_PATH="ibCommissionDetails/getDetails";
	
	
	public static final String IB_SUMMARY_PATH="ibCommission/getIbSummary";
	public static final String IB_GET_CLIENT_SUMMARY_PATH="ibCommission/getIbClientSummary";
	public static final String IB_CLIENT_PROFILE_PATH="clientProfile/getClientProfile";
	
	
	public static final String IB_CLIENT_MARGIN_OUT_PATH="clientMarginInOut/getByDateRange";
	public static final String IB_CLIENT_TRADE_HISTORY_PATH="trade/getTradeHistory";
	public static final String IB_CLIENT_IB_CODES_PATH="ibClientMap/getByIbCodeDateRange";
	
	public static final String IB_WITHDRAWAL_MARGINOUT_PATH="marginOut/getMarginOut";
	public static final String IB_WITHDRAWAL_CANCEL_MARGINOUT_PATH="marginOut/cancelMarginOut";
	
	public static final String IB_LEADS_PATH="ibLead/getLeads";
	
	public static final String IB_NEW_COMMISSION_SUMMARY_PATH="/ibCommission/getIbCommissionSummary";
	public static final String IB_COMMISSION_SUMMARY_DETAIL_PATH="/ibCommission/getIbCommissionSummaryDetails";
	
	public static final String BALANCE_MAX_WITHDRAWAL_PATH="balanceTransfer/getMaxWithdrawal";
	public static final String BALANCE_MAX_MASTER_WITHDRAWAL_PATH="balanceTransfer/getMasterAccountMaxWithdrawal";
	public static final String BALANCE_DEPOSIT_PATH="balanceTransfer/deposit";
	public static final String BALANCE_WITHDRAWAL_PATH="balanceTransfer/withdrawal";
	public static final String GET_UPLOAD_FILE_PATH="registration/getAccountsByDate";
	public static final String GET_CLIENT_INFOS="registration/exportRegisteredAccount";
	public static final String UPLOADED_FILE_PATH="uploadedFilePath/updateUploadedFilePath";
	public static final String GET_DOWNLOAD_FILE_PATH="document/download";
	//contant session
	public static final String IB_USERNAME="ibUsername";
	public static final String IB_CODE="ibCode";
	public static final String IB_STATE="ibCode";
	public static final String IB_BRANDCODE="brandCode";
	public static final String IB_EMAIL="ibEmail";
	public static final String IB_ERROR="IbRegError";
	public static final String IB_PASSWORD="ibPassword";
	
	public static final String IB_PASSWORD_TYPE="I";
	public static final String IB_PASSWORD_TOKEN="passwordtoken";
	public static final String IB_SECRET_KEY="SecretKey";
	
	public static final String IB_IS_WHITE_LABEL_USER="IsWhiteLabelUser";
	public static final String WL_SERVER_CODE="serverCode";
	public static final String WL_COMPANY_CODE="companyCode";
	public static final String WL_REGISTRATION_TYPE="registrationType";
	public static final String WL_PLATFORM="platform";
	
	public static final String IB_WITHDRAWAL_CURRENCY="withdrawalCurrency";
	//index
	public static final String 	ACCOUNT_MODEL="accountModel";
	public static final String CLIENT_ACCOUNT_MODEL="ClientAccountModel";
	public static final String BALANCE_MODEL="balanceModel";
	
	public static final String WITHDRAWAL_CATEGORY="U";
	public static final String WITHDRAWAL_METHOD="BK";
	public static final String FAIL="FAIL";
	public static final String SUCCESS="SUCCESS";
	public static final String STATUS="status";
	public static final String LOGIN="LOGIN";
	public static final String 	ERROR_10015="10015";
	public static final String ERROR_10033="10033";
	
	public static final String  IB_ENGLISH="english";
	public static final String  IB_CHINESE="chinese";
	public static final String  IB_SC="SC";
	public static final String  IB_EN="EN";
	public static final String CURRENT_LANGUAGE="currentLanguage";
	
	public static final String Connection_ERROR="connection.error";
	
	public static final String CONFIG_INFO="configinfo";
}
