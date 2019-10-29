package com.henyep.ib.terminal.server.dao;
import java.util.List;
import java.util.Date;
import com.henyep.ib.terminal.api.dto.db.IbBonusMapBean;

public interface IbBonusMapDao{
	public void saveIbBonusMap(final IbBonusMapBean ibBonusMap) throws Exception;

	public List<IbBonusMapBean> getAllIbBonusMaps() throws Exception;

	public List<IbBonusMapBean> getIbBonusMapByKey(String brand_code, String ib_code, String bonus_code, Date start_date) throws Exception;

	public int updateIbBonusMap(IbBonusMapBean ibBonusMap) throws Exception;

	public int deleteIbBonusMap(String brand_code, String ib_code, String bonus_code, Date start_date) throws Exception;
}
