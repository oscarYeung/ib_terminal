<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
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
          <spring:message code="ib.client"></spring:message><i class="fa fa-angle-right"></i><spring:message code="ib.client.profile"></spring:message></li>
        </ul>
        <div class="container-fluid">
          <h2 class="title"><spring:message code="ib.client.profile"></spring:message></h2>
          <p class="line"></p>
          <div class="profile">
              <form:form role="form" action="getIbClientProfile.hyml" method="post" id="clientProfileForm">
                 <div class="client-profile select">
                 	<label><spring:message code="ib.client.code"></spring:message>:</label>
                      <ul class="clearfix">
                          <li class="return_reques">
                              <input type="text" value="" class="local select_style provin_select" readonly="" id="clientCode">
                              <div class="clearfix pa">
                                  <div class="bomb_con_style provin_con none">
                                     <dl>
                                        <c:forEach items="${listClients}" var="clients">
                                         <dd >${ clients.client_code}</dd>
                                        </c:forEach>
                                     </dl>
                                  </div>
                              </div>
                          </li>
                      </ul>
                      
                      <a href="javascript:void(0)" class="btn btn-red" onclick="getIbClientProfile();return false"><spring:message code="ib.search"></spring:message></a>
                      <p class="errorPrompt"><form:errors path="*"></form:errors>  
                      
                       <c:if test="${!empty errormessage}">
             			<spring:message code="${errormessage }"></spring:message>
             		</c:if>
                       </p>
                 </div>
                  <div class="form-group">
                 	 <label><spring:message code="ib.client.code"></spring:message>:</label>
                      <input class="form-control"  type="text"  readonly="true" id="ibCode">
                      <label><spring:message code="ib.name"></spring:message>:</label>
                      <input class="form-control"  type="text"  readonly="true" id="firstName" />
                      <label><spring:message code="ib.user.name"></spring:message>:</label>
                      <input class="form-control"  type="text"  readonly="true" id="lastName" />
                      <label><spring:message code="ib.sex"></spring:message>:</label>
                      <input class="form-control"  type="text"  readonly="true" id="sex">
                      <label><spring:message code="ib.mobile.number"></spring:message>:</label>
                      <input class="form-control"  type="text"   readonly="true" id="mobile">
                      <label><spring:message code="ib.email"></spring:message>:</label>
                      <input class="form-control"  type="text"   readonly="true" id="email">
                      <label><spring:message code="ib.chinese.name"></spring:message>:</label>
                      <input class="form-control"  type="text"   readonly="true" id="chineseName">
                      <label><spring:message code="ib.platform"></spring:message>:</label>
                      <input class="form-control"  type="text"   readonly="true" id="PlatForm">
                      <label><spring:message code="ib.account.balance"></spring:message>:</label>
                      <input class="form-control"  type="text"   readonly="true" id="accountBalance">
                      
                  </div>
                  
              </form:form>
              <div class="errorPrompt" style="display:none">
				<span id="m_clientcode_exsit"><spring:message code="ib.client.code.not.exsit"></spring:message></span>
				<span id="m_clientcode"><spring:message code="input.client.code"></spring:message></span>
			</div>
          </div>
        </div>
        
      </div>
    </div>
  </div>
</div>

 <jsp:include page="${langType}/include/foot.html"></jsp:include>
  <script src="/js/commom/dealResult.js"></script>  
 <script type="text/javascript" src="/js/commission/ibClientProfile.js"></script>
<script src="/js/adaptability.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/jquery-1.8.3.min.js"></script>
<script src="/js/selected.js"></script>
<%@ include file="/WEB-INF/views/premission.jsp" %>
</body>
</html>