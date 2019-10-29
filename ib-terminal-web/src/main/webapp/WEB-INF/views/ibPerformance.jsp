<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>  
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%//10072562 /1234567 %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><spring:message code="ib.terminal.hycm"></spring:message></title>
<link rel="shortcut icon" href="/images/favicon.png" type="image/png" />
<link rel="stylesheet" href="/css/bootstrap.min.css" >
<link rel="stylesheet" href="/css/font-awesome.min.css" >
<link rel="stylesheet" href="/css/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" href="/css/common.css?v=<%= new java.util.Date().getTime() %>">
<link rel="stylesheet" href="/css/layout.css?v=<%= new java.util.Date().getTime() %>">
<link rel="stylesheet" href="/css/jstree/style.min.css">
<c:if test="${langType=='/chinese'}">
<link rel="stylesheet" href="/css/table_cn.css">
</c:if>
 <script src="/js/commom/dealResult.js"></script> 
<script src="/js/jquery-1.9.1.min.js"></script> 
 <script type="text/javascript" src="/js/jquery/jstree.min.js"></script>

<!--[if IE 9]>
<style type="text/css">
@media only screen and (max-width: 760px){
  .details-table{ width:100%; overflow-x: auto;}
  .details-table td:before{ float:none; padding-left:3px; }
  }
