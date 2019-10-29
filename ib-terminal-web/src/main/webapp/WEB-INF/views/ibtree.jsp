<%@page import="com.henyep.ib.terminal.entity.Profiledto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>  
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
String []str1={"tt","aa"};
session.setAttribute("test", str1);
String [] str =(String [])session.getAttribute("test");
Profiledto t= new Profiledto();
%>
<html>
<head>

	
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
	<style type="text/css" src="css/jstree/style.min.css"></style>
	<style type="text/css">
	.errorInfo{ color: red}
	</style>
    <script type="text/javascript" src="/js/jquery/lib/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="/js/jquery/jstree.min.js"></script>
</head>
<body>
	

	
	<h1>MY IB Tree</h1>
	<form:form action="/ibCommission/searchTree.hyml" method="post" commandName="searchDto">
		<p><form:errors path="*" cssClass="error"  /> </p>
		start_date:<form:input  type="text" class=" form-control"  path="start_date" value="2016-07-01 12:12:12" />
		end_date:<form:input  type="text" class=" form-control"  path="end_date"  />
		ib_code:<form:input  type="text" class=" form-control"  path="ib_code"  />
		
		<form:errors path="*"></form:errors>
		<form:button type="submit" >submit search</form:button>
	</form:form>
	
	<div id="data" class="demo"></div>

	<div id="summaryContent"></div>
	<h2> Commssion Summary</h2>
	<table id="tblSummary">
	<tr><td></td></tr>
	</table>
	
	<h2>Client Commssion Summary</h2>
	<table id="tblClient">
	<tr><td></td></tr>
	</table>
	
	
	<hr>
	<h2>Commssion Summary Details</h2>
	<table id="tblDetail">
	<tr><td ></td></tr>
	</table>
	<script>
	var start_date=$("#start_date").val();
	var startDate=$("#end_date").val();
	$('#data').jstree(
		{
			${jsonString}
		}
	).on("select_node.jstree", 
			function (e, data) 
			{ 
				$.ajax({
					  url: "/ibCommission/getSummary.hyml",
					  context: document.body,
					  //data: "ib_code="+data.node.text+"&start_date=2016-07-26 12:12:12&end_date=2016-09-26 12:12:12",
					   data: "ib_code="+data.node.text+"&start_date=2016-07-26 12:12:12&end_date=2016-09-26 12:12:12",
					  dataType: 'text', 
					  type: "GET"
					}).done(function(json) {
							
						
						var htmlstr="";
						if(null!=json&&""!=json&&json.indexOf("\{")<=-1){
							htmlstr="<tr>";
							htmlstr+="<td colspan=9 class=errorInfo>"+json+"</td>";
							htmlstr+="</tr>";
						}else{
							json=JSON.parse(json);
							var errorMap=json.header.errorMap;
							if(errorMap['001']=="SUCCESS"){
								var  summaryDate =json.body.list;
								for(var i=0 ;i<summaryDate.length;i++){
									htmlstr+="<tr>"
									htmlstr+="<td>"+summaryDate[i].brand_code+"</td>";
									htmlstr+="<td>"+summaryDate[i].ib_code+"</td>";
									htmlstr+="<td>"+summaryDate[i].product_group+"</td>"
									htmlstr+="<td>"+summaryDate[i].currency+"</td>";
									htmlstr+="<td>"+summaryDate[i].total_lot+"</td>";
									htmlstr+="<td>"+summaryDate[i].total_fix_commission+"</td>";
									htmlstr+="<td>"+summaryDate[i].total_rebate_commission_lot+"</td>";
									htmlstr+="<td>"+summaryDate[i].total_rebate_commission_pip+"</td>";
									htmlstr+="<td>"+summaryDate[i].total_commission+"</td>";
									htmlstr+="<td > <a href=javascript:void(0)  onclick=clickToClient("+summaryDate[i].ib_code+")>IbInfo</a></td>";
									htmlstr+="</tr>";
								}
							}else{
								for(var str in errorMap ){
									htmlstr="<tr>";
									htmlstr+="<td colspan=9 class=errorInfo>"+errorMap[str]+"</td>";
									htmlstr+="</tr>";
								}
							}
						}
						
						$("#tblSummary").html(htmlstr);
					});
		
			}
	);
	
	function clickToClient(datapara){
		$.ajax({
			url:"getClientSummary.hyml",
			type:"get",
			dataType:"text",
			 data: "ib_code="+datapara+"&start_date=2016-07-26 12:12:12&end_date=2016-09-26 12:12:12",
			success:function (json){
				var htmlstr="";
				if(null!=json&&""!=json&&json.indexOf("\{")<=-1){
					htmlstr="<tr>";
					htmlstr+="<td colspan=9 class=errorInfo>"+json+"</td>";
					htmlstr+="</tr>";
				}else{
					
					json=JSON.parse(json);
					var errorMap=json.header.errorMap;
					if(errorMap['001']=="SUCCESS"){
						var  summaryDate =json.body.list;
						for(var i=0 ;i<summaryDate.length;i++){
							htmlstr+="<tr>"
								htmlstr+="<td>"+summaryDate[i].brand_code+"</td>";
								htmlstr+="<td>"+summaryDate[i].ib_code+"</td>";
								htmlstr+="<td>"+summaryDate[i].product_group+"</td>"
								htmlstr+="<td>"+summaryDate[i].currency+"</td>";
								htmlstr+="<td>"+summaryDate[i].client_ib_code+"</td>";
								htmlstr+="<td>"+summaryDate[i].client_code+"</td>";
								htmlstr+="<td>"+summaryDate[i].total_lot+"</td>";
								htmlstr+="<td>"+summaryDate[i].total_rebate_commission_pip+"</td>";
								htmlstr+="<td>"+summaryDate[i].total_commission+"</td>";
								htmlstr+="<td > <a href=javascript:void(0)  onclick=clickToDetail("+summaryDate[i].ib_code+","+summaryDate[i].client_ib_code+")>Details</a></td>";
								htmlstr+="</tr>";
						}
					}else{
						for(var str in errorMap ){
							htmlstr="<tr>";
							htmlstr+="<td colspan=9 class=errorInfo>"+errorMap[str]+"</td>";
							htmlstr+="</tr>";
						}
					}
				}
				
				$("#tblClient").html(htmlstr);
			}
		});
		
	}
	function clickToDetail(ibcode,clientcode){
		
		$.ajax({
			url:"getDetails.hyml",
			type:"get",
			dataType:"text",
			 data: "ib_code="+ibcode+"&start_date=2016-07-26 12:12:12&end_date=2016-09-26 12:12:12&clientcode="+clientcode,
			success:function (json){
				var htmlstr="";
				if(null!=json&&""!=json&&json.indexOf("\{")<=-1){
					htmlstr="<tr>";
					htmlstr+="<td colspan=9 class=errorInfo>"+json+"</td>";
					htmlstr+="</tr>";
				}else{
					json=JSON.parse(json);
					var errorMap=json.header.errorMap;
					var htmlstr="";
					if(errorMap['001']=="SUCCESS"){
						var  summaryDate =json.body.list;
						for(var i=0 ;i<summaryDate.length;i++){
							htmlstr+="<tr>"
							htmlstr+="<td>"+summaryDate[i].brand_code+"</td>";
							htmlstr+="<td>"+summaryDate[i].ib_code+"</td>";
							htmlstr+="<td>"+summaryDate[i].product_group+"</td>"
							htmlstr+="<td>"+summaryDate[i].currency+"</td>";
							htmlstr+="<td>"+summaryDate[i].client_ib_code+"</td>";
							htmlstr+="<td>"+summaryDate[i].client_code+"</td>";
							htmlstr+="<td>"+summaryDate[i].total_lot+"</td>";
							htmlstr+="<td>"+summaryDate[i].total_rebate_commission_pip+"</td>";
							htmlstr+="<td>"+summaryDate[i].total_commission+"</td>";
							htmlstr+="</tr>";
						}
					}else{
						for(var str in errorMap ){
							htmlstr="<tr>";
							htmlstr+="<td colspan=9 class=errorInfo>"+errorMap[str]+"</td>";
							htmlstr+="</tr>";
						}
					}
				}
				
				$("#tblDetail").html(htmlstr);
			}
		});
		
	}
	</script>

<%@ include file="/WEB-INF/views/premission.jsp" %>

</body>
</html>



