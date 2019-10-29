package com.henyep.ib.terminal.server.repository;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.server.dao.IbTreeDao;

@Repository(value = "UserIbTreeRepository")
@Scope("singleton")
public class UserIbTreeRepository {

	protected final transient Log logger = LogFactory.getLog(getClass());

	// Key : user_code, Value : list of ib
	private Map<String, List<String>> userIbMap;

	private Date lastRefreshTime = new Date();
	
	@Resource(name = "IbTreeDao")
	private IbTreeDao ibTreeDao;

	private UserIbTreeRepository() {
		userIbMap = new HashMap<String, List<String>>();
	}

	public void clearMap() {		
		if (userIbMap != null) {			
			lastRefreshTime = new Date();
			userIbMap.clear();
			logger.info("Clear User IB Tree Cache.");
		}
	}
	
	private void resetCache(){		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY,-24);
		Date expiry = calendar.getTime();		
		if(lastRefreshTime.compareTo(expiry)<0){
			clearMap();
		}
	}

	public boolean validateUserIbMap(String user_code, String ib_code) {		
		resetCache();		
		if (!userIbMap.containsKey(user_code)) {
			try {
				List<String> ibTeamHeadIdList = ibTreeDao.getIbTeamHeadByUserCode(user_code);
				if (ibTeamHeadIdList != null && ibTeamHeadIdList.size() > 0) {
					List<String> ibList = ibTreeDao.getIbListByTeamHeadIds(ibTeamHeadIdList);
					if (ibList != null && ibList.size() > 0) {
						userIbMap.put(user_code, ibList);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		List<String> userIbList = userIbMap.get(user_code);
		return userIbList.contains(ib_code);
	}

}
