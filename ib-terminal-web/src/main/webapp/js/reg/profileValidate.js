function updateProfile(){
	
	 var validate = $("#profileForm").validate({
        debug: true, //调试模式取消submit的默认提交功能   
        //errorClass: "label.error", //默认为错误的样式类为：error   
        focusInvalid: false, //当为false时，验证无效时，没有焦点响应  
        onkeyup: false,   
        submitHandler: function(form){   //表单提交句柄,为一回调函数，带一个参数：form   
          
            form.submit();   //提交表单   
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
           /* address:{
           	 required:true
            },*/
            sex:{
           	 required:true,
            },
            country:{
           	 required:true
            }
        },
        messages:{
       	 firstName:{
                required:"firstName is required"
            },
            lastName:{
                required:"lastName is required"
            },
            mobile:{
                required:"mobile is required"
            },
            username:{
                required:"username is required"
            },
            email:{
                required:"emails required",
                email:"Please enter a valid email address."
            },
           /* address:{
                required:"This field is required"
            },*/
            sex:{
           	 required:"sex is required",
            },
            country:{
           	 required:"country is required"
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
	 if(validate){
		 $("#profileForm").submit();
	 }

}
