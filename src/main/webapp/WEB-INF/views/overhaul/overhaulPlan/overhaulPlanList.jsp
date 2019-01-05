<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
<%@ page import="com.aptech.business.component.dictionary.OverhaulPlanStatusEnum"%>

<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="breadcrumbs ace-save-state" id="breadcrumbs">
			<ul class="breadcrumb">
				<li>
					<i class="ace-icon fa fa-home home-icon"></i>
					<a href="javascript:void(0);" onclick="firstPage()">首页</a>
				</li>
				<li>
					检修管理
				</li>
				<li class="active">检修计划</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class=" col-lg-12 col-md-12 col-sm-12  padding-zero search-content">
				<div class="form-inline text-left" role="form">
					<div class="clearfix">
						<div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
	 						<div  class="clearfix groupDiv">
								<label class="searchLabel" for="form-field-1">单位名称</label>：
								<div class="padding-zero inputWidth  text-left">
			                      <select id="searchunitNameId" class="inputWidth text-left padding-zero" ></select>
		   						</div>
<!-- 						   		<div id="searchunitNameId"  class="inputWidth text-left padding-zero"></div> -->
	                   		</div>
                   		  <div  class="clearfix  groupRightDiv">
								<label class="searchLabel longSearchLabel" for="form-field-1">检修计划编号</label>：
					                  <input id="searchplanNumber" class="inputWidth padding-zero  text-left"  placeholder="请输入检修计划编号" type="text"></input>
	                   		</div>
<!-- 							<div  class="clearfix  groupRightDiv"> -->
<!-- 							<label class="searchLabel longSearchLabel" for="form-field-1">检修负责人</label>： -->
<!-- 								<div class="inputWidth padding-zero  text-left"> -->
<!-- 				                <select id="searchdutyUser" class="" ></select> -->
<!-- 			                </div> -->
<!--                    			</div> -->
                   		</div>
                   		
			        	<div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
							<label class="searchLabel" >申报日期</label>：
							<div class="form-group dateInput padding-zero text-left">
		                		<div id="searchPlanDateStartDiv" style="border:none; padding:0px;"></div>
                   			</div>
							<div class="toLabel" for="searchStartTimeRightDiv" >~</div>
                         
							<div class="form-group dateInput padding-zero text-left">
		               		 	<div id="searchPlanDateEndDiv"  style="border:none; padding:0px;"></div>
                   			</div>
		          		 </div>
		           
		           
				   </div>
				   
				   <div class="clearfix">
                			<div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
	 						<div  class="clearfix groupDiv">
								<label class="searchLabel" for="form-field-1">状态</label>：
	   		     				 <div class="padding-zero inputWidth  text-left">
					                <select id="searchApproveStatus" class="" >
					                </select>
				                </div>
	                   		</div>
