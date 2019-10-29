package com.henyep.ib.terminal.server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.henyep.ib.terminal.api.dto.db.IbAccountDetailsBean;
import com.henyep.ib.terminal.api.dto.db.IbClientRebateMapBean;
import com.henyep.ib.terminal.api.dto.db.IbProfileBean;
import com.henyep.ib.terminal.api.dto.db.IbTreeBean;
import com.henyep.ib.terminal.api.dto.db.RebateBean;
import com.henyep.ib.terminal.api.dto.request.IbRequestDto;
import com.henyep.ib.terminal.api.dto.request.ib.IbCreateRequestDto;
import com.henyep.ib.terminal.api.dto.request.ib.IbUpdateRequestDto;
import com.henyep.ib.terminal.api.dto.request.user.IbRegistReq;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.user.IbCreateRespDto;
import com.henyep.ib.terminal.api.dto.response.user.IbRegistResp;
import com.henyep.ib.terminal.api.dto.response.user.IbUpdateRespDto;
import com.henyep.ib.terminal.server.component.HySpringUtil;
import com.henyep.ib.terminal.server.dao.IbAccountDetailsDao;
import com.henyep.ib.terminal.server.dao.IbClientRebateMapDao;
import com.henyep.ib.terminal.server.dao.IbTreeDao;
import com.henyep.ib.terminal.server.dao.IprofileDao;
import com.henyep.ib.terminal.server.dao.RebateDao;
import com.henyep.ib.terminal.server.factory.DtoFactory;
import com.henyep.ib.terminal.server.global.Constants;
import com.henyep.ib.terminal.server.repository.UserIbTreeRepository;
import com.henyep.ib.terminal.server.service.RegistService;
import com.henyep.ib.terminal.server.util.DateUtil;
import com.henyep.ib.terminal.server.util.RebateUtil;

/**
 * RegistService
 * 
 * @author ShenZhong
 * @date 2016年8月17日
 */
@Service(value = "RegistService")
public class RegistServiceImpl extends AbstractServiceImpl implements RegistService {

	@Resource(name = "sz_IprofileDao")
	IprofileDao profileDao;
	@Resource(name = "sz_SimpleDtoFactory")
	DtoFactory dtoFactory;
	@Resource(name = "sz_HySpringUtil")
	HySpringUtil hySpringUtil;
	@Resource(name = "IbAccountDetailsDao")
	IbAccountDetailsDao ibAccountDetailsDao;
	@Resource(name = "RebateDao")
	private RebateDao rebateDao;
	@Resource(name = "IbClientRebateMapDao")
	private IbClientRebateMapDao ibClientRebateMapDao;

	@Resource(name = "IbTreeDao")
	private IbTreeDao ibTreeDao;
	@Resource(name = "UserIbTreeRepository")
	private UserIbTreeRepository userIbTreeRepository;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public IbResponseDto<IbRegistResp> regist(IbRequestDto<IbRegistReq> registDto) {
		IbResponseDto<IbRegistResp> respDto = this.dtoFactory.createResp();
		IbRegistReq registerReq = registDto.getBody();
		IbProfileBean pb = this.convertReq(registerReq);
		String parentIbCode = registerReq.getParentIbCode();
		try {
			this.profileDao.create(pb);
			IbRegistResp resp = new IbRegistResp();
			IbProfileBean profile = this.profileDao.getProfileByUsernameAndPassword(pb.getUsername(), pb.getPassword());
			this.profileDao.execProc(null, new Object[] { parentIbCode, profile.getIb_code() });
			resp.setIbCode(Long.valueOf("" + profile.getIb_code()));
			respDto.setBody(resp);
		} catch (Exception e) {
			logger.error("regist error", e);
			respDto = hySpringUtil.setDtoErrorWithCode(respDto, "system.db.exception");
		}
		return respDto;
	}

