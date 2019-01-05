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
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addForm">
				<input class="col-md-12" id="createUserId" name="createUserId" type="hidden" value="${loginUser.id}">
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>公司编码</label>
					<div class="col-md-4">
						<input class="col-md-12" id="code" name="code" type="text" maxlength="64" value="">
					</div>
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>公司名称</label>
					<div class="col-md-4">
						<input class="col-md-12" id="name" name="name" type="text" maxlength="64" value="">
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right">创建人</label>
					<div class="col-md-4">
						<input class="col-md-12" id="createUserName" name="createUserName" type="text" maxlength="64" value="${loginUser.name}" readonly>
				</div>
			</div>
		</form>
			<div style="margin-right:100px;">
				<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
					<i class="ace-icon fa fa-times"></i>
					取消
				</button>
				<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i>
					保存
				</button>
			</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone','wysiwyg'], function(A){
					$('#addForm').validate({
						debug:true,
						rules:  {
							code : {required:true, maxlength:64},
							name : {required:true, maxlength:64},
						},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/safeCheckUnit/addEntity");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
							debugger;
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('添加成功');
									listFormDialog.close();
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					$("#saveBtn").on("click", function(){
						$("#addForm").submit();
					});
					
				});
			});
	</script>
</body>
</html>