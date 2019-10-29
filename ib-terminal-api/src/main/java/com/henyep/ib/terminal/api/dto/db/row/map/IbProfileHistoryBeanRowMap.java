package com.henyep.ib.terminal.api.dto.db.row.map;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.henyep.ib.terminal.api.dto.db.IbProfileHistoryBean;

public class IbProfileHistoryBeanRowMap implements RowMapper<IbProfileHistoryBean> {

	public IbProfileHistoryBean mapRow(ResultSet rs, int count) throws SQLException {

		IbProfileHistoryBean ibProfileHistoryBean = new IbProfileHistoryBean();
		ibProfileHistoryBean.setBrand_code(rs.getString("brand_code"));
		ibProfileHistoryBean.setIb_code(rs.getInt("ib_code"));
		ibProfileHistoryBean.setUsername(rs.getString("username"));
		ibProfileHistoryBean.setPassword(rs.getString("password"));
		ibProfileHistoryBean.setFirst_name(rs.getString("first_name"));
		ibProfileHistoryBean.setLast_name(rs.getString("last_name"));
		ibProfileHistoryBean.setChinese_name(rs.getString("chinese_name"));
		ibProfileHistoryBean.setSex(rs.getString("sex"));
		ibProfileHistoryBean.setMobile(rs.getString("mobile"));
		ibProfileHistoryBean.setCountry(rs.getString("country"));
		ibProfileHistoryBean.setEmail(rs.getString("email"));
		ibProfileHistoryBean.setAddress(rs.getString("address"));
		ibProfileHistoryBean.setLanguage(rs.getString("language"));
		ibProfileHistoryBean.setStatus(rs.getString("status"));
		ibProfileHistoryBean.setCreate_time(rs.getTimestamp("create_time"));
		ibProfileHistoryBean.setLast_update_user(rs.getString("last_update_user"));
		ibProfileHistoryBean.setVersion(rs.getInt("version"));
		return ibProfileHistoryBean;

	}
}
