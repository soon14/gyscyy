<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
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
					计划管理
				</li>
				<li class="active">投资计划</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
	   <div class="col-lg-12 col-md-12 col-sm-12 padding-zero search-content">
				<div class="form-inline text-left " role="form">
				 <div class="clearfix">
				        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero" >
                            <label class="searchLabel" for="searchName">项目名称</label>：
                             <input id="searchName" class="input-width text-left"  placeholder="请输入项目名称" type="text"></input>
                        </div>
                       <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="searchCategoryDiv">项目类别</label>：
                            <div class="padding-zero inputWidth  text-left">
                              <select id="searchCategoryDiv" class="" ></select>
                              </div>
                        </div>
                         <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="searchBuildStageDiv">建设阶段</label>：
                             <div class="padding-zero inputWidth  text-left">
                               <select id="searchBuildStageDiv" class="" ></select>
                            </div>
                        </div>
<!--                           <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero"> -->
<!-- 	                    <label class="searchLabel" for="searchStatus">状态</label>： -->
<!-- 	                    <div class="inputWidth padding-zero  text-left"> -->
<!-- 	                        <select id="searchStatus" class="form-control chosen-select"></select> -->
<!-- 	                    </div> -->
<!-- 	                  </div> -->
                    </div>
                     <div class="clearfix">
                      <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                      </div>
                     <div class="form-group col-lg-9 col-md-9 col-sm-9 col-xs-12 padding-zero btnSearchBottom"style="text-align:right;" >
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
						<h5 class='table-title header smaller blue' >投资计划</h5>
						<table id="investmentPlan_table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
	                                <th>项目名称</th>
	                                <th>项目类别</th>
	                                <th>年份</th>
	                                <th>股权结构(%)</th>
	                                <th>建设规模</th>
	                                <th>建设规模量纲</th>
	                                <th>建设阶段</th>
	                                <th>年度完成主要工程节点</th>
	                                <th>小计(万元)</th>
	                                <th>自有金额(万元)</th>
	                                <th>贷款(万元)</th>
	                                <th>填写人</th>
