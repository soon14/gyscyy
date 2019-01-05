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
				<li class="active">场站运行日志查看</li>
			</ul><!-- /.breadcrumb -->
		</div>
		 <div class="page-content">
		 	<div class="tabbable"  style="margin-top:20px;">
		 		<ul class="nav nav-tabs" id="myTab">
		 			<li class="active">
			 			<a data-toggle="tab" href="#meetingDivWind" aria-expanded="true">
							<i class="green ace-icon fa fa-columns bigger-120"></i>
							日例会
						</a>
		 			</li>
		 			<li>
			 			<a data-toggle="tab" href="#runRecordWind" aria-expanded="false">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							工作日志
						</a>
		 			</li>
		 			<li>
			 			<a data-toggle="tab" href="#routeInspectDivWind" aria-expanded="false">
							<i class="green ace-icon fa fa-table bigger-120"></i>
							巡检记录
						</a>
		 			</li>
		 		</ul>
		 		
		 		<div style="float:right; margin-top:-35px;margin-right:5px;">
					<button id="btnBack" class="btn btn-xs btn-primary">
						<i class="fa fa-reply"></i>
						返回
					</button>
				</div>
		 		<div class="tab-content">
		 			<div id="meetingDivWind" class="tab-pane fade active in">
		 			</div>
		 			<div id="runRecordWind" class="tab-pane fade ">
		 			</div>	
		 			<div id="routeInspectDivWind" class="tab-pane fade ">			 			
		 			</div>	
		 		</div>
		 	</div>
		 </div>
<!--  		<div id="upnext"> -->
<!--         		</div> -->
			<script type="text/javascript">
				jQuery(function($){
					seajs.use(['datatables', 'confirm', 'dialog','upnextstrip'], function(A){
						var rlId = "${RLId}";
						console.log(rlId);
						A.loadPage({
							render: "#meetingDivWind",
							url: format_url("/safeMeeting/detailForWind/"+rlId)
						});
						$('#myTab a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
	 						//日例会
							if($(e.target).attr('href') == "#meetingDivWind"){
								A.loadPage({
									render: "#meetingDivWind",
									url: format_url("/safeMeeting/detailForWind/"+rlId)
								});
							}
							//运行记事
							if($(e.target).attr('href') == "#runRecordWind"){
								A.loadPage({
									render: "#runRecordWind",
									url : format_url("/runRecord/detailForWind/"+rlId)
								});
							}
// 							//巡检记录
							if($(e.target).attr('href') == "#routeInspectDivWind"){
								A.loadPage({
									render: "#routeInspectDivWind",
									url : format_url("/routeInspect/detailForWind/"+rlId)
								});
							}
						});
						$('#btnBack').on('click',function(){
							A.loadPage({
								render : '#page-container',
								url : format_url("/runLog/indexForWind")
							});
						});
						//上一条 下一条
// 						 new A.upnextstrip({
// 							render : "#upnext",
// 							url : format_url('/runLog/getDetail/0'),
// 							data: '${dedit}',
// 							options : {
								
// 							}
// 						}).render();
					});				
				});
			</script>
	</body>
</html>