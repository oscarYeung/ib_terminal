package com.henyep.ib.terminal.api.dto.db.row.map;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.henyep.ib.terminal.api.dto.db.IbAccountDetailsBean;

public class IbAccountDetailsBeanRowMap implements RowMapper<IbAccountDetailsBean> {

	public IbAccountDetailsBean mapRow(ResultSet rs, int count) throws SQLException {

		IbAccountDetailsBean ibAccountDetailsBean = new IbAccountDetailsBean();
		ibAccountDetailsBean.setIb_code(rs.getString("ib_code"));
		ibAccountDetailsBean.setCurrency(rs.getString("currency"));
		ibAccountDetailsBean.setAccount_balance(rs.getDouble("account_balance"));
		ibAccountDetailsBean.setPending_commission(rs.getDouble("pending_commission"));
		ibAccountDetailsBean.setDay_open(rs.getDouble("day_open"));
		ibAccountDetailsBean.setDay_open_pending_commission(rs.getDouble("day_open_pending_commission"));
		ibAccountDetailsBean.setMonth_to_date(rs.getDouble("month_to_date"));
		ibAccountDetailsBean.setYear_to_date(rs.getDouble("year_to_date"));
		ibAccountDetailsBean.setLast_update_time(rs.getTimestamp("last_update_time"));
		ibAccountDetailsBean.setLast_update_user(rs.getString("last_update_user"));
		return ibAccountDetailsBean;

	}
}