<!-- 	                                <th>状态</th> -->
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
			var exportExcel = "";
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					var answerht = ${answerht};
					//项目类别
					var statusCombobox = new A.combobox({
						render: "#searchCategoryDiv",
						datasource:${categoryCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					//建设阶段
					var buildStageCombobox = new A.combobox({
						render: "#searchBuildStageDiv",
						datasource:${stageCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
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
					
					var investmentPlanDatatables = new A.datatables({
						render: '#investmentPlan_table',
						options: {
					        "ajax": {
					            "url": format_url("/investmentPlan/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
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
							          {data: "name",width: "auto",orderable: true}, 
							          {data: "categoryName",name:"category",width: "auto",orderable: true}, 
							          {data: "yearNumString",name:"yearNum",width: "auto",orderable: true}, 
							          {data: "stockStruct",width: "auto",orderable: true},
							          {data: "buildSize",width: "auto",orderable: true},
							          {data: "buildSizeUnit",width: "auto",orderable: true},
							          {data: "buildStageName",name:"buildStage",width: "auto",orderable: true}, 
							          {data: "content",width: "auto",orderable: true}, 
							          {data: "totalMoney",width: "auto",orderable: true},
							          {data: "ownMoney",width: "auto",orderable: true},
							          {data: "loanMoney",width: "auto",orderable: true},
							          {data: "userName",name:"createUserId",width: "auto",orderable: true},
// 							          {data: "approveStatusName",name:"approveStatus",width: "auto",orderable: true}
							         ],
							         
							         fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
										 if(exportExcel){
											 exportExcels(format_url("/investmentPlan/exportExcel"),JSON.stringify(conditions));
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
    											url : format_url("/investmentPlan/getAdd")
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
// 										click: function(event, nRow, nData){
										debugger;
										var data = investmentPlanDatatables.getSelectRowDatas();
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
										
										if(data[0].approveStatus != "0"){
											alert("只有待提交的状态才可以删除!");
											return;
										}
										var url = format_url('/investmentPlan/bulkDelete/');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													investmentPlanDatatables.draw(false);
												},
												error:function(v,n){
													alert('操作失败');
												}
											});
										});
									}
								}
							}, {
								label:"导出",
								icon: "glyphicon glyphicon-download",
								className: "btn-primary",
								events:{
									click:function(event){
										
										$('#btnSearch').click();
            							exportExcel="exportExcel";
            						}
								}
							}],
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
									if(data.approveStatus!='0'&& data.approveStatus!='3'){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										A.loadPage({
											render : '#page-container',
											url : format_url('/investmentPlan/getEdit/' + id)
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
									if(data.approveStatus!='0'){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/investmentPlan/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											investmentPlanDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						}
// 						{
// 							id:"submit",
// 							label:"提交审批",
// 							icon: "fa fa-check-square-o bigger-130",
// 							className: " edit",
// 							render: function(btnNode, data){
// 								if(data.approveStatus!='0'){
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
// 									var status=nData.approveStatus;
// 									if(status!='0' && status!='6'){
// 										alert("只有待提交和驳回状态的可以提交!");
// 										btnNode.hide();
// 										return;
// 									}
								
// 									listFormDialog = new A.dialog({
// 										width: 850,
// 										height: 481,
// 										title: "提交审批",
// 										url:format_url('/investmentPlan/sureSubmit'),
// 										closed: function(resule){
// 											var obj=new Object(); 
// 											var id = nData.id;
// 											var url =format_url('/investmentPlan/submit/'+ id);
// 											if(listFormDialog.resule){
// 												  obj.userList=listFormDialog.resule.join(",");
// 														$.ajax({
// 															url : url,
// 				    										contentType : 'application/json',
// 				    										dataType : 'JSON',
// 				    										type : 'POST',
// 				    										data:JSON.stringify(obj),
// 				    										success: function(result){
// 				    											if(result.result=="success"){
// 				    												alert("提交成功");
// 				    												investmentPlanDatatables.draw(false);
// 				    											}else{
// 				    												alert(result.errorMsg);
// 				    											}
// 				    										},
// 				    										error:function(v,n){
// 				    											alert('提交失败');
// 				    										}
// 													});
// 											}
// 										}
// 									}).render();
// 									}
// 								}
// 							}
						,{
							id: "detail",
							label:"查看",
							icon: "fa fa-binoculars bigger-130",
							className: "blue ",
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									A.loadPage({
										render : '#page-container',
										url : format_url('/investmentPlan/getDetail/' + id)
							   });
							 }
							}
						}]
						}
					}).render();
					$('#btnSearch').on('click',function(){
						conditions=[];
						if($('#searchCategoryDiv').val()){
	    					conditions.push({
	        					field: 'category',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#searchCategoryDiv').val()
	        				});
						}
						if($('#searchBuildStageDiv').val()){
	    					conditions.push({
	        					field: 'buildStage',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#searchBuildStageDiv').val()
	        				});
						}
				
						if($("#searchName").val()!=""){
							conditions.push({
								field: 'a.C_NAME',
								fieldType:'STRING',
								matchType:'LIKE',
								value:$("#searchName").val().trim()
							});
						}
						
// 						if($("#searchStatus").val()!=""){
// 	    					conditions.push({
// 	    						field: 'C_APPROVE_STATUS',
// 	    						fieldType:'STRING',
// 	    						matchType:'EQ',
// 	    						value:$('#searchStatus').val()
// 	    					});
//     					}
						investmentPlanDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						$("#searchCategoryDiv").val("");
						$("#searchCategoryDiv").trigger("chosen:updated");
						$('#searchBuildStageDiv').val("");
						$("#searchBuildStageDiv").trigger("chosen:updated");
						$("#searchName").val('');
						$('#searchStatus').val('');
						$("#searchStatus").trigger("chosen:updated");
					});
				});
			});
        </script>
    </body>
</html>