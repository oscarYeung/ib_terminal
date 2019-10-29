package com.henyep.ib.terminal.api.dto.db.row.map;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.henyep.ib.terminal.api.dto.db.IbClientMapBean;

public class IbClientMapBeanRowMap implements RowMapper<IbClientMapBean> {

	public IbClientMapBean mapRow(ResultSet rs, int count) throws SQLException {

		IbClientMapBean ibClientMapBean = new IbClientMapBean();
		ibClientMapBean.setIb_code(rs.getString("ib_code"));
		ibClientMapBean.setClient_code(rs.getString("client_code"));
		ibClientMapBean.setClient_package_code(rs.getString("client_package_code"));
		ibClientMapBean.setStart_date(rs.getDate("start_date"));
		ibClientMapBean.setEnd_date(rs.getDate("end_date"));
		ibClientMapBean.setLast_update_time(rs.getTimestamp("last_update_time"));
		ibClientMapBean.setLast_update_user(rs.getString("last_update_user"));
		return ibClientMapBean;

	}
}
