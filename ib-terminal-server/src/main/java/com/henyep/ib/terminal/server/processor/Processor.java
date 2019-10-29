package com.henyep.ib.terminal.server.processor;

import java.util.Date;
import java.util.List;

public interface Processor<T> {

	List<T> calculate(String updatedBy, Date startDate, Date endDate) throws Exception;

	String getProcessorType();
}
