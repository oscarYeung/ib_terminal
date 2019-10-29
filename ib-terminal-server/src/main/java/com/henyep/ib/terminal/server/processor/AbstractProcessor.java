package com.henyep.ib.terminal.server.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractProcessor<T> implements Processor<T> {

	protected final transient Log logger = LogFactory.getLog(getClass());
	protected final String processor_type;

	public AbstractProcessor(String processor_type) {
		this.processor_type = processor_type;
	}

	public String getProcessorType() {
		return processor_type;
	}

}
