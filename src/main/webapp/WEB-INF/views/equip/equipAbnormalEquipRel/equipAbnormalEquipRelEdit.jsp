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
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    主键
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="id" name="id" type="text" placeholder="" maxlength="20" value="${ entity.id }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    编码
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="code" name="code" type="text" placeholder="" maxlength="64" value="${ entity.code }">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    名称
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="name" name="name" type="text" placeholder="" maxlength="64" value="${ entity.name }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    创建人
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="createUserId" name="createUserId" type="text" placeholder="" maxlength="64" value="${ entity.createUserId }">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    创建时间
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="createDate" name="createDate" type="text" placeholder="" maxlength="0" value="${ entity.createDate }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    修改人
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="updateUserId" name="updateUserId" type="text" placeholder="" maxlength="64" value="${ entity.updateUserId }">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    修改时间
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="updateDate" name="updateDate" type="text" placeholder="" maxlength="0" value="${ entity.updateDate }">
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
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone','wysiwyg'], function(A){
        			var appData = ${entityJson};
					var id = $('#id').val();
					$('#editForm').validate({
						debug:true,
						rules: {id:{maxlength:20},code:{maxlength:64},name:{maxlength:64},createUserId:{maxlength:64},createDate:{maxlength:0},updateUserId:{maxlength:64},updateDate:{maxlength:0},},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/equipAbnormalEquipRel/"+id);
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