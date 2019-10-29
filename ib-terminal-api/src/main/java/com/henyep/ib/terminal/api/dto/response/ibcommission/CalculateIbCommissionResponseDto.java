package com.henyep.ib.terminal.api.dto.response.ibcommission;

import java.io.Serializable;
import java.util.HashMap;

public class CalculateIbCommissionResponseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3078197337658435239L;

	private HashMap<String, String> ErrorMsgs = new HashMap<String, String>();

	private int NumOfIbCommissionCreated;
	private int NumOfMarginInCreated;
	private int NumOfIbCommissionDeleted;
	private int NumOfMarginInDeleted;

	public int getNumOfIbCommissionDeleted() {
		return NumOfIbCommissionDeleted;
	}

	public void setNumOfIbCommissionDeleted(int numOfIbCommissionDeleted) {
		NumOfIbCommissionDeleted = numOfIbCommissionDeleted;
	}

	public int getNumOfMarginInDeleted() {
		return NumOfMarginInDeleted;
	}

	public void setNumOfMarginInDeleted(int numOfMarginInDeleted) {
		NumOfMarginInDeleted = numOfMarginInDeleted;
	}

	public int getNumOfIbCommissionCreated() {
		return NumOfIbCommissionCreated;
	}

	public void setNumOfIbCommissionCreated(int numOfIbCommissionCreated) {
		NumOfIbCommissionCreated = numOfIbCommissionCreated;
	}

	public int getNumOfMarginInCreated() {
		return NumOfMarginInCreated;
	}

	public void setNumOfMarginInCreated(int numOfMarginInCreated) {
		NumOfMarginInCreated = numOfMarginInCreated;
	}

	public HashMap<String, String> getErrorMsgs() {
		return ErrorMsgs;
	}

	public void setErrorMsgs(HashMap<String, String> errorMsgs) {
		ErrorMsgs = errorMsgs;
	}

}
