package com.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import com.henyep.ib.terminal.entity.RequestTransCommDto;
import com.henyep.ib.terminal.entity.ResponseTransCommomDto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.henyep.ib.terminal.api.dto.request.BaseRequestHeader;
import com.henyep.ib.terminal.api.dto.response.BaseResponseHeader;
import com.henyep.ib.terminal.utils.JsonUtil;

//BaseRequestHeader ReqHeader
public class TestGson {

	public static  void getJson() {
		 Gson gson = new Gson();
		 RequestTransCommDto<BaseRequestHeader,ReqBody> trans= new RequestTransCommDto<BaseRequestHeader,ReqBody>();
		 BaseRequestHeader ibLoginHeader = new BaseRequestHeader();
		 ReqBody ibLoginBody=new ReqBody();
		 trans.setIbLoginBody(ibLoginBody);
		 trans.setIbLoginHeader(ibLoginHeader);
		 ibLoginHeader.setMessageType("messtype");
		 ibLoginHeader.setChannelId("001");
		 ibLoginHeader.setMessageDate("2015-02-06");
		 ibLoginBody.setIbLoginName("ibLoginName");
		 ibLoginBody.setIbCreateDate("2015-5-3");
		
		 ibLoginBody.setIbStatus("approved");
		 System.out.println("ibLoginBody :"+ibLoginBody);
		 java.lang.reflect.Type type = new TypeToken<RequestTransCommDto>() {}.getType(); 
		 String strJson=gson.toJson(trans, type);
		
		 System.out.println("json:"+strJson);

	}
	public void testStrToEntity(){
		/*String respJson =testJson();
		java.lang.reflect.Type type = new TypeToken<ResponseTransCommomDto<BaseResponseHeader,IbLoginResponseDto>>() {}.getType();
		System.out.println(respJson);
		ResponseTransCommomDto <BaseResponseHeader,IbLoginResponseDto> dto=JsonUtil.fromJson(respJson, type);
		System.out.println("body  :"+dto.getBody());
		System.out.println("header:  "+dto.getHeader());
		System.out.println("channeid:"+dto.getHeader().getChannelId());*/
		
	}
	public  String  testJson(){
		FileInputStream file= null;
		InputStreamReader isr= null;
		StringBuffer buffer = new StringBuffer();
		try {
			 String path1=this.getClass().getClassLoader().getResource("").toString();
			 System.out.println( path1);
			 file= new FileInputStream("D:\\newworkspace\\ib-terminal\\ib-terminal-web\\target\\test-classes\\jsonResponse.txt");
			 isr= new InputStreamReader(file);
			BufferedReader bw = new BufferedReader(isr);
			try {
				
				String str=bw.readLine();
				buffer.append(str);
				while(bw.readLine()!=null){
					
					str=bw.readLine();
					buffer.append(str);
				}
				
				
			} catch (IOException e) {
				
			}
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}finally{
			try {
				isr.close();
				file.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(buffer.toString());
		return buffer.toString();
	}
	public static void main(String[] args) {
		getJson();
		TestGson test = new TestGson();
		test.testStrToEntity();
		
		test.testJson();
		
	}
}
