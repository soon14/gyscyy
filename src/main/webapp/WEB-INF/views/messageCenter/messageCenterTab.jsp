<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
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
					消息管理
				</li>
				<li class="active">消息中心</li>
			</ul>
		</div>
		 <div class="page-content">
		 	<div class="tabbable"  style="margin-top:20px;">
		 		<ul class="nav nav-tabs">
		 			<li class="active">
			 			<a data-toggle="tab" href="#receiveBox" aria-expanded="true">
							<i class="green ace-icon fa fa-columns bigger-120"></i>
							收件箱
						</a>
		 			</li>
		 			<li>
			 			<a id="sendBoxLi" data-toggle="tab" href="#sendBox" aria-expanded="true">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							发件箱
						</a>
		 			</li>
		 		</ul>
		 		<div class="tab-content">
		 			<div id="receiveBox" class="tab-pane fade active in">
		 			</div>
		 			<div id="sendBox" class="tab-pane fade active in">
		 			</div>	
		 		</div>
		 	</div>
		 </div>

			<script type="text/javascript">
				jQuery(function($){
					seajs.use(['datatables', 'confirm', 'dialog'], function(A){
						A.loadPage({
							render: "#receiveBox",
							url: format_url("/messageCenter/recIndex")
						});
						
						//点击列表设置tab页时的点击事件
						$("#sendBoxLi").on('click', function(){
							A.loadPage({
								render : '#sendBox',
								url : format_url("/messageCenter/sendIndex")
							});
						});
					});				
				});
			</script>
	</body>
</html>