package com.henyep.ib.terminal.server.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.henyep.ib.terminal.api.dto.db.IbClientLinkBean;
import com.henyep.ib.terminal.api.dto.request.ib.client.link.AddIbClientLinkRequest;
import com.henyep.ib.terminal.api.dto.request.ib.client.link.UpdateIbClientLinkRequest;
import com.henyep.ib.terminal.api.dto.response.ib.client.link.GetIbClientLinkResponseDto;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.server.dao.IbClientLinkDao;
import com.henyep.ib.terminal.server.service.IbClientLinkService;

@Service(value = "IbClientLinkService")
public class IbClientLinkServiceImpl extends AbstractServiceImpl implements IbClientLinkService {
	private final transient Log logger = LogFactory.getLog(getClass());

	@Resource
	private IbClientLinkDao ibClientLinkDao;

	@Override
	public GetIbClientLinkResponseDto getIbClientLinkList(String brand_code, String ib_code) {

		GetIbClientLinkResponseDto responseDto = new GetIbClientLinkResponseDto();
		try {
			responseDto.setIbClientLinkList(ibClientLinkDao.getByBrandCodeAndIbCode(brand_code, ib_code));
		} catch (Exception e) {
			logger.error(e);
			responseDto = null;
		}
		return responseDto;
	}

	@Override
	public GetIbClientLinkResponseDto addIbClientLink(AddIbClientLinkRequest request) {
		IbClientLinkBean ibClientLink = request.getBody().getIbClientLink();
		
		GetIbClientLinkResponseDto responseDto = new GetIbClientLinkResponseDto();
		try {
			Integer seq_id = ibClientLinkDao.getMaxSeqId(ibClientLink.getBrand_code(), ibClientLink.getIb_code());
			if (ibClientLink.getSeq_id() == null || ibClientLink.getSeq_id().equals(0)) {
				ibClientLink.setSeq_id(seq_id++);
			}
			
			String sender = this.getSender(request.getHeader());
			ibClientLink.setLast_update_user(sender);
			int rtn = ibClientLinkDao.saveIbClientLink(ibClientLink);
			if (rtn > 0) {
				IbClientLinkBean bean = ibClientLinkDao.getIbClientLinkByKey(ibClientLink.getClient_package_code(), ibClientLink.getIb_code(),
						ibClientLink.getUrl(), ibClientLink.getCampaign_id());
				if (bean != null)
					responseDto.setIbClientLinkList(Arrays.asList(bean));
				else
					responseDto.setIbClientLinkList(null);
			}
		} catch (Exception e) {
			logger.error(e);
			responseDto = null;
		}
		return responseDto;
	}

	@Override
	public GetIbClientLinkResponseDto updateIbClientLink(UpdateIbClientLinkRequest request) {
		IbClientLinkBean ibClientLink = request.getBody().getIbClientLink();
		GetIbClientLinkResponseDto responseDto = new GetIbClientLinkResponseDto();
		try {
			String sender = this.getSender(request.getHeader());
			ibClientLink.setLast_update_user(sender);
			int rtn = ibClientLinkDao.updateIbClientLink(ibClientLink);
			if (rtn > 0) {
				IbClientLinkBean bean = ibClientLinkDao.getIbClientLinkByKey(ibClientLink.getClient_package_code(), ibClientLink.getIb_code(),
						ibClientLink.getUrl(), ibClientLink.getCampaign_id());
				if (bean != null)
					responseDto.setIbClientLinkList(Arrays.asList(bean));
				else
					responseDto.setIbClientLinkList(null);
			}
		} catch (Exception e) {
			logger.error(e);
			responseDto = null;
		}
		return responseDto;
	}

	@Override
	public List<String> validateAddIbClientLink(AddIbClientLinkRequest request) throws Exception {
		IbClientLinkBean ibClientLink = request.getBody().getIbClientLink();
		IbClientLinkBean bean = ibClientLinkDao.getIbClientLinkByKey(ibClientLink.getClient_package_code(), ibClientLink.getIb_code(),
				ibClientLink.getUrl(), ibClientLink.getCampaign_id());
		List<String> errList = new ArrayList<String>();
		if (bean != null) {
			errList.add(Constants.ERR_IB_CLIENT_LINK_ALREADY_EXIST);
		}
		return errList;
	}

	@Override
	public List<String> validateUpdateIbClientLink(UpdateIbClientLinkRequest request) throws Exception {
		IbClientLinkBean ibClientLink = request.getBody().getIbClientLink();
		IbClientLinkBean bean = ibClientLinkDao.getIbClientLinkByKey(ibClientLink.getClient_package_code(), ibClientLink.getIb_code(),
				ibClientLink.getUrl(), ibClientLink.getCampaign_id());
		List<String> errList = new ArrayList<String>();
		if (bean == null) {
			errList.add(Constants.ERR_IB_CLIENT_LINK_NOT_EXIST);
		}
		return errList;
	}

}
