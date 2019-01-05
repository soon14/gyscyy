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
			<input id="appId" name="appId" type="hidden" value="${RLId}" class="col-md-12">
			<ul class="breadcrumb">
				<li>
					<i class="ace-icon fa fa-home home-icon"></i>
					<a href="javascript:void(0);" onclick="firstPage()">首页</a>
				</li>

				<li>
					运行管理
				</li>
				<li>
					场站运行日志
				</li>
				<li class="active">场站运行日志填报</li>
			</ul><!-- /.breadcrumb -->
		</div>
		 <div class="page-content">
		 	<div class="tabbable"  style="margin-top:20px;">
		 		<ul class="nav nav-tabs" id="myTabWind">
		 			<li class="active">
			 			<a data-toggle="tab" href="#meetingDivWind" aria-expanded="true">
							<i class="green ace-icon fa fa-columns bigger-120"></i>
							日例会
						</a>
		 			</li>
		 			<li>
			 			<a id="runRecord" data-toggle="tab" href="#runRecordWind" aria-expanded="false">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							工作日志
						</a>
		 			</li>
		 			<li>
			 			<a id="routeInspection" data-toggle="tab" href="#routeInspectDivWind" aria-expanded="false">
							<i class="green ace-icon fa fa-table bigger-120"></i>
							巡检记录
						</a>
		 			</li>
		 		</ul>
		 		
		 		<div style="float:right; margin-top:-35px;margin-right:25px;">
					<button id="btnBack" class="btn btn-xs btn-primary">
						<i class="fa fa-reply"></i>
						返回
					</button>
				</div>
		 		<div class="tab-content">
		 			<div id="meetingDivWind" class="tab-pane fade  active in">
		 			</div>
		 			<div id="runRecordWind" class="tab-pane fade">
		 			</div>	
		 			<div id="routeInspectDivWind" class="tab-pane fade ">			 			
		 			</div>	
		 		</div>
		 	</div>
		 </div>
			<script type="text/javascript">
				jQuery(function($){
					seajs.use(['datatables', 'confirm', 'dialog'], function(A){
						var rlId = "${RLId}";
						A.loadPage({
							render: "#meetingDivWind",
							url: format_url("/safeMeeting/indexForWind/"+rlId)
						});
						$('#myTabWind a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
							//日例会
// 							if($(e.target).attr('href') == "#meetingDivWind"){
// 								A.loadPage({
// 									render: "#meetingDivWind",
// 									url: format_url("/safeMeeting/indexForWind/"+rlId)
// 								});
// 							}
							//工作日志
							if($(e.target).attr('href') == "#runRecordWind"){
								A.loadPage({
									render: "#runRecordWind",
									url : format_url("/runRecord/indexForWind/"+rlId)
								});
							}
							//巡检记录
							if($(e.target).attr('href') == "#routeInspectDivWind"){
								A.loadPage({
									render: "#routeInspectDivWind",
									url : format_url("/routeInspect/indexForWind/"+rlId)
								});
							}
						});

						$('#btnBack').on('click',function(){
							A.loadPage({
								render : '#page-container',
								url : format_url("/runLog/indexForWind")
							});
						});
					});				
				});
			</script>
	</body>
</html>