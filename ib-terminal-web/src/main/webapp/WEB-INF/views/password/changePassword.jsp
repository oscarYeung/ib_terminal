<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
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
<body>
<header>
   <jsp:include page="${langType}/include/head.html"></jsp:include>
</header>

<!-- left fixed -->
<div id="wrap">
   <jsp:include page="/WEB-INF/include/left.jsp"></jsp:include>

  <!-- left fixed End--> 
  
  <!-- right center -->
  <div class="page-center" id="boxb">
    <div id="content">
      <div id="content_center">
        <ul class="breadcrumb">
          <li><i class="fa fa-home"></i> <a href="/index.hyml"><spring:message code="ib.home"></spring:message></a><i class="fa fa-angle-right"></i><spring:message code="ib.settings"></spring:message> <i class="fa fa-angle-right"></i><spring:message code="ib.change.password"></spring:message></li>
        </ul>
        <div class="container-fluid">
          <h2 class="title"><spring:message code="ib.change.password"></spring:message></h2>
          <p class="line">
            <!--<b>On this page you can change the password to your HYCM Private Cabinet.</b> --> 
          </p>
          <div class="profile ChangePassword">
               <form:form  role="form" action="doChangePassword.hyml" method="post" name="changeForm">
                  <div class="form-group">
                      <label><span>*</span><spring:message code="ib.current.password"></spring:message>:</label>
                      <input class="form-control" type="password" name="oldPassword" id="oldPassword">
                      <label><span>*</span><spring:message code="ib.new.password"></spring:message>:</label>
                      <input class="form-control"  type="password" name="newPassword" id="newPassword">
                      <label><span>*</span><spring:message code="ib.confirm.new.password"></spring:message>:</label>
                      <input class="form-control"  type="password" name="renewPassword"  id="renewPassword">
                      <p class="errorPrompt">
		                  <form:errors path="*"></form:errors>
		                <%   if(null!=request.getAttribute("errormessage")&&!"".equals(request.getAttribute("errormessage"))){ %>
             		  	<spring:message code="${errormessage}"></spring:message>
             		  <%} %>
		                  
	                  </p>
                      <a href="javascript:void(0)" class="btn btn-red" onclick="setPassword();return false;"><spring:message code="ib.save.changes"></spring:message></a>
                  </div>
                  
              </form:form>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<script type="text/javascript">
function setPassword(){
	var password=$.trim($("#newPassword").val());
	var repassword=$.trim($("#renewPassword").val());
	var oldPassword=$.trim($("#oldPassword").val());
	
	if(oldPassword.length==0){
		alert("<spring:message code='input.password'></spring:message> ");
		return false;
	}
	if(password.length==0){
		alert("<spring:message code='input.new.password'></spring:message> ");
		return false;
	}
	if(repassword.length==0){
		alert("<spring:message code='input.confirm.new.password'></spring:message> ");
		return false;
	} 
	if(password!=repassword){
		alert("<spring:message code='input.correct.repassword'></spring:message> ");
		return false;
	}
	 document.forms["changeForm"].submit();
}
</script>
<jsp:include page="${langType}/include/foot.html"></jsp:include>
<script src="/js/adaptability.js"></script>
<script src="/js/bootstrap.min.js"></script>
</body>
</html>