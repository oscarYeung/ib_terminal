package com.camel;

import java.awt.image.BufferStrategy;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import com.henyep.ib.terminal.entity.ResponseTransCommomDto;
import com.fasterxml.jackson.core.util.BufferRecycler;
import com.google.gson.reflect.TypeToken;


import com.henyep.ib.terminal.api.dto.request.BaseRequestHeader;
import com.henyep.ib.terminal.api.dto.response.BaseResponseHeader;
import com.henyep.ib.terminal.operator.MiddleOperator;
import com.henyep.ib.terminal.utils.ConstantFields;
import com.henyep.ib.terminal.utils.JsonUtil;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:camel*.xml" })
public class TestMiddleOPerator {
	@Resource(name = "middleOperatorBean")
	MiddleOperator middleOperator;
	@Test
	public  void testOp() {
	    /* String json="";
		String rsponseJson = middleOperator.sendMessage(json,ConstantFields.Login_PATH);
		java.lang.reflect.Type type = new TypeToken<ResponseTransCommomDto<BaseResponseHeader,IbLoginResponseDto>>() {}.getType();
		System.out.println(rsponseJson);
		ResponseTransCommomDto <BaseResponseHeader,IbLoginResponseDto> dto= JsonUtil.fromJson(rsponseJson, type);
		System.out.println("reslut=========: "+dto.getHeader().getChannelId());*/
	}
	
	
	
	public static void main(String[] args) {
		TestMiddleOPerator op = new TestMiddleOPerator();
		
	}
	public MiddleOperator getMiddleOperator() {
		return middleOperator;
	}
	public void setMiddleOperator(MiddleOperator middleOperator) {
		this.middleOperator = middleOperator;
	}
	
}
