package com.henyep.ib.terminal.api.dto.db.row.map;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.henyep.ib.terminal.api.dto.db.MarginOutBean;

public class MarginOutBeanRowMap implements RowMapper<MarginOutBean> {

	public MarginOutBean mapRow(ResultSet rs, int count) throws SQLException {

		MarginOutBean marginOutBean = new MarginOutBean();
		marginOutBean.setId(rs.getInt("id"));
		marginOutBean.setBrand_code(rs.getString("brand_code"));
		marginOutBean.setCategory(rs.getString("category"));
		marginOutBean.setMethod(rs.getString("method"));
		marginOutBean.setAccount(rs.getString("account"));
		marginOutBean.setCurrency(rs.getString("currency"));
		marginOutBean.setAmount(rs.getDouble("amount"));
		marginOutBean.setAccount_currency(rs.getString("account_currency"));
		marginOutBean.setExchange_rate(rs.getDouble("exchange_rate"));
		marginOutBean.setAccount_amount(rs.getDouble("account_amount"));
		marginOutBean.setStatus(rs.getString("status"));
		marginOutBean.setComment(rs.getString("comment"));
		marginOutBean.setCreate_user(rs.getString("create_user"));
		marginOutBean.setCreate_time(rs.getTimestamp("create_time"));
		marginOutBean.setLast_update_user(rs.getString("last_update_user"));
		marginOutBean.setLast_update_time(rs.getTimestamp("last_update_time"));
		marginOutBean.setTrade_date(rs.getTimestamp("trade_date"));
		return marginOutBean;

	}
}
