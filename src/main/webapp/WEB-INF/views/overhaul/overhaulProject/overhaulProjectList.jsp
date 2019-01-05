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
					<a href="#">检修管理</a>
				</li>
				<li class="active"><a href="#">检修计划</a></li>
				<li class="active">检修项目</li>
			</ul><!-- /.breadcrumb -->
		</div>
         	<div class="page-content">
		 		<div class="row">
		 			<div class="col-xs-12">
						<!-- div.dataTables_borderWrap -->
						<div class="widget-main no-padding">
							<h5 class="table-title header smaller lighter blue " style="margin-bottom:0px;">检修计划</h5>
							<form id="overhaulProject" class="form-horizontal" role="form">
								<table id="overhaulProject-table" class="table table-striped table-bordered table-hover" style="width:100%;">
									<thead>
										<tr>
											<th style="display:none;">主键</th>
											<th>项目名称</th>
	                                		<th>项目明细</th>
	                                		<th>列入原因</th>
	                                		<th>方案措施</th>
	                                		<th>开工时间</th>
	                                		<th>完成时间</th>
	                                		<th>计划总金额</th>
                                    		<th> </th>
										</tr>
									</thead>
								</table>
							</form>
						</div>
					</div>
				</div>
						
				<div class="">
				<button id="btnBack" class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
					<i class="ace-icon fa fa-times"></i>
					取消
				</button>
				
				<button id="btnSave" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i>
					保存
				</button>
			</div>	
	</div>		 	
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			var listFormDialog;
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					overhaulProjectDatatables = new A.datatables({
						render: '#overhaulProject-table',
						options: {
					        /* "ajax": {
					            "url": format_url("/overhaulProject/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        }, 
					        multiple : true,
							ordering: true,*/
							serverSide: false,
					        multiple : true,
					        checked:false,
							bInfo:false,
							paging:false,
							ordering:true,
							columns: [
								{data:"id", visible:false,orderable:false},
							 {data: "projectName",width: "15%",orderable: true, editType:"input"}, 
							 {data: "projecDetail",width: "15%",orderable: true, editType:"input"}, 
							 {data: "projectReason",width: "15%",orderable: true, editType:"input"}, 
							 {data: "measure",width: "15%",orderable: true, editType:"input"}, 
							 {data: "startDate",width: "10%",orderable: false,editType:"my97datepicker", cfg:{
									name: 'startDate',
									options : {
											isShowWeek : false,
											skin : 'ext',
											maxDate: "",
											minDate: "",
											dateFmt: 'yyyy-MM-dd HH:mm:ss'
									}
								}},
							 {data: "endDate",width: "10%",orderable: false, editType:"my97datepicker", cfg:{
									name: 'endDate',
									options : {
											isShowWeek : false,
											skin : 'ext',
											maxDate: "",
											minDate: "",
											dateFmt: 'yyyy-MM-dd HH:mm:ss'
									}
								}},
							 {data: "totalMoney",width: "10%",orderable: true, editType:"input"}
							 ],
							optWidth:50,
							toolbars: [
								{
									label:"新增",
									icon: "glyphicon glyphicon-plus",
									className:"btn-success",
									events:{
										click: function(event){
											overhaulProjectDatatables.addRow({"id":null, "projectName":"", "projecDetail":"","projectReason":"", "measure":"", "startDate":null, "endDate":null, "totalMoney":""});
										}
									}
							  	 }
							   ],
							btns:[
								{
									id:"delete",
									label:"删除",
									icon: "fa fa-trash-o bigger-130",
									className: "red del",
									events:{
										"click": function(event, nRow, nData){
											var id = nData.id;
											A.confirm('您确认删除么？',function(){
												overhaulProjectDatatables.deleteSelectRow(nRow);
											});		
										}
									}
								}
							]
						}
					}).render();
					function initDataTable(){
						var params = {};
						params.length = 10;
						params.start = 0;
						params.draw = 0;
						params.conditions = [];
						$.ajax({
							url: format_url("/overhaulProject/search"),
							contentType: "application/json",
							dataType: 'JSON',
							type: 'POST',
							data : JSON.stringify(params),
							success: function(result){
								overhaulProjectDatatables.addRows(result.data);
							}
							
						})
					};
					
					initDataTable();
					$('#overhaulProject').validate({
						debug:true,
						rules: {
							projectName: {
								required: true,
								maxlength: 10
							},
							projecDetail: {
								required: true,
								maxlength: 10,
							},
							projectReason: {
								required: true,
							},
							measure:{
								required: true
							},
							/*  startDate: {
								required: true,
							},
							endDate:{
								required: true
							}, */
							totalMoney:{
								required: true
							}
						},
				
						submitHandler: function (form) {
							var url = format_url("/overhaulProject");
							var str="";
							var obj = $("#overhaulProject").serializeObject();
							var resultList= [];
							

							for(var key in obj){
							
								for(var i = 0; i<obj[key].length; i ++){
									if(resultList.length != obj[key].length){
										var result={};
										result[key] = obj[key][i];
										resultList.push(result);
									}else{
										resultList[i][key] = obj[key][i];
									}
								}
							}
						}
					});
					$("#btnSave").on("click", function(){
						$("#overhaulProject").submit();
					});
					$("#btnBack").on('click',function(){
						A.loadPage({
							render : '#page-container',
							url : format_url("/overhaulPlan/index")
						});
					});
				});
				
			});
        </script>
    </body>
</html>