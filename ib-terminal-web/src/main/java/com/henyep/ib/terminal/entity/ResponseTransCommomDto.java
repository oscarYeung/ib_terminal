package com.henyep.ib.terminal.entity;


public class ResponseTransCommomDto <T,V> {

	private T header;
	private V body;
	public T getHeader() {
		return header;
	}
	public void setHeader(T header) {
		this.header = header;
	}
	public V getBody() {
		return body;
	}
	public void setBody(V body) {
		this.body = body;
	}
	
	
	
}
