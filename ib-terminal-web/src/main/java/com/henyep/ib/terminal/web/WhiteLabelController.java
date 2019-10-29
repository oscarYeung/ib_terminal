package com.henyep.ib.terminal.web;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.util.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;

import com.google.gson.reflect.TypeToken;
import com.henyep.ib.terminal.entity.AdminMaxWithdrawal;
import com.henyep.ib.terminal.entity.ConfigFields;
import com.henyep.ib.terminal.entity.MaxWithdrawalDto;
import com.henyep.ib.terminal.entity.SerachFiles;
import com.henyep.ib.terminal.operator.MiddleOperator;
import com.henyep.ib.terminal.operator.MiddleOperatorWhiteLabel;
import com.henyep.ib.terminal.utils.ConstantFields;
import com.henyep.ib.terminal.utils.DealErrorsUtil;
import com.henyep.ib.terminal.utils.HeaderUtil;
import com.henyep.ib.terminal.utils.JsonUtil;
import com.henyep.ib.terminal.utils.TimeUtil;
import com.henyep.ib.terminal.utils.WhiteLabelUtil;
import com.henyep.white.label.api.dto.request.account.registration.GetAccountRegistrationByDateRequestDto;
import com.henyep.white.label.api.dto.request.account.registration.GetRegisteredAccountRequestDto;
import com.henyep.white.label.api.dto.request.balance.transfer.BalanceTransferRequestDto;
import com.henyep.white.label.api.dto.request.balance.transfer.GetMasterAccountMaxWithdrawalRequestDto;
import com.henyep.white.label.api.dto.request.balance.transfer.GetMaxWithdrawalRequestDto;
import com.henyep.white.label.api.dto.request.document.DownloadRequestDto;
import com.henyep.white.label.api.dto.response.BaseResponseDto;
import com.henyep.white.label.api.dto.response.BaseResponseHeader;
import com.henyep.white.label.api.dto.response.account.registration.AccountRegistrationsModel;
import com.henyep.white.label.api.dto.response.account.registration.GetAccountRegistrationResponseDto;
import com.henyep.white.label.api.dto.response.balance.transfer.BalanceTransferResponseDto;
import com.henyep.white.label.api.dto.response.balance.transfer.GetMaxWithdrawalResponseDto;
import com.henyep.white.label.api.dto.response.document.DownloadResponseDto;
import com.henyep.white.label.api.global.Constants;
import com.henyep.white.label.api.util.FileUtil;
import com.sun.jersey.core.util.Base64;



@Controller
@RequestMapping("/whiteLabel")
public class WhiteLabelController {
	final static Logger logger = Logger.getLogger(WhiteLabelController.class);
	
	@Resource(name = "middleOperatorBean")
	private MiddleOperator middlewareContext;
	
	@Resource(name = "whiteLabelMiddleOperatorBean")
	private MiddleOperatorWhiteLabel middlewareContextWhiteLabel;
	
