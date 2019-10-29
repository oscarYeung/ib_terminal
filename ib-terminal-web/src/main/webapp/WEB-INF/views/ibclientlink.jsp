<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="spring" uri="/spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>


<%@ page isELIgnored="false" %>
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
          <li><i class="fa fa-home"></i> <a href="/index.hyml"><spring:message code="ib.home"></spring:message></a><i class="fa fa-angle-right"></i><spring:message code="ib.link"></spring:message><i class="fa fa-angle-right"></i><spring:message code="ib.client.reg.link"></spring:message></li>
        </ul>
        <div class="container-fluid">
          <h2 class="title"><spring:message code="ib.client.reg.link"></spring:message></h2>
          <div class="client-reg">
              <ul>
              	<c:forEach items="${links}" var="link" varStatus="status">
              	<c:choose> 
              	<c:when test="${status.count==2}">           
              	 <li class="marginRNone">
              	</c:when>
              	<c:otherwise>
              	<li>
              	</c:otherwise>
		   		 </c:choose>
                  <P>${link.client_package_code } (${link.description } <spring:message code="ib.commission"></spring:message>)</P>
                 <div class="link"> <a href="${link.url }">${link.url }</a></div>
                  <a href="mailto:?body=${link.url }" class="btn btn-red"><spring:message code="ib.send.via.email"></spring:message></a>
                </li>
		   		</c:forEach>
               
              </ul>
               
          </div>
          
        </div>
      </div>
    </div>
  </div>
</div>
<jsp:include page="${langType}/include/foot.html"></jsp:include>
<script src="/js/adaptability.js"></script>
<script src="/js/bootstrap.min.js"></script>
<%@ include file="/WEB-INF/views/premission.jsp" %>
</body>
</html>