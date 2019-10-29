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
                     required:"请输入名"
                 },
                 lastName:{
                     required:"请输入姓"
                 },
                 mobile:{
                     required:"请输入电话号码"
                 },
                 username:{
                     required:"请输入用户名"
                 },
                 email:{
                	 required:"请输入邮箱地址",
                     email:"请输入正确的邮箱地址"
                 },
                 address:{
                     required:"请输入地址"
                 },
                 password:{
                     required:"请输入密码"
                 },
                 rePassword:{
                	 required:"请重复输入密码",
                	  equalTo:"重复密码必须和密码一致"
                 },
                 sex:{
                	 required:"请输入性别",
                 },
                 country:{
                	 required:"请输入国家"
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
	
	
	