<!-- 	                   	    <div  class="clearfix  groupRightDiv"> -->
<!-- 								<label class="searchLabel longSearchLabel" for="form-field-1">检修计划编号</label>： -->
<!-- 					                  <input id="searchplanNumber" class="inputWidth padding-zero  text-left"  placeholder="请输入检修计划编号" type="text"></input> -->
<!-- 	                   		</div> -->
                   		  </div>
	              <div  class="form-group col-lg-6 col-md-6 col-sm-12 padding-zero text-right btnSearchBottom">
						<button id="btnSearch" class="btn btn-xs btn-primary">
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
					<h5 class='table-title header smaller blue' >检修计划</h5>
					<div class="widget-main no-padding">
						
						<table id="overhaulPlan-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:20px;">
										<label class="pos-rel">
											<input type="checkbox" class="ace" />
											<span class="lbl"></span>
										</label>
									</th>
	                                <th>序号</th>
	                                <th>检修计划编号</th>
	                                <th>单位名称</th>
	                                <th>计划填报人</th>
	                                <th>检修计划名称</th>
	                                <th>申报日期</th>
	                                <th>状态</th>
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
			var formDialog;
			var unitNameIdCombotree;
			var conditions=[];
			var overhaulPlanDatatables ="";

			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					 
						//单位
					 searchunitid = new A.combobox({
						render : "#searchunitNameId",
						datasource : ${unitList},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					
					//检修负责人下拉框
					var requestUsersCombobox = new A.combobox({
						render: '#searchdutyUser',
						datasource:${dutyUser},
						width:"150px",
						multiple:false,
						allowBlank:true,
						options:{
							"allow_single_deselect": true,
							"disable_search_threshold":10
						}
					}).render();
					//流程下拉框
					var searchApproveStatus = new A.combobox({
						render: '#searchApproveStatus',
						datasource:${approveStatus},
						width:"150px",
						multiple:false,
						allowBlank:true,
						options:{
							"allow_single_deselect": true,
							"disable_search_threshold":10
						}
					}).render();
					
					
					var searchPlanDateStartDiv = new A.my97datepicker({
						id: 'searchPlanDateStartDivId',
						name:'planDate',
						render:'#searchPlanDateStartDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "#F{$dp.$D(\\'searchPlanDateEndDivId\\')}",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					
					var searchPlanDateEndDiv = new A.my97datepicker({
						id: 'searchPlanDateEndDivId',
						name:'planDate',
						render:'#searchPlanDateEndDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'searchPlanDateStartDivId\\')}",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					
					 overhaulPlanDatatables = new A.datatables({
						render: '#overhaulPlan-table',
						options: {
					        "ajax": {
					            "url": format_url("/overhaulPlan/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "async":false, 
					            "data": function (d) {
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth:150,
							order:[[0,'desc']],
							columns: [{data:"id", visible:false,orderable:false}, 
							          {orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							{data: "planNumber",width: "15%",orderable: true}, 
							{data: "unitName",width: "15%",orderable: true},
							{data: "dutyUserName",width: "10%",orderable: true},
							{data: "planNameId",width: "20%",orderable: true,
								render : function(data, type, row, meta) {
					                   if(data=="1"){
					                	   return "有年度计划";
					                   } else{ return "无";}
					               	}
							},
							{data: "planYearString",name:"planYear",width: "20%",orderable: true},
							{data: "approveStatusString",name:"approveStatus",width: "11%",orderable: true},
						],
									 
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							window.scrollTo(0,0);
										A.loadPage({
											render : '#page-container',
											url : format_url("/overhaulPlan/getAdd")
										});
            						}
        						}
        					}, {
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = overhaulPlanDatatables.getSelectRowDatas();
										var ids = [];
										var checkids=[];
										var userIds = [];
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												ids.push(data[i].id);
												userIds.push(data[i].dutyUserId);
												
												if(data[i].approveStatus!=<%=OverhaulPlanStatusEnum.PENDING.getCode()%> 
												&& data[i].approveStatus!=<%=OverhaulPlanStatusEnum.CANCEL.getCode()%>){
													checkids.push(data[i].id);
												}
											}
										}
										if(ids.length < 1){
											alert('请选择要删除的数据');
											return;
										}
										if(checkids.length>0){
											alert("只有待提交和已取消状态可以删除");
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
										
										
										var url = format_url('/overhaulPlan/bulkDelete/');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													overhaulPlanDatatables.draw(false);
												},
												error:function(v,n){
													alert('操作失败');
												}
											});
										});
									}
								}
							},{
								id:"dc",
        						label:"导出",
        						icon:"glyphicon glyphicon-download",
        						className:"btn-primary",
        						events:{
            						click:function(event){
            							searchFunction();
            							window.location.href=format_url("/overhaulPlan/exportExcel/"+JSON.stringify(conditions)); 
            						}
        						}
        					}],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								render: function(btnNode, data){
									var status=data.approveStatus;
									var userId = ${sysUserId};
									var loginName =${loginName};
									if((status!=<%=OverhaulPlanStatusEnum.REJECT.getCode()%> && status!=<%=OverhaulPlanStatusEnum.MAINTENANCE_REPORTT.getCode()%>) && status!=<%=OverhaulPlanStatusEnum.PENDING.getCode()%> ){
										btnNode.hide();
									}
									if(data.dutyUserId!=userId && loginName!='super'){
										btnNode.hide();
									}
								},
								
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
            							window.scrollTo(0,0);
										A.loadPage({
											render : '#page-container',
											url : format_url('/overhaulPlan/getEdit/' + id)
										});
									
									}
								}
							}, {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								render: function(btnNode, data){
									var status=data.approveStatus;
									var userId = ${sysUserId};
									var loginName =${loginName};
									if(status!=<%=OverhaulPlanStatusEnum.PENDING.getCode()%> && status!=<%=OverhaulPlanStatusEnum.CANCEL.getCode()%>){
										btnNode.hide();
									}
									if(data.dutyUserId!=userId && loginName!='super'){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/overhaulPlan/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											overhaulPlanDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						}, {
								id:"approve",
								label:"提交",
								icon: "fa fa-check-square-o bigger-130",
								className: "edit",
								render: function(btnNode, data){
									var status=data.approveStatus;
									var userId = ${sysUserId};
									if(status!=<%=OverhaulPlanStatusEnum.PENDING.getCode()%>){
										btnNode.hide();
									}
									if(data.dutyUserId!=userId){
										btnNode.hide();
									}
									
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var status=nData.approveStatus;
										if(status!=<%=OverhaulPlanStatusEnum.REJECT.getCode()%> && status!=<%=OverhaulPlanStatusEnum.PENDING.getCode()%>){
											alert("只有待提交和驳回状态的可以提交!");
											btnNode.hide();
											return;
										}
										listFormDialog = new A.dialog({
											width: 850,
											height: 400,
											title: "审批人",
											url:format_url('/overhaulPlan/sureSubmit'),
											closed: function(resule){
												var obj=new Object(); 
												var id = nData.id;
												var url =format_url('/overhaulPlan/submit/'+ id);
												if(listFormDialog.resule){
													  obj.userList=listFormDialog.resule.join(",");
															$.ajax({
																url : url,
					    										contentType : 'application/json',
					    										dataType : 'JSON',
					    										type : 'POST',
					    										data:JSON.stringify(obj),
					    										success: function(result){
					    											if(result.result=="success"){
					    												alert("提交成功");
					    												overhaulPlanDatatables.draw(false);
					    											}else{
					    												alert(result.errorMsg);
					    											}
					    										},
					    										error:function(v,n){
					    											alert('提交失败');
					    										}
														});
												}
											}
										}).render();
										
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
														url : format_url("/overhaulPlan/detail/"+ id)
														
													});
								}
							}
						}]
						}
					}).render();
					 
