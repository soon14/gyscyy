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
				<li class="active">生产运营计划</li>
				<li class="active">技术质量类计划</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
		<div id="tabbable" class="tabbable" >
		 		<ul class="nav nav-tabs" id="myTab">
		 			<li class="active">
			 			<a  data-toggle="tab" href="#scienceTechnologyPlan" aria-expanded="true">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							年度科技计划
						</a>
		 			</li>
		 			<li >
			 			<a  data-toggle="tab" href="#annualTechnicalPlan" aria-expanded="false">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							年度技改计划
						</a>
		 			</li>

		 			<li >
			 			<a  data-toggle="tab" href="#annualSupervisionPlan" aria-expanded="false">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							年度技术监督计划
						</a>
		 			</li>
		 			<li >
			 			<a  data-toggle="tab" href="#annualTrainingPlan" aria-expanded="false">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							年度培训计划
						</a>
		 			</li>
		 		</ul>
			<div class="tab-content" style='padding: 0px 12px 16px 0;'>
					<!-- 年度反事故措施计划 -->
 					<div id="scienceTechnologyPlan" class="tab-pane fade active in" ></div>
					<!-- 年度技改计划 -->
					<div id="annualTechnicalPlan" class="tab-pane"></div>
					<!-- 年度技术监督计划-->
					<div id="annualSupervisionPlan" class="tab-pane"></div>
					<!-- 年度培训计划-->
					<div id="annualTrainingPlan" class="tab-pane"></div>
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
							render: "#scienceTechnologyPlan",
							url: format_url("/scienceTechnologyPlan/index")
						});
					}
					var conditions=[];
					$('#myTab a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
						//年度科技计划
						if($(e.target).attr('href') == "#scienceTechnologyPlan"){
							A.loadPage({
								render: "#scienceTechnologyPlan",
								url: format_url("/scienceTechnologyPlan/index")
							});
						}
						//年度技改计划
						if($(e.target).attr('href') == "#annualTechnicalPlan"){
							$('#accidentMeasuresPlan').show();
							A.loadPage({
								render: "#annualTechnicalPlan",
								url : format_url("/annualTechnicalPlan/index")
							});
						}
						//年度生产物资计划
						if($(e.target).attr('href') == "#annualSupervisionPlan"){
							A.loadPage({
								render: "#annualSupervisionPlan",
								url : format_url("/annualSupervisionPlan/index")
							});
						}
						//年度培训计划
						if($(e.target).attr('href') == "#annualTrainingPlan"){
							A.loadPage({
								render: "#annualTrainingPlan",
								url : format_url("/annualTrainingPlan/index")
							});
						}
					});
				});
			});
        </script>
    </body>
</html>