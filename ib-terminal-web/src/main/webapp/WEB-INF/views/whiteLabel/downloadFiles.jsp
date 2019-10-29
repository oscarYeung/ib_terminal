<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<link rel="stylesheet" href="/css/bootstrap-datetimepicker.min.css">
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
  <form action="/whiteLabel/getUploadedFiles.hyml" method="post" id="downloadForm" >
  
	<div class="page-center" id="boxb">
	  <div id="content">
	    <div id="content_center">
	      <ul class="breadcrumb">
	        <li>
	        	<i class="fa fa-home"></i> 
	        	<a href="index.html"><spring:message code="ib.home"/></a>
	        	<i class="fa fa-angle-right"></i><spring:message code="white.label"/>
	        	<i class="fa fa-angle-right"></i><spring:message code="documents.download"/>
	        </li>
	      </ul>
	      <div class="container-fluid">
	        <h2 class="title"><spring:message code="documents.download"/></h2>
	        <div class="client client-margin">
	            <h4><i class="fa fa-chevron-right"></i><spring:message code="ib.search"/></h4> 
	            <div class="select search">
	                <div class="client-date download-date">           
	                    <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input1">
	                        <label><spring:message code="ib.from"/>:</label>
	                        <input size="16" type="text" value="${start_date }" name="start_date" id="start_date" class="required">
	                        <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
	                    </div>
	                    <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
	                        <label><spring:message code="ib.to"/>:</label>
	                        <input size="16" type="text"  name="end_date" id="end_date" value="${end_date }" class="required">
	                        <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
	                    </div>
	                </div>
	                    <div class="download-searchBtn">
		                    <a href="javascript:;" class="btn btn-red" onclick="submitSearchFile()">
		                    	<i class="fa fa-search"></i>
		                    	<spring:message code="ib.search"/>
		                    </a>

	                   		<div class="dowloadError" id="dowloadError"> 
	                   			<c:if test="${!empty errorInfo}">
	                   				<spring:message code="${errorInfo }"></spring:message>
	                   			</c:if>
	                   		</div>
	                    </div>
	                </div>
	                
	                <div class="download-all">
	                    <ul>
	                      <li>
	                      	<spring:message code="documents.download.total.records"/>:
							<c:if test="${listFiles!=null}">
								${listFiles.size()}
							</c:if>
						  </li>
	                      <li class="floatR"><a href="javascript:void(0)" id="downLoadAllClientInfo"><spring:message code="documents.download.download.client.info"/></a></li>
	                      <li class="floatR"><a href="javascript:void(0)" id="downLoadAllDocuments"><spring:message code="documents.download.download.all.documents"/></a></li>
	                    </ul>
	
	                    <div class="details-table documents-download">
	                        <table class="table-hover">
	                            <thead>
	                                <tr>
	                                	<th><spring:message code="documents.download.type"/></th>
	                                    <th><spring:message code="documents.download.trading.id"/></th>
	                                    <th><spring:message code="documents.download.first.name"/></th>
	                                    <th><spring:message code="documents.download.last.name"/></th>
	                                    <th><spring:message code="documents.download.registration.date"/></th>
	                                    <th class="borRnone"><spring:message code="documents.download.download"/></th>
	                                </tr>
	                            </thead>
	                           <c:forEach items="${listFiles}" var="files" varStatus="status">
									<tr>
										<td>${files.server_code }</td>
										<td>${files.trading_id }</td>
										<td>${files.first_name }</td>
										<td>${files.last_name }</td>
										
										<td>
										<fmt:formatDate value="${files.registration_date }" pattern="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td>
										<c:if test="${files.has_upload_file==false||files.upload_file_path==null }">
											<spring:message code="documents.download.no.document"/>
										</c:if>
										<c:if test="${files.has_upload_file==true}">
										<a href="/whiteLabel/downLoadZipFile.hyml?tradingId=${files.trading_id }">
											<spring:message code="documents.download.download"/>
										</a>
										<input type="hidden" value="${files.trading_id }" name="tradingId">
										</td>
										</c:if>
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
  </form>

</div>
<jsp:include page="${langType}/include/foot.html"></jsp:include>
<script type="text/javascript" src="/js/jquery.validate.min.js"></script>
<script src="/js/adaptability.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/whiteLabel/downloadFile.js"></script>
<script src="/js/bootstrap-datetimepicker.min.js"></script>  
<script type="text/javascript">
	$('.form_date').datetimepicker({
	    weekStart: 1,
	    todayBtn:  true,
	    autoclose: true,
	    todayHighlight: true,
	    startView: 2,
	    minView: 2,
	    forceParse: true,
	    pickerPosition: "bottom-left"
	});
</script>

<%@ include file="/WEB-INF/views/premission.jsp" %>
</body>
</html>