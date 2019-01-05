<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/views/common/meta.jsp"%>
</head>
<body>
<div class="breadcrumbs ace-save-state" id="breadcrumbs">
			<ul class="breadcrumb">
				<li>
					<i class="ace-icon fa fa-home home-icon"></i>
					<a href="javascript:void(0);" onclick="firstPage()">首页</a>
				</li>
				<li class="active">运行管理</li>
				<li class="active">运行检查</li>
				<li class="active">查看</li>
			</ul><!-- /.breadcrumb -->
		</div>
	<div class="page-content">
		<div class="col-lg-12 col-md-12 col-sm-12 search-content">
			<div class="form-inline " role="form" >
				<div class="clearfix">
					<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" >
						<label class="" for="searchdepict">检查结果：</label>
						<div class="form-group  input-width padding-zero  text-left">
							<select id="checkResultDiv" class="col-md-12" name="checkResult"></select>
						</div>
					</div>

					<div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12">
						<label class="" for="searchdepict">检查时间：</label>
						<div class="form-group date-input padding-zero text-left">
							<div id="searchStarttimeDiv" style="border:none; padding:0px;"></div>
						</div>
						<label style="width:2.6%;text-align:center">~</label>
						<div class="form-group date-input padding-zero text-left">
							<div id="searchEndtimeDiv" style="border:none; padding:0px;"></div>
						</div>
					</div>
					<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
						<label class="searchLabel" for="searchdepict">工作记录类型</label>：
						<div class="padding-zero inputWidth  text-left">
							<select id="typeDiv" class="col-md-12" name="type"></select>
						</div>
					</div>
				</div>
				<div class="clearfix">
<!-- 				<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="margin-left: -3%"> -->
<!-- 						<label class="" for="searchdepict">检查结果：</label> -->
<!-- 						<div class="form-group  input-width padding-zero  text-left"> -->
<!-- 							<select id="checkResultDiv" class="col-md-12" name="checkResult"></select> -->
<!-- 						</div> -->
<!-- 					</div> -->
					<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" >
						<label class="" for="searchdepict" style="margin-left:3%">检查人：</label> 
