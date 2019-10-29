package com.henyep.ib.terminal.api.dto.db.row.map;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.henyep.ib.terminal.api.dto.db.IbTreeBean;

public class IbTreeBeanRowMap implements RowMapper<IbTreeBean> {

	public IbTreeBean mapRow(ResultSet rs, int count) throws SQLException {

		IbTreeBean ibTreeBean = new IbTreeBean();
		ibTreeBean.setId(rs.getInt("id"));
		ibTreeBean.setBrand_code(rs.getString("brand_code"));
		ibTreeBean.setTeam(rs.getString("team"));
		ibTreeBean.setIb_code(rs.getString("ib_code"));
		ibTreeBean.setMin_id(rs.getInt("min_id"));
		ibTreeBean.setMax_id(rs.getInt("max_id"));
		ibTreeBean.setStart_date(rs.getDate("start_date"));
		ibTreeBean.setEnd_date(rs.getDate("end_date"));
		ibTreeBean.setLast_update_time(rs.getTimestamp("last_update_time"));
		ibTreeBean.setLast_update_user(rs.getString("last_update_user"));
		return ibTreeBean;

	}
}
