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
                            <label class="searchLabel longSearchLabel" for="searchProjectName">采购计划名称</label>：
<!--                              <input id="searchProjectName" class="input-width text-left"  placeholder="请输入采购计划名称" type="text"></input> -->
                              <div class="padding-zero inputWidth  text-left">
                                  <select id="searchPlanName" class="" ></select>
                              </div>
                       
                        </div>
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="searchTypeDiv">设备类别</label>：
                            <div class="padding-zero inputWidth  text-left">
                              <select id="searchTypeDiv" class="" ></select>
                              </div>
                        </div>
	                  <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12  padding-zero">
	                    <label class="searchLabel longSearchLabel" for="form-field-1">预计采购时间</label>：
	                    <div class="form-group dateInputOther padding-zero text-left">
	                        <div id="searchBuyTimeStartDiv" style="border:none; padding:0px;"></div>
	                    </div>
	                    <div class="toLabel" for="searchStartTimeRightDiv" >~</div>
	                    <div class="form-group dateInputOther padding-zero text-left">
	                        <div id="searchBuyTimeEndDiv" style="border:none; padding:0px;"></div>
	                    </div>
	                </div>
	                   
                    </div>
				 <div class="clearfix">
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero" >
                            <label class="searchLabel" for="searchName">项目采购名称</label>：
<!--                              <input id="searchName" class="input-width text-left"  placeholder="请输入采购名称" type="text"></input> -->
							<div class="padding-zero inputWidth  text-left">
                                  <select id="searchBuyName" class="" ></select>
                            </div>
                        </div>
