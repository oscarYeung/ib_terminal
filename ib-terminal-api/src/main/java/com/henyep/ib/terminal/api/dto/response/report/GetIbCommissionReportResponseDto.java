package com.henyep.ib.terminal.api.dto.response.report;

import java.io.Serializable;

public class GetIbCommissionReportResponseDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6700901254596285530L;
	
	private String file_name;

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
}
