package com.henyep.ib.terminal.api.dto.db.row.map;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.henyep.ib.terminal.api.dto.db.MarginInBean;

public class MarginInBeanRowMap implements RowMapper<MarginInBean> {

	public MarginInBean mapRow(ResultSet rs, int count) throws SQLException {

		MarginInBean marginInBean = new MarginInBean();
		marginInBean.setId(rs.getInt("id"));
		marginInBean.setBrand_code(rs.getString("brand_code"));
		marginInBean.setCategory(rs.getString("category"));
		marginInBean.setAccount(rs.getString("account"));
		marginInBean.setCurrency(rs.getString("currency"));
		marginInBean.setAmount(rs.getDouble("amount"));
		marginInBean.setAccount_currency(rs.getString("account_currency"));
		marginInBean.setExchange_rate(rs.getDouble("exchange_rate"));
		marginInBean.setAccount_amount(rs.getDouble("account_amount"));
		marginInBean.setTransfer_id(rs.getInt("transfer_id"));
		marginInBean.setStatus(rs.getString("status"));
		marginInBean.setComment(rs.getString("comment"));
		marginInBean.setCreate_time(rs.getTimestamp("create_time"));
		marginInBean.setCreate_user(rs.getString("create_user"));
		marginInBean.setLast_update_time(rs.getTimestamp("last_update_time"));
		marginInBean.setLast_update_user(rs.getString("last_update_user"));
		marginInBean.setTrade_date(rs.getTimestamp("trade_date"));
		return marginInBean;

	}
}
