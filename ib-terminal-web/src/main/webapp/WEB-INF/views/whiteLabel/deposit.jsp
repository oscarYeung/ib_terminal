<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"  %> 
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
<title>IBTerminal-HYCM</title>
<link rel="shortcut icon" href="../images/favicon.png" type="image/png" />
<link rel="stylesheet" href="../css/bootstrap.min.css" >
<link rel="stylesheet" href="../css/bootstrap-datetimepicker.min.css">	
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
          	<i class="fa fa-angle-right"></i><spring:message code="white.label.deposit"/></li>
        </ul>
         <div class="container-fluid">
          <h2 class="title"><spring:message code="white.label.deposit"/></h2>
          <p class="line"></p>
          <div class="balanceTransferProfile">
          	<form action="/whiteLabel/doDeposit.hyml" method="post" id="adminDepositForm" role="form">
	          <div class="form-group">
	          	<div>    	 
		          	<label><spring:message code="white.label.master.account"/>:</label>
	          	  	<input type="text"  id="masterAccount" class="form-control"  disabled="" value="${masterAccount}">
          	  	</div>
          	  	<div>
		          	<label><spring:message code="ib.currency"/>:</label>
		          	<input type="text" name="currency" id="currency" class="form-control" value="USD" disabled="" value="${currency}">
	          	</div>
	          	<div>
			        <label><spring:message code="white.label.available.fund"/>:</label>
			        <input type="text" id="maxWithdrawal" name="maxWithdrawal" class="form-control" value="${maxWithdrawal}"  disabled="">
		        </div>
		        <div>
					<label><spring:message code="white.label.to.account"/>:</label>
			        <input type="text" id="account" name="account" class="form-control" >
		        </div>
		        <div>
			        <label><spring:message code="ib.amount"/>:</label>
			        <input type="text" id="amount" name="amount" class="form-control">
		        </div>
       
       			<a id="depositSubmit" href="" class="btn btn-red"><spring:message code="ib.submit"/></a>
       			<a id="depositCancel" href="" class="btn"><spring:message code="ib.cancle"/></a>
       			
       			<label class="error" id="errorInfo" style="display:none">
		          <c:if test="${!empty errorInfo}"><spring:message code="${errorInfo }"></spring:message></c:if>
		          <c:if test="${!empty errorDeposit}"><spring:message code="${errorDeposit }"></spring:message></c:if>
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
<script type="text/javascript" src="/js/whiteLabel/deposit.js"></script>
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
	System.out.println("1  errorInfo  "+request.getSession().getAttribute("errorDeposit"));
	if(null!=request.getSession().getAttribute("errorDeposit")){
		String errorInfo=request.getSession().getAttribute("errorDeposit").toString();
		System.out.println("  errorDeposit  "+errorInfo);
		request.getSession().removeAttribute("errorDeposit");
	}
    %>


<script src="/js/adaptability.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/selected.js"></script>
<%@ include file="/WEB-INF/views/premission.jsp" %>
</body>
</html>