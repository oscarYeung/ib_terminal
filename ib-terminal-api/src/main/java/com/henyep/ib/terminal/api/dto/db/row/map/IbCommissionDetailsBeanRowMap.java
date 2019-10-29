package com.henyep.ib.terminal.api.dto.db.row.map;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.henyep.ib.terminal.api.dto.db.IbCommissionDetailsBean;

public class IbCommissionDetailsBeanRowMap implements RowMapper<IbCommissionDetailsBean> {

	public IbCommissionDetailsBean mapRow(ResultSet rs, int count) throws SQLException {

		IbCommissionDetailsBean ibCommissionDetailsBean = new IbCommissionDetailsBean();
		ibCommissionDetailsBean.setBrand_code(rs.getString("brand_code"));
		ibCommissionDetailsBean.setPlatform(rs.getString("platform"));
		ibCommissionDetailsBean.setTicket(rs.getString("ticket"));
		ibCommissionDetailsBean.setIb_code(rs.getString("ib_code"));
		ibCommissionDetailsBean.setClient_code(rs.getString("client_code"));
		ibCommissionDetailsBean.setClient_ib_code(rs.getString("client_ib_code"));
		ibCommissionDetailsBean.setProduct_group(rs.getString("product_group"));
		ibCommissionDetailsBean.setTrade_date(rs.getDate("trade_date"));
		ibCommissionDetailsBean.setSymbol(rs.getString("symbol"));
		ibCommissionDetailsBean.setBuy_sell(rs.getString("buy_sell"));
		ibCommissionDetailsBean.setLot(rs.getDouble("lot"));
		ibCommissionDetailsBean.setDeposit(rs.getDouble("deposit"));
		ibCommissionDetailsBean.setCurrency(rs.getString("currency"));
		ibCommissionDetailsBean.setClient_fix_commission(rs.getDouble("client_fix_commission"));
		ibCommissionDetailsBean.setClient_spread_commission(rs.getDouble("client_spread_commission"));
		ibCommissionDetailsBean.setRebate_commission_lot(rs.getDouble("rebate_commission_lot"));
		ibCommissionDetailsBean.setRebate_commission_pip(rs.getDouble("rebate_commission_pip"));
		ibCommissionDetailsBean.setTrade_swaps(rs.getDouble("trade_swaps"));
		ibCommissionDetailsBean.setTrade_pl(rs.getDouble("trade_pl"));
		ibCommissionDetailsBean.setOpen_trade_time(rs.getTimestamp("open_trade_time"));
		ibCommissionDetailsBean.setClose_trade_time(rs.getTimestamp("close_trade_time"));
		ibCommissionDetailsBean.setRebate_code(rs.getString("rebate_code"));
		ibCommissionDetailsBean.setRebate_type_lot(rs.getString("rebate_type_lot"));
		ibCommissionDetailsBean.setRebate_per_lot(rs.getDouble("rebate_per_lot"));
		ibCommissionDetailsBean.setRebate_type_pip(rs.getString("rebate_type_pip"));
		ibCommissionDetailsBean.setRebate_per_pip(rs.getDouble("rebate_per_pip"));
		ibCommissionDetailsBean.setLast_update_time(rs.getTimestamp("last_update_time"));
		ibCommissionDetailsBean.setLast_update_user(rs.getString("last_update_user"));
		return ibCommissionDetailsBean;

	}
}
