package com.henyep.ib.terminal.server.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.PermissionBean;
import com.henyep.ib.terminal.server.dao.PermissionDao;

@Repository(value = "UserPermissionRepository")
@Scope("singleton")
public class UserPermissionRepository {

	protected final transient Log logger = LogFactory.getLog(getClass());

	// Key : user_code, Value : list of permission id
	private Map<String, List<Integer>> userPermissionMap;

	@Resource(name = "PermissionDao")
	private PermissionDao permissionDao;

	private UserPermissionRepository() {
		userPermissionMap = new HashMap<String, List<Integer>>();
	}

	public void clearMap(){
		if(userPermissionMap!=null){
			userPermissionMap.clear();
		}
	}
	
	public List<Integer> getPermission(String user_code) {
		if (!userPermissionMap.containsKey(user_code)) {
			try {
				List<PermissionBean> list = permissionDao.getPermissionListByActiveUser(user_code);
				List<Integer> permissionList = new ArrayList<Integer>();
				for (PermissionBean model : list) {
					permissionList.add(model.getId());
				}
				userPermissionMap.put(user_code, permissionList);
			} catch (Exception e) {
				logger.error(e, e);
			}
		}

		return userPermissionMap.get(user_code);
	}

}
