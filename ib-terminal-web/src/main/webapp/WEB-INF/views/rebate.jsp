<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
<c:if test="${langType=='/chinese'}">
<link rel="stylesheet" href="/css/table_cn.css">
</c:if>
<script src="/js/jquery-1.9.1.min.js"></script> 
</head>
<body>
<header>
 <jsp:include page="${langType}/include/head.html"></jsp:include>
</header>

<!-- left fixed -->
<div id="wrap">
   <jsp:include page="/WEB-INF/include/left.jsp"></jsp:include>
  <!-- right center -->
  <div class="page-center" id="boxb">
    <div id="content">
      <div id="content_center">
        <ul class="breadcrumb">
          <li><i class="fa fa-home"></i> <a href="/index.hyml"><spring:message code="ib.home"></spring:message></a><i class="fa fa-angle-right"></i><spring:message code="ib.account"></spring:message><i class="fa fa-angle-right"></i><spring:message code="ib.my.package"></spring:message> </li>
        </ul>
        <div class="container-fluid">
          <h2 class="title"><spring:message code="ib.my.package"></spring:message></h2>
          <div class="package">
              <table class="table-bordered">
              	<c:forEach items="${rebates}" var="reb" >
              	 <tr class="tr-titileBg">
                      <td colspan="3"><i class="fa fa-chevron-right"></i><spring:message code="ib.package"></spring:message> :${reb.client_package_code}</td>
                  </tr>
                   <tr class="font-web">
                      <td><spring:message code="ib.product"></spring:message></td>
                      <td><spring:message code="ib.client"></spring:message> <spring:message code="ib.commission"></spring:message></td>
                      <td><spring:message code="ib.rebate.per"></spring:message></td>
                  </tr>
                  <c:forEach items="${reb.rebateDetails }" var="detail">
                   <tr>
                      <td>${detail.product_group }</td>
                      <td><span>${detail.client_fix_commission }</span><spring:message code="ib.usd"></spring:message></td>
                      <td>
                      	<c:choose>
						  <c:when test="${detail.rebate_type == '$'}">
	                      	<span>${detail.rebate_commission} </span>
	                      	<spring:message code="ib.usd"></spring:message>
						  </c:when>
						  <c:when test="${detail.rebate_type == 'S'}">
						  	<span>${detail.rebate_commission} </span>
	                      	% <spring:message code="ib.spread"></spring:message>
						  </c:when>
						  <c:otherwise>
						  	<span>${detail.rebate_commission}</span>
						  </c:otherwise>
						</c:choose>
					  </td>
                  </tr>
                  </c:forEach>
              	</c:forEach>
              </table>
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