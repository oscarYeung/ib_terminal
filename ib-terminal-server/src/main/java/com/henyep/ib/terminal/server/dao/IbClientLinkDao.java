package com.henyep.ib.terminal.server.dao;

import java.util.List;

import com.henyep.ib.terminal.api.dto.db.IbClientLinkBean;

public interface IbClientLinkDao {
	public int saveIbClientLink(final IbClientLinkBean ibClientLink) throws Exception;

	public List<IbClientLinkBean> getAllIbClientLinks() throws Exception;

	public IbClientLinkBean getIbClientLinkByKey(String client_package_code, String ib_code, String url, String campaign_id) throws Exception;

	public int updateIbClientLink(IbClientLinkBean ibClientLink) throws Exception;

	public int deleteIbClientLink(String client_package_code, String ib_code, String url, String campaign_id) throws Exception;

	public List<IbClientLinkBean> getByBrandCodeAndIbCode(String brand_code, String ib_code);

	public int getMaxSeqId(String brand_code, String ib_code);
}
