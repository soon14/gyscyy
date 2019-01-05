<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<!DOCTYPE html>
<html lang="zh">
    <head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
		<style>
			.page-content .tabbable{height:960px}
			.page-content .tab-content{height:100%}
		</style>
	</head>
	<body>
		<div class="page-content" style="height: 40%">
		<div class="col-md-12"  >
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addForm">
		      <input id="id" name="id" type="hidden" value="${entity.id }">
			  <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>培训单位
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="unitId" name="unitId" readonly="readonly" type="text" placeholder="" maxlength="20" value="${sysUnitEntity.name }">
<!-- 						<div id="unitIdDiv"></div> -->
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>培训名称
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="trainName" name="trainName" readonly="readonly" type="text" placeholder="" maxlength="20" value="${entity.trainName }">
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>培训内容
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="trainContent" name="trainContent" readonly="readonly" type="text" placeholder="" maxlength="20" value="${entity.trainContent }">
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>培训时间
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="trainTime"  readonly="readonly" type="text" placeholder="" maxlength="20" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${entity.trainTime}"  type="both"/>">
<!-- 	                    <div id="trainTimeDiv"></div> -->
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>培训人员
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="trainMember" name="trainMember" readonly="readonly" type="text" placeholder="" maxlength="20" value="${sysUserEntity.name }">
<!-- 						<select class="col-md-12 chosen-select" id="trainMember" name="trainMember"></select> -->
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>培训地点
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="trainLocation" readonly="readonly" name="trainLocation" type="text" placeholder="" maxlength="20" value="${entity.trainLocation }">
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>培训类别
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="trainType" name="trainType" readonly="readonly" type="text" placeholder="" maxlength="20" value="${entity.trainTypeName }">
<!-- 						<select class="col-md-12 chosen-select" id="trainType" name="trainType"></select> -->
                    </div>
               </div>
               </form>
		</div>
		</div>
		   <div style="margin-right:100px;margin-top: 20px;">
					    <c:forEach items="${nodeList}" var="nodeList" varStatus="vs">
					    <c:if test="${nodeList.authFactorCode=='ztjBtn' && type=='dealing'}">
						<button id="ztjBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;margin-top:20px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				再提交
		    			</button>
		    			</c:if>
		    			<c:if test="${nodeList.authFactorCode=='zfBtn' && type=='dealing' }">
						<button id="zfBtn" class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;margin-top:20px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				作废
		    			</button>
		    			</c:if>
		    			
	    				<c:if test="${nodeList.authFactorCode=='technicalBtn' && type=='dealing'}">
						<button id="technicalBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;margin-top:20px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				生产技术处审核
		    			</button>
		    			</c:if>
	    				<c:if test="${nodeList.authFactorCode=='planBtn' && type=='dealing'}">
		    			<button id="planBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;margin-top:20px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				计划经营处审核
		    			</button>
		    			</c:if>
	    				<c:if test="${nodeList.authFactorCode=='manageBtn' && type=='dealing'}">
						<button id="manageBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;margin-top:20px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				单位领导讨论
		    			</button>
		    			</c:if>
	    				<c:if test="${nodeList.authFactorCode=='excuteBtn' && type=='dealing'}">
		    			<button id="excuteBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;margin-top:20px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				计划经营处下文执行
		    			</button>
		    			</c:if>
		    			</c:forEach>
	        	</div>
		<script type="text/javascript">
		 var taskId=$("#taskId").val();
		 var procInstId=$("#procInstId").val();
		 var procDefId=$("#procDefId").val();
		 var id='${entity.id}'; 
		 var scienceTechnologyPlanDatatables;
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					$('#addForm').validate({
						debug:true,
						rules:  {
							},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/annualTrainingPlan");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('添加成功');
									window.scrollTo(0,0);
									A.loadPage({
										render : '#page-container',
										url : format_url("/annualTrainingPlan/list")
									});
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					var id=${entity.id};
					//生产技术处审核
					$('#technicalBtn').on('click',function(){
						workSafeOneDialog = new A.dialog({
    						width: 1000,
    						height: 400,
    						title: "生产技术处审核",
    						url:format_url("/annualTrainingPlan/getAddSCJSC?id="+id+"&taskId="+taskId ),
    						closed: function(){
    						}	
    					}).render();
					});
					//计划经营处审核
					$('#planBtn').on('click',function(){
						workSafeOneDialog = new A.dialog({
    						width: 1000,
    						height: 400,
    						title: "计划经营处审核",
    						url:format_url("/annualTrainingPlan/getAddJHJYC?id="+id+"&taskId="+taskId ),
    						closed: function(){
    						}	
    					}).render();
					});
					//单位领导讨论
					$('#manageBtn').on('click',function(){
						workSafeOneDialog = new A.dialog({
    						width: 1000,
    						height: 400,
    						title: "单位领导审核",
    						url:format_url("/annualTrainingPlan/getAddDWLDTL?id="+id+"&taskId="+taskId ),
    						closed: function(){
    						}	
    					}).render();
					});
					//计划经营处下文执行
					$('#excuteBtn').on('click',function(){
						workSafeOneDialog = new A.dialog({
    						width: 1000,
    						height: 400,
    						title: "计划经营处下文执行",
    						url:format_url("/annualTrainingPlan/getAddJHJYCXW?id="+id+"&taskId="+taskId ),
    						closed: function(){
    						}	
    					}).render();
					});
					//再提交
					$('#ztjBtn').on('click',function(){
						var id='${entity.id}';
						workSafeOneDialog = new A.dialog({
    						width: 500,
    						height: 450,
    						title: "选择生产技术处人员",
    						url:format_url("/annualTrainingPlan/sureSubmit?id="+ id),
    						closed: function(){
    						}	
    					}).render();
					});
					//作废
					$('#zfBtn').on('click',function(){
						var obj = $("#addForm").serializeObject();
						obj.id=${entity.id};
						$.ajax({
							url : format_url("/annualTrainingPlan/disAgreeZf?taskId="+taskId+"&procInstId="+procInstId+"&selectUser="+""),
							contentType : 'application/json',
							dataType : 'JSON',
							data : JSON.stringify(obj),
							cache: false,
							type : 'POST',
							success: function(result){
								if(result.result=="success"){
									alert('作废成功');	
									window.scrollTo(0,0);
									$("#page-container").load(format_url('/todoTask/list/1/10'));
								}else{
									alert(result.result);
								}
							},
							error:function(v,n){
								alert('作废失败');
							}
						});
					});
					$("#btnBack").on('click',function(){
						A.loadPage({
							render : '#page-container',
							url : format_url("/annualTrainingPlan/list")
						});
					});
				});
			});
			function goBackToSubmitPerson(id,selectUser){//回调函数
				var taskId=$("#taskId").val();
				var procInstId=$("#procInstId").val();
				var url =format_url("/annualTrainingPlan/againSubmit?workId="+id+"&selectUser="+selectUser+"&taskId="+taskId+"&procInstId="+procInstId);
				$.ajax({
					contentType: "application/json",
					dataType:"JSON",
					url : url,
					success: function(result){
						if(result.result=="success"){
							alert('操作成功');
							window.scrollTo(0,0);
							$("#page-container").load(format_url('/todoTask/list/1/10'));
						}else{
							alert(result.errorMsg);
						}
					},
					error:function(v,n){
						alert('操作失败');
					}
				});
				
			}
        </script>
    </body>
</html>