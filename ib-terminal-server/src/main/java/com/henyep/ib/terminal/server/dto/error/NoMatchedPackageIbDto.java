package com.henyep.ib.terminal.server.dto.error;

import java.util.Date;

import com.henyep.ib.terminal.server.util.DateUtil;

public class NoMatchedPackageIbDto {

	public NoMatchedPackageIbDto(String packageCode, String clientPackageCode, String productGroup, Date tradeDate){
		this.packageCode = packageCode;
		this.clientPackageCode = clientPackageCode;
		this.productGroup = productGroup;
		this.tradeDate = tradeDate;
	}
	
	private String packageCode;
	private String clientPackageCode;
	private String productGroup;
	private Date tradeDate;
	
	public Date getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}
	public String getPackageCode() {
		return packageCode;
	}
	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}
	public String getClientPackageCode() {
		return clientPackageCode;
	}
	public void setClientPackageCode(String clientPackageCode) {
		this.clientPackageCode = clientPackageCode;
	}
	public String getProductGroup() {
		return productGroup;
	}
	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}
	
	public String getKey(){
		String seperator = "@@";
		return packageCode + seperator + clientPackageCode + seperator + productGroup;
	}
	
	public String getErrorMsg(){
		
		return String.format("No matched package found, PackageCode: %s, ClientPackageCode: %s, ProductGroup: %s, TradeDate: %s", 
				this.getPackageCode(), 
				this.getClientPackageCode(), 
				this.getProductGroup(),
				DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, this.getTradeDate()));
		
	}
	
}
