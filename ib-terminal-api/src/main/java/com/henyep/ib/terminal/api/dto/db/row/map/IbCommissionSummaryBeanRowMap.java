package com.henyep.ib.terminal.api.dto.db.row.map;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.henyep.ib.terminal.api.dto.db.IbCommissionSummaryBean;

public class IbCommissionSummaryBeanRowMap implements RowMapper<IbCommissionSummaryBean> {

	public IbCommissionSummaryBean mapRow(ResultSet rs, int count) throws SQLException {

		IbCommissionSummaryBean ibCommissionSummaryBean = new IbCommissionSummaryBean();
		ibCommissionSummaryBean.setBrand_code(rs.getString("brand_code"));
		ibCommissionSummaryBean.setPlatform(rs.getString("platform"));
		ibCommissionSummaryBean.setIb_code(rs.getString("ib_code"));
		ibCommissionSummaryBean.setProduct_group(rs.getString("product_group"));
		ibCommissionSummaryBean.setTrade_date(rs.getDate("trade_date"));
		ibCommissionSummaryBean.setCurrency(rs.getString("currency"));
		ibCommissionSummaryBean.setTotal_lot(rs.getDouble("total_lot"));
		ibCommissionSummaryBean.setTotal_fix_commission(rs.getDouble("total_fix_commission"));
		ibCommissionSummaryBean.setTotal_spread_commission(rs.getDouble("total_spread_commission"));
		ibCommissionSummaryBean.setTotal_rebate_commission_lot(rs.getDouble("total_rebate_commission_lot"));
		ibCommissionSummaryBean.setTotal_rebate_commission_pip(rs.getDouble("total_rebate_commission_pip"));
		ibCommissionSummaryBean.setTotal_ev_commission(rs.getDouble("total_ev_commission"));
		ibCommissionSummaryBean.setTotal_rev_commission(rs.getDouble("total_rev_commission"));
		ibCommissionSummaryBean.setTotal_commission(rs.getDouble("total_commission"));
		ibCommissionSummaryBean.setLast_update_time(rs.getTimestamp("last_update_time"));
		ibCommissionSummaryBean.setLast_update_user(rs.getString("last_update_user"));
		ibCommissionSummaryBean.setNet_deposit(rs.getDouble("net_deposit"));
		
		return ibCommissionSummaryBean;

	}
}
