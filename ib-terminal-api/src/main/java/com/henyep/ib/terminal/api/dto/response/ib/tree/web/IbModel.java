package com.henyep.ib.terminal.api.dto.response.ib.tree.web;

import java.io.Serializable;

public class IbModel implements Serializable {
	private static final long serialVersionUID = 6163006188738410846L;
	private String id;
	private String parent;
	private String text;
	private IbTreeState state;
	private boolean has_child;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public IbTreeState getState() {
		return state;
	}

	public void setState(IbTreeState state) {
		this.state = state;
	}

	public boolean isHas_child() {
		return has_child;
	}

	public void setHas_child(boolean has_child) {
		this.has_child = has_child;
	}
	
	
	
}

