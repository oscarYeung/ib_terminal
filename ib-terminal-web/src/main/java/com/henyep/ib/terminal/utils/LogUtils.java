package com.henyep.ib.terminal.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;




/**
 * simple log write 
 * @author lixiaofang
 *
 */
public class LogUtils {
	
	public LogUtils(String logname) {
		if(null==logname||"".equals(logname)){
			this.setLogname("error");
		}else{
			this.setLogname(logname);
		}
	}
	private String logname;
	public String getLogname() {
		return logname;
	}
	public void setLogname(String logname) {
		this.logname = logname;
	}
	
	public String logpath(){
		String fileName=null;
		String str_date = TimeUtil.getTimeShort();  
		try {
			String myOs = System.getProperty("os.name");
			String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			if(System.getProperty("os.name").contains("Window")){
				path = path.replace("classes", "logs");
				//System.out.println("ac--"+path);
			}else{
				String _temp = LogUtils.class.getResource("").toString();
				_temp = _temp.substring(_temp.indexOf("/"), _temp.indexOf("lib"));
				path =_temp+"logs/";
				System.out.println("ac--"+path);
//				path = "//local/apache-tomcat-6.0.32/acAdmin/WebRoot/WEB-INF/config/base.properties";
			}
			fileName=path+str_date+"_"+getLogname()+".log";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return fileName;
	}
	public  void writeLog(String logs){
		 FileOutputStream fos=null;
		try{
			 String str_date1 =TimeUtil.getTimeLong(); //将日期时间格式化 
			 logs=str_date1+"--"+logs+"\n";
			 String fileName=logpath();
			 System.out.println("writeFileToLocalDisk fileName is:"+fileName);
				File file = new File(fileName);
				if(!file.exists()){
					System.out.println("file not exsit");
					 if(!file.getParentFile().exists()) {   
				            if(!file.getParentFile().mkdirs()) {   
				            }else{
				            	 if (file.createNewFile()) {  
				                     System.out.println("创建单个文件成功！");  
				                 } else {  
				                     System.out.println("创建单个文件失败！");  
				                 } 
				            }  
					 }else{
						 System.out.println("目录存在");
					 }
				}
				fos = new FileOutputStream(file,true);
				fos.write(logs.getBytes("UTF-8"));
				fos.close();
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(null!=fos){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
			}
				
			}
		}
	}
	/*public static void main(String[] args) {
		LogUtils log= new LogUtils("train");
		log.writeLog("test here");
	}*/
}
