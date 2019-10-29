package com.henyep.ib.terminal.server.dao;

import java.util.List;

import com.henyep.ib.terminal.api.dto.db.SystemParamsBean;
import com.henyep.ib.terminal.server.dto.mt4.model.Mt4WebServiceConnectionModel;
import com.henyep.ib.terminal.server.dto.mt4.model.Mt5WebServiceConnectionModel;

public interface SystemParamsDao {
	public void saveSystemParams(final SystemParamsBean systemParams) throws Exception;

	public List<SystemParamsBean> getAllSystemParamss() throws Exception;

	public List<SystemParamsBean> getSystemParamsByKey(String name) throws Exception;

	public int updateSystemParams(SystemParamsBean systemParams) throws Exception;

	public int deleteSystemParams(String name) throws Exception;

	public Double getMinMarginOutAmount() throws Exception;

	public Integer getFreeMarginOutCount() throws Exception;

	public Double getSubsequentMarginOutFee() throws Exception;

	public boolean isDataSyncRunning() throws Exception;

	public boolean isUpdatingIbCommission() throws Exception;

	public boolean sendEmailNewUserRequestMarginOut() throws Exception;

	public int updateSystemParamValue(String key, String value, String updatedBy) throws Exception;

	public Mt4WebServiceConnectionModel getMt4ServiceConnectionModel() throws Exception;

	public Mt5WebServiceConnectionModel getMt5ServiceConnectionModel() throws Exception;

}
