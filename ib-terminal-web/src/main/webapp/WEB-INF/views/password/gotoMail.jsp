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
  

  <!-- left fixed End--> 
  
  <!-- right center -->
  <div class="page-center" id="boxb">
    <div id="content">
      <div id="content_center">
       <!-- <ul class="breadcrumb">
          <li><i class="fa fa-home"></i> <a href="index.html">Home</a><i class="fa fa-angle-right"></i><a href="/login.hyml">Login</a><i class="fa fa-angle-right"></i>Reset Password</li>
        </ul>  -->
        <div class="container-fluid">
          <h2 class="title"><spring:message code="ib.forgot.password"></spring:message></h2>
          <p class="line">
          </p>
          <div class="profile fogetPassword widMax">
              <p>
              <spring:message code="ib.send.email"></spring:message>
              <a href="/index.hyml"><spring:message code="ib.return.home.page"></spring:message></a></p>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<jsp:include page="/include/foot.html"></jsp:include>
<script src="/js/bootstrap.min.js"></script>
</body>
</html>
