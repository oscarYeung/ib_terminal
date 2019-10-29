package com.henyep.ib.terminal.server.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.InitBinder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.henyep.ib.terminal.api.dto.request.IbRequestDto;
import com.henyep.ib.terminal.server.component.HySpringUtil;
import com.henyep.ib.terminal.server.dto.security.SenderDto;
import com.henyep.ib.terminal.server.exception.ServiceException;
import com.henyep.ib.terminal.server.factory.DtoFactory;
import com.henyep.ib.terminal.server.util.SecurityUtil;
import com.henyep.ib.terminal.server.util.adapter.JsonDateTypeAdapter;

public abstract class AbstractController {
	protected final transient Log logger = LogFactory.getLog(getClass());
	protected Gson g;

//	@Resource(name = "messageSource")
//	protected MessageSource messageSource;

	@Resource(name = "sz_SimpleDtoFactory")
	protected DtoFactory dtoFactory;

	@Resource(name = "sz_HySpringUtil")
	protected HySpringUtil hySpringUtil;

	@Resource(name = "SecurityUtil")
	private SecurityUtil securityUtil;
	
	protected Validator validator;

	@Autowired
	public AbstractController(Validator validator) {
		this.validator = validator;
		// TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		g = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDateTypeAdapter()).create();
	}

	@InitBinder()
	public void initBinder(DataBinder binder) {
		binder.addValidators(validator);
	}
	
	protected String getSender(IbRequestDto<?> request) throws ServiceException{
		SenderDto sender = getSenderDto(request);
		if(sender != null){
			return sender.getSender();
		}
		else{
			return "";
		}
	}
	
	protected SenderDto getSenderDto(IbRequestDto<?> request) throws ServiceException{
		SenderDto sender = securityUtil.getSenderDto(request.getHeader().getSecretKey());
		if(sender != null){
			return sender;
		}
		else{
			return null;
		}
	}

}
