package com.henyep.ib.terminal.api.dto.db.row.map;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.henyep.ib.terminal.api.dto.db.IbCommissionClientSummaryBean;

public class IbCommissionClientSummaryBeanRowMap implements RowMapper<IbCommissionClientSummaryBean> {

	public IbCommissionClientSummaryBean mapRow(ResultSet rs, int count) throws SQLException {

		IbCommissionClientSummaryBean ibCommissionClientSummaryBean = new IbCommissionClientSummaryBean();
		ibCommissionClientSummaryBean.setBrand_code(rs.getString("brand_code"));
		ibCommissionClientSummaryBean.setPlatform(rs.getString("platform"));
		ibCommissionClientSummaryBean.setIb_code(rs.getString("ib_code"));
		ibCommissionClientSummaryBean.setClient_code(rs.getString("client_code"));
		ibCommissionClientSummaryBean.setClient_ib_code(rs.getString("client_ib_code"));
		ibCommissionClientSummaryBean.setProduct_group(rs.getString("product_group"));
		ibCommissionClientSummaryBean.setTrade_date(rs.getDate("trade_date"));
		ibCommissionClientSummaryBean.setCurrency(rs.getString("currency"));
		ibCommissionClientSummaryBean.setTotal_lot(rs.getDouble("total_lot"));
		ibCommissionClientSummaryBean.setTotal_fix_commission(rs.getDouble("total_fix_commission"));
		ibCommissionClientSummaryBean.setTotal_spread_commission(rs.getDouble("total_spread_commission"));
		ibCommissionClientSummaryBean.setTotal_rebate_commission_lot(rs.getDouble("total_rebate_commission_lot"));
		ibCommissionClientSummaryBean.setTotal_rebate_commission_pip(rs.getDouble("total_rebate_commission_pip"));
		ibCommissionClientSummaryBean.setTotal_commission(rs.getDouble("total_commission"));
		ibCommissionClientSummaryBean.setTotal_trade_pl(rs.getDouble("total_trade_pl"));
		ibCommissionClientSummaryBean.setTotal_trade_swaps(rs.getDouble("total_trade_swaps"));
		ibCommissionClientSummaryBean.setLast_update_time(rs.getTimestamp("last_update_time"));
		ibCommissionClientSummaryBean.setLast_update_user(rs.getString("last_update_user"));
		ibCommissionClientSummaryBean.setNet_deposit(rs.getDouble("net_deposit"));
		
		return ibCommissionClientSummaryBean;

	}
}
