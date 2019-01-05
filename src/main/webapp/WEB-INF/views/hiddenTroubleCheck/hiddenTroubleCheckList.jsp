<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="breadcrumbs ace-save-state" id="breadcrumbs" style="margin-bottom:0px;">
			<ul class="breadcrumb">
				<li>
					<i class="ace-icon fa fa-home home-icon"></i>
					<a href="javascript:void(0);" onclick="firstPage()">首页</a>
				</li>
				<li class="active">安全管理</li>
				<li class="active">隐患排查台账</li>
				<li class="active">隐患排查台账填报</li>
			</ul>
		</div>
		<div class="page-content">
			<div class="col-lg-12 col-md-12 col-sm-12 search-content  padding-zero">
				<div class="form-inline text-left" role="form">
					<div class="clearfix" >
						<div class="form-group col-lg-12 col-md-3 col-sm-6 col-xs-12 padding-zero">
								<div class="col-lg-1  padding-zero text-left" style="width: 70px" >
									<label class="searchLabel" for="searchNameDiv">发现时间</label>：
								</div>
								<div class="col-lg-9 col-md-3 col-sm-6 col-xs-12 padding-zero">
									<div id="searchStartTime" style="float: left; width: 220px; margin-right: 10px;"></div>
									<label style="float: left;display:inline-block;margin-top:4px ">~</label>
									<div id="searchEndTime" style="float: left;width: 220px; margin-left: 10px; "></div>
								</div>
						</div>
						<div class="form-group col-lg-12 col-md-12 col-sm-6 col-xs-12 padding-zero btnSearchBottom" style="text-align:right; ">
							<button id="btnSearch" class="btn btn-xs btn-primary">
								<i class="glyphicon glyphicon-search"></i>查询</button>
							<button id="btnReset" class="btn btn-xs btn-primary" >
								<i class="glyphicon glyphicon-repeat"></i>重置</button>
						</div>
					</div>
				</div>
			</div>			
			<div class="row">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
						<h5 class='table-title header smaller blue' >隐患排查台账</h5>
						<table id="hiddenTroubleCheck_table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
									<th>隐患描述</th>
									<th>发现时间</th>
									<th>排查方式</th>
									<th>排查人</th>
									<th>整改措施</th>
									<th>计划整改时间</th>
									<th>整改责任人</th>
									<th>是否整改</th>
									<th>整改完成时间</th>
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
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					
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
					
					var hiddenTroubleCheckDatatables = new A.datatables({
						render: '#hiddenTroubleCheck_table',
						options: {
							"ajax": {
								"url": format_url("/hiddenTroubleCheck/searchData"),
								"contentType": "application/json",
								"type": "POST",
								"dataType": "JSON",
								"data": function (d) {
									conditions.push({
										field : 'C_UNIT_ID',
										fieldType : 'STRING',
										matchType : 'EQ',
										value : ${unitId}
									});
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
									{data: "discrible",width: "auto",orderable: true},
									{data: "discoveryTimeStr",width: "auto",orderable: true},
									{data: "modeName",width: "auto",orderable: true},
									{data: "checkPersionName",width: "auto",orderable: true},
									{data: "correctiveMeasures",width: "auto",orderable: true},
									{data: "planTimeStr",width: "auto",orderable: true},
									{data: "correctivePersionName",width: "auto",orderable: true},
									{data: "isCorrectiveName",width: "auto",orderable: true},
									{data: "completionTimeStr",width: "auto",orderable: true},
									{data: "remark",width: "auto",orderable: true}
							],
							toolbars: [{
								label:"新增",
								icon:"glyphicon glyphicon-plus",
								className:"btn-success",
								events:{
									click:function(event){
										listFormDialog = new A.dialog({
											width: '860',
											height: '600',
											title: "隐患排查台账增加",
											url:format_url('/hiddenTroubleCheck/getAdd/' + ${unitId}),
											closed: function(){
												hiddenTroubleCheckDatatables.draw(false);
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
										var data = hiddenTroubleCheckDatatables.getSelectRowDatas();
										var ids = [];
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												ids.push(data[i].id);
											}
										}
										if(ids.length < 1){
											alert('请选择要删除的数据');
											return;
										}
										var url = format_url('/hiddenTroubleCheck/bulkDelete/');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													hiddenTroubleCheckDatatables.draw(false);
												},
												error:function(v,n){
													alert('操作失败');
												}
											});
										});
									}
								}
							}, {
								label:"返回",
								icon:"glyphicon glyphicon-share-alt",
								className:"btn btn-xs btn-primary", 
								events:{
									click: function(event){
										A.loadPage({
				    						render : '#page-container',
				    						url : format_url("/hiddenTroubleCheck/unitList/")
				    					});
									}
								}
							}],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil bigger-130",
								className: "green edit",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										listFormDialog = new A.dialog({
											width: '850',
											height: '600',
											title: "隐患排查台账编辑",
											url:format_url('/hiddenTroubleCheck/getEdit/' + id),
											closed: function(){
												hiddenTroubleCheckDatatables.draw(false);
											}
										}).render();
									}
								}
							}, {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/hiddenTroubleCheck/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												success: function(result){
													alert('删除成功');
													hiddenTroubleCheckDatatables.draw(false);
												},
												error:function(v,n){
													alert('操作失败');
												}
											});
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
									listFormDialog = new A.dialog({
										width: '850',
										height: '600',
										title: "隐患排查台账查看",
										url:format_url('/hiddenTroubleCheck/getView/' + id),
										closed: function(){
											hiddenTroubleCheckDatatables.draw(false);
										}
									}).render();
								}
							}
						}]
						}
					}).render();
					
					$('#btnSearch').on('click',function(){
						conditions=[];
						//开始时间
						if ($("#startTime").val()!="") {
							var startTime = $("#startTime").val() + " 00:00:00";
							conditions.push({
								field: 'C_DISCOVERY_TIME',
								fieldType:'DATE',
								matchType:'GE',
								value:startTime
							});
						}
						//结束时间
						if ($("#endTime").val()!="") {
							var endTime = $("#endTime").val() + " 23:59:59";
							conditions.push({
								field: 'C_DISCOVERY_TIME',
								fieldType:'DATE',
								matchType:'LE',
								value:endTime
							});
						}
						hiddenTroubleCheckDatatables.draw();
					});
					$('#btnReset').on('click',function(){
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
					});
					
					//由添加迁移页返回到列表页
    				$("#backBtnUnitList").on("click",function(){
    					A.loadPage({
    						render : '#page-container',
    						url : format_url("/hiddenTroubleCheck/unitList/")
    					});
    				});
				});
			});
		</script>
	</body>
</html>