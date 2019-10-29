package com.henyep.ib.terminal.api.dto.db.row.map;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.henyep.ib.terminal.api.dto.db.RebateBean;

public class RebateBeanRowMap implements RowMapper<RebateBean> {

	public RebateBean mapRow(ResultSet rs, int count) throws SQLException {

		RebateBean rebateBean = new RebateBean();
		rebateBean.setId(rs.getInt("id"));
		rebateBean.setRebate_code(rs.getString("rebate_code"));
		rebateBean.setBrand_code(rs.getString("brand_code"));
		rebateBean.setDescription(rs.getString("description"));
		rebateBean.setLast_update_time(rs.getTimestamp("last_update_time"));
		rebateBean.setLast_update_user(rs.getString("last_update_user"));
		return rebateBean;

	}
}
