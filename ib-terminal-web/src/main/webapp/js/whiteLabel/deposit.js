$("#adminDepositForm").validate({
	
	rules:{
		account:{
            required:true,
            number:true
        },
        amount:{
            required:true,
            number:true
        } 
       
        
    },
    messages:{
    	account:{
            required:"This field is required.",
            number:"Please enter a valid number.",
        },
        amount:{
            required: "This field is required.",
            number:"Please enter a valid number.",
        }
    }
});



function resetForm(){
	$('#adminDepositForm')[0].reset()
}

$('#depositCancel').click(function() {
    resetForm();
    return false; // cancel the event
});

$('#depositSubmit').click(function() {
	$("#adminDepositForm").submit();
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
