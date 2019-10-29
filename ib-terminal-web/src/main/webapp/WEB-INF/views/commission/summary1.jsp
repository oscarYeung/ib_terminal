<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"  %> 
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
<style type="text/css">
.headerth{

}
#clientSummary thead tr .header {
	background-image: url(/images/bg.gif);
	background-repeat: no-repeat;
	background-position: center right;
	cursor: pointer;
	transition:th 0s ;
	
}
#clientSummary thead tr .headerSortUp {
	background-image: url(/images/asc.gif);
}
#clientSummary thead tr .headerSortDown {
	background-image: url(/images/desc.gif);
}
</style>
<script src="/js/jquery-1.9.1.min.js"></script>  
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
  <div class="page-center" id="boxb">
    <div id="content">
      <div id="content_center">
        <ul class="breadcrumb">
          <li><i class="fa fa-home"></i> <a href="/index.hyml"><spring:message code="ib.home"></spring:message></a><i class="fa fa-angle-right"></i><spring:message code="ib.commission"></spring:message><i class="fa fa-angle-right"></i><spring:message code="ib.commission.summary"></spring:message></li>
        </ul>
        <div class="container-fluid">
          <h2 class="title"><spring:message code="ib.commission.summary"></spring:message></h2>
          <div class="client Commission">
              <h4><i class="fa fa-chevron-right"></i><spring:message code="ib.summary"></spring:message></h4> 
              <div class="comm-summary">
                  <div class="details-table summary-table">
                      <table class="table-hover" id="tasummary">
                          <thead>
                              <tr>
                                  <th></th>
                                  <th><spring:message code="ib.commission"></spring:message></th>
                                   <th><spring:message code="ib.rebate"></spring:message></th>
                                  <th><spring:message code="ib.total.lot"></spring:message></th>
                                  <th><spring:message code="ib.net.margin.in"></spring:message></th>
                                  <th><spring:message code="ib.margin"></spring:message></th>
                                  <th class="borRnone"><spring:message code="ib.margin.out"></spring:message></th>
                              </tr>
                          </thead>
                          <tr>
                            <td><spring:message code="ib.commission"></spring:message>(${todaySummary.date_description })</td>
                            <td>
                            <c:if test="${empty todaySummary.commission}">
                            0.00
                            </c:if>
                            <c:if test="${!empty todaySummary.commission}">
                           <fmt:formatNumber type="number" value="${todaySummary.commission }"  maxFractionDigits="2" minFractionDigits="2" />
                            </c:if>
                            </td>
                             <td>
                            <c:if test="${empty todaySummary.rebate}">
                            0.00
                            </c:if>
                            <c:if test="${!empty todaySummary.rebate}">
                           <fmt:formatNumber type="number" value="${todaySummary.rebate }"  maxFractionDigits="2" minFractionDigits="2" />
                            </c:if>
                            </td>
                             <td>
                                <c:if test="${empty todaySummary.total_lot}">
                           			 0.00
                           		 </c:if>
                           		 <c:if test="${!empty todaySummary.total_lot}">
                           		 <fmt:formatNumber type="number" value="${todaySummary.total_lot }"  maxFractionDigits="2" minFractionDigits="2"/>
                           		 </c:if>
                            </td>
                            <td>
                            <c:if test="${empty todaySummary.net_margin_in}">
                           			 0.00
                           		 </c:if>
                           		 <c:if test="${!empty todaySummary.net_margin_in}">
                           		 <fmt:formatNumber type="number" value="${todaySummary.net_margin_in }"  maxFractionDigits="2" minFractionDigits="2"/>
                           		 </c:if>
                           </td>
                            <td>
                            <c:if test="${empty todaySummary.margin_in}">
                           			 0.00
                           		 </c:if>
                           		 <c:if test="${!empty todaySummary.margin_in}">
                           		 <fmt:formatNumber type="number" value="${todaySummary.margin_in }"  maxFractionDigits="2" minFractionDigits="2"/>
                           		 </c:if>
                            </td>
                            <td>
                           		 <c:if test="${empty todaySummary.margin_out}">
                           			 0.00
                           		 </c:if>
                           		 <c:if test="${!empty todaySummary.margin_out}">
                           		 <fmt:formatNumber type="number" value="${todaySummary.margin_out }"  maxFractionDigits="2" minFractionDigits="2"/>
                           		 </c:if>
                            </td>
                          </tr>
                          
                          <tr>
                             <td><spring:message code="ib.commission"></spring:message><br>(${monthSummary.date_description })</td>
                            <td>
                             <c:if test="${empty monthSummary.commission}">
                           			 0.00
                           		 </c:if>
                           		 <c:if test="${!empty monthSummary.commission}">
                           		 <fmt:formatNumber type="number" value="${monthSummary.commission }"  maxFractionDigits="2" minFractionDigits="2"/>
                           		 </c:if>
                            </td>
                            <td>
                             <c:if test="${empty monthSummary.rebate}">
                           			 0.00
                           		 </c:if>
                           		 <c:if test="${!empty monthSummary.rebate}">
                           		 <fmt:formatNumber type="number" value="${monthSummary.rebate }"  maxFractionDigits="2" minFractionDigits="2"/>
                           		 </c:if>
                            </td>
                             <td>
                              <c:if test="${empty monthSummary.total_lot}">
                           			 0.00
                           		 </c:if>
                           		 <c:if test="${!empty monthSummary.total_lot}">
                           		 <fmt:formatNumber type="number" value="${monthSummary.total_lot }"  maxFractionDigits="2" minFractionDigits="2"/>
                           		 </c:if>
                             </td>
                            <td>
                             <c:if test="${empty monthSummary.net_margin_in}">
                           			 0.00
                           		 </c:if>
                           		 <c:if test="${!empty monthSummary.net_margin_in}">
                           		 <fmt:formatNumber type="number" value="${monthSummary.net_margin_in}"  maxFractionDigits="2" minFractionDigits="2"/>
                           		 </c:if>
                            </td>
                            <td>
                           		  <c:if test="${empty monthSummary.margin_in}">
                           			 0.00
                           		 </c:if>
                           		 <c:if test="${!empty monthSummary.margin_in}">
                           		  <fmt:formatNumber type="number" value="${monthSummary.margin_in}"  maxFractionDigits="2" minFractionDigits="2"/>
                           		 </c:if>
                            </td>
                            <td>
                             <c:if test="${empty monthSummary.margin_out}">
                           			 0.00
                           		 </c:if>
                           		 <c:if test="${!empty monthSummary.margin_out}">
                           		 <fmt:formatNumber type="number" value="${monthSummary.margin_out }"  maxFractionDigits="2" minFractionDigits="2"/>
                           		 </c:if>
                            </td>
                          </tr>
                           <tr>
                             <td><spring:message code="ib.commission"></spring:message><br>(${lastMonthSummary.date_description })</td>
                            <td>
                             <c:if test="${empty lastMonthSummary.commission}">
                           			 0.00
                           		 </c:if>
                           		 <c:if test="${!empty lastMonthSummary.commission}">
                           		 <fmt:formatNumber type="number" value="${lastMonthSummary.commission }"  maxFractionDigits="2" minFractionDigits="2"/>
                           		 </c:if>
                            </td>
                            <td>
                             <c:if test="${empty lastMonthSummary.rebate}">
                           			 0.00
                           		 </c:if>
                           		 <c:if test="${!empty lastMonthSummary.rebate}">
                           		 <fmt:formatNumber type="number" value="${lastMonthSummary.rebate }"  maxFractionDigits="2" minFractionDigits="2"/>
                           		 </c:if>
                            </td>
                             <td>
                              <c:if test="${empty lastMonthSummary.total_lot}">
                           			 0.00
                           		 </c:if>
                           		 <c:if test="${!empty lastMonthSummary.total_lot}">
                           		 <fmt:formatNumber type="number" value="${lastMonthSummary.total_lot }"  maxFractionDigits="2" minFractionDigits="2"/>
                           		 </c:if>
                             </td>
                            <td>
                             <c:if test="${empty lastMonthSummary.net_margin_in}">
                           			 0.00
                           		 </c:if>
                           		 <c:if test="${!empty lastMonthSummary.net_margin_in}">
                           		 <fmt:formatNumber type="number" value="${lastMonthSummary.net_margin_in}"  maxFractionDigits="2" minFractionDigits="2"/>
                           		 </c:if>
                            </td>
                            <td>
                           		  <c:if test="${empty lastMonthSummary.margin_in}">
                           			 0.00
                           		 </c:if>
                           		 <c:if test="${!empty lastMonthSummary.margin_in}">
                           		  <fmt:formatNumber type="number" value="${lastMonthSummary.margin_in}"  maxFractionDigits="2" minFractionDigits="2"/>
                           		 </c:if>
                            </td>
                            <td>
                             <c:if test="${empty lastMonthSummary.margin_out}">
                           			 0.00
                           		 </c:if>
                           		 <c:if test="${!empty lastMonthSummary.margin_out}">
                           		 <fmt:formatNumber type="number" value="${lastMonthSummary.margin_out }"  maxFractionDigits="2" minFractionDigits="2"/>
                           		 </c:if>
                            </td>
                          </tr>
                      </table>
                  </div>
                  
                  <div class="details-table summary-table2">
                   <c:if test="${evCommission!=null }">
                      <table class="table-hover">
                          <thead>
                              <tr>
                                  <th></th>
                                  <th><spring:message code="ib.ev.commission"></spring:message></th>
                                  <th><spring:message code="ib.ev"></spring:message></th>
                                  <th><spring:message code="ib.quity.change"></spring:message></th>
                                  <th><spring:message code="ib.fix.commission"></spring:message></th>
                                  <th><spring:message code="ib.rebate"></spring:message></th>
                                  <th><spring:message code="ib.deficit"></spring:message></th>
                                  <th><spring:message code="ib.deposit.bonus"></spring:message></th>
                                  <th class="borRnone"><spring:message code="ib.margin.bonus"></spring:message></th>
                              </tr>
                          </thead>
                         
                          <tr>
                            <td><spring:message code="ib.current.month" /></td>
                            <td>${evCommission.ev_commission }</td>
                            <td>${evCommission.ev_percentage }</td>
                           <td>${evCommission.total_equity_change }</td>
                           <td>${evCommission.client_fix_commission }</td>
                           <td>${evCommission.rebate_code }</td>
                           <td>${evCommission.deficit }</td>
                           <td>${evCommission.deposit_bonus }</td>
                           <td>${evCommission.margin_in_bonus }</td>
                           
                          </tr>
                         
                      </table>
                      </c:if>
                  </div>


              </div>
              
                  <h4><i class="fa fa-chevron-right"></i><spring:message code="ib.details"></spring:message></h4>
                   <div id="searchDate" style="display:none"><fmt:formatDate value="${todaySummary.start_date}" pattern="yyyy-MM-dd HH:mm:ss" />,
                    <fmt:formatDate value="${todaySummary.end_date}" pattern="yyyy-MM-dd HH:mm:ss" />,
                     <fmt:formatDate value="${monthSummary.start_date}" pattern="yyyy-MM-dd HH:mm:ss" />,
                      <fmt:formatDate value="${monthSummary.end_date}" pattern="yyyy-MM-dd HH:mm:ss" />,
                      <fmt:formatDate value="${lastMonthSummary.start_date}" pattern="yyyy-MM-dd HH:mm:ss" />,
                       <fmt:formatDate value="${lastMonthSummary.end_date}" pattern="yyyy-MM-dd HH:mm:ss" />
                 </div>
                  <div class="commission-details">
                  <div id="searchDate">
                      <a href="javascript:void(0)" class="btn btn-red" onclick="submitSummary(0);return false" id="dayClick"><spring:message code="ib.commission"></spring:message>(${todaySummary.date_description })</a>
                      <a href="javascript:void(0)" class="btn" onclick="submitSummary(1);return false" id="monthClick"><spring:message code="ib.commission"></spring:message>(${monthSummary.date_description})</a>
                      <a href="javascript:void(0)" class="btn" onclick="submitSummary(2);return false" id="lastmonthClick"><spring:message code="ib.commission"></spring:message>(${lastMonthSummary.date_description})</a>
                   </div>   
                      <input type="hidden" id="todayOrMonth">
                       <p class="errorPrompt"></p>
                       <form action="/ibCommission/getIbClientSummary1.hyml" method="post">
                        <input type="hidden" name="startDate" id="startDate">
                        <input type="hidden" name="endDate" id="endDate">
                       </form>
                     <div class="commission-table ib-perSummary">
		                <ul class="nav nav-tabs">
		                  <li class="active"><a href="#divSummary1" data-toggle="tab">Summary</a></li>
		                  <li><a href="#divSummary2" data-toggle="tab">Client Summary</a></li>
		                </ul>
		                <div class="tab-content ib-tab-content">
		                  <div class="tab-pane fade in active" id="divSummary1">
		                    <dl>
		                      <dd>Commission:0.00</dd>
		                      <dd>Rebate:0.00</dd>
		                      <dd>Total:0.00</dd>
		                    </dl>
                          <div class="details-table commisDetails-table">
                          
                          <table class="table-hover" id="clientSummary">
                              <thead>
                                  <tr>
                                      <th><spring:message code="ib.ib.code"></spring:message></th>
                                      <th><spring:message code="ib.ib"></spring:message> <spring:message code="ib.name"></spring:message></th>
                                      <th><spring:message code="ib.client"></spring:message></th>
                                      <th><spring:message code="ib.total.lot"></spring:message></th>
                                      <th><spring:message code="ib.net.margin.in"></spring:message></th>
                                      <th><spring:message code="ib.pl"></spring:message></th>
                                      <th><spring:message code="ib.commission"></spring:message></th>
                                      <th class="borRnone"><spring:message code="ib.rebate"></spring:message></th>
                                  </tr>
                              </thead>
                              <tbody>
                              <c:forEach items="${summaryBody.ib_client_summaries }" var="summary">
                              <tr onclick="submitClient(${summary.ib_code })">
                              	<td>${summary.ib_code }</td>
                              	<td>${summary.ib_code }</td>
                              	<td>${summary.client_code }</td>
                              	<td>
                              	 <c:if test="${empty summary.total_lot}">
                           			 0.00
                           		 </c:if>
                           		 <c:if test="${!empty summary.total_lot}">
                           		 <fmt:formatNumber type="number" value="${summary.total_lot }"  maxFractionDigits="2" minFractionDigits="2"/>
                           		 </c:if>
                             </td>
                             <td>
                              	 <c:if test="${empty summary.net_margin_in}">
                           			 0.00
                           		 </c:if>
                           		 <c:if test="${!empty summary.net_margin_in}">
                           		 <fmt:formatNumber type="number" value="${summary.net_margin_in }"  maxFractionDigits="2" minFractionDigits="2"/>
                           		 </c:if>
                             </td>
                             <td>
                              	 <c:if test="${empty summary.pl}">
                           			 0.00
                           		 </c:if>
                           		 <c:if test="${!empty summary.pl}">
                           		 <fmt:formatNumber type="number" value="${summary.pl }"  maxFractionDigits="2" minFractionDigits="2"/>
                           		 </c:if>
                             </td>
                             <td>
                              	 <c:if test="${empty summary.commission}">
                           			 0.00
                           		 </c:if>
                           		 <c:if test="${!empty summary.commission}">
                           		 <fmt:formatNumber type="number" value="${summary.commission }"  maxFractionDigits="2" minFractionDigits="2"/>
                           		 </c:if>
                             </td>
                             <td>
                              	 <c:if test="${empty summary.rebate}">
                           			 0.00
                           		 </c:if>
                           		 <c:if test="${!empty summary.rebate}">
                           		 <fmt:formatNumber type="number" value="${summary.rebate }"  maxFractionDigits="2" minFractionDigits="2"/>
                           		 </c:if>
                             </td>
                              </tr>
                              </c:forEach>
                              
                              </tbody>
                          </table>
						<!-- <div id="pager" class="pager" style="display:none">
						<form>
						   <input type="button" value="首页"  class="first"> 
						     <input type="button" value="上一页"  class="prev"> 
						     <input type="text" class="pagedisplay"/>
						       <input type="button" value="下一页"  class="next"> 
						         <input type="button" value="尾页"  class="last"> 
							<select class="pagesize">
								<option selected="selected"  value="10">10</option>
								<option value="20">20</option>
								<option value="30">30</option>
								<option  value="40">40</option>
							</select>
						</form>
					</div> -->
					<!-- end page -->
					 </div>  

                      </div>
                      <!-- table 2 -->
                      <div class="tab-pane fade" id="divSummary2">
                    <dl>
                      <dd>IB Code:1002</dd>
                      <dd>Commission:0.00</dd>
                      <dd>Rebate:0.00</dd>
                      <dd>Total:0.00</dd>
                    </dl>
                    <div class="details-table commission-summary-table">
                      <table class="table-hover" id="clientSummaryDetail">
                        <thead>
                          <tr>
                            <th>Client</th>
                            <th>Total Lot</th>
                            <th>Net Margin In</th>
                            <th>P/L</th>
                            <th>Commission</th>
                            <th class="borRnone">Rebate</th>
                          </tr>
                        </thead>
                        <tr id="315854" onclick="getClientDetailsSummary(this.id)">
                          <td>315854</td>
                          <td>0.4</td>
                          <td>0.00</td>
                          <td>212.9</td>
                          <td>8</td>
                          <td>6.7</td>
                        </tr>
                        <tr id="316192" onclick="getClientDetailsSummary(this.id)">
                          <td>316192</td>
                          <td>0.8</td>
                          <td>0.00</td>
                          <td>39.4</td>
                          <td>16</td>
                          <td>10.4</td>
                        </tr>
                       
                      </table>
                    </div>
                  </div>
                      	<!-- table 2 end -->
                      </div>  
							
                      </div>
                      <div class="commission-list">
                         <!-- <h4>Client Deatils</h4>
                       <ul>
                          <li>Account:<span id="clientAccount"></span></li>
                          <li>Name:<span id="clientName"></span></li>
                        </ul> -->
                         <div class="commissionMax-table">
                          <table class="table-hover">
                           <thead>
		                      <tr>
		                        <th colspan="2">Client Information</th>
		                      </tr>
		                    </thead>
                             
                                  <tr>
                                      <td><spring:message code="ib.client.code"></spring:message></td>
                                      <td><span id="clientAccount"></span></td>
                                  </tr>
                              
                              <tr>
                                <td><spring:message code="ib.name"></spring:message></td>
                                <td><span id="clientName"></span></td>
                              </tr>
                               <tr>
                                <td><spring:message code="ib.phone"></spring:message></td>
                                <td><span id="clientPhone"></span></td>
                              </tr>
                          </table>

                      </div>
                        <div class="details-table commission-ClientDeatils-table">
                          <table class="table-hover" id="ibclientdetails">
                              <thead>
                                  <tr>
                                      <th><spring:message code="ib.date"></spring:message></th>
                                      <th class="borRnone"><spring:message code="ib.net.margin.in"></spring:message></th>
                                  </tr>
                              </thead>
                              
                          </table>
					
                      </div>
                      </div><!-- commission list  -->
                  </div>


          </div>
        </div>
      </div>
    </div>
  </div>
</div>
 <jsp:include page="${langType}/include/foot.html"></jsp:include>
  <script src="/js/commom/dealResult.js"></script>  
<script src="/js/tablesorter/jquery.tablesorter.min.js"></script> 
<script type="text/javascript" src="/js/tablesorter/jquery.tablesorter.pager.js"></script>
 <!-- <script src="/js/commission/clientSummaryPeriod.js"></script>   -->
<script src="/js/adaptability.js"></script>
<script src="/js/bootstrap.min.js"></script>
<%@ include file="/WEB-INF/views/premission.jsp" %> 

</body>
</html>
