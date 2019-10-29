package com.henyep.ib.terminal.server.dao;
import java.util.List;
import java.util.Date;
import com.henyep.ib.terminal.api.dto.db.BonusDetailsBean;

public interface BonusDetailsDao{
	public void saveBonusDetails(final BonusDetailsBean bonusDetails) throws Exception;

	public List<BonusDetailsBean> getAllBonusDetailss() throws Exception;

	public List<BonusDetailsBean> getBonusDetailsByKey(String bonus_code, String bonus_type) throws Exception;

	public int updateBonusDetails(BonusDetailsBean bonusDetails) throws Exception;

	public int deleteBonusDetails(String bonus_code, String bonus_type) throws Exception;
}
