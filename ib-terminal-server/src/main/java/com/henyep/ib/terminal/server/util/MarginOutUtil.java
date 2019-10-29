package com.henyep.ib.terminal.server.util;

import com.henyep.ib.terminal.api.global.Constants;

public class MarginOutUtil {

	public static String GetMethodDescription(String method){
	
		if(method.equals(Constants.MARGIN_OUT_METHOD_ADJUSTMENT)){
			return "Adjustment";
		}
		else if(method.equals(Constants.MARGIN_OUT_METHOD_BANK_TRANSFER)){
			return "Bank Transfer";
		}
		else if(method.equals(Constants.MARGIN_OUT_METHOD_PAYMENT_GATEWAY)){
			return "Payment Gateway";
		}
		else if(method.equals(Constants.MARGIN_OUT_METHOD_TO_TRADE_ACCOUNT)){
			return "To Trade Account";
		}
		else if(method.equals(Constants.MARGIN_OUT_METHOD_INTERNAL_ACCOUNT_TRANSFER)){
			return "Internal Account Transfer";
		}
		else if(method.equals(Constants.MARGIN_OUT_METHOD_FEE)){
			return "Fee";
		}
		else {
			return method;
		}
		
	}
	
	public static String GetStatusDescription(String status){

		if(status.equals(Constants.MARGIN_OUT_STATUS_PENDING)){
			return "Pending";
		}
		else if(status.equals(Constants.MARGIN_OUT_STATUS_EXECUTED)){
			return "Executed";
		}
		else if(status.equals(Constants.MARGIN_OUT_STATUS_REJECTED)){
			return "Rejected";
		}
		else if(status.equals(Constants.MARGIN_OUT_STATUS_CANCELLED)){
			return "Cancelled";
		}
		else if(status.equals(Constants.MARGIN_OUT_STATUS_DELETED)){
			return "Deleted";
		}
		else {
			return status;
		}
		
	}
	
	public static String GetCategoryDescription(String category){

		if(category.equals(Constants.MARGIN_OUT_CATEGORY_USER_REQUEST)){
			return "User Request";
		}
		else if(category.equals(Constants.MARGIN_OUT_CATEGORY_ADJUSTMENT)){
			return "Adjustment";
		}
		else if(category.equals(Constants.MARGIN_OUT_CATEGORY_EXCEL_UPLOAD)){
			return "Excel Upload";
		}
		else if(category.equals(Constants.MARGIN_OUT_CATEGORY_FEE)){
			return "Fee";
		}
		else {
			return category;
		}	
	}	
}
