<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="page-content">
		<div class="col-lg-12 col-md-12 col-sm-12 padding-zero search-content">
				<div class="form-inline text-left " role="form">
				 <div class="clearfix">
				      <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="searchgoodsProjectName">采购计划名称</label>：
<!--                              <input id="searchgoodsProjectName" class="input-width text-left"  placeholder="请输入所需项目名称" type="text"></input> -->
							<div class="padding-zero inputWidth  text-left">
                                  <select id="searchPlanName1" class="" ></select>
                            </div>
                        </div>
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel longSearchLabel" for="searchPlanTypeDiv">物资采购计划类别</label>：
                            <div class="padding-zero inputWidth  text-left">
                              <select id="searchPlanTypeDiv" class="" ></select>
                              </div>
                        </div>
                  	   <div class="form-group  col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-zero" style="height: 31px;">
							<label class="searchLabel longSearchLabel" >预计采购时间</label>：
							<div class="form-group dateInputOther padding-zero text-left">
		                		<div id="searchBuyTimeDateStartDiv" style="border:none; padding:0px;"></div>
                   			</div>
							<div class="toLabel" for="searchStartTimeRightDiv" >~</div>
                         
							<div class="form-group dateInputOther padding-zero text-left">
		               		 	<div id="searchBuyTimeDateEndDiv"  style="border:none; padding:0px;"></div>
                   			</div>
		          		 </div>
                        
                    </div>
				 <div class="clearfix">
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero" >
                            <label class="searchLabel" for="searchgoodsName">项目采购名称</label>：
<!--                              <input id="searchgoodsName" class="input-width text-left"  placeholder="请输入计划名称" type="text"></input> -->
							<div class="padding-zero inputWidth  text-left">
                                  <select id="searchName1" class="" ></select>
                            </div>
                        </div>
<!--                            <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero"> -->
<!-- 	                    <label class="searchLabel" for="searchgoodsStatus">流程状态</label>： -->
<!-- 	                    <div class="inputWidth padding-zero  text-left"> -->
<!-- 	                        <select id="searchgoodsStatus" class="form-control chosen-select"></select> -->
<!-- 	                    </div> -->
<!-- 	                  </div> -->
	                    <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
	                    <label class="searchLabel" for="searchStatus">计划处室</label>：
	                     <div class="padding-zero inputWidth  text-left">
                                  <select id="searchDutyUnitDiv" class="" ></select>
                         </div>
	                  </div> 
	                  <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
	                    <label class="searchLabel" for="searchIfEnd1">是否关闭</label>：
	                     <div class="padding-zero inputWidth  text-left">
                                  <select id="searchIfEnd1" class="" ></select>
                         </div>
	                  </div>
	               </div>
	               <div class="clearfix">
	               		<div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
                    		<div  class="clearfix groupDiv">
								<label class="searchLabel" for="totalPriceStart1">预算总价</label>：
			                	<input id="totalPriceStart1" class="input-width text-left"  placeholder="请输入开始值" type="text" style="width:32%;">
			                	<label class="" for="totalPriceEnd1">~</label>
			                	<input id="totalPriceEnd1" class="input-width text-left"  placeholder="请输入结束值" type="text" style="width:32%;">
		           			</div>
                        </div>
                         <div class="form-group col-lg-6 col-md-12 col-sm-6 col-xs-12 padding-zero btnSearchBottom"style="text-align:right;" >
                            <button id="btnGoodsSearch" class="btn btn-xs btn-primary" >
                                <i class="glyphicon glyphicon-search"></i>
                                查询
                            </button>
                            <button id="btnGoodsReset" class="btn btn-xs btn-primary">
                                <i class="glyphicon glyphicon-repeat"></i>
                                重置
                            </button>
                        </div>
                        
                    </div>
                </div>
            </div>
			<div class="row">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
						<h5 class='table-title header smaller blue' >零星采购</h5>
						<table id="goodsPurchase_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>序号</th>
<!-- 	                                <th>计划编号</th> -->
	                                <th>项目采购名称</th>
	                                <th>设备类别</th>
	                                <th>物资采购计划类别</th>
	                                <th>型号规格</th>
	                                <th>数量</th>
	                                <th>计数单位</th>
	                                <th>预算单价(万元)</th>
	                                <th>预算总价(万元)</th>
	                                <th>预计采购时间</th>
	                                <th>采购计划名称</th>
	                                <th>采购人员</th>
	                                <th>计划处室</th>