// 					 $(".aptech-combotree-dropdown").css("width","30%");
 
					 
					 $('#btnSearch').on('click',function(){
						searchFunction();
						});
					 
					 
						$('#btnReset').on('click',function(){
							$("#searchplanNumber").val("");
							$("#searchPlanDateStartDivId").val("");
							$("#searchPlanDateStartDiv").val("");
							$("#searchPlanDateEndDivId").val("");
							$("#searchPlanDateEndDiv").val("");
// 							$('#searchdutyUser').val('');
// 							$("#searchdutyUser").trigger("chosen:updated");
							$('#searchApproveStatus').val('');
							$("#searchApproveStatus").trigger("chosen:updated");
							$("#searchunitNameId").val('');
							$("#searchunitNameId").trigger("chosen:updated");
						});
				});
			});
			
			function goBackToSubmitPerson(id,selectUser){//回调函数
				
				var url =format_url("/overhaulPlan/submit?workId="+id+"&selectUser="+selectUser);
				$.ajax({
					contentType: "application/json",
					dataType:"JSON",
					url : url,
					success: function(result){
						if(result.result=="success"){
							alert('操作成功');
							overhaulPlanDatatables.draw(false);
						}else{
							alert(result.errorMsg);
							overhaulPlanDatatables.draw(false);
						}
					},
					error:function(v,n){
						alert('操作失败');
						overhaulPlanDatatables.draw(false);
					}
				});
				
			}
			
			function searchFunction(){ 
				 conditions=[];
					if($("#searchplanNumber").val()!=""){
						conditions.push({
							field: 'o.C_PLAN_NUMBER',
							fieldType:'STRING',
							matchType:'LIKE',
							value:$("#searchplanNumber").val().trim()
						});
					}
				
					if($('#searchunitNameId').val()){
    					conditions.push({
        					field: 'o.C_UNIT_ID',
        					fieldType:'STRING',
        					matchType:'EQ',
        					value:$('#searchunitNameId').val()
        				});
					}
// 					if($("#searchdutyUser").val()!=""){
// 						conditions.push({
// 	    					field: 'o.C_DUTY_USER_ID',
// 	    					fieldType:'LONG',
// 	    					matchType:'EQ',
// 	    					value:$('#searchdutyUser').val()
// 	    				});
// 					}
					if($("#searchApproveStatus").val()!=""){
						conditions.push({
	    					field: 'o.C_APPROVE_STATUS',
	    					fieldType:'STRING',
	    					matchType:'EQ',
	    					value:$('#searchApproveStatus').val()
	    				});
					}
					
					if($("#searchPlanDateStartDivId").val()!=""){
						conditions.push({
							field: 'o.C_PLAN_YEAR',
							fieldType:'DATE',
							matchType:'GE',
							value:$("#searchPlanDateStartDivId").val()+" 00:00:00"
						});
					}
					if($("#searchPlanDateEndDivId").val()!=""){
						conditions.push({
							field: 'o.C_PLAN_YEAR',
							fieldType:'DATE',
							matchType:'LE',
							value:$("#searchPlanDateEndDivId").val()+" 23:59:59"
						});
					}
				 overhaulPlanDatatables.draw();
			} 
        </script>
    </body>
</html>