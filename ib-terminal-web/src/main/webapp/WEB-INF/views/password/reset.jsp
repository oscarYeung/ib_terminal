<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
  <!--include virtual="/WEB-INF/include/left.jsp"-->

  <!-- left fixed End--> 
  
  <!-- right center -->
  <div class="page-center" id="boxb">
    <div id="content">
      <div id="content_center">
       <!--   <ul class="breadcrumb">
          <li><i class="fa fa-home"></i> <a href="/index.hyml">Home</a><i class="fa fa-angle-right"></i><a href="/login.hyml">Login</a><i class="fa fa-angle-right"></i>Reset Password</li>
        </ul>  -->
        <div class="container-fluid">
          <h2 class="title"><spring:message code="ib.forgot.password"></spring:message></h2>
          <p class="line">
            <b><spring:message code="ib.to.reset.your.password"></spring:message></b>
          </p>
          <div class="profile fogetPassword">
              <form role="form" action="doSetPassword.hyml" name="forgotForm" method="POST">
                  <div class="form-group">
                      <label><span>*</span><spring:message code="ib.new.password"></spring:message>:</label>
                      <input class="form-control" type="password" name="password" maxlength="45" id="password">
                      <label><span>*</span><spring:message code="ib.reenter.password"></spring:message>:</label>
                      <input class="form-control"  type="password" name="repassword" maxlength="45" id="repassword" >
                      <a href="javascript:void(0)" class="btn btn-red" onclick="setPassword();return false;"><spring:message code="ib.submit"></spring:message></a>
                  </div>
                   <p class="errorPrompt">
                   <%   if(null!=request.getAttribute("errormessage")&&!"".equals(request.getAttribute("errormessage"))){ %>
             		  	<spring:message code="${errormessage}"></spring:message>
             		  <%} %>
                    </p>
              </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<script type="text/javascript">
function setPassword(){
	var password=$("#password").val().trim();
	var repassword=$("#repassword").val().trim();
	if(password.length==0){
		alert("<spring:message code='input.password'></spring:message> ");
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
	 document.forms["forgotForm"].submit();
}
</script>
<jsp:include page="/include/foot.html"></jsp:include>
<script src="/js/bootstrap.min.js"></script>
</body>
</html>
