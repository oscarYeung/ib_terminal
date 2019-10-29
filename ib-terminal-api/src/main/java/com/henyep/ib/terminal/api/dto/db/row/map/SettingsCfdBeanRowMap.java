package com.henyep.ib.terminal.api.dto.db.row.map;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.henyep.ib.terminal.api.dto.db.SettingsCfdBean;

public class SettingsCfdBeanRowMap implements RowMapper<SettingsCfdBean> {

	public SettingsCfdBean mapRow(ResultSet rs, int count) throws SQLException {

		SettingsCfdBean settingsCfdBean = new SettingsCfdBean();
		settingsCfdBean.setSymbol(rs.getString("symbol"));
		settingsCfdBean.setCurrency(rs.getString("currency"));
		settingsCfdBean.setAmount_per_unit(rs.getDouble("amount_per_unit"));
		settingsCfdBean.setStart_date(rs.getDate("start_date"));
		settingsCfdBean.setEnd_date(rs.getDate("end_date"));
		settingsCfdBean.setLast_update_time(rs.getTimestamp("last_update_time"));
		settingsCfdBean.setLast_update_user(rs.getString("last_update_user"));
		return settingsCfdBean;

	}
}
