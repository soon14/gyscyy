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
				<li class="active">年度生产指标计划</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
		<div id="tabbable" class="tabbable" >
		 		<ul class="nav nav-tabs" id="myTab">
		 			<li class="active">
			 			<a  data-toggle="tab" href="#quotaStatus" aria-expanded="true">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							年度生产指标流程
						</a>
		 			</li>
		 			<li>
			 			<a  data-toggle="tab" href="#quotaPlan" aria-expanded="false">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							年度生产指标计划
						</a>
		 			</li>
		 		</ul>
			<div class="tab-content" style='padding: 0px 12px 16px 0;'>
					<!-- 年度生产指标流程 -->
 					<div id="quotaStatus" class="tab-pane fade active in" ></div>
					<!-- 年度生产指标计划 -->
					<div id="quotaPlan" class="tab-pane"></div>
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
							render: "#quotaStatus",
							url: format_url("/quotaPlanApprove/index/3")
						});
					}
					var conditions=[];
					$('#myTab a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
						
						//年度生产指标流程
						if($(e.target).attr('href') == "#quotaStatus"){
							A.loadPage({
								render: "#quotaStatus",
								url: format_url("/quotaPlanApprove/index/3")
							});
						}
						//年度生产指标计划
						if($(e.target).attr('href') == "#quotaPlan"){
							
							A.loadPage({
								render: "#quotaPlan",
								url: format_url("/annualProductionCapacity/index/3")
							});
						}
					});
				});
			});
        </script>
    </body>
</html>