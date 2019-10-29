$("#adminWithdrawalForm").validate({
	rules:{
		amount:{
            required:true,
            number:true
        },
        account:{
            required:true,
            number:true
        } 
    },
    messages:{
    	amount:{
            required:"This field is required.",
            number:"Please enter a valid number.",
        },
        account:{
            required: "This field is required.",
            number:"Please enter a valid number.",
        }
    }
});

$.validator.addMethod("compareAmount",function(value,element){
	if(parseInt($("#amount").val())>parseInt($("#account").val())){
		return false;
	}else{
		return true;
	}
},"Amount must less than max withdrawal.");

function getMaxWithdrawal(){
	if($("#account-error").length<=0||$("#account-error").css("display")=="none"){
		$.ajax({
			   type: "POST",
			   url: "/whiteLabel/getMaxWithdrawalAmount.hyml",
			   data: "account="+$("#account").val(),
			   success: function(data){
				   if(null!=data&&""!=data){
						  if(null==data.errorInfo||""==data.errorInfo||"null"==data.errorInfo){
							  console.log(data.max_withdrawal+"  curren "+data.currency+"  account "+data.currency);
							//$("#sbtWithdrawal").attr("onclick","");
							 $("#maxWithdrawal").val(data.max_withdrawal+"");
							 $("#currency").val(data.currency);
						      $("#errorInfo").css("display","none");
						  }else{
							 $("#errorInfo").html(data.errorInfo); 
							 $("#maxWithdrawal").val("");
							 $("#currency").val("");
						      $("#errorInfo").css("display","block");
						  }
					  }else{
						  
					  }
			   }
			});
	}
	
}
function resetForm(){
	$('#adminWithdrawalForm')[0].reset()
}


$('#withdrawalCancel').click(function() {
    resetForm();
    return false; // cancel the event
});

$('#withdrawalSubmit').click(function() {
	$("#adminWithdrawalForm").submit();
    return false; 
});



var errorinfo=$.trim($("#errorInfo").html());
if(errorinfo!=null&&""!=errorinfo){
	$("#errorInfo").css("display","block");
	if(errorinfo.indexOf("success")>-1){
		$("#errorInfo").addClass("correctTip");
	}else{
		$("#errorInfo").removeClass("correctTip");
	}
}
