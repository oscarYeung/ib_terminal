package com.henyep.ib.terminal.server.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.IbTreeBean;
import com.henyep.ib.terminal.server.dao.IbTreeDao;
import com.henyep.ib.terminal.server.dto.sales.ib.tree.UserIbTreeDto;
import com.henyep.ib.terminal.server.util.DateUtil;

@Repository(value = "IbTreeDao")
public class IbTreeDaoImpl implements IbTreeDao {

	private final transient Log logger = LogFactory.getLog(getClass());

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "IbTreeDao_SQLMap")
	Map<String, String> map;

	public IbTreeDaoImpl() throws Exception {
		super();
	}

	@Override
	public List<IbTreeBean> getCurrentIbTrees(Date start_date, Date end_date) throws Exception {
		String sql = map.get("getCurrentSnapShot");
		Object[] objs = new Object[] { start_date, end_date };
		List<IbTreeBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbTreeBean>(IbTreeBean.class), objs);
		return beans;
	}

	@Override
	public List<IbTreeBean> getByIbCodeDateRange(String ib_code, Date start_date, Date end_date) throws Exception {
		String sql = map.get("getByIbCodeDateRange");
		Object[] objs = new Object[] { ib_code, start_date, end_date };
		List<IbTreeBean> list = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbTreeBean>(IbTreeBean.class), objs);
		return list;
	}

	@Override
	public int addIbToTree(Integer parent_tree_id, String brand_code, String team, String ib_code, Date start_date, Date end_date, String user)
			throws Exception {
		String sql = map.get("insert");
		Object[] objs = new Object[] { parent_tree_id, brand_code, team, ib_code, start_date, end_date, user };
		Integer rtn = this.jdbcTemplate.queryForObject(sql, objs, Integer.class);
		return rtn;

	}

	@Override
	public int deteleIbFromTree(Integer tree_id, String sender) throws Exception {
		String sql = map.get("delete");
		Object[] objs = new Object[] { tree_id, sender };
		Integer rtn = this.jdbcTemplate.queryForObject(sql, objs, Integer.class);
		return rtn;
	}

	@Override
	public int moveIb(Integer from_ib_id, Integer to_ib_id, String sender) throws Exception {
		String sql = map.get("move");
		Object[] objs = new Object[] { from_ib_id, to_ib_id, sender };
		Integer rtn = this.jdbcTemplate.queryForObject(sql, objs, Integer.class);
		return rtn;
	}

	@Override
	public List<IbTreeBean> getByTeamHead(String ib_code, Date start_date, Date end_date) throws Exception {
		String sql = map.get("getByTeamHead");
		Object[] objs = new Object[] { ib_code, start_date, end_date };
		List<IbTreeBean> list = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbTreeBean>(IbTreeBean.class), objs);
		return list;
	}

	@Override
	public List<IbTreeBean> getByTeamHead(String user_code, Date trade_date) throws Exception {
		String sql = map.get("getUserIbTreeByUserCode");
		Object[] objs = new Object[] { user_code };
		List<UserIbTreeDto> userIbTreeList = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<UserIbTreeDto>(UserIbTreeDto.class), objs);

		// get user tree
		IbTreeBean userIbTreeBean = null;
		List<IbTreeBean> ibTreeBeanList = new ArrayList<IbTreeBean>();
		List<String> userCodeList = new ArrayList<String>();
		for (UserIbTreeDto dto : userIbTreeList) {
			if (!userCodeList.contains(dto.getUser_code())) {
				IbTreeBean ibTreeBean = new IbTreeBean();
				ibTreeBean.setBrand_code(dto.getBrand_code());
				ibTreeBean.setMin_id(0);
				ibTreeBean.setMax_id(1);
				ibTreeBean.setStart_date(dto.getStart_date());
				ibTreeBean.setEnd_date(dto.getEnd_date());
				ibTreeBean.setIb_code(dto.getUser_code());
				ibTreeBean.setTeam(dto.getUser_code());
				ibTreeBean.setId(-1);
				ibTreeBean.setIs_ib(false);
				ibTreeBeanList.add(ibTreeBean);
				userCodeList.add(dto.getUser_code());
				userIbTreeBean = ibTreeBean;
			}
		}

		// insert ib to user Ib tree
		for (UserIbTreeDto dto : userIbTreeList) {
			if (dto.getIb_team_head_id() != null) {
				String teamHeadId = dto.getIb_team_head_id();
				sql = map.get("getIbTreeByTeamHead");
				String tradeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, trade_date);
				objs = new Object[] { teamHeadId, tradeDateString, tradeDateString, tradeDateString, tradeDateString };
				List<IbTreeBean> list = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbTreeBean>(IbTreeBean.class), objs);
				if (list != null && list.size() > 0) {
					Integer teamSize = list.get(0).getMax_id() - list.get(0).getMin_id() + 1;

					if (userIbTreeBean != null) {
						// adjust other ibTree max id and min id
						for (IbTreeBean ibTreeBean : ibTreeBeanList) {
							if (ibTreeBean.getMax_id() > userIbTreeBean.getMin_id()) {
								ibTreeBean.setMax_id(ibTreeBean.getMax_id() + teamSize);
							}
							if (ibTreeBean.getMin_id() > userIbTreeBean.getMin_id()) {
								ibTreeBean.setMin_id(ibTreeBean.getMin_id() + teamSize);
							}
						}

						// userIbTreeBean.setMax_id(userIbTreeBean.getMax_id()
						// + teamSize);

						// insert ib team
						for (IbTreeBean newIb : list) {
							newIb.setMin_id(newIb.getMin_id() + 1 + userIbTreeBean.getMin_id());
							newIb.setMax_id(newIb.getMax_id() + 1 + userIbTreeBean.getMin_id());
							ibTreeBeanList.add(newIb);

						}
					}
				}
				

			}
		}

		Collections.sort(ibTreeBeanList, new Comparator<IbTreeBean>() {
			@Override
			public int compare(IbTreeBean ibTree2, IbTreeBean ibTree1) {
				return ibTree2.getMin_id().compareTo(ibTree1.getMin_id());
			}
		});

		return ibTreeBeanList;
	}

	@Override
	public List<IbTreeBean> getIbTreeByIbCode(String ib_code, Date start_date, Date end_date) throws Exception {

		String sql = map.get("getIbTreeByIb");
		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, start_date);
		String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, end_date);
		Object[] objs = new Object[] { ib_code, startDateString, startDateString, endDateString, endDateString, endDateString, endDateString, ib_code, startDateString,
				startDateString, endDateString, endDateString, endDateString, endDateString };
		List<IbTreeBean> list = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbTreeBean>(IbTreeBean.class), objs);
		return list;

	}

	@Override
	public List<String> validateAddByIbCode(String parent_ib_code, String target_ib_code, Date start_date) throws Exception {

		String sql = map.get("validateAddIbByIbCode");
		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, start_date);
		Object[] objs = new Object[] { parent_ib_code, target_ib_code, target_ib_code, parent_ib_code, target_ib_code, parent_ib_code,
				startDateString };
		List<String> errorCodes = this.jdbcTemplate.queryForList(sql, String.class, objs);
		return errorCodes;
	}

	@Override
	public int addByIbCode(String parent_ib_code, String target_ib_code, Date start_date, String user) throws Exception {
		String sql = map.get("getByIbCode");
		Object[] objs = new Object[] { parent_ib_code };
		List<IbTreeBean> parentIbs = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbTreeBean>(IbTreeBean.class), objs);
		if (parentIbs.size() > 0) {
			IbTreeBean parentIb = parentIbs.get(0);
			return addIbToTree(parentIb.getId(), parentIb.getBrand_code(), parentIb.getBrand_code(), target_ib_code, start_date,
					parentIb.getEnd_date(), user);
		} else {
			return -1;
		}
	}

	public IbTreeBean getByIbCode(String ib_code) {
		String sql = map.get("getByIbCode");
		Object[] objs = new Object[] { ib_code };
		List<IbTreeBean> list = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbTreeBean>(IbTreeBean.class), objs);
		if (list != null && list.size() == 1) {
			return list.get(0);
		} else
			return null;
	}

	@Override
	public List<String> getIbTeamHeadByUserCode(String userCode, String brandCode) throws Exception {
		String sql = map.get("getTeamHeadIdByUserCode");
		Object[] objs = new Object[] { userCode, brandCode };
		List<String> teamHeadIds = this.jdbcTemplate.queryForList(sql, objs, String.class);
		
		return teamHeadIds;
	}

	public List<String> getIbListByTeamHeadIds(List<String> ibTeamHeadIdList) throws Exception {
		String sql = map.get("getIbListByTeamHeadIds");
		sql = sql.replace("#team_head_ids", StringUtils.join(ibTeamHeadIdList, ','));
		List<String> ib_list = this.jdbcTemplate.queryForList(sql, null, String.class);
		return ib_list;
	}

	@Override
	public List<String> getIbTeamHeadByUserCode(String userCode) throws Exception {
		String sql = map.get("getTeamHeadIdByUserCodeOnly");
		Object[] objs = new Object[] { userCode };
		List<String> teamHeadIds = this.jdbcTemplate.queryForList(sql, objs, String.class);

		return teamHeadIds;
	}

}
