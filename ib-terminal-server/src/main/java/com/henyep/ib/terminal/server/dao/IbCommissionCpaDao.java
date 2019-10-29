package com.henyep.ib.terminal.server.dao;
import java.util.List;
import java.util.Date;
import com.henyep.ib.terminal.api.dto.db.IbCommissionCpaBean;

public interface IbCommissionCpaDao{
	public void saveIbCommissionCpa(final IbCommissionCpaBean ibCommissionCpa) throws Exception;

	public List<IbCommissionCpaBean> getAllIbCommissionCpas() throws Exception;

	public List<IbCommissionCpaBean> getIbCommissionCpaByKey(String brand_code, String ib_code, String client_code) throws Exception;

	public int updateIbCommissionCpa(IbCommissionCpaBean ibCommissionCpa) throws Exception;

	public int deleteIbCommissionCpa(String brand_code, String ib_code, String client_code) throws Exception;
	
	public int deleteIbCommissionCpa(Date tradeDate) throws Exception;
	
	public List<String> getClientListByIbList(List<String> ibList) throws Exception;
}