<!-- 	                                <th>流程状态</th> -->
	                                <th>是否关闭</th>
	                                <th>备注</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			var listFormDialog;
			var googsPurchaseSubmitDialog;
			var exportExcel = "";
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					
// 					var unitId = ${unitId};
					var answerht = ${answerht};
					//计划名称
					var planName1Combobox = new A.combobox({
						render: "#searchPlanName1",
						datasource:${planNameList1},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					//采购名称
					var name1Combobox = new A.combobox({
						render: "#searchName1",
						datasource:${nameList1},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					//计划类别
					var statusCombobox = new A.combobox({
						render: "#searchPlanTypeDiv",
						datasource:${planCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					//责任处室
					var dutyCombobox = new A.combobox({
						render: "#searchDutyUnitDiv",
						//返回数据待后台返回
						datasource:${unitList},
						multiple:false,
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					//是否关闭
					var ifEndCombobox = new A.combobox({
						render: "#searchIfEnd1",
						datasource:${ifEndList},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					//采购时间 
					var searchBuyDateStartDiv = new A.my97datepicker({
						id: 'searchBuyTimeDateStartDivId',
						name:'buyTime',
						render:'#searchBuyTimeDateStartDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "#F{$dp.$D(\\'searchBuyTimeDateEndDivId\\')}",
								minDate: "",
								dateFmt: "yyyy-MM"
						}
					}).render();
					
					var searchBuyDateEndDiv = new A.my97datepicker({
						id: 'searchBuyTimeDateEndDivId',
						name:'buyTime',
						render:'#searchBuyTimeDateEndDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'searchBuyTimeDateStartDivId\\')}",
								dateFmt: "yyyy-MM"
						}
					}).render();
					
					//状态下拉框
					var ztTypeCombobox = new A.combobox({
						render: '#searchgoodsStatus',
						datasource:${statusTypes},
						allowBlank:true,
						options:{
							"allow_single_deselect": true,
							"disable_search_threshold":10
						}
					}).render();
					
					var goodsPurchaseDatatables = new A.datatables({
						render: '#goodsPurchase_table',
						options: {
					        "ajax": {
					            "url": format_url("/goodsPurchase/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
// 					            	conditions.push({
// 				     					field: 'C_COMPANY_UNIT',
// 				     					fieldType:'STRING',
// 				     					matchType:'EQ',
// 				     					value:unitId
// 				     				});
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 80,
							columns: [
							          {data:"id", visible:false,orderable:false}, 
							          {orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
// 							          {data: "code",width: "5%",orderable: true}, 
							          {data: "name",width: "6%",orderable: true}, 
							          {data: "typeName",name:"type",width: "6%",orderable: true}, 
							          {data: "planTypeName",name:"planType",width: "9%",orderable: true}, 
							          {data: "specification",width: "6%",orderable: true}, 
							          {data: "amount",width: "3%",orderable: true}, 
							          {data: "unitName",name:"unit",width: "5%",orderable: true}, 
							          {data: "budgePrice",width: "7%",orderable: true}, 
							          {data: "totalPrice",width: "6%",orderable: true}, 
							          {data: "buyTimeString",name:"buyTime",width: "6%",orderable: true},
							          {data: "projectName",width: "7%",orderable: true},
							          {data: "userName",name:"createUserId",width: "6%",orderable: true}, 
							          {data: "dutyName",name:"dutyUnit",width: "6%",orderable: true}, 
// 							          {data: "statusName",name:"status",width: "6%",orderable: true}, 
							          {data: "ifEndStatusName",name:"ifEndStatus",width: "6%",orderable: true}, 
							          {data: "remark",width: "7%",orderable: true}
							          ],
							          
							          fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
											 if(exportExcel){
												 exportExcels(format_url("/goodsPurchase/exportExcel"),JSON.stringify(conditions));
											 }
											 exportExcel="";
										 },
										 
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							if(answerht=="1"){
            								A.loadPage({
    											render : '#page-container',
    											url : format_url('/goodsPurchase/getAdd')
    										});
            							}else{
            								 alert("只有计划经营处负责人可以新增！");
            			                      return;
            							}
            							
            						}
        						}
        					}, {
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = goodsPurchaseDatatables.getSelectRowDatas();
										var ids = [];
										var userIds = [];
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												ids.push(data[i].id);
												userIds.push(data[i].createUserId);
											}
										}
										if(ids.length < 1){
											alert('请选择要删除的数据');
											return;
										}
										
										var loginUser = ${sysUserId};
										var loginName =${loginName};
										for(var j=0;j<userIds.length;j++){
											if(userIds[j]!=loginUser&&loginName!="super"){
												alert('记录中包含不是当前登陆人的记录不能删除!');
												return;
											}
										}
										if(data[0].status != "0"){
											alert("只有待提交的状态才可以删除!");
											return;
										}
										var url = format_url('/goodsPurchase/bulkDelete/');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													goodsPurchaseDatatables.draw(false);
												},
												error:function(v,n){
													alert('操作失败');
												}
											});
										});
									}
								}
							}, 
							<p:purview code="putinDuty">
							{
        						label:"批量导入",
        						icon:"glyphicon glyphicon-upload",
        						className:"btn-primary",
        						events:{
            						click:function(event){
            							workSafeOneDialog = new A.dialog({
                    						width: 800,
                    						height: 300,
                    						title: "批量导入",
                    						url:format_url("/goodsPurchase/getBatchAdd/"),
                    						closed: function(){
                    						}	
                    					}).render();
            						}
        						}
        					},
        					</p:purview>
        					{  
        						id:"dc",
        						label:"导出",
        						icon:"glyphicon glyphicon-download",
        						className:"btn-primary",
        						events:{
        							click:function(event){
            							$('#btnGoodsSearch').click();
            							exportExcel="exportExcel";
            						}
        						}
        					},
        					<p:purview code="putinDuty">
        					{  
        						id:"dc",
        						label:"导出模板",
        						icon:"glyphicon glyphicon-download",
        						className:"btn-primary",
        						events:{
        							click:function(event){
    								 	exportExcels(format_url("/goodsPurchase/exportExcelModel"));
            						}
        						}
        					},
        					</p:purview>
//         					{
// 								label:"返回",
// 								icon: "fa fa-reply",
// 								className: "btn-primary ",
// 								events:{
// 									click: function(event, nRow, nData){
// 										A.loadPage({
// 											render : '#page-container',
// 											url : format_url("/purchaseOrganization/list")
// 										});
// 									}
// 								}
// 							}
        					],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								render: function(btnNode, data){
									var userId = ${sysUserId};
									var loginName =${loginName};
									if(data.createUserId!=userId && loginName!='super'){
										btnNode.hide();
									}
									if(data.status!='0'&& data.status!='6'){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										A.loadPage({
											render : '#page-container',
											url:format_url('/goodsPurchase/getEdit/' + id)
										});
								
									}
								}
							}, {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								render: function(btnNode, data){
									var userId = ${sysUserId};
									var loginName =${loginName};
									if(data.createUserId!=userId && loginName!='super'){
										btnNode.hide();
									}
									if(data.status!='0'){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/goodsPurchase/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											goodsPurchaseDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						}
// 							,{
// 							id:"submit",
// 							label:"提交审批",
// 							icon: "fa fa-check-square-o bigger-130",
// 							className: " edit",
// 							render: function(btnNode, data){
				
// 								var userId =${sysUserId};
// 								var loginName =${loginName};
// 								if(data.createUserId!=userId){
// 									btnNode.hide();
// 								}
// 								if(data.status!='0'){
// 									btnNode.hide();
// 								} 
// 							},
// 							events:{
// 								click: function(event, nRow, nData){
// 									var id = nData.id;
// 										googsPurchaseSubmitDialog = new A.dialog({
// 											title:"选择单位负责人",
// 											url:format_url("/goodsPurchase/sureSubmit?id="+ id),
// 											height:500,
// 											width:520,
// 											closed: function(){
// 												if(googsPurchaseSubmitDialog.resule){
// 													debugger;
// 													nData.userList=googsPurchaseSubmitDialog.resule.join(",");
// 													//obj.id = nData.id;
// 													nData.taskId = $("#taskId").val();
// 													nData.procInstId=$("#procInstId").val();
// 													nData.status = '2';
// 													$.ajax({
// 														url : format_url('/goodsPurchase/submit'),
// 														contentType : 'application/json',
// 														dataType : 'JSON',
// 														type : 'POST',
// 														data : JSON.stringify(nData),
// 														success: function(result){
// 															if(result.result=="success"){
// 																alert("提交成功");
// 																goodsPurchaseDatatables.draw(false);
// 															}else{
// 																alert(result.errorMsg);
// 															}
// 														},
// 														error:function(v,n){
// 															alert("审批失败");
// 														}
// 													}); 
// 												}
// 											}
// 										}).render();
// 								}
// 								}
// 							}
							, {
								id:"end",
								label:"结束",
								icon: "fa fa-power-off",
								className: "red del",
								render: function(btnNode, data){
									var userId = ${sysUserId};
									var loginName =${loginName};
									if(data.createUserId!=userId && loginName!='super'){
										btnNode.hide();
									}
									if(data.status!='7' && data.status!='8'){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/goodsPurchase/endPlan/'+ id);
										A.confirm('您确认关闭物资采购计划么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'POST',
        										success: function(result){
        											alert('关闭成功');
        											goodsPurchaseDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('关闭失败');
        										}
											});
										});
									}
								}
						},
						{
							id: "detail",
							label:"查看",
							icon: "fa fa-binoculars bigger-130",
							className: "blue ",
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									A.loadPage({
										render : '#page-container',
										url : format_url("/goodsPurchase/getDetail/"+ id)
									});
								}
							}
						}]
						}
					}).render();
					$('#btnGoodsSearch').on('click',function(){
						conditions=[];
						//判断预估总价数据合理性
						if($('#totalPriceStart1').val()&&$('#totalPriceEnd1').val()){
							if(parseFloat($('#totalPriceEnd1').val())<=parseFloat($('#totalPriceStart1').val())){
								alert("预算总价开始值不能大于结束值");
								return;
							}
						}
						if($('#totalPriceStart1').val()){
        					conditions.push({
        						field: 'a.C_TOTAL_PRICE',
        						fieldType:'LONG',
        						matchType:'GE',
        						value:parseFloat($('#totalPriceStart1').val())
        					});
						}
    					if($('#totalPriceEnd1').val()){
        					conditions.push({
        						field: 'a.C_TOTAL_PRICE',
        						fieldType:'LONG',
        						matchType:'LE',
        						value:parseFloat($('#totalPriceEnd1').val())
        					});
						}
						if($('#searchPlanTypeDiv').val()){
	    					conditions.push({
	        					field: 'a.C_PLAN_TYPE',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#searchPlanTypeDiv').val()
	        				});
						}
						if($('#searchPlanName1').val()){
	    					conditions.push({
	        					field: 'a.C_ID',
	        					fieldType:'LONG',
	        					matchType:'EQ',
	        					value:$('#searchPlanName1').val()
	        				});
						}
				
						if($("#searchName1").val()!=""){
							conditions.push({
								field: 'a.C_ID',
								fieldType:'LONG',
								matchType:'EQ',
								value:$("#searchName1").val()
							});
						}
						
						if($('#searchIfEnd1').val()){
	    					conditions.push({
	        					field: 'a.C_IF_END_STATUS',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#searchIfEnd1').val()
	        				});
						}
						if($("#searchBuyTimeDateStartDivId").val()!=""){
							conditions.push({
								field: 'a.C_BUY_TIME',
								fieldType:'DATE',
								matchType:'GE',
								value:$("#searchBuyTimeDateStartDivId").val()+"-01 00:00:00"
							});
						}
						if($("#searchBuyTimeDateEndDivId").val()!=""){
							conditions.push({
								field: 'a.C_BUY_TIME',
								fieldType:'DATE',
								matchType:'LE',
								value:$("#searchBuyTimeDateEndDivId").val()+"-01 23:59:59"
							});
						}
// 						if($("#searchgoodsStatus").val()!=""){
// 	    					conditions.push({
// 	    						field: 'a.C_STATUS',
// 	    						fieldType:'STRING',
// 	    						matchType:'EQ',
// 	    						value:$('#searchgoodsStatus').val()
// 	    					});
//     					}
						
						if($('#searchDutyUnitDiv').val()){
	    					conditions.push({
	        					field: 'a.C_DUTY_UNIT',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#searchDutyUnitDiv').val()
	        				});
						}
						goodsPurchaseDatatables.draw();
					});
					$('#btnGoodsReset').on('click',function(){
						$("#searchPlanTypeDiv").val("");
						$("#searchPlanTypeDiv").trigger("chosen:updated");
						$('#searchPlanName1').val("");
						$("#searchPlanName1").trigger("chosen:updated");
						$("#searchName1").val('');
						$("#searchName1").trigger("chosen:updated");
						$('#searchIfEnd1').val("");
						$("#searchIfEnd1").trigger("chosen:updated");
						$("#searchBuyTimeDateStartDivId").val("");
						$("#searchBuyTimeDateStartDiv").val("");
						$("#searchBuyTimeDateEndDivId").val("");
						$("#searchBuyTimeDateEndDiv").val("");
						$('#searchgoodsStatus').val('');
						$("#searchgoodsStatus").trigger("chosen:updated");
						$('#totalPriceStart1').val("")
						$('#totalPriceEnd1').val("")
						$('#searchDutyUnitDiv').val("");
						$("#searchDutyUnitDiv").trigger("chosen:updated");
						conditions=[];
						goodsPurchaseDatatables.draw();
					});
				});
			});
			
// 			function goBackToSubmitPerson(id,selectUser){//回调函数
// 				var url =format_url("/goodsPurchase/submit?workId="+id+"&selectUser="+selectUser);
// 				var unitId = ${unitId};
// 				$.ajax({
// 					contentType: "application/json",
// 					dataType:"JSON",
// 					url : url,
// 					success: function(result){
// 						if(result.result=="success"){
// 							alert('操作成功');
// 							A.loadPage({
// 								render : '#page-container',
// 								url:format_url("/goodsPurchase/list/"+unitId),
// 							});
// 						}else{
// 							alert(result.errorMsg);
// 						}
// 					},
// 					error:function(v,n){
// 						alert('操作失败');
// 					}
// 				});
				
// 			}
        </script>
    </body>
</html>