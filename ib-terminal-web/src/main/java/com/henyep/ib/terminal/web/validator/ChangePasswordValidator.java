package com.henyep.ib.terminal.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.henyep.ib.terminal.entity.ChangePasswordDto;

@Component(value = "ChangePasswordValidator")
public class ChangePasswordValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz.equals(ChangePasswordDto.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		
	}
	
	
}
