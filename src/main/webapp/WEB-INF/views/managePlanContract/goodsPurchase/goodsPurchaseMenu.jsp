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
				<li class="active">计划经营管理</li>
				<li class="active">设备物资采购计划</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
		<div id="tabbable" class="tabbable" >
		 		<ul class="nav nav-tabs" id="myTab">
		 			<li class="active">
			 			<a  data-toggle="tab" href="#goodsPurchase" aria-expanded="true">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							零星采购
						</a>
		 			</li>
		 			<li >
			 			<a  data-toggle="tab" href="#yearPurchase" aria-expanded="false">
							<i class="green ace-icon fa fa-home bigger-120"></i>
							年度采购
						</a>
		 			</li>

		 		</ul>
			<div class="tab-content" style='padding: 0px 12px 16px 0;'>
					<!-- 零星采购 -->
					<div id="goodsPurchase" class="tab-pane fade active in"></div>
					<!-- 年度采购 -->
 					<div id="yearPurchase" class="tab-pane" ></div>
				
			</div>
		</div>
        </div>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			var listFormDialog;
			var exportExcel = "";
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var unitId =${unitId};
					test();
					function test(){
						A.loadPage({
							render: "#goodsPurchase",
							url: format_url("/goodsPurchase/index/"+unitId)
						});
					}
					var conditions=[];
					$('#myTab a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
						//年度采购
						if($(e.target).attr('href') == "#yearPurchase"){
							A.loadPage({
								render: "#yearPurchase",
								url: format_url("/yearPurchase/index/"+unitId)
							});
						}
						//物资采购
						if($(e.target).attr('href') == "#goodsPurchase"){
							A.loadPage({
								render: "#goodsPurchase",
								url : format_url("/goodsPurchase/index/"+unitId)
							});
						}
				
					});
				});
			});
        </script>
    </body>
</html>