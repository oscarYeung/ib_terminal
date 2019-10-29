package com.henyep.ib.terminal.server.processor;

import java.util.Date;

public interface Preparator {

	public void prepareData(String updatedBy , Date startDate, Date endDate) throws Exception;
}
