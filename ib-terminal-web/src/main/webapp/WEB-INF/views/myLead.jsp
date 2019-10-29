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
<link rel="stylesheet" href="/css/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" href="/css/common.css?v=<%= new java.util.Date().getTime() %>">
<link rel="stylesheet" href="/css/layout.css?v=<%= new java.util.Date().getTime() %>">
<c:if test="${langType=='/chinese'}">
<link rel="stylesheet" href="/css/table_cn.css">
</c:if>
<script src="/js/jquery-1.9.1.min.js"></script>
<!--[if IE 9]>
<style type="text/css">
@media only screen and (max-width: 760px){
  .details-table{ width:100%; overflow-x: auto;}
  .details-table td:before{ float:none; padding-left:3px; }
  }
</style>
<![endif]-->
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
          <li><i class="fa fa-home"></i> <a href="/index.hyml"><spring:message code="ib.home"></spring:message></a><i class="fa fa-angle-right"></i>
          <spring:message code="ib.account"></spring:message><i class="fa fa-angle-right"></i><spring:message code="ib.my.lead"></spring:message> </li>
        </ul>
        <div class="container-fluid">
          <h2 class="title"><spring:message code="ib.my.lead"></spring:message></h2>
          <div class="my-lead">
            <div class="details-table lead-table">
              <table class="table-hover">
                <thead>
                  <tr>
                    <th><spring:message code="ib.ib.code"></spring:message></th>
                    <th><spring:message code="ib.client.name"></spring:message></th>
                    <th><spring:message code="ib.phone.no"></spring:message></th>
                    <th><spring:message code="ib.email"></spring:message></th>
                    <th><spring:message code="ib.reg.date"></spring:message></th>
                    <th><spring:message code="ib.demo.live"></spring:message></th>
                    <th class="borRnone"><spring:message code="ib.status"></spring:message></th>
                  </tr>
                </thead>
                	<c:forEach items="${leads}" var="leads" varStatus="status">
                <tr>
                  <td>${leads.ib_code}</td>
                  <td>${leads.client_first_name} ${leads.client_last_name}</td>
                  <td>${leads.phone}</td>
                  <td>${leads.email}</td>
                  <td><fmt:formatDate value="${leads.registration_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                  <td>
                  <c:forEach items="${demoLive }" var="dl">
                  	<c:if test="${leads.register_type eq dl.key }">
                  		${dl.value }
                  	</c:if>
                  </c:forEach>
                  </td>
                  <td>
                  ${leads.status}</td>
                </tr>
                </c:forEach>
                

              </table>
            </div>
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