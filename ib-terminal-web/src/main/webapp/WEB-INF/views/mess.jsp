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
    
    <title>My JSP 'mess.jsp' starting page</title>
    
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
     <a href="changeLanguage.hyml?langType=zh">中文</a> | <a href="changeLanguage.hyml?langType=en">英文</a><br/>

    下面展示的是后台获取的国际化信息：<br/>
    ${money}<br/>
    ${date}<br/>

    下面展示的是视图中直接绑定的国际化信息：<br/>
    <spring:message code="money"/>:<br/>
    <spring:eval expression="money"></spring:eval><br/>
    <spring:message code="date"/>:<br/>
    <spring:eval expression="date"></spring:eval><br/>
  </body>
</html>
