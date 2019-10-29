package com.henyep.ib.terminal.api.dto.db.row.map;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.henyep.ib.terminal.api.dto.db.ProductSymbolMapBean;

public class ProductSymbolMapBeanRowMap implements RowMapper<ProductSymbolMapBean> {

	public ProductSymbolMapBean mapRow(ResultSet rs, int count) throws SQLException {

		ProductSymbolMapBean productSymbolMapBean = new ProductSymbolMapBean();
		productSymbolMapBean.setProduct_group(rs.getString("product_group"));
		productSymbolMapBean.setSymbol(rs.getString("symbol"));
		productSymbolMapBean.setStart_date(rs.getDate("start_date"));
		productSymbolMapBean.setEnd_date(rs.getDate("end_date"));
		productSymbolMapBean.setLast_update_time(rs.getTimestamp("last_update_time"));
		productSymbolMapBean.setLast_update_user(rs.getString("last_update_user"));
		return productSymbolMapBean;

	}
}
