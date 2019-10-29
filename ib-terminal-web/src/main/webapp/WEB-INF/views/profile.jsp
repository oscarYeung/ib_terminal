<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>  
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page isELIgnored="false" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
System.out.println("errormessage  :"+request.getAttribute("errormessage")+"getLocale :"+response.getLocale());
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
<script src="/js/jquery-1.9.1.min.js"></script> 
<script type="text/javascript" src="/js/jquery/jquery.validate.min.js"></script>

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
          <li><i class="fa fa-home"></i> <a href="/index.hyml"><spring:message code="ib.home"></spring:message></a><i class="fa fa-angle-right"></i><spring:message code="ib.account"></spring:message><i class="fa fa-angle-right"></i><spring:message code="ib.account.profile"></spring:message> </li>
        </ul>
        <div class="container-fluid">
          <h2 class="title"><spring:message code="ib.account.profile"></spring:message></h2>
          <p class="line"></p>
          <div class="profile">
              <form:form role="form"  action="doProfile.hyml" method="post" id="profileForm" modelAttribute="profilemodel">
               <input name="token"   type="hidden" value="${token}"/>
                  <div class="form-group">
                  	 <label><spring:message code="ib.ib.code"></spring:message>:</label>
                     <form:input path="ibCode" class="form-control" readonly="true"/>
                     <label><spring:message code="ib.first.name"></spring:message>:</label>
                      <form:input  type="text" class="form-control"  path="firstName" readonly="true" />
                       <label><spring:message code="ib.last.name"></spring:message>:</label>
                     <form:input  type="text" class="form-control"  path="lastName"  readonly="true"  />
                      <label><spring:message code="ib.chinese.name"></spring:message>:</label>
                     <form:input  type="text" class="form-control"  path="chineseName"    />
                      <label><spring:message code="ib.sex"></spring:message>:</label>
                      <form:select path="sex" class="form-control" data-style="btn-info">
                      	<form:option value="m"><spring:message code="ib.male"></spring:message></form:option>
                      	<form:option value="f"><spring:message code="ib.female"></spring:message></form:option>
                      </form:select>
                      
                    
                      <label><spring:message code="ib.mobile.number"></spring:message>:</label>
                      <form:input  type="text" class="form-control required"  path="mobile"  />
                      <label><spring:message code="ib.email"></spring:message>:</label>
                      <form:input  type="text" class=" form-control required email"  path="email"  />
                      
					 <label><spring:message code="ib.country"></spring:message>:</label>
						 <form:select path="country"  class="form-control" data-style="btn-info">    
							  <option><spring:message code="ib.select.area"></spring:message></option>  
							 
							  	<c:choose >
							  	 <c:when test="${langType=='/chinese' }">
							  	 			<option value="AL">阿尔巴尼亚</option>
                                	 
                                    <option value="DZ">阿尔及利亚</option>
                                	 
                                    <option value="AF">阿富汗</option>
                                	 
                                    <option value="AR">阿根廷</option>
                                	 
                                    <option value="AE">阿拉伯联合酋长国</option>
                                	 
                                    <option value="AW">阿鲁巴</option>
                                	 
                                    <option value="OM">阿曼</option>
                                	 
                                    <option value="AZ">阿塞拜疆</option>
                                	 
                                    <option value="EG">埃及</option>
                                	 
                                    <option value="ET">埃塞俄比亚</option>
                                	 
                                    <option value="IE">爱尔兰</option>
                                	 
                                    <option value="EE">爱沙尼亚</option>
                                	 
                                    <option value="AD">安道尔</option>
                                	 
                                    <option value="AO">安哥拉</option>
                                	 
                                    <option value="AI">安圭拉岛</option>
                                	 
                                    <option value="AG">安提瓜和巴布达</option>
                                	 
                                    <option value="AT">奥地利</option>
                                	 
                                    <option value="AU">澳大利亚</option>
                                	 
                                    <option value="MO">澳门特别行政区</option>
                                	 
                                    <option value="BB">巴巴多斯</option>
                                	 
                                    <option value="PG">巴布亚新几内亚</option>
                                	 
                                    <option value="BS">巴哈马</option>
                                	 
                                    <option value="PK">巴基斯坦</option>
                                	 
                                    <option value="PY">巴拉圭</option>
                                	 
                                    <option value="PS">巴勒斯坦当局</option>
                                	 
                                    <option value="BH">巴林</option>
                                	 
                                    <option value="PA">巴拿马</option>
                                	 
                                    <option value="BR">巴西</option>
                                	 
                                    <option value="BY">白俄罗斯</option>
                                	 
                                    <option value="BM">百慕大群岛</option>
                                	 
                                    <option value="BG">保加利亚</option>
                                	 
                                    <option value="MP">北马里亚纳群岛</option>
                                	 
                                    <option value="BJ">贝宁</option>
                                	 
                                    <option value="BE">比利时</option>
                                	 
                                    <option value="IS">冰岛</option>
                                	 
                                    <option value="PR">波多黎各</option>
                                	 
                                    <option value="PL">波兰</option>
                                	 
                                    <option value="BA">波斯尼亚和黑塞哥维那</option>
                                	 
                                    <option value="BO">玻利维亚</option>
                                	 
                                    <option value="BZ">伯利兹</option>
                                	 
                                    <option value="BW">博茨瓦纳</option>
                                	 
                                    <option value="BT">不丹</option>
                                	 
                                    <option value="IO">不列颠印度洋属土</option>
                                	 
                                    <option value="BF">布基纳法索</option>
                                	 
                                    <option value="BI">布隆迪</option>
                                	 
                                    <option value="BV">布韦岛</option>
                                	 
                                    <option value="KP">朝鲜</option>
                                	 
                                    <option value="GQ">赤道几内亚</option>
                                	 
                                    <option value="DK">丹麦</option>
                                	 
                                    <option value="DE">德国</option>
                                	 
                                    <option value="TP">东帝汶</option>
                                	 
                                    <option value="TG">多哥</option>
                                	 
                                    <option value="DM">多米尼加</option>
                                	 
                                    <option value="DO">多米尼加共和国</option>
                                	 
                                    <option value="RU">俄罗斯</option>
                                	 
                                    <option value="EC">厄瓜多尔</option>
                                	 
                                    <option value="ER">厄立特里亚</option>
                                	 
                                    <option value="FR">法国</option>
                                	 
                                    <option value="FO">法罗群岛</option>
                                	 
                                    <option value="PF">法属波利尼西亚</option>
                                	 
                                    <option value="GF">法属圭亚那</option>
                                	 
                                    <option value="TF">法属南极地区</option>
                                	 
                                    <option value="VA">梵蒂冈城</option>
                                	 
                                    <option value="PH">菲律宾</option>
                                	 
                                    <option value="FJ">斐济群岛</option>
                                	 
                                    <option value="FI">芬兰</option>
                                	 
                                    <option value="CV">佛得角</option>
                                	 
                                    <option value="FK">福克兰群岛（马尔维纳斯群岛）</option>
                                	 
                                    <option value="GM">冈比亚</option>
                                	 
                                    <option value="CG">刚果</option>
                                	 
                                    <option value="ZR">刚果民主共和国</option>
                                	 
                                    <option value="CO">哥伦比亚</option>
                                	 
                                    <option value="CR">哥斯达黎加</option>
                                	 
                                    <option value="GD">格林纳达</option>
                                	 
                                    <option value="GL">格陵兰</option>
                                	 
                                    <option value="CU">古巴</option>
                                	 
                                    <option value="GP">瓜德罗普岛</option>
                                	 
                                    <option value="GU">关岛</option>
                                	 
                                    <option value="GY">圭亚那</option>
                                	 
                                    <option value="KZ">哈萨克斯坦</option>
                                	 
                                    <option value="HT">海地</option>
                                	 
                                    <option value="KR">韩国</option>
                                	 
                                    <option value="NL">荷兰</option>
                                	 
                                    <option value="AN">荷属安的列斯群岛</option>
                                	 
                                    <option value="HM">赫德和麦克唐纳群岛</option>
                                	 
                                    <option value="ME">黑山</option>
                                	 
                                    <option value="HN">洪都拉斯</option>
                                	 
                                    <option value="KI">基里巴斯</option>
                                	 
                                    <option value="DJ">吉布提</option>
                                	 
                                    <option value="KG">吉尔吉斯斯坦</option>
                                	 
                                    <option value="GN">几内亚</option>
                                	 
                                    <option value="GW">几内亚比绍</option>
                                	 
                                    <option value="CA">加拿大</option>
                                	 
                                    <option value="GH">加纳</option>
                                	 
                                    <option value="GA">加蓬</option>
                                	 
                                    <option value="KH">柬埔寨</option>
                                	 
                                    <option value="CZ">捷克共和国</option>
                                	 
                                    <option value="ZW">津巴布韦</option>
                                	 
                                    <option value="CM">喀麦隆</option>
                                	 
                                    <option value="QA">卡塔尔</option>
                                	 
                                    <option value="KY">开曼群岛</option>
                                	 
                                    <option value="CC">科科斯群岛（基灵群岛）</option>
                                	 
                                    <option value="KM">科摩罗</option>
                                	 
                                    <option value="CI">科特迪瓦</option>
                                	 
                                    <option value="KW">科威特</option>
                                	 
                                    <option value="HR">克罗地亚</option>
                                	 
                                    <option value="KE">肯尼亚</option>
                                	 
                                    <option value="CK">库克群岛</option>
                                	 
                                    <option value="LV">拉脱维亚</option>
                                	 
                                    <option value="LS">莱索托</option>
                                	 
                                    <option value="LA">老挝</option>
                                	 
                                    <option value="LB">黎巴嫩</option>
                                	 
                                    <option value="LT">立陶宛</option>
                                	 
                                    <option value="LR">利比里亚</option>
                                	 
                                    <option value="LY">利比亚</option>
                                	 
                                    <option value="LI">列支敦士登</option>
                                	 
                                    <option value="RE">留尼汪岛</option>
                                	 
                                    <option value="LU">卢森堡</option>
                                	 
                                    <option value="RW">卢旺达</option>
                                	 
                                    <option value="RO">罗马尼亚</option>
                                	 
                                    <option value="MG">马达加斯加</option>
                                	 
                                    <option value="MV">马尔代夫</option>
                                	 
                                    <option value="MT">马耳他</option>
                                	 
                                    <option value="MW">马拉维</option>
                                	 
                                    <option value="MY">马来西亚</option>
                                	 
                                    <option value="ML">马里</option>
                                	 
                                    <option value="MK">马其顿(前南斯拉夫共和国)</option>
                                	 
                                    <option value="MH">马绍尔群岛</option>
                                	 
                                    <option value="MQ">马提尼克岛</option>
                                	 
                                    <option value="YT">马约特岛</option>
                                	 
                                    <option value="MU">毛里求斯</option>
                                	 
                                    <option value="MR">毛里塔尼亚</option>
                                	 
                                    <option value="AS">美属萨摩亚</option>
                                	 
                                    <option value="MN">蒙古</option>
                                	 
                                    <option value="MS">蒙特塞拉特</option>
                                	 
                                    <option value="BD">孟加拉国</option>
                                	 
                                    <option value="PE">秘鲁</option>
                                	 
                                    <option value="FM">密克罗尼西亚</option>
                                	 
                                    <option value="MM">缅甸</option>
                                	 
                                    <option value="MD">摩尔多瓦</option>
                                	 
                                    <option value="MA">摩洛哥</option>
                                	 
                                    <option value="MC">摩纳哥</option>
                                	 
                                    <option value="MZ">莫桑比克</option>
                                	 
                                    <option value="MX">墨西哥</option>
                                	 
                                    <option value="NA">纳米比亚</option>
                                	 
                                    <option value="ZA">南非</option>
                                	 
                                    <option value="AQ">南极洲</option>
                                	 
                                    <option value="GS">南乔治亚和南桑德威奇群岛</option>
                                	 
                                    <option value="NR">瑙鲁</option>
                                	 
                                    <option value="NP">尼泊尔</option>
                                	 
                                    <option value="NI">尼加拉瓜</option>
                                	 
                                    <option value="NE">尼日尔</option>
                                	 
                                    <option value="NU">纽埃</option>
                                	 
                                    <option value="NO">挪威</option>
                                	 
                                    <option value="NF">诺福克岛</option>
                                	 
                                    <option value="PW">帕劳群岛</option>
                                	 
                                    <option value="PN">皮特克恩群岛</option>
                                	 
                                    <option value="PT">葡萄牙</option>
                                	 
                                    <option value="GE">乔治亚</option>
                                	 
                                    <option value="JP">日本</option>
                                	 
                                    <option value="SE">瑞典</option>
                                	 
                                    <option value="CH">瑞士</option>
                                	 
                                    <option value="SV">萨尔瓦多</option>
                                	 
                                    <option value="WS">萨摩亚</option>
                                	 
                                    <option value="RS">塞尔维亚共和国</option>
                                	 
                                    <option value="SL">塞拉利昂</option>
                                	 
                                    <option value="SN">塞内加尔</option>
                                	 
                                    <option value="CY">塞浦路斯</option>
                                	 
                                    <option value="SC">塞舌尔</option>
                                	 
                                    <option value="SA">沙特阿拉伯</option>
                                	 
                                    <option value="CX">圣诞岛</option>
                                	 
                                    <option value="ST">圣多美和普林西比</option>
                                	 
                                    <option value="SH">圣赫勒拿岛</option>
                                	 
                                    <option value="KN">圣基茨和尼维斯</option>
                                	 
                                    <option value="LC">圣卢西亚</option>
                                	 
                                    <option value="SM">圣马力诺</option>
                                	 
                                    <option value="PM">圣皮埃尔岛和密克隆岛</option>
                                	 
                                    <option value="VC">圣文森特和格林纳丁斯</option>
                                	 
                                    <option value="LK">斯里兰卡</option>
                                	 
                                    <option value="SK">斯洛伐克</option>
                                	 
                                    <option value="SI">斯洛文尼亚</option>
                                	 
                                    <option value="SJ">斯瓦尔巴群岛和扬马延</option>
                                	 
                                    <option value="SZ">斯威士兰</option>
                                	 
                                    <option value="SD">苏丹</option>
                                	 
                                    <option value="SR">苏里南</option>
                                	 
                                    <option value="SB">所罗门群岛</option>
                                	 
                                    <option value="SO">索马里</option>
                                	 
                                    <option value="TJ">塔吉克斯坦</option>
                                	 
                                    <option value="TW">台湾</option>
                                	 
                                    <option value="TH">泰国</option>
                                	 
                                    <option value="TZ">坦桑尼亚</option>
                                	 
                                    <option value="TO">汤加</option>
                                	 
                                    <option value="TC">特克斯群岛和凯科斯群岛</option>
                                	 
                                    <option value="TT">特立尼达和多巴哥</option>
                                	 
                                    <option value="TN">突尼斯</option>
                                	 
                                    <option value="TV">图瓦卢</option>
                                	 
                                    <option value="TR">土耳其</option>
                                	 
                                    <option value="TM">土库曼斯坦</option>
                                	 
                                    <option value="TK">托克劳</option>
                                	 
                                    <option value="WF">瓦利斯群岛和富图纳群岛</option>
                                	 
                                    <option value="VU">瓦努阿图</option>
                                	 
                                    <option value="GT">危地马拉</option>
                                	 
                                    <option value="VI">维尔京群岛</option>
                                	 
                                    <option value="VG">维尔京群岛（英属）</option>
                                	 
                                    <option value="VE">委内瑞拉</option>
                                	 
                                    <option value="BN">文莱</option>
                                	 
                                    <option value="UG">乌干达</option>
                                	 
                                    <option value="UA">乌克兰</option>
                                	 
                                    <option value="UY">乌拉圭</option>
                                	 
                                    <option value="UZ">乌兹别克斯坦</option>
                                	 
                                    <option value="ES">西班牙</option>
                                	 
                                    <option value="GR">希腊</option>
                                	 
                                    <option value="SG">新加坡</option>
                                	 
                                    <option value="NC">新喀里多尼亚</option>
                                	 
                                    <option value="NZ">新西兰</option>
                                	 
                                    <option value="HU">匈牙利</option>
                                	 
                                    <option value="SY">叙利亚</option>
                                	 
                                    <option value="JM">牙买加</option>
                                	 
                                    <option value="AM">亚美尼亚</option>
                                	 
                                    <option value="YE">也门</option>
                                	 
                                    <option value="IQ">伊拉克</option>
                                	 
                                    <option value="IR">伊朗</option>
                                	 
                                    <option value="IL">以色列</option>
                                	 
                                    <option value="IT">意大利</option>
                                	 
                                    <option value="IN">印度</option>
                                	 
                                    <option value="ID">印度尼西亚</option>
                                	 
                                    <option value="GB">英国</option>
                                	 
                                    <option value="JO">约旦</option>
                                	 
                                    <option value="VN">越南</option>
                                	 
                                    <option value="ZM">赞比亚</option>
                                	 
                                    <option value="TD">乍得</option>
                                	 
                                    <option value="GI">直布罗陀</option>
                                	 
                                    <option value="CL">智利</option>
                                	 
                                    <option value="CF">中非共和国</option>
                                	 
                                    <option value="CN" selected="">中国</option>
							  	 </c:when> 
							  	 <c:otherwise>
							  	  <option value="AS">American Samoa</option>  
										<option value="AD">Andorra</option>  
										<option value="AO">Angola</option>  
										
										<option value="AI">Anguilla</option>  
										
										<option value="AQ">Antarctica</option>  
										
										<option value="AG">Antigua and Barbuda</option>  
										
										<option value="AR">Argentina</option>  
										
										<option value="AM">Armenia</option>  
										
										<option value="AW">Aruba</option>  
										
										<option value="AU">Australia</option>  
										
										<option value="AT">Austria</option>  
										
										<option value="AZ">Azerbaijan</option>  
										
										<option value="BS">Bahamas</option>  
										
										<option value="BH">Bahrain</option>  
										
										<option value="BD">Bangladesh</option>  
										
										<option value="BB">Barbados</option>  
										
										<option value="BY">Belarus</option>  
										
										<option value="BE">Belgium</option>  
										
										<option value="BZ">Belize</option>  
										
										<option value="BJ">Benin</option>  
										
										<option value="BM">Bermuda</option>  
										
										<option value="BT">Bhutan</option>  
										
										<option value="BO">Bolivia</option>  
										
										<option value="BA">Bosnia and Herzegovina</option>  
										
										<option value="BW">Botswana</option>  
										
										<option value="BV">Bouvet	Island</option>  
										
										<option value="BR">Brazil</option>  
										
										<option value="IO">British Indian Ocean Territory</option>  
										
										<option value="BN">Brunei</option>  
										
										<option value="BG">Bulgaria</option>  
										
										<option value="BF">Burkina Faso</option>  
										
										<option value="BI">Burundi</option>  
										
										<option value="KH">Cambodia</option>  
										
										<option value="CM">Cameroon</option>  
										
										<option value="CA">Canada</option>  
										
										<option value="CV">Cape Verde</option>  
										
										<option value="KY">Cayman Islands</option>  
										
										<option value="CF">Central African Republic</option>  
										
										<option value="TD">Chad</option>  
										
										<option value="CL">Chile</option>  
										
										<option value="CN">China</option>  
										
										<option value="CX">Christmas Island</option>  
										
										<option value="CC">Cocos (Keeling)	Islands</option>  
										
										<option value="CO">Colombia</option>  
										
										<option value="KM">Comoros</option>  
										
										<option value="CG">Congo</option>  
										
										<option value="CK">Cook Islands</option>  
										
										<option value="CR">Costa Rica</option>  
										
										<option value="CI">Cote	D'Ivoire</option>  
										
										<option value="HR">Croatia</option>  
										
										<option value="CU">Cuba</option>  
										
										<option value="CY">Cyprus</option>  
										
										<option value="CZ">Czech	Republic</option>  
										
										<option value="DK">Denmark</option>  
										
										<option value="DJ">Djibouti</option>  
										
										<option value="DM">Dominica</option>  
										
										<option value="DO">Dominican Republic</option>  
										
										<option value="EC">Ecuador</option>  
										
										<option value="EG">Egypt</option>  
										
										<option value="SV">El	Salvador</option>  
										
										<option value="GQ">Equatorial Guinea</option>  
										
										<option value="ER">Eritrea</option>  
										
										<option value="EE">Estonia</option>  
										
										<option value="ET">Ethiopia</option>  
										
										<option value="FK">Falkland Islands (Islas	Malvin</option>  
										
										<option value="FO">Faroe Islands</option>  
										
										<option value="FJ">Fiji Islands</option>  
										
										<option value="FI">Finland</option>  
										
										<option value="MK">Former Yugoslav Republic of Ma</option>  
										
										<option value="FR">France</option>  
										
										<option value="TF">French	Southern and Antarctic </option>  
										
										<option value="GF">French Guiana</option>  
										
										<option value="PF">French Polynesia</option>  
										
										<option value="GA">Gabon</option>  
										
										<option value="GM">Gambia</option>  
										
										<option value="GE">Georgia</option>  
										
										<option value="DE">Germany</option>  
										
										<option value="GH">Ghana</option>  
										
										<option value="GI">Gibraltar</option>  
										
										<option value="GR">Greece</option>  
										
										<option value="GL">Greenland</option>  
										
										<option value="GD">Grenada</option>  
										
										<option value="GP">Guadeloupe</option>  
										
										<option value="GU">Guam</option>  
										
										<option value="GT">Guatemala</option>  
										
										<option value="GN">Guinea</option>  
										
										<option value="GW">Guinea-Bissau</option>  
										
										<option value="GY">Guyana</option>  
										
										<option value="HT">Haiti</option>  
										
										<option value="HM">Heard Island and	McDonald Isla</option>  
										
										<option value="HN">Honduras</option>  
										
										<option value="HU">Hungary</option>  
										
										<option value="IS">Iceland</option>  
										
										<option value="IN">India</option>  
										
										<option value="ID">Indonesia</option>  
										
										<option value="IR">Iran</option>  
										
										<option value="IQ">Iraq</option>  
										
										<option value="IE">Ireland</option>  
										
										<option value="IL">Israel</option>  
										
										<option value="IT">Italy</option>  
										
										<option value="JM">Jamaica</option>  
										
										<option value="JP">Japan</option>  
										
										<option value="JO">Jordan</option>  
										
										<option value="KZ">Kazakhstan</option>  
										
										<option value="KE">Kenya</option>  
										
										<option value="KI">Kiribati</option>  
										
										<option value="KR">Korea</option>  
										
										<option value="KW">Kuwait</option>  
										
										<option value="KG">Kyrgyzstan</option>  
										
										<option value="LA">Laos</option>  
										
										<option value="LV">Latvia</option>  
										
										<option value="LB">Lebanon</option>  
										
										<option value="LS">Lesotho</option>  
										
										<option value="LR">Liberia</option>  
										
										<option value="LY">Libya</option>  
										
										<option value="LI">Liechtenstein</option>  
										
										<option value="LT">Lithuania</option>  
										
										<option value="LU">Luxembourg</option>  
										
										<option value="MO">Macao	SAR</option>  
										
										<option value="MG">Madagascar</option>  
										
										<option value="MW">Malawi</option>  
										
										<option value="MY">Malaysia</option>  
										
										<option value="MV">Maldives</option>  
										
										<option value="ML">Mali</option>  
										
										<option value="MT">Malta</option>  
										
										<option value="MH">Marshall Islands</option>  
										
										<option value="MQ">Martinique</option>  
										
										<option value="MR">Mauritania</option>  
										
										<option value="MU">Mauritius</option>  
										
										<option value="YT">Mayotte</option>  
										
										<option value="MX">Mexico</option>  
										
										<option value="FM">Micronesia</option>  
										
										<option value="MD">Moldova</option>  
										
										<option value="MC">Monaco</option>  
										
										<option value="MN">Mongolia</option>  
										
										<option value="ME">Montenegro</option>  
										
										<option value="MS">Montserrat</option>  
										
										<option value="MA">Morocco</option>  
										
										<option value="MZ">Mozambique</option>  
										
										<option value="MM">Myanmar</option>  
										
										<option value="NA">Namibia</option>  
										
										<option value="NR">Nauru</option>  
										
										<option value="NP">Nepal</option>  
										
										<option value="AN">Netherlands	Antilles</option>  
										
										<option value="NL">Netherlands</option>  
										
										<option value="NZ">New	Zealand</option>  
										
										<option value="NC">New Caledonia</option>  
										
										<option value="NI">Nicaragua</option>  
										
										<option value="NE">Niger</option>  
										
										<option value="NU">Niue</option>  
										
										<option value="NF">Norfolk Island</option>  
										
										<option value="KP">North Korea</option>  
										
										<option value="MP">Northern Mariana	Islands</option>  
										
										<option value="NO">Norway</option>  
										
										<option value="OM">Oman</option>  
										
										<option value="PK">Pakistan</option>  
										
										<option value="PW">Palau</option>  
										
										<option value="PS">Palestinian Authority</option>  
										
										<option value="PA">Panama</option>  
										
										<option value="PG">Papua New	Guinea</option>  
										
										<option value="PY">Paraguay</option>  
										
										<option value="PE">Peru</option>  
										
										<option value="PH">Philippines</option>  
										
										<option value="PN">Pitcairn Islands</option>  
										
										<option value="PL">Poland</option>  
										
										<option value="PT">Portugal</option>  
										
										<option value="PR">Puerto	Rico</option>  
										
										<option value="QA">Qatar</option>  
										
										<option value="RS">Republic of Serbia</option>  
										
										<option value="RE">Reunion</option>  
										
										<option value="RO">Romania</option>  
										
										<option value="RU">Russia</option>  
										
										<option value="RW">Rwanda</option>  
										
										<option value="WS">Samoa</option>  
										
										<option value="SM">San Marino</option>  
										
										<option value="ST">Sao Tome and	Principe</option>  
										
										<option value="SA">Saudi Arabia</option>  
										
										<option value="SN">Senegal</option>  
										
										<option value="SC">Seychelles</option>  
										
										<option value="SL">Sierra Leone</option>  
										
										<option value="SG">Singapore</option>  
										
										<option value="SK">Slovakia</option>  
										
										<option value="SI">Slovenia</option>  
										
										<option value="SB">Solomon	Islands</option>  
										
										<option value="SO">Somalia</option>  
										
										<option value="ZA">South Africa</option>  
										
										<option value="GS">South Georgia South Sandwich I</option>  
										
										<option value="ES">Spain</option>  
										
										<option value="LK">Sri Lanka</option>  
										
										<option value="SH">St. Helena</option>  
										
										<option value="KN">St. Kitts and Nevis</option>  
										
										<option value="LC">St. Lucia</option>  
										
										<option value="VC">St. Vincent and the Grenadines</option>  
										
										<option value="PM">St.Pierre and Miquelon</option>  
										
										<option value="SD">Sudan</option>  
										
										<option value="SR">Suriname</option>  
										
										<option value="SJ">Svalbard	and Jan Mayen</option>  
										
										<option value="SZ">Swaziland</option>  
										
										<option value="SE">Sweden</option>  
										
										<option value="CH">Switzerland</option>  
										
										<option value="SY">Syria</option>  
										
										<option value="TW">Taiwan</option>  
										
										<option value="TJ">Tajikistan</option>  
										
										<option value="TZ">Tanzania</option>  
										
										<option value="TH">Thailand</option>  
										
										<option value="TP">Timor-Leste</option>  
										
										<option value="TG">Togo</option>  
										
										<option value="TK">Tokelau</option>  
										
										<option value="TO">Tonga</option>  
										
										<option value="TT">Trinidad and Tobago</option>  
										
										<option value="TN">Tunisia</option>  
										
										<option value="TR">Turkey</option>  
										
										<option value="TM">Turkmenistan</option>  
										
										<option value="TC">Turks	and Caicos Islands</option>  
										
										<option value="TV">Tuvalu</option>  
										
										<option value="UG">Uganda</option>  
										
										<option value="UA">Ukraine</option>  
										
										<option value="AE">United Arab Emirates</option>  
										
										<option value="GB">United Kingdom</option>  
										
										<option value="UY">Uruguay</option>  
										
										<option value="UZ">Uzbekistan</option>  
										
										<option value="VU">Vanuatu</option>  
										
										<option value="VA">Vatican City</option>  
										
										<option value="VE">Venezuela</option>  
										
										<option value="VN">Vietnam</option>  
										
										<option value="VI">Virgin	Islands</option>  
										
										<option value="VG">Virgin Islands(British)</option>  
										
										<option value="WF">Wallis and Futuna</option>  
										
										<option value="YE">Yemen</option>  
										
										<option value="ZR">Zaire</option>  
										
										<option value="ZM">Zambia</option>  
										
										<option value="ZW">Zimbabwe</option> 
							  	 </c:otherwise>
							  	</c:choose>
							  	
							  
							          
		                    
							
                      	 </form:select>
                      <label><spring:message code="ib.address"></spring:message>:</label>
                      <form:input  type="text" class=" form-control required"  path="address"  />
                      <!--   <label>Birthday:</label>
                      <input class="form-control"  type="text"  >
                    <label>MT4 Account:</label>
                      <input class="form-control"  type="text"  placeholder="Ture">
                       <label>Parent IB Code:</label>
                      <input class="form-control"  type="text" placeholder="False" disabled>-->
                      <a href="javascript:void(0)" class="btn btn-red" onclick="updateProfile();return false;"  ><spring:message code="ib.update"></spring:message></a>
                      <a href="/index.hyml" class="btn"><spring:message code="ib.cancle"></spring:message></a>
                      <p class="errorPrompt"><form:errors path="*"></form:errors> 
                   
             		  <%   if(null!=request.getAttribute("errormessage")&&!"".equals(request.getAttribute("errormessage"))){ %>
             		  	<spring:message code="${errormessage}"></spring:message>
             		  <%} %>
                       </p>
                      
              </div>
              </form:form>
              
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<script type="text/javascript">
  var selectCountr="${profilemodel.country}";
  $("#country").val(selectCountr);
 
</script>
<script type="text/javascript" src="/js${langType}/reg/profileValidate.js"></script>
<jsp:include page="${langType}/include/foot.html"></jsp:include>
<script src="js/adaptability.js"></script>
<script src="js/bootstrap.min.js"></script>
<%@ include file="/WEB-INF/views/premission.jsp" %>
</body>
</html>