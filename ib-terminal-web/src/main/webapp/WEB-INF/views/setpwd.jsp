<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<
<%
String flag=(String)session.getAttribute("flag");
 %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>忘记密码</title>
<link href="/css/web.css" rel="stylesheet" type="text/css" />
<script src="/js/select.js" language="javascript"></script>
<script type="text/javascript" src="/js/jquery.menu.js"></script>


</head>

<script type="text/javascript" src="/js/mailvalidator_zh_CN.js"></script>

<script language="javaScript">


    function login(){
		
		if(isNotEmpty(document.getElementsByName("email")[0])){
	    	alert("<s:text name="alert.email.desc.displayName"/>");
	    	document.getElementsByName("email")[0].value="";
			document.getElementsByName("email")[0].focus();
	  		return false;			
		}
		
		if(!checkEmail(document.getElementById("inputEmail"),"Email","true")){
			document.getElementById("inputEmail").focus();
			return false;			
		}
		
		if(isNotEmpty(document.getElementsByName("username")[0])){
	    	alert("<s:text name="alert.username.desc.displayName"/>");
	    	document.getElementsByName("username")[0].value="";
			document.getElementsByName("username")[0].focus();
	  		return false;			
		}
		
	    document.forms["dosetpwd"].submit();
	
	}	

	
	function isNotEmpty(elem) {
		var str=elem.value;
		if(str==null || str=="")
		{ 
			return true;
		}
	}
	

</script>


<body>

<div class="here"><ul><em class="home"><a href="/index.hyml"></a></em><em></em><cite>
<a href="/${lanname}/login/login.hyml"><s:text name="hyinvestment.login.button.login"></s:text></a></cite><em></em><cite>
<s:text name="Hymarkest.passWord_SetPassWord.displayName"/></cite></ul>
</div>
<div id="webmid">
    <div class="midright">

	</div>
    <div class="midleft">
      <dl class="title_line">
<s:text name="Hymarkest.passWord_SetPassWord.displayName"/>
<h5><s:text name="hyinvestment.getpassword.note"/></h5></dl>
	  <div class="neirong">
     
	 <div class="regBox">
	 <form  name="dosetpwd" action="dosetpwd.hyml" method="post" onsubmit="login();return false;">
	   <p>
							<label>邮箱 </label>
							<input name="email"  type="text"  class="txt"  size="32" id="inputEmail"/>
		</p>
						
						<p>
							<label>用户名</label>
						
							<input name="username" type="text" class="txt" size="32"/>
						</p>						
		  <p class="but"><a class="sblue" href="#" onclick="login()"><em>提交</em></a></p>
		  
		   	 <% if(null!=flag && flag.equals("1")){  %>
                   <p >
                    <font color="red">邮箱或用户名错误.</font>
					</p>
                                  
                   <%
                     }
                  %>
             </form>
        </div>
	  </div>
  </div>
</div>
<div id="foot">

</div>
</body>
</html>