<!--                       <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero"> -->
<!-- 	                    <label class="searchLabel" for="searchStatus">流程状态</label>： -->
<!-- 	                    <div class="inputWidth padding-zero  text-left"> -->
<!-- 	                        <select id="searchStatus" class="form-control chosen-select"></select> -->
<!-- 	                    </div> -->
<!-- 	                  </div> -->
	                  <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
	                    <label class="searchLabel" for="searchDutyUnit">责任处室</label>：
	                     <div class="padding-zero inputWidth  text-left">
                                  <select id="searchDutyUnit" class="" ></select>
                         </div>
	                  </div>
	                  <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
	                    <label class="searchLabel" for="searchIfEnd">是否关闭</label>：
	                     <div class="padding-zero inputWidth  text-left">
                                  <select id="searchIfEnd" class="" ></select>
                         </div>
	                  </div>
                    </div>
                    <div class="clearfix">
                    	<div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
                    		<div  class="clearfix groupDiv">
								<label class="searchLabel" for="totalPriceStart">预算总价</label>：
			                	<input id="totalPriceStart" class="input-width text-left"  placeholder="请输入开始值" type="text" style="width:32%;">
			                	<label class="" for="totalPriceEnd">~</label>
			                	<input id="totalPriceEnd" class="input-width text-left"  placeholder="请输入结束值" type="text" style="width:32%;">
		           			</div>
                        </div>
                         <div class="form-group col-lg-6 col-md-3 col-sm-6 col-xs-12 padding-zero btnSearchBottom"style="text-align:right;" >
                            <button id="btnSearch" class="btn btn-xs btn-primary" >
                                <i class="glyphicon glyphicon-search"></i>
                                查询
                            </button>
                            <button id="btnReset" class="btn btn-xs btn-primary">
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
						<h5 class='table-title header smaller blue' >年度采购</h5>
						<table id="yearPurchase_table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
	                                <th>规格型号</th>
	                                <th>数量</th>
	                                <th>计数单位</th>
	                                <th>预算单价(万元)</th>
	                                <th>预算总价(万元)</th>
	                                <th>预计采购时间</th>
	                                <th>采购计划名称</th>
	                                <th>采购人员</th>
	                                <th>责任处室</th>
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
			var workticketDialog;
			var exportExcel = "";
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
// 					var unitId = ${unitId};
					var answerht = ${answerht};
					$('#myTab a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
						//年度采购
						if($(e.target).attr('href') == "#yearPurchase"){
							A.loadPage({
								render: "#yearPurchase",
								url: format_url("/yearPurchase/index")
							});
						}
						//物资采购
						if($(e.target).attr('href') == "#goodsPurchase"){
							
							A.loadPage({
								render: "#goodsPurchase",
								url : format_url("/goodsPurchase/index")
							});
						}
					
					});
					
					//项目采购名称
					var buyCombobox = new A.combobox({
						render: "#searchBuyName",
						datasource:${nameList},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					//计划名称
					var nameCombobox = new A.combobox({
						render: "#searchPlanName",
						datasource:${planNameList},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					//是否关闭
					var ifEndCombobox = new A.combobox({
						render: "#searchIfEnd",
						datasource:${ifEndList},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					//责任处室
					var dutySource = ${unitList};
					var dutyunitCombobox = new A.combobox({
						render: "#searchDutyUnit",
						//返回数据待后台返回
						datasource:dutySource,
						multiple:false,
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					//项目类别
					var statusCombobox = new A.combobox({
						render: "#searchTypeDiv",
						datasource:${typeCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					//采购时间 
					var searchPlanDateStartDiv = new A.my97datepicker({
						id: 'searchBuyTimeStartDivId',
						name:'buyTime',
						render:'#searchBuyTimeStartDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "#F{$dp.$D(\\'searchBuyTimeEndDivId\\')}",
								minDate: "",
								dateFmt: "yyyy-MM"
						}
					}).render();
					
					var searchPlanDateEndDiv = new A.my97datepicker({
						id: 'searchBuyTimeEndDivId',
						name:'buyTime',
						render:'#searchBuyTimeEndDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'searchBuyTimeStartDivId\\')}",
								dateFmt: "yyyy-MM"
						}
					}).render();
					
					//状态下拉框
					var ztTypeCombobox = new A.combobox({
						render: '#searchStatus',
						datasource:${statusTypes},
						allowBlank:true,
						options:{
							"allow_single_deselect": true,
							"disable_search_threshold":10
						}
					}).render();
					
					var yearPurchaseDatatables = new A.datatables({
						render: '#yearPurchase_table',
						options: {
					        "ajax": {
					            "url": format_url("/yearPurchase/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
// 					            	conditions.push({
// 				     					field: 'a.C_COMPANY_UNIT',
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
							          {data: "name",width: "7%",orderable: true}, 
							          {data: "typeName",name:"type",width: "7%",orderable: true}, 
							          {data: "specification",width: "6%",orderable: true}, 
							          {data: "amount",width: "4%",orderable: true}, 
							          {data: "unitName",name:"unit",width: "5%",orderable: true}, 
							          {data: "budgetPrice",width: "9%",orderable: true}, 
							          {data: "totalPrice",width: "8%",orderable: true}, 
							          {data: "buyTimeString",name:"buyTime",width: "6%",orderable: true}, 
							          {data: "projectName",width: "7%",orderable: true}, 
							          {data: "userName",name:"createUserId",width: "6%",orderable: true}, 
							          {data: "dutyName",name:"dutyUnit",width: "6%",orderable: true}, 
// 							          {data: "statusName",name:"status",width: "7%",orderable: true}, 
							          {data: "ifEndStatusName",name:"ifEndStatus",width: "7%",orderable: true}, 
							          {data: "remark",width: "8%",orderable: true}
							          ],
							          
							          fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
											 if(exportExcel){
												 exportExcels(format_url("/yearPurchase/exportExcel"),JSON.stringify(conditions));
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
    											url : format_url('/yearPurchase/getAdd')
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
										var data = yearPurchaseDatatables.getSelectRowDatas();
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
										var url = format_url('/yearPurchase/bulkDelete/');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													yearPurchaseDatatables.draw(false);
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
                    						url:format_url("/yearPurchase/getBatchAdd"),
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
            							$('#btnSearch').click();
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
    								 	exportExcels(format_url("/yearPurchase/exportExcelModel"));
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
									if(data.status!="0" && data.status!="6"){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										A.loadPage({
											render : '#page-container',
											url:format_url('/yearPurchase/getEdit/'+ id)
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
									if(data.status!="0"){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/yearPurchase/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											yearPurchaseDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						}
// 							, {
// 							id:"submit",
// 							label:"提交审批",
// 							icon: "fa fa-check-square-o bigger-130",
// 							className: " edit",
// 							render: function(btnNode, data){
// 								if(data.status!='0'){
// 									btnNode.hide();
// 								}
								
// 								var userId =${sysUserId};
// 								var loginName =${loginName};
// 								if(data.createUserId!=userId){
// 									btnNode.hide();
// 								}
// 							},
// 							events:{
// 								click: function(event, nRow, nData){
// 									var id = nData.id;
// 										workticketDialog = new A.dialog({
// 											title:"选择单位负责人",
// 											url:format_url("/yearPurchase/sureSubmit?id="+ id),
// 											height:500,
// 											width:600,
// 											closed: function(){
// 												if(workticketDialog.resule){
// 													debugger;
// 													nData.userList=workticketDialog.resule.join(",");
// 													//obj.id = nData.id;
// 													nData.taskId = $("#taskId").val();
// 													nData.procInstId=$("#procInstId").val();
// 													nData.status = '2';
// 													$.ajax({
// 														url : format_url('/yearPurchase/submit'),
// 														contentType : 'application/json',
// 														dataType : 'JSON',
// 														type : 'POST',
// 														data : JSON.stringify(nData),
// 														success: function(result){
// 															if(result.result=="success"){
// 																alert("审批成功");
// 																yearPurchaseDatatables.draw(false);
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
									if(data.status!='7'&&data.status!='8'){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/yearPurchase/endPlan/'+ id);
										A.confirm('您确认关闭物资采购计划么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'POST',
        										success: function(result){
        											alert('关闭成功');
        											yearPurchaseDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('关闭失败');
        										}
											});
										});
									}
								}
						},{
							id: "detail",
							label:"查看",
							icon: "fa fa-binoculars bigger-130",
							className: "blue ",
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									A.loadPage({
										render : '#page-container',
										url : format_url("/yearPurchase/getDetail/"+ id)
									});
								}
							}
						},{
							id: "report",
							label:"实际采购填报",
							icon: "fa fa-cog bigger-130",
							className: "blue ",
							render: function(btnNode, data){
								var userId = ${sysUserId};
								var loginName =${loginName};
								if(data.createUserId!=userId && loginName!='super'){
									btnNode.hide();
								}
								if(data.status!='7'){
									btnNode.hide();
								}
							},
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									A.loadPage({
										render : '#page-container',
										url : format_url("/yearPurchase/yearPurchaseReal/"+id)
									});
								}
							}
						}]
						}
					}).render();
					$('#btnSearch').on('click',function(){
						conditions=[];
						//判断预估总价数据合理性
						if($('#totalPriceStart').val()&&$('#totalPriceEnd').val()){
							if(parseFloat($('#totalPriceEnd').val())<=parseFloat($('#totalPriceStart').val())){
								alert("预算总价开始值不能大于结束值");
								return;
							}
						}
						if($('#totalPriceStart').val()){
        					conditions.push({
        						field: 'a.C_TOTAL_PRICE',
        						fieldType:'LONG',
        						matchType:'GE',
        						value:parseFloat($('#totalPriceStart').val())
        					});
						}
    					if($('#totalPriceEnd').val()){
        					conditions.push({
        						field: 'a.C_TOTAL_PRICE',
        						fieldType:'LONG',
        						matchType:'LE',
        						value:parseFloat($('#totalPriceEnd').val())
        					});
						}
						if($('#searchTypeDiv').val()){
	    					conditions.push({
	        					field: 'a.C_TYPE',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#searchTypeDiv').val()
	        				});
						}
						if($('#searchPlanName').val()){
	    					conditions.push({
	        					field: 'a.C_ID',
	        					fieldType:'LONG',
	        					matchType:'EQ',
	        					value:$('#searchPlanName').val()
	        				});
						}
						if($('#searchIfEnd').val()){
	    					conditions.push({
	        					field: 'a.C_IF_END_STATUS',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#searchIfEnd').val()
	        				});
						}
						if($('#searchDutyUnit').val()){
	    					conditions.push({
	        					field: 'a.C_DUTY_UNIT',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#searchDutyUnit').val()
	        				});
						}
// 						if($('#searchProjectName').val()){
// 	    					conditions.push({
// 	        					field: 'a.C_PROJECT_NAME',
// 	        					fieldType:'STRING',
// 	        					matchType:'LIKE',
// 	        					value:$('#searchProjectName').val().trim()
// 	        				});
// 						}
				
						if($("#searchBuyName").val()!=""){
							conditions.push({
								field: 'a.C_ID',
								fieldType:'LONG',
								matchType:'EQ',
								value:$("#searchBuyName").val()
							});
						}
						if($("#searchBuyTimeStartDivId").val()!=""){
							conditions.push({
								field: 'a.C_BUY_TIME',
								fieldType:'DATE',
								matchType:'GE',
								value:$("#searchBuyTimeStartDivId").val()+"-01 00:00:00"
							});
						}
						if($("#searchBuyTimeEndDivId").val()!=""){
							conditions.push({
								field: 'a.C_BUY_TIME',
								fieldType:'DATE',
								matchType:'LE',
								value:$("#searchBuyTimeEndDivId").val()+"-01 23:59:59"
							});
						}
						
// 						if($("#searchStatus").val()!=""){
// 	    					conditions.push({
// 	    						field: 'a.C_STATUS',
// 	    						fieldType:'STRING',
// 	    						matchType:'EQ',
// 	    						value:$('#searchStatus').val()
// 	    					});
//     					}
						
						yearPurchaseDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						$("#searchTypeDiv").val("");
						$("#searchTypeDiv").trigger("chosen:updated");
						$('#searchPlanName').val("");
						$("#searchPlanName").trigger("chosen:updated");
						$('#searchIfEnd').val("");
						$("#searchIfEnd").trigger("chosen:updated");
						$('#searchDutyUnit').val("");
						$("#searchDutyUnit").trigger("chosen:updated");
						$('#totalPriceStart').val("")
						$('#totalPriceEnd').val("")
						$("#searchBuyName").val('');
						$("#searchBuyName").trigger("chosen:updated");
						$("#searchBuyTimeStartDivId").val("");
						$("#searchBuyTimeStartDiv").val("");
						$("#searchBuyTimeEndDivId").val("");
						$("#searchBuyTimeEndDiv").val("");
						$('#searchStatus').val('');
						$("#searchStatus").trigger("chosen:updated");
						conditions=[];
						yearPurchaseDatatables.draw();
					});
					
					
				});
			});
			
			function goBackToYearSubmitPerson(id,selectUser){//回调函数
				var url =format_url("/yearPurchase/submit?workId="+id+"&selectUser="+selectUser);
				$.ajax({
					contentType: "application/json",
					dataType:"JSON",
					url : url,
					success: function(result){
						if(result.result=="success"){
							alert('操作成功');
							
						}else{
							alert(result.errorMsg);
						}
					},
					error:function(v,n){
						alert('操作失败');
					}
				});
				
			}
			
        </script>
    </body>
</html>