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
				<li>安全管理</li>
				<li>资质管理</li>
				<li class="active">${targetType}</li>
			</ul>
		</div>
		<div class="page-content">
				<div class="col-lg-12 col-md-12 col-sm-12 padding-zero search-content">
					<div class="form-inline text-left " role="form">
						<div class="clearfix">
							<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
								<label class="searchLabel" for="searchSignUnitIdDiv">名称</label>：
								<div class="padding-zero inputWidth  text-left" style="width: 63%">
										<input id="searchAssetName" class="input-width text-left" style="width: 100%" placeholder="请输入名称" type="text"></input>
								</div>
							</div>
							<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
								<label class="searchLabel" for="searchTargetDateDiv">姓名</label>：
								<div class="form-group dateInputOther padding-zero text-left" style="width: 63%">
									<input id="searchName" class="input-width text-left" style="width: 100%" placeholder="请输入姓名" type="text"></input>
								</div>
							</div>
							<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero" >
								<label class="searchLabel" for="searchNameDiv">证书</label>：
								<div class="form-group dateInputOther padding-zero text-left" style="width: 63%">
									<input id="searchCertificate" class="input-width text-left" style="width: 100%" placeholder="请输入证书" type="text"></input>
								</div>
							</div>
							<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero btnSearchBottom"style="text-align:right;" >
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
						<h5 class='table-title header smaller blue' >${targetType}</h5>
						<input class="col-md-12" id="type" name="type"  value="${type}" type="hidden">
						<table id="assetManage_table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
									<th>姓名</th>
									<th>证书</th>
									<th>名称</th>
									<th>编号</th>
									<th>单位</th>
									<th>有效期</th>
									<th>填写人</th>
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
					
					function getYear() {
						var today = new Date();
						var strYear = today.getFullYear();
						return strYear+"";
					}
					
					//年号
					var yearNumPickerDiv = new A.my97datepicker({
							id:"searchYearNum",
							render : '#searchYearDiv',
							options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy"
						},
					}).render();

					//默认类型
					var searchTypeCombo = new A.combobox({
						render : "#searchTypeDiv",
						datasource : ${defualtTypeCombobox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					
					var assetManageDatatables = new A.datatables({
						render: '#assetManage_table',
						options: {
							"ajax": {
								"url": format_url("/assetManagement/searchData"),
								"contentType": "application/json",
								"type": "POST",
								"dataType": "JSON",
								"data": function (d) {
									conditions.push({
										field: 'C_TYPE',
										fieldType:'STRING',
										matchType:'EQ',
										value:$('#type').val()
									});
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
									{data: "certificate",width: "auto",orderable: true}, 
									{data: "assetName",width: "auto",orderable: true}, 
									{data: "code",width: "auto",orderable: true}, 
									{data: "unitName",name:"unitId",width: "auto",orderable: true}, 
									{data: "time", width: "auto",orderable: true},
									{data: "createrName",name:"creater",width: "auto",orderable: true}
							],
							fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
								if(exportExcel){
									exportExcels(format_url("/assetManagement/exportExcel"),JSON.stringify(conditions));
								}
								exportExcel="";
							},
							toolbars: [{
								label:"新增",
								icon:"glyphicon glyphicon-plus",
								className:"btn-success",
								events:{
									click:function(event){
										var type = $("#type").val();
										listFormDialog = new A.dialog({
											width:870 ,
											height:500 ,
											title: "${targetType}增加",
											url:format_url('/assetManagement/gotoAddPage/' + type),
											closed: function(){
												assetManageDatatables.draw(false);
											}	
										}).render()
									}
								}
							}, {
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = assetManageDatatables.getSelectRowDatas();
										var ids = [];
										var loginUser = '${loginUser.id}';
										var loginName = '${loginUser.loginName}'
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												if (data[i].creater != loginUser && loginName!="super") {
													alert('记录中包含不是当前登陆人的记录不能删除!');
													return;
												}
												ids.push(data[i].id);
												
												
											}
										}
										if(ids.length < 1){
											alert('请选择要删除的数据');
											return;
										}
										var url = format_url('/assetManagement/allDelete/2');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													assetManageDatatables.draw(false);
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
									if(data.buttonDisplayFlag != true){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										listFormDialog = new A.dialog({
											width: 900,
											height:500 ,
											title: "${targetType}修改",
											url:format_url('/assetManagement/gotoEditPro/' + id),
											closed: function(){
												assetManageDatatables.draw(false);
											}
										}).render();
									}
								}
							}, {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								render: function(btnNode, data){
									if(data.buttonDisplayFlag != true){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/assetManagement/deleteOne/'+ id+"/2");
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												success: function(result){
													alert('删除成功');
													assetManageDatatables.draw(false);
												},
												error:function(v,n){
													alert('操作失败');
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
									listFormDialog = new A.dialog({
										width: 870,
										height:500 ,
										title: "${targetType}查看",
										url:format_url('/assetManagement/gotoViewPro/' + id),
										closed: function(){
											assetManageDatatables.draw(false);
										}
									}).render();
								}
							}
						}]
						}
					}).render();
					$('#btnSearch').on('click',function(){
						conditions=[];
						if ($("#searchName").val()!="") {
							conditions.push({
								field: 't.C_NAME',
								fieldType:'STRING',
								matchType:'LIKE',
								value:$("#searchName").val().trim()
							});
						}
						if ($("#searchAssetName").val()!="") {
							conditions.push({
								field: 'C_ASSET_NAME',
								fieldType:'STRING',
								matchType:'LIKE',
								value:$("#searchAssetName").val().trim()
							});
						}
						if ($("#searchCertificate").val()!="") {
							conditions.push({
								field: 'C_CERTIFICATE',
								fieldType:'STRING',
								matchType:'LIKE',
								value:$("#searchCertificate").val().trim()
							});
						}
						if ($('#searchYearNum').val()) {
							conditions.push({
								field: 'C_YEAR',
								fieldType:'STRING',
								matchType:'EQ',
								value:$('#searchYearNum').val()
							});
						}
						if ($('#searchTypeDiv').val()) {
							conditions.push({
								field: 'C_DEFUALT_TYPE',
								fieldType:'STRING',
								matchType:'EQ',
								value:$('#searchTypeDiv').val()
							});
						}
						assetManageDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						conditions=[];
						$("#searchName").val("");
						$("#searchCertificate").val("");
						$("#searchAssetName").val("");
					});
				}); 
			});
		</script>
	</body>
</html>