package com.henyep.ib.terminal.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.henyep.ib.terminal.api.dto.db.IbProfileBean;
import com.henyep.ib.terminal.api.dto.db.RebateBean;
import com.henyep.ib.terminal.api.dto.request.IbRequestDto;
import com.henyep.ib.terminal.api.dto.request.user.IbProfileGetRequestDto;
import com.henyep.ib.terminal.api.dto.response.user.IbProfileGetResponseDto;
import com.henyep.ib.terminal.server.component.HySpringUtil;
import com.henyep.ib.terminal.server.dao.IprofileDao;
import com.henyep.ib.terminal.server.dao.RebateDao;
import com.henyep.ib.terminal.server.factory.DtoFactory;
import com.henyep.ib.terminal.server.global.Constants;
import com.henyep.ib.terminal.server.global.EmailConfig;
import com.henyep.ib.terminal.server.service.ProfileService;
import com.henyep.ib.terminal.server.util.EmailUtil;
import com.henyep.ib.terminal.server.util.RebateUtil;

@Service(value = "ProfileService")
public class ProfileServiceImpl implements ProfileService {
	final static Logger log = Logger.getLogger(ProfileServiceImpl.class);
	@Resource(name = "sz_IprofileDao")
	IprofileDao profileDao;
	@Resource(name = "RebateDao")
	RebateDao rebateDao;
	@Resource(name = "sz_SimpleDtoFactory")
	DtoFactory dtoFactory;
	@Resource(name = "sz_HySpringUtil")
	HySpringUtil hySpringUtil;

	@Resource
	EmailConfig emailConfig;

	@Resource(name = "EmailUtil")
	EmailUtil emailUtil;

	public IbProfileGetResponseDto getProfileByIbCode(String ibCode) {

		IbProfileGetResponseDto resp = new IbProfileGetResponseDto();

		try {
			if (ibCode != null) {
				IbProfileBean bean = this.profileDao.getProfileByIbCode(ibCode);
				resp = this.convertReq(bean);
				String rebateCode = RebateUtil.getRebateCode(ibCode);
				RebateBean rebateBean = rebateDao.getRebateByRebateCode(rebateCode);
				if (rebateBean != null) {
					resp.setRebateCode(rebateCode);
					resp.setIgnore_tree_rebate(rebateBean.getIgnore_tree_rebate());
				} else {
					resp.setRebateCode(null);
					resp.setIgnore_tree_rebate(false);
				}

			}
		} catch (Exception ex) {
			resp = null;
		}

		return resp;
	}

	@Override
	public IbProfileGetResponseDto getProfile(IbRequestDto<IbProfileGetRequestDto> getDto) {
		IbProfileGetRequestDto getReq = getDto.getBody();
		IbProfileGetResponseDto resp = new IbProfileGetResponseDto();
		try {
			String brandCode = getReq.getBrandCode();
			String ibCode = getReq.getIbCode();
			if (ibCode != null) {
				IbProfileBean bean = this.profileDao.getProfileByIbCode(ibCode);
				resp = this.convertReq(bean);
			} else {
				String username = getReq.getUsername();
				if (username != null && !"".equals(username.trim())) {
					IbProfileBean bean = this.profileDao.getProfileByUsernameAndBrandCode(username, brandCode);
					resp = this.convertReq(bean);
				}
			}
		} catch (Exception e) {
			log.error("getProfile exception : ", e);
			resp = null;
		}
		return resp;
	}

	private IbProfileGetResponseDto convertReq(IbProfileBean bean) {
		IbProfileGetResponseDto pb = new IbProfileGetResponseDto();
		pb.setIbCode(bean.getIb_code());
		pb.setAddress(bean.getAddress());
		pb.setBrandCode(bean.getBrand_code());
		pb.setChineseName(bean.getChinese_name());
		pb.setCountry(bean.getCountry());
		if (bean.getCreate_time() != null)
			pb.setCreateTime(bean.getCreate_time());
		pb.setEmail(bean.getEmail());
		pb.setFirstName(bean.getFirst_name());
		pb.setLanguage(bean.getLanguage());
		pb.setLastName(bean.getLast_name());
		if (bean.getLast_update_time() != null)
			pb.setLastUpdateTime(bean.getLast_update_time());
		pb.setLastUpdateUser(bean.getLast_update_user());
		pb.setMobile(bean.getMobile());
		pb.setPassword(bean.getPassword());
		pb.setSex(bean.getSex());
		pb.setStatus(bean.getStatus());
		pb.setUsername(bean.getUsername());
		return pb;
	}

	@Override
	public String sendWelcomeEmail(String ibCode, boolean generateRandomPassword, String sender) throws Exception {

		if(generateRandomPassword){
			generateRandomPasswordToIbProfile(ibCode, sender);
		}
		
		IbProfileBean ibProfile = null;
		try{
			ibProfile = profileDao.getProfileByIbCode(ibCode);
		}
		catch(EmptyResultDataAccessException emptyException){
			// if ib code incorrect
		}

		if (ibProfile == null) {
			return Constants.ERR_COMMON_IB_CODE_NOT_EXIST;
		} else {
			log.debug("Sending ib welcome email start");
			Map<String, Object> emailModel = new HashMap<String, Object>();
			String template = Constants.EMAIL_TEMPLATE_WELCOME_REQUEST_EN;
			String emailSubject = emailConfig.getWelcomeSubject_en();

			if (ibProfile.getLanguage() != null && ibProfile.getLanguage().equals(Constants.LANG_CN)) {
				template = Constants.EMAIL_TEMPLATE_WELCOME_REQUEST_CN;
				emailSubject = emailConfig.getWelcomeSubject_cn();
			}

			// receiver email is null, it will directly send to BCC email
			String receiverEmail = ibProfile.getEmail();

			if (receiverEmail == null) {
				return Constants.ERR_COMMON_EMAIL_IS_BLANK;
			} else {

				emailModel.put(Constants.EMAIL_RECEIVER, receiverEmail);

				emailModel.put(Constants.EMAIL_SUBJECT, emailSubject);
				emailModel.put(Constants.EMAIL_TEMPLATE, template);
				emailModel.put(Constants.EMAIL_BCC, emailConfig.getWelcomeBcc());
				emailModel.put(Constants.EMAIL_SENDER, emailConfig.getWelcomeSender());

				emailModel.put("ib_code", ibProfile.getIb_code());
				emailModel.put("password", ibProfile.getPassword());
				emailModel.put("ib_name", ibProfile.getFirst_name() + " " + ibProfile.getLast_name());

				emailUtil.sendEmail(emailModel);

			}

			log.debug("Sending ib welcome email finish");
		}

		return null;

	}
	
	private int generateRandomPasswordToIbProfile(String ibCode, String sender) throws Exception{
		String randomPassword = generateRandomPassword();
		
		int res = profileDao.updateProfilePassword(ibCode,  randomPassword, sender);
		return res;
	}
	
	private String generateRandomPassword() {
		Random random = new Random();

		Character upperCaseChar = (char) ('A' + random.nextInt(26));
		Character lowerCaseChar = (char) ('a' + random.nextInt(26));
		ArrayList<Character> charList = new ArrayList<Character>();
		charList.add(lowerCaseChar);
		charList.add(upperCaseChar);

		for (int i = 0; i < 5; i++) {
			charList.add((char) ('0' + random.nextInt(10)));
		}

		StringBuilder result = new StringBuilder();
		while (charList.size() > 0) {
			int index = random.nextInt(charList.size());
			result.append(charList.get(index));
			charList.remove(index);
		}

		return result.toString();
	}
}
