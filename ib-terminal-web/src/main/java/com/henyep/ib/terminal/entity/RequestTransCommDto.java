package com.henyep.ib.terminal.entity;


/**
 * lxf
 * @author Administrator
 *  for transmission json dto
 * @param <T>
 */
public class RequestTransCommDto<T,V> {

	
	private  T ibLoginHeader;
	private  V ibLoginBody;
	public T getIbLoginHeader() {
		return ibLoginHeader;
	}
	public void setIbLoginHeader(T ibLoginHeader) {
		this.ibLoginHeader = ibLoginHeader;
	}
	public V getIbLoginBody() {
		return ibLoginBody;
	}
	public void setIbLoginBody(V ibLoginBody) {
		this.ibLoginBody = ibLoginBody;
	}
	
	
	
}
