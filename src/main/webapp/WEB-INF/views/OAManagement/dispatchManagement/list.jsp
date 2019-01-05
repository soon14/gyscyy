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
				<li>OA管理</li>
				<li>发文管理</li>
				<li>部内发文</li>
			<%-- 	<li class="active">${targetType}</li> --%>
			</ul>
		</div>
		<div class="page-content">
				<div class="col-lg-12 col-md-12 col-sm-12 padding-zero search-content">
					<div class="form-inline text-left " role="form">
						<input class="col-md-12" id="startTimeValue" value="${startTime}" type="hidden">
						<input class="col-md-12" id="endTimeValue" value="${endTime}" type="hidden">
						<input class="col-md-12" id="loginUserId" value="${loginUser.id}" type="hidden">
						<div class="clearfix">
							<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero" style="width:20%">
								<div class="col-lg-3  padding-zero text-left" style="width: 70px">
									<label class="searchLabel" for="searchTitle" style="width: 70px">发文标题：</label>
								</div>
								<div class="col-lg-8 padding-zero   text-left" >
										<input id="searchTitle" class="input-width text-left"  placeholder="请输入发文标题" type="text"  style="width: 100%"></input>
								</div>
							</div>
							<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero" style="width:20%">
								<div class="col-lg-3  padding-zero text-left" style="width: 70px">
									<label class="searchLabel" for="searchFileNum" style="width: 70px">发文字号：</label>
								</div>
								<div class="col-lg-8 padding-zero  text-left" >
										<input id="searchFileNum" class="input-width text-left" placeholder="请输入发文字号" type="text" style="width: 100%"></input>
								</div>
							</div>
							<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero" style="width:20%">
								<div class="col-lg-3  padding-zero text-left" style="width: 70px">
									<label class="searchLabel" for="searchDrafterDiv" style="width: 70px">拟稿人：</label>
								</div>
								<div class="col-lg-8 padding-zero text-left" >
									<select id="searchDrafterDiv" ></select>
								</div>
							</div>
							<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero" style="width:40%">
								<div class="col-lg-1  padding-zero text-left" style="width: 70px" >
									<label class="searchLabel" for="searchDrafterDiv" style="width: 70px">拟稿时间：</label>
								</div>
								<div class="col-lg-9 col-md-3 col-sm-6 col-xs-12 padding-zero">
									<div id="searchStartTime" style="float: left; width: 220px; margin-right: 10px;"></div>
									<label style="float: left;display:inline-block;margin-top:4px ">~</label>
									<div id="searchEndTime" style="float: left;width: 220px; margin-left: 10px; "></div>
								</div>
							</div>
						</div>
						<div class="clearfix">
							<div class="form-group col-lg-2 col-md-3 col-sm-6 col-xs-12 padding-zero" style="width:20%">
								<div class="col-lg-3  padding-zero text-left" style="width: 70px">
									<label class="searchLabel" for="searchDepartMentDiv" style="width: 70px">发文部门：</label>
								</div>
								<div class="col-lg-8 padding-zero text-left" >
									<select id="searchDepartMentDiv" ></select>
								</div>
							</div>
							<div class="form-group col-lg-2 col-md-3 col-sm-6 col-xs-12 padding-zero" style="width:20%">
							</div>
							<div class="form-group col-lg-2 col-md-3 col-sm-6 col-xs-12 padding-zero" style="width:20%">
							</div>
							<div class="form-group col-lg-1 col-md-3 col-sm-6 col-xs-12 padding-zero btnSearchBottom"style="text-align:right;width:40%" >
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
					<div class="widget-main no-padding">
						<h5 class='table-title header smaller blue' >部内发文</h5>
						<table id="dispatch_data_table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
									<th>发文标题</th>
									<th>发文字号</th>
									<th>拟稿时间</th>
									<th>发文部门</th>
									<th>发布日期</th>
									<th>审批状态</th>
									<th>待办人</th>
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
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					var exportExcel="";
					var startTimeValue = $("#startTimeValue").val();
					var endTimeValue = $("#endTimeValue").val();

					//拟稿人
					var searchDrafterCombo = new A.combobox({
						render : "#searchDrafterDiv",
						datasource : ${drafterCombobox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render(); 

					//统计开始日期
					var dpStartDatePicker = new A.my97datepicker({
						id:"startTime",
						render : '#searchStartTime',
						options : {
							isShowWeek : false,
							skin : 'ext',
							maxDate: "#F{$dp.$D(\\'endTime\\')}",
							minDate: "",
							dateFmt: "yyyy-MM-dd"
						},
					}).render();
					//dpStartDatePicker.setValue(startTimeValue);

					//统计结束
					var dpEndDatePicker = new A.my97datepicker({
						id:'endTime',
						render : '#searchEndTime',
						options : {
							isShowWeek : false,
							skin : 'ext',
							maxDate: '',
							minDate: "#F{$dp.$D(\\'startTime\\')}",
							dateFmt: "yyyy-MM-dd"
						},
					}).render();
					//dpEndDatePicker.setValue(endTimeValue);

					//发文部门
					var searchDepartMentCombo = new A.combobox({
						render : "#searchDepartMentDiv",
						datasource : ${tabValue},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render(); 
				
					//数据列表
					var dispatchDatatables = new A.datatables({
						render: '#dispatch_data_table',
						options: {
							"ajax": {
								"url": format_url("/dispatchManagement/searchData"),
								"contentType": "application/json",
								"type": "POST",
								"dataType": "JSON",
								"data": function (d) {
									var loginUserId = $("#loginUserId").val();
// 									if ("super" != loginUserId) {
// 										loginUserId =loginUserId;
// 										conditions.push({
// 											field: 'C_DRAFTER_ID',
// 											fieldType:'STRING',
// 											matchType:'EQ',
// 											value : loginUserId
// 										});
// 									}
									d.conditions = conditions;
									return JSON.stringify(d);
								}
							},
							multiple : true,
							ordering: true,
							optWidth: 80,
							order: [[ 0, "desc" ]],
							columns: [
									{data:"id", visible:false,orderable:false}, 
									{orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
										var startIndex = meta.settings._iDisplayStart;  
										row.start=startIndex + meta.row;
										return startIndex + meta.row + 1;  
									} },
									{data: "title",width: "15%",orderable: true,
										render : function(data, type, row, meta) {
											if(data!=null && data.length>20){
												return "<div title='"+data+"'>"+data.substring(0,20)+"..."+"</div>";  
											}else{
												return data;  
											}
									}},
									{data: "dispatchName",width: "12%",orderable: true}, 
									{data: "draftTime",width: "12%",orderable: true},
									{data: "departmentName",width: "12%",orderable: true},
									{data: "releaseTimeStr",width: "12%",orderable: true},
									{data: "approvalStatusCN",width: "12%", orderable: true},
									{data: "currentStepUserList",width: "15%",orderable: false,
										render : function(data, type, row, meta) {
											if(data!=null && data.length>20){
												return "<div title='"+data+"'>"+data.substring(0,20)+"..."+"</div>";  
											}else{
												return data;  
											}
									}}
							],
							fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
								if(exportExcel){
									exportExcels(format_url("/dispatchManagement/exportExcel"),JSON.stringify(conditions));
								}
								exportExcel="";
							},
							toolbars: [{
								label:"新增",
								icon:"glyphicon glyphicon-plus",
								className:"btn-success",
								events:{
									click:function(event){ 
										A.loadPage({
											render : '#page-container',
											url : format_url('/dispatchManagement/gotoAddPage')
										});
										/* listFormDialog = new A.dialog({
											width:870 ,
											height:850 ,
											title: "新增发文",
											url:format_url('/dispatchManagement/gotoAddPage'),
											closed: function(){
												dispatchDatatables.draw(false);
											}	
										}).render() */
									}
								}
							}, {
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = dispatchDatatables.getSelectRowDatas();
										var ids = [];
										var loginUser = '${loginUser.id}';
										var loginName = '${loginUser.loginName}';
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												if (data[i].drafterId != loginUser && loginName!="super") {
													alert('记录中包含不是当前登陆人的记录不能删除!');
													return;
												}
												if (data[i].approvalStatus != "1") {
													alert('记录中包含已经在审批流程中的记录不能删除!');
													return;
												}
												ids.push(data[i].id);
											}
										}
										if(ids.length < 1){
											alert('请选择要删除的数据');
											return;
										}
										var url = format_url('/dispatchManagement/allDelete');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													dispatchDatatables.draw(false);
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
									var loginUser ='${loginUser.id}';
									var loginName = '${loginUser.loginName}';
									if (data.drafterId != loginUser && "super" != loginName) {
										btnNode.hide();
									}
									if ( data.approvalStatus != "1"  && data.approvalStatus != "10" ) {
										btnNode.hide();
									}
									if ( data.approvalStatus == "10"  &&  "super" != loginName) {
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										A.loadPage({
											render : '#page-container',
											url : format_url('/dispatchManagement/gotoEditPage/' + id)
										});
										/* listFormDialog = new A.dialog({
											width:870 ,
											height:850 ,
											title: "发文修改",
											url:format_url('/dispatchManagement/gotoEditPage/' + id),
											closed: function(){
												dispatchDatatables.draw(false);
											}
										}).render(); */
									}
								}
							}, {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								render: function(btnNode, data){
									var loginUser = '${loginUser.id}';
									var loginName = '${loginUser.loginName}';
									if (data.drafterId != loginUser && "super" != loginName){
										btnNode.hide();
									}
									if ( data.approvalStatus != "1"  && data.approvalStatus != "10" ) {
											btnNode.hide();
									}
									if ( data.approvalStatus == "10"  &&  "super" != loginName) {
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										if (nData.approvalStatus != "1" && nData.approvalStatus != "10") {
											alert('该记录已经在审批流程中不能删除!');
											return;
										}
										var url =format_url('/dispatchManagement/deleteOne/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												success: function(result){
													alert('删除成功');
													dispatchDatatables.draw(false);
												},
												error:function(v,n){
													alert('操作失败');
												}
											});
										});
									}
								}
						}, {
							id:"review",
						 	label: "提交审核",
							icon: "fa fa-check-square-o bigger-130",
							className: " edit",
							render: function(btnNode, data){
								var loginUser = '${loginUser.id}';
								var loginName = '${loginUser.loginName}';
								if(data.drafterId != loginUser && "super" != loginName){
									btnNode.hide();
								}
								if(data.approvalStatus != "1"){
									btnNode.hide();
								}
							},
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									$.ajax({
										url: format_url('/dispatchManagement/approvalSubmit/'+id),
										contentType : 'application/json',
										dataType : 'JSON',
										type: 'POST',
										data : JSON.stringify(nData),
										success: function(result){
											if(result.result == 'success'){
												alert("提交成功");
												/* workticketDialog = new A.dialog({
													title:"提交确认",
													//url:format_url("/workTicketFire/sureSubmit?id="+ id),
													width:870 ,
													height:850 ,
												}).render(); */
												dispatchDatatables.draw(false);
        									} else if(result.result == 'noPerson'){
        										alert("无可以审核的部门领导");
        									} else {
        										alert(result.errorMsg);
        									}
										}
									});
								}
							}
						}, {
							id: "detail",
							label:"查看",
							icon: "fa fa-binoculars bigger-130",
							className: "blue ",
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									$.ajax({
										url: format_url('/dispatchManagement/showValidate/'+id),
										contentType : 'application/json',
										dataType : 'JSON',
										type: 'POST',
										data : JSON.stringify(nData),
										success: function(result){
											if(result.result == 'success'){
												A.loadPage({
													render : '#page-container',
													url : format_url('/dispatchManagement/gotoViewPage/' + id + '/all')
												});
        									} else{
        										alert(result.errorMsg);
        									}
										}
									});
									
								}
							}
						}, {
							id: "sendNotice",
							label:"发布",
							icon: "ace-icon fa fa-share bigger-130",
							className: "green ",
							render: function(btnNode, data){
								var loginUser ='${loginUser.id}';
								var loginName = '${loginUser.loginName}'
								if(data.drafterId != loginUser && "super" != loginName){
									btnNode.hide();
								}
								if(data.approvalStatus != "10"){
									btnNode.hide();
								}
								 if(data.isRelease == "1"){
									btnNode.hide();
								}
							},
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									A.loadPage({
										render : '#page-container',
										url : format_url('/dispatchManagement/gotoSendDispatchPage/' + id)
									});
									/* listFormDialog = new A.dialog({
										width:870 ,
										height:800 ,
										title: "发文发布",
										url:format_url('/dispatchManagement/gotoSendDispatchPage/' + id),
										closed: function(){
											dispatchDatatables.draw(false);
										}
									}).render(); */
								}
							}
						}, {
							id: "print",
							label:"打印",
							icon: "glyphicon glyphicon-print",
							className: "blue ",
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									var print="dispatch.rptdesign";
									birtPrint(print,id);
								}
							}
						}]
						}
					}).render();


					$('#btnSearch').on('click',function(){
						conditions=[];
						//标题
						if ($("#searchTitle").val()!="") {
							conditions.push({
								field: 'C_TITLE',
								fieldType:'STRING',
								matchType:'LIKE',
								value:$("#searchTitle").val().trim()
							});
						}
						//来文字号
						if ($("#searchFileNum").val()!="") {
							conditions.push({
								field: 'C_DISPATCH_NAME',
								fieldType:'STRING',
								matchType:'LIKE',
								value:$("#searchFileNum").val().trim()
							});
						}

						//拟稿人
						if ($("#searchDrafterDiv").val() !=null && $("#searchDrafterDiv").val()+"" != "") {
							conditions.push({
								field: 'C_DRAFTER_ID',
								fieldType:'STRING',
								matchType:'EQ',
								value:$("#searchDrafterDiv").val()
							});
						}
						//开始时间
						if ($("#startTime").val()!="") {
							var startTime = $("#startTime").val() + " 00:00:00";
							conditions.push({
								field: 'C_DRAFT_TIME',
								fieldType:'DATE',
								matchType:'GE',
								value:startTime
							});
						}
						//结束时间
						if ($("#endTime").val()!="") {
							var endTime = $("#endTime").val() + " 23:59:59";
							conditions.push({
								field: 'C_DRAFT_TIME',
								fieldType:'DATE',
								matchType:'LE',
								value:endTime
							});
						}
						//选择部门
						if ($("#searchDepartMentDiv").val() !=null && $("#searchDepartMentDiv").val()+"" != "") {
							conditions.push({
								field: 'C_DEPARTMENT_ID',
								fieldType:'STRING',
								matchType:'EQ',
								value: $("#searchDepartMentDiv").val() 
							});
						}
						dispatchDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						conditions=[];
						$("#searchTitle").val("");
						$("#searchFileNum").val("");
						dpStartDatePicker=new A.my97datepicker({
							id:"startTime",
							render : '#searchStartTime',
							options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "#F{$dp.$D(\\'endTime\\')}",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
							},
						}).render();

						//统计结束
						dpEndDatePicker = new A.my97datepicker({
							id:'endTime',
							render : '#searchEndTime',
							options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: '',
								minDate: "#F{$dp.$D(\\'startTime\\')}",
								dateFmt: "yyyy-MM-dd"
							},
						}).render();
						
						searchDrafterCombo = new A.combobox({
							render : "#searchDrafterDiv",
							datasource : ${drafterCombobox},
							allowBlank: true,
							options : {
								"disable_search_threshold" : 10
							}
						}).render();
						
						searchDepartMentCombo = new A.combobox({
							render : "#searchDepartMentDiv",
							datasource : ${tabValue},
							allowBlank: true,
							options : {
								"disable_search_threshold" : 10
							}
						}).render(); 
					});
				}); 
			});
		</script>
	</body>
</html>