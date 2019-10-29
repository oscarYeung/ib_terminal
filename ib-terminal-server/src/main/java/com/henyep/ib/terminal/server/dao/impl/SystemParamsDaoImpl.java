package com.henyep.ib.terminal.server.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.SystemParamsBean;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.server.dao.SystemParamsDao;
import com.henyep.ib.terminal.server.dto.mt4.model.Mt4WebServiceConnectionModel;
import com.henyep.ib.terminal.server.dto.mt4.model.Mt5WebServiceConnectionModel;

@Repository(value = "SystemParamsDao")
public class SystemParamsDaoImpl implements SystemParamsDao {

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "SystemParamsDao_SQLMap")
	Map<String, String> map;

	public SystemParamsDaoImpl() throws Exception {
		super();
	}

	@Override
	public void saveSystemParams(SystemParamsBean systemParams) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[] { systemParams.getName(), systemParams.getDescription(), systemParams.getValue(),
				systemParams.getLast_update_time(), systemParams.getLast_update_user() };
		int res = this.jdbcTemplate.update(sql, objs);

	}

	@Override
	public int updateSystemParams(SystemParamsBean systemParams) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[] { systemParams.getDescription(), systemParams.getValue(), systemParams.getLast_update_time(),
				systemParams.getLast_update_user(), systemParams.getName() };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<SystemParamsBean> getAllSystemParamss() throws Exception {
		String sql = map.get("selectAll");
		List<SystemParamsBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<SystemParamsBean>(SystemParamsBean.class));
		return beans;
	}

	@Override
	public List<SystemParamsBean> getSystemParamsByKey(String name) throws Exception {
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[] { name };
		List<SystemParamsBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<SystemParamsBean>(SystemParamsBean.class), objs);
		return beans;
	}

	@Override
	public int deleteSystemParams(String name) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[] { name };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public Double getMinMarginOutAmount() throws Exception {
		String key = Constants.SYSTEM_PARAM_MIN_MARGIN_OUT_AMOUNT;
		List<SystemParamsBean> beanList = getSystemParamsByKey(key);
		if (beanList.size() >= 1) {
			return Double.parseDouble(beanList.get(0).getValue());
		} else {
			return null;
		}
	}

	@Override
	public Integer getFreeMarginOutCount() throws Exception {
		String key = Constants.SYSTEM_PARAM_FREE_MARGIN_OUT_COUNT;
		List<SystemParamsBean> beanList = getSystemParamsByKey(key);
		if (beanList.size() >= 1) {
			return Integer.parseInt(beanList.get(0).getValue());
		} else {
			return null;
		}
	}

	@Override
	public Double getSubsequentMarginOutFee() throws Exception {
		String key = Constants.SYSTEM_PARAM_SUBSEQUENT_MARGIN_OUT_FEE;
		List<SystemParamsBean> beanList = getSystemParamsByKey(key);
		if (beanList.size() >= 1) {
			return Double.parseDouble(beanList.get(0).getValue());
		} else {
			return null;
		}
	}

	@Override
	public boolean isDataSyncRunning() throws Exception {
		String key = Constants.SYSTEM_PARAM_IS_RUNNING_DATA_SYNC;
		return getBooleanSystemParam(key);
	}

	@Override
	public boolean isUpdatingIbCommission() throws Exception {
		String key = Constants.SYSTEM_PARAM_IS_UPDATING_IB_COMMISSION;
		return getBooleanSystemParam(key);
	}

	@Override
	public boolean sendEmailNewUserRequestMarginOut() throws Exception {
		String key = Constants.SYSTEM_PARAM_SEND_EMAIL_NEW_USER_REQUEST_MARGIN_OUT;
		return getBooleanSystemParam(key);
	}

	@Override
	public Mt4WebServiceConnectionModel getMt4ServiceConnectionModel() throws Exception {
		final String sql = map.get("selectMt4WebServiceConnectionModel");
		Object[] objs = new Object[] {};
		List<SystemParamsBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<SystemParamsBean>(SystemParamsBean.class), objs);
		Mt4WebServiceConnectionModel model = new Mt4WebServiceConnectionModel();
		for (SystemParamsBean bean : beans) {
			if (bean.getName().equals(Constants.SYSTEM_PARAM_MT4_WEB_SERVICE_CONNECTION)) {
				model.setConnection(bean.getValue());
			} else if (bean.getName().equals(Constants.SYSTEM_PARAM_MT4_WEB_SERVICE_IP)) {
				model.setIp(bean.getValue());
			} else if (bean.getName().equals(Constants.SYSTEM_PARAM_MT4_WEB_SERVICE_LOGIN)) {
				model.setLogin(bean.getValue());
			} else if (bean.getName().equals(Constants.SYSTEM_PARAM_MT4_WEB_SERVICE_PASSWORD)) {
				model.setPassword(bean.getValue());
			} else if (bean.getName().equals(Constants.SYSTEM_PARAM_MT4_WEB_SERVICE_PORT)) {
				model.setPort(bean.getValue());
			}
		}
		return model;

	}

	@Override
	public Mt5WebServiceConnectionModel getMt5ServiceConnectionModel() throws Exception {
		final String sql = map.get("selectMt5WebServiceConnectionModel");
		Object[] objs = new Object[] {};
		List<SystemParamsBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<SystemParamsBean>(SystemParamsBean.class), objs);
		Mt5WebServiceConnectionModel model = new Mt5WebServiceConnectionModel();
		for (SystemParamsBean bean : beans) {
			if (bean.getName().equals(Constants.SYSTEM_PARAM_MT5_WEB_SERVICE_CONNECTION)) {
				model.setConnection(bean.getValue());
			} else if (bean.getName().equals(Constants.SYSTEM_PARAM_MT5_WEB_SERVICE_IP)) {
				model.setIp(bean.getValue());
			} else if (bean.getName().equals(Constants.SYSTEM_PARAM_MT5_WEB_SERVICE_LOGIN)) {
				model.setLogin(bean.getValue());
			} else if (bean.getName().equals(Constants.SYSTEM_PARAM_MT5_WEB_SERVICE_PASSWORD)) {
				model.setPassword(bean.getValue());
			} else if (bean.getName().equals(Constants.SYSTEM_PARAM_MT5_WEB_SERVICE_PORT)) {
				model.setPort(bean.getValue());
			}
		}
		return model;

	}

	private boolean getBooleanSystemParam(String key) throws Exception {
		List<SystemParamsBean> beanList = getSystemParamsByKey(key);
		if (beanList.size() >= 1) {
			return Boolean.parseBoolean(beanList.get(0).getValue());
		} else {
			return true;
		}
	}

	@Override
	public int updateSystemParamValue(String key, String value, String updatedBy) throws Exception {

		final String sql = map.get("updateVal");
		Object[] objs = new Object[] { value, updatedBy, key };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;

	}
}
