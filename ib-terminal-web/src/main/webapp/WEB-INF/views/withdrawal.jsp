<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>  
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"  %> 
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

  <!-- left fixed End--> 
  
  <!-- right center -->
  <div class="page-center" id="boxb">
    <div id="content">
      <div id="content_center">
        <ul class="breadcrumb">
          <li><i class="fa fa-home"></i> <a href="/index.hyml"><spring:message code="ib.home"></spring:message></a><i class="fa fa-angle-right"></i><spring:message code="ib.fund"></spring:message><i class="fa fa-angle-right"></i><spring:message code="ib.withdrawal"></spring:message></li>
        </ul>
        <div class="container-fluid">
          <h2 class="title"><spring:message code="ib.withdrawal"></spring:message> </h2>
          <p class="line">
          </p>
          <div class="profile withdrawal">
              <form:form   action="insertPendingMarginOut.hyml" method="post" id="withdrawalForm" commandName="withdrawalModel" >
                 <form:input path="max_withdrawal" type="hidden" class="form-control"  readonly="true" />
                  <div class="form-group">
                      <label><spring:message code="ib.ib.code"></spring:message>：</label>
                      <form:input path="ib_code" type="text" class="form-control"   readonly="true"/>
                      <label><spring:message code="ib.currency"></spring:message>:</label>
                       <form:input path="currency" type="text" class="form-control"  readonly="true"  />
                      <label><spring:message code="ib.max.withdrawal"></spring:message>:</label>
                      <input id="max_withdrawal_show" type="text" class="form-control"  readonly="true" />
                      <label><spring:message code="ib.request.amount"></spring:message>:</label>
                      <form:input path="amount" type="text" class="form-control" />
                       <p id="pmaxWidthdrawal" style="display:none">
                       <fmt:formatNumber type="number" value="${withdrawalModel.max_withdrawal}"  maxFractionDigits="2" minFractionDigits="2"/></p>
                      <p class="errorPrompt">
                      
                      <c:if test="${!empty errormessage}">
             			<spring:message code="${errormessage}"></spring:message>
             		</c:if>
                      <form:errors path="*"></form:errors>
                      </p>
                      <a href="javascript:void(0)" class="btn btn-red" id="sbtWithdrawal"><spring:message code="ib.submit"></spring:message></a><a href="/index.hyml" class="btn"><spring:message code="ib.cancle"></spring:message></a>
                  </div>
              </form:form>
              
              <h4><i class="fa fa-chevron-right"></i><spring:message code="ib.history"></spring:message></h4>
              <div class="errorPrompt">${errorWithdrawal }</div>
              <div class="details-table withdrawal-table">
                          <table class="table-hover">
                              <thead>
                                  <tr>
                                      <th><spring:message code="ib.request.date"></spring:message></th>
                                      <th><spring:message code="ib.currency"></spring:message></th>
                                      <th><spring:message code="ib.request.amount"></spring:message></th>
                                      <th><spring:message code="ib.status"></spring:message></th>
                                      <th class="borRnone"></th>
                                  </tr>
                              </thead>
                              <c:forEach items="${withdrawlHistry.marginOutBeans }" var="histroy">
                              <tr>
                                <td><fmt:formatDate value="${histroy.create_time }" pattern="yyyy-MM-dd HH:mm:ss" /> </td>
                                <td>${histroy.currency }</td>
                                <td><div>${histroy.amount }</div></td>
                                <td>
                                <c:forEach items="${statusMap}" var="st">
	                               <c:if test="${histroy.status eq st.key }">
	                                 ${st.value }
	                                </c:if> 
	                              
                                </c:forEach></td>
                                <td>
                                <c:if test="${histroy.status eq 'P' }">
                                <a href="cancelMarginOut.hyml?marginOutId=${histroy.id }" class="btn btn-red"><spring:message code="ib.cancle"></spring:message></a>
                                </c:if>
                                </td>
                              </tr>
                              </c:forEach>
                             
                          </table>

                      </div>
              <!-- table -->
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<%
if(null!=request.getSession().getAttribute("errorWithdrawal")){
	request.getSession().removeAttribute("errorWithdrawal");
 }
if(null!=request.getSession().getAttribute("errormessage")){
	request.getSession().removeAttribute("errormessage");
 }

%>
<script type="text/javascript">
function istel(str) { 
	var regexp=/^[0-9]+$/;
	var flag = regexp.test(str);
	return !flag; 
	}

$("#sbtWithdrawal").click(function (){
	var amount=$.trim($("#amount").val());
	if(amount.length==0){
		alert("<spring:message code='input.amount'></spring:message>");
		return false;
	}
	if(istel(amount)){
		alert("<spring:message code='input.correct.amount'></spring:message>");
		return false;
	}
	$("#withdrawalForm").submit();
});
$("#max_withdrawal_show").val($.trim($("#pmaxWidthdrawal").text()));
</script>
<jsp:include page="${langType}/include/foot.html"></jsp:include>
<script src="/js/adaptability.js"></script>
<script src="/js/bootstrap.min.js"></script>
<%@ include file="/WEB-INF/views/premission.jsp" %>
</body>
</html>