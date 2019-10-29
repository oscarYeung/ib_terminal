<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.henyep.ib.terminal.api.dto.response.ibcommission.IbAccountModel"%>
<%@page import="com.henyep.ib.terminal.utils.ConstantFields"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
System.out.println("local: "+response.getLocale());
if(null!=request.getSession().getAttribute(ConstantFields.IB_USERNAME)){
	
}
//request.getSession().setAttribute(ConstantFields.CLIENT_ACCOUNT_MODEL,ibBody.getIbClientAccountModels());
//request.getSession().setAttribute(ConstantFields.BALANCE_MODEL,ibBody.getIbMonthBalanceModel());
%>
<div id="left_info">
    <div id="boxa">
      <div id="arrow" class="active"></div>
      <div id="client_info">
        <div class="client_name">
          <h3><spring:message code="ib.welcome"></spring:message></h3>
          <span><%=request.getSession().getAttribute(ConstantFields.IB_USERNAME) %></span> <em>(<spring:message code="ib.ib"></spring:message>#
          <%=request.getSession().getAttribute(ConstantFields.IB_CODE) %>
          )</em> </div>
      </div>
      <div class="items">
        <div class="left-list">
          <h4><spring:message code="ib.current.month" /></h4>
          <ul>
            <li><spring:message code="ib.commssion"></spring:message>(${balanceModel.currency}) <span>
            <c:choose>
            <c:when test="${balanceModel.commissions==null }">
            0.00
            </c:when>
            <c:otherwise>
             <fmt:formatNumber type="number" value=" ${balanceModel.commissions }"  maxFractionDigits="2" minFractionDigits="2"/> 
            </c:otherwise>
            </c:choose>
           </span></li>
            <li><spring:message code="ib.rebates"></spring:message>(${balanceModel.currency}) <span>
             <c:choose>
            <c:when test="${balanceModel.rebates==null }">
            0.00
            </c:when>
            <c:otherwise>
              <fmt:formatNumber type="number" value="${balanceModel.rebates }"  maxFractionDigits="2" minFractionDigits="2"/> 
            </c:otherwise>
            </c:choose>
            
            </span></li>
            
          </ul>
        </div>
      </div>
      <div class="items">
        <h3 class="acc_amount"><a id="arrow_drop" class="up"><spring:message code="ib.account"></spring:message></a> </h3>
        <div id="accounts_deteil">
          <div class="left-list divider">
            <h4><spring:message code="ib.ib.account"></spring:message></h4>
            <ul>
              <li>${accountModel.ib_code} (${accountModel.currency})<span>
              
               <c:choose>
		            <c:when test="${accountModel.account_balance==null}">
		           		 0.00
		            </c:when>
		            <c:otherwise>
		            <fmt:formatNumber type="number" value="${accountModel.account_balance}"  maxFractionDigits="2" minFractionDigits="2"/>
		            </c:otherwise>
           	 </c:choose>
              </span></li>
            </ul>
          </div> 
          <div class="left-list divider" >
            <h4><spring:message code="ib.client.account"></spring:message></h4>
            <h5><spring:message code="ib.mt4"></spring:message></h5>
            <ul id="mt4_ul">
            <c:forEach items="${ClientAccountModels }" var="client" >
            <c:if test="${client.trading_platform=='MT4' }">
              <li><a href="/client/ClientProfile.hyml?clientCode=${client.client_code }">${client.client_code }</a> (${client.currency })<span>
               <c:choose>
		            <c:when test="${client.balance==null }">
		           		 0.00
		            </c:when>
		            <c:otherwise>
		           		 <fmt:formatNumber type="number" value=" ${client.balance }"  maxFractionDigits="2" minFractionDigits="2"/>
		            </c:otherwise>
           	 </c:choose>
              </span></li>
              </c:if>
            </c:forEach>
            </ul>
          </div>
          <div class="left-list divider" >
            <h4><spring:message code="ib.star"></spring:message></h4>
            <ul id="star_ul">
              <c:forEach items="${ClientAccountModels }" var="client" >
            <c:if test="${client.trading_platform=='STAR' }">
              <li><a href="/client/ClientProfile.hyml?clientCode=${client.client_code }">${client.client_code } </a>
             <c:if test="${langType=='/chinese'}">
				(${client.currency })
			</c:if>
              <span>
               <c:choose>
		            <c:when test=" ${client.balance==null }">
		           		 0.00
		            </c:when>
		            <c:otherwise>
		           		<fmt:formatNumber type="number" value="${client.balance }"  maxFractionDigits="2" minFractionDigits="2"/> 
		            </c:otherwise>
           	 </c:choose>
             </span></li>
              </c:if>
            </c:forEach>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
  <script type="text/javascript">
  		
  		if($("#mt4_ul li").length==0){
  			$("#mt4_ul ").css("display","none");
  		}
  		if($("#star_ul li").length==0){
  			$("#star_ul ").parent().css("display","none");
  		}
  </script>