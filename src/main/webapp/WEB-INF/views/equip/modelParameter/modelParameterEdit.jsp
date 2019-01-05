<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
	<head>
    	<meta charset="UTF-8">
    	<%@ include file="/WEB-INF/views/common/meta.jsp" %> 
    </head>
	<body>
		<div class="tabbable" style="margin-top:2%;">
		 		<div style="float:right; margin-top:-35px;margin-right:55px;">
					<button id="btnBack" class="btn btn-xs btn-primary">
						<i class="glyphicon glyphicon-share-alt"></i>
						返回
					</button>
				</div>	
    	</div>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="editForm">
				<input type="hidden" id="id" name="id" value="${modelParameterEntity.id}" /> 
				<input type="hidden" id="parameterType" name="parameterType" value="${modelParameterEntity.parameterType}" /> 
				<input type="hidden" id="modelId" name="modelId" value="${modelParameterEntity.modelId}" /> 
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    参数
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="parameter" name="parameter" type="text"  maxlength="64" value="${modelParameterEntity.parameter}">
					</div>
					  <label class="col-md-2 control-label no-padding-right">
					    默认值
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="defaultValue" name="defaultValue" type="text"  maxlength="64" value="${modelParameterEntity.defaultValue}">
				    </div>	
				</div>
			</form>
    		<div style="margin-right:100px;">
        		<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
        			<i class="ace-icon fa fa-times"></i>
        			取消
        		</button>
        		<button id="editBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
        			<i class="ace-icon fa fa-floppy-o"></i>
        			保存
        		</button>
        	</div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox'], function(A){
					var id = $('#id').val();
					$('#editForm').validate({
						debug:true,
						rules: {
							parameter: {
								required: true,
								maxlength: 64
							},
							defaultValue: {
								required: true,
								maxlength: 64
							} 
						},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/modelParameter/edit/"+id);
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editForm").serializeObject();
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									if(result.data!=null){
										$("#saveBtn").removeAttr("disabled");
										alert('添加成功');
										listFormDialog.close();
									}else{
										alert("参数已存在！");
									}
								},
								error:function(v,n){
									alert('操作失败');
								}
							});
						}
					});
					$("#editBtn").on("click", function(){
    					$("#editForm").submit();
    				});
				});
			});
        </script>
    </body>
</html>