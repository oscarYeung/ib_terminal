package com.henyep.ib.terminal.api.dto.db.row.map;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.henyep.ib.terminal.api.dto.db.ExternalMt4ClientBean;

public class ExternalMt4ClientBeanRowMap implements RowMapper<ExternalMt4ClientBean> {

	public ExternalMt4ClientBean mapRow(ResultSet rs, int count) throws SQLException {

		ExternalMt4ClientBean externalMt4ClientBean = new ExternalMt4ClientBean();
		externalMt4ClientBean.setClient_account(rs.getInt("client_account"));
		externalMt4ClientBean.setMt4_group(rs.getString("mt4_group"));
		externalMt4ClientBean.setCurrency(rs.getString("currency"));
		externalMt4ClientBean.setLast_update_time(rs.getTimestamp("last_update_time"));
		externalMt4ClientBean.setLast_update_user(rs.getString("last_update_user"));
		return externalMt4ClientBean;

	}
}
