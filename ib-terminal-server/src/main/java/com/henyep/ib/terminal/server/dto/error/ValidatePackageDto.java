package com.henyep.ib.terminal.server.dto.error;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.henyep.ib.terminal.server.global.Constants;

public class ValidatePackageDto {

	private HashMap<String, NoMatchedRebateIbDto> noMatchedPackageIbMap;
	private HashMap<String, IbTreeContainDiffRebateTypeDto> ibTreeContainDiffRebateTypeMap;
	private List<String> ibTreeNotExist;

	public List<String> getIbTreeNotExist() {
		return ibTreeNotExist;
	}

	public void setIbTreeNotExist(List<String> ibTreeNotExist) {
		this.ibTreeNotExist = ibTreeNotExist;
	}

	public HashMap<String, NoMatchedRebateIbDto> getNoMatchedPackageIbDto() {
		return noMatchedPackageIbMap;
	}

	public void setNoMatchedPackageIbDto(HashMap<String, NoMatchedRebateIbDto> noMatchedPackageIbDto) {
		this.noMatchedPackageIbMap = noMatchedPackageIbDto;
	}

	public HashMap<String, IbTreeContainDiffRebateTypeDto> getIbTreeContainDiffRebateTypeDto() {
		return ibTreeContainDiffRebateTypeMap;
	}

	public void setIbTreeContainDiffRebateTypeDto(HashMap<String, IbTreeContainDiffRebateTypeDto> ibTreeContainDiffRebateTypeDto) {
		this.ibTreeContainDiffRebateTypeMap = ibTreeContainDiffRebateTypeDto;
	}

	public ValidatePackageDto(HashMap<String, NoMatchedRebateIbDto> noMatchedPackageIbDto,
			HashMap<String, IbTreeContainDiffRebateTypeDto> ibTreeContainDiffRebateTypeDto) {
		this.ibTreeContainDiffRebateTypeMap = ibTreeContainDiffRebateTypeDto;
		this.noMatchedPackageIbMap = noMatchedPackageIbDto;
		ibTreeNotExist = new ArrayList<String>();
	}

	public List<String> getErrorMsgs() {
		List<String> errorMsgs = new ArrayList<String>();

		if (noMatchedPackageIbMap.size() > 0) {
			for (NoMatchedRebateIbDto dto : noMatchedPackageIbMap.values()) {
				errorMsgs.add(dto.getErrorMsg());
			}
		}
		if (ibTreeContainDiffRebateTypeMap.size() > 0) {
			for (IbTreeContainDiffRebateTypeDto dto : ibTreeContainDiffRebateTypeMap.values()) {
				errorMsgs.add(dto.getErrorMsg());
			}
		}

		if (ibTreeNotExist.size() > 0) {
			for (String ibCode : ibTreeNotExist) {
				errorMsgs.add(Constants.ERR_REBATE_IB_NO_IB_TREE_FOUND + "|" + ibCode);
			}
		}

		return errorMsgs;
	}
}
