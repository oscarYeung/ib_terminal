package com.henyep.ib.terminal.utils;



import javax.servlet.http.HttpServletRequest;

import com.henyep.ib.terminal.api.dto.communal.ConstantDefineCode;
import com.henyep.ib.terminal.api.dto.request.BaseRequestHeader;

public class HeaderUtil {

	public static BaseRequestHeader getBaseHeader(HttpServletRequest request){
		BaseRequestHeader baseHeader = new BaseRequestHeader();
		baseHeader.setMessageDate(TimeUtil.getTimeLong());
		baseHeader.setChannelId(ConstantDefineCode.CHANNEL_WEB);
		baseHeader.setIpAddress(request.getLocalAddr());
		baseHeader.setLanguage(ConstantDefineCode.SYS_LANGUAGE_ENGLISH);
		baseHeader.setMessageType(ConstantDefineCode.MESSAGE_TYPE_IB_REGISTER);
		Object secrectkey=request.getSession().getAttribute(ConstantFields.IB_SECRET_KEY);
		if(null!=secrectkey&&!"".equals(secrectkey)){
			baseHeader.setSecretKey(secrectkey.toString());
		}else{
			baseHeader.setSecretKey("");
		}
		
		return baseHeader;
	}
	
	
	
	public static BaseRequestHeader  getIbLoginHeader(HttpServletRequest request ){
		BaseRequestHeader header = getBaseHeader(request);
		header.setMessageType(ConstantDefineCode.MESSAGE_TYPE_IB_LOGIN);
		return header;
	}
	public static BaseRequestHeader  getRegHeader(HttpServletRequest request ){
		BaseRequestHeader header = getBaseHeader(request);
		header.setMessageType(ConstantDefineCode.MESSAGE_TYPE_IB_REGISTER);
		return header;
	}
	
	public static BaseRequestHeader  getProfileHeader(HttpServletRequest request ){
		BaseRequestHeader header = getBaseHeader(request);
		header.setMessageType(ConstantDefineCode.MESSAGE_TYPE_IB_UPDATE_PROFILE);
		return header;
	}
	
	public static BaseRequestHeader  getClientLinkHeader(HttpServletRequest request ){
		BaseRequestHeader header = getBaseHeader(request);
		header.setMessageType(ConstantDefineCode.MESSAGE_TYPE_IB_CLIENT_LINK);
		return header;
	}
	public static BaseRequestHeader  getRebateHeader(HttpServletRequest request ){
		BaseRequestHeader header = getBaseHeader(request);
		header.setMessageType(ConstantDefineCode.MESSAGE_TYPE_IB_REBATE_RANGE);
		return header;
	}
	public static BaseRequestHeader  getIbTreeHeader(HttpServletRequest request ){
		BaseRequestHeader header = getBaseHeader(request);
		header.setMessageType(ConstantDefineCode.MESSAGE_TYPE_IB_TEAM_TREE);
		return header;
	}
	
	public static BaseRequestHeader  getIbWithdrawerHeader(HttpServletRequest request ){
		BaseRequestHeader header = getBaseHeader(request);
		header.setMessageType(ConstantDefineCode.MESSAGE_TYPE_IB_WITHDRAWAL);
		return header;
	}
	public static BaseRequestHeader  getWithdrawerHistoryHeader(HttpServletRequest request ){
		BaseRequestHeader header = getBaseHeader(request);
		header.setMessageType(ConstantDefineCode.MESSAGE_TYPE_WITHDRAWAL_HISTROY);
		return header;
	}
	public static BaseRequestHeader  getCancleWithdrawerHeader(HttpServletRequest request ){
		BaseRequestHeader header = getBaseHeader(request);
		header.setMessageType(ConstantDefineCode.MESSAGE_TYPE_WITHDRAWAL_CANCLE);
		return header;
	}
	public static BaseRequestHeader  getIbSummaryHeader(HttpServletRequest request ){
		BaseRequestHeader header = getBaseHeader(request);
		header.setMessageType(ConstantDefineCode.MESSAGE_TYPE_IB_SUMMARY);
		return header;
	}
	public static BaseRequestHeader  getRebateCodeHeader(HttpServletRequest request ){
		BaseRequestHeader header = getBaseHeader(request);
		header.setMessageType(ConstantDefineCode.MESSAGE_TYPE_IB_REBATE_CODE);
		return header;
	}
	
