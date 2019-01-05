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
					两票管理
				</li>
				<li class="active">两票统计</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class="col-xs-12 search-content">
				<div class="form-inline" role="form">
					<div class="form-group">
						<label class="" for="form-field-1">统计时间：</label>
					</div>
					<div class="form-group">
						<div id="searchstatisticsDateDiv"  style="width:175px;border:none; padding:0px;"></div>
					</div>

					<div class="form-group">
						<label class="" for="form-field-1">单位名称：</label>
					</div>
					<div class="form-group">
						 <div id="searchunitDiv" style="width: 160px"></div>
					</div>

					<div class="form-group" style="float:right; margin-right:30px;">
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
			<div class="row">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<h5 class="table-title-withoutbtn header smaller blue " style="margin-bottom:0px;" id="table_title">
						<label id="table_titile">“两票”情况统计表</label>
						<div style="float:right; margin-right:40px;">
							<button id="btnimport" class="btn btn-xs btn-primary">
								<i class="ace-icon glyphicon glyphicon-download"></i>
								导出
							</button>
						</div>
					</h5>
					<div class="widget-main no-padding" id="defectStatistics-print" style="overflow-x:scroll; height:500px">
						<table id="ticketStatisticsTable" class="table table-striped table-bordered table-hover" style="width:2600px;">
							<thead>
								<tr>
									<th rowspan="2" style="text-align:center;min-width:160px">票据种类</th>
									<th colspan="4" style="text-align:center">1月</th>
									<th colspan="4" style="text-align:center">2月</th>
									<th colspan="4" style="text-align:center">3月</th>
									<th colspan="4" style="text-align:center">4月</th>
									<th colspan="4" style="text-align:center">5月</th>
									<th colspan="4" style="text-align:center">6月</th>
									<th colspan="4" style="text-align:center">7月</th>
									<th colspan="4" style="text-align:center">8月</th>
									<th colspan="4" style="text-align:center">9月</th>
									<th colspan="4" style="text-align:center">10月</th>
									<th colspan="4" style="text-align:center">11月</th>
									<th colspan="4" style="text-align:center">12月</th>
									<th colspan="4" style="text-align:center">全年合计</th>
								</tr>
								<tr>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率</th>
									<th style="text-align:center;min-width:66px">张数</th>
									<th style="text-align:center;min-width:66px">合格</th>
									<th style="text-align:center;min-width:66px">不合格</th>
									<th style="text-align:center;min-width:66px">合格率</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
		<form id="downloadForm" class="hide" method="post" action="${ctx}/ticketStatistics/exprotExcel">
			<input type="hidden" name="name" id="downloadName">
			<input type="hidden" name="path" id="downloadPath">
		</form>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					
					var conditions=[];
					//统计时间
					var searchstatisticsDate = new A.my97datepicker({
						id: 'searchstatisticsDate',
						name:'searchstatisticsDate',
						render:'#searchstatisticsDateDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy"
						}
					}).render();
					
					//单位名称下拉树
					var searchunitId = new A.combotree({
						render: "#searchunitDiv",
						name: 'searchunitId',
						//返回数据待后台返回TODO
						datasource: ${unitNameIdTreeList},
						width:"210px",
						options: {
							treeId: 'searchunitId',
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

					//发送ajax请求参数取得方法，返回选择值
					function getAjaxParam () {
						//统计时间
						var searchTime = $("#searchstatisticsDate").val();
						//单位名称
						var unitId = searchunitId.getValue();
						if (unitId == undefined) {
							unitId = ""
						}
						var param = {};
						param.searchTime = searchTime;
						param.unitId = unitId;

						return param;
					}
					var param = getAjaxParam ();
					var dataTables = new A.datatables({
						render: '#ticketStatisticsTable',
						options: {
							"ajax": {
								"url": format_url("/ticketStatistics/searchData"),
								"contentType": "application/json",
								"type": "POST",
								"dataType": "JSON",
								"data": function (d) {
									d.param = param;
									return JSON.stringify(d);
								}
							},
							multiple : true,
							checked:false,
							bInfo:false,
							paging:false,
							ordering:false,
							columns: [{data: "ticketTypeName",width: "auto",orderable: false ,"sClass": "center"},
									{data: "ticketCount_1",width: "auto",orderable: false,"sClass": "center"},
									{data: "qualifiedCount_1",width: "auto",orderable: false,"sClass": "center"}, 
									{data: "unQualifiedCount_1",width: "auto",orderable: false,"sClass": "center"},
									{data: "qualifiedRate_1",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_2",width: "auto",orderable: false,"sClass": "center"},
									{data: "qualifiedCount_2",width: "auto",orderable: false,"sClass": "center"}, 
									{data: "unQualifiedCount_2",width: "auto",orderable: false,"sClass": "center"},
									{data: "qualifiedRate_2",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_3",width: "auto",orderable: false,"sClass": "center"},
									{data: "qualifiedCount_3",width: "auto",orderable: false,"sClass": "center"}, 
									{data: "unQualifiedCount_3",width: "auto",orderable: false,"sClass": "center"},
									{data: "qualifiedRate_3",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_4",width: "auto",orderable: false,"sClass": "center"},
									{data: "qualifiedCount_4",width: "auto",orderable: false,"sClass": "center"}, 
									{data: "unQualifiedCount_4",width: "auto",orderable: false,"sClass": "center"},
									{data: "qualifiedRate_4",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_5",width: "auto",orderable: false,"sClass": "center"},
									{data: "qualifiedCount_5",width: "auto",orderable: false,"sClass": "center"}, 
									{data: "unQualifiedCount_5",width: "auto",orderable: false,"sClass": "center"},
									{data: "qualifiedRate_5",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_6",width: "auto",orderable: false,"sClass": "center"},
									{data: "qualifiedCount_6",width: "auto",orderable: false,"sClass": "center"}, 
									{data: "unQualifiedCount_6",width: "auto",orderable: false,"sClass": "center"},
									{data: "qualifiedRate_6",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_7",width: "auto",orderable: false,"sClass": "center"},
									{data: "qualifiedCount_7",width: "auto",orderable: false,"sClass": "center"}, 
									{data: "unQualifiedCount_7",width: "auto",orderable: false,"sClass": "center"},
									{data: "qualifiedRate_7",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_8",width: "auto",orderable: false,"sClass": "center"},
									{data: "qualifiedCount_8",width: "auto",orderable: false,"sClass": "center"}, 
									{data: "unQualifiedCount_8",width: "auto",orderable: false,"sClass": "center"},
									{data: "qualifiedRate_8",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_9",width: "auto",orderable: false,"sClass": "center"},
									{data: "qualifiedCount_9",width: "auto",orderable: false,"sClass": "center"}, 
									{data: "unQualifiedCount_9",width: "auto",orderable: false,"sClass": "center"},
									{data: "qualifiedRate_9",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_10",width: "auto",orderable: false,"sClass": "center"},
									{data: "qualifiedCount_10",width: "auto",orderable: false,"sClass": "center"}, 
									{data: "unQualifiedCount_10",width: "auto",orderable: false,"sClass": "center"},
									{data: "qualifiedRate_10",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_11",width: "auto",orderable: false,"sClass": "center"},
									{data: "qualifiedCount_11",width: "auto",orderable: false,"sClass": "center"}, 
									{data: "unQualifiedCount_11",width: "auto",orderable: false,"sClass": "center"},
									{data: "qualifiedRate_11",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_12",width: "auto",orderable: false,"sClass": "center"},
									{data: "qualifiedCount_12",width: "auto",orderable: false,"sClass": "center"}, 
									{data: "unQualifiedCount_12",width: "auto",orderable: false,"sClass": "center"},
									{data: "qualifiedRate_12",width: "auto",orderable: false,"sClass": "center"},
									{data: "ticketCount_13",width: "auto",orderable: false,"sClass": "center"},
									{data: "qualifiedCount_13",width: "auto",orderable: false,"sClass": "center"}, 
									{data: "unQualifiedCount_13",width: "auto",orderable: false,"sClass": "center"},
									{data: "qualifiedRate_13",width: "auto",orderable: false,"sClass": "center"},
							],
							
						}
					}).render();
					//查询按钮
					$('#btnSearch').on('click',function(){
						//统计时间
						var searchTime = $("#searchstatisticsDate").val();
						//单位名称
						var unitId = searchunitId._displayNames;
						var yearText = "";
						if (searchTime == "") {
							var date = new Date();
							var year = date.getFullYear();
							yearText = year + "年";
						} else {
							yearText = searchTime + "年";
						}
						var unitText = "";
						if (unitId != "" && unitId != undefined) {
							unitText = unitId;
						}
						$("#table_titile").html(yearText + unitText + "“两票”情况统计表");
						param = getAjaxParam ();
						dataTables.draw();
					});
					//重置按钮
					$('#btnReset').on('click',function(){
						//单位名称下拉树
						searchunitId.setValue(undefined); 
						searchunitId._displayNames = "";
						//统计时间
						$("#searchstatisticsDate").val("");
						conditions=[];
					});
					//导出按钮
					$('#btnimport').on('click',function(){
						$("#btnSearch").click();

						var param = getAjaxParam ();
						var url = "${ctx}/ticketStatistics/createDataExcelFile";
					 	$.ajax({
							type: 'POST',
							contentType : 'application/json',
							url: url,
							data : JSON.stringify(param),
							dataType : "JSON",
							cache: false,
							success: function(datas){
								if(datas.tempFileName.length >0) { 
									var excelName = $("#table_titile").html();
									$("#downloadName").val(excelName);
									$("#downloadPath").val(datas.tempFileName);
					 				$("#downloadForm").submit();
								}
							},
							error: function(datas){
							}
						}); 
					});
					
					function initPage() {
						//统计时间
						var searchTime = $("#searchstatisticsDate").val();
						//单位名称
						var unitId =  searchunitId._displayNames;
						var yearText = "";
						if (searchTime == "") {
							var date = new Date();
							var year = date.getFullYear();
							yearText = year + "年";
						} else {
							yearText = searchTime + "年";
						}
						var unitText = "";
						if (unitId != "" && unitId != undefined) {
							unitText = unitId;
						} else {
							 unitText = "";
						}
						$("#table_titile").html(yearText + unitText + "“两票”情况统计表");
					}
					initPage();
				});
			});
        </script>
    </body>
</html>