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
					    姓名
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="name" name="name" type="text" placeholder="" maxlength="20" value="${ entity.name }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    性别
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="sex" name="sex" type="text" placeholder="" maxlength="20" value="${ entity.sex }">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    部门
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="department" name="department" type="text" placeholder="" maxlength="64" value="${ entity.department }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    职务
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="duty" name="duty" type="text" placeholder="" maxlength="64" value="${ entity.duty }">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    负责业务
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="business" name="business" type="text" placeholder="" maxlength="64" value="${ entity.business }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    电话
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="telephone" name="telephone" type="text" placeholder="" maxlength="20" value="${ entity.telephone }">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    手机
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="mobilePhone" name="mobilePhone" type="text" placeholder="" maxlength="11" value="${ entity.mobilePhone }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    邮箱
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="email" name="email" type="text" placeholder="" maxlength="64" value="${ entity.email }">
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
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
        			var appData = ${entityJson};
					var id = $('#id').val();
					$('#editForm').validate({
						debug:true,
						rules: {name:{      maxlength:20},sex:{      maxlength:20},department:{      maxlength:64},duty:{      maxlength:64},business:{      maxlength:64},telephone:{      maxlength:20},mobilePhone:{      maxlength:11},email:{      maxlength:64},},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/supplierContact/"+id);
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