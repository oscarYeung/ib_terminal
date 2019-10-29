<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"  %> 
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
          <li><i class="fa fa-home"></i> <a href="/index.hyml"><spring:message code="ib.home"></spring:message></a><i class="fa fa-angle-right"></i><spring:message code="ib.support"></spring:message><i class="fa fa-angle-right"></i><spring:message code="ib.contact.us"></spring:message></li>
        </ul>
        <div class="container-fluid">
          <h2 class="title"><spring:message code="ib.contact.us"></spring:message></h2>
          <div class="contact">
              <p><img src="images/icon_email.png"></p>
              <ul>
                  <h5><spring:message code="ib.contact.my.assistant"></spring:message></h5>
                  <li><span><spring:message code="ib.name"></spring:message>:</span><spring:message code="ib.hycm.ib.support"></spring:message></li>
                  <li><span><spring:message code="ib.email"></spring:message>:</span><a href="mailto:ib.support@hycm.com"><spring:message code="ib.info.email"></spring:message></a></li>
              </ul>
                
              </dl>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
 <jsp:include page="${langType}/include/foot.html"></jsp:include>
<script src="/js/adaptability.js"></script>
<script src="/js/bootstrap.min.js"></script>
</body>
</html>
