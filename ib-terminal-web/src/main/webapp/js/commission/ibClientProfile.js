var clientCode=getQueryStrExp("clientCode");
clientCode=trim(clientCode)
if(null!=clientCode&&""!=clientCode){
	$("#clientCode").val(clientCode);
	getIbClientProfile();
}

function getIbClientProfile(){
	var date= new Date();
	var startDate=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	var clientCode=$.trim($("#clientCode").val());
	if(clientCode.length==0){
		alert($("#m_clientcode").html());
		return;
	}
	$("input").val('');
	$("#clientCode").val(clientCode);
	$(".errorPrompt").html("");
	$.ajax({
		  url: "/client/getIbClientPeriodSummary.hyml",
		  context: document.body,
		  //data: "ib_code="+data.node.text+"&start_date=2016-07-26 12:12:12&end_date=2016-09-26 12:12:12",
		  data: "clientCode="+clientCode+"&startDate="+startDate+"&endDate="+startDate,
		  dataType: 'text', 
		  type: "POST"
		}).done(function(json) {
				
			var htmlstr="";
			if(null!=json&&""!=json&&json.indexOf("\{")<=-1){
				$(".errorPrompt").html(json);
			}else{
				json=JSON.parse(json);
				var errorMap=json.header.errorMap;
				if(json.header.status=="SUCCESS"){
					var  client_margins =json.body.client_margins;
					var client_details=json.body.client_details;
					if(null!=client_details){
						$("#firstName").val(client_details.first_name);
						$("#lastName").val(client_details.last_name);
						$("#chineseName").val(client_details.chinese_name);
						$("#email").val(client_details.email);
						$("#PlatForm").val(client_details.trading_platform);
						$("#mobile").val(client_details.mobile);
						$("#accountBalance").val(client_details.account_balance);
						$("#ibCode").val(client_details.client_trading_id);
						
						$("#address").val(client_details.address);
						$("#sex").val(client_details.sex);
						
					}else{
						$(".errorPrompt").html($("#m_clientcode_exsit").html());
					}
					
					
				}else{
					dealResultKey(errorMap);
				}
			}
		});
}