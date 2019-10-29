package com.henyep.ib.terminal.api.dto.db.row.map;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.henyep.ib.terminal.api.dto.db.RebateDetailsBean;

public class RebateDetailsBeanRowMap implements RowMapper<RebateDetailsBean> {

	public RebateDetailsBean mapRow(ResultSet rs, int count) throws SQLException {

		RebateDetailsBean rebateDetailsBean = new RebateDetailsBean();
		rebateDetailsBean.setRebate_code(rs.getString("rebate_code"));
		rebateDetailsBean.setProduct_group(rs.getString("product_group"));
		rebateDetailsBean.setClient_package_code(rs.getString("client_package_code"));
		rebateDetailsBean.setCurrency(rs.getString("currency"));
		rebateDetailsBean.setMin_lot(rs.getDouble("min_lot"));
		rebateDetailsBean.setClient_fix_commission_type(rs.getString("client_fix_commission_type"));
		rebateDetailsBean.setClient_fix_commission(rs.getDouble("client_fix_commission"));
		rebateDetailsBean.setClient_spread_commission_type(rs.getString("client_spread_commission_type"));
		rebateDetailsBean.setClient_spread_commission(rs.getDouble("client_spread_commission"));
		rebateDetailsBean.setRebate_type(rs.getString("rebate_type"));
		rebateDetailsBean.setRebate_commission(rs.getDouble("rebate_commission"));
		rebateDetailsBean.setStart_date(rs.getDate("start_date"));
		rebateDetailsBean.setEnd_date(rs.getDate("end_date"));
		rebateDetailsBean.setLast_update_time(rs.getTimestamp("last_update_time"));
		rebateDetailsBean.setLast_update_user(rs.getString("last_update_user"));
		return rebateDetailsBean;

	}
}
