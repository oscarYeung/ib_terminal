package com.henyep.ib.terminal.api.dto.db.row.map;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.henyep.ib.terminal.api.dto.db.IbTradingAccountMapBean;

public class IbTradingAccountMapBeanRowMap implements RowMapper<IbTradingAccountMapBean> {

	public IbTradingAccountMapBean mapRow(ResultSet rs, int count) throws SQLException {

		IbTradingAccountMapBean ibTradingAccountMapBean = new IbTradingAccountMapBean();
		ibTradingAccountMapBean.setIb_code(rs.getString("ib_code"));
		ibTradingAccountMapBean.setTrading_account(rs.getString("trading_account"));
		ibTradingAccountMapBean.setTrading_platform(rs.getString("trading_platform"));
		ibTradingAccountMapBean.setLast_update_time(rs.getTimestamp("last_update_time"));
		ibTradingAccountMapBean.setLast_update_user(rs.getString("last_update_user"));
		return ibTradingAccountMapBean;

	}
}
