package com.henyep.ib.terminal.utils;

import java.lang.reflect.Type;



import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.henyep.ib.terminal.api.dto.request.BaseRequestHeader;
import com.henyep.ib.terminal.api.dto.request.IbRequestDto;
import com.henyep.ib.terminal.api.dto.request.rebate.IbRebateRequestDto;
/**
 * gson ,json 
 * @author Administrator
 *
 */

public class JsonUtil {
	//yyyy-MM-dd HH:mm:ss  yyyy-MM-dd'T'HH:mm:ss.SSSZ
	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();   
	public static String toJson(Object obj) {  
        Gson gson = new Gson();  
        return gson.toJson(obj);  
    }  
	/**
	 * change t to string
	 * @param t
	 * @param type
	 * @return
	 */
	public  <T> String toJson(T t,Type type){
		return gson.toJson(t, type);
		 
	}
    
    public  <T> T fromJson(String str, Type type) {  
        return gson.fromJson(str, type);  
    }  
  
    /** 
     * json
     * @param str   
     * @param type  
     * @return  
     */  
    public  <T> T fromJson(String str, Class<T> type) {  
        return gson.fromJson(str, type);  
    }
    /**
     * 
     * @param trans
     * @return
     * {"ibLoginHeader":{"channelId":"001","messageType":"messtype","messageDate":"2015-02-06"},
     * "ibLoginBody":{"ibLoginName":"ibLoginName","ibStatus":"approved","ibCreateDate":"2015-5-3"}}
     */
    public   <T> String toEntityJson(T trans ){
    	 java.lang.reflect.Type type = new TypeToken<T>() {}.getType(); 
		 String strJson=gson.toJson(trans, type);
		 return strJson;
    }
    
    
    public   <T> String toRequestEntityJson(BaseRequestHeader header,T body ){
    	Gson gsonRequest = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();   
    	IbRequestDto<T> trans= new IbRequestDto<T>() ;
    	trans.setHeader(header);
    	trans.setBody(body);
   	 	java.lang.reflect.Type type = new TypeToken<T>() {}.getType(); 
		 String strJson=gsonRequest.toJson(trans, type);
		 return strJson;
   }
    public   <T> String toRequestEntityJsonNotNull(BaseRequestHeader header,T body ){
    	Gson gsonRequest = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();   
    	IbRequestDto<T> trans= new IbRequestDto<T>() ;
    	trans.setHeader(header);
    	trans.setBody(body);
   	 	java.lang.reflect.Type type = new TypeToken<T>() {}.getType(); 
		 String strJson=gsonRequest.toJson(trans, type);
		 return strJson;
   }
}
