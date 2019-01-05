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
				<li class="active">指标类计划</li>
				<li class="active">年度生产量计划</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
		<div id="tabbable" class="tabbable" >
		 		<ul class="nav nav-tabs" id="myTab">
		 			<li>
			 			<a  data-toggle="tab" href="#jituanStatus" aria-expanded="false">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							集团年度生产量流程
						</a>
		 			</li>
		 			<li class="active">
			 			<a  data-toggle="tab" href="#neikongStatus" aria-expanded="true">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							内控年度生产量流程
						</a>
		 			</li>
		 			<li>
			 			<a  data-toggle="tab" href="#jituanCapacity" aria-expanded="false">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							集团年度生产量计划
						</a>
		 			</li>
		 			<li>
			 			<a  data-toggle="tab" href="#neikongCapacity" aria-expanded="false">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							内控年度生产量计划
						</a>
		 			</li>
		 		</ul>
			<div class="tab-content" style='padding: 0px 12px 16px 0;'>
					<!-- 集团年度生产量流程 -->
 					<div id="jituanStatus" class="tab-pane" ></div>
					<!-- 内控年度生产量流程 -->
					<div id="neikongStatus" class="tab-pane fade active in"></div>
					<!-- 集团年度生产量计划 -->
 					<div id="jituanCapacity" class="tab-pane" ></div>
					<!-- 内控年度生产量计划 -->
					<div id="neikongCapacity" class="tab-pane"></div>
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
							render: "#neikongStatus",
							url: format_url("/quotaPlanApprove/index/2")
						});
					}
					var conditions=[];
					$('#myTab a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
						
						//集团年度生产量流程
						if($(e.target).attr('href') == "#jituanStatus"){
							A.loadPage({
								render: "#jituanStatus",
								url: format_url("/quotaPlanApprove/index/1")
							});
						}
						//内控年度生产量流程 
						if($(e.target).attr('href') == "#neikongStatus"){
							
							A.loadPage({
								render: "#neikongStatus",
								url: format_url("/quotaPlanApprove/index/2")
							});
						}
						//集团年度生产量计划
						if($(e.target).attr('href') == "#jituanCapacity"){
							A.loadPage({
								render: "#jituanCapacity",
								url: format_url("/annualProductionCapacity/index/1")
							});
						}
						//内控年度生产量计划 
						if($(e.target).attr('href') == "#neikongCapacity"){
							
							A.loadPage({
								render: "#neikongCapacity",
								url: format_url("/annualProductionCapacity/index/2")
							});
						}
					});
				});
			});
        </script>
    </body>
</html>