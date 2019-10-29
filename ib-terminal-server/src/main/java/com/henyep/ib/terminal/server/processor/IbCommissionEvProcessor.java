package com.henyep.ib.terminal.server.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.api.dto.db.BonusDetailsBean;
import com.henyep.ib.terminal.api.dto.db.IbAccountDetailsBean;
import com.henyep.ib.terminal.api.dto.db.IbBonusMapBean;
import com.henyep.ib.terminal.api.dto.db.IbClientRebateMapBean;
import com.henyep.ib.terminal.api.dto.db.IbCommissionDetailsBean;
import com.henyep.ib.terminal.api.dto.db.IbCommissionDetailsSupplementaryBean;
import com.henyep.ib.terminal.api.dto.db.RebateSupplementaryBean;
import com.henyep.ib.terminal.server.dao.BonusDetailsDao;
import com.henyep.ib.terminal.server.dao.ClientBalanceSummaryDao;
import com.henyep.ib.terminal.server.dao.IbAccountDetailsDao;
import com.henyep.ib.terminal.server.dao.IbBonusMapDao;
import com.henyep.ib.terminal.server.dao.IbClientRebateMapDao;
import com.henyep.ib.terminal.server.dao.IbCommissionDetailsDao;
import com.henyep.ib.terminal.server.dao.IbCommissionDetailsSupplementaryDao;
import com.henyep.ib.terminal.server.dao.RebateSupplementaryDao;
import com.henyep.ib.terminal.server.dto.dao.ClientDeficitDto;
import com.henyep.ib.terminal.server.dto.dao.ClientEquityChangeDto;
import com.henyep.ib.terminal.server.dto.processor.IbClientDeficitDto;
import com.henyep.ib.terminal.server.global.Constants;

@Component("IbCommissionEvProcessor")
public class IbCommissionEvProcessor extends AbstractProcessor<IbCommissionDetailsSupplementaryBean> implements Preparator {

	@Resource(name = "RebateSupplementaryDao")
	private RebateSupplementaryDao rebateSupplementaryDao;

	@Resource(name = "IbClientRebateMapDao")
	private IbClientRebateMapDao ibClientRebateMapDao;

	@Resource(name = "IbCommissionDetailsSupplementaryDao")
	private IbCommissionDetailsSupplementaryDao ibCommissionDetailsSupplementaryDao;

	@Resource(name = "ClientBalanceSummaryDao")
	private ClientBalanceSummaryDao clientBalanceSummaryDao;

	@Resource(name = "IbCommissionDetailsDao")
	private IbCommissionDetailsDao ibCommissionDetailsDao;

	@Resource(name = "BonusDetailsDao")
	private BonusDetailsDao bonusDetailsDao;

	@Resource(name = "IbBonusMapDao")
	private IbBonusMapDao ibBonusMapDao;

	@Resource(name = "IbAccountDetailsDao")
	private IbAccountDetailsDao ibAccountDetailsDao;

	// Key : Ib Code
	private HashMap<String, ClientEquityChangeDto> ibClientEquityChangeMap;
	// Key : Ib Code
	private HashMap<String, RebateSupplementaryBean> ibRebateMap;
	// Key : Ib Code
	private HashMap<String, IbCommissionDetailsBean> ibCommissionMap;
	// Key : Ib Code

	private HashMap<String, BonusDetailsBean> ibBonusMap;

	private HashMap<String, IbClientDeficitDto> ibDeficitMap;

	private HashMap<String, IbAccountDetailsBean> ibAccountDetailsMap;

	public IbCommissionEvProcessor() {
		super(Constants.PROCESSOR_TYPE_EV);
	}

	protected boolean validateData() {
		if (ibClientEquityChangeMap != null && ibRebateMap != null && ibCommissionMap != null && ibDeficitMap != null) {
			if (ibClientEquityChangeMap.size() == ibRebateMap.size() && ibRebateMap.size() == ibCommissionMap.size()
					&& ibRebateMap.size() == ibDeficitMap.size())
				return true;
		}
		return false;
	}

	@Override
	public List<IbCommissionDetailsSupplementaryBean> calculate(String updatedBy, Date startDate, Date endDate) throws Exception {

		// get prepared data from DB
		List<IbCommissionDetailsSupplementaryBean> data = ibCommissionDetailsSupplementaryDao
				.getIbCommissionDetailsSupplementaryByDateRange(startDate, endDate);
		if (data != null && data.size() > 0) {
			// Formula = Equity Change + fix commission + rebate commission +
			// deficit + net margin bonus + deposit bonus

			for (IbCommissionDetailsSupplementaryBean commModel : data) {
				double net_ev = commModel.getTotal_equity_change() + commModel.getClient_fix_commission() + commModel.getClient_rebate_commission()
						+ commModel.getDeficit() + commModel.getMargin_in_bonus() + commModel.getDeposit_bonus() + commModel.getAdjustment();
				double ev_comm = -net_ev * commModel.getEv_percentage() / 100;
				commModel.setNet_ev(net_ev);
				commModel.setEv_commission(ev_comm);
				commModel.setLast_update_time(new Date());
				commModel.setLast_update_user(updatedBy);
				// update
				ibCommissionDetailsSupplementaryDao.updateIbCommissionDetailsSupplementary(commModel);
			}
		}
		return ibCommissionDetailsSupplementaryDao.getIbCommissionDetailsSupplementaryByDateRange(startDate, endDate);
	}

	protected void saveData(String updatedBy, Date lastTradeDate) {

		for (String ibCode : ibRebateMap.keySet()) {
			try {

				RebateSupplementaryBean rebate = ibRebateMap.get(ibCode);
				IbCommissionDetailsBean ibComm = ibCommissionMap.get(ibCode);
				ClientEquityChangeDto clientDto = ibClientEquityChangeMap.get(ibCode);
				IbClientDeficitDto ibDeficitDto = ibDeficitMap.get(ibCode);
				BonusDetailsBean bonusDetails = null;
				if (ibBonusMap.containsKey(ibCode)) {
					bonusDetails = ibBonusMap.get(ibCode);
				}
				IbAccountDetailsBean ibAccountDetails = ibAccountDetailsMap.get(ibCode);

				IbCommissionDetailsSupplementaryBean ibCommDetails = new IbCommissionDetailsSupplementaryBean();
				ibCommDetails.setBrand_code(ibComm.getBrand_code());
				ibCommDetails.setClient_fix_commission(ibComm.getClient_fix_commission());
				ibCommDetails.setClient_rebate_commission(ibComm.getRebate_commission_lot() + ibComm.getRebate_commission_pip());
				ibCommDetails.setCurrency(ibComm.getCurrency());
				ibCommDetails.setDeficit(ibDeficitDto.getDeficit_change().doubleValue());
				ibCommDetails.setGroup_code(rebate.getGroup_code());
				ibCommDetails.setCredit_card_charges(new Double(0));
				ibCommDetails.setDeposit_bonus(new Double(0));
				ibCommDetails.setEv_commission(new Double(0));
				ibCommDetails.setAdjustment(new Double(0));
				ibCommDetails.setNet_ev(new Double(0));
				ibCommDetails.setEv_percentage(rebate.getEv_percentage());
				ibCommDetails.setMargin_in_bonus(new Double(0));
				ibCommDetails.setNet_ev(new Double(0));
				ibCommDetails.setAdjustment(new Double(0));
				ibCommDetails.setIb_code(ibCode);
				ibCommDetails.setLast_update_time(new Date());
				ibCommDetails.setLast_update_user(updatedBy);
				ibCommDetails.setRebate_code(rebate.getRebate_code());
				ibCommDetails.setTotal_equity_change(clientDto.getEquity_change_1().doubleValue());
				ibCommDetails.setTrade_date(lastTradeDate);
				ibCommDetails.setNetMargin(clientDto.getNet_margin().doubleValue());
				ibCommDetails.setStatus(Constants.IB_COMMISSION_SUPPLEMENTARY_STATUS_PENDING);

				if (bonusDetails != null && ibAccountDetails != null) {
					Double accumlatedNetMarginIn = 0.0;
					Double currentMonthNetMarginIn = clientDto.getNet_margin().doubleValue();
					if (currentMonthNetMarginIn >= bonusDetails.getMin_amount()) {
						Double marginInBonus = currentMonthNetMarginIn * bonusDetails.getAward_percentage();
						ibCommDetails.setMargin_in_bonus(marginInBonus);
						accumlatedNetMarginIn = 0.0;
					} else {
						Double lastMonthNetMargin = ibAccountDetails.getNet_margin_bonus();
						accumlatedNetMarginIn = lastMonthNetMargin + currentMonthNetMarginIn;
						if (accumlatedNetMarginIn >= bonusDetails.getMin_amount()) {
							Double marginInBonus = accumlatedNetMarginIn * bonusDetails.getAward_percentage();
							ibCommDetails.setMargin_in_bonus(marginInBonus);
							accumlatedNetMarginIn = 0.0;
						} else {
							ibCommDetails.setMargin_in_bonus(0.0);
						}
					}
					ibAccountDetailsDao.updateIbAccountAccumlatedNetMargin(ibCode, accumlatedNetMarginIn);
				}

				// delete data
				ibCommissionDetailsSupplementaryDao.deleteIbCommissionDetailsSupplementary(ibCommDetails.getBrand_code(), ibCommDetails.getIb_code(),
						ibCommDetails.getTrade_date());

				ibCommissionDetailsSupplementaryDao.saveIbCommissionDetailsSupplementary(ibCommDetails);
			} catch (Exception e) {
				logger.error(e, e);
			}
		}
	}

