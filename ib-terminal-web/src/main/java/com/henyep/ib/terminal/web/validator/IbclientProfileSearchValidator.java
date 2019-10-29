package com.henyep.ib.terminal.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.henyep.ib.terminal.entity.ClientprofileDto;
@Component(value = "IbclientProfileSearchValidator")
public class IbclientProfileSearchValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(ClientprofileDto.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		
	}

}
