package com.henyep.ib.terminal.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.henyep.ib.terminal.api.dto.db.PermissionBean;
import com.henyep.ib.terminal.server.dao.PermissionDao;
import com.henyep.ib.terminal.server.service.UserPermissionService;

@Service(value = "UserPermissionService")
public class UserPermissionServiceImpl extends AbstractServiceImpl implements UserPermissionService {

	@Resource(name = "PermissionDao")
	private PermissionDao permissionDao;

	@Override
	public List<PermissionBean> getPermissionListByActiveUser(String user_code) {
		List<PermissionBean> list = null;
		try {
			list = permissionDao.getPermissionListByActiveUser(user_code);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e, e);
			list = null;
		}
		return list;
	}

}
