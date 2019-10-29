<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String ibcode=request.getParameter("ibcode");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>REGISTER</title>
	
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
   
    <link href="css/system/bootstrap.min.css" rel="stylesheet">
     <style type="text/css">
    .panelceaa{  width:400px;    height:660px;    position:absolute;    left:50%;    top:40%;    margin-left:-200px;    margin-top:-130px; box-shadow:0 2px 7px 1px #568D90
			
			}
	form label.error
		{
		color: #c00;
		clear:both;
		}
	label{ font-weight: normal;}	
    </style>
  <script type="text/javascript" src="js/jquery/lib/jquery.min-3.0.0.js"></script>
 <script type="text/javascript" src="js/system/bootstrap.min.js"></script>
  <script type="text/javascript" src="js/jquery/jquery.validate.min.js"></script>
  <script type="text/javascript" src="js/reg/registerValidate.js"></script>

  </head>
  
  <body class="loginbg">
  <div class="container ">
<div class="panel panel-default panelce">
   <div class="panel-heading">
      <h3 class="panel-title">
       Registration
      </h3>
   </div>
   <div class="panel-body">
   
   <form:form  class="form-horizontal" action="doReg.hyml" method="post" id="registerForm" commandName="registerDto" >
  		<form:input path="parentIbCode" type="hidden" value="<%=ibcode %>"/>
       <label for="firstName" class="col-sm-3 control-label ">firstName</label>
       <div class="form-group col-sm-9">
      		<form:input  type="text" class=" form-control"  path="firstName"  />
      </div>
   		<label for="lastName" class="col-sm-3 control-label">lastName</label>
       <div class="form-group col-sm-9">
      		<form:input  type="text" class=" form-control"  path="lastName"  />
      </div>
       <label for="country" class="col-sm-3 control-label">country</label>
       <div class="form-group col-sm-9">
      		
      		<form:select path="country"  class="selectpicker" data-style="btn-info">
				<form:option value=""> Select Area</form:option>
				<form:option value="AF">Afghanistan</form:option>
				<form:option value="AL">Albania</form:option>
				<form:option value="DZ">Algeria</form:option>
			</form:select>
      </div>
     
    <label for="mobile" class="col-sm-3 control-label">mobile</label>
       <div class="form-group col-sm-9">
      		
      		<form:input  type="text" class=" form-control"  path="mobile"  />
      </div>
      
       <label for="sex" class="col-sm-3 control-label">sex</label>
       <div class="radio form-group col-sm-9">
	   		<label>
	       <form:radiobutton   path="sex"  value="m"/>male
	    	</label>
	    	 <label>
	    	  <form:radiobutton   path="sex"  value="f"/>female
	    	</label>
	    	 <br/>
      </div>
    
       <label for="address" class="col-sm-3 control-label">address</label>
       <div class="form-group col-sm-9">
      		<form:input  type="text" class=" form-control"  path="address"  />
      </div>
     
      <label for="email" class="col-sm-3 control-label">email</label>
       <div class="form-group col-sm-9">
      		<form:input  type="text" class=" form-control"  path="email"  />
      </div>
      <label for="username" class="col-sm-3 control-label">username</label>
       <div class="form-group col-sm-9">
      		<form:input  type="text" class=" form-control"  path="username"  maxlength=""/>
      </div>
      <label for="password" class="col-sm-3 control-label">password</label>
       <div class="form-group col-sm-9">
      		<form:input  type="password" class=" form-control"  path="password"  />
      </div>
      <label for="rePassword" class="col-sm-3 control-label">rePassword</label>
       <div class="form-group col-sm-9">
      		<form:input  type="password" class=" form-control"  path="rePassword"  />
      </div>
         
   <div class="form-group text-center">
      <button type="submit" class="btn btn-info col-sm-offset-3 col-sm-8" id="registerBtn">Sign Up Now, Free!</button>
   </div>
    <div class="form-group text-center" style="color:red" id="regError"> <form:errors path="*" cssClass="error"  /> 
  	<% if(null!=request.getAttribute("errormessage")){ %>
  	<%=request.getAttribute("errormessage") %>
  	<%} %>
  	
  	
  	</div>
  </form:form>
   </div><!-- panel body-->
   </div><!-- panel -->
   </div>
  </body>
</html>

