function marginOutSubmit(){
	var startDate=$.trim($("#startDate").val());
	var endDate=$.trim($("#endDate").val());
	var clientCode=$.trim($("#clientCode").val());
	
	if(startDate.length==0){
		alert($("#m_start").html());
		return false;
	}
	if(endDate.length==0){
		alert($("#m_end").html());
		return false;
	}
	if(clientCode.length==0){
		alert($("#m_clientcode").html());
		return false;
	} 
	//alert($("#clientCode").val()+"  "+$(".bomb_con_style dd").eq(0).text());
	if($("#clientCode").val()==$(".bomb_con_style dd").eq(0).text()){
		$("#clientCode").val("*");
	}
	$("#marginOutForm").submit();
}

$(document).ready(function(){
	
	$("#sumMargin1").html($("#sumMargin").html());
	$("#sumMarout1").html($("#sumMarout").html());
	$("#sumMargInOut1").html($("#sumMargInOut").html());
});