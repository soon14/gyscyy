<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
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
						两票管理
					</li>
					<li class="active">紧急抢修工作票</li>
					<li class="active">查看</li>
				</ul>
		</div>
		
		<div class="col-md-12" >
		
		<div class="page-content">
		<div class="tabbable" style="margin-top: 20px;">
		 		<ul class="nav nav-tabs" id="myTab">
		 			<li class="active" >
			 			<a  data-toggle="tab" href="#workPjxxDetail" aria-expanded="true">
							<i class="green ace-icon fa fa-home bigger-120"></i>
							票据信息
						</a>
		 			</li>
<!-- 		 			<li> -->
<!-- 			 			<a  data-toggle="tab" href="#workCardDetail" aria-expanded="false"> -->
<!-- 							<i class="green ace-icon fa fa-list bigger-120"></i> -->
<!-- 							安全风险控制卡 -->
<!-- 						</a> -->
<!-- 		 			</li> -->
		 			<c:if test="${workStatus!=1&&workStatus!=8&&workStatus!=21&&todoTaskEntity.procDefId!=null}">
		 			<li>
			 			<a  data-toggle="tab" href="#splctDetail" aria-expanded="false">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							审批流程图
						</a>
		 			</li>
		 			<li>
			 			<a  data-toggle="tab" href="#spteptDetail" aria-expanded="false">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							审批流程步骤
						</a>
		 			</li>
		 			</c:if>
		 		</ul>
		 		<div style="float:right; margin-top:-35px;margin-right:55px;">
					<button id="btnBack" class="btn btn-xs btn-primary">
						<i class="fa fa-reply"></i>
						返回
					</button>
				</div>	
				<div class="tab-content">
				
				<%-- <input id="a" value="${todoTaskEntity.procDefId}" type="text"/>
				<input id="b" value="${todoTaskEntity.procInstId}" type="text"/> --%>
				
					<input id="workTicketId" value="${workTicketId}" type="hidden"/>
					<div id="workPjxxDetail" class="tab-pane fade active in" style="overflow-x:hidden;overflow-y: auto;height: 680px">
			 		</div>
					<!-- 风险卡开始 -->
					<div id="workCardDetail" class="tab-pane fade">
					</div>
					<!-- 风险卡结束 -->
					<div id="splctDetail" class="tab-pane fade">
						<iframe id ="iframeimage" src="" style="width:100%;height:1100px"; frameborder="0" scrolling="no" ></iframe>
		 			</div>
					<div id="spteptDetail" class="tab-pane fade">
					</div>
					
				</div>
					<!-- <div style="margin-right:100px;margin-top: 20px;"  >
	        		<button   id="printBtn"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
			    				<i class="glyphicon glyphicon-print"></i>
			    				打印
			    	</button>
	        		</div> -->
			</div><!-- tabbable -->
			</div><!-- page-content -->
        </div><!-- col-md-12 -->
        
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					var  workTicketId=$("#workTicketId").val();
					A.loadPage({
								render: "#workPjxxDetail",
								url: format_url("/repairTicket/workPjxxDetail/"+workTicketId)
					});
					var print="repair.rptdesign";
					$("#printBtn").on("click", function(e){
							birtPrint(print,workTicketId);
					});
					$('#myTab a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
 						//表单详细
						if($(e.target).attr('href') == "#workPjxxDetail"){
							print="repair.rptdesign";
							A.loadPage({
								render: "#workPjxxDetail",
								url: format_url("/repairTicket/workPjxxDetail/"+workTicketId)
							});
						}
 						//2
						if($(e.target).attr('href') == "#workCardDetail"){
							print="fxkzk.rptdesign";
							A.loadPage({
								render: "#workCardDetail",
								url: format_url("/repairTicket/workCardDetail/"+workTicketId)
							});
						}
						//流程图
						if($(e.target).attr('href') == "#splctDetail"){
							var url=format_url("/act/diagram-viewer/index.html?processDefinitionId=${todoTaskEntity.procDefId}&processInstanceId=${todoTaskEntity.procInstId}");
							$("#iframeimage").attr("src",url);
						} 
						//流程详细
						if($(e.target).attr('href') == "#spteptDetail"){
							A.loadPage({
								render : spteptDetail,
								url : format_url("/todoTask/processDetail/"+${todoTaskEntity.id_}+"/"+${todoTaskEntity.procInstId})
							});
						} 
 					 });
					
					
					$('#btnBack').on('click',function(){
						var rlId = "${rlId}";
						if(rlId==""){
							A.loadPage({
								render : '#page-container',
								url : format_url("/repairTicket/index")
							});
						}else {
							A.loadPage({
								render : '#page-container',
								url : format_url("/runLog/info/"+rlId)
							});	
						}
						
					});
				});
			});
        </script>
    </body>
</html>