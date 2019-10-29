package com.henyep.ib.terminal.server.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.api.dto.db.ClientDailySummaryBean;
import com.henyep.ib.terminal.api.dto.db.IbClientMapBean;
import com.henyep.ib.terminal.api.dto.db.IbCommissionCpaBean;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.server.dao.ClientDailySummaryDao;
import com.henyep.ib.terminal.server.dao.IbClientMapDao;
import com.henyep.ib.terminal.server.dao.IbCommissionCpaDao;
import com.henyep.ib.terminal.server.dao.RebateCpaDao;
import com.henyep.ib.terminal.server.dto.dao.IbRebateCpaDto;

@Component("IbCommissionCpaProcessor")
public class IbCommissionCpaProcessor extends AbstractProcessor<IbCommissionCpaBean> implements Preparator {

	@Resource(name = "ClientDailySummaryDao")
	private ClientDailySummaryDao clientDailySummaryDao;

	@Resource(name = "RebateCpaDao")
	private RebateCpaDao rebateCpaDao;

	@Resource(name = "IbCommissionCpaDao")
	private IbCommissionCpaDao ibCommissionCpaDao;

	@Resource(name = "IbClientMapDao")
	private IbClientMapDao ibClientMapDao;

	// Key: Client Code
	private HashMap<String, List<ClientDailySummaryBean>> clientSummaryList;

	private List<IbCommissionCpaBean> ibCommissionCapList;

	private List<IbRebateCpaDto> ibRebateCpaList;

	// Key : ib code , Value : list of client
	private Map<String, List<IbClientMapBean>> ibClientMap;

	public IbCommissionCpaProcessor() {
		super(Constants.PROCESSOR_TYPE_CPA);
	}

	@Override
	public List<IbCommissionCpaBean> calculate(String updatedBy, Date startDate, Date endDate) throws Exception {

		List<IbCommissionCpaBean> result = new ArrayList<IbCommissionCpaBean>();

		prepareData(updatedBy, startDate, endDate);

		if (ibCommissionCapList != null && ibCommissionCapList.size() > 0) {
			for (IbCommissionCpaBean cpa : ibCommissionCapList) {
				if (cpa.getTotal_lot().compareTo(cpa.getMin_lot()) >= 0) {
					cpa.setLast_update_time(new Date());
					result.add(cpa);
				}
			}
		}

		return result;
	}

	private void populateIbCommissionCpaList(String updatedBy, Date startDate) {
		ibCommissionCapList = new ArrayList<IbCommissionCpaBean>();
		if (ibRebateCpaList != null && ibRebateCpaList.size() > 0 && clientSummaryList != null && clientSummaryList.size() > 0) {

			for (IbRebateCpaDto ibRebateCpa : ibRebateCpaList) {
				String ibCode = ibRebateCpa.getIb_code();
				Date rebateStartDate = ibRebateCpa.getStart_date();
				// Date rebateEndDate = ibRebateCpa.getEnd_date();
				List<IbClientMapBean> clientMapList = ibClientMap.get(ibCode);
				for (IbClientMapBean client : clientMapList) {
					IbCommissionCpaBean comm = new IbCommissionCpaBean();
					comm.setBrand_code(ibRebateCpa.getBrand_code());
					comm.setIb_code(ibCode);
					comm.setMin_lot(ibRebateCpa.getMin_lot());
					comm.setMin_deposit(ibRebateCpa.getMin_deposit());
					comm.setLast_update_user(updatedBy);
					comm.setCurrency(ibRebateCpa.getCurrency());
					comm.setTrade_date(startDate);
					comm.setAmount(ibRebateCpa.getAmount());
					comm.setTotal_lot(new Double(0));
					comm.setTotal_deposit(new Double(0));
					// Check client in rebate period
					if (ibCode.equals(client.getIb_code())
							&& ((client.getEnd_date() == null) || (client.getEnd_date().compareTo(rebateStartDate) >= 1))) {
						comm.setClient_code(client.getClient_code());
						// Calculate total lot and deposit for a client
						List<ClientDailySummaryBean> summaryList = clientSummaryList.get(client.getClient_code());
						if (summaryList != null && summaryList.size() > 0) {
							for (ClientDailySummaryBean summary : summaryList) {
								// Check summary date in rebate period
								if (summary.getTrade_date().compareTo(rebateStartDate) >= 0) {
									comm.setTotal_lot(comm.getTotal_lot() + summary.getTotal_lot());
									comm.setTotal_deposit(comm.getTotal_deposit() + summary.getTotal_deposit_usd());
								}
							}
						}
					}
					ibCommissionCapList.add(comm);
				}

			}
		}
	}

	@Override
	public void prepareData(String updatedBy, Date startDate, Date endDate) throws Exception {
		// Get IB Rebate Cpa
		ibRebateCpaList = rebateCpaDao.getIbRebateCpaByDateRange(startDate, endDate);
		List<String> ibList = new ArrayList<String>();
		Date minStartDate = new Date();
		for (IbRebateCpaDto obj : ibRebateCpaList) {
			if (minStartDate.compareTo(obj.getStart_date()) > 0) {
				minStartDate = obj.getStart_date();
			}

			if (!ibList.contains(obj.getIb_code()))
				ibList.add(obj.getIb_code());
		}

		// get excluded client list
		List<String> excludedClientList = ibCommissionCpaDao.getClientListByIbList(ibList);

		if (ibRebateCpaList != null && ibRebateCpaList.size() > 0) {
			// Get IB Client Map
			// Key : ib code , Value : list of client
			ibClientMap = ibClientMapDao.getIbClientListByIbCodes(ibList, excludedClientList, startDate, endDate);

			// populate client list
			List<String> clientList = new ArrayList<String>();
			if (ibClientMap != null && ibClientMap.size() > 0) {
				for (String ibCode : ibClientMap.keySet()) {
					List<IbClientMapBean> clientMapList = ibClientMap.get(ibCode);
					for (IbClientMapBean client : clientMapList) {
						if (!clientList.contains(client.getClient_code())) {
							clientList.add(client.getClient_code());
						}
					}
				}
				// populate Client Summary
				populateClientSummaryList(minStartDate, endDate, clientList);
				// populate basic Ib Commission Cpa list
				populateIbCommissionCpaList(updatedBy, startDate);
			}
		} else {
			logger.debug("Empty rebate cpa list");
		}

	}

	private void populateClientSummaryList(Date start_date, Date end_date, List<String> clientList) {
		clientSummaryList = new HashMap<String, List<ClientDailySummaryBean>>();
		try {
			List<ClientDailySummaryBean> list = clientDailySummaryDao.getClientDailySummarysByDateRange(start_date, end_date, clientList);
			if (list != null && list.size() > 0) {
				for (ClientDailySummaryBean obj : list) {
					if (!clientSummaryList.containsKey(obj.getClient_code())) {
						clientSummaryList.put(obj.getClient_code(), new ArrayList<ClientDailySummaryBean>());
					}
					List<ClientDailySummaryBean> clientDailySummaryList = clientSummaryList.get(obj.getClient_code());
					clientDailySummaryList.add(obj);
				}
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}
	}

}
