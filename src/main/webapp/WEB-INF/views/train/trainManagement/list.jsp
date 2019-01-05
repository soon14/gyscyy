<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
<!-- 		<meta http-equiv="refresh" content="1;url=http://www.baidu.com"> -->
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="breadcrumbs ace-save-state" id="breadcrumbs">
			<ul class="breadcrumb">
				<li>
					<i class="ace-icon fa fa-home home-icon"></i>
					<a href="javascript:void(0);" onclick="firstPage()">首页</a>
				</li>
				<li>生产技术培训管理</li>
				<li class="active">培训管理</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class="col-lg-12 col-md-12 col-sm-12 search-content  padding-zero">
				<div class="form-inline text-left" role="form">
					<div class="clearfix">
					   <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero" >
							<label class="searchLabel" for="form-field-1">办班单位</label>：
							<div id="searchunitId" class="input-width padding-zero  text-left"></div>
						</div>
					   <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-zero" >
							<label class="searchLabel" for="form-field-1">培训时间</label>：
					   		<div class="form-group dateInputOther padding-zero text-left">
								<div id="searchStartstartTimeDiv" style="border:none; padding:0px;height:30px;"></div>
							</div>
				   			<div class="toLabel" for="searchStartTimeRightDiv" >~</div>
							<div class="form-group dateInputOther padding-zero text-left">
								<div id="searchEndstartTimeDiv" style="border:none; padding:0px;height:30px;"></div>
							</div>
					   </div>
					   <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
							<label class="searchLabel" for="form-field-1">培训分类</label>：
							<div class="input-width padding-zero  text-left">
								<select id="trainClassify" class="form-control chosen-select" style="width:150px;"></select>
							</div>
					   </div>
				   </div>
					<div class="clearfix">
						<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
							<label class="searchLabel" for="form-field-1">培训范围</label>：
							<div class="input-width padding-zero  text-left">
								<select id="trainRange" class="form-control chosen-select" style="width:150px;"></select>
							</div>
					   </div>
						<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero" >
							<label class="searchLabel" for="form-field-1">课题名称</label>：
							<input id="searchtitle" class="input-width text-left" placeholder="请输入课题名称" type="text" >
					   </div>
					   <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
							<!-- <label class="searchLabel" for="form-field-1">培训状态</label>：
							<div class="input-width padding-zero  text-left">
								<select id="trainStatus" class="form-control chosen-select" style="width:150px;"></select>
							</div> -->
					   </div>
					   <div class="form-group col-lg-3 col-md-9 col-sm-6 col-xs-12 padding-zero btnSearchBottom" style="text-align:right; ">
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
					<div class="widget-main no-padding">
						<h5 class='table-title header smaller blue' >培训管理表</h5>
						<table id="trainPlan_table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
									<th>组织单位</th>
									<th>课题名称</th>
									<th>培训分类</th>
									<th>培训时间</th>
									<th>培训范围</th>
									<th>培训人</th>
									<th>培训地点</th>
									<th>考核方式</th>
									<th>培训内容</th>
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
			var listFormDialog;
			var submitUserDialog;
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					var unitIdCombotree = new A.combotree({
						render: "#searchunitId",
						name: 'unitId',
						//返回数据待后台返回TODO
						datasource: ${unitNameIdTreeList},
						width:"210px",
						options: {
							treeId: 'unitId',
							data: {
								key: {
									name: "name"
								},
								simpleData: {
									enable: true,
									idKey: "id",
									pIdKey: "parentId"
								}
							}
						}
					}).render();
					var startDatePicker = new A.my97datepicker({
						id: 'searchStartstartTime',
						render:'#searchStartstartTimeDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate:  "#F{$dp.$D(\\'searchEndstartTime\\')}",
								minDate: "",
								dateFmt: 'yyyy-MM-dd HH:mm'
						}
					}).render();
					var endDatePicker = new A.my97datepicker({
						id: 'searchEndstartTime',
						render:'#searchEndstartTimeDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate:  "#F{$dp.$D(\\'searchStartstartTime\\')}",
								dateFmt: 'yyyy-MM-dd HH:mm'
						}
					}).render();
					
					//培训状态
				/* 	var statusCombobox = new A.combobox({
						render: "#trainStatus",
						datasource:${trainPlanStatusCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render(); */
					//培训分类
					var classifyCombobox = new A.combobox({
						render: "#trainClassify",
						datasource:${trainPlanClassifyCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					//培训范围
					var rangeCombobox = new A.combobox({
						render: "#trainRange",
						datasource:${trainPlanRangeCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					var trainDatatables = new A.datatables({
						render: '#trainPlan_table',
						options: {
							"ajax": {
							"url": format_url("/trainManagement/search"),
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
							order:[[0,'desc']],
							columns: [
									{data:"id", visible:false,orderable:true}, 
									{orderable: false,"width":"3%", "sClass": "center", render : function(data, type, row, meta) {
										var startIndex = meta.settings._iDisplayStart;  
										row.start=startIndex + meta.row;
										return startIndex + meta.row + 1;  
									} },
									{data: "unitName",width: "auto",orderable: true}, 
									{data: "titleName",width: "auto",orderable: true, render:function(data,type,row,meta) {
											if(data!=null&&data.length>20){
												return "<div title='"+data+"'>'"+data.substring(0,20)+"..."+"</div>";
											} else {
												 return data;
											}
									} }, 
									{data: "classifyName",name:"classify",width: "auto",orderable: true}, 
									{data: "showTime",name:"time",width: "auto",orderable: true}, 
									{data: "rangeName",name:"range",width: "auto",orderable: true}, 
									{data: "trainPersion",name:"trainPersion",width: "auto",orderable: true}, 
									{data: "trainLocation",width: "auto", orderable: true},
									{data: "assessmentMethodName",name:"assessmentMethod",width: "auto", orderable: true},
									{data: "trainContent",width: "auto", orderable: true},
									{data: "statusName",name:"status",width: "auto", orderable: true},
							],
							toolbars: [{
								label:"新增",
								icon:"glyphicon glyphicon-plus",
								className:"btn-success",
								events:{
									click:function(event){
										A.loadPage({
											render : '#page-container',
											url : format_url('/trainManagement/getAdd')
										});
									}
								}
							}, {
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = trainDatatables.getSelectRowDatas();
										var ids = [];
										var userIds = [];
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												ids.push(data[i].id);
												userIds.push(data[i].createPeopleId);
												if (data[i].status !="1") {
													alert('记录中包含已经在审批流程中的记录不能删除!');
													return;
												}
											}
										}
										var loginUser = '${sysUserEntity.id}';
										var loginName = '${sysUserEntity.loginName}'
										for(var j=0;j<userIds.length;j++){
											if(userIds[j]!=loginUser&&loginName!="super"&&loginName!="caijiangchao"&&loginName!="yanyuan"){
											  alert('记录中包含不是当前登陆人的记录不能删除!');
											  return;
											}
										}
										if(ids.length < 1){
											alert('请选择要删除的数据');
											return;
										}
										var url = format_url('/trainManagement/allDelete/');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													trainDatatables.draw(false);
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
										exportExcels(format_url("/trainManagement/exportExcel"),JSON.stringify(conditions));
									}
								}
							}],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								render: function(btnNode, data){
									var loginUser = '${sysUserEntity.id}';
									var loginName = '${sysUserEntity.loginName}'
									if(data.createPeopleId != loginUser && loginName != "super"&&loginName!="caijiangchao"&&loginName!="yanyuan"){
										btnNode.hide();
									}
									if (data.status !="1") {
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										A.loadPage({
											render : '#page-container',
											url : format_url('/trainManagement/getEdit/' + id)
										});
									}
								}
							}, {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								render: function(btnNode, data){
									var loginUser = '${sysUserEntity.id}';
									var loginName = '${sysUserEntity.loginName}'
									if (data.createPeopleId != loginUser && loginName != "super"&&loginName!="caijiangchao"&&loginName!="yanyuan"){
										btnNode.hide();
									}
									if (data.status !="1") {
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/trainManagement/deleteOne/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												success: function(result){
													if(result.result=="success"){
														alert('删除成功');
														trainDatatables.draw(false);
													}else{
														alert(result.errorMsg);
													}
													
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
								var loginUser = '${sysUserEntity.id}';
								var loginName = '${sysUserEntity.loginName}'
								if (data.createPeopleId != loginUser  && loginName != "super"){
									btnNode.hide();
								}
								if (data.status !="1") {
									btnNode.hide();
								}
								
							},
							events:{
								click: function(event, nRow, nData){
									var urls = format_url("/trainManagement/submitPerson/" + $("#taskId").val());
									submitUserDialog = new A.dialog({
										width: 850,
										height: 481,
										title: "审核人",
										url: urls,
										closed: function(){
											if(submitUserDialog.resule){
												debugger;
												nData.userList=submitUserDialog.resule.join(",");
												//obj.id = nData.id;
												nData.taskId = $("#taskId").val();
												nData.procInstId=$("#procInstId").val();
												nData.status = '2';
												$.ajax({
													url : format_url('/trainManagement/approvalSubmit'),
													contentType : 'application/json',
													dataType : 'JSON',
													type : 'POST',
													data : JSON.stringify(nData),
													success: function(result){
														if(result.result=="success"){
															alert("审批成功");
															trainDatatables.draw(false);
														}else{
															alert(result.errorMsg);
														}
													},
													error:function(v,n){
														alert("审批失败");
													}
												}); 
											}
										}
									}).render();
								}
							}
						},  {
						 	label: "查看",
							icon: "fa fa-binoculars bigger-130",
							className: "blue search",
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									A.loadPage({
										render : '#page-container',
										url : format_url('/trainManagement/showPage/' + id)
									});
								}
							}
						}]
						}
					}).render();
					$('#btnSearch').on('click',function(){
						searchFunction();
					});
					function searchFunction(){
						conditions=[];
						if(unitIdCombotree.getValue()!=undefined&&unitIdCombotree.getValue()+""!=""){
    						conditions.push({
            					field: 'p.C_UNIT_ID',
            					fieldType:'STRING',
            					matchType:'EQ',
            					value:unitIdCombotree.getValue()
            				});
						}

						if($('#searchtitle').val()){
							conditions.push({
								field: 'p.C_TITLE_NAME',
								fieldType:'STRING',
								matchType:'LIKE',
								value:$('#searchtitle').val()
							});
						}


						if($("#searchStartstartTime").val()){
							conditions.push({
								field: 'p.C_TIME',
								fieldType:'DATE',
								matchType:'GE',
								value:$("#searchStartstartTime").val()+" :00"
							});
						}
						if($("#searchEndstartTime").val()){
							conditions.push({
								field: 'p.C_TIME',
								fieldType:'DATE',
								matchType:'LE',
								value:$("#searchEndstartTime").val()+" :59"
							});
						}

						if($("#trainClassify").val()){
							conditions.push({
								field: 'p.C_CLASSIFY',
								fieldType:'STRING',
								matchType:'EQ',
								value:$("#trainClassify").val()
							});
						}
						
						if($("#trainRange").val()){
							conditions.push({
								field: 'p.C_RANGE',
								fieldType:'STRING',
								matchType:'EQ',
								value:$("#trainRange").val()
							});
						}
						
						if($("#trainStatus").val()){
							conditions.push({
								field: 'p.C_STATUS',
								fieldType:'STRING',
								matchType:'EQ',
								value:$("#trainStatus").val()
							});
						}

						trainDatatables.draw();
					};
					$('#btnReset').on('click',function(){
						unitIdCombotree.setValue();
						$('#searchtitle').val('')
						$('#searchStartstartTime').val('')
						$('#searchEndstartTime').val('')
						$('#trainStatus').val('')
						$("#trainStatus").trigger("chosen:updated");
						$('#trainRange').val('')
						$("#trainRange").trigger("chosen:updated");
						$('#trainClassify').val('')
						$("#trainClassify").trigger("chosen:updated");
					});
				});
			});
		</script>
	</body>
</html>