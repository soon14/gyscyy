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
					集控运行日志
				</li>
				<li class="active">集控运行日志填报</li>
			</ul><!-- /.breadcrumb -->
		</div>
		 <div class="page-content">
		 	<div class="tabbable"  style="margin-top:20px;">
		 		<ul class="nav nav-tabs" id="myTab">
		 			<li class="active">
			 			<a data-toggle="tab" href="#meetingDiv" aria-expanded="true">
							<i class="green ace-icon fa fa-columns bigger-120"></i>
							日例会
						</a>
		 			</li>
		 			<li>
			 			<a id="runRecord" data-toggle="tab" href="#runRecordDiv" aria-expanded="false">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							运行记事
						</a>
		 			</li>
		 			<li>
			 			<a id="runWay" data-toggle="tab" href="#runWayDiv" aria-expanded="false">
							<i class="green ace-icon fa fa-table bigger-120"></i>
							运行方式
						</a>
		 			</li>		 
		 			<li>
			 			<a id="joinLand" data-toggle="tab" href="#joinLandDiv" aria-expanded="false">
							<i class="green ace-icon fa fa-table bigger-120"></i>
							接地线（刀闸）
						</a>
		 			</li>
		 			<li>
			 			<a id="joinRecord" data-toggle="tab" href="#joinRecordDiv" aria-expanded="false">
							<i class="green ace-icon fa fa-table bigger-120"></i>
							交接班记录
						</a>
		 			</li>
		 			<li>
			 			<a id="twoTicket" data-toggle="tab" href="#twoTicketDiv" aria-expanded="false">
							<i class="green ace-icon fa fa-table bigger-120"></i>
							两票记录
						</a>
		 			</li>
		 			<li>
			 			<a id="dispatchCommand" data-toggle="tab" href="#dispatchCommandDiv" aria-expanded="false">
							<i class="green ace-icon fa fa-table bigger-120"></i>
							调度命令
						</a>
		 			</li>	
<!-- 		 			<li> -->
<!-- 			 			<a id="routeInspection" data-toggle="tab" href="#routeInspectDiv" aria-expanded="false"> -->
<!-- 							<i class="green ace-icon fa fa-table bigger-120"></i> -->
<!-- 							巡检记录 -->
<!-- 						</a> -->
<!-- 		 			</li> -->
<!-- 		 			<li> -->
<!-- 			 			<a id="runCheck2" data-toggle="tab" href="#runCheck2Div" aria-expanded="false"> -->
<!-- 							<i class="green ace-icon fa fa-table bigger-120"></i> -->
<!-- 							运行检查 -->
<!-- 						</a> -->
<!-- 		 			</li> 					 								 			 -->
		 		</ul>
		 		
		 		<div style="float:right; margin-top:-35px;margin-right:25px;">
					<button id="btnBack" class="btn btn-xs btn-primary">
						<i class="fa fa-reply"></i>
						返回
					</button>
				</div>
		 		<div class="tab-content">
		 			<div id="meetingDiv" class="tab-pane fade  active in">
		 			</div>
		 			<div id="runRecordDiv" class="tab-pane fade">
		 			</div>	
		 			<div id="runWayDiv" class="tab-pane fade ">
		 			</div>			 			
		 			<div id="joinLandDiv" class="tab-pane fade ">			 			
		 			</div>	
		 			<div id="joinRecordDiv" class="tab-pane fade ">			 			
		 			</div>	
		 			<div id="twoTicketDiv" class="tab-pane fade ">			 			
		 			</div>
		 			<div id="dispatchCommandDiv" class="tab-pane fade ">			 			
		 			</div>	
		 			<div id="routeInspectDiv" class="tab-pane fade ">			 			
		 			</div>	
<!-- 		 			<div id="runCheck2Div" class="tab-pane fade ">			 			 -->
<!-- 		 			</div>		 				 			 -->
		 		</div>
		 	</div>
		 </div>

			<script type="text/javascript">
				jQuery(function($){
					seajs.use(['datatables', 'confirm', 'dialog'], function(A){
						var rlId = "${RLId}";
						A.loadPage({
							render: "#meetingDiv",
							url: format_url("/safeMeeting/index/"+rlId)
						});
						//运行记事
// 						A.loadPage({
// 							render: "#runRecordDiv",
// 							url : format_url("/runRecord/index/"+rlId)
// 						});
						$('#myTab a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
							//日例会
							if($(e.target).attr('href') == "#meetingDiv"){
								A.loadPage({
									render: "#meetingDiv",
									url: format_url("/safeMeeting/index/"+rlId)
								});
							}
							//运行记事
							if($(e.target).attr('href') == "#runRecordDiv"){
								A.loadPage({
									render: "#runRecordDiv",
									url : format_url("/runRecord/index/"+rlId)
								});
							}
							//运行方式
							if($(e.target).attr('href') == "#runWayDiv"){
								A.loadPage({
									render: "#runWayDiv",
									url : format_url("/runWay/index/"+rlId)
								});
							}
							//接地线
							if($(e.target).attr('href') == "#joinLandDiv"){
								A.loadPage({
									render: "#joinLandDiv",
									url : format_url("/joinLand/index/"+rlId)
								});
							}
							//交接班
							if($(e.target).attr('href') == "#joinRecordDiv"){
								A.loadPage({
									render: "#joinRecordDiv",
									url : format_url("/runLog/detail/"+rlId)
								});
							}
							//两票记录(edit)
							if($(e.target).attr('href') == "#twoTicketDiv"){
								A.loadPage({
									render: "#twoTicketDiv",
									url : format_url("/runLog/workTicket/"+rlId)
								});
							}
							//调度命令
							if($(e.target).attr('href') == "#dispatchCommandDiv"){
								A.loadPage({
									render: "#dispatchCommandDiv",
									url : format_url("/dispaCom/disComList/"+rlId)
								});
							}
							//巡检记录
							if($(e.target).attr('href') == "#routeInspectDiv"){
								A.loadPage({
									render: "#routeInspectDiv",
									url : format_url("/routeInspect/index/"+rlId)
								});
							}
							//运行检查
// 							if($(e.target).attr('href') == "#runCheck2Div"){
// 								A.loadPage({
// 									render: "#runCheck2Div",
// 									url : format_url("/runCheck/index/"+rlId)
// 								});
// 							}
						});

						$('#btnBack').on('click',function(){
							A.loadPage({
								render : '#page-container',
								url : format_url("/runLog/index/")
							});
						});
					});				
				});
			</script>
	</body>
</html>