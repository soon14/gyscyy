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

		<div class="row">
			<div class="col-xs-12">
				<!-- div.dataTables_borderWrap -->
				<div class="widget-main no-padding">
					<div id="tableTitleRoutInspect"></div>
					<input type="hidden" id="id" name="id" value="${entity.id}" />
					<table id="routeInspect-tableForWind"
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
								<th>记录类型</th>
								<th>记录时间</th>
								<th>单位名称</th>
								<th>巡检内容</th>
								<th>负责人</th>
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
							[ 'datatables'],
							function(A) {
								var exportExcel="";
								var conditions = [];
								var rlId=${rlId};
								$('#tableTitleRoutInspect').html('<h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;" >巡检记录</h5>');
								var routeInspectDatatables = new A.datatables(
										{
											render : '#routeInspect-tableForWind',
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
												order : [ [ 4, "desc" ] ],

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
															width : "15%",
															orderable : true
														},
														{
															data : "recordTime",
															width : "15%",
															orderable : true
														},
														{
															data : "unitName",
															width : "15%",
															orderable : true
														},
														{
															data : "recordContent",
															width : "33%",
															orderable : true
														},
														
														{
															data : "fzr",
															width : "15%",
															orderable : true
														}],
														btns:[{
															    id:"440",
																label:"查看",
																icon: "fa fa-binoculars bigger-110",
																className: "blue",
																events : {
																	click : function(event,
																			nRow,
																			nData) {
																		var id = nData.id;
																		listFormDialog = new A.dialog(
																				{
																					width : 850,
																					height : 530,
																					title : "巡检记录查看",
																					url : format_url('/routeInspect/singleDetail/'+id),
																					closed : function() {
																						routeInspectDatatables.draw(false);
																					}
																				})
																				.render()
																	}
																}
														}
														]
											}
										}).render();
							});
		});
	</script>
</body>
</html>