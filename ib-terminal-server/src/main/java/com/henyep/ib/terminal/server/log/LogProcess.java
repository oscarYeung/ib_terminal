package com.henyep.ib.terminal.server.log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.henyep.ib.terminal.server.util.DateUtil;


public class LogProcess {
	public static void writeFileToLocalDisk(String logMessage,String logFileName){
		
		 String str_date =DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE_TIME,DateUtil.getCurrentDate()); //将日期时间格式化 
		 FileOutputStream o=null;
		try {
			String myOs = System.getProperty("os.name");
			String path = System.getProperty("user.dir");
			logMessage=str_date+"--"+logMessage+"\n";
			String fileName;
			if(myOs.indexOf("Windows") >= 0){
				fileName = path+"\\logs\\"+DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE,DateUtil.getCurrentDate())+"-"+logFileName+".log";
			}else{
				fileName = path+"/logs/"+DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE,DateUtil.getCurrentDate())+"-"+logFileName+".log";
			}
			File file = new File(fileName);
			if(!file.exists()){
				boolean mkT = file.createNewFile();
				if(!mkT){
					return ;
				}
			}
	
		    o = new FileOutputStream(fileName,true);
		    o.write(logMessage.getBytes("UTF-8"));
		    o.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(null!=o){
				try {
					 o.close();
				} catch (IOException e) {
					e.printStackTrace();
			}
				
			}
		}
	
		
	}
}
