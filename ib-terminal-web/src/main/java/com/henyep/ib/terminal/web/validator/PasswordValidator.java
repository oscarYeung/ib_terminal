package com.henyep.ib.terminal.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.henyep.ib.terminal.entity.PasswordDto;
@Component(value = "PasswordValidator")
public class PasswordValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		System.out.println("  supports  ");
		return clazz.equals(PasswordDto.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		System.out.println("validate validate");
		
	}

}
