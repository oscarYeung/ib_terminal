<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>  
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

//http://127.0.0.1:8080/resetPassword.hyml?code=TFJLYXNEbVpjcGgyNXpVVDh5NzJUeFdCdkFIelFNOFhDVnl3ekhTaHp2bz0=
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><spring:message code="ib.terminal.hycm"></spring:message></title>
<link rel="shortcut icon" href="/images/favicon.png" type="image/png" />
<link rel="stylesheet" href="/css/bootstrap.min.css" >
<link rel="stylesheet" href="/css/font-awesome.min.css" >
<link rel="stylesheet" href="/css/common.css?v=<%= new java.util.Date().getTime() %>">
<link rel="stylesheet" href="/css/layout.css?v=<%= new java.util.Date().getTime() %>">
<script src="/js/jquery-1.9.1.min.js"></script> 
</head>
<body class="foget-password IbReg">
<header>
  
   <div class="menu">
    <div class="logo"> <a href="/index.hyml"><img src="images/logo.png" ></a> </div>
  </div>
</header>

<!-- left fixed -->
<div id="wrap">

  <!-- left fixed End--> 
  
  <!-- right center -->
  <div class="page-center" id="boxb">
    <div id="content">
      <div id="content_center">
       <!--  <ul class="breadcrumb">
          <li><i class="fa fa-home"></i> <a href="/index.hyml">Home</a><i class="fa fa-angle-right"></i><a href="/login.hyml">Login</a><i class="fa fa-angle-right"></i>Reset Password</li>
        </ul>--> 
        <div class="container-fluid">
          <h2 class="title"><spring:message code="ib.forgot.password"></spring:message></h2>
          <p class="line">
            <b><spring:message code="ib.provide.email"></spring:message></b>
          </p>
          <div class="profile fogetPassword">
              
              <form:form  role="form"  action="doForgotPassword.hyml"  name="forgotForm" modelAttribute="passwordModel" method="POST">
                  <div class="form-group">
                      <label><span>*</span><spring:message code="ib.emal.address"></spring:message> :</label>
                      <form:input path="email" class="form-control" type="text"/>
                     
                      <label><span>*</span><spring:message code="ib.ib.code"></spring:message>:</label>
                      <form:input path="user_code" class="form-control" type="text"/>
                      <a href="javascript:void(0)" class="btn btn-red" onclick="forgeotPassword();return false;" ><spring:message code="ib.submit"></spring:message></a>
                  </div>
                  <p class="errorPrompt">
                  <form:errors path="*"></form:errors>
                  <%   if(null!=request.getAttribute("errormessage")&&!"".equals(request.getAttribute("errormessage"))){ %>
             		  	<spring:message code="${errormessage}"></spring:message>
             		  <%} %>
                  </p>
              </form:form>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<script type="text/javascript">
   function forgeotPassword(){
	   var email=$("#email").val().trim();
		var user_code=$("#user_code").val().trim();
		if(email.length==0){
			alert("<spring:message code='input.password'></spring:message>");
			return false;
		}
		if(user_code.length==0){
			alert("<spring:message code='input.ib.code'></spring:message>");
			return false;
		}
		
		 var reg = /\w+[@]{1}\w+[.]\w+/;
		 if(!reg.test(email)){
			alert("<spring:message code='input.corrrect.email'></spring:message>");
		 }
	   document.forms["forgotForm"].submit();
   }

</script>
<jsp:include page="/include/foot.html"></jsp:include>
<script src="/js/bootstrap.min.js"></script>
</body>
</html>