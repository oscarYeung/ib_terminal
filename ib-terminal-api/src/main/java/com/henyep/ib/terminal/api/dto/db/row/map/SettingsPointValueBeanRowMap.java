package com.henyep.ib.terminal.api.dto.db.row.map;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.henyep.ib.terminal.api.dto.db.SettingsPointValueBean;

public class SettingsPointValueBeanRowMap implements RowMapper<SettingsPointValueBean> {

	public SettingsPointValueBean mapRow(ResultSet rs, int count) throws SQLException {

		SettingsPointValueBean settingsPointValueBean = new SettingsPointValueBean();
		settingsPointValueBean.setSymbol(rs.getString("symbol"));
		settingsPointValueBean.setCurrency(rs.getString("currency"));
		settingsPointValueBean.setAmount(rs.getDouble("amount"));
		settingsPointValueBean.setStart_date(rs.getDate("start_date"));
		settingsPointValueBean.setEnd_date(rs.getDate("end_date"));
		settingsPointValueBean.setLast_update_time(rs.getTimestamp("last_update_time"));
		settingsPointValueBean.setLast_update_user(rs.getString("last_update_user"));
		return settingsPointValueBean;

	}
}
