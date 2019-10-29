package com.henyep.ib.terminal.server.dao;

import java.util.List;

import com.henyep.ib.terminal.api.dto.db.BonusBean;

public interface BonusDao {
	public void saveBonus(final BonusBean bonus) throws Exception;

	public List<BonusBean> getAllBonuss() throws Exception;

	public List<BonusBean> getBonusByKey(Integer id) throws Exception;

	public int updateBonus(BonusBean bonus) throws Exception;

	public int deleteBonus(Integer id) throws Exception;
}
