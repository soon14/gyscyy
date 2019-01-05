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
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						主键
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="id" name="id" type="text" placeholder="" maxlength="20" value="">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						检修人名称
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="name" name="name" type="text" placeholder="" maxlength="64" value="">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						编码
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="code" name="code" type="text" placeholder="" maxlength="64" value="">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						创建时间
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="createDate" name="createDate" type="text" placeholder="" maxlength="0" value="">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						修改时间
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="updateDate" name="updateDate" type="text" placeholder="" maxlength="0" value="">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						检修人
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="createUserId" name="createUserId" type="text" placeholder="" maxlength="64" value="">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						修改人
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="updateUserId" name="updateUserId" type="text" placeholder="" maxlength="64" value="">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						检修项目Id
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="overhaulProjectId" name="overhaulProjectId" type="text" placeholder="" maxlength="64" value="">
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
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					$('#addForm').validate({
						debug:true,
						rules:  {id:{      maxlength:20},name:{      maxlength:64},code:{      maxlength:64},createDate:{      maxlength:0},updateDate:{      maxlength:0},createUserId:{      maxlength:64},updateUserId:{      maxlength:64},overhaulProjectId:{      maxlength:64},},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/overhaulPlanMember");
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