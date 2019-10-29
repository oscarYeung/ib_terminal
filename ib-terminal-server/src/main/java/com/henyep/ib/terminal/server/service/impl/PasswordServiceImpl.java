package com.henyep.ib.terminal.server.service.impl;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.henyep.ib.terminal.api.dto.db.IbProfileBean;
import com.henyep.ib.terminal.api.dto.request.password.ChangePasswordRequest;
import com.henyep.ib.terminal.api.dto.request.password.ForgotPasswordRequest;
import com.henyep.ib.terminal.api.dto.request.password.ResetPasswordRequest;
import com.henyep.ib.terminal.api.dto.request.password.VerifyTokenRequest;
import com.henyep.ib.terminal.api.dto.response.password.ForgotPasswordResponseDto;
import com.henyep.ib.terminal.api.dto.response.password.PasswordUpdatedResponseDto;
import com.henyep.ib.terminal.api.dto.response.password.VerifyTokenResponseDto;
import com.henyep.ib.terminal.server.dao.IbProfileDao;
import com.henyep.ib.terminal.server.dao.UserDao;
import com.henyep.ib.terminal.server.dto.security.EmailVerifyTokenDto;
import com.henyep.ib.terminal.server.exception.ServiceException;
import com.henyep.ib.terminal.server.global.Constants;
import com.henyep.ib.terminal.server.global.EmailConfig;
import com.henyep.ib.terminal.server.service.PasswordService;
import com.henyep.ib.terminal.server.util.EmailUtil;
import com.henyep.ib.terminal.server.util.SecurityUtil;

@Service("PasswordService")
public class PasswordServiceImpl extends AbstractServiceImpl implements PasswordService {

	private final transient Log logger = LogFactory.getLog(getClass());

	@Resource(name = "IbProfileDao")
	IbProfileDao ibProfileDao;

	@Resource(name = "UserDao")
	UserDao userDao;

	@Resource(name = "SecurityUtil")
	SecurityUtil securityUtil;

	@Resource(name = "EmailUtil")
	EmailUtil emailUtil;
	
	@Resource
	EmailConfig emailConfig;
	
	
	private final static String PASSWORD_SEPARATOR = "_";

	// private String getIbResetPasswordEmailTemplate(){
	// File fFile = new File("C:\\TEMP\\newUser.html");
	// String html_trimmed="";
	// Scanner scanner = new Scanner(new FileReader(fFile));
	// try {
	// while ( scanner.hasNextLine() ){
	// String s=scanner.nextLine();
	// //System.out.println(s);
	// String tmp= s.trim();
	// html_trimmed= html_trimmed.concat(tmp);
	//
	// }
	// }
	// finally {
	// scanner.close();
	// }`
	// }

	// private void sendMailToUser(String user_type, String emailAddress, String
	// token) {
	// String subject = "IB_terminal_identifying_code";
	// String content = "your indentifying code is " + token;
	// List<String> userEmail = new ArrayList<String>();
	// userEmail.add(emailAddress);
	//
	// List<String> bccEmail = new ArrayList<String>();
	// bccEmail.add("jm.zhang@cn.henyep.com");
	//
	// MailContextDto mailDto = new MailContextDto(subject, userEmail, bccEmail,
	// content);
	// mailDto.setMailServerCfg(mailServerCfg);
	// MailSender mailSender = new MailSender(mailDto);
	// mailSender.run();
	//
	// }

	private String getVerificationCode(String user_code, String user_type) throws InvalidKeyException, NoSuchPaddingException,
			NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {

		Calendar calendar = Calendar.getInstance();

		String plainText = user_code + PASSWORD_SEPARATOR + user_type + PASSWORD_SEPARATOR + calendar.getTimeInMillis();
		String encryptedText = securityUtil.encrypt(plainText);
		String base64String = DatatypeConverter.printBase64Binary(encryptedText.getBytes());
		return base64String;
	}

