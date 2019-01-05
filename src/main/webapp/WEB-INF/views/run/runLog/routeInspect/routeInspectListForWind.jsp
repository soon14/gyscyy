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
	<div class="page-content">
	
	<div class="col-lg-12 col-md-12 col-sm-12 padding-zero search-content">
			<div class="form-inline text-left " role="form">
				 <div class="clearfix">
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="form-field-1">单位名称</label>：
                              <div id="searchunitIdRouteDiv"  class="inputWidth text-left padding-zero"></div>
                        </div>
                         <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="form-field-1">巡视记录</label>：
                            <div class="inputWidth padding-zero  text-left">
                              <select id="inspectTypeDiv" class="" name="protectWay"></select>
                              </div>
                        </div>
                        <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-zero" >
                            <label class="searchLabel" for="form-field-1">记录时间</label>：
                              <div class="form-group dateInputOther padding-zero text-left" >
                            	<div id="searchStartfindTimeRouteDiv"  style="border:none; padding:0px;"></div>
                            	</div>
                            <div class="toLabel" for="searchStartTimeRightDiv" >~</div>
		                    <div class="form-group dateInputOther padding-zero text-left">
		                        <div id="searchEndfindTimeRouteDiv"></div>
		                    </div>
                        </div>
                    </div>
				 <div class="clearfix">
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="form-field-1">巡检内容</label>：
                             <input id="recordContent" class="inputWidth text-left contentInput" placeholder="请输入巡检内容" type="text">
                        </div>
                         <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                           
                        </div>
                        <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-zero btnSearchBottom"style="text-align:right;" >
                            <button id="btnSearchRoute" class="btn btn-xs btn-primary" >
                                <i class="glyphicon glyphicon-search"></i>
                                查询
                            </button>
                            <button id="btnResetRoute" class="btn btn-xs btn-primary">
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
				<h5 class='table-title header smaller blue' >巡检记录</h5>
					<table id="routeInspect-table"
						class="table table-striped table-bordered table-hover"
						style="width: 100%;">
						<thead>
							<tr>
								<th style="display: none;">主键</th>
								<th class="center sorting_disabled" style="width: 50px;"><label
									class="pos-rel"> <input type="checkbox" class="ace" />
										<span class="lbl"></span>
								</label></th>
								<th>序号</th>
								<th>巡视记录</th>
								<th>记录时间</th>
								<th>单位名称</th>
								<th>巡检内容</th>
								<th>发现问题</th>
								<th>负责人</th>
								<!-- <th style="display: none;">附件URL</th>
								<th>附件</th> -->
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
			seajs
					.use(
							[ 'datatables', 'confirm', 'dialog', 'combobox',
									'combotree', 'my97datepicker' ],
							function(A) {
								var exportExcel="";
								var conditions = [];
								var rlId=${rlId};


								//部门控件下拉树
								var searchunitId = new A.combotree({
									render : "#searchunitIdRouteDiv",
									name : 'searchunitId',
									//返回数据待后台返回TODO
									datasource : ${routeInspectTreeList},
									width : "210px",
									options : {
										treeId : 'searchunitId',
										data : {
											key : {
												name : "name"
											},
											simpleData : {
												enable : true,
												idKey : "id",
												pIdKey : "parentId"
											}
										}
									}
								}).render();
								var protectWayCombobox = new A.combobox({
									render : "#inspectTypeDiv",
									datasource : ${inspectTypeCombobox},
									//multiple为true时select可以多选
									multiple : false,
									//allowBlank为false表示不允许为空
									allowBlank : true,
									options : {
										"disable_search_threshold" : 10,
										"width" : "100%"
									}
								}).render();
								var searchStartfindTime = new A.my97datepicker(
										{
											id : 'searchStartfindTime',
											name : 'searchStartfindTime',
											render : '#searchStartfindTimeRouteDiv',
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
									render : '#searchEndfindTimeRouteDiv',
									options : {
										isShowWeek : false,
										skin : 'ext',
										maxDate : "",
										minDate : "",
										dateFmt : "yyyy-MM-dd HH:mm"
									}
								}).render();

								var routeInspectDatatables = new A.datatables(
										{
											render : '#routeInspect-table',
											options : {
												"ajax" : {
													"url" : format_url("/routeInspect/searchData"),
													"contentType" : "application/json",
													"type" : "POST",
													"dataType" : "JSON",
													"data" : function(d) {
														conditions.push({
															field : 'C_RL_ID',
															fieldType : 'INT',
															matchType : 'EQ',
															value : ${rlId}
														});
														d.conditions = conditions;
														return JSON
																.stringify(d);
													}
												},
												multiple : true,
												ordering : true,
												optWidth : 80,

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
															data : "recordTypeName",
															width : "10%",
															orderable : true
														},
														{
															data : "recordTime",
															width : "10%",
															orderable : true
														},
														{
															data : "unitName",
															width : "10%",
															orderable : true
														},
														{
															data : "recordContent",
															width : "auto",
															orderable : true
														},
														{
															data : "findQuestion",
															width : "33%",
															orderable : true
														},
														{
															data : "fzr",
															width : "10%",
															orderable : true
														},
														/* {data:"attachmentUrl", "visible": false, orderable: false },
														{
															data : "attachmentName",
															width : "10%",
															orderable : true,
															 "render": function(data, type, row){
													                var html = "<div>";
													                  html+=  "<a  href="+format_url(row.attachmentUrl)+" target='_blank'  title='查看'  >"+row.attachmentName+"</a>";
													                return html;
													                }
														} */
														 ],
// 														 fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
// 															 if(exportExcel){
// 																 exportExcels(format_url("/routeInspect/exportExcel/"),JSON.stringify(conditions));
// 															 }
// 															 exportExcel="";
// 														 },
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
																				height : 650,
																				title : "巡检记录增加",
																				url : format_url('/routeInspect/getAddForWind/'
																						+ rlId),
																				closed : function() {
																					routeInspectDatatables
																							.draw(false);
																				}
																			})
																			.render()
																}
															}
														},
														/* {
															id : "hissel",
															label : "历史记录查询",
															icon : "fa fa-search-plus bigger-130",
															className : "btn-success",
															events : {
																click : function(
																		event) {
																	var rlId = ${rlId};
																	listFormDialog = new A.dialog(
																			{
																				width : 1150,
																				height : 671,
																				title : "巡检记录历史记录列表",
																				url : format_url("/routeInspect/hisSel/"),
																				closed : function() {
																					routeInspectDatatables
																							.draw(false);
																				}
																			})
																			.render()
																}
															}
														}, */
														{
															label : "删除",
															icon : "glyphicon glyphicon-trash",
															className : "btn-danger",
															events : {
																click : function(
																		event) {
																	var data = routeInspectDatatables
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
																	var url = format_url('/routeInspect/bulkDeleteForWind/');
																	
																			A.confirm(
																					'您确认删除么？',
																					function() {
																						
																								$.ajax({
																									url : url,
																									contentType : 'application/json',
																									dataType : 'JSON',
																									type : 'DELETE',
																									data : JSON
																											.stringify(ids),
																									success : function(
																											result) {
																										alert('删除成功');
																										routeInspectDatatables
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
															label : "导出",
															icon : "glyphicon glyphicon-download",
															className : "btn-primary",
															events : {
																click : function(
																		event) {
																	var rlId = ${rlId};
																	window.location.href = format_url("/routeInspect/exportExcelForWind/"
																			+ rlId);
																}
															}
														} ],
												btns : [
														/* {
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
																				height : 560,
																				title : "巡检记录编辑",
																				url : format_url('/routeInspect/getEdit/'
																						+ id),
																				closed : function() {
																					routeInspectDatatables
																							.draw(false);
																				}
																			})
																			.render();
																}
															}
														}, */
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
																	var url = format_url('/routeInspect/delForWind/'
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
																										routeInspectDatatables
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
								$('#btnSearchRoute')
										.on(
												'click',
												function() {
													conditions = [];
													conditions.push({
														field : 'C_RL_ID',
														fieldType : 'INT',
														matchType : 'EQ',
														value : ${rlId}
													});
													if ($(
															'#searchStartfindTime')
															.val()) {
														conditions
																.push({
																	field : 'a.C_RECORD_TIME',
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
																	field : 'a.C_RECORD_TIME',
																	fieldType : 'DATE',
																	matchType : 'LE',
																	value : $(
																			'#searchEndfindTime')
																			.val()
																});
													}
													if ($('#inspectTypeDiv')
															.val()) {
														conditions
																.push({
																	field : 'a.C_RECORD_TYPE',
																	fieldType : 'STRING',
																	matchType : 'EQ',
																	value : $(
																			'#inspectTypeDiv')
																			.val()
																});
													}
													if (searchunitId.getValue() != undefined
															&& searchunitId
																	.getValue()
																	+ "" != "") {
														conditions
																.push({
																	field : 'a.C_UNIT_ID',
																	fieldType : 'INT',
																	matchType : 'EQ',
																	value : searchunitId
																			.getValue()
																});
													}
													if ($('#recordContent').val()) {
														conditions
																.push({
																	field : 'a.C_RECORD_CONTENT',
																	fieldType : 'STRING',
																	matchType : 'LIKE',
																	value : $(
																			'#recordContent')
																			.val()
																});
													}
													if ($('#fzr').val()) {
														conditions
																.push({
																	field : 'fzr',
																	fieldType : 'STRING',
																	matchType : 'LIKE',
																	value : $(
																			'#fzr')
																			.val()
																});
													}
													routeInspectDatatables.draw();
												});
								$('#btnResetRoute').on('click',function() {
											searchunitId.setValue(undefined);
											$("#inspectTypeDiv").val("");
											$("#inspectTypeDiv").trigger("chosen:updated");
											$("#searchStartfindTime").val("");
											$("#searchEndfindTime").val("");
											$("#recordContent").val("");
											$("#fzr").val("");
										});
							});
		});
	</script>
</body>
</html>