	@Resource(name = "configFields")
	private ConfigFields configFields;
	
	
	//////////////////// deposit //////////////////////////	
	public void getMasterAccountMaxWithdrawal(HttpServletRequest request,HttpServletResponse response){
		MaxWithdrawalDto adminMax =new MaxWithdrawalDto();
		try{
			GetMasterAccountMaxWithdrawalRequestDto body = new GetMasterAccountMaxWithdrawalRequestDto();
			body.setCompany_code(getObjSession(request,ConstantFields.WL_COMPANY_CODE));
			body.setPlatform(getObjSession(request,ConstantFields.WL_PLATFORM));
			body.setRegistration_type(getObjSession(request,ConstantFields.WL_REGISTRATION_TYPE));
			body.setServer_code(getObjSession(request,ConstantFields.WL_SERVER_CODE));
			JsonUtil jsonUtil= new JsonUtil();
			String json=jsonUtil.toRequestEntityJson(HeaderUtil.getMasterAccMaxWithdrawalHeader(request),body);
			logger.info("requestJson"+json);
			logger.info("request url:"+ConstantFields.BALANCE_MAX_MASTER_WITHDRAWAL_PATH);
			String responseJson=middlewareContextWhiteLabel.sendMessage(json, ConstantFields.BALANCE_MAX_MASTER_WITHDRAWAL_PATH);
			logger.info("responseJson:"+responseJson);
			Type type = new TypeToken<BaseResponseDto<GetMaxWithdrawalResponseDto>>() {}.getType();
			BaseResponseDto<GetMaxWithdrawalResponseDto> dto=jsonUtil.fromJson(responseJson, type);
			BaseResponseHeader responseHeader = dto.getHeader();
			if(null!=responseHeader){
				 if((ConstantFields.SUCCESS).equals(responseHeader.getStatus())){
					 if(null!=responseHeader.getSecretKey()&&!"".equals(responseHeader.getSecretKey())){
							request.getSession().setAttribute(ConstantFields.IB_SECRET_KEY, responseHeader.getSecretKey());
						}
					 Double maxWithdrawal=dto.getBody().getMax_withdrawal();
					 String group=dto.getBody().getGroup();
					 
					 adminMax.setMax_withdrawal(maxWithdrawal);
					 adminMax.setAccount(dto.getBody().getAccount());
					 adminMax.setGroup(group);
					 request.setAttribute("maxWithdrawal", maxWithdrawal);
					 request.setAttribute("masterAccount", dto.getBody().getAccount());
					 request.setAttribute("group", group);
					 request.setAttribute("currency", dto.getBody().getCurrency());
				 }else{
					 Map<String, String> map = responseHeader.getErrorMap();
					 String errorInfo= DealErrorsUtil.getErrorInfo(map);
					 request.setAttribute("errorInfo", errorInfo);
					 logger.info(errorInfo);
				 }
			}
			logger.info("dto:"+dto);
			
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e, e);
		}
		
	}
	
	public String getObjSession(HttpServletRequest request,String sessionName){
		String serverCode=null;
		Object obj=request.getSession().getAttribute(sessionName);
		if(null!=obj&&!"".equals(obj)){
			serverCode=obj.toString();
		}
		return serverCode;
	}
	
	@RequestMapping(value="/deposit")
	public String deposit(HttpServletRequest request,HttpServletResponse response){
		
		getMasterAccountMaxWithdrawal( request, response);
		return "whiteLabel/deposit";
	}
	
	@RequestMapping(value="/doDeposit")
	public String doDeposit(HttpServletRequest request,HttpServletResponse response){
		try{
			double amount=0;
			int account=0;
			if(null!=request.getParameter("amount")&&!"".equals(request.getParameter("amount"))){
				amount=Double.parseDouble(request.getParameter("amount"));
			}else{
				request.setAttribute("errorInfo", "amount is empty!");
				 return "redirect:/whiteLabel/deposit.hyml";
			}
			if(null!=request.getParameter("account")&&!"".equals(request.getParameter("account"))){
				account=Integer.parseInt(request.getParameter("account"));
			}else{
				request.setAttribute("errorInfo", "account is empty!");
				return "redirect:/whiteLabel/deposit.hyml";
			}
			logger.info("doDeposit not to admin ");
			BalanceTransferRequestDto body = new BalanceTransferRequestDto();
			body.setCompany_code(getObjSession(request,ConstantFields.WL_COMPANY_CODE));
			body.setPlatform(getObjSession(request,ConstantFields.WL_PLATFORM));
			body.setRegistration_type(getObjSession(request,ConstantFields.WL_REGISTRATION_TYPE));
			body.setServer_code(getObjSession(request,ConstantFields.WL_SERVER_CODE));
			body.setLogin_id(account);
			body.setAmount(amount);
			JsonUtil jsonUtil= new JsonUtil();
			String json=jsonUtil.toRequestEntityJson(HeaderUtil.getDepositHeader(request),body);
			logger.info("requestJson"+json);
			logger.info("request url:"+ConstantFields.BALANCE_DEPOSIT_PATH);
			String responseJson=middlewareContextWhiteLabel.sendMessage(json, ConstantFields.BALANCE_DEPOSIT_PATH);
			logger.info("responseJson:"+responseJson);
			Type type = new TypeToken<BaseResponseDto<BalanceTransferResponseDto>>() {}.getType();
			BaseResponseDto<BalanceTransferResponseDto> dto=jsonUtil.fromJson(responseJson, type);
			BaseResponseHeader responseHeader = dto.getHeader();
			if(null!=responseHeader){
				 if((ConstantFields.SUCCESS).equals(responseHeader.getStatus())){
					 if(null!=responseHeader.getSecretKey()&&!"".equals(responseHeader.getSecretKey())){
							request.getSession().setAttribute(ConstantFields.IB_SECRET_KEY, responseHeader.getSecretKey());
						}
					 int toOrderId=dto.getBody().getToOrderId();
					 int fromOrderId=dto.getBody().getFromOrderId();
					 Double fromAmount=dto.getBody().getFromAmount();
					 request.getSession().setAttribute("toOrderId", toOrderId);
					 request.getSession().setAttribute("fromOrderId", fromOrderId);
					 request.getSession().setAttribute("fromAmount", fromAmount);
					 logger.info("success toOrderId:"+toOrderId+"fromOrderId: "+fromOrderId +"fromAmount:"+fromAmount);
					 request.getSession().setAttribute("errorDeposit", "successful.operation");
					 WhiteLabelUtil whiteLabelUtil=new WhiteLabelUtil();
					 whiteLabelUtil.getLeftSummary(request, response, middlewareContext);
				 }else{
					 Map<String, String> map = responseHeader.getErrorMap();
					 String errorInfo= DealErrorsUtil.getErrorInfo(map);
					 
					 request.getSession().setAttribute("errorDeposit", errorInfo);
					 logger.info(errorInfo);
				 }
			}
			logger.info("dto:"+dto);
		
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e, e);
		}
		return "redirect:/whiteLabel/deposit.hyml";
	}
	
	///////////////////// with drawal //////////////////////////
	
	@RequestMapping(value="/withdrawal")
	public String withdrawal(HttpServletRequest request,HttpServletResponse response){
		
		logger.info("withdrawal-----");
		return "whiteLabel/withdrawal";
	}
	
	@RequestMapping(value="/doWithdrawal")
	public String  doWithdrawal(HttpServletRequest request,HttpServletResponse response){

		try{
			double amount=0;
			int account=0;
			logger.info("doWithdrawal amount "+request.getParameter("amount")+" account"+request.getParameter("account"));
			if(null!=request.getParameter("amount")&&!"".equals(request.getParameter("amount"))){
				amount=Double.parseDouble(request.getParameter("amount"));
				if(null!=request.getParameter("account")&&!"".equals(request.getParameter("account"))){
					account=Integer.parseInt(request.getParameter("account"));
					BalanceTransferRequestDto body = new BalanceTransferRequestDto();
					body.setCompany_code(getObjSession(request,ConstantFields.WL_COMPANY_CODE));
					body.setPlatform(getObjSession(request,ConstantFields.WL_PLATFORM));
					body.setRegistration_type(getObjSession(request,ConstantFields.WL_REGISTRATION_TYPE));
					body.setServer_code(getObjSession(request,ConstantFields.WL_SERVER_CODE));
					body.setLogin_id(account);
					body.setAmount(amount);
					JsonUtil jsonUtil= new JsonUtil();
					String json=jsonUtil.toRequestEntityJson(HeaderUtil.getWithdrawalHeader(request),body);
					logger.info("requestJson"+json);
					logger.info("request url:"+ConstantFields.BALANCE_WITHDRAWAL_PATH);
					String responseJson=middlewareContextWhiteLabel.sendMessage(json, ConstantFields.BALANCE_WITHDRAWAL_PATH);
					logger.info("responseJson:"+responseJson);
					Type type = new TypeToken<BaseResponseDto<BalanceTransferResponseDto>>() {}.getType();
					BaseResponseDto<BalanceTransferResponseDto> dto=jsonUtil.fromJson(responseJson, type);
					BaseResponseHeader responseHeader = dto.getHeader();
					if(null!=responseHeader){
						 if((ConstantFields.SUCCESS).equals(responseHeader.getStatus())){
							 if(null!=responseHeader.getSecretKey()&&!"".equals(responseHeader.getSecretKey())){
									request.getSession().setAttribute(ConstantFields.IB_SECRET_KEY, responseHeader.getSecretKey());
								}
							 int toOrderId=dto.getBody().getToOrderId();
							 int fromOrderId=dto.getBody().getFromOrderId();
							 Double fromAmount=dto.getBody().getFromAmount();
							 request.getSession().setAttribute("toOrderId", toOrderId);
							 request.getSession().setAttribute("fromOrderId", fromOrderId);
							 request.getSession().setAttribute("fromAmount", fromAmount);
							 logger.info("success toOrderId:"+toOrderId+"fromOrderId: "+fromOrderId +"fromAmount:"+fromAmount);
							 request.getSession().setAttribute("errorWithdrawal", "successful.operation");
							 WhiteLabelUtil whiteLabelUtil=new WhiteLabelUtil();
							 whiteLabelUtil.getLeftSummary(request, response, middlewareContext);
						 }else{
							 Map<String, String> map = responseHeader.getErrorMap();
							 String errorInfo= DealErrorsUtil.getErrorInfo(map);
							 request.getSession().setAttribute("errorWithdrawal", errorInfo);
							 logger.info(errorInfo);
						 }
					}
				logger.info("dto:"+dto);
				}else{
					 return "redirect:/whiteLabel/withdrawal.hyml";
				}
			}else{
				 return "redirect:/whiteLabel/withdrawal.hyml";
			}
			
			
	
	}catch(Exception e){
		e.printStackTrace();
		logger.error(e, e);
		 request.getSession().setAttribute("errorWithdrawal", ConstantFields.Connection_ERROR);
	}
		
		return "redirect:/whiteLabel/withdrawal.hyml";
	}
	
	@RequestMapping(value="/getMaxWithdrawalAmount")
	public @ResponseBody  AdminMaxWithdrawal getMaxWithdrawalAmount(HttpServletRequest request,HttpServletResponse response){
		AdminMaxWithdrawal adminMax =new AdminMaxWithdrawal();
		try{
			GetMaxWithdrawalRequestDto body = new GetMaxWithdrawalRequestDto();
			String account=request.getParameter("account");
			if(null!=account&&!"".equals(account)){
				body.setLogin_id(Integer.parseInt(account));
				
				body.setCompany_code(getObjSession(request,ConstantFields.WL_COMPANY_CODE));
				body.setPlatform(getObjSession(request,ConstantFields.WL_PLATFORM));
				body.setRegistration_type(getObjSession(request,ConstantFields.WL_REGISTRATION_TYPE));
				body.setServer_code(getObjSession(request,ConstantFields.WL_SERVER_CODE));
				JsonUtil jsonUtil= new JsonUtil();
				String json=jsonUtil.toRequestEntityJson(HeaderUtil.getMaxWithdrawalHeader(request),body);
				String responseJson=middlewareContextWhiteLabel.sendMessage(json, ConstantFields.BALANCE_MAX_WITHDRAWAL_PATH);
				Type type = new TypeToken<BaseResponseDto<GetMaxWithdrawalResponseDto>>() {}.getType();
				BaseResponseDto<GetMaxWithdrawalResponseDto> dto=jsonUtil.fromJson(responseJson, type);
				BaseResponseHeader responseHeader = dto.getHeader();
				if(null!=responseHeader){
					 if((ConstantFields.SUCCESS).equals(responseHeader.getStatus())){
						 if(null!=responseHeader.getSecretKey()&&!"".equals(responseHeader.getSecretKey())){
								request.getSession().setAttribute(ConstantFields.IB_SECRET_KEY, responseHeader.getSecretKey());
							}
						 Double maxWithdrawal=dto.getBody().getMax_withdrawal();
						 String group=dto.getBody().getGroup();
						 
						 adminMax.setMax_withdrawal(maxWithdrawal);
						 adminMax.setAccount(dto.getBody().getAccount());
						 adminMax.setGroup(group);
						 adminMax.setCurrency(dto.getBody().getCurrency());
						 
					 }else{
						 Map<String, String> map = responseHeader.getErrorMap();
						 String errorInfo= DealErrorsUtil.getErrorInfo(map);
						 if(!"".equals(errorInfo)&&null!=errorInfo){
							 RequestContext requestContext = new RequestContext(request);
							 errorInfo= requestContext.getMessage(errorInfo.trim());
						 }
						 adminMax.setErrorInfo(errorInfo);
						 logger.info(errorInfo);
					 }
				}
			logger.info("dto:"+dto);
			}else{
				adminMax.setErrorInfo("account.is.null");
			}
			
		}catch(Exception e){
			adminMax.setErrorInfo(ConstantFields.Connection_ERROR);
			e.printStackTrace();
			logger.error(e, e);
		}
		return adminMax;
	}
	
	
	@RequestMapping(value="/getUploadedFiles")
	public String getUploadedFilePathByCreateTime(HttpServletRequest request,SerachFiles searchCondition){
		
		logger.info("================= Enter get uploaded files ===================");
		
		try{
			GetAccountRegistrationByDateRequestDto body = new GetAccountRegistrationByDateRequestDto();

			request.setAttribute("start_date", searchCondition.getStart_date());
			request.setAttribute("end_date", searchCondition.getEnd_date());
			
			Date startDate = TimeUtil.StringToDate(Constants.FORMAT_DATE, searchCondition.getStart_date());
			Date endDate = TimeUtil.StringToDate(Constants.FORMAT_DATE, searchCondition.getEnd_date());
			endDate = getNextDate(endDate);
		    
			
			body.setCompany_code(getObjSession(request,ConstantFields.WL_COMPANY_CODE));
			body.setStart_date(startDate);
			body.setEnd_date(endDate);
			body.setServer_code(getObjSession(request,ConstantFields.WL_SERVER_CODE));
			
			JsonUtil jsonUtil= new JsonUtil();
			String json=jsonUtil.toRequestEntityJson(HeaderUtil.getUploadFilePathByCreateTimeHeader(request),body);
			String responseJson=middlewareContextWhiteLabel.sendMessage(json, ConstantFields.GET_UPLOAD_FILE_PATH);
			Type type = new TypeToken<BaseResponseDto<GetAccountRegistrationResponseDto>>() {}.getType();
			BaseResponseDto<GetAccountRegistrationResponseDto> dto=jsonUtil.fromJson(responseJson, type);
			BaseResponseHeader responseHeader = dto.getHeader();
			if(null!=responseHeader){
				if((ConstantFields.SUCCESS).equals(responseHeader.getStatus())){
					if(null!=responseHeader.getSecretKey()&&!"".equals(responseHeader.getSecretKey())){
						request.getSession().setAttribute(ConstantFields.IB_SECRET_KEY, responseHeader.getSecretKey());
						}
					List<AccountRegistrationsModel> uploaded_file_paths=dto.getBody().getAccounts();
					request.setAttribute("listFiles", uploaded_file_paths);
					}else{
						Map<String, String> map = responseHeader.getErrorMap();
						String errorInfo= DealErrorsUtil.getErrorInfo(map);
						request.setAttribute("errorInfo", errorInfo);
						logger.info(errorInfo);
						}
				}
			logger.info("dto:"+dto);
			}catch(Exception e){
				request.setAttribute("errorInfo", ConstantFields.Connection_ERROR);
				e.printStackTrace();
				logger.error(e, e);
				}
		return "whiteLabel/downloadFiles";
	}
	
	@RequestMapping(value="/downloadFiles")
	public String uploadedFiles(){
		
		return "whiteLabel/downloadFiles";
	}
	@RequestMapping(value="/downLoadZipFile")
	public void downLoadZipFile(HttpServletRequest request,HttpServletResponse response){
//		ZipOutputStream out = null;
		logger.info("================= Enter download zip file ===================");
		try {
			String zipName ="userFiles.zip";
            response.setContentType("APPLICATION/OCTET-STREAM");  
            

            // get trading ids
            ArrayList<String> tradingIds = new ArrayList<String>();
            
            // individual download
            String tradingIdsString=request.getParameter("tradingIds");
            if(null != tradingIdsString && !"".equals(tradingIdsString)){
            	String [] tradingIdsArray = tradingIdsString.split(",");
            	for(String tradingId : tradingIdsArray){
            		tradingIds.add(tradingId);
            	}
            }
            
            // download all
			String tradingId = request.getParameter("tradingId");
			if(null != tradingId && !"".equals(tradingId)){
				tradingIds.add(tradingId);
			}
            
			// get zip files from server
			DownloadRequestDto body = new DownloadRequestDto();

			body.setCompany_code(getObjSession(request,ConstantFields.WL_COMPANY_CODE));
			body.setServer_code(getObjSession(request,ConstantFields.WL_SERVER_CODE));
			body.setTrading_id_list(tradingIds);
			
			JsonUtil jsonUtil= new JsonUtil();
			String json = jsonUtil.toRequestEntityJson(HeaderUtil.getDownloadFileHeader(request),body);
			logger.info("Send download request to white label server");
			String responseJson = middlewareContextWhiteLabel.sendMessage(json, ConstantFields.GET_DOWNLOAD_FILE_PATH);
			Type type = new TypeToken<BaseResponseDto<DownloadResponseDto>>() {}.getType();
			BaseResponseDto<DownloadResponseDto> dto=jsonUtil.fromJson(responseJson, type);
			BaseResponseHeader responseHeader = dto.getHeader();
			if(null != responseHeader){
				if((ConstantFields.SUCCESS).equals(responseHeader.getStatus())){
					logger.info("Response success");
					if(null != responseHeader.getSecretKey() && !"".equals(responseHeader.getSecretKey())){
						request.getSession().setAttribute(ConstantFields.IB_SECRET_KEY, responseHeader.getSecretKey());
					}
					
					String fileInString = dto.getBody().getFileInString();
					String fileName = dto.getBody().getFileName();
					logger.info("File Name: " + fileName);
					response.setHeader("Content-Disposition","attachment; filename=" + fileName);
					
					FileUtil util = new FileUtil();
					String tempFolderName = configFields.getDownloadTempFolder();
					boolean rtn = util.saveFileInLocal(fileInString, tempFolderName, fileName);
					logger.info("Save file in local rtn: " + rtn);
					String filePath = tempFolderName + File.separator + fileName;
					FileInputStream fileIS = new FileInputStream(filePath);
					
					logger.info("Save to: " + filePath);
					int data = 0;
					while((data = fileIS.read()) != -1){
						response.getOutputStream().write(data);
                    }  
					response.getOutputStream().flush();
                    fileIS.close();
                    logger.info("Read output to response output stream");
				}
				logger.info("dto:" + dto);
			}

        } 
		catch (Exception e) {
           e.printStackTrace();
           logger.error(e, e);
        }
	}
	
	
	@RequestMapping(value="/downloadClientInfos")
	public void downloadClientInfos(HttpServletRequest request,HttpServletResponse response){
		try {
			String startDateString = request.getParameter("start_date");
			String endDateString = request.getParameter("end_date");
		  
			GetRegisteredAccountRequestDto body = new GetRegisteredAccountRequestDto();
			
			Date startDate = TimeUtil.StringToDate(Constants.FORMAT_DATE, startDateString);
			Date endDate = TimeUtil.StringToDate(Constants.FORMAT_DATE, endDateString);
			endDate = getNextDate(endDate);
		    
		    
			body.setCompany_code(getObjSession(request,ConstantFields.WL_COMPANY_CODE));
			body.setStart_date(startDate);
			body.setEnd_date(endDate);
			
		    
			
			JsonUtil jsonUtil= new JsonUtil();
			String json=jsonUtil.toRequestEntityJson(HeaderUtil.getUploadFilePathByCreateTimeHeader(request),body);
			
			String urlStr = middlewareContextWhiteLabel.getConfigFields().getWhite_label_request_url() + ConstantFields.GET_CLIENT_INFOS;	

	        URL url = new URL(urlStr);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        
	        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	        connection.setReadTimeout(10000);
	        connection.setConnectTimeout(15000);
	        connection.setRequestMethod("POST");
	        connection.setDoInput(true);
	        connection.setDoOutput(true);
	        
	        OutputStream os = connection.getOutputStream();
	        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
	        writer.write(json);
	        writer.flush();
	        writer.close();
	        connection.connect();

	        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
	        Date today = Calendar.getInstance().getTime();
	        String fileName = "clientInfos_" + df.format(today) + ".xls";
    	    response.setHeader("Content-disposition", "attachment; filename=" + fileName);
    	    IOUtils.copy(connection.getInputStream(), response.getOutputStream());
    	    
           
        } catch (Exception e) {
        	logger.error(e, e);
            e.printStackTrace();
        }       
		
	}
	
	private Date getNextDate(Date date){
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.DATE, 1);
	    return cal.getTime();
	}
	
	
	
	
	
}
