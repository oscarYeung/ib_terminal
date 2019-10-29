<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>

	
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
	<style type="text/css" src="css/jstree/style.min.css"></style>
    <script type="text/javascript" src="/js/jquery/lib/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="/js/jquery/jquery.columns.min.js"></script>
</head>
<body>
	<form:form action="/ibCommission/searchTree.hyml" method="post" commandName="searchDto">
	
		start_date:<form:input  type="text" class=" form-control"  path="start_date"  />
		end_date:<form:input  type="text" class=" form-control"  path="end_date"  />
		ib_code:<form:input  type="text" class=" form-control"  path="ib_code"  />
		<form:button type="submit" >submit</form:button>
	</form:form>
	   <fmt:formatNumber value="0.3" type="currency"/><br />  
	   <fmt:formatNumber value="0.3" type="number"/><br />  
                <fmt:formatNumber value="0.3" type="currency"/><br />  
                <fmt:formatNumber value="0.3" type="percent"/><br />    
	<h1>/ibCommission/getSummary.hyml?ib_code=10073019</h1>
	<div id="data" class="demo"></div>

	<div id="example3"></div>

	<script>
	
	$.ajax({
		url:'/ibCommission/getSummary.hyml?ib_code=10073019',
		dataType: 'json', 
		success: function(json) { 
			
			var  summaryDate =json.body.list;
			example3 = $('#example3').columns({
				data:summaryDate
			});  
			var htmlstr="";
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
				htmlstr+="</tr>";
			}
			$("#summary").html(htmlstr);
		}
	}); 

	
	</script>

<table id="summary">
	<tr><td></td></tr>
</table>

</body>
</html>



