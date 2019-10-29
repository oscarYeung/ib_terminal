package com.henyep.ib.terminal.api.dto.request.client.balance.summary;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.henyep.ib.terminal.api.global.Constants;

public class UpdateAccountBalanceRequestDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1528680138930678133L;

	@NotNull(message = Constants.ERR_COMMON_CLIENT_CODE_IS_BLANK)
	private String client_code;
	
	@NotNull(message = Constants.ERR_COMMON_ACCOUNT_BALANCE_IS_BLANK)
	private Double account_balance;

	public String getClient_code() {
		return client_code;
	}

	public void setClient_code(String client_code) {
		this.client_code = client_code;
	}

	public Double getAccount_balance() {
		return account_balance;
	}

	public void setAccount_balance(Double account_balance) {
		this.account_balance = account_balance;
	}
	
	
	
}
