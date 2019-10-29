package com.henyep.ib.terminal.server.service.impl;

import java.sql.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.henyep.ib.terminal.api.dto.db.IbProfileBean;
import com.henyep.ib.terminal.api.dto.request.IbRequestDto;
import com.henyep.ib.terminal.api.dto.request.ib.IbEditRequestDto;
import com.henyep.ib.terminal.api.dto.response.user.IbProfileEditResp;
import com.henyep.ib.terminal.server.dao.IprofileDao;
import com.henyep.ib.terminal.server.dao.IprofileHistoryDao;
import com.henyep.ib.terminal.server.service.EditService;

/**
 * Update account profile
 *
 * @author ShenZhong
 * @date 2016年8月23日
 */
@Service(value = "EditService")
public class EditServiceImpl extends AbstractServiceImpl implements EditService {
	final static Logger log = Logger.getLogger(EditServiceImpl.class);
	@Resource(name = "sz_IprofileDao")
	IprofileDao profileDao;
	@Resource(name = "sz_IprofileHistoryDao")
	IprofileHistoryDao profileHistoryDao;

	@Transactional(rollbackFor = Exception.class)
	private int updateProfileAndHistory(IbProfileBean pb) throws Exception {
		int num = this.profileDao.updateProfile(pb);
		this.profileHistoryDao.create(pb);
		return num;
	}

	public IbProfileEditResp edit(IbRequestDto<IbEditRequestDto> request) {
		IbEditRequestDto requestBody = request.getBody();
		String sender = this.getSender(request.getHeader());
		requestBody.setLastUpdateUser(sender);
		IbProfileBean ibProfile = this.convertReq(requestBody);
		IbProfileEditResp responseBody = new IbProfileEditResp();
		try {
			int num = this.updateProfileAndHistory(ibProfile);
			responseBody.setRecordNumberAffected(num);
		} catch (Exception e) {
			log.error("err", e);
		}
		return responseBody;
	}

	private IbProfileBean convertReq(IbEditRequestDto editReq) {
		IbProfileBean pb = new IbProfileBean();
		pb.setIb_code(editReq.getIbCode());
		pb.setAddress(editReq.getAddress());
		pb.setBrand_code(editReq.getBrandCode());
		pb.setChinese_name(editReq.getChineseName());
		pb.setCountry(editReq.getCountry());
		if (editReq.getCreateTime() != null)
			pb.setCreate_time(new Date(editReq.getCreateTime()));
		pb.setEmail(editReq.getEmail());
		pb.setFirst_name(editReq.getFirstName());
		pb.setLanguage(editReq.getLanguage());
		pb.setLast_name(editReq.getLastName());
		if (editReq.getLastUpdateTime() != null)
			pb.setLast_update_time(new Date(editReq.getLastUpdateTime()));
		pb.setLast_update_user(editReq.getLastUpdateUser());
		pb.setMobile(editReq.getMobile());
		pb.setPassword(editReq.getPassword());
		pb.setSex(editReq.getSex());
		pb.setStatus(editReq.getStatus());
		pb.setUsername(editReq.getUsername());
		return pb;
	}
}
