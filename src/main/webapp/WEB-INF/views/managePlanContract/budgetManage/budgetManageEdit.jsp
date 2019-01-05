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
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="editForm">
			<input class="col-md-12" id="id" name="id" type="hidden"  value="${entity.id }">
			<input class="col-md-12" id="unitId" name="unitId" type="hidden"  value="${entity.unitId }">
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    项目
				    </label>
				    <div class="col-md-10">
					    <input class="col-md-12" id="projectName" name="projectName" type="text" placeholder="" maxlength="64" style="height: 50px;" value="${ entity.projectName }">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    本年发生数
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="occurNum" name="occurNum" type="text" placeholder="" maxlength="64" value="${ entity.occurNum }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    本年预算数
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="budgetNum" name="budgetNum" type="text" placeholder="" maxlength="64" value="${ entity.budgetNum }">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    执行预算差额
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="differentNum" name="differentNum" type="text" placeholder="" maxlength="64" value="${ entity.differentNum }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    执行率
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="implementRate" name="implementRate" type="text" placeholder="" maxlength="64" value="${ entity.implementRate }">
					</div>
				</div>
			</form>
    		<div style="margin-right:100px;margin-top: 30px;">
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
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone','wysiwyg'], function(A){
        			var appData = ${entityJson};
					
					var id = $('#id').val();
					$('#editForm').validate({
						debug:true,
						rules: {
							id:{maxlength:20},
							year:{maxlength:64},
							projectName:{maxlength:64,required:true},
							occurNum:{maxlength:64,required:true},
							budgetNum:{maxlength:64,required:true},
							differentNum:{maxlength:64},
							implementRate:{maxlength:64},
							unitId:{maxlength:64}
							},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/budgetManage/saveEdit/"+id);
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
									alert('操作成功');
									listFormDialog.close();
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