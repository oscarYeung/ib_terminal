package com.henyep.ib.terminal.server.util;

import com.henyep.ib.terminal.api.global.Constants;

public class MarginInUtil {

		
	public static String GetCategoryDescription(String category){
		if(category.equals(Constants.MARGIN_IN_CATEGORY_DEPOSIT)){
			return "Deposit";
		}
		else if(category.equals(Constants.MARGIN_IN_CATEGORY_BONUS)){
			return "Bonus";
		}
		else if(category.equals(Constants.MARGIN_IN_CATEGORY_REBATE)){
			return "Rebate";
		}
		else if(category.equals(Constants.MARGIN_IN_CATEGORY_INTERNAL_TRANSFER)){
			return "Internal Transfer";
		}
		else if(category.equals(Constants.MARGIN_IN_CATEGORY_ADJUSTMENT)){
			return "Adjustment";
		}
		else {
			return category;
		}
		
	}
	
	public static String GetStatusDescription(String status){

		if(status.equals(Constants.MARGIN_IN_STATUS_PENDING)){
			return "Pending";
		}
		else if(status.equals(Constants.MARGIN_IN_STATUS_EXECUTED)){
			return "Executed";
		}
		else if(status.equals(Constants.MARGIN_IN_STATUS_REJECTED)){
			return "Rejected";
		}
		else if(status.equals(Constants.MARGIN_IN_STATUS_CANCELLED)){
			return "Cancelled";
		}
		else if(status.equals(Constants.MARGIN_IN_STATUS_DELETED)){
			return "Deleted";
		}
		else {
			return status;
		}
		
	}
	
}
