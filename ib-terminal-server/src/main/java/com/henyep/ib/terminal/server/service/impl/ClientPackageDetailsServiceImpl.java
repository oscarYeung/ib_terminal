package com.henyep.ib.terminal.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.henyep.ib.terminal.api.dto.db.ClientPackageDetailsBean;
import com.henyep.ib.terminal.api.dto.db.RebateDetailsBean;
import com.henyep.ib.terminal.api.dto.response.clientPackageDetails.ClientPackageSpreadTypeDto;
import com.henyep.ib.terminal.server.dao.ClientPackageDetailsDao;
import com.henyep.ib.terminal.server.dao.RebateDetailsDao;
import com.henyep.ib.terminal.server.service.ClientPackageDetailsService;
import com.henyep.ib.terminal.server.util.RebateUtil;

@Service(value = "ClientPackageDetailsService")
public class ClientPackageDetailsServiceImpl implements ClientPackageDetailsService {

	private ClientPackageDetailsDao clientPackageDetailsDao;
	private RebateDetailsDao rebateDetailsDao;

	@Autowired
	public ClientPackageDetailsServiceImpl(ClientPackageDetailsDao clientPackageDetailsDao, RebateDetailsDao rebateDetailsDao) {
		this.clientPackageDetailsDao = clientPackageDetailsDao;
		this.rebateDetailsDao = rebateDetailsDao;
	}

	@Override
	public List<String> getAllClientPackages() throws Exception {
		List<String> clientPackages = new ArrayList<String>();

		List<ClientPackageDetailsBean> beans = clientPackageDetailsDao.getAllClientPackageDetailss();
		for (ClientPackageDetailsBean bean : beans) {
			clientPackages.add(bean.getClient_package_code());
		}
		return clientPackages;
	}

	@Override
	public List<ClientPackageSpreadTypeDto> getClientPackagesByIbCode(String ibCode) throws Exception {
		String rebateCode = RebateUtil.getRebateCode(ibCode);
		return rebateDetailsDao.getClientPackageCodesByRebateCode(rebateCode);
	}

}
