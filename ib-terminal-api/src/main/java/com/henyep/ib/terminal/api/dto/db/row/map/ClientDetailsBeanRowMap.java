package com.henyep.ib.terminal.api.dto.db.row.map;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.henyep.ib.terminal.api.dto.db.ClientDetailsBean;

public class ClientDetailsBeanRowMap implements RowMapper<ClientDetailsBean> {

	public ClientDetailsBean mapRow(ResultSet rs, int count) throws SQLException {

		ClientDetailsBean clientDetailsBean = new ClientDetailsBean();
		clientDetailsBean.setClient_trading_id(rs.getString("client_trading_id"));
		clientDetailsBean.setFirst_name(rs.getString("first_name"));
		clientDetailsBean.setLast_name(rs.getString("last_name"));
		clientDetailsBean.setChinese_name(rs.getString("chinese_name"));
		clientDetailsBean.setEmail(rs.getString("email"));
		clientDetailsBean.setTrading_platform(rs.getString("trading_platform"));
		clientDetailsBean.setSex(rs.getString("sex"));
		clientDetailsBean.setMobile(rs.getString("mobile"));
		clientDetailsBean.setCurrency(rs.getString("currency"));
		clientDetailsBean.setAccount_balance(rs.getDouble("account_balance"));
		clientDetailsBean.setLast_update_time(rs.getTimestamp("last_update_time"));
		clientDetailsBean.setLast_update_user(rs.getString("last_update_user"));
		return clientDetailsBean;

	}
}
