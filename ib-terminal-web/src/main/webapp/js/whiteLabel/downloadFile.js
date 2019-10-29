function submitSearchFile(){
	var start_date=$("#start_date").val();
	var end_date=$("#end_date").val();
	if(start_date.length==0){
		//alert("please input startdate");
		$("#dowloadError").html("Please input start date");
		return false;
	}
	
	if(end_date.length==0){
		//alert("please input end_date");
		$("#dowloadError").html("Please input end date");
		return false;
	}
	
	$("#downloadForm").submit();
}


$("#start_date").blur(function(){
	if($("#start_date").val().length>0){
		$("#dowloadError").html("");
	}
	
});
$("#end_date").blur(function(){
	$("#end_date").blur();
});

$("#start_date").change(function(){
	$("#start_date").blur();
	
});
$("#end_date").change(function(){
	$("#end_date").blur();
});
var dwonloadUrl="";
	$("input[name='tradingId']").each(function(){
		dwonloadUrl=dwonloadUrl+$(this).val()+",";
});

var hrefdown="/whiteLabel/downLoadZipFile.hyml?tradingIds="+dwonloadUrl;
if(dwonloadUrl!=""){
	$("#downLoadAllDocuments").attr("href",hrefdown);
}

var start_date_val = $('#start_date').val();
var end_date_val = $('#end_date').val();
if(start_date_val != "" && end_date_val != ""){
	var getClientInfosUrl="/whiteLabel/downloadClientInfos.hyml?start_date=" + $('#start_date').val() + "&end_date=" + $('#end_date').val();
	$('#downLoadAllClientInfo').attr("href", getClientInfosUrl);
	
}


function submittempFile(){
	var valid= $("#downloadForm").validate({
		rules:{
			amount:{
	            required:true,
	            date:true
	        },
	        account:{
	            required:true,
	            date:true
	        } 
	    },
	    messages:{
	    	amount:{
	            required:"This field is required.",
	            date:"Please enter a valid date.",
	        },
	        account:{
	            required: "This field is required.",
	            date:"Please enter a valid date.",
	        }
	    }
	});
	
	if(valid){
		$("#downloadForm").submit();
	}
}

