<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.List"%>

<%
 /*IbLoginResponseDto  ibLoginResponseDto = (IbLoginResponseDto)session.getAttribute("ibLoginResponseDto");


 String loginname = ibLoginResponseDto.getIbLoginName();
  String ibStatus = ibLoginResponseDto.getIbStatus();
   String ibCreateDate = ibLoginResponseDto.getIbCreateDate();
    String ibCode = ibLoginResponseDto.getIbCode();
     String numberOfAccount = ibLoginResponseDto.getNumberOfAccount();
     String commissions = ibLoginResponseDto.getCurrentMothData().getCommissions();
      String rebates = ibLoginResponseDto.getCurrentMothData().getRebates();
       String spreadWidening = ibLoginResponseDto.getCurrentMothData().getSpreadWidening();
      List accountInformations = ibLoginResponseDto.getAccountInformations();
      */
      String loginname = "";
      String ibStatus = "";
       String ibCreateDate = "";
        String ibCode = "";
         String numberOfAccount = "";
         String commissions = "";
          String rebates = "";
           String spreadWidening = "";
          List accountInformations = null;
      

%>
        
        <div class="login out" id="divLogin">
 <b class="red">欢迎您 <span id="spanUserName"><%=loginname %></span></b> 
<ul class="graylist">
	 <li>用户状态：<%=ibStatus %></li> 
	 <li>用户创建时间：<%=ibCreateDate %></li>
	 <li>用户ID<%=ibCode %></li> 
	 <li>账号数量<%=numberOfAccount %></li> 
	 </ul>
 </div>

        <div class="neiyeleft">
			<ul>
				<li>佣金：<%=commissions %></li>
				<li>返佣：<%=rebates %></li>
				<li>点差：<%=spreadWidening %></li>
				<li>子账户信息：<%=accountInformations %></li>
			</ul>
		</div>
        


		
		
