package com.henyep.ib.terminal.api.dto.db.row.map;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.henyep.ib.terminal.api.dto.db.SettingsMt4GroupBean;

public class SettingsMt4GroupBeanRowMap implements RowMapper<SettingsMt4GroupBean> {

	public SettingsMt4GroupBean mapRow(ResultSet rs, int count) throws SQLException {

		SettingsMt4GroupBean settingsMt4GroupBean = new SettingsMt4GroupBean();
		settingsMt4GroupBean.setMt4_group(rs.getString("mt4_group"));
		settingsMt4GroupBean.setProduct_group(rs.getString("product_group"));
		settingsMt4GroupBean.setCurrency(rs.getString("currency"));
		settingsMt4GroupBean.setFix_commission(rs.getDouble("fix_commission"));
		settingsMt4GroupBean.setSpread_commission(rs.getDouble("spread_commission"));
		settingsMt4GroupBean.setStart_date(rs.getDate("start_date"));
		settingsMt4GroupBean.setEnd_date(rs.getDate("end_date"));
		settingsMt4GroupBean.setLast_update_time(rs.getTimestamp("last_update_time"));
		settingsMt4GroupBean.setLast_update_user(rs.getString("last_update_user"));
		return settingsMt4GroupBean;

	}
}
