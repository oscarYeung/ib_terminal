$(function (){
		
		 var validate = $("#registerForm").validate({
             debug: true, //调试模式取消submit的默认提交功能   
             //errorClass: "label.error", //默认为错误的样式类为：error   
             focusInvalid: false, //当为false时，验证无效时，没有焦点响应  
             onkeyup: false,   
             submitHandler: function(form){   //表单提交句柄,为一回调函数，带一个参数：form   
                // alert("提交表单"); 
            	 var parval=$("#parentIbCode").val();
            	 if(null!=parval&&!isNaN(parval)){
            		  form.submit();   //提交表单   
            		}else{
            		  $("#regError").html("ib code is wrong !");
            		}
             },   
             
             rules:{
            	 firstName:{
                     required:true
                 },
                 lastName:{
                     required:true
                 },
                 mobile:{
                     required:true
                 },
                 username:{
                     required:true
                 },
                 email:{
                     required:true,
                     email:true
                 },
                 address:{
                	 required:true
                 },
                 password:{
                     required:true
                 },
                 rePassword:{
                     required:true,
                     equalTo:"#password"
                 },
                 sex:{
                	 required:true,
                 },
                 country:{
                	 required:true
                 }
             },
             messages:{
            	 firstName:{
                     required:"This field is required"
                 },
                 lastName:{
                     required:"This field is required"
                 },
                 mobile:{
                     required:"This field is required"
                 },
                 username:{
                     required:"This field is required"
                 },
                 email:{
                     required:"This field is required",
                     email:"Please enter a valid email address."
                 },
                 address:{
                     required:"This field is required"
                 },
                 password:{
                     required:"This field is required"
                 },
                 rePassword:{
                	 required:"This field is required",
                	  equalTo:"Please enter the same value again."
                 },
                 sex:{
                	 required:"This field is required",
                 },
                 country:{
                	 required:"This field is required"
                 }
             },
             errorPlacement: function(error, element) {
            	    if (element.is(":radio")){
            	    	//alert(element.attr("name"));
            	    	 error.appendTo(element.parent().parent()); 
            	    }else{
            	    	 error.appendTo(element.parent()); 
            	    }
            	}
                       
         }); 
	});
	
	
	