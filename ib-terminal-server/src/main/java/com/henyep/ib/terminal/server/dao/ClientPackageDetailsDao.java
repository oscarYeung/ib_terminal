package com.henyep.ib.terminal.server.dao;
import java.util.List;
import java.util.Date;
import com.henyep.ib.terminal.api.dto.db.ClientPackageDetailsBean;

public interface ClientPackageDetailsDao{
	public void saveClientPackageDetails(final ClientPackageDetailsBean clientPackageDetails) throws Exception;

	public List<ClientPackageDetailsBean> getAllClientPackageDetailss() throws Exception;

	public List<ClientPackageDetailsBean> getClientPackageDetailsByKey(String client_package_code) throws Exception;

	public int updateClientPackageDetails(ClientPackageDetailsBean clientPackageDetails) throws Exception;

	public int deleteClientPackageDetails(String client_package_code) throws Exception;
}
