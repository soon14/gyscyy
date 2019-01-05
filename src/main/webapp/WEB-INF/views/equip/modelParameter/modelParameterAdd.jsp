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
				<input class="col-md-12" id="modelId" name="modelId" type="hidden"  maxlength="20" value="${modelId}">
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						参数
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="parameter" name="parameter" type="text" placeholder="${formField.toolTip}" maxlength="64" value="${formField.defaultValue}">
                    </div>
					<label class="col-md-2 control-label no-padding-right">
						默认值
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="defaultValue" name="defaultValue" type="text" placeholder="${formField.toolTip}" maxlength="64" value="${formField.defaultValue}">
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						参数类型
				    </label>
				    <div class="col-md-4">
						<select id="parameterType" name="parameterType" class="form-control chosen-select" style="width:150px;"></select>
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
				seajs.use(['combobox'], function(A){
					var parameterTypeCombobox = new A.combobox({
						render: "#parameterType",
						datasource:${parameterType},
						multiple:false,
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					$('#addForm').validate({
						debug:true,
						rules:  {},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/modelParameter/add");
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
									if(result.data!=null){
										$("#saveBtn").removeAttr("disabled");
										alert('添加成功');
										listFormDialog.close();
									}else{
										alert("参数已存在！");
									}
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