package com.henyep.ib.terminal.server.processor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.api.dto.db.IbClientRebateMapBean;
import com.henyep.ib.terminal.api.dto.db.IbCommissionDetailsBean;
import com.henyep.ib.terminal.api.dto.db.IbCommissionDetailsSupplementaryBean;
import com.henyep.ib.terminal.api.dto.db.RebateSupplementaryBean;
import com.henyep.ib.terminal.api.dto.request.ibcommission.EvFigureUpdateDto;
import com.henyep.ib.terminal.server.dao.ClientBalanceSummaryDao;
import com.henyep.ib.terminal.server.dao.IbClientRebateMapDao;
import com.henyep.ib.terminal.server.dao.IbCommissionDetailsDao;
import com.henyep.ib.terminal.server.dao.IbCommissionDetailsSupplementaryDao;
import com.henyep.ib.terminal.server.dao.RebateSupplementaryDao;
import com.henyep.ib.terminal.server.dto.dao.GroupDeficitDto;
import com.henyep.ib.terminal.server.dto.dao.GroupEquityChangeDto;
import com.henyep.ib.terminal.server.dto.dao.IbRebateGroupCodeDto;
import com.henyep.ib.terminal.server.dto.processor.IbGroupDeficitDto;
import com.henyep.ib.terminal.server.global.Constants;

@Component("IbCommissionGroupEvProcessor")
public class IbCommissionGroupEvProcessor extends AbstractProcessor<IbCommissionDetailsSupplementaryBean> implements Preparator {

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

	public IbCommissionGroupEvProcessor() {
		super(Constants.PROCESSOR_TYPE_GROUP_EV);
	}