</style>
<![endif]-->
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
  <div class="page-center" >
    <div id="content">
      <div id="content_center">
        <ul class="breadcrumb">
          <li><i class="fa fa-home"></i> <a href="/index.hyml"><spring:message code="ib.home"></spring:message></a> <i class="fa fa-angle-right"></i><spring:message code="ib.commission"></spring:message> <i class="fa fa-angle-right"></i><spring:message code="ib.commission.enquiry"></spring:message></li>
        </ul>
        <div class="container-fluid">
          <h2 class="title"><spring:message code="ib.commission.enquiry"></spring:message></h2>
          <div class="client ib-performance" id="boxb">
              <h4><i class="fa fa-chevron-right"></i><spring:message code="ib.search"></spring:message></h4> 
              <form:form action="/ibCommission/searchTree.hyml" method="post" commandName="searchDto">
              <div class="select search">
                  <div class="per-wid44 client-date">           
                      <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input1">
                          <label><spring:message code="ib.from"></spring:message>:</label>
                          <form:input path="start_date" type="text"   readonly="true" size="16" />
                          <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                      </div>
                      <br>
                      <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                          <label><spring:message code="ib.to"></spring:message>:</label>
                          <form:input path="end_date" type="text"    readonly="true" size="16" />
                          <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                      </div>
                      <input type="hidden" name="ib_code">
                  </div>
                   <div class="wid150"><a href="javascript:void(0)" class="btn btn-red" onclick="submitIbForm();return false;"><i class="fa fa-search"></i><spring:message code="ib.search"></spring:message></a></div>
                     <p class="errorPrompt"><form:errors path="*"></form:errors>  
                     <c:if test="${!empty errormessage}">
             			<spring:message code="${errormessage}"></spring:message>
             		</c:if>
                     </p>
              </div>
			</form:form>
              <div class="ib-tree" >
                  <h3><spring:message code="ib.tree"></spring:message></h3>
                  <div class="st_tree" id="data">
                     
                  </div>
              </div>
            
              <div class="ib-perSummary" >
                  <ul class="nav nav-tabs">
                      <li class="active"><a href="#summary" data-toggle="tab"><spring:message code="ib.summary"></spring:message></a></li>
                      <li><a href="#clertSummary" id="a_client"><spring:message code="ib.client.summary"></spring:message> </a></li>
                      <li style=""><a href="#deatils"  id="a_details"><spring:message code="ib.details"></spring:message></a></li>  
                  </ul>
                  
                  <div class="tab-content ib-tab-content">
                      <div class="tab-pane fade in active" id="summary">
                          <dl>
                                <dd style="display:none"><spring:message code="ib.ib.code"></spring:message>:<span id="summary_ibcode"></span></dd>
                                <dd><spring:message code="ib.commission"></spring:message>:<span id="summary_commission"></span></dd>
                                <dd><spring:message code="ib.rebate"></spring:message>:<span id="summary_rebate"></span></dd>
                                <dd style="display:none"><spring:message code="ib.spread"></spring:message>:<span id="summary_spread"></span></dd>
                                <dd><spring:message code="ib.total"></spring:message>:<span id="summary_total"></span></dd>
                          </dl>
                          <div class="details-table per-summary-table">
                              <table class="table-hover" id="summaryTable">
                                <thead>
                                    <tr>
                                       
                                        <th><spring:message code="ib.ib.code"></spring:message></th>
                                        <th><spring:message code="ib.product"></spring:message></th>
                                        <th><spring:message code="ib.total.lot"></spring:message></th>
                                        <th><spring:message code="ib.currency"></spring:message></th>
                                        <th><spring:message code="ib.commission"></spring:message></th>
                                        <th><spring:message code="ib.rebate"></spring:message></th>
                                        <th class="borRnone"><spring:message code="ib.total"></spring:message></th>
                                    </tr>
                                </thead>
                                   
                              </table>
                          </div>
                      </div>

                      <div class="tab-pane fade" id="clertSummary">
                          <dl>
                                <dd><spring:message code="ib.ib.code"></spring:message>:<span id="client_ibcode"></span></dd>
                                <dd><spring:message code="ib.commission"></spring:message>:<span id="client_commission"></span></dd>
                                <dd><spring:message code="ib.rebate"></spring:message>:<span id="client_rebate"></span></dd>
                                <dd><spring:message code="ib.spread"></spring:message>:<span id="client_spread"></span></dd>
                                <dd><spring:message code="ib.total"></spring:message>:<span id="client_total"></span></dd>
                          </dl>
                          <div class="details-table per-ClientSummary-table">
                              <table class="table-hover" id="clientTable">
                                <thead>
                                    <tr>
                                        <th><spring:message code="ib.client.code"></spring:message></th>
                                        <th><spring:message code="ib.product"></spring:message></th>
                                        <th><spring:message code="ib.total.lot"></spring:message></th>
                                        <th><spring:message code="ib.currency"></spring:message></th>
                                        <th><spring:message code="ib.commission"></spring:message></th>
                                        <th><spring:message code="ib.rebate"></spring:message></th>
                                        <th class="borRnone"><spring:message code="ib.total"></spring:message></th>
                                    </tr>
                                </thead>
                                   
                              </table>
                          </div>

                      </div>
                      <div class="tab-pane fade" id="deatils">
                          <dl>
                                <dd><spring:message code="ib.client.code"></spring:message>:<span id="detail_ibcode"></span></dd>
                                <dd><spring:message code="ib.commission"></spring:message>:<span id="detail_commission"></span></dd>
                                <dd><spring:message code="ib.rebate"></spring:message>:<span id="detail_rebate"></span></dd>
                                <dd><spring:message code="ib.spread"></spring:message>:<span id="detail_spread"></span></dd>
                                <dd><spring:message code="ib.trade.pl"></spring:message>:<span id="detail_total"></span></dd>
                          </dl>
                          <div class="details-table per-Detaile-table">
                              <table class="table-hover" id="detailTable">
                                <thead>
                                    <tr>
                                       
                                        <th><spring:message code="ib.client.ib.code"></spring:message></th>
                                        <th><spring:message code="ib.product"></spring:message></th>
                                        <th><spring:message code="ib.total.lot"></spring:message></th>
                                        <th><spring:message code="ib.currency"></spring:message></th>
                                        <th><spring:message code="ib.commission"></spring:message></th>
                                        <th><spring:message code="ib.rebate"></spring:message></th>
                                        <th class="borRnone"><spring:message code="ib.trade.pl"></spring:message></th>
                                    </tr>
                                </thead>
                              </table>
                          </div>
                      </div>
                </div>
              </div>    


          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<div id="divSummary" style="display:none">${jsonString}</div>
<div style="display:none">
	<span id="m_start"><spring:message code="input.start.date"></spring:message></span>
	<span id="m_end"><spring:message code="input.end.date"></spring:message></span>
