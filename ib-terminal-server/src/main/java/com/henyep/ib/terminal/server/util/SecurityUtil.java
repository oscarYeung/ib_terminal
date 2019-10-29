package com.henyep.ib.terminal.server.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.henyep.ib.terminal.server.global.Constants;
import com.henyep.ib.terminal.server.dto.security.SenderDto;
import com.henyep.ib.terminal.server.exception.ServiceException;

@Component("SecurityUtil")
public class SecurityUtil {

	private static final String SECRET_KEY_1 = "7yhn*UJM9ik,)OL>";
	private static final String SECRET_KEY_2 = "1qaz@WSX3edc$RFV";

	private IvParameterSpec ivParameterSpec;
	private SecretKeySpec secretKeySpec;
	private Cipher cipher;

	public SecurityUtil() throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException {
		ivParameterSpec = new IvParameterSpec(SECRET_KEY_1.getBytes("UTF-8"));
		secretKeySpec = new SecretKeySpec(SECRET_KEY_2.getBytes("UTF-8"), "AES");
		cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	}

	public String getSecretKey(String sender) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
		String plainText = sender + "_" + new Date().getTime();
		return encrypt(plainText);
	}
	

	public SenderDto getSenderDto(String encryptedText) throws ServiceException {
		try {
			SenderDto dto = new SenderDto();
			String plainText = decrypt(encryptedText);
			String[] data = plainText.split("_");
			dto.setSender(data[0]);
			Date time = new Date();
			time.setTime(Long.parseLong(data[1]));
			dto.setLast_request_time(time);
		
			
			return dto;
		} catch (Exception e) {
			throw new ServiceException(Constants.ERR_COMMON_DECRYPT_SECRET_KEY);
		}		
	}

	public String encrypt(String toBeEncrypt) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
			InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
		byte[] encrypted = cipher.doFinal(toBeEncrypt.getBytes());
		return Base64.encodeBase64String(encrypted);
	}

	public String decrypt(String encrypted)
			throws InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
		byte[] decryptedBytes = cipher.doFinal(Base64.decodeBase64(encrypted));
		return new String(decryptedBytes);
	}
}
