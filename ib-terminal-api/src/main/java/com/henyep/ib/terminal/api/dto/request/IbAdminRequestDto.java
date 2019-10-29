package com.henyep.ib.terminal.api.dto.request;

public abstract class IbAdminRequestDto<T> extends IbRequestDto<T> {

	private static final long serialVersionUID = -8271035292649446236L;

	public abstract Integer getPermission_code();
}