<!-- 						<input id="checkPerson"class="input-width   text-left" -->
<!-- 							placeholder="请输入检查人" type="text"> -->
							<div class="padding-zero inputWidth  text-left">
							<select id="checkPerson" class="col-md-12" name="checkPerson"></select>
						</div>
					</div>
					<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
						<label class="searchLabel" for="searchdepict">意见和建议</label>：
						 <input
							id="suggest"
							class="input-width text-left"
							placeholder="请输入意见和建议" type="text" >
					</div>
					<div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-zero btnSearchBottom" style="text-align:right; ">		
							<button id="btnSearch" class="btn btn-xs btn-primary">
								<i class="glyphicon glyphicon-search"></i> 查询
							</button>
							<button id="btnReset" class="btn btn-xs btn-primary" style="margin-right: -12px">
								<i class="glyphicon glyphicon-repeat"></i> 重置
							</button>
					</div>
				</div>
			</div>
		</div>
		<div style="float:right;">
				<button id="btnBack"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" style="    margin-top: 14px;">
        			<i class="fa fa-reply"></i>
        			返回
        		</button>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<!-- div.dataTables_borderWrap -->
				<div class="widget-main no-padding">
					<table id="runCheck_table"
						class="table table-striped table-bordered table-hover"
						style="width:100%;">
						<h5 class='table-title header smaller blue'>运行检查列表</h5>
						<thead>
							<tr>
								<th style="display:none;">主键</th>
								<th class="center sorting_disabled" style="width:50px;"><label
									class="pos-rel"> <input type="checkbox" class="ace" />
										<span class="lbl"></span>
								</label></th>
								<th>序号</th>
								<th>检查时间</th>
								<th>检查人</th>
								<th>工作记录类型</th>
								<th>检查结果</th>
								<th>意见和建议</th>
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
			seajs.use([ 'datatables', 'confirm', 'dialog', 'combobox','combotree', 'my97datepicker' ],
							function(A) {
								var conditions = [];
								var searchStartfindTime = new A.my97datepicker(
										{
											id : 'searchStartfindTime',
											name : 'searchStartfindTime',
											render : '#searchStarttimeDiv',
											options : {
												isShowWeek : false,
												skin : 'ext',
												maxDate : "",
												minDate : "",
												dateFmt : "yyyy-MM-dd HH:mm"
											}
										}).render();
								var searchEndfindTime = new A.my97datepicker({
									id : 'searchEndfindTime',
									name : 'searchEndfindTime',
									render : '#searchEndtimeDiv',
									options : {
										isShowWeek : false,
										skin : 'ext',
										maxDate : "",
										minDate : "",
										dateFmt : "yyyy-MM-dd HH:mm"
									}
								}).render();
								var checkResultCombobox = new A.combobox({
									render : "#checkResultDiv",
									datasource : ${runCheckCombobox},
									allowBlank : true,
									options : {
										"disable_search_threshold" : 10
									}
								}).render();
								var typeCombobox = new A.combobox({
									render : "#typeDiv",
									datasource : ${workRecordCombobox},
									allowBlank : true,
									options : {
										"disable_search_threshold" : 10
									}
								}).render();
								var checkPersonCombobox = new A.combobox({
									render : "#checkPerson",
									datasource : ${createPeopleCombobox},
									allowBlank : true,
									options : {
										"disable_search_threshold" : 10
									}
								}).render();
								var exportExcel = "";
								var rlId = ${rlId};
								var runCheckDatatables = new A.datatables(
										{
											render : '#runCheck_table',
											options : {
												"ajax" : {
													"url" : format_url("/runCheck/dataList/"+rlId),
													"contentType" : "application/json",
													"type" : "POST",
													"dataType" : "JSON",
													"data" : function(d) {
														d.conditions = conditions;
														return JSON
																.stringify(d);
													}
												},
												multiple : true,
												ordering : true,
												optWidth : 80,
												bStateSave: true,
												searching: true,
												order:[[2,"desc"]],
												columns : [
														{
															data : "id",
															visible : false,
															orderable : false
														},
														{
															orderable : false,
															"width" : "3%",
															"sClass" : "center",
															render : function(
																	data, type,
																	row, meta) {
																var startIndex = meta.settings._iDisplayStart;
																row.start = startIndex
																		+ meta.row;
																return startIndex
																		+ meta.row
																		+ 1;
															}
														},
														{
															data : "checkDate",
															name : "checkDate",
															width : "auto",
															orderable : true
														},
														{
															data : "checkPersonName",
															name : "checkPerson",
															width : "auto",
															orderable : true
														},
														{
															data : "typeName",
															name : "type",
															width : "auto",
															orderable : true
														},
														{
															data : "checkResultName",
															name : "checkResult",
															width : "auto",
															orderable : true
														}, {
															data : "suggest",
															width : "auto",
															orderable : true
														} ],
												fnPreDrawCallback : function(
														oSettings, iStart,
														iEnd, iMax, iTotal,
														sPre) {
													if (exportExcel) {
														window.location.href = format_url("/runCheck/exportExcel?rlId="
																+ rlId);
													}
													exportExcel = "";
												},
												toolbars : [
														{
															label : "新增",
															icon : "glyphicon glyphicon-plus",
															className : "btn-success",
															events : {
																click : function(
																		event) {
																	var rlId = ${rlId};
																	listFormDialog = new A.dialog(
																			{
																				width : 850,
																				height : 321,
																				title : "运行检查增加",
																				url : format_url('/runCheck/getAdd/'+ rlId),
																				closed : function() {
																					runCheckDatatables
																							.draw(false);
																				}
																			})
																			.render()
																}
															}
														},
														{
															label : "删除",
															icon : "glyphicon glyphicon-trash",
															className : "btn-danger",
															events : {
																click : function(
																		event) {
																	var data = runCheckDatatables
																			.getSelectRowDatas();
																	var ids = [];
																	if (data.length
																			&& data.length > 0) {
																		for (var i = 0; i < data.length; i++) {
																			ids
																					.push(data[i].id);
																		}
																	}
																	if (ids.length < 1) {
																		alert('请选择要删除的数据');
																		return;
																	}
																	var url = format_url('/runCheck/bulkDelete/');
																	A
																			.confirm(
																					'您确认删除么？',
																					function() {
																						$
																								.ajax({
																									url : url,
																									contentType : 'application/json',
																									dataType : 'JSON',
																									type : 'DELETE',
																									data : JSON
																											.stringify(ids),
																									success : function(
																											result) {
																										alert('删除成功');
																										runCheckDatatables
																												.draw(false);
																									},
																									error : function(
																											v,
																											n) {
																										alert('操作失败');
																									}
																								});
																					});
																}
															}
														},
														{
															id : "dc",
															label : "导出",
															icon : "glyphicon glyphicon-download",
															className : "btn-primary",
															events : {
																click : function(
																		event) {
																	$(
																			'#btnSearch')
																			.click();
																	exportExcel = "exportExcel";
																}
															}
														} ],
												btns : [
														{
															id : "edit",
															label : "修改",
															icon : "fa fa-pencil-square-o bigger-130",
															className : "green",
															events : {
																click : function(
																		event,
																		nRow,
																		nData) {
																	var id = nData.id;
																	listFormDialog = new A.dialog(
																			{
																				width : 850,
																				height : 471,
																				title : "运行检查编辑",
																				url : format_url('/runCheck/getEdit/'
																						+ id),
																				closed : function() {
																					runCheckDatatables
																							.draw(false);
																				}
																			})
																			.render();
																}
															}
														},
														{
															id : "delete",
															label : "删除",
															icon : "fa fa-trash-o bigger-130",
															className : "red del",
															events : {
																click : function(
																		event,
																		nRow,
																		nData) {
																	var id = nData.id;
																	var url = format_url('/runCheck/'
																			+ id);
																	A
																			.confirm(
																					'您确认删除么？',
																					function() {
																						$
																								.ajax({
																									url : url,
																									contentType : 'application/json',
																									dataType : 'JSON',
																									type : 'DELETE',
																									success : function(
																											result) {
																										alert('删除成功');
																										runCheckDatatables
																												.draw(false);
																									},
																									error : function(
																											v,
																											n) {
																										alert('操作失败');
																									}
																								});
																					});
																}
															}
														} ]
											}
										}).render();
								$('#btnSearch')
										.on(
												'click',
												function() {
													conditions = [];
													if ($(
															'#searchStartfindTime')
															.val()) {
														conditions
																.push({
																	field : 'T.C_CHECK_DATE',
																	fieldType : 'DATE',
																	matchType : 'GE',
																	value : $(
																			'#searchStartfindTime')
																			.val()
																});
													}
													if ($('#searchEndfindTime')
															.val()) {
														conditions
																.push({
																	field : 'T.C_CHECK_DATE',
																	fieldType : 'DATE',
																	matchType : 'LE',
																	value : $(
																			'#searchEndfindTime')
																			.val()
																});
													}
													if ($('#checkResultDiv')
															.val()) {
														conditions
																.push({
																	field : 'T.C_CHECK_RESULT',
																	fieldType : 'STRING',
																	matchType : 'EQ',
																	value : $(
																			'#checkResultDiv')
																			.val()
																});
													}
													if ($('#typeDiv').val()) {
														conditions
																.push({
																	field : 'T.C_TYPE',
																	fieldType : 'STRING',
																	matchType : 'EQ',
																	value : $(
																			'#typeDiv')
																			.val()
																});
													}
													if ($('#checkPerson').val()) {
														conditions
																.push({
																	field : 'T.C_CHECK_PERSON',
																	fieldType : 'STRING',
																	matchType : 'EQ',
																	value : $(
																			'#checkPerson')
																			.val()
																});
													}
													if ($('#suggest').val()) {
														conditions
																.push({
																	field : 'T.C_SUGGEST',
																	fieldType : 'STRING',
																	matchType : 'LIKE',
																	value : $(
																			'#suggest')
																			.val()
																});
													}
													runCheckDatatables.draw();
												});
								$('#btnReset').on(
										'click',
										function() {
											$("#checkResultDiv").val("");
											$("#checkResultDiv").trigger(
													"chosen:updated");
											$("#typeDiv").val("");
											$("#typeDiv").trigger(
													"chosen:updated");
											$("#searchStartfindTime").val("");
											$("#searchEndfindTime").val("");
											$("#checkPerson").val("");
											$("#checkPerson").trigger(
											"chosen:updated");
											$("#suggest").val("");
										});
								//返回
								$('#btnBack').on('click',function(){
										A.loadPage({
											render : '#page-container',
											url : format_url("/runLog/indexForRunCheck/")
										});
								});
							});
		});
	</script>
</body>
</html>