	private EmailVerifyTokenDto getEmailVerifyTokenDto(String encryptText) {
		EmailVerifyTokenDto dto = null;
		String plainText;
		try {
			String decodeString = new String(DatatypeConverter.parseBase64Binary(encryptText));
			plainText = securityUtil.decrypt(decodeString);
			String[] data = plainText.split(PASSWORD_SEPARATOR);
			if (data != null && data.length == 3) {
				dto = new EmailVerifyTokenDto();
				dto.setUser_code(data[0]);
				dto.setUser_type(data[1]);
				long time = Long.parseLong(data[2]);
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(time);
				dto.setRequest_time(calendar.getTime());
			} else {
				logger.debug("decrypted data is NULL or result size not match.");
			}
		} catch (Exception e) {
			logger.error(e, e);
			dto = null;
		}
		return dto;
	}

	@Override
	public ForgotPasswordResponseDto ibForgotPassword(ForgotPasswordRequest request) throws ServiceException {

		logger.debug("ib code=" + request.getBody().getUser_code() + ", email=" + request.getBody().getEmail() + ", type="
				+ request.getBody().getUser_type());
		ForgotPasswordResponseDto response = new ForgotPasswordResponseDto();
		ServiceException se = null;
		try {
			IbProfileBean ibProfile = ibProfileDao.getIbProfileByKey(request.getBody().getUser_code());
			if (ibProfile != null) {
				if (ibProfile.getEmail().equals(request.getBody().getEmail())) {
					String token = getVerificationCode(ibProfile.getIb_code(), Constants.USER_TYPE_IB);
					if (!StringUtils.isBlank(token)) {
						response.setUser_code(request.getBody().getUser_code());
						response.setVerify_code(token);
						response.setEmail(request.getBody().getEmail());
						Map<String, Object> emailModel = new HashMap<String, Object>();
						String emailSubject = emailConfig.getMailSubject_en();
						String template = Constants.EMAIL_TEMPLATE_IB_FORGOT_PASSWORD_EN;
						if (ibProfile.getLanguage() != null && ibProfile.getLanguage().toUpperCase().equals(Constants.LANG_CN)) {
							emailSubject = emailConfig.getMailSubject_cn();
							template = Constants.EMAIL_TEMPLATE_IB_FORGOT_PASSWORD_CN;
						}
						emailModel.put(Constants.EMAIL_RECEIVER, ibProfile.getEmail());
						emailModel.put(Constants.EMAIL_SUBJECT, emailSubject);
						emailModel.put(Constants.EMAIL_TEMPLATE, template);
						emailModel.put(Constants.EMAIL_BCC, emailConfig.getBcc());
						emailModel.put(Constants.EMAIL_SENDER, emailConfig.getSender());
						String htmlToken = StringEscapeUtils.escapeHtml(token);
						emailModel.put("Link", htmlToken);
						emailModel.put("ib_name", ibProfile.getFirst_name() + " " + ibProfile.getLast_name());
						
						emailUtil.sendEmail(emailModel);
					}
				} else {
					se = new ServiceException(Constants.ERR_VERIFICATION_IB_CODE_EMAIL_NOT_MATCH);
				}
			} else {
				logger.debug("Cannot get ib profile :" + request.getBody().getUser_code());
				se = new ServiceException(Constants.ERR_VERIFICATION_IB_CODE_NOT_FOUND);
			}
		} catch (Exception e) {
			logger.error(e, e);
			throw new ServiceException(Constants.ERR_COMMON_INTERNAL_ERROR);
		}
		if (se != null)
			throw se;

		return response;

	}

	@Override
	public VerifyTokenResponseDto verifyToken(VerifyTokenRequest request) throws ServiceException {
		logger.debug("Token =" + request.getBody().getToken());
		VerifyTokenResponseDto response = new VerifyTokenResponseDto();
		EmailVerifyTokenDto verifyTokenDto = getEmailVerifyTokenDto(request.getBody().getToken());
		if (isTokenStillValid(verifyTokenDto)) {
			response.setIb_Code(verifyTokenDto.getUser_code());
			response.setUser_type(verifyTokenDto.getUser_type());
			response.setSuccess(true);
		} else
			throw new ServiceException(Constants.ERR_VERIFICATION_INVALID_TOKEN);
		return response;
	}

	@Override
	public PasswordUpdatedResponseDto changePassword(ChangePasswordRequest request) throws ServiceException {
		logger.debug("User Code:" + request.getBody().getUser_code() + ", User Type:" + request.getBody().getUser_type() + ", Old Pwd:"
				+ request.getBody().getOld_password() + ", New Pwd:" + request.getBody().getNew_password());

		PasswordUpdatedResponseDto dto = new PasswordUpdatedResponseDto();
		ServiceException se = null;

		dto.setUser_code(request.getBody().getUser_code());
		dto.setUser_type(request.getBody().getUser_type());
		try {

			if (request.getBody().getUser_type().equals(Constants.USER_TYPE_IB)) {
				// IB
				IbProfileBean ibProfile = ibProfileDao.getIbProfileByKey(request.getBody().getUser_code());
				if (ibProfile != null) {
					if (ibProfile.getPassword().equals(request.getBody().getOld_password())) {
						ibProfile.setPassword(request.getBody().getNew_password());
						String sender = this.getSender(request.getHeader());
						ibProfile.setLast_update_user(sender);
						ibProfile.setLast_update_time(new Date());
						ibProfileDao.updateIbProfile(ibProfile);
					} else {
						se = new ServiceException(Constants.ERR_VERIFICATION_INCORRECT_PASSWORD);
					}
				} else {
					se = new ServiceException(Constants.ERR_VERIFICATION_IB_CODE_NOT_FOUND);
				}
			} else {
				// User

			}
		} catch (Exception e) {
			logger.error(e, e);
			se = new ServiceException(Constants.ERR_COMMON_INTERNAL_ERROR);
		}

		if (se != null)
			throw se;

		dto.setSuccess(true);
		return dto;

	}

	private boolean isTokenStillValid(EmailVerifyTokenDto tokenDto) {
		if (tokenDto != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(tokenDto.getRequest_time());
			calendar.add(Calendar.HOUR, 2);
			Date now = new Date();
			Date expireTime = calendar.getTime();
			if (now.compareTo(expireTime) > 0)
				return false;
			else
				return true;
		}
		return false;
	}

	@Override
	public PasswordUpdatedResponseDto resetPassword(ResetPasswordRequest request) throws ServiceException {
		logger.debug("User Code:" + request.getBody().getUser_code() + ", User Type:" + request.getBody().getUser_type() + ", Pwd:"
				+ request.getBody().getPassword() + ", Token:" + request.getBody().getToken());

		PasswordUpdatedResponseDto dto = new PasswordUpdatedResponseDto();
		ServiceException se = null;

		dto.setUser_code(request.getBody().getUser_code());
		dto.setUser_type(request.getBody().getUser_type());

		try {
			if (request.getBody().getUser_type().equals(Constants.USER_TYPE_IB)) {
				// IB
				IbProfileBean ibProfile = ibProfileDao.getIbProfileByKey(request.getBody().getUser_code());
				if (ibProfile != null) {
					EmailVerifyTokenDto verifyTokenDto = getEmailVerifyTokenDto(request.getBody().getToken());
					if (verifyTokenDto.getUser_code().equals(request.getBody().getUser_code())) {
						if (isTokenStillValid(verifyTokenDto)) {
							ibProfile.setPassword(request.getBody().getPassword());
							String sender = this.getSender(request.getHeader());
							ibProfile.setLast_update_user(sender);
							ibProfile.setLast_update_time(new Date());
							ibProfileDao.updateIbProfile(ibProfile);
						} else {
							se = new ServiceException(Constants.ERR_VERIFICATION_INVALID_TOKEN);
						}
					} else {
						se = new ServiceException(Constants.ERR_VERIFICATION_IB_CODE_NOT_MATCH);
					}
				} else {
					se = new ServiceException(Constants.ERR_VERIFICATION_IB_CODE_NOT_FOUND);
				}
			} else {
				// User
			}
		} catch (Exception e) {
			logger.error(e, e);
			se = new ServiceException(Constants.ERR_COMMON_INTERNAL_ERROR);
		}

		if (se != null)
			throw se;

		dto.setSuccess(true);
		return dto;
	}

}