	private IbProfileBean convertReq(IbRegistReq registerReq) {
		IbProfileBean pb = new IbProfileBean();
		// pb.setAddress(registerReq.getAddress());
		// pb.setBrand_code(registerReq.getBrandCode());
		// pb.setChinese_name(registerReq.getChineseName());
		// pb.setCountry(registerReq.getCountry());
		// pb.setCreate_time(new Date(registerReq.getCreateTime()));
		// pb.setEmail(registerReq.getEmail());
		// pb.setFirst_name(registerReq.getFirstName());
		// pb.setLanguage(registerReq.getLanguage());
		// pb.setLast_name(registerReq.getLastName());
		// pb.setLast_update_time(new Date(registerReq.getLastUpdateTime()));
		// pb.setLast_update_user(registerReq.getLastUpdateUser());
		// pb.setMobile(registerReq.getMobile());
		// pb.setPassword(registerReq.getPassword());
		// pb.setSex(registerReq.getSex());
		// pb.setStatus(registerReq.getStatus());
		// pb.setUsername(registerReq.getUsername());
		return pb;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public IbCreateRespDto createIbProfile(IbRequestDto<IbCreateRequestDto> ibCreateReq) throws Exception {

		IbCreateRequestDto reqDto = ibCreateReq.getBody();
		String sender = this.getSender(ibCreateReq.getHeader());
		// create ib profile
		IbProfileBean bean = reqDto.getIb_profile();
		bean.setUsername(bean.getIb_code());
		logger.info("ib profile: " + bean.toString());
		bean.setLast_update_user(sender);
		this.profileDao.create(bean);

		// generating rebate bean by ib profile
		String rebateCode = RebateUtil.getRebateCode(bean.getIb_code());
		RebateBean rebate = new RebateBean();
		rebate.setBrand_code(bean.getBrand_code());
		rebate.setDescription("");
		rebate.setIgnore_tree_rebate(reqDto.getIgnore_tree_rebate());
		rebate.setRebate_code(rebateCode);
		rebate.setLast_update_user(sender);
		logger.info("Insert rebate: " + rebate.toString());
		rebateDao.saveRebate(rebate);

		// uploading client rebate map
		bean.setLast_update_user(sender);
		ibClientRebateMapDao.saveIbClientRebateMap(bean, DateUtil.getCurrentDate(), null);

		// insert ib account details
		IbAccountDetailsBean ibAccountDetails = new IbAccountDetailsBean();
		ibAccountDetails.setAccount_balance(0.0);
		ibAccountDetails.setCurrency(reqDto.getCurrency());
		ibAccountDetails.setDay_open(0.0);
		ibAccountDetails.setDay_open_pending_commission(0.0);
		ibAccountDetails.setIb_code(bean.getIb_code());
		ibAccountDetails.setPending_commission(0.0);
		ibAccountDetails.setMonth_to_date(0.0);
		ibAccountDetails.setYear_to_date(0.0);
		ibAccountDetails.setNet_margin_bonus(0.0);
		ibAccountDetails.setLast_update_user(sender);
		logger.info("Insert accountDetail: " + ibAccountDetails.toString());
		this.ibAccountDetailsDao.saveIbAccountDetails(ibAccountDetails);

		// generating ib create response
		IbCreateRespDto respDto = new IbCreateRespDto();
		IbProfileBean dbIbProfile = this.profileDao.getProfileByIbCode(reqDto.getIb_profile().getIb_code());
		if (dbIbProfile != null)
			respDto.setIbProfilebean(dbIbProfile);

		List<IbAccountDetailsBean> dbIbAccountDetailsList = this.ibAccountDetailsDao.getIbAccountDetailsByKey(reqDto.getIb_profile().getIb_code());
		if (dbIbAccountDetailsList.size() == 1) {
			respDto.setIbAccountDetailsBean(dbIbAccountDetailsList.get(0));
		}
		RebateBean dbRebateBean = rebateDao.getRebateByRebateCode(rebateCode);
		respDto.setRebateBean(dbRebateBean);

		List<String> rebateCodeList = new ArrayList<String>();
		rebateCodeList.add(rebateCode);
		List<IbClientRebateMapBean> dbIbClientRebateMaps = ibClientRebateMapDao.getByRebateCodeList(rebateCodeList);
		respDto.setIbClientRebateMapBean(dbIbClientRebateMaps);

		// add in the ib tree
		// get parent code id
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date today = calendar.getTime();

		IbTreeBean parentIb = ibTreeDao.getByIbCode(reqDto.getIb_parent_code());
		logger.info("Add ib to tree");
		if (parentIb != null) {
			int rtn = ibTreeDao.addIbToTree(parentIb.getId(), parentIb.getBrand_code(), parentIb.getIb_code(), dbIbProfile.getIb_code(), today, null,
					sender);
			logger.info("Add ib to tree:" + rtn);
			userIbTreeRepository.clearMap();
		}

		return respDto;
	}

	@Override
	public List<String> validateCreateIbProfile(IbRequestDto<IbCreateRequestDto> ibCreateReq) throws Exception {

		List<String> errMsg = new ArrayList<String>();

		IbCreateRequestDto dto = ibCreateReq.getBody();
		String ibCode = dto.getIb_profile().getIb_code();

		String errorCode = validateIbStatus(dto.getIb_profile().getStatus());
		if (errorCode != null) {
			errMsg.add(errorCode);
		}

		boolean isUserExist = true;
		try {
			IbProfileBean profile = this.profileDao.getProfileByIbCode(ibCode);
			if (profile == null) {
				isUserExist = false;
			}

		} catch (EmptyResultDataAccessException e) {
			isUserExist = false;
		}

		if (isUserExist) {
			errMsg.add(Constants.ERR_IB_CODE_ALREADY_EXIST);
		}

		List<IbClientRebateMapBean> clientRebateMap = ibClientRebateMapDao.getByIbCode(ibCode);
		if (clientRebateMap.size() > 0) {
			errMsg.add(Constants.ERR_REBATE_CLIENT_REBATE_MAP_ALREADY_EXIST);
		}

		String rebateCode = RebateUtil.getRebateCode(ibCode);
		RebateBean dbRebateBean = rebateDao.getRebateByRebateCode(rebateCode);
		if (dbRebateBean != null) {
			errMsg.add(Constants.ERR_REBATE_ALREADY_EXIST);
		}

		if (dto.getIb_profile().getPassword() == null) {
			errMsg.add(Constants.ERR_COMMON_PASSWORD_IS_BLANK);
		}

		return errMsg;
	}

	private String validateIbStatus(String inputStatus) {

		List<String> availableIbStatus = new ArrayList<String>();
		availableIbStatus.add(Constants.IB_STATUS_ACTIVE);
		availableIbStatus.add(Constants.IB_STATUS_INACTIVE);

		if (!availableIbStatus.contains(inputStatus)) {
			return Constants.ERR_IB_INVALID_USER_STATUS;
		} else
			return null;
	}

	// update ib profile
	public IbUpdateRespDto updateIbProfile(IbRequestDto<IbUpdateRequestDto> ibUpdateReq) throws Exception {

		String sender = this.getSender(ibUpdateReq.getHeader());
		IbUpdateRequestDto reqDto = ibUpdateReq.getBody();
		IbProfileBean bean = reqDto.getIb_profile();
		IbProfileBean dbIbProfile = this.profileDao.getProfileByIbCode(reqDto.getIb_profile().getIb_code());
		bean.setLast_update_user(sender);
		if (bean.getPassword() == null) {
			// if password not changed
			bean.setPassword(dbIbProfile.getPassword());
		}
		this.profileDao.updateProfile(bean);

		String rebateCode = RebateUtil.getRebateCode(dbIbProfile.getIb_code());
		RebateBean rebateBean = rebateDao.getRebateByRebateCode(rebateCode);
		if (rebateBean != null) {
			rebateBean.setIgnore_tree_rebate(ibUpdateReq.getBody().getIgnore_tree_rebate());
			rebateBean.setLast_update_user(sender);
			this.rebateDao.updateRebate(rebateBean);
		}

		// generating ib create response
		IbUpdateRespDto respDto = new IbUpdateRespDto();
		IbProfileBean updatedIbProfile = this.profileDao.getProfileByIbCode(reqDto.getIb_profile().getIb_code());
		if (updatedIbProfile != null)
			respDto.setIbProfilebean(updatedIbProfile);

		RebateBean updatedRebateBean = rebateDao.getRebateByRebateCode(rebateCode);
		if (updatedRebateBean != null)
			respDto.setIgnore_tree_rebate(updatedRebateBean.getIgnore_tree_rebate());

		return respDto;

	}

	public List<String> validateUpdateIbProfile(IbRequestDto<IbUpdateRequestDto> ibUpdateReq) throws Exception {
		List<String> errMsg = new ArrayList<String>();

		IbUpdateRequestDto dto = ibUpdateReq.getBody();
		String ibCode = dto.getIb_profile().getIb_code();

		String errorCode = validateIbStatus(dto.getIb_profile().getStatus());
		if (errorCode != null) {
			errMsg.add(errorCode);
		}

		boolean isUserExist = true;
		try {
			IbProfileBean profile = this.profileDao.getProfileByIbCode(ibCode);
			if (profile == null) {
				isUserExist = false;
			}

		} catch (EmptyResultDataAccessException e) {
			isUserExist = false;
		}

		if (!isUserExist) {
			errMsg.add(Constants.ERR_COMMON_IB_CODE_NOT_EXIST);
		}

		return errMsg;
	}
}
