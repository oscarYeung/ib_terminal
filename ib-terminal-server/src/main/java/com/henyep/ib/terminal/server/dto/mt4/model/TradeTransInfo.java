package com.henyep.ib.terminal.server.dto.mt4.model;

import com.google.gson.annotations.SerializedName;

public class TradeTransInfo {
	@SerializedName("Cmd")
	private Integer cmd;
	@SerializedName("Comment")
	private String comment;
	@SerializedName("Crc")
	private String crc;
	@SerializedName("Expiration")
	private Integer expiration;
	@SerializedName("Ie_deviation")
	private Integer ieDeviation;
	@SerializedName("Order")
	private Integer order;
	@SerializedName("Orderby")
	private Integer orderBy;
	@SerializedName("Price")
	private Double price;
	@SerializedName("Reserved")
	private String reserved;
	@SerializedName("Sl")
	private Integer sl;
	@SerializedName("Symbol")
	private String symbol;
	@SerializedName("Tp")
	private String tp;
	@SerializedName("Type")
	private Integer type;
	@SerializedName("Volume")
	private String volume;

	public Integer getCmd() {
		return cmd;
	}

	public void setCmd(Integer cmd) {
		this.cmd = cmd;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCrc() {
		return crc;
	}

	public void setCrc(String crc) {
		this.crc = crc;
	}

	public Integer getExpiration() {
		return expiration;
	}

	public void setExpiration(Integer expiration) {
		this.expiration = expiration;
	}

	public Integer getIeDeviation() {
		return ieDeviation;
	}

	public void setIeDeviation(Integer ieDeviation) {
		this.ieDeviation = ieDeviation;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Integer getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

	public Integer getSl() {
		return sl;
	}

	public void setSl(Integer sl) {
		this.sl = sl;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getTp() {
		return tp;
	}

	public void setTp(String tp) {
		this.tp = tp;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

}
