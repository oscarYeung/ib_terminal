function trim(sStr){
	return sStr.replace(/(^\s*)|(\s*$)/g,"");
} 
function parseNum(num){  
	    var reg=/(?=(?!\b)(\d{3})+$)/g;  
	   
	    return (num.toFixed(2) + '').replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');
	   
	}
 
function dealResultKey(errorMap){
	for(var str in errorMap ){
		if(str==10015||str==10033){
			window.location.href="/login.hyml";
			return ;
		}
		$(".errorPrompt").html(errorMap[str]);
	}
}

function dealResultLoginOut(errorMap){
	for(var str in errorMap ){
		if(str==10015||str==10033){
			window.location.href="/login.hyml";
			return ;
		}
	}
	
}
function dealResult(errorInfo,json){
	var errorInfo=json.split(",");
	if(10015==errorInfo[0]||10033==errorInfo[0]){
		window.location.href="/login.hyml";
		return ;
	}
	return errorInfo[1];
}

function clearTableTr(tableId){
	$("#"+tableId+" tr").each(function (i){
		if(i>0){
			$(this).remove();
		}
	});
}
function getQueryStrExp(name) {    
    var reg = new RegExp("(^|\\?|&)"+ name +"=([^&]*)(\\s|&|$)", "i");   
     if (reg.test(location.href)) {
     		return unescape(RegExp.$2.replace(/\+/g, " ")); 
     }
     return ""; 
 }

//new 
function submitSummary(period){
	var arraydate=$.trim($("#searchDate").html());
	if(null==arraydate){
		mycars[0]=startDate;
		mycars[1]=endDate;
	}else{
		dateStrs=arraydate.split(",");
	}
	
	if(period==1){
		$("#dayClick").removeClass("btn-red");
		$("#monthClick").addClass("btn-red");
		$("#lastmonthClick").removeClass("btn-red");
		startDate=$.trim(dateStrs[2]);
		endDate=$.trim(dateStrs[3]);
		
	}else if(period==2){
		$("#dayClick").removeClass("btn-red");
		$("#monthClick").removeClass("btn-red");
		$("#lastmonthClick").addClass("btn-red");
		startDate=$.trim(dateStrs[4]);
		endDate=$.trim(dateStrs[5]);
		
	}else{
		$("#dayClick").addClass("btn-red");
		$("#lastmonthClick").removeClass("btn-red");
		$("#monthClick").removeClass("btn-red");
		startDate=$.trim(dateStrs[0]);
		endDate=$.trim(dateStrs[1]);
		
	}
	$("#startDate").val(startDate);
	$("#endDate").val(endDate);
	document.forms[0].submit();
}