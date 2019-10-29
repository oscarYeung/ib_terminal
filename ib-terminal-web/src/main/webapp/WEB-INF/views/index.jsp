<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>  
<%@taglib prefix="spring" uri="/spring" %>
<%@ page isELIgnored="false" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
System.out.println("--langType"+request.getSession().getAttribute("langType"));
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><spring:message code="ib.terminal.hycm"></spring:message></title>
<link rel="shortcut icon" href="/images/favicon.png" type="image/png" />
<script src="js/jquery-1.9.1.min.js"></script> 
<link rel="stylesheet" href="/css/bootstrap.min.css" >
<link rel="stylesheet" href="/css/font-awesome.min.css" >
<link rel="stylesheet" href="/css/common.css?v=<%= new java.util.Date().getTime() %>">
<link rel="stylesheet" href="/css/layout.css?v=<%= new java.util.Date().getTime() %>">
</head>
<body>
<header>
  <jsp:include page="${langType}/include/head.html"></jsp:include>
</header>

<!-- left fixed -->
<div id="wrap">
    <jsp:include page="/WEB-INF/include/left.jsp"></jsp:include>
  <!-- right center -->
  <div>${serverCode}</div>
  <div class="page-center" id="boxb">
    <div id="content">
      <div id="content_center">
        <ul class="breadcrumb">
          <li><i class="fa fa-home"></i> <a href="/index.hyml"><spring:message code="ib.home"></spring:message></a> </li>
        </ul>
        <div class="container-fluid">
          <h2 class="title"><spring:message code="ib.welcome.ib"></spring:message></h2>
          <div class="home-center">
            <ul class="icon-row">
              <li><a href="/profile.hyml"> <i class="fa fa-user-plus" ></i>
                <p><spring:message code="ib.account.profile"></spring:message></p>
                </a></li>
              <li><a href="/ib/rebate.hyml"> <i class="fa fa-suitcase" ></i>
                <p><spring:message code="ib.my.package"></spring:message></p>
                </a></li>
              <li><a href="/ibCommission/getTree.hyml"> <i class="fa fa-circle-thin fa-stack-1x" ></i><i class="fa fa-bookmark-o fa-stack-1x"></i>
                <p class="txtTeam"><spring:message code="ib.commission.enquiry"></spring:message></p>
                </a></li>
              <li><a href="/ib/getClientLinks.hyml"> <i class="fa fa-link" ></i>
                <p><spring:message code="ib.client.reg.link"></spring:message></p>
                </a></li>
              <!-- <li><a href="/sub_ib_reg.html"> <i class="fa fa-edit" ></i>
                <p>Sub IB Registration Link</p>
                </a></li> -->
              <li><a href="/margin/getMaxWithdrawal.hyml"> <i class="fa fa-power-off" ></i>
                <p><spring:message code="ib.withdrawal"></spring:message></p>
                </a></li>
              <li><a href="/mt4.hyml"> <i class="fa  fa-desktop" ></i>
                <p><spring:message code="ib.mt4"></spring:message></p>
                </a></li>
              <li><a href="/changePassword.hyml"> <i class="fa fa-key" ></i>
                <p><spring:message code="ib.change.password"></spring:message></p>
                </a></li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<jsp:include page="${langType}/include/foot.html"></jsp:include>
<script src="js/adaptability.js"></script> 
<script src="js/bootstrap.min.js"></script>
<%@ include file="/WEB-INF/views/premission.jsp" %>
</body>
</html>