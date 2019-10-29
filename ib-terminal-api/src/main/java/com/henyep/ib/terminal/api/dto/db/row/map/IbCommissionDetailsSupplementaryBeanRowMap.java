package com.henyep.ib.terminal.api.dto.db.row.map;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.henyep.ib.terminal.api.dto.db.IbCommissionDetailsSupplementaryBean;

public class IbCommissionDetailsSupplementaryBeanRowMap implements RowMapper<IbCommissionDetailsSupplementaryBean> {

	public IbCommissionDetailsSupplementaryBean mapRow(ResultSet rs, int count) throws SQLException {

		IbCommissionDetailsSupplementaryBean ibCommissionDetailsSupplementaryBean = new IbCommissionDetailsSupplementaryBean();
		ibCommissionDetailsSupplementaryBean.setBrand_code(rs.getString("brand_code"));		
		ibCommissionDetailsSupplementaryBean.setIb_code(rs.getString("ib_code"));
		ibCommissionDetailsSupplementaryBean.setTrade_date(rs.getDate("trade_date"));
		ibCommissionDetailsSupplementaryBean.setCurrency(rs.getString("currency"));
		ibCommissionDetailsSupplementaryBean.setEv_commission(rs.getDouble("ev_commission"));
		
		ibCommissionDetailsSupplementaryBean.setClient_fix_commission(rs.getDouble("client_fix_commission"));
		
		ibCommissionDetailsSupplementaryBean.setClient_rebate_commission(rs.getDouble("client_rebate_commission"));
		ibCommissionDetailsSupplementaryBean.setDeficit(rs.getDouble("deficit"));
		ibCommissionDetailsSupplementaryBean.setRebate_code(rs.getString("rebate_code"));
		ibCommissionDetailsSupplementaryBean.setEv_percentage(rs.getDouble("ev_percentage"));
		ibCommissionDetailsSupplementaryBean.setLast_update_time(rs.getTimestamp("last_update_time"));
		ibCommissionDetailsSupplementaryBean.setLast_update_user(rs.getString("last_update_user"));
		return ibCommissionDetailsSupplementaryBean;

	}
}