	@Override
	public List<IbCommissionDetailsSupplementaryBean> calculate(String updatedBy, Date startDate, Date endDate) throws Exception {
		List<IbCommissionDetailsSupplementaryBean> data = ibCommissionDetailsSupplementaryDao
				.getIbCommissionDetailsSupplementaryByDateRange(startDate, endDate);
		if (data != null && data.size() > 0) {
			// Formula = Equity Change + margin in bonus + adjustment
			// - fix commission - rebate commission - deposit bonus - deficit -
			// credit card charges
			//
			for (IbCommissionDetailsSupplementaryBean commModel : data) {
				double equityChange = commModel.getTotal_equity_change() == null ? 0 : commModel.getTotal_equity_change();
				double marginInBonus = commModel.getMargin_in_bonus() == null ? 0 : commModel.getMargin_in_bonus();
				double adjustment = commModel.getAdjustment() == null ? 0 : commModel.getAdjustment();
				double fixComm = commModel.getClient_fix_commission() == null ? 0 : commModel.getClient_fix_commission();
				double rebateComm = commModel.getClient_rebate_commission() == null ? 0 : commModel.getClient_rebate_commission();
				double depositBonus = commModel.getDeposit_bonus() == null ? 0 : commModel.getDeposit_bonus();
				double deficit = commModel.getDeficit() == null ? 0 : commModel.getDeficit();
				double creditCardCharges = commModel.getCredit_card_charges() == null ? 0 : commModel.getCredit_card_charges();

				double net_ev = -equityChange + marginInBonus + adjustment + depositBonus + deficit - fixComm - rebateComm;
				double ev_comm = (net_ev * commModel.getEv_percentage() / 100) + creditCardCharges;
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

	private void populateEvCommission(IbCommissionDetailsSupplementaryBean commModel, String updatedBy) {

		// Formula = Equity Change + margin in bonus + adjustment
		// - fix commission - rebate commission - deposit bonus - deficit
		// - credit card charges

		double equityChange = commModel.getTotal_equity_change() == null ? 0 : commModel.getTotal_equity_change();
		double marginInBonus = commModel.getMargin_in_bonus() == null ? 0 : commModel.getMargin_in_bonus();
		double adjustment = commModel.getAdjustment() == null ? 0 : commModel.getAdjustment();
		double fixComm = commModel.getClient_fix_commission() == null ? 0 : commModel.getClient_fix_commission();
		double rebateComm = commModel.getClient_rebate_commission() == null ? 0 : commModel.getClient_rebate_commission();
		double depositBonus = commModel.getDeposit_bonus() == null ? 0 : commModel.getDeposit_bonus();
		double deficit = commModel.getDeficit() == null ? 0 : commModel.getDeficit();
		double creditCardCharges = commModel.getCredit_card_charges() == null ? 0 : commModel.getCredit_card_charges();
		double net_ev = -equityChange + marginInBonus + adjustment + depositBonus + deficit - fixComm - rebateComm;
		double ev_comm = (net_ev * commModel.getEv_percentage() / 100) + creditCardCharges;
		commModel.setNet_ev(net_ev);
		commModel.setEv_commission(ev_comm);
		commModel.setLast_update_time(new Date());
		commModel.setLast_update_user(updatedBy);
	}

	public List<IbCommissionDetailsSupplementaryBean> calculateByTarget(String updatedBy, List<EvFigureUpdateDto> target, Date startDate,
			Date endDate) throws Exception {
		List<IbCommissionDetailsSupplementaryBean> data = ibCommissionDetailsSupplementaryDao
				.getIbCommissionDetailsSupplementaryByDateRange(startDate, endDate);
		List<IbCommissionDetailsSupplementaryBean> result = new ArrayList<IbCommissionDetailsSupplementaryBean>();

		if (data != null && data.size() > 0) {
			for (IbCommissionDetailsSupplementaryBean commModel : data) {
				for (EvFigureUpdateDto targetModel : target) {
					if (commModel.getIb_code().equals(targetModel.getIb_code()) && commModel.getGroup_code().equals(targetModel.getGroup_code())) {
						populateEvCommission(commModel, updatedBy);
						// update
						IbCommissionDetailsSupplementaryBean returnModel = ibCommissionDetailsSupplementaryDao
								.updateIbCommissionDetailsSupplementary(commModel);
						if (returnModel != null)
							result.add(returnModel);
					}
				}
			}
		}
		return result;
	}

	@Override
	public void prepareData(String updatedBy, Date startDate, Date endDate) throws Exception {
		List<RebateSupplementaryBean> rebateList = rebateSupplementaryDao.getRebateSupplementaryByDateRange(startDate, endDate);
		if (rebateList != null && rebateList.size() > 0) {
			// Key : IbCode_GroupCode
			HashMap<String, GroupEquityChangeDto> ibGroupEquityChangeMap = new HashMap<String, GroupEquityChangeDto>();
			// Key : IbCode_GroupCode
			HashMap<String, IbGroupDeficitDto> ibGroupDeficitMap = new HashMap<String, IbGroupDeficitDto>();
			// Key : Ib Code
			HashMap<String, List<RebateSupplementaryBean>> ibRebateMap = new HashMap<String, List<RebateSupplementaryBean>>();
			// Key : Ib Code
			HashMap<String, IbCommissionDetailsBean> ibCommissionMap = new HashMap<String, IbCommissionDetailsBean>();
			// Key : Group Code, Value : ib Code
			HashMap<String, IbRebateGroupCodeDto> groupCodeIbList = new HashMap<String, IbRebateGroupCodeDto>();
			// populate rebate code list
			// Key : Rebate Code
			HashMap<String, List<RebateSupplementaryBean>> rebateMap = new HashMap<String, List<RebateSupplementaryBean>>();

			List<String> groupList = new ArrayList<String>();
			for (RebateSupplementaryBean rebate : rebateList) {
				if (StringUtils.isNotBlank(rebate.getGroup_code()) && rebate.getEv_percentage() > 0) {
					if (!rebateMap.containsKey(rebate.getRebate_code())) {
						rebateMap.put(rebate.getRebate_code(), new ArrayList<RebateSupplementaryBean>());
					}
					List<RebateSupplementaryBean> list = rebateMap.get(rebate.getRebate_code());
					list.add(rebate);
					rebateMap.put(rebate.getRebate_code(), list);
					groupList.add(rebate.getGroup_code());
				}
			}

			// Set up ib rebate map
			List<IbClientRebateMapBean> IbClientRebateMapList = ibClientRebateMapDao.getByRebateCodeList(new ArrayList<String>(rebateMap.keySet()),
					startDate, endDate);
			for (IbClientRebateMapBean ibClientRebateMapModel : IbClientRebateMapList) {
				// populate ib rebate map => ib :rebate Code
				ibRebateMap.put(ibClientRebateMapModel.getIb_code(), rebateMap.get(ibClientRebateMapModel.getRebate_code()));
			}

			List<IbRebateGroupCodeDto> rebateGroupCodeList = ibClientRebateMapDao.getIbRebateGroupCodeList(startDate, endDate);
			for (IbRebateGroupCodeDto model : rebateGroupCodeList) {
				groupCodeIbList.put(model.getGroup_code(), model);
			}

			// get equity change from SAP
			List<GroupEquityChangeDto> equityChangeList = clientBalanceSummaryDao.getGroupEquityChangeFromSAP(groupList, startDate, endDate);
			// populate ib equity change
			for (GroupEquityChangeDto ec : equityChangeList) {
				IbRebateGroupCodeDto rebateGroupCodeModel = groupCodeIbList.get(ec.getGroup_code());
				if (rebateGroupCodeModel != null) {
					String key = rebateGroupCodeModel.getIb_code() + "_" + ec.getGroup_code();
					ec.setBrand_code(rebateGroupCodeModel.getBrand_Code());
					ibGroupEquityChangeMap.put(key, ec);
				}
			}

			// get deficit from SAP
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			Date last_month_end = calendar.getTime();
			List<GroupDeficitDto> deficitList = clientBalanceSummaryDao.getGroupDeficitFromSAP(groupList, last_month_end, endDate);
			for (GroupDeficitDto deficit : deficitList) {
				IbRebateGroupCodeDto rebateGroupCodeModel = groupCodeIbList.get(deficit.getGroup_code());
				if (rebateGroupCodeModel != null) {
					String key = rebateGroupCodeModel.getIb_code() + "_" + deficit.getGroup_code();
					if (!ibGroupDeficitMap.containsKey(key)) {
						ibGroupDeficitMap.put(key, new IbGroupDeficitDto());
					}
					IbGroupDeficitDto ibDeficit = ibGroupDeficitMap.get(key);
					ibDeficit.setGroup_code(deficit.getGroup_code());
					ibDeficit.setIb_code(rebateGroupCodeModel.getIb_code());
					if (deficit.getTrade_date().compareTo(endDate) <= 0 && deficit.getTrade_date().compareTo(last_month_end) > 0) {
						ibDeficit.setCurrent_deficit(deficit.getDeficit());
						ibDeficit.setCurrent_month_end(endDate);
					} else {
						ibDeficit.setLast_deficit(deficit.getDeficit());
						ibDeficit.setLast_month_end(last_month_end);
					}
					ibGroupDeficitMap.put(key, ibDeficit);
				}
			}

			// Get Rebate Summary
			List<IbCommissionDetailsBean> commissionDetailsList = ibCommissionDetailsDao
					.getIbCommissionSummaryWithGroupCode(new ArrayList<String>(ibRebateMap.keySet()), startDate, endDate);
			if (commissionDetailsList != null && commissionDetailsList.size() > 0) {
				for (IbCommissionDetailsBean commModel : commissionDetailsList) {
					ibCommissionMap.put(commModel.getIb_code(), commModel);
				}
			}

			// delete data
			for (String ibCode : ibCommissionMap.keySet()) {
				ibCommissionDetailsSupplementaryDao.deleteIbCommissionDetailsSupplementary(ibCommissionMap.get(ibCode).getBrand_code(), ibCode,
						endDate);
			}

			// save to db
			for (String key : ibGroupDeficitMap.keySet()) {
				String[] data = key.split("_");
				String ibCode = data[0];
				String groupCode = data[1];
				List<RebateSupplementaryBean> rebateSupplList = ibRebateMap.get(ibCode);
				for (RebateSupplementaryBean rebateModel : rebateSupplList) {
					if (rebateModel.getGroup_code().equals(groupCode)) {
						IbCommissionDetailsBean ibComm = ibCommissionMap.get(ibCode);
						if (ibComm == null || (ibComm.getGroup_code() != null && !ibComm.getGroup_code().equals(rebateModel.getGroup_code())))
							ibComm = new IbCommissionDetailsBean();
						GroupEquityChangeDto grpEquityChangeDto = ibGroupEquityChangeMap.get(key);
						IbGroupDeficitDto grpDeficitDto = ibGroupDeficitMap.get(key);
						IbCommissionDetailsSupplementaryBean ibCommDetails = new IbCommissionDetailsSupplementaryBean();
						ibCommDetails.setBrand_code(grpEquityChangeDto.getBrand_code());
						ibCommDetails.setClient_fix_commission(ibComm.getClient_fix_commission());
						ibCommDetails.setClient_rebate_commission(ibComm.getRebate_commission_lot() + ibComm.getRebate_commission_pip());
						ibCommDetails.setCurrency(rebateModel.getCurrency());
						ibCommDetails.setDeficit(grpDeficitDto.getDeficit_change().doubleValue());
						ibCommDetails.setEv_percentage(rebateModel.getEv_percentage());
						ibCommDetails.setEv_commission(new Double(0));
						ibCommDetails.setIb_code(ibCode);
						ibCommDetails.setGroup_code(rebateModel.getGroup_code());
						ibCommDetails.setLast_update_time(new Date());
						ibCommDetails.setLast_update_user(updatedBy);
						ibCommDetails.setRebate_code(rebateModel.getRebate_code());
						ibCommDetails.setTotal_equity_change(grpEquityChangeDto.getEquity_change_1().doubleValue());
						ibCommDetails.setTrade_date(endDate);
						ibCommDetails.setNetMargin(grpEquityChangeDto.getNet_margin().doubleValue());
						ibCommDetails.setStatus(Constants.IB_COMMISSION_SUPPLEMENTARY_STATUS_PENDING);
						ibCommDetails.setCredit_card_charges(0.0);
						ibCommDetails.setAdjustment(0.0);
						ibCommDetails.setMargin_in_bonus(0.0);
						ibCommDetails.setDeposit_bonus(0.0);
						ibCommDetails.setEv_commission(0.0);
						ibCommDetails.setNet_ev(0.0);

						// insert data
						ibCommissionDetailsSupplementaryDao.saveIbCommissionDetailsSupplementary(ibCommDetails);
					}
				}
			}
		}
	}
}