	public static BaseRequestHeader  getPasswordHeader(HttpServletRequest request ){
		BaseRequestHeader header = getBaseHeader(request);
		header.setMessageType(ConstantDefineCode.MESSAGE_TYPE_IB_FORGET_PASSWORD);
		return header;
	}
	
	public static BaseRequestHeader  getSetPasswordHeader(HttpServletRequest request ){
		BaseRequestHeader header = getBaseHeader(request);
		header.setMessageType(ConstantDefineCode.MESSAGE_TYPE_IB_RESET_PASSWORD);
		return header;
	}
	public static BaseRequestHeader  getChangePasswordHeader(HttpServletRequest request ){
		BaseRequestHeader header = getBaseHeader(request);
		header.setMessageType(ConstantDefineCode.MESSAGE_TYPE_IB_CHANGE_PASSWORD);
		return header;
	}
	
	public static BaseRequestHeader  getIbSummaryByDateHeader(HttpServletRequest request ){
		BaseRequestHeader header = getBaseHeader(request);
		header.setMessageType(ConstantDefineCode.MESSAGE_TYPE_IB_SUMMARY_TODAY);
		return header;
	}
	
	public static BaseRequestHeader  getClientSummaryHeader(HttpServletRequest request ){
		BaseRequestHeader header = getBaseHeader(request);
		header.setMessageType(ConstantDefineCode.MESSAGE_TYPE_IB_CLIENT_SUMMARY);
		return header;
	}
	
	public static BaseRequestHeader  getClientProfileHeader(HttpServletRequest request ){
		BaseRequestHeader header = getBaseHeader(request);
		
		header.setMessageType(ConstantDefineCode.MESSAGE_TYPE_IB_CLIENT_PROFILE);
		return header;
	}
	public static BaseRequestHeader  getClientMarginOutHeader(HttpServletRequest request ){
		BaseRequestHeader header = getBaseHeader(request);
		header.setMessageType(ConstantDefineCode.MESSAGE_TYPE_CLIENT_MARGINOUT);
		return header;
	}
	public static BaseRequestHeader  getClientTradingHistoryHeader(HttpServletRequest request ){
		BaseRequestHeader header = getBaseHeader(request);
		header.setMessageType(ConstantDefineCode.MESSAGE_TYPE_CLIENT_TRADING_HISTORY);
		return header;
	}
	
	public static BaseRequestHeader  getClientLeadsHeader(HttpServletRequest request ){
		BaseRequestHeader header = getBaseHeader(request);
		header.setMessageType(ConstantDefineCode.MESSAGE_TYPE_CLIENT_LEADS);
		return header;
	}
	
	public static BaseRequestHeader getMaxWithdrawalHeader(HttpServletRequest request){
		BaseRequestHeader header = getBaseHeader(request);
		header.setMessageType(ConstantDefineCode.MESSAGE_TYPE_WHITE_LABEL_GET_MAX_WITHDRAWAL);
		return header;
	}
	
	public static BaseRequestHeader getMasterAccMaxWithdrawalHeader(HttpServletRequest request){
		BaseRequestHeader header = getBaseHeader(request);
		header.setMessageType(ConstantDefineCode.MESSAGE_TYPE_WHITE_LABEL_GET_MASTER_ACC_MAX_WITHDRAWAL);
		return header;
	}
	
	public static BaseRequestHeader getDepositHeader(HttpServletRequest request){
		BaseRequestHeader header = getBaseHeader(request);
		header.setMessageType(ConstantDefineCode.MESSAGE_TYPE_WHITE_LABEL_DEPOSIT);
		return header;
	}
	
	public static BaseRequestHeader getWithdrawalHeader(HttpServletRequest request){
		BaseRequestHeader header = getBaseHeader(request);
		header.setMessageType(ConstantDefineCode.MESSAGE_TYPE_WHITE_LABEL_DEPOSIT);
		return header;
	}
	
	public static BaseRequestHeader getUploadFilePathByCreateTimeHeader(HttpServletRequest request){
		BaseRequestHeader header = getBaseHeader(request);
		header.setMessageType(ConstantDefineCode.MESSAGE_TYPE_WHITE_LABEL_UPLOAD_FILE_PATH_BY_CREATE_TIME);
		return header;
	}
	
	public static BaseRequestHeader getDownloadFileHeader(HttpServletRequest request){
		BaseRequestHeader header = getBaseHeader(request);
		header.setMessageType(ConstantDefineCode.MESSAGE_TYPE_WHITE_LABEL_DOWNLOAD_FILE_HEADER);
		return header;
	}
	

}
