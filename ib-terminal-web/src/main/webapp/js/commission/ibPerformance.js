var tabclick1="";
var tabclick2=""; 
function submitIbForm(){
	  var startDate=$.trim($("#start_date").val());
		var endDate=$.trim($("#end_date").val());
		if(startDate.length==0){
			alert($("#m_start").html());
			return false;
		}
		if(endDate.length==0){
			alert($("#m_end").html());
			return false;
		}
	  document.forms[0].submit();
	  
  }
  function parseNum(num){  
	    var reg=/(?=(?!\b)(\d{3})+$)/g;  
	   
	    return (num.toFixed(2) + '').replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');
	   
	}  
function setTotalValue(para,para1,para2,para3,para4){
	
		$("#"+para+"_commission").html(parseNum(para1));
		$("#"+para+"_rebate").html(parseNum(para2));
		$("#"+para+"_spread").html("0.00");
		$("#"+para+"_total").html(parseNum(para4));
	}
function clearTotalValues(para){
	$("#"+para+"_commission").html("0.00");
	$("#"+para+"_rebate").html("0.00");
	$("#"+para+"_spread").html("0.00");
	$("#"+para+"_total").html("0.00");
	$("#"+para+"_ibcode").html("");
	
}

function treeClickClear(){
	clearTableTr("clientTable");
	clearTableTr("detailTable");
	clearTotalValues("client");
	clearTotalValues("detail");
	$("#a_client").attr("data-toggle","");
	$("#a_details").attr("data-toggle","");
}
function clickToClient(datapara){
	showTab(1);
	 var start_date=$("#start_date").val();
	 var end_date=$("#end_date").val();
		$.ajax({
			url:"getClientSummary.hyml",
			type:"post",
			dataType:"text",
			 data: "ib_code="+datapara+"&start_date="+start_date+"&end_date="+end_date,
			success:function (json){
				var htmlstr="";
				
				clearTableTr("clientTable");
				var client_commission=0.00;
				var client_rebate=0.00;
				var client_spread=0.00;
				var client_total=0.00;
				
				if(null!=json&&""!=json&&json.indexOf("\{")<=-1){
					htmlstr="<tr>";
					htmlstr+="<td colspan=8 class=errorInfo>"+json+"</td>";
					htmlstr+="</tr>";
				}else{
					json=JSON.parse(json);
					var errorMap=json.header.errorMap;
					if(json.header.status=="SUCCESS"){
						var  summaryDate =json.body.list;
						for(var i=0 ;i<summaryDate.length;i++){
							var rebate=summaryDate[i].total_rebate_commission_lot+summaryDate[i].total_rebate_commission_pip;
							htmlstr+="<tr onclick=clickToDetail("+summaryDate[i].ib_code+","+summaryDate[i].client_code+")>"
							//	htmlstr+="<td>"+summaryDate[i].brand_code+"</td>";
								htmlstr+="<td>"+summaryDate[i].client_code+"</td>";
								htmlstr+="<td>"+summaryDate[i].product_group+"</td>"
								htmlstr+="<td>"+summaryDate[i].total_lot+"</td>";
								htmlstr+="<td>"+summaryDate[i].currency+"</td>";
								htmlstr+="<td>"+summaryDate[i].total_fix_commission+"</td>";
								htmlstr+="<td>"+rebate+"</td>";
								htmlstr+="<td>"+summaryDate[i].total_commission+"</td>";
							//	htmlstr+="<td > <a href=javascript:void(0)  onclick=clickToDetail("+summaryDate[i].ib_code+","+summaryDate[i].client_ib_code+")>Details</a></td>";
								htmlstr+="</tr>";
								$("#client_ibcode").html(summaryDate[i].ib_code);
								client_commission+=summaryDate[i].total_fix_commission;
								client_rebate+=rebate;
								client_spread+=summaryDate[i].total_fix_commission;
								client_total+=summaryDate[i].total_commission;
								
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
				setTotalValue("client",client_commission,client_rebate,client_spread,client_total);
				var  $tr=$("#clientTable thead");
				 $tr.after(htmlstr);
			}
		});
		
	}
    function showTab(index){
    	
    	$(".ib-perSummary .nav-tabs li").each(function (i){
    		
    		if(index==i){
    			$(this).addClass("active");
    		}else{
    			$(this).removeClass("active");
    		}
    	});
    	/*for(var len in $(".tab-pane") ){
    		document.write(len+"   ")
    	}*/
    	$(".tab-pane").removeClass("in active");
    	$(".tab-pane").eq(index).addClass("in active");
    }
	function clickToDetail(ibcode,clientcode){
		showTab(2);
		 var start_date=$("#start_date").val();
		 var end_date=$("#end_date").val();
		$.ajax({
			url:"getDetails.hyml",
			type:"post",
			dataType:"text",
			 data: "ib_code="+ibcode+"&start_date="+start_date+"&end_date="+end_date+"&clientcode="+clientcode,
			success:function (json){
				var htmlstr="";
				clearTableTr("detailTable");
				var detail_commission=0.00;
				var detail_rebate=0.00;
				var detail_spread=0.00;
				var detail_total=0.00;
				if(null!=json&&""!=json&&json.indexOf("\{")<=-1){
					htmlstr="<tr>";
					htmlstr+="<td colspan=8 class=errorInfo>"+json+"</td>";
					htmlstr+="</tr>";
				}else{
					json=JSON.parse(json);
					var errorMap=json.header.errorMap;
					var htmlstr="";
					if(json.header.status=="SUCCESS"){
						var  summaryDate =json.body.list;
						for(var i=0 ;i<summaryDate.length;i++){
							var rebate=summaryDate[i].rebate_commission_pip+summaryDate[i].rebate_commission_lot;
							htmlstr+="<tr>"
							//htmlstr+="<td>"+summaryDate[i].brand_code+"</td>";
							htmlstr+="<td>"+summaryDate[i].client_ib_code+"</td>";
							htmlstr+="<td>"+summaryDate[i].product_group+"</td>"
							htmlstr+="<td>"+summaryDate[i].lot+"</td>";
							htmlstr+="<td>"+summaryDate[i].currency+"</td>";
							htmlstr+="<td>"+summaryDate[i].client_fix_commission+"</td>";
							htmlstr+="<td>"+rebate+"</td>";
							htmlstr+="<td>"+summaryDate[i].trade_pl+"</td>";
							htmlstr+="</tr>";
							$("#detail_ibcode").html(summaryDate[i].client_code);
							detail_commission+=summaryDate[i].client_fix_commission;
							detail_rebate+=rebate;
							detail_spread+=summaryDate[i].client_fix_commission;
							detail_total+=summaryDate[i].trade_pl;
							tabclick2="tab";
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
				$("#a_details").attr("data-toggle",tabclick2);
				setTotalValue("detail",detail_commission,detail_rebate,detail_spread,detail_total);
				var  $tr=$("#detailTable thead");
				 $tr.after(htmlstr);
			}
		});
		
	}
	

/*function showTree(){
	alert($("#divSummary").html());
	var datastr=$("#divSummary").html();
	$('#data').jstree(
			{
				datastr
			}
		).on("select_node.jstree", 
				function (e, data) 
				{ 
					$.ajax({
						  url: "/ibCommission/getSummary.hyml",
						  context: document.body,
						  //data: "ib_code="+data.node.text+"&start_date=2016-07-26 12:12:12&end_date=2016-09-26 12:12:12",
						   data: "ib_code="+data.node.text+"&start_date=2016-07-26 12:12:12&end_date=2016-09-26 12:12:12",
						  dataType: 'text', 
						  type: "GET"
						}).done(function(json) {
								
							
							var htmlstr="";
							if(null!=json&&""!=json&&json.indexOf("\{")<=-1){
								htmlstr="<tr>";
								htmlstr+="<td colspan=8 class=errorInfo>"+json+"</td>";
								htmlstr+="</tr>";
							}else{
								json=JSON.parse(json);
								var errorMap=json.header.errorMap;
								if(errorMap['001']=="SUCCESS"){
									var  summaryDate =json.body.list;
									for(var i=0 ;i<summaryDate.length;i++){
										htmlstr+="<tr onclick=clickToClient("+summaryDate[i].ib_code+")>"
										htmlstr+="<td>"+summaryDate[i].brand_code+"</td>";
										htmlstr+="<td>"+summaryDate[i].ib_code+"</td>";
										htmlstr+="<td>"+summaryDate[i].product_group+"</td>"
										htmlstr+="<td>"+summaryDate[i].currency+"</td>";
										htmlstr+="<td>"+summaryDate[i].total_lot+"</td>";
										htmlstr+="<td>"+summaryDate[i].total_commission+"</td>";
										//htmlstr+="<td>"+summaryDate[i].total_fix_commission+"</td>";
										htmlstr+="<td>"+summaryDate[i].total_rebate_commission_lot+"</td>";
										htmlstr+="<td>"+summaryDate[i].total_rebate_commission_pip+"</td>";
										
										//htmlstr+="<td > <a href=javascript:void(0)  onclick=clickToClient("+summaryDate[i].ib_code+")>IbInfo</a></td>";
										htmlstr+="</tr>";
									}
								}else{
									for(var str in errorMap ){
										htmlstr="<tr>";
										htmlstr+="<td colspan=8 class=errorInfo>"+errorMap[str]+"</td>";
										htmlstr+="</tr>";
									}
								}
							}
							 var $tr=$("#summary tr").eq(0);
						      $tr.after(htmlstr);
						});
			
				}
		);
	
}*/