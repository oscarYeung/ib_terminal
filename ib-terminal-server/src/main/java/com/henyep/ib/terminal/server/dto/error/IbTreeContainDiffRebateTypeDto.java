package com.henyep.ib.terminal.server.dto.error;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.henyep.ib.terminal.api.dto.db.IbTreeBean;
import com.henyep.ib.terminal.server.global.Constants;

public class IbTreeContainDiffRebateTypeDto {
	public IbTreeContainDiffRebateTypeDto(String clientIbCode, List<IbTreeBean> ibTreeBeans, String clientPackageCode, String productGroup){
		this.clientPackageCode = clientPackageCode;
		this.productGroup = productGroup;
		this.clientIbCode = clientIbCode;
		ibCodes = new ArrayList<String>();
		for(IbTreeBean ibTreeBean : ibTreeBeans){
			ibCodes.add(ibTreeBean.getIb_code());
		}
	}
	private String clientIbCode;
	private List<String> ibCodes;
	private String clientPackageCode;
	private String productGroup;
	
	public List<String> getIbCodes() {
		return ibCodes;
	}
	public void setIbCodes(List<String> ibCodes) {
		this.ibCodes = ibCodes;
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
	public String getClientIbCode() {
		return clientIbCode;
	}
	public void setClientIbCode(String clientIbCode) {
		this.clientIbCode = clientIbCode;
	}
	
	public String getKey(){
		String seperator = "@@";
		return clientIbCode + seperator + clientPackageCode + seperator + productGroup;
	}
	
	public String getErrorMsg(){
		
		String ibCodesString = StringUtils.join(ibCodes, "->");
						 
		return Constants.ERR_REBATE_IB_TREE_HAS_MULTIPLE_REBATE_TYPES 
				+ "|" + ibCodesString
				+ "|" + this.clientIbCode
				+ "|" + this.getClientPackageCode()
				+ "|" + this.getProductGroup();
		
		
	}
}
