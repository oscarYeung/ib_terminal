var date = new Date(); 
var sortTable="";
var summaryTable="";
var clientTable="";
var tabclick1="";
var tabclick2=""; 
var startDate=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
var endDate=startDate;
function clearTableTr(tableId){
	$("#"+tableId+" tr").each(function (i){
		if(i>0){
			$(this).remove();
		}
	});
}
function clearClientInfo(){
	$("#clientAccount").html("");
	$("#clientName").html("");
	$("#clientPhone").html("");
	clearTableTr("ibclientdetails");
}
function clearClientDetailsDate(){
	clearClientInfo()
	$("#c_commisson").html("");
	$("#c_rebate").html("");
	$("#c_ibcode").html("");
	$("#c_total").html("");
	$("#s_total").html("");
	$("#s_commisson").html("");
	$("#s_rebate").html("");
	clearTableTr("clientSummary");
	clearTableTr("clientSummaryDetail");
	
}
function setParaVlaue(s,s1,s2,s3){
	$("#"+s+"_commisson").html(filterPara(s1));
	$("#"+s+"_rebate").html(filterPara(s2));
	$("#"+s+"_total").html(filterPara(s1+s2));
}
function filterPara(para){
	if(para==""||null==para){
		para=0.00;
	}
	return parseNum(para);
}
function setCurrrDate(){
	
}
function getClientSummary(period){
	showTab(0);
	clearClientDetailsDate();
	var dateStrs= new Array(); 
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
	
	$("#todayOrMonth").val(period);
	$.ajax({
		  url: "/ibCommission/getIbClientSummary.hyml",
		  context: document.body,
		  //data: "ib_code="+data.node.text+"&start_date=2016-07-26 12:12:12&end_date=2016-09-26 12:12:12",
		   data: "startDate="+startDate+"&endDate="+endDate,//ibcode==1000
		  dataType: 'text', 
		  type: "POST"
		}).done(function(json) {
			var htmlstr="";
			resetHeaderCss("clientSummary");
			if(null!=json&&""!=json&&json.indexOf("\{")<=-1){
				$(".errorPrompt").html(json);
			}else{
				json=JSON.parse(json);
				var errorMap=json.header.errorMap;
				var s_commisson=0.00;
				var s_rebate=0.00;
				var s_total=0.00;
				if(json.header.status=="SUCCESS"){
					if(""!=summaryTable){
						summaryTable.destroy();
					   }
					var  clientSummary =json.body.ib_commission_summaries;
					for(var i=0 ;i<clientSummary.length;i++){
						htmlstr+="<tr id="+clientSummary[i].ib_code+" onclick=clientSummaryTable(this.id) >"
						htmlstr+="<td>"+clientSummary[i].ib_code+"</td>";
						htmlstr+="<td>"+clientSummary[i].ib_name+"</td>";
					//	htmlstr+="<td>"+filterPara(clientSummary[i].total_lot)+"</td>";
						htmlstr+="<td>"+filterPara(clientSummary[i].net_margin_in)+"</td>";
					//	htmlstr+="<td>"+filterPara(clientSummary[i].pl)+"</td>";
						htmlstr+="<td>"+filterPara(clientSummary[i].commission)+"</td>";
						htmlstr+="<td>"+filterPara(clientSummary[i].rebate)+"</td>";
						htmlstr+="<td>"+filterPara(clientSummary[i].referral_total_lot)+"</td>";
						htmlstr+="<td>"+filterPara(clientSummary[i].referral_pl)+"</td>";
						htmlstr+="<td>"+filterPara(clientSummary[i].referral_commission)+"</td>";
						htmlstr+="<td>"+filterPara(clientSummary[i].referral_rebate)+"</td>";
						htmlstr+="</tr>";
						s_commisson+=clientSummary[i].commission;
						s_rebate+=clientSummary[i].rebate;
						s_total+=clientSummary[i].total_lot;
					}
				}else{
					dealResultKey(errorMap);
				}
			}
			  
				  setParaVlaue("s",s_commisson,s_rebate,s_total);
			      $("#clientSummary tbody").html(htmlstr);
			      summaryTable=sortCommissionTables("clientSummary");
			     
			
		});
	var urlpath="/ibCommission/getIbClientSummary.hyml?"+"startDate="+startDate+"&endDate="+endDate;
	
	    
}

	

function getClientDetailsSummary(ibcode){
	clearTableTr("ibclientdetails");
	
	$("#clientAccount").html("");
	$("#clientName").html("");
	$("#clientPhone").html("");
	//$("#clientSummary tr").click(function (){});
		var period=$("#todayOrMonth").val();
		if(period==1){
			endDate=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+(date.getDate()-1);
		}else{
			endDate=startDate;
		}
	$.ajax({
		  url: "/client/getIbClientPeriodSummary.hyml",
		  context: document.body,
		  //data: "ib_code="+data.node.text+"&start_date=2016-07-26 12:12:12&end_date=2016-09-26 12:12:12",
		  data: "clientCode="+ibcode+"&startDate="+startDate+"&endDate="+endDate,
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
					  if(null!=client_margins&&""!=client_margins){
						  for(var i=0 ;i<client_margins.length;i++){
						      htmlstr+="<tr>"
								htmlstr+="<td>"+client_margins[i].trade_date+"</td>";
								htmlstr+="<td>"+filterPara(client_margins[i].net_margin)+"</td>";
								htmlstr+="</tr>";
						  }
					  }
						
					if(null!=client_details&&""!=client_details){
						
						$("#clientAccount").html(client_details.client_trading_id);
						$("#clientName").html(client_details.first_name+" "+client_details.last_name);
						$("#clientPhone").html(client_details.mobile);
					}
					
				}else{
					dealResultKey(errorMap);
				}
			}
			
			     var $tr=$("#ibclientdetails tbody").eq(0);
			      $tr.after(htmlstr);
		//	$("#ibclientdetails tbody").html(htmlstr);
			//$("#clientSummary").html(htmlstr);
		});

}


