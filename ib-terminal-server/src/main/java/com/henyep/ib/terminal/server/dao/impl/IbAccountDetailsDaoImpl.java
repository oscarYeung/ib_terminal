package com.henyep.ib.terminal.server.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.IbAccountDetailsBean;
import com.henyep.ib.terminal.api.dto.response.ibcommission.IbAccountModel;
import com.henyep.ib.terminal.server.dao.IbAccountDetailsDao;
import com.henyep.ib.terminal.server.global.Constants;
import com.henyep.ib.terminal.server.util.DateUtil;

@Repository(value = "IbAccountDetailsDao")
public class IbAccountDetailsDaoImpl implements IbAccountDetailsDao{

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "IbAccountDetailsDao_SQLMap")
	Map<String, String> map;
	public IbAccountDetailsDaoImpl() throws Exception{
		super();
	}
	@Override
	public void saveIbAccountDetails(IbAccountDetailsBean ibAccountDetails) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[]{
				ibAccountDetails.getDay_open(),
				ibAccountDetails.getMonth_to_date(),
				ibAccountDetails.getYear_to_date(),
				ibAccountDetails.getNet_margin_bonus(),
				ibAccountDetails.getLast_update_user(),
				ibAccountDetails.getIb_code(),
				ibAccountDetails.getAccount_balance(),
				ibAccountDetails.getPending_commission(),
				ibAccountDetails.getCurrency(),
				ibAccountDetails.getDay_open_pending_commission()
		};
		int res = this.jdbcTemplate.update(sql, objs);

	}


	@Override
	public int updateIbAccountDetails(IbAccountDetailsBean ibAccountDetails) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[]{
				ibAccountDetails.getDay_open(),
				ibAccountDetails.getMonth_to_date(),
				ibAccountDetails.getYear_to_date(),
				ibAccountDetails.getNet_margin_bonus(),
				ibAccountDetails.getLast_update_user(),
				ibAccountDetails.getAccount_balance(),
				ibAccountDetails.getPending_commission(),
				ibAccountDetails.getDay_open_pending_commission(),
				ibAccountDetails.getIb_code(),
				ibAccountDetails.getCurrency()
		};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<IbAccountDetailsBean> getAllIbAccountDetailss() throws Exception{
		String sql = map.get("selectAll");
		List<IbAccountDetailsBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbAccountDetailsBean>(IbAccountDetailsBean.class));
		return beans;
	}

	@Override
	public List<IbAccountDetailsBean> getIbAccountDetailsByKey(String ib_code) throws Exception{
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[]{ib_code};
		List<IbAccountDetailsBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbAccountDetailsBean>(IbAccountDetailsBean.class), objs);
		return beans;
	}

	@Override
	public int deleteIbAccountDetails(String ib_code, String currency) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[]{ib_code, currency};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
	
	@Override
	public int resetPendingCommissions(Date trade_date, List<String> ibCodes) throws Exception {
		
		String tradeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, trade_date);
		
		String sql = map.get("resetPendingCommissions");
		String ibFilter = "account in ('" + StringUtils.join(ibCodes, "','") + "')"; 
		sql = StringUtils.replace(sql, "{IB_FILTER}", ibFilter);
		
		Object[] objs = new Object[]{tradeDateString};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;			

	}
	@Override
	public int resetAccBalance(Date trade_date, List<String> ibCodes) throws Exception{
		
		if(ibCodes.size() > 0){
			String tradeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, trade_date);
			
			String sql = map.get("resetAccountBalance");
			
			String ibFilter = "account in ('" + StringUtils.join(ibCodes, "','") + "')"; 
			sql = StringUtils.replace(sql, "{IB_FILTER}", ibFilter);
			
			Object[] objs = new Object[]{tradeDateString};
			int res = this.jdbcTemplate.update(sql, objs);
			return res;			
		}
		else{
			return 0;
		}
	
			
	}
	
	@Override
	public IbAccountModel getIbAccountDetail(String ib_code) throws Exception{
		final String sql = map.get("selectIbAccountSummary");
		Object[] objs = new Object[]{ib_code};
		IbAccountModel model = null;
		try{
			model = this.jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<IbAccountModel>(IbAccountModel.class), objs);
		}catch(Exception ex){
			model = null;
		}
		return model;
	}
	@Override
	public void updateIbAccountAccumlatedNetMargin(String ib_code, double accumlatedNetMargin) throws Exception {
		final String sql = map.get("updateAccumlatedNetMargin");
		Object[] objs = new Object[]{
				accumlatedNetMargin,
				ib_code
		};
		this.jdbcTemplate.update(sql, objs);
	}
	
	@Override
	public void updateDayOpen() throws Exception {
		final String sql = map.get("updateDayOpen");
		Object[] objs = new Object[]{
		};
		this.jdbcTemplate.update(sql, objs);	
	}

	@Override
	public void updateAccountBalanceByMarginOutBatchFileId(String batchFileId) throws Exception {
		final String sql = map.get("updateAccountBalanceByMarginOutBatchFileId");
		Object[] objs = new Object[]{batchFileId, Constants.MARGIN_OUT_STATUS_EXECUTED};
		this.jdbcTemplate.update(sql, objs);	
	}
	
	
	
}


