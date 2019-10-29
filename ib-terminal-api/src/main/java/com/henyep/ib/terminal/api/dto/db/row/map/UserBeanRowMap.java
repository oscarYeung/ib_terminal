package com.henyep.ib.terminal.api.dto.db.row.map;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.henyep.ib.terminal.api.dto.db.UserBean;

public class UserBeanRowMap implements RowMapper<UserBean> {

	public UserBean mapRow(ResultSet rs, int count) throws SQLException {

		UserBean userBean = new UserBean();
		userBean.setBrand_code(rs.getString("brand_code"));
		userBean.setUser_code(rs.getString("user_code"));
		userBean.setUser_name(rs.getString("user_name"));
		userBean.setPassword(rs.getString("password"));
		userBean.setStatus(rs.getString("status"));
		userBean.setCreate_time(rs.getTimestamp("create_time"));
		userBean.setLast_update_time(rs.getTimestamp("last_update_time"));
		userBean.setLast_update_user(rs.getString("last_update_user"));
		return userBean;

	}
}
