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
		<div class="col-md-12">
			<form class="form-horizontal" role="form"  id="approveForm">
			<input class="col-md-12" id="id" name="id" type="hidden" value="${id}">
			<input class="col-md-12" id="processStatus" name="processStatus" type="hidden" value="${processStatus}">
			<div>
				   <h5 class='table-title header smaller blue' style="margin-bottom:0px;">基础信息</h5>
				   <div style="margin: 7% 0 0 -10%">
			  	    <div class="form-group" style="margin-top: 30px">
					    <label class="col-md-2 control-label no-padding-right">
							报告编码
					    </label>
					    <div class="col-md-4">
					    <input class="col-md-12" id="reportCode" name="reportCode" type="text" placeholder="" maxlength="64" value="${equipAbnormalReportEntities.reportCode}" readonly="readonly">
	                    </div>
	                    <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;" >*</span>报告名称
						</label>
						<div class="col-md-4">
		                   <input class="col-md-12" id="reportName" name="reportName" type="text" placeholder="" maxlength="64" value="${equipAbnormalReportEntities.reportName}" readonly="readonly">
	                	</div>
	               </div>
			       <div class="form-group">
			       		 <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;" >*</span>设备编号
					    </label>
					    <div class="col-md-4">
							<input class="col-md-12" id="equipCodeDiv" name="equipCodeDiv" type="text" placeholder="" maxlength="64" value="${equipAbnormalReportEntities.equipCode}" readonly="readonly">
	                    </div>
						<label class="col-md-2 control-label no-padding-right">
							设备名称
						</label>
						<div class="col-md-4">
		                   <input class="col-md-12" id="equipName" name="equipName" type="text" placeholder="" maxlength="64" value="${equipAbnormalReportEntities.equipName}" readonly="readonly">
	                	</div>
	               </div>
			       <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;" >*</span>提交时间
						</label>
						<div class="col-md-4">
		                   <div id="submitDateDiv"></div>
		                   <input class="col-md-12" id="submitDate" name="submitDate" type="text"  value="<fmt:formatDate value="${equipAbnormalReportEntities.submitDate}" type="both"  pattern="yyyy-MM-dd" />" readonly="readonly"  >
	                	</div>
		       		    <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;" >*</span>提交人
					    </label>
					    <div class="col-md-4">
							<input class="col-md-12" id="submitPersionName" name="submitPersionName" type="text"   value="${equipAbnormalReportEntities.submitPersionName}" readonly="readonly"  >
	                    </div>
	               </div>
	               <div class="form-group">
	               <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;" >*</span>设备所属
					    </label>
	               <div class="col-md-4">
		                    <input class="col-md-12" type="text" id="equipBelong" name="equipBelong" value="${equipAbnormalReportEntities.equipBelong }" readonly="readonly"  placeholder="" maxlength="64">
	                    </div>
	               </div>
			       <div class="form-group">
						 <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;" >*</span>执行情况说明
					    </label>
					    <div class="col-md-10">
							<textarea class="col-md-12" id="excuteInfoExplain" name="excuteInfoExplain" type="text"  maxlength="128" style="height:100px; resize:none;" readonly="readonly">${equipAbnormalReportEntities.excuteInfoExplain}</textarea>
	                    </div>
	               </div>
			       <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">
							备注
						</label>
						<div class="col-md-10">
		                   <textarea class="col-md-12" id="remark" name="remark" type="text"  maxlength="128" style="height:100px; resize:none;" readonly="readonly">${equipAbnormalReportEntities.remark}</textarea>
	                	</div>
	               </div>
			       <div class="form-group">
					    <label class="col-md-2 control-label no-padding-right">
							附件
					    </label>
						<div class="col-md-10" id="basefielid"  ></div>
	               </div>
	               </div>
	               <div style="margin-right:100px;">
    			<c:forEach items="${nodeList}" var="nodeList" varStatus="vs">
	    			<!--送领导审批按钮 -->
<%-- 	    			<c:if test="${nodeList.authFactorCode=='sbydldsh'}"> --%>
<!-- 		        		<button  id="sbydldsh"  title="提交领导审批"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;" type="button"> -->
<!-- 		    				<i class="ace-icon glyphicon glyphicon-level-up"></i> -->
<!-- 		    				领导审核 -->
<!-- 				    	</button> -->
<%-- 	    			</c:if>  --%>
	    			<!--送执行人执行按钮 -->
