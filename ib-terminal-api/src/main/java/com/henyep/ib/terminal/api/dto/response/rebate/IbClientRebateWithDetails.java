package com.henyep.ib.terminal.api.dto.response.rebate;

import java.io.Serializable;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.RebateDetailsBean;

public class IbClientRebateWithDetails implements Serializable {

	private static final long serialVersionUID = -2827793194504352698L;

	private String rebate_Code;
	private String spread_type;
	private String client_package_code;
	private List<RebateDetailsBean> rebateDetails;

	public String getRebate_Code() {
		return rebate_Code;
	}

	public void setRebate_Code(String rebate_Code) {
		this.rebate_Code = rebate_Code;
	}

	public String getSpread_type() {
		return spread_type;
	}

	public void setSpread_type(String spread_type) {
		this.spread_type = spread_type;
	}

	public String getClient_package_code() {
		return client_package_code;
	}

	public void setClient_package_code(String client_package_code) {
		this.client_package_code = client_package_code;
	}

	public List<RebateDetailsBean> getRebateDetails() {
		return rebateDetails;
	}

	public void setRebateDetails(List<RebateDetailsBean> rebateDetails) {
		this.rebateDetails = rebateDetails;
	}

}
