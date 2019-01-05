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
			<form class="form-horizontal" role="form" style="margin-right:100px;" id="addFormYqFzr">
			<input class="col-md-12" id="id" name="id" type="hidden"  maxlength="64" value="${electricId}">
			<input class="col-md-12" id="workPersonId"  type="hidden"  maxlength="64" value="${workPersonId}">
							<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">有效期延长到</label>
									<div class="col-md-4">
										<input class="col-md-12" id="delayDate" name="delayDate" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm:ss" value="${workElectricTwoEntity.delayDate}" type="date"/>'>
									</div>
<!-- 									<label class="col-md-2 control-label no-padding-right">值长（值班长）签名</label> -->
<!-- 									<div class="col-md-4"> -->
<!-- 												<input class="col-md-12" id="delayDutyMonitorId" name="delayDutyMonitorId" type="hidden" placeholder="" maxlength="64" value="${workElectricTwoEntity.delayDutyMonitorId}"> -->
<!-- 												<input class="col-md-12" id="delayDutyMonitorName" name="delayDutyMonitorName" type="text" readonly="readonly"  maxlength="64" value="${workElectricTwoEntity.delayDutyMonitorName}"> -->
<!-- 									</div> -->
							</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">工作许可人签名</label>
									<div class="col-md-4">
												<input class="col-md-12" id="delayAllowId" name="delayAllowId" type="hidden" placeholder="" maxlength="64" value="${workElectricTwoEntity.delayAllowId}">
												<input class="col-md-12" id="delayAllowName" name="delayAllowName" type="text" readonly="readonly"  maxlength="64" value="${workElectricTwoEntity.delayAllowName}">
									</div>
									<label class="col-md-2 control-label no-padding-right">工作负责人签名</label>
									<div class="col-md-4">
												<input class="col-md-12" id="delayPicId" name="delayPicId" type="hidden"  maxlength="64" value="${userEntity.id}">
												<input class="col-md-12" id="delayPicName" name="delayPicName" type="text" readonly="readonly"  maxlength="64" value="${userEntity.name}">
									</div>
								
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>审批意见</label>
									<div class="col-md-10">
										<textarea id="approveIdea" name="approveIdea" placeholder="请输入审批意见" style="height:60px; resize:none;" class="col-md-12" maxlength="128"></textarea>
									</div>
								</div>
			</form>
    		<div style="margin-right:100px;">
    			<button id="agreeYqFzr" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				同意
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					
					$('#addFormYqFzr').validate({
						debug:true,
						rules:  {
							approveStarttime:{required:true,maxlength:64},
							approveEndtime:{required:true,maxlength:64},
							approveIdea:{required:true,maxlength:64},
							},
						submitHandler: function (form) {
							
							//添加按钮
							 url = url;
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addFormYqFzr").serializeObject();
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									if(result.result=="success"){
										alert('审批成功');	
										window.scrollTo(0,0);
										workSafeOneDialog.close();
										$("#page-container").load(format_url('/todoTask/list/1/10'));
									}else{
										alert(result.result);
									}
								},
								error:function(v,n){
									alert('审批失败');
								}
							});
						}
					});
					$("#agreeYqFzr").on("click", function(){
						
						var taskId=$("#taskId").val();
						var procInstId=$("#procInstId").val(); 
						var workPersonId=$("#workPersonId").val();
						url = format_url("/workElectricTwo/agreeYqFzr?taskId="+taskId+"&procInstId="+procInstId+"&workPersonId="+workPersonId);
						$("#addFormYqFzr").submit();
						
    				});
				});
			});
        </script>
    </body>
</html>