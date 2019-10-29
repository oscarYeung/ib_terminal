package com.henyep.ib.terminal.server.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class HttpUtil {

	public static int Connection_Time_Out=60000;
	
	public static String sendAndReceiveByHttp(String urlString,String ReqStr){
		String resStr="";
		Date dt=new Date();
        URL url =null;
        HttpURLConnection connection =null;
		try{
            url = new URL(urlString);
            connection =(HttpURLConnection)url.openConnection();
            connection.setRequestProperty("content-type", "text/xml");            
            connection.setConnectTimeout(Connection_Time_Out);
            connection.setReadTimeout(Connection_Time_Out);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/json");
            connection.connect();            
            OutputStreamWriter out=new OutputStreamWriter(connection.getOutputStream());
            out.write(ReqStr);            
            out.write("\r\n");   
            out.flush();   
            out.close();   
            BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));   
            String line="";   
            while((line=reader.readLine())!=null){   
            	resStr=resStr+line;   
            }
            reader.close();
        }catch(MalformedURLException e){
              System.out.println("URL Error------------------------------"+urlString);
              e.printStackTrace();
              resStr="ConnectError";
        }catch(IOException e){
             System.out.println("IOException occured while connecting----------------"+urlString);
             e.printStackTrace();
             resStr="ConnectError";
        }finally{
        	Date dt2=new Date();
        	System.out.println("Last---------------------"+(dt2.getTime()-dt.getTime())/1000);
        	if (connection!=null)
        		connection.disconnect();        	
            url =null;
            connection =null;
        }
		return resStr;
    }	
}
