<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="spring" uri="/spring" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page isELIgnored="false" %>
<%
String flag=(String)session.getAttribute("flag");
 %>
 <!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Login-IBTerminal</title>
	<link rel="shortcut icon" href="/images/favicon.png" type="image/png" />
	<link rel="stylesheet" href="/css/bootstrap.min.css" type="text/css">
	<link rel="stylesheet" href="/css/font-awesome.min.css" type="text/css"> 
	<link rel="stylesheet" href="/css/layout.css" type="text/css">
	<style type="text/css">
		::-moz-placeholder  { color: #fff!important; }
		::-webkit-input-placeholder { color:#fff!important; }
		:-ms-input-placeholder { color:#fff!important; }
	</style>
	<script src="/js/jquery-1.9.1.min.js"></script> 
</head>
<body class="login">

<div class="login-bg">
		<div class="loginlang">
			<a class="icon-en" href="http://ibweb.henyep.com/index.hyml?lang=en_US" id="en_language">English</a>|
			<a class="icon-cn" href="http://ibweb.henyep.com/index.hyml?lang=zh_CN" id="cn_language">中文</a>
	 </div>
		<div class="login-position">
		<div class="container">
			<div class="logo">
			<img src="/images/logo.png">
			</div>
			<form:form  role="form" class="form-horizontal" action="/dologin.hyml" onsubmit="login();return false;" name="userLogin" modelAttribute="loginDto">
				<div class="login-input-bg"></div>
				<div class="login-input"> 
						<div class="control-group">
							<i class="fa fa-user icon-user"></i>
						    <input type="text" class=" form-control userinput" id="ibLoginName" name="ibLoginName" placeholder="<spring:message code='ib.ib.code'></spring:message>" >
						</div>
						<div class="control-group">
							<i class="fa fa-lock icon-lock"></i>
							<input type="password" class=" form-control password-input" id="ibLoginPassWord"  name="ibLoginPassWord" placeholder="<spring:message code='ib.password'></spring:message>">
						</div>
						<!--  <div class="checkbox"><label><input type="checkbox">Remember me</label></div>-->
						<p class="text-right"><a href="forget.hyml" ><spring:message code="ib.forgot.password"></spring:message>?</a></p>
				</div>
				
				 <p id="returnError">
             		<font color="red">
             		<c:if test="${!empty errormessage}">
             		<spring:message code="${errormessage}"></spring:message>
             		</c:if>
             		<form:errors path="*" cssClass="error"  /> 
             		</font>
             	</p>
				<button type="submit" class="btn btn-warning btn-block" ><spring:message code="ib.sign"></spring:message></button>
			</form:form>
			<script type="text/JavaScript">
function showKeyDown(evt) {
              evt = (evt) ? evt : window.event
              if(evt.keyCode==13){
              	 login();
              }
}	
document.onkeydown = showKeyDown  

   function login(){	
     if(isNotEmpty(document.getElementsByName("ibLoginName")[0])){
   	   	alert("<spring:message code='input.ib.code' ></spring:message>");
	    document.getElementsByName("ibLoginName")[0].value="";
		document.getElementsByName("ibLoginName")[0].focus();
	  	return false;
	}
	if(isNotEmpty(document.getElementsByName("ibLoginPassWord")[0])){
   	   alert("<spring:message code='input.password' ></spring:message>");
	    document.getElementsByName("ibLoginPassWord")[0].value="";
		document.getElementsByName("ibLoginPassWord")[0].focus();
	  	return false;
	} 
	document.forms["userLogin"].submit();
}
function resetLogin(){
		  document.MT4LoginForm("ibLoginName").value="";
		  document.MT4LoginForm("ibLoginPassWord").value="";
	
   }
   function trim(sStr){
		return sStr.replace(/(^\s*)|(\s*$)/g,"");
}

	function isNotEmpty(elem) {		
        var str=trim(elem.value);
        if(str==null || str=="")
        { return true;
		} 
          return false;
	}
	function succ(){
			 window.open ('/${lanname}/Support_forms&documents.htm', 'newwindow', 'top=0, left=0, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no')
	}


</script>
 <script>
 function getCookie(cname)
 {
	 var name = cname + "=";
	 var ca = document.cookie.split(';');
	 for(var i=0; i<ca.length; i++) 
	   {
	   var c = ca[i].trim();
	   if (c.indexOf(name)==0) return c.substring(name.length,c.length);
	   }
	 return "";
 }
 
   var hrefurl=window.location.href;
  
  if(hrefurl.indexOf("?lang=en_US")>-1){
	  hrefurl= hrefurl.replace("?lang=en_US","");
	  hrefurl= hrefurl.replace("?lang=en_US","");
  }
  if(hrefurl.indexOf("?lang=zh_CN")>-1){
	  hrefurl= hrefurl.replace("?lang=zh_CN","");
	  hrefurl= hrefurl.replace("?lang=zh_CN","");
  }
   $("#en_language").attr("href",hrefurl+"?lang=en_US");
   $("#cn_language").attr("href",hrefurl+"?lang=zh_CN");
   
  
 </script>
		</div>
		</div>


<jsp:include page="/include/foot.html"></jsp:include>
</div>
  <%
request.getSession().removeAttribute("flag");
%>
<script src="/js/bootstrap.min.js"></script>	
</body>
</html>