<%-- 	    			<c:if test="${nodeList.authFactorCode=='sbydzxr'}"> --%>
<!-- 		        		<button  id="sbydzxr"  title="提交执行人"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;" type="button"> -->
<!-- 		    				<i class="ace-icon glyphicon glyphicon-ok"></i> -->
<!-- 		    				执行人 -->
<!-- 				    	</button> -->
<%-- 	    			</c:if>  --%>
    				<!--生技部人员驳回  -->
	    			<c:if test="${nodeList.authFactorCode=='sbydbgsjbrybh'}">
		        		<button  id=sbydbgsjbrybh  title="驳回"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;" type="button">
		    				<i class="ace-icon glyphicon glyphicon-remove-circle"></i>
		    				驳回
				    	</button>
	    			</c:if> 
    				<!--生技部主任驳回  -->
	    			<c:if test="${nodeList.authFactorCode=='sbydbgsjbzrbh'}">
		        		<button  id=sbydbgsjbzrbh  title="驳回"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;" type="button">
		    				<i class="ace-icon glyphicon glyphicon-remove-circle"></i>
		    				驳回
				    	</button>
	    			</c:if> 
    				<!--生产分管领导驳回  -->
	    			<c:if test="${nodeList.authFactorCode=='sbydbgscfgldbh'}">
		        		<button  id=sbydbgscfgldbh  title="驳回"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;" type="button">
		    				<i class="ace-icon glyphicon glyphicon-remove-circle"></i>
		    				驳回
				    	</button>
	    			</c:if> 
	    			<!--检修、集控专工及以上同意 -->
	    			<c:if test="${nodeList.authFactorCode=='sbydbgjxjkzgty'}">
		        		<button  id="sbydbgjxjkzgty"  title="审批通过"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;" type="button">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				同意
				    	</button>
	    			</c:if> 
	    			<!--生技部人员同意 -->
	    			<c:if test="${nodeList.authFactorCode=='sbydbgsjbryty'}">
		        		<button  id="sbydbgsjbryty"  title="审批通过"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;" type="button">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				同意
				    	</button>
	    			</c:if> 
	    			<!--生技部主任同意 -->
	    			<c:if test="${nodeList.authFactorCode=='sbydbgsjbzrty'}">
		        		<button  id="sbydbgsjbzrty"  title="审批通过"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;" type="button">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				同意
				    	</button>
	    			</c:if> 
	    			<!--分管领导同意执行按钮 -->
	    			<c:if test="${nodeList.authFactorCode=='sbydbgtyzx'}">
		        		<button  id="sbydbgtyzx"  title="同意执行"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;" type="button">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				同意执行
				    	</button>
	    			</c:if> 
	    			<!--分管领导不同意执行按钮 -->
	    			<c:if test="${nodeList.authFactorCode=='sbydbgbtyzx'}">
		        		<button  id="sbydbgbtyzx"  title="不同意执行"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;" type="button">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				不同意执行
				    	</button>
	    			</c:if> 
	    			<!--申请人再提交按钮 -->
<%-- 	    			<c:if test="${nodeList.authFactorCode=='sbydztj'}"> --%>
<!-- 		        		<button  id="sbydztj"  title="重新发起流程"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;" type="button"> -->
<!-- 		    				<i class="ace-icon glyphicon glyphicon-ok"></i> -->
<!-- 		    				再提交 -->
<!-- 				    	</button> -->
<%-- 	    			</c:if>  --%>
	    			<!--申请人结票按钮 -->
