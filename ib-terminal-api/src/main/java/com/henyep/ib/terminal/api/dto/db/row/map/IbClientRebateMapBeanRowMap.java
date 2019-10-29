package com.henyep.ib.terminal.api.dto.db.row.map;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.henyep.ib.terminal.api.dto.db.IbClientRebateMapBean;

public class IbClientRebateMapBeanRowMap implements RowMapper<IbClientRebateMapBean> {

	public IbClientRebateMapBean mapRow(ResultSet rs, int count) throws SQLException {

		IbClientRebateMapBean ibClientRebateMapBean = new IbClientRebateMapBean();
		ibClientRebateMapBean.setBrand_code(rs.getString("brand_code"));
		ibClientRebateMapBean.setIb_code(rs.getString("ib_code"));
		ibClientRebateMapBean.setClient_code(rs.getString("client_code"));
		ibClientRebateMapBean.setRebate_code(rs.getString("rebate_code"));
		ibClientRebateMapBean.setStart_date(rs.getDate("start_date"));
		ibClientRebateMapBean.setEnd_date(rs.getDate("end_date"));
		ibClientRebateMapBean.setLast_update_time(rs.getTimestamp("last_update_time"));
		ibClientRebateMapBean.setLast_update_user(rs.getString("last_update_user"));
		return ibClientRebateMapBean;

	}
}
