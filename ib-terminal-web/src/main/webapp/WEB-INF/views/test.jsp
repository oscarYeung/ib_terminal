<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  
  </head>
  
  <body>
    <a href="/changeLanguage.hyml?langType=zh">中文</a> <a href="/changeLanguage.hyml?langType=en">英文</a>
    下面展示的是后台获取的国际化信息：<br/>
    ${money}<br/>
    ${date}<br/>
 ${message}
    下面展示的是视图中直接绑定的国际化信息：<br/>
    <spring:message code="money"/>:<br/>
   
    <spring:message code="date"/>:<br/>
     <spring:message code="message"/>:<br/>
     
     <a href="/testssion.jsp">testssion.jsp</a>
   <script type="text/javascript">
  	var currenpath =window.location.href;
  </script>
  </body>
</html>
