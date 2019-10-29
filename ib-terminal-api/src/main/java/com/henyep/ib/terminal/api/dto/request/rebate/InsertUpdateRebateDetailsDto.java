package com.henyep.ib.terminal.api.dto.request.rebate;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.henyep.ib.terminal.api.dto.db.RebateDetailsBean;
import com.henyep.ib.terminal.api.dto.db.RebateSupplementaryBean;
import com.henyep.ib.terminal.api.global.Constants;

public class InsertUpdateRebateDetailsDto implements Serializable {
	private static final long serialVersionUID = 5786795907771831223L;

	@NotNull(message = Constants.ERR_COMMON_IB_CODE_IS_BLANK)
	private String ib_code;

	@NotNull(message = Constants.ERR_COMMON_CLIENT_PACKAGE_CODE_IS_BLANK)
	private String client_package_code;
	
	@NotNull(message = Constants.ERR_COMMON_SPERAD_TYPE_IS_BLANK)
	private String spread_type;

	@NotNull(message = Constants.ERR_REBATE_DETAILS_IS_NULL)
	@Valid
	private List<RebateDetailsBean> rebateDetails;

	public String getClient_package_code() {
		return client_package_code;
	}

	public void setClient_package_code(String client_package_code) {
		this.client_package_code = client_package_code;
	}

	public String getSpread_type() {
		return spread_type;
	}

	public void setSpread_type(String spread_type) {
		this.spread_type = spread_type;
	}

	public String getIb_code() {
		return ib_code;
	}

	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}

	@Valid
	private List<RebateSupplementaryBean> rebateSupplementaries;

	public List<RebateDetailsBean> getRebateDetails() {
		return rebateDetails;
	}

	public void setRebateDetails(List<RebateDetailsBean> rebateDetails) {
		this.rebateDetails = rebateDetails;
	}

	public List<RebateSupplementaryBean> getRebateSupplementaries() {
		return rebateSupplementaries;
	}

	public void setRebateSupplementaries(List<RebateSupplementaryBean> rebateSupplementaries) {
		this.rebateSupplementaries = rebateSupplementaries;
	}

}
