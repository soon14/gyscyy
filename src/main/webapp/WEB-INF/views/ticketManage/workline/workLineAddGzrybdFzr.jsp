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
			<form class="form-horizontal" role="form" style="margin-right:100px;" id="addFormGzrybdFzr">
				<div class="form-group">
						<label class="col-md-2 control-label no-padding-right">工作人员变动情况（变动人员姓名、日期及时间）</label>
						<div class="col-md-10">
								<textarea id="workPersonGroup" name="workPersonGroup"  readonly="readonly" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${workElectricEntity.workPersonGroup}</textarea>
						</div>
				</div>
			<div class="form-group">
					<label class="col-md-2 control-label no-padding-right">工作负责人签名</label>
					<div class="col-md-4">
							<input class="col-md-12" id="id" name="id" type="hidden"  maxlength="64" value="${electricId}">
							<input class="col-md-12" id="workPersonPicId" name="workPersonPicId" type="hidden" readonly="readonly" maxlength="128" value="${workPersonId}">
							<input class="col-md-12" id="workPersonPicName" name="workPersonPicName" type="text" readonly="readonly" maxlength="128" value="${workPersonName}">
					</div>
			</div>
			
			
			</form>
    		<div style="margin-right:100px;">
    			<button id="disagreeGzrybdFzr" class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
    				<i class="glyphicon glyphicon-remove-circle"></i>
    				驳回
    			</button>
    			<button id="agreeGzrybdFzr" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				同意
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					
					
					$('#addFormGzrybdFzr').validate({
						debug:true,
						rules:  {
							approveStarttime:{required:true,maxlength:64},
							approveEndtime:{required:true,maxlength:64}
							},
						submitHandler: function (form) {
							
							//添加按钮
							 url = url;
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addFormGzrybdFzr").serializeObject();
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('审批成功');
									workSafeOneDialog.close();
								},
								error:function(v,n){
									alert('审批失败');
								}
							});
						}
					});
					$("#agreeGzrybdFzr").on("click", function(){
						var taskId=$("#taskId").val();
						var procInstId=$("#procInstId").val(); 
						url = format_url("/workLine/agreeGzrybdFzr?taskId="+taskId+"&procInstId="+procInstId);
						$("#addFormGzrybdFzr").submit();
    				});
					
					$("#disagreeGzrybdFzr").on("click", function(){
						var taskId=$("#taskId").val();
						var procInstId=$("#procInstId").val(); 
						url = format_url("/workLine/disagreeGzrybdFzr?taskId="+taskId+"&procInstId="+procInstId);
						$("#addFormGzrybdFzr").submit();
					});
				});
			});
        </script>
    </body>
</html>