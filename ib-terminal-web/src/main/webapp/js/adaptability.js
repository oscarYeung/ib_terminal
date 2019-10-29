$(function() {
var ischange= $(window).width();

/* ---------------------------------------left block---------------------------  */
 $(window).resize(function(){
 	
   if ($(window).width()>979 && ischange !== $(window).width()){
   		ischange = $(window).width();
   		$("#arrow").removeClass();
   		$("#content_center").removeClass();
   		$("#left_info").removeClass();
   		$("#arrow").addClass('active');
   		$("#left_info").addClass('left_zero');
		$("#content_center").addClass('content_toright');
		$("#accounts_deteil").addClass('acc_deteil_auto');
		
   		
   }else { if($(window).width()<=979 && ischange !== $(window).width()){
   		ischange = $(window).width();
   		$("#arrow").removeClass();
   		$("#arrow_drop").removeClass();
   		$("#content_center").removeClass();
   		$("#left_info").removeClass();
   		$("#arrow").addClass('passive');
   		$("#left_info").addClass('left_minus');
		$("#content_center").addClass('content_tocenter');
		$("#arrow_drop").addClass('down');
		
		}
   }
     if ($(window).width()>640){
     	$("#accounts_deteil").removeClass();
     	$("#accounts_deteil").addClass('acc_deteil_auto');
			
			$(".menu_button_settings__").hide();
			$("#menu_button").css('background-color','transparent');
     }
   		else{
   			$("#accounts_deteil").removeClass();
   			$("#accounts_deteil").addClass('acc_deteil_zero');
   		}

          
});


$("#arrow").click(function(){	

	$("#left_info").removeClass();
	$("#content_center").removeClass();
	if ($(this).hasClass("active")){
		$(this).removeClass();
   	 	$(this).addClass('passive');   	 	
   	 		
   	 			$("#left_info").addClass('left_minus');
				$("#content_center").addClass('content_tocenter');
   	 		
   	 	
	
	}else{
		$(this).removeClass();
   	 	$(this).addClass('active');
   	 	$("#left_info").addClass('left_zero');
   	 	 	 if ($(window).width()<979){
   	 			$("#content_center").addClass('content_tocenter');
   	 	 	} else{
   	 	 		$("#content_center").addClass('content_toright');
   	 	 	}
   	 	
		
	}

	
});
/* ---------------------------------middle block account details---------------------------  */
$(".acc_amount").click(function(){
	if($('#accounts_deteil').is(':hidden')){
		$('#accounts_deteil').slideDown();
		$("#arrow_drop").removeClass().addClass('up');
	}else{
		$('#accounts_deteil').slideUp();
		$("#arrow_drop").removeClass().addClass('down');
	}
	/* $("#accounts_deteil").removeClass();
	if($("#arrow_drop").hasClass("down")){
		 $("#arrow_drop").removeClass();
		 $("#arrow_drop").addClass('up');
		 $("#accounts_deteil").addClass('acc_deteil_auto');
   	 	   	 
   }else{
		$("#arrow_drop").removeClass();
		$("#arrow_drop").addClass('down');
		if ($(window).width()<640){
			 $("#accounts_deteil").addClass('acc_deteil_zero');
		}else{
			 $("#accounts_deteil").addClass('acc_deteil_auto');
		}  	 
   } */
});
});

window.onload=function(){
document.getElementById("boxa").style.height=document.getElementById("boxb").scrollHeight+"px";
}