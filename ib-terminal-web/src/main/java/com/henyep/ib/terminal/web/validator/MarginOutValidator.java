package com.henyep.ib.terminal.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.henyep.ib.terminal.entity.InsertMarginOutDto;
@Component(value = "MarginOutValidator")
public class MarginOutValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		
		return clazz.equals(InsertMarginOutDto.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		InsertMarginOutDto outDto=(InsertMarginOutDto)target;
		if(!errors.hasErrors()){
			if(outDto.getMax_withdrawal()<outDto.getAmount()){
				errors.reject("withdrawal.not.enough.money", "withdrawal.not.enough.money");
				System.out.println("test");
			}
		}
	}

}
