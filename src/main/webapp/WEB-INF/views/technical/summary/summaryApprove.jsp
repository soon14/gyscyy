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
		<div class="col-md-12" >
		<div class="page-content">
		<div class="tabbable" >
		 		<ul class="nav nav-tabs" id="myTab">
		 			<li class="active">
			 			<a   data-toggle="tab" href="#workitem" aria-expanded="true">
							<i class="green ace-icon fa fa-home bigger-120"></i>
							基础信息
						</a>
		 			</li>
		 		</ul>
				<div class="tab-content">
				<div id="workitem" class="tab-pane fade active in" style="overflow-x:hidden;overflow-y: auto;height: 600px">
				<form class="form-horizontal" role="form" style="margin-right:100px;" id="workTicketForm">
				                <input type="hidden" id="id" name="id" value="${summaryEntity.id}" />
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">填报单位</label>
									<div class="col-md-4">
										<input type="text" id="unitName" name="unitName" class="col-md-12"  readonly="readonly" value="${sysUnitEntity.name}"></input>
								    </div>
									<label class="col-md-2 control-label no-padding-right">填报人</label>
									<div class="col-md-4">
										<input type="text" id="tbrName" name="tbrName" class="col-md-12" readonly="readonly" value="${summaryEntity.tbrName}"></input>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">填报时间</label>
									<div class="col-md-4">
										<input type="text" id="time" name="time" class="col-md-12" readonly="readonly" value="${summaryEntity.timeString}"></input>
									</div>
									<label class="col-md-2 control-label no-padding-right">事件名称</label>
									<div class="col-md-4">
										<input type="text" id="sjmc" name="sjmc" class="col-md-12" readonly="readonly" value="${summaryEntity.sjmc}"></input>
									</div>
								</div>
							
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">总结</label>
									<div class="col-md-10">
										<textarea id="zjnr" name="zjnr" readonly="readonly" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${summaryEntity.zjnr}</textarea>
									</div>
								</div>
								
								<div class="form-group form-horizontal">
									<label class="col-md-2 control-label no-padding-right">附件</label>
									<div class="col-md-10" id="divfile">
									</div>
								</div>
						</form>	
		    	</div>
    		</div>
    	</div>
    </div>
</div>
<div class="col-md-12">
<div style="margin-right:10px;margin-top: 20px;">
		<c:forEach items="${nodeList}" var="nodeList" varStatus="vs">
			<c:if test="${nodeList.authFactorCode=='zfBtn'}">
				<button id="zfBtn" class="btn btn-xs btn-danger pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 作废
				</button>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='ztjBtn'}">
				<button id="ztjBtn" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 再提交
				</button>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='biotechSpBtn'}">
				<button id="biotechSPBtn" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 生技处审核
				</button>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='biotechManageSpBtn'}">
				<button id="biotechManageSPBtn" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 生技部主任审核
				</button>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='leaderSpBtn'}">
				<button id="leaderSPBtn" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 上级领导审核
				</button>
			</c:if>
		</c:forEach>

	</div>
</div>
<script type="text/javascript">
			var workSafeOneDialog;
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','selectbox','uploaddropzone'], function(A){
					
					var taskId = $("#taskId").val();
					var procInstId = $("#procInstId").val();
					var procDefId = $("#procDefId").val();
					var summaryId = ${summaryEntity.id};
					
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${summaryEntity.fileid},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true,
					}).render();
					
					//生技处审批
					$('#biotechSPBtn').on('click',function() {
						workSafeOneDialog = new A.dialog(
								{
									width : 1000,
									height : 400,
									title : "生技处审核",
									url : format_url("/summary/getAddBiotech?summaryId="
											+ summaryId
											+ "&taskId="
											+ taskId),
									closed : function() {
									}
								}).render();	
					});
					//生技部主任审批
					$('#biotechManageSPBtn').on('click',function() {
						workSafeOneDialog = new A.dialog(
								{
									width : 1000,
									height : 400,
									title : "生技主任审核",
									url : format_url("/summary/getAddBiotechManage?summaryId="
											+ summaryId
											+ "&taskId="
											+ taskId),
									closed : function() {
									}
								}).render();	
					});
					//上级领导审批
					$('#leaderSPBtn').on('click',function() {
						workSafeOneDialog = new A.dialog(
								{
									width : 1000,
									height : 400,
									title : "上级领导审核",
									url : format_url("/summary/getAddLeader?summaryId="
											+ summaryId
											+ "&taskId="
											+ taskId),
									closed : function() {
									}
								}).render();	
					});
					
					//废票
					$('#zfBtn').on('click',function() {
							var obj = $("#workTicketForm").serializeObject();
							obj.id = ${summaryEntity.id};
							$.ajax({
										url : format_url("/summary/disAgreeFp?taskId="
												+ taskId
												+ "&procInstId="
												+ procInstId
												+ "&selectUser="
												+ ""),
										contentType : 'application/json',
										dataType : 'JSON',
										data : JSON.stringify(obj),
										cache : false,
										type : 'POST',
										success : function(result) {
											if (result.result == "success") {
												alert('作废成功');
												window.scrollTo(0,0);
												$("#page-container").load(format_url('/todoTask/list/1/10'));
											} else {
												alert(result.result);
											}
										},
										error : function(v, n) {
											alert('作废失败');
										}
									});
					});
								//再提交
								$('#ztjBtn').on('click',function() {
												 workSafeOneDialog = new A.dialog({
														width : 1000,
														height : 400,
														title : "负责人提交",
														url : format_url("/technical/getAddZtj?summaryId="
																+ summaryId
																+ "&taskId="
																+ taskId),
														closed : function() {
														}
													}).render();	
								});
					
					
					});
			});
        </script>
    </body>
</html>