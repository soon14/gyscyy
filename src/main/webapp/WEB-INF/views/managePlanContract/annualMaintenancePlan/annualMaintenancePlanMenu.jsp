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
				<li class="active">计划管理</li>
				<li class="active">运维检修类计划</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
		<div id="tabbable" class="tabbable" >
		 		<ul class="nav nav-tabs" id="myTab">
		 			<li >
			 			<a  data-toggle="tab" href="#accidentMeasuresPlan" aria-expanded="false">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							年度反事故措施计划
						</a>
		 			</li>
		 			<li class="active">
			 			<a  data-toggle="tab" href="#annualMaintenancePlan" aria-expanded="true">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							年度检修维护计划
						</a>
		 			</li>

		 			<li >
			 			<a  data-toggle="tab" href="#rollingMaintencePlan" aria-expanded="false">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							三年滚动检修计划
						</a>
		 			</li>
		 			<li >
			 			<a  data-toggle="tab" href="#annualProdutionPlan" aria-expanded="false">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							年度生产物资计划
						</a>
		 			</li>
		 			<li >
			 			<a  data-toggle="tab" href="#monthlyProductionPlan" aria-expanded="false">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							月度生产物资计划
						</a>
		 			</li>
		 		</ul>
			<div class="tab-content" style='padding: 0px 12px 16px 0;'>
					<!-- 年度反事故措施计划 -->
 					<div id="accidentMeasuresPlan" class="tab-pane" ></div>
					<!-- 年度检修维护计划 -->
					<div id="annualMaintenancePlan" class="tab-pane fade active in"></div>
					<!-- 三年滚动检修计划-->
					<div id="rollingMaintencePlan" class="tab-pane"></div>
					<!-- 年度生产物资计划-->
					<div id="annualProdutionPlan" class="tab-pane"></div>
					<!-- 月度生产物资计划-->
					<div id="monthlyProductionPlan" class="tab-pane"></div>
			</div>
		</div>
        </div>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			var listFormDialog;
			var exportExcel = "";
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					test();
					function test(){
						A.loadPage({
							render: "#annualMaintenancePlan",
							url: format_url("/annualMaintenancePlan/index")
						});
					}
					var conditions=[];
					$('#myTab a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
						//年度反事故措施计划
						if($(e.target).attr('href') == "#accidentMeasuresPlan"){
							A.loadPage({
								render: "#accidentMeasuresPlan",
								url: format_url("/accidentMeasuresPlan/index")
							});
						}
						//年度检修维护计划 
						if($(e.target).attr('href') == "#annualMaintenancePlan"){
							A.loadPage({
								render: "#annualMaintenancePlan",
								url : format_url("/annualMaintenancePlan/index")
							});
						}
						//三年滚动检修计划
						if($(e.target).attr('href') == "#rollingMaintencePlan"){
							A.loadPage({
								render: "#rollingMaintencePlan",
								url : format_url("/rollingMaintencePlan/index")
							});
						}
						//年度培训计划
						if($(e.target).attr('href') == "#annualProdutionPlan"){
							A.loadPage({
								render: "#annualProdutionPlan",
								url : format_url("/annualProdutionPlan/index")
							});
						}
						//月度培训计划
						if($(e.target).attr('href') == "#monthlyProductionPlan"){
							A.loadPage({
								render: "#monthlyProductionPlan",
								url : format_url("/monthlyProductionPlan/index")
							});
						}
					});
				});
			});
        </script>
    </body>
</html>