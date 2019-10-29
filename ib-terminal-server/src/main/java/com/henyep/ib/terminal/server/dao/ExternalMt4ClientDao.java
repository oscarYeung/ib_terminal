package com.henyep.ib.terminal.server.dao;
import java.util.List;
import java.util.Date;
import com.henyep.ib.terminal.api.dto.db.ExternalMt4ClientBean;

public interface ExternalMt4ClientDao{
	public void saveExternalMt4Client(final ExternalMt4ClientBean externalMt4Client) throws Exception;

	public List<ExternalMt4ClientBean> getAllExternalMt4Clients() throws Exception;

	public List<ExternalMt4ClientBean> getExternalMt4ClientByKey(int client_account) throws Exception;

	public int updateExternalMt4Client(ExternalMt4ClientBean externalMt4Client) throws Exception;

	public int deleteExternalMt4Client(int client_account) throws Exception;
}
