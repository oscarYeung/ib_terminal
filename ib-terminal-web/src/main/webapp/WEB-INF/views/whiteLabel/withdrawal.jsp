<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>IBTerminal-HYCM</title>
<link rel="shortcut icon" href="../images/favicon.png" type="image/png" />
<link rel="stylesheet" href="../css/bootstrap.min.css" >
<link rel="stylesheet" href="../css/font-awesome.min.css" >
<link rel="stylesheet" href="../css/common.css?v=<%= new java.util.Date().getTime() %>">
<link rel="stylesheet" href="../css/layout.css?v=<%= new java.util.Date().getTime() %>">
<script src="../js/jquery-1.9.1.min.js"></script> 
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
          <li>
          	<i class="fa fa-home"></i> 
          	<a href="index.html"><spring:message code="ib.home"/></a>
          	<i class="fa fa-angle-right"></i><spring:message code="white.label"/>
          	<i class="fa fa-angle-right"></i><spring:message code="white.label.withdrawal"/></li>
        </ul>
        <div class="container-fluid">
          <h2 class="title"><spring:message code="white.label.withdrawal"/></h2>
          <p class="line"></p>
          <div class="balanceTransferProfile">
              <form action="/whiteLabel/doWithdrawal.hyml" method="post" id="adminWithdrawalForm" >
                  <div class="form-group">
                  	  <div>
	                 	  <label><spring:message code="ib.client.account"/>:</label>
	                      <input class="form-control" type="text" name="account" id="account" class="required" onblur="getMaxWithdrawal()">
                      </div>
                      <div>
	                      <label><spring:message code="ib.currency"/>:</label>
	                      <input class="form-control" type="text" name="currency" id="currency" class="required"   disabled="">
                      </div>
                      <div>
	                      <label><spring:message code="ib.max.withdrawal"/>:</label>
	                      <input class="form-control" type="text" id="maxWithdrawal" name="maxWithdrawal" class="required"  disabled="">
                      </div>
                      <div>
	                      <label><spring:message code="ib.amount"/>:</label>
	                      <input class="form-control" type="text" id="amount" name="amount" class="required compareAmount">
                      </div>
                      
                      <a id="withdrawalSubmit" href="" class="btn btn-red"><spring:message code="ib.submit"/></a>
                      <a id="withdrawalCancel" href="" class="btn"><spring:message code="ib.cancle"/></a>
                      
                      <label class="error" id="errorInfo" style="display:none">
						<c:if test="${!empty errorInfo}"><spring:message code="${fn:trim(errorInfo)}"></spring:message></c:if>
		     			<c:if test="${!empty errorWithdrawal}"><spring:message code="${fn:trim(errorWithdrawal)}"></spring:message></c:if>
					  </label>
                  </div>
              </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

    <script type="text/javascript" src="/js/jquery/jquery.validate.min.js"></script>
    <script type="text/javascript" src="/js/whiteLabel/withdrawal.js"></script>
    <%
    if(null!=request.getSession().getAttribute("toOrderId")){
		request.getSession().removeAttribute("toOrderId");
	}
	if(null!=request.getSession().getAttribute("fromOrderId")){
		request.getSession().removeAttribute("fromOrderId");
	}
	if(null!=request.getSession().getAttribute("fromAmount")){
		request.getSession().removeAttribute("fromAmount");
	}
	
	if(null!=request.getSession().getAttribute("errorWithdrawal")){
		request.getSession().removeAttribute("errorWithdrawal");
	}
    %>

<script src="/js/adaptability.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/selected.js"></script>
<%@ include file="/WEB-INF/views/premission.jsp" %>
</body>
</html>