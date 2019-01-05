<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
	<head>
    	<meta charset="UTF-8">
    	<%@ include file="/WEB-INF/views/common/meta.jsp" %> 
    </head>
	<body>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="approveForm">
			  <input class="col-md-12" id="id" name="id" type="hidden" value="${entity.id}">
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>
					    项目名称
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="projectName" readonly="readonly" name="projectName" type="text" placeholder="" maxlength="255" value="${ entity.projectName }">
					</div>
					 <label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>
					    立项文件
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="projectFile" name="projectFile" readonly="readonly" type="text" placeholder="" maxlength="255" value="${ entity.projectFile }">
				    </div>	
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    立项批复
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="projectReply" name="projectReply" readonly="readonly" type="text" placeholder="" maxlength="255" value="${ entity.projectReply }">
					</div>
					<label class="col-md-2 control-label no-padding-right">
					    合同编号
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="contractCode" name="contractCode" readonly="readonly" type="text" placeholder="" maxlength="255" value="${ entity.contractCode }">
				    </div>	
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    合同签订
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="contractSign" name="contractSign" readonly="readonly" type="text" placeholder="" maxlength="255" value="${ entity.contractSign }">
					</div>
					 <label class="col-md-2 control-label no-padding-right">
					    项目类型
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="projectType" name="projectType" readonly="readonly" type="text" placeholder="" maxlength="255" value="${ entity.projectType }">
				    </div>	
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    执行情况
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="execution" name="execution" readonly="readonly" type="text" placeholder="" maxlength="255" value="${ entity.execution }">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    原件上传
				    </label>
				    <div class="col-md-10" id="attachmentDiv">
					</div>
               </div>
                <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">审批意见</label>
					<div class="col-md-10">
						<textarea name="approveComment" placeholder=""  style="height:100px; resize:none;" class="col-md-12" maxlength="128" ></textarea>
					</div>
               </div>
			</form>
			<div style="margin-right:100px;">
        		<c:forEach items="${nodeList}" var="nodeList" varStatus="vs">
        			<c:if test="${nodeList.authFactorCode=='contractSubmitAgainBtn'}">
		        		<button  id="contractSubmitAgainBtn"  title="取消流程"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-remove-sign"></i>
		    				提交
				    	</button>
	    			</c:if> 
	    			<c:if test="${nodeList.authFactorCode=='contractTechAgreeBtn'}">
		        		<button  id="contractTechAgreeBtn"  title="同意"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				同意
				    	</button>
	    			</c:if> 
	    			<c:if test="${nodeList.authFactorCode=='contractTechBackBtn'}">
		        		<button  id="contractTechBackBtn"  title="驳回"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-remove-circle"></i>
		    				驳回
				    	</button>
	    			</c:if> 
	    			<c:if test="${nodeList.authFactorCode=='contractOperateAgreeBtn'}">
		        		<button  id="contractOperateAgreeBtn"  title="同意"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				同意
				    	</button>
	    			</c:if> 
	    			<c:if test="${nodeList.authFactorCode=='contractOperateBackBtn'}">
		        		<button  id="contractOperateBackBtn"  title="驳回"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-remove-circle"></i>
		    				驳回
				    	</button>
	    			</c:if> 
	    			<c:if test="${nodeList.authFactorCode=='contractLeaderAgreeBtn'}">
		        		<button  id="contractLeaderAgreeBtn"  title="同意"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				同意
				    	</button>
	    			</c:if> 
	    			<c:if test="${nodeList.authFactorCode=='contractLeaderBackBtn'}">
		        		<button  id="contractLeaderBackBtn"  title="驳回"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-remove-circle"></i>
		    				驳回
				    	</button>
	    			</c:if> 
	    			
	    			<c:if test="${nodeList.authFactorCode=='contractExcuteBtn'}">
		        		<button  id="contractExcuteBtn"  title="执行"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				执行
				    	</button>
	    			</c:if> 
	    			
    			</c:forEach>
        	</div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				var sysUserEntity = ${sysUserEntity};
				var entity = ${entityJson};
				var dataId = entity.id;
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
        			var appData = ${entityJson};
					var id = $('#id').val();
					//附件
					var uploadattachment=new A.uploaddropzone({
						render : "#attachmentDiv",
						fileId:${entity.attachment},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
						chargeUp:true
					}).render();
					
					//再次提交按钮
					$("#contractSubmitAgainBtn").on("click", function(e){
						$("#wjxxtjBtn").attr({"disabled":"disabled"});
    					var urls=format_url('/contractManage/submitPersonLeader/'+$("#taskId").val());
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					submitUserDialog = new A.dialog({
							width: 850,
							height: 481,
							title: "生产技术处审核人",
							url:urls,
							closed: function(){
								debugger;
								if(submitUserDialog.resule){
								 	obj.userList=submitUserDialog.resule.join(",");
								 	obj.id = dataId;
									$.ajax({
										url : format_url("/contractManage/submitAgain"),
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
					//生产技术处同意按钮
    				$("#contractTechAgreeBtn").on("click", function(e){
    					$("#contractTechAgreeBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
						$.ajax({
							url : format_url("/contractManage/techPass"),
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
					//生产技术处驳回按钮
    				$("#contractTechBackBtn").on("click", function(e){
    					$("#contractTechBackBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
    					$.ajax({
							url : format_url("/contractManage/techReject"),
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
					//计划经营处同意按钮
    				$("#contractOperateAgreeBtn").on("click", function(e){
    					$("#contractOperateAgreeBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
						$.ajax({
							url : format_url("/contractManage/operatePass"),
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
					//计划经营处驳回按钮
    				$("#contractOperateBackBtn").on("click", function(e){
    					$("#contractOperateBackBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
    					$.ajax({
							url : format_url("/contractManage/operateReject"),
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
					//单位领导同意按钮
    				$("#contractLeaderAgreeBtn").on("click", function(e){
    					$("#contractLeaderAgreeBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
						$.ajax({
							url : format_url("/contractManage/leaderPass"),
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
					//单位领导驳回按钮
    				$("#contractLeaderBackBtn").on("click", function(e){
    					$("#contractLeaderBackBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
    					$.ajax({
							url : format_url("/contractManage/leaderReject"),
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
					//计划经营处执行按钮
    				$("#contractExcuteBtn").on("click", function(e){
    					$("#contractExcuteBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
						$.ajax({
							url : format_url("/contractManage/operateExcute"),
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
					
    				
				});
			});
			function getFormatDate(){    
			    var nowDate = new Date();     
			    var year = nowDate.getFullYear();    
			    var month = nowDate.getMonth() + 1 < 10 ? "0" + (nowDate.getMonth() + 1) : nowDate.getMonth() + 1;    
			    var date = nowDate.getDate() < 10 ? "0" + nowDate.getDate() : nowDate.getDate();    
			    var hour = nowDate.getHours()< 10 ? "0" + nowDate.getHours() : nowDate.getHours();    
			    var minute = nowDate.getMinutes()< 10 ? "0" + nowDate.getMinutes() : nowDate.getMinutes();    
			    var second = nowDate.getSeconds()< 10 ? "0" + nowDate.getSeconds() : nowDate.getSeconds();    
			    return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;    
			} 
        </script>
    </body>
</html>