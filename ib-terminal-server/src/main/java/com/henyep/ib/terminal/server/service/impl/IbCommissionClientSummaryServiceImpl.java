package com.henyep.ib.terminal.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.api.dto.request.ibcommission.clientSummary.GetByIbCodeRequest;
import com.henyep.ib.terminal.api.dto.response.ibcommission.clientSummary.GetByIbCodeResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.clientSummary.IbClientCommissionSummaryWebModel;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.server.dao.IbClientMapDao;
import com.henyep.ib.terminal.server.dao.IbCommissionClientSummaryDao;
import com.henyep.ib.terminal.server.service.IbCommissionClientSummaryService;

@Component(value = "IbCommissionClientSummaryService")
public class IbCommissionClientSummaryServiceImpl implements IbCommissionClientSummaryService {

	private IbCommissionClientSummaryDao ibCommissionClientSummaryDao;
	private IbClientMapDao ibClientMapDao;

	@Autowired
	public IbCommissionClientSummaryServiceImpl(IbCommissionClientSummaryDao ibCommissionClientSummaryDao, IbClientMapDao ibClientMapDao) {

		this.ibCommissionClientSummaryDao = ibCommissionClientSummaryDao;
		this.ibClientMapDao = ibClientMapDao;
	}

	@Override
	public GetByIbCodeResponseDto getIbCommissionClientSummaryByDate(GetByIbCodeRequest request) throws Exception {

		List<IbClientCommissionSummaryWebModel> list = ibCommissionClientSummaryDao.getIbClientCommissionSummaryByIbCodeDate(
				request.getBody().getIb_code(), request.getBody().getStart_date(), request.getBody().getEnd_date());

		GetByIbCodeResponseDto dto = new GetByIbCodeResponseDto();
		dto.setList(list);
		return dto;

	}

	@Override
	public List<String> validateIbCommissionClientSummaryByDate(GetByIbCodeRequest request) throws Exception {

		String ibCode = request.getBody().getIb_code();
		List<String> ibClientMaps = ibClientMapDao.getIbClientMapByIbCode(ibCode);
		List<String> errorCodes = new ArrayList<String>();
		if (ibClientMaps.size() == 0) {
			errorCodes.add(Constants.ERR_COMMON_IB_CODE_NOT_EXIST);
		}

		return errorCodes;
	}

}