	@Override
	public void prepareData(String updatedBy, Date startDate, Date endDate) throws Exception {
		try {

			// Get rebate setting
			List<RebateSupplementaryBean> rebateList = rebateSupplementaryDao.getRebateSupplementaryByDateRange(startDate, endDate);
			if (rebateList != null && rebateList.size() > 0) {

				// init
				ibClientEquityChangeMap = new HashMap<String, ClientEquityChangeDto>();
				ibRebateMap = new HashMap<String, RebateSupplementaryBean>();
				ibDeficitMap = new HashMap<String, IbClientDeficitDto>();
				// populate rebate code list;
				HashMap<String, RebateSupplementaryBean> rebateMap = new HashMap<String, RebateSupplementaryBean>();

				for (RebateSupplementaryBean rebate : rebateList) {
					if (rebate.getGroup_code().equals(Constants.TYPE_ALL))
						rebateMap.put(rebate.getRebate_code(), rebate);
				}

				// Get ib client rebate map
				List<IbClientRebateMapBean> IbClientRebateMapBean = ibClientRebateMapDao
						.getByRebateCodeList(new ArrayList<String>(rebateMap.keySet()), startDate, endDate);
				for (IbClientRebateMapBean ibClientRebateMapModel : IbClientRebateMapBean) {
					String ibCode = ibClientRebateMapModel.getIb_code();
					// populate ib rebate map
					ibRebateMap.put(ibClientRebateMapModel.getIb_code(), rebateMap.get(ibClientRebateMapModel.getRebate_code()));
					// populate ib client equity change map
					// Get client balance summary (Equity Change , net margin)
					ClientEquityChangeDto equityChange = clientBalanceSummaryDao.getEquityChangeByIbDayRange(ibCode, startDate, endDate);
					ibClientEquityChangeMap.put(ibCode, equityChange);

					// Get client deficit
					List<ClientDeficitDto> deficitList = clientBalanceSummaryDao.getClientDeficit(ibCode, startDate, endDate);
					if (deficitList != null && deficitList.size() == 2) {
						IbClientDeficitDto deficitDto = new IbClientDeficitDto();

						ClientDeficitDto lastMonthModel = deficitList.get(0);
						ClientDeficitDto currentMonthModel = deficitList.get(1);
						deficitDto.setIb_code(ibCode);
						deficitDto.setLast_month_end(lastMonthModel.getTrade_date());
						deficitDto.setLast_deficit(lastMonthModel.getDeficit());
						deficitDto.setCurrent_month_end(currentMonthModel.getTrade_date());
						deficitDto.setCurrent_deficit(currentMonthModel.getDeficit());
						ibDeficitMap.put(ibCode, deficitDto);
					}
				}

				// Get Rebate Summary
				ibCommissionMap = new HashMap<String, IbCommissionDetailsBean>();
				List<IbCommissionDetailsBean> commissionDetailsList = ibCommissionDetailsDao
						.getSummaryByIbCodeListDateRange(new ArrayList<String>(ibClientEquityChangeMap.keySet()), startDate, endDate);
				if (commissionDetailsList != null && commissionDetailsList.size() > 0) {
					for (IbCommissionDetailsBean commModel : commissionDetailsList) {
						ibCommissionMap.put(commModel.getIb_code(), commModel);
					}
				}

				// get ib Bonus details
				ibBonusMap = new HashMap<String, BonusDetailsBean>();
				HashMap<String, BonusDetailsBean> bonusDetailsMap = new HashMap<String, BonusDetailsBean>();
				List<BonusDetailsBean> bonusDetailsBeanList = bonusDetailsDao.getAllBonusDetailss();
				List<IbBonusMapBean> ibBonusMapBeanList = ibBonusMapDao.getAllIbBonusMaps();

				for (BonusDetailsBean bonusDetailsBean : bonusDetailsBeanList) {
					if (bonusDetailsBean.getBonus_type() == Constants.BONUS_TYPE_ACCUMLATED) {
						bonusDetailsMap.put(bonusDetailsBean.getBonus_code(), bonusDetailsBean);
					}
				}

				for (IbBonusMapBean ibBonusMapBean : ibBonusMapBeanList) {
					if (bonusDetailsMap.containsKey(ibBonusMapBean.getBonus_code())) {
						BonusDetailsBean bonusDetailsBean = bonusDetailsMap.get(ibBonusMapBean.getBonus_code());
						ibBonusMap.put(ibBonusMapBean.getIb_code(), bonusDetailsBean);
					}
				}

				// get ib account details
				ibAccountDetailsMap = new HashMap<String, IbAccountDetailsBean>();
				List<IbAccountDetailsBean> ibAccountDetailsBeanList = ibAccountDetailsDao.getAllIbAccountDetailss();
				for (IbAccountDetailsBean ibAccountDetailsBean : ibAccountDetailsBeanList) {
					ibAccountDetailsMap.put(ibAccountDetailsBean.getIb_code(), ibAccountDetailsBean);
				}

				if (validateData())
					saveData(updatedBy, endDate);
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
	}
}
