package com.henyep.ib.terminal.api.dto.db;

public class ClientPackageDetailsBean {

	private String client_package_code;
	private Double base_commission;

	public void setClient_package_code(String client_package_code) {
		this.client_package_code = client_package_code;
	}

	public String getClient_package_code() {
		return client_package_code;
	}

	public void setBase_commission(Double base_commission) {
		this.base_commission = base_commission;
	}

	public Double getBase_commission() {
		return base_commission;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (client_package_code != null)
			builder.append("client_package_code: " + client_package_code + ", ");
		builder.append("base_commission: " + base_commission + ", ");
		return builder.toString();
	}
}
