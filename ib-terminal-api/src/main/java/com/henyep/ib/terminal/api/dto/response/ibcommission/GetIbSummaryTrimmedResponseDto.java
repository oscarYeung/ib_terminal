package com.henyep.ib.terminal.api.dto.response.ibcommission;

import java.io.Serializable;
import java.util.List;

public class GetIbSummaryTrimmedResponseDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5863540351411882793L;

	private IbAccountModel ibAccountModel;
	private IbMonthBalanceModel ibMonthBalanceModel;
	private List<IbClientAccountModel> ibClientAccountModels;
	private List<TradeAccountModel> tradeAccountList;
	private Double totalClientsBalance = 0.0;
	private Integer numOfClients = 0;
	
	public IbAccountModel getIbAccountModel() {
		return ibAccountModel;
	}
	public void setIbAccountModel(IbAccountModel ibAccountModel) {
		this.ibAccountModel = ibAccountModel;
	}
	public IbMonthBalanceModel getIbMonthBalanceModel() {
		return ibMonthBalanceModel;
	}
	public void setIbMonthBalanceModel(IbMonthBalanceModel ibMonthBalanceModel) {
		this.ibMonthBalanceModel = ibMonthBalanceModel;
	}
	public List<IbClientAccountModel> getIbClientAccountModels() {
		return ibClientAccountModels;
	}
	public void setIbClientAccountModels(List<IbClientAccountModel> ibClientAccountModels) {
		this.ibClientAccountModels = ibClientAccountModels;
	}
	
	public List<TradeAccountModel> getTradeAccountList() {
		return tradeAccountList;
	}
	public void setTradeAccountList(List<TradeAccountModel> tradeAccountList) {
		this.tradeAccountList = tradeAccountList;
	}
	public Double getTotalClientsBalance() {
		return totalClientsBalance;
	}
	public void setTotalClientsBalance(Double totalClientsBalance) {
		this.totalClientsBalance = totalClientsBalance;
	}
	public Integer getNumOfClients() {
		return numOfClients;
	}
	public void setNumOfClients(Integer numOfClients) {
		this.numOfClients = numOfClients;
	}
	
}
