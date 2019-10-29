package com.henyep.ib.terminal.api.dto.db.row.map;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.henyep.ib.terminal.api.dto.db.RebateSupplementaryBean;

public class RebateSupplementaryBeanRowMap implements RowMapper<RebateSupplementaryBean> {

	public RebateSupplementaryBean mapRow(ResultSet rs, int count) throws SQLException {

		RebateSupplementaryBean rebateSupplementaryBean = new RebateSupplementaryBean();
		rebateSupplementaryBean.setRebate_code(rs.getString("rebate_code"));
		rebateSupplementaryBean.setEv_percentage(rs.getDouble("ev_percentage"));
		rebateSupplementaryBean.setStart_date(rs.getDate("start_date"));
		rebateSupplementaryBean.setEnd_date(rs.getDate("end_date"));
		rebateSupplementaryBean.setLast_update_time(rs.getTimestamp("last_update_time"));
		rebateSupplementaryBean.setLast_update_user(rs.getString("last_update_user"));
		return rebateSupplementaryBean;

	}
}
