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
			      	      <div class="form-group" >
						<label class="col-md-2 control-label no-padding-right">
							异动单号
						</label>
						<div class="col-md-4">
		                   <input class="col-md-12" id="equipAbnormalBill" name="equipAbnormalBill" type="text"  value="${equipAbnormalEntities.equipAbnormalBill}" readonly="readonly" >
	                	</div>
					    <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>单位名称
					    </label>
					    <div class="col-md-4">
							<input class="col-md-12" id="unitId" name="unitId" type="text" value="${equipAbnormalEntities.unitName}" readonly="readonly">
	                    </div>
	               </div>
			       <div class="form-group">
	                    <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>设备编号
						</label>
						<div class="col-md-4">
							<input class="col-md-12"  type="text" value="${equipAbnormalEntities.equipmentId}" readonly="readonly">
	                	</div>
					    <label class="col-md-2 control-label no-padding-right">
							设备名称
					    </label>
					    <div class="col-md-4">
							<input class="col-md-12" id="equipName" name="equipName" type="text" value="${equipAbnormalEntities.equipName}" readonly="readonly">
	                    </div>
	               </div>
			       <div class="form-group">
					    <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>计划开始时间
					    </label>
					    <div class="col-md-4">
							<input class="col-md-12" id="planBeginDate" name="planBeginDate" type="text"  value="<fmt:formatDate value="${equipAbnormalEntities.planBeginDate}" type="both"/>" readonly="readonly"  >
	                    </div>
						<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>计划结束时间
						</label>
						<div class="col-md-4">
							<input class="col-md-12" id="planEndDate" name="planEndDate" type="text"  value="<fmt:formatDate value="${equipAbnormalEntities.planEndDate}" type="both"/>" readonly="readonly"  >
	                	</div>
	               </div>
			       <div class="form-group">
					    <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>申请时间
					    </label>
					    <div class="col-md-4">
							<div id="applyDate"></div>
							<input class="col-md-12" id="applyDate" name="applyDate" type="text"  value="<fmt:formatDate value="${equipAbnormalEntities.applyDate}" type="both"/>" readonly="readonly"  >
	                    </div>
						<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>申请人
						</label>
						<div class="col-md-4">
							<input class="col-md-12" id="applyUserName" name="applyUserName" type="text"   value="${equipAbnormalEntities.applyUserName}" readonly="readonly"  >
							<input class="col-md-12" id="applyUserId" name="applyUserId" type="hidden"   value="${equipAbnormalEntities.applyUserId}" readonly="readonly"  >
	                	</div>
	               </div>
			       <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>异动说明
						</label>
						<div class="col-md-10">
	                	   <textarea class="col-md-12" id="abnormalDesc" name="abnormalDesc" type="text"  maxlength="500" readonly="readonly">${equipAbnormalEntities.abnormalDesc}</textarea>
	                	</div>
	               </div>
			       <div class="form-group">
					    <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>异动原因
					    </label>
					    <div class="col-md-10">
							<textarea class="col-md-12" id="abnormalCause" name="abnormalCause" type="text"  maxlength="500" readonly="readonly">${equipAbnormalEntities.abnormalCause}</textarea>
	                    </div>
	               </div>
			       <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">
							备注
						</label>
						<div class="col-md-10">
		                   <textarea class="col-md-12" id="remark" name="remark" type="text"  maxlength="500" readonly="readonly">${equipAbnormalEntities.remark}</textarea>
	                	</div>
	               </div>
		        	<div class="form-group" style="margin-right: 75px;">
					    <label class="col-md-2 control-label no-padding-right">
							附件
					    </label>
					    <div class="col-md-10">
							<div class="col-md-12" id="basefielid"  ></div>
	                    </div>
               		</div>
	               </div>
	               <div style="margin-right:100px;">
    			<c:forEach items="${nodeList}" var="nodeList" varStatus="vs">
	    			<!--送领导审批按钮 -->
<%-- 	    			<c:if test="${nodeList.authFactorCode=='sbydbgldsh'}"> --%>
<!-- 		        		<button  id="sbydbgldsh"  title="提交领导审批"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;" type="button"> -->
<!-- 		    				<i class="ace-icon glyphicon glyphicon-level-up"></i> -->
<!-- 		    				领导审核 -->
<!-- 				    	</button> -->
<%-- 	    			</c:if>  --%>
	    			<!--送执行人执行按钮 -->
<%-- 	    			<c:if test="${nodeList.authFactorCode=='sbydbgzxr'}"> --%>
<!-- 		        		<button  id="sbydbgzxr"  title="提交执行人"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;" type="button"> -->
<!-- 		    				<i class="ace-icon glyphicon glyphicon-ok"></i> -->
<!-- 		    				执行人 -->
<!-- 				    	</button> -->
<%-- 	    			</c:if>  --%>
    				<!--生技部人员驳回  -->
	    			<c:if test="${nodeList.authFactorCode=='sbydsqsjbrybh'}">
		        		<button  id=sbydsqsjbrybh  title="驳回"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;" type="button">
		    				<i class="ace-icon glyphicon glyphicon-remove-circle"></i>
		    				驳回
				    	</button>
	    			</c:if> 
    				<!--生技部主任驳回  -->
	    			<c:if test="${nodeList.authFactorCode=='sbydsqsjbzrbh'}">
		        		<button  id=sbydsqsjbzrbh  title="驳回"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;" type="button">
		    				<i class="ace-icon glyphicon glyphicon-remove-circle"></i>
		    				驳回
				    	</button>
	    			</c:if> 
    				<!--生产分管领导驳回  -->
	    			<c:if test="${nodeList.authFactorCode=='sbydsqscfgldbh'}">
		        		<button  id=sbydsqscfgldbh  title="驳回"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;" type="button">
		    				<i class="ace-icon glyphicon glyphicon-remove-circle"></i>
		    				驳回
				    	</button>
	    			</c:if> 
	    			<!--检修、集控专工及以上同意 -->
	    			<c:if test="${nodeList.authFactorCode=='sbydsqjxjkzgty'}">
		        		<button  id="sbydsqjxjkzgty"  title="审批通过"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;" type="button">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				同意
				    	</button>
	    			</c:if> 
	    			<!--生技部人员同意 -->
	    			<c:if test="${nodeList.authFactorCode=='sjbryty'}">
		        		<button  id="sjbryty"  title="审批通过"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;" type="button">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				同意
				    	</button>
	    			</c:if> 
	    			<!--生技部主任同意 -->
	    			<c:if test="${nodeList.authFactorCode=='sbydsqsjbzrty'}">
		        		<button  id="sbydsqsjbzrty"  title="审批通过"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;" type="button">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				同意
				    	</button>
	    			</c:if> 
	    			<!--分管领导同意执行按钮 -->
	    			<c:if test="${nodeList.authFactorCode=='sbydzx'}">
		        		<button  id="sbydzx"  title="同意执行"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;" type="button">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				同意执行
				    	</button>
	    			</c:if> 
	    			<!--分管领导不同意执行按钮 -->
	    			<c:if test="${nodeList.authFactorCode=='sbydbzx'}">
		        		<button  id="sbydbzx"  title="不同意执行"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;" type="button">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				不同意执行
				    	</button>
	    			</c:if> 
	    			<!--申请人再提交按钮 -->
<%-- 	    			<c:if test="${nodeList.authFactorCode=='sbydbgztj'}"> --%>
<!-- 		        		<button  id="sbydbgztj"  title="重新发起流程"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;" type="button"> -->
<!-- 		    				<i class="ace-icon glyphicon glyphicon-ok"></i> -->
<!-- 		    				再提交 -->
<!-- 				    	</button> -->
<%-- 	    			</c:if>  --%>
<%-- 	    			<c:if test="${nodeList.authFactorCode=='sbydbgqxlc'}"> --%>
<!-- 		        		<button  id="sbydbgqxlc"  title="流程结束"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;" type="button"> -->
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
    		var finishinfo,finishStartDate,finishEndDate,finishFielId,executeUserId,executeUserName;
    		var processStatus = ${equipAbnormalEntities.processStatus};
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone','selectbox'], function(A){
					//附件上传
					var baseuploaddropzone =new A.uploaddropzone({
						render : "#basefielid",
						fileId:${equipAbnormalEntities.fileId},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks:true,//显示删除按钮
						chargeUp:true
					}).render();
					//生技部人员驳回
    				$("#sbydsqsjbrybh").on("click", function(e){
    					$("#sbydsqsjbrybh").attr({"disabled":"disabled"});
    					reject();
    				});
					//生技部主任驳回
    				$("#sbydsqsjbzrbh").on("click", function(e){
    					$("#sbydsqsjbzrbh").attr({"disabled":"disabled"});
    					reject();
    				});
					//生产分管领导驳回
    				$("#sbydsqscfgldbh").on("click", function(e){
    					$("#sbydsqscfgldbh").attr({"disabled":"disabled"});
    					reject();
    				});
    				//驳回
					function reject(){
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					$.ajax({
							url : format_url("/equipAbnormal/reject/10"),
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
    				};
    				//同意(检修、集控专工及以上)
    				$("#sbydsqjxjkzgty").on("click", function(e){
    					agree(1);
    				});
    				//同意(生技部人员)
    				$("#sjbryty").on("click", function(e){
    					agree(2);
    				});
    				//同意(生技部主任)
    				$("#sbydsqsjbzrty").on("click", function(e){
    					agree(3);
    				});
    				//同意
    				function agree(ptitle){
    					var urls=format_url('/equipAbnormal/submitPersonLeader/'+$("#taskId").val());
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
								 	console.log(obj);
									$.ajax({
										url : format_url("/equipAbnormal/leader/10"),
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
    				};
    				//不同意执行
    				$("#sbydbzx").on("click", function(e){
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
									$.ajax({
										url : format_url("/equipAbnormal/disagree"),
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
    				$("#sbydzx").on("click", function(e){
    					var urls=format_url('/equipAbnormal/submitExecutePerson/'+$("#taskId").val());
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
									obj.executeUserId = executeUserId;
									obj.executeUserName = executeUserName;
									$.ajax({
										url : format_url("/equipAbnormal/pass"),
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