<%-- 	    			<c:if test="${nodeList.authFactorCode=='sbydjp'}"> --%>
<!-- 		        		<button  id="sbydjp"  title="流程结束"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;" type="button"> -->
<!-- 		    				<i class="ace-icon glyphicon glyphicon-remove-sign"></i> -->
<!-- 		    				取消流程 -->
<!-- 				    	</button> -->
<%-- 	    			</c:if>  --%>
    			</c:forEach>
        	</div>
               </div>
            </form>
    			<script type="text/javascript">
    		var listFormDialog,approveIdea;
    		var executeUserName ="${equipAbnormalEntities.executeUserName}";
    		var finishinfo,finishStartDate,finishEndDate,finishFielId,executeUserId,executeUserName;
    		var processStatus = ${equipAbnormalReportEntities.processStatus};
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone','selectbox'], function(A){
					//附件上传
					var baseuploaddropzone =new A.uploaddropzone({
						render : "#basefielid",
						fileId:${equipAbnormalReportEntities.fileId},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks:true,//显示删除按钮
						chargeUp:true
					}).render();
					//生技部人员驳回
    				$("#sbydbgsjbrybh").on("click", function(e){
    					$("#sbydbgsjbrybh").attr({"disabled":"disabled"});
    					reject();
    				});
					//生技部主任驳回
    				$("#sbydbgsjbzrbh").on("click", function(e){
    					$("#sbydbgsjbzrbh").attr({"disabled":"disabled"});
    					reject();
    				});
					//生产分管领导驳回
    				$("#sbydbgscfgldbh").on("click", function(e){
    					$("#sbydbgscfgldbh").attr({"disabled":"disabled"});
    					reject();
    				});
					//驳回
					function reject(){
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					$.ajax({
							url : format_url("/equipAbnormalReport/reject/10"),
							contentType : 'application/json',
							dataType : 'JSON',
							type : 'POST',
							data : JSON.stringify(obj),
							success: function(result){
								if(result.result=="success"){
									alert("审批成功");
									window.scrollTo(0,0);
									A.loadPage({
										render : '#page-container',
										url : format_url("/todoTask/list/1/10")
									});
								}else{
									alert(result.errorMsg);
								}
							},
							error:function(v,n){
								alert("审批失败");
							}
						});
					}
    				//同意(检修、集控专工及以上)
    				$("#sbydbgjxjkzgty").on("click", function(e){
    					agree(1);
    				});
    				//同意(生技部人员)
    				$("#sbydbgsjbryty").on("click", function(e){
    					agree(2);
    				});
    				//同意(生技部主任)
    				$("#sbydbgsjbzrty").on("click", function(e){
    					agree(3);
    				});
    				//同意
    				function agree(ptitle){
    					var urls=format_url('/equipAbnormalReport/submitPersonLeader/'+$("#taskId").val());
						var obj = $("#approveForm").serializeObject();
						var title = ptitle==1?"生技部人员":ptitle==2?"生技部主任":"生产分管领导";
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						listFormDialog = new A.dialog({
							width: 850,
							height: 531,
							title: title,
							url:urls,
							closed: function(){
								if(listFormDialog.resule){
								 	obj.userList=listFormDialog.resule.join(",");
								 	obj.approveIdea=approveIdea;
									$.ajax({
										url : format_url("/equipAbnormalReport/leader/"+ptitle),
										contentType : 'application/json',
										dataType : 'JSON',
										type : 'POST',
										data : JSON.stringify(obj),
										success: function(result){
											if(result.result=="success"){
												alert("审批成功");
												window.scrollTo(0,0);
												A.loadPage({
													render : '#page-container',
													url : format_url("/todoTask/list/1/10")
												});
											}else{
												alert(result.errorMsg);
											}
										},
										error:function(v,n){
											alert("审批失败");
										}
									});
								}
							}
						}).render();
    				}
    				//不同意执行
    				$("#sbydbgbtyzx").on("click", function(e){
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
							$.ajax({
								url : format_url("/equipAbnormalReport/disagree"),
								contentType : 'application/json',
								dataType : 'JSON',
								type : 'POST',
								data : JSON.stringify(obj),
								success: function(result){
									if(result.result=="success"){
										alert("审批成功");
										window.scrollTo(0,0);
										A.loadPage({
											render : '#page-container',
											url : format_url("/todoTask/list/1/10")
										});
									}else{
										alert(result.errorMsg);
									}
								},
								error:function(v,n){
									alert("审批失败");
								}
							});
    					});
    				//同意执行
    				$("#sbydbgtyzx").on("click", function(e){
    					var urls=format_url('/equipAbnormalReport/submitExecutePerson/'+$("#taskId").val());
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						listFormDialog = new A.dialog({
							width: 850,
							height: 531,
							title: "执行",
							url:urls,
							closed: function(){
								if(finishStartDate && finishEndDate){
									obj.finishInfo = finishinfo;
									obj.startDate = finishStartDate;
									obj.endDate = finishEndDate;
									obj.executeFileId = finishFielId;
									obj.executeUserId = executeUserId
									obj.executeUserName = executeUserName
									$.ajax({
										url : format_url("/equipAbnormalReport/pass"),
										contentType : 'application/json',
										dataType : 'JSON',
										type : 'POST',
										data : JSON.stringify(obj),
										success: function(result){
											if(result.result=="success"){
												alert("审批成功");
												window.scrollTo(0,0);
												A.loadPage({
													render : '#page-container',
													url : format_url("/todoTask/list/1/10")
												});
											}else{
												alert(result.errorMsg);
											}
										},
										error:function(v,n){
											alert("审批失败");
										}
									});
								}
							}	
						}).render();
    				});
				});
			});
 			</script>
		</div>
    </body>
</html>