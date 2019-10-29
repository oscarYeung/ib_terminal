package com.henyep.ib.terminal.api.dto.db.row.map;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.henyep.ib.terminal.api.dto.db.IbProfileBean;

public class IbProfileBeanRowMap implements RowMapper<IbProfileBean> {

	public IbProfileBean mapRow(ResultSet rs, int count) throws SQLException {

		IbProfileBean ibProfileBean = new IbProfileBean();
		ibProfileBean.setBrand_code(rs.getString("brand_code"));
		ibProfileBean.setIb_code(rs.getString("ib_code"));
		ibProfileBean.setUsername(rs.getString("username"));
		ibProfileBean.setPassword(rs.getString("password"));
		ibProfileBean.setFirst_name(rs.getString("first_name"));
		ibProfileBean.setLast_name(rs.getString("last_name"));
		ibProfileBean.setChinese_name(rs.getString("chinese_name"));
		ibProfileBean.setSex(rs.getString("sex"));
		ibProfileBean.setMobile(rs.getString("mobile"));
		ibProfileBean.setCountry(rs.getString("country"));
		ibProfileBean.setEmail(rs.getString("email"));
		ibProfileBean.setAddress(rs.getString("address"));
		ibProfileBean.setLanguage(rs.getString("language"));
		ibProfileBean.setStatus(rs.getString("status"));
		ibProfileBean.setCreate_time(rs.getTimestamp("create_time"));
		ibProfileBean.setLast_update_time(rs.getTimestamp("last_update_time"));
		ibProfileBean.setLast_update_user(rs.getString("last_update_user"));
		return ibProfileBean;

	}
}
