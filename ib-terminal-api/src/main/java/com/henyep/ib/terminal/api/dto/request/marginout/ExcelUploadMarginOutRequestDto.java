package com.henyep.ib.terminal.api.dto.request.marginout;

import java.io.Serializable;

public class ExcelUploadMarginOutRequestDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4792326696227058509L;

	private String brand_code;
	
	private byte[] excelBytes;

	public byte[] getExcelBytes() {
		return excelBytes;
	}

	public void setExcelBytes(byte[] excelBytes) {
		this.excelBytes = excelBytes;
	}
	
	// xls or xlsx
	private String extension;

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getBrand_code() {
		return brand_code;
	}

	public void setBrand_code(String brand_code) {
		this.brand_code = brand_code;
	}
	
	
	
	
	

	
	
	
}
