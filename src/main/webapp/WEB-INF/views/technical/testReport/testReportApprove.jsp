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
				                <input id="id" name="id" value="${testReportEntity.id}" type="hidden">
				                <input id="testMember" name="testMember"  type="hidden" value="${testReportEntity.testMember}" class="col-md-12">
			   <div class="form-group">
			   <label class="col-md-2 control-label no-padding-right">
					    填报人
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="userName" name="userName" type="text" readonly="readonly" placeholder="" maxlength="20" value="${ sysUser.name }">
					    <input class="col-md-12" id="userId" name="userId" type="hidden" readonly="readonly" placeholder="" maxlength="20" value="${ sysUser.id }">
					</div>
					<label class="col-md-2 control-label no-padding-right">
					    填报单位
				    </label>
				    <div class="col-md-4">
				    <input class="col-md-12" id="unitId" name="unitId" readonly="readonly" type="text" placeholder="" maxlength="20" value="${ sysUnitEntity.name }">
					</div>
				    
				</div>
			   <div class="form-group">
			   	<label class="col-md-2 control-label no-padding-right">
					    项目名称
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="name" name="name" type="text" placeholder="" readonly="readonly" maxlength="20" value="${ testReportEntity.name }">
					</div>
				    <label class="col-md-2 control-label no-padding-right">
					    试验人员
				    </label>
				    <div class="col-md-4">
				    <select id="testMemberDiv" class="col-md-12" name="testMember" disabled="disabled"></select>	
<%-- 				    <input class="col-md-12" id="testMember" readonly="readonly" name="testMember" type="text" placeholder="" maxlength="20" value="${ testReportEntity.testMember }"> --%>
				    </div>	
				    
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    填报时间
				    </label>
				    <div class="col-md-4">
				    <input class="col-md-12" id="time" name="time" readonly="readonly" type="text" placeholder="" maxlength="20" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${testReportEntity.time}" type="both"/>">
				    </div>	
				    
				</div>
			   <div class="form-group">
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

			<c:if test="${nodeList.authFactorCode=='sjbBtn'}">
				<button id="sjbBtn" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 生技部审核
				</button>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='sjbzrBtn'}">
				<button id="sjbzrBtn" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 生技部主任审核
				</button>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='sjldBtn'}">
				<button id="sjldBtn" class="btn btn-xs btn-success pull-right"
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
					var testReportEntityId = ${testReportEntity.id}
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${testReportEntity.fileId},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true,
					}).render();
					var testMembers = $("#testMember").val().split(",");
					//combobox组件
					var btmPersonsIdsCombobox = new A.combobox({
						render: "#testMemberDiv",
						datasource:${testReportCombobox},
						multiple:true,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						},
						initValue:testMembers
					}).render();
					
					
					//生技部审核
					$('#sjbBtn').on('click',function() {
						workSafeOneDialog = new A.dialog(
								{
									width : 1000,
									height : 400,
									title : "生技部审核",
									url : format_url("/testReport/getAddSJB?testReportEntityId="
											+ testReportEntityId
											+ "&taskId="
											+ taskId),
									closed : function() {
									}
								}).render();	
					});
					//生技部审核
					$('#sjbzrBtn').on('click',function() {
						workSafeOneDialog = new A.dialog(
								{
									width : 1000,
									height : 400,
									title : "生技部主任审核",
									url : format_url("/testReport/getAddSJBZR?testReportEntityId="
											+ testReportEntityId
											+ "&taskId="
											+ taskId),
									closed : function() {
									}
								}).render();	
					});
					//上级领导审核
					$('#sjldBtn').on('click',function() {
						workSafeOneDialog = new A.dialog(
								{
									width : 1000,
									height : 400,
									title : "上级领导审核",
									url : format_url("/testReport/getAddSJLD?testReportEntityId="
											+ testReportEntityId
											+ "&taskId="
											+ taskId),
									closed : function() {
									}
								}).render();	
					});
					
					//废票
					$('#zfBtn').on('click',function() {
							var obj = $("#workTicketForm").serializeObject();
							$.ajax({
										url : format_url("/testReport/disAgreeFp?taskId="
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
														url : format_url("/testReport/getAddZtj?testReportEntityId="
																+ testReportEntityId
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