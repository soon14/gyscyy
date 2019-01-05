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
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addFormType">
		       <div class="form-group">
					
				    <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>类型名称
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="name" name="name" type="text" placeholder="" maxlength="64" value="">
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>创建人
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="createUserId" name="createUserId" type="text" readonly="readonly" maxlength="64" value="${userName }">
                	</div>
               </div>
            </form>
    		<div style="margin-right:100px;margin-top: 30px;">
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
					$('#addFormType').validate({
						debug:true,
						rules:  {
							id:{maxlength:20},
							name:{maxlength:64,required:true},
							createUserId:{maxlength:64,required:true},
							createDate:{maxlength:64},
							},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/purchaseType");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addFormType").serializeObject();
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
						$("#addFormType").submit();
    				});
    				
				});
			});
        </script>
    </body>
</html>