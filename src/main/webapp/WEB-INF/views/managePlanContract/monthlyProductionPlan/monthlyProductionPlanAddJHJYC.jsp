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
			<form class="form-horizontal" role="form" style="margin-right:100px;" id="addFormSp">
			<div class="form-group">
					<label class="col-md-2 control-label no-padding-right">计划经营处签名</label>
					<div class="col-md-4">
							<input class="col-md-12" id="id" name="id" type="hidden"  maxlength="64" value="${id}">
							<input class="col-md-12" id="userId" name="userId" type="hidden"  maxlength="64" value="${userEntity.id}">
							<input class="col-md-12" id="" name="userName" type="text" readonly="readonly" maxlength="64" value="${userEntity.name}">
					</div>
			</div>
			<div class="form-group">
				<label class="col-md-2 control-label no-padding-right">审批意见</label>
				<div class="col-md-10">
					<textarea id="approveIdea" name="approveIdea" placeholder="请输入审批意见" style="height:60px; resize:none;" class="col-md-12" maxlength="128"></textarea>
				</div>
			</div>
			<div class="form-group">
						<label class="col-md-2 control-label no-padding-right">单位领导</label>
						<div class="col-sm-10">
							<select multiple="multiple" size="5" name="duallistbox_demo1[]" id="duallist">
								<c:forEach items="${userList}" var="userList" varStatus="vs">
									<option value="${userList.loginName}">${userList.name}</option>
								</c:forEach>
							</select>
						</div>
			</div>
			</form>
    		<div class="col-md-12"  style="margin-right:100px;margin-top:20px;margin-bottom:20px;">
    			<button id="agreeSp" class="btn btn-xs btn-success pull-right"  style="margin-right:144px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				同意
    			</button>
    			<button id="disAgreeSp" class="btn btn-xs btn-danger pull-right"  style="margin-right:-123px;">
    				<i class="glyphicon glyphicon-remove-circle"></i>
    				驳回
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','duallistbox'], function(A){
					var demo1 = $('select[name="duallistbox_demo1[]"]').bootstrapDualListbox({infoTextFiltered: '<span class="label label-purple label-lg">Filtered</span>'});
					var container1 = demo1.bootstrapDualListbox('getContainer');
					container1.find('.btn').addClass('btn-white btn-info btn-bold');
					
					
					$("#agreeSp").on("click", function(){
						var taskId=$("#taskId").val();
						var procInstId=$("#procInstId").val(); 
						var selectUser=$('[name="duallistbox_demo1[]"]').val();
						if(selectUser==null||selectUser==""){
							alert('请选择单位领导!');
							return;
						}
						
						$('#addFormSp').validate({
							debug:true,
							rules:  {
								},
							submitHandler: function (form) {
								
								//添加按钮
								url = format_url("/monthlyProductionPlan/agreeJHJYC?taskId="+taskId+"&procInstId="+procInstId+"&selectUser="+selectUser);
								//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
								var obj = $("#addFormSp").serializeObject();
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
						$("#addFormSp").submit();
    				});
					$("#disAgreeSp").on("click", function(){
						var taskId=$("#taskId").val();
						var procInstId=$("#procInstId").val(); 
						var selectUser=$('[name="duallistbox_demo1[]"]').val();
						
						$('#addFormSp').validate({
							debug:true,
							rules:  {
								},
							submitHandler: function (form) {
								
								//添加按钮
								url = format_url("/monthlyProductionPlan/disagreeJHJYC?taskId="+taskId+"&procInstId="+procInstId+"&selectUser="+"");
								//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
								var obj = $("#addFormSp").serializeObject();
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
						$("#addFormSp").submit();
    				});
					
				});
			});
        </script>
    </body>
</html>