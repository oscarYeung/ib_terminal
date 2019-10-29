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
    
   <title>SpringMVC<spring:message code="InsertMarginOut.amount.not.blank" /></title>
</head>
<body>
    Language: <a href="hello.hyml?lang=zh_CN">中文</a> - <a href="hello.hyml?lang=en_US">英文</a>
    <h2>
        <spring:message code="message" />
    </h2>
    Locale: ${pageContext.response.locale }
   
    1.输入框提示语
    2.server传递过来文字
    3.操作成功文字
    4.本地validate message
    5.国家
</body>
</html>
</html>