getClientSummary(0);

function resetHeaderCss(para){
	//clientSummary
		$("#"+para+" thead tr th:gt(1)").addClass("header");
}
function update_sort(para){
	 $("#"+para).trigger("update");
     var sorting = [[2,1],[0,0]];   
      $("#"+para).trigger("sorton",""); 
    //  $("#clientSummary").tablesorterPager({container: $("#pager")})
}


$(".ib-perSummary .nav-tabs li").click(function (){
	clearClientInfo();
});
function showTab(index){
	
	$(".ib-perSummary .nav-tabs li").each(function (i){
		
		if(index==i){
			$(this).addClass("active");
		}else{
			$(this).removeClass("active");
		}
	});
	
	$(".tab-pane").removeClass("in active");
	$(".tab-pane").eq(index).addClass("in active");
}
function clientSummaryTable(datapara){
	
	showTab(1);
	resetHeaderCss("clientSummaryDetail");
		$.ajax({
			url:"/ibCommission/getIbCommissionSummaryDetails.hyml",
			type:"post",
			dataType:"text",
			 data: "ib_code="+datapara+"&start_date="+startDate+"&end_date="+endDate,
			success:function (json){
				var htmlstr="";
				clearTableTr("clientSummaryDetail");
				var client_commission=0.00;
				var client_rebate=0.00;
				var client_spread=0.00;
				var client_total=0.00;
				
				if(null!=json&&""!=json&&json.indexOf("\{")<=-1){
					htmlstr="<tr>";
					htmlstr+="<td colspan=6 class=errorInfo>"+json+"</td>";
					htmlstr+="</tr>";
				}else{
					json=JSON.parse(json);
					var errorMap=json.header.errorMap;
					if(json.header.status=="SUCCESS"){
						if(""!=clientTable){
							clientTable.destroy();
						}
						var  summaryDate =json.body.ib_commission_summary_details;
						for(var i=0 ;i<summaryDate.length;i++){
								htmlstr+="<tr id="+summaryDate[i].client_code+" onclick=getClientDetailsSummary(this.id) >"
								htmlstr+="<td>"+summaryDate[i].client_code+"</td>";
								htmlstr+="<td>"+filterPara(summaryDate[i].total_lot)+"</td>"
								
								htmlstr+="<td>"+filterPara(summaryDate[i].net_margin_in)+"</td>";
								htmlstr+="<td>"+filterPara(summaryDate[i].pl)+"</td>";
								
								htmlstr+="<td>"+filterPara(summaryDate[i].commission)+"</td>";
								htmlstr+="<td>"+filterPara(summaryDate[i].rebate)+"</td>";
							
								//htmlstr+="<td>"+summaryDate[i].total_commission+"</td>";
							//	htmlstr+="<td > <a href=javascript:void(0)  onclick=clickToDetail("+summaryDate[i].ib_code+","+summaryDate[i].client_ib_code+")>Details</a></td>";
								htmlstr+="</tr>";
								$("#client_ibcode").html(summaryDate[i].ib_code);
								client_commission+=summaryDate[i].commission;
								client_rebate+=summaryDate[i].rebate;
								client_spread+=summaryDate[i].commission;
								client_total+=summaryDate[i].commission;
								
								tabclick1="tab";
						}
					}else{
						dealResultLoginOut(errorMap);
						for(var str in errorMap ){
							htmlstr="<tr>";
							htmlstr+="<td colspan=8 class=errorInfo>"+errorMap[str]+"</td>";
							htmlstr+="</tr>";
						}
					}
				}
				$("#a_client").attr("data-toggle",tabclick1);
				$("#c_ibcode").html(datapara);
				 setParaVlaue("c",client_commission,client_rebate,client_total);
				 $("#clientSummaryDetail tbody").html(htmlstr);
				 clientTable= sortCommissionTables("clientSummaryDetail");
			}
		});
		
	
	
}
$(document).ready(function(){ 
	//var table=document.getElementById("clientSummary");
	// alert(table.rows.length);
	// sort_table("clientSummary");
	
}); 

$.extend( true, $.fn.dataTable.defaults, {
    "searching": false
    
} );
function sortCommissionTables(para){
	var currengLan=$("#langType").val();
	var sortTable="";
	if(""!=currengLan&&null!=currengLan&&currengLan.indexOf("chinese")>-1){
		sortTable= $('#'+para).DataTable({
			"paging": false, // 禁止分页
			"bInfo": false,//页脚信息
	        "language": {
	            "url": "/js/dataTables/language_zh.json"
	        }
	    } );
	}else{
		sortTable= $('#'+para).DataTable({
		    "paging": false, // 禁止分页
		    "bInfo": false//页脚信息
		});
	}
	
	 return sortTable;
}