package com.henyep.ib.terminal.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.api.dto.db.IbProfileBean;
import com.henyep.ib.terminal.api.dto.request.ibcommission.summary.GetByTeamHeadRequest;
import com.henyep.ib.terminal.api.dto.response.ibcommission.summary.GetByTeamHeadResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.summary.IbCommissionSummaryWebModel;
import com.henyep.ib.terminal.server.dao.IbCommissionSummaryDao;
import com.henyep.ib.terminal.server.dao.IbProfileDao;
import com.henyep.ib.terminal.server.global.Constants;
import com.henyep.ib.terminal.server.service.IbCommissionSummaryService;

@Component(value = "IbCommissionSummaryService")
public class IbCommissionSummaryServiceImpl implements IbCommissionSummaryService {

	private IbCommissionSummaryDao summaryDao;
	private IbProfileDao ibProfileDao;

	@Autowired
	public IbCommissionSummaryServiceImpl(IbCommissionSummaryDao summaryDao, IbProfileDao ibProfileDao) {
		this.summaryDao = summaryDao;
		this.ibProfileDao = ibProfileDao;
	}

	@Override
	public GetByTeamHeadResponseDto GetIbCommissionSummary(GetByTeamHeadRequest request) throws Exception {
		Date startDate = request.getBody().getStart_date();
		Date endDate = request.getBody().getEnd_date();
		String ibCode = request.getBody().getIb_code();
		List<IbCommissionSummaryWebModel> list = summaryDao.getIbCommissionSummaryByTeamHead(ibCode, startDate, endDate);
		GetByTeamHeadResponseDto dto = new GetByTeamHeadResponseDto();
		dto.setList(list);
		return dto;
	}

	@Override
	public List<String> ValidateGetIbCommissionSummary(GetByTeamHeadRequest request) throws Exception {
		List<String> errorCodes = new ArrayList<String>();
		List<IbProfileBean> ibProfileBeans = ibProfileDao.getIbProfileByIbCode(request.getBody().getIb_code());
		if (ibProfileBeans.size() == 0)
			errorCodes.add(Constants.ERR_COMMON_IB_CODE_NOT_EXIST);

		return errorCodes;
	}

}
