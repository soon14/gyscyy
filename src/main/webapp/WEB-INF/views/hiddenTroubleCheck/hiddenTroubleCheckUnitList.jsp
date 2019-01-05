<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
		<style>
			.dataTables_wrapper{
				margin-top:54px;
			}
		</style>
	</head>
	<body>
		<div class="breadcrumbs ace-save-state" id="breadcrumbs">
			<ul class="breadcrumb">
				<li>
					<i class="ace-icon fa fa-home home-icon"></i>
					<a href="javascript:void(0);" onclick="firstPage()">首页</a>
				</li>
				<li class="active">安全管理</li>
				<li class="active">隐患排查台账</li>
				<li class="active">隐患排查组织机构表</li>
			</ul>
		</div>
		<div class="page-content">
			<div class="col-lg-12 col-md-12 col-sm-12 search-content  padding-zero">
				<div class="form-inline text-left" role="form">
					<div class="clearfix" >
						<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
							<label class="searchLabel" for="searchNameDiv">公司名称</label>：
							<div class="padding-zero inputWidth  text-left">
								<select id="searchNameDiv" class="" ></select>
							</div>
						</div>
						<div class="form-group col-lg-12 col-md-12 col-sm-6 col-xs-12 padding-zero btnSearchBottom" style="text-align:right; ">
							<button id="btnSearch" class="btn btn-xs btn-primary">
								<i class="glyphicon glyphicon-search"></i>查询
							</button>
							<button id="btnReset" class="btn btn-xs btn-primary" >
								<i class="glyphicon glyphicon-repeat"></i>重置
							</button>
						</div>
					 </div>
				</div>
			</div>			
			<div class="row">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
						<h5 class='table-title header smaller blue' >隐患排查台账组织机构表</h5>
						<table id="troubleCheckUnit_table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
									<th>公司名称</th>
									<th>公司编码</th>
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
					//组织机构
					var nameCombobox = new A.combobox({
						render: "#searchNameDiv",
						datasource: ${unitList},
						multiple:false,
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					//数据表格
					var safeCheckUnitDatatables = new A.datatables({
						render: '#troubleCheckUnit_table',
						options: {
							"ajax": {
								"url": format_url("/hiddenTroubleCheck/searchUnitData"),
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
							order: [[ 0, "desc" ]],
							columns: [
								{data:"id", visible:false,orderable:false},
								{orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
										var startIndex = meta.settings._iDisplayStart;  
										row.start=startIndex + meta.row;
										return startIndex + meta.row + 1;  
								} },
								{data: "name",width: "auto",orderable: true},
								{data: "code",width: "auto",orderable: true},
							],
							toolbars: [], 
							btns: [	{
								id: "detail",
								label:"隐患排查台账填报",
								icon: "fa fa-cog bigger-130",
								className: "blue ",
								render: function(btnNode, data){
									var userUnitId=${userUnitId};
									var unitId=data.id;
									if(userUnitId!=unitId){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var name =  nData.name;
										A.loadPage({
											render : '#page-container',
											url : format_url("/hiddenTroubleCheck/index/"+ id + "/" + name)
										});
									}
								}
							}]
						}
					}).render();
					
					$('#btnSearch').on('click',function(){
						conditions=[];
						if ( $("#searchNameDiv").val() != undefined && $("#searchNameDiv").val() != "") {
							var unitId = $("#searchNameDiv").val();
							conditions.push({
								field : 'C_ID',
								fieldType : 'STRING',
								matchType : 'EQ',
								value : unitId
							});
						}
						safeCheckUnitDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						nameCombobox = new A.combobox({
							render: "#searchNameDiv",
							datasource: ${unitList},
							multiple:false,
							allowBlank: true,
							options:{
								"disable_search_threshold":10
							}
						}).render();
					});
				});
			});
		</script>
	</body>
</html>