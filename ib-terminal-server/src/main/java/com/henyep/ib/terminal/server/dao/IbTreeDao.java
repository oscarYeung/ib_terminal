package com.henyep.ib.terminal.server.dao;

import java.util.Date;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.IbTreeBean;

public interface IbTreeDao {

	public List<IbTreeBean> getCurrentIbTrees(Date start_date, Date end_date) throws Exception;

	public List<IbTreeBean> getByIbCodeDateRange(String ib_code, Date start_date, Date end_date) throws Exception;

	public int addIbToTree(Integer parent_tree_id, String brand_code, String team, String ib_code, Date start_date,
			Date end_date, String user) throws Exception;
	
	public int deteleIbFromTree(Integer tree_id,String sender) throws Exception;
	
	public List<IbTreeBean> getByTeamHead(String ib_code, Date start_date, Date end_date) throws Exception;
	
	public List<IbTreeBean> getByTeamHead(String user_code, Date trade_date) throws Exception;
	
	public int moveIb(Integer from_ib_id, Integer to_ib_id, String sender) throws Exception;
	
	public List<IbTreeBean> getIbTreeByIbCode(String ib_code, Date start_date, Date end_date) throws Exception;
	
	public List<String> validateAddByIbCode(String parent_ib_code, String target_ib_code, Date start_date) throws Exception;
	
	public int addByIbCode(String parent_ib_code, String target_ib_code, Date start_date, String user) throws Exception;
	
	public List<String> getIbTeamHeadByUserCode(String userCode, String brandCode) throws Exception;	
	
	public List<String> getIbTeamHeadByUserCode(String userCode) throws Exception;
	
	public List<String> getIbListByTeamHeadIds(List<String> ibTeamHeadIdList) throws Exception;
	
	public IbTreeBean getByIbCode(String ib_code) ;
}
