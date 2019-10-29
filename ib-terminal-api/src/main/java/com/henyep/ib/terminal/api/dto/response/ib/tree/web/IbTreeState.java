package com.henyep.ib.terminal.api.dto.response.ib.tree.web;

import java.io.Serializable;

public class IbTreeState implements Serializable {

	private static final long serialVersionUID = 7796192984586754842L;
	private Boolean opened;
	private Boolean selected;

	public Boolean getOpened() {
		return opened;
	}

	public void setOpened(Boolean opened) {
		this.opened = opened;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

}