</div>
<jsp:include page="${langType}/include/foot.html"></jsp:include>

<script src="/js/adaptability.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/bootstrap-datetimepicker.min.js"></script>  
  <script src="/js/commom/dealResult.js"></script>  
<script type="text/javascript" src="/js/commission/ibPerformance.js"></script>
<script type="text/javascript">
//Spread:0.00 是哪个的值
  $('.form_date').datetimepicker({
        weekStart: 1,
        todayBtn:  true,
        autoclose: true,
        todayHighlight: true,
        startView: 2,
        minView: 2,
        forceParse: true,
    });
  
  function clearTableTr(tableId){
		$("#"+tableId+" tr").each(function (i){
			if(i>0){
				$(this).remove();
			}
		});
	}

	$('#data').jstree(
		{
			${jsonString}
		}
	).on("select_node.jstree", 
			function (e, data) 
			{
				showTab(0);//show summary
				treeClickClear();
			    var start_date=$("#start_date").val();
		 		 var end_date=$("#end_date").val();
				$.ajax({
					  url: "/ibCommission/getSummary.hyml",
					  context: document.body,
					 data: "ib_code="+data.node.text+"&start_date="+start_date+"&end_date="+end_date,
					  dataType: 'text', 
					  type: "post"
					}).done(function(json) {
						var $tr=$("#summaryTable thead").eq(0);
						clearTableTr("summaryTable");
						var htmlstr="";
						var summary_commission=0.00;
						var summary_rebate=0.00;
						var summary_spread=0.00;
						var summary_total=0.00;
						$("#summary_ibcode").html(data.node.text);
						if(null!=json&&""!=json&&json.indexOf("\{")<=-1){
							htmlstr="<tr>";
							htmlstr+="<td colspan=8 class=errorInfo>"+json+"</td>";
							htmlstr+="</tr>";
						}else{
							json=JSON.parse(json);
							//$("#summary_ibcode").html("");
							var errorMap=json.header.errorMap;
							if(json.header.status=="SUCCESS"){
								var summaryDate =json.body.list;
								
								for(var i=0 ;i<summaryDate.length;i++){
									var rebate=summaryDate[i].total_rebate_commission_lot+summaryDate[i].total_rebate_commission_pip;
									htmlstr+="<tr onclick=clickToClient("+summaryDate[i].ib_code+")>"
									//htmlstr+="<td>"+summaryDate[i].brand_code+"</td>";
									htmlstr+="<td>"+summaryDate[i].ib_code+"</td>";
									htmlstr+="<td>"+summaryDate[i].product_group+"</td>"
									htmlstr+="<td>"+summaryDate[i].total_lot+"</td>";
									htmlstr+="<td>"+summaryDate[i].currency+"</td>";
									htmlstr+="<td>"+summaryDate[i].total_fix_commission+"</td>";
									htmlstr+="<td>"+rebate+"</td>";
									htmlstr+="<td>"+summaryDate[i].total_commission+"</td>";
									//htmlstr+="<td>"+summaryDate[i].total_rebate_commission_pip+"</td>";
									
									//htmlstr+="<td > <a href=javascript:void(0)  onclick=clickToClient("+summaryDate[i].ib_code+")>IbInfo</a></td>";
									htmlstr+="</tr>";
								//	$("#summary_ibcode").html(summaryDate[i].ib_code);
									summary_commission+=summaryDate[i].total_fix_commission;
									summary_rebate+=rebate;
									summary_spread+=summaryDate[i].total_fix_commission;
									summary_total+=summaryDate[i].total_commission;
									
								}
							}else{
								dealResultLoginOut(errorMap);
								for(var str in errorMap ){
									htmlstr="<tr>";
									htmlstr+="<td colspan=8 class=errorInfo>"+errorMap[str]+"</td>";
									htmlstr+="</tr>";
								}
							}
						}
						setTotalValue("summary",summary_commission,summary_rebate,summary_spread,summary_total);
					      $tr.after(htmlstr);
					});
			}
	); 
	
	
  //showTree();
</script>
<%@ include file="/WEB-INF/views/premission.jsp" %>
</body>
</html>