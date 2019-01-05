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
	                   <input class="col-md-12" id="id" name="id" type="text" placeholder="${formField.toolTip}" maxlength="20" value="${formField.defaultValue}">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						名称
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="name" name="name" type="text" placeholder="${formField.toolTip}" maxlength="64" value="${formField.defaultValue}">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						创建时间
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="createDate" name="createDate" type="text" placeholder="${formField.toolTip}" maxlength="0" value="${formField.defaultValue}">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						修改时间
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="updateDate" name="updateDate" type="text" placeholder="${formField.toolTip}" maxlength="0" value="${formField.defaultValue}">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						创建人
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="createUserId" name="createUserId" type="text" placeholder="${formField.toolTip}" maxlength="64" value="${formField.defaultValue}">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						检修计划ID
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="overhualPlanId" name="overhualPlanId" type="text" placeholder="${formField.toolTip}" maxlength="20" value="${formField.defaultValue}">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						项目名称
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="projectName" name="projectName" type="text" placeholder="${formField.toolTip}" maxlength="64" value="${formField.defaultValue}">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						项目明细
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="projecDetail" name="projecDetail" type="text" placeholder="${formField.toolTip}" maxlength="128" value="${formField.defaultValue}">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						列入原因
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="projectReason" name="projectReason" type="text" placeholder="${formField.toolTip}" maxlength="128" value="${formField.defaultValue}">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						方案措施
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="measure" name="measure" type="text" placeholder="${formField.toolTip}" maxlength="128" value="${formField.defaultValue}">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						开工时间
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="startDate" name="startDate" type="text" placeholder="${formField.toolTip}" maxlength="0" value="${formField.defaultValue}">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						完成时间
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="endDate" name="endDate" type="text" placeholder="${formField.toolTip}" maxlength="0" value="${formField.defaultValue}">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						计划总金额
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="totalMoney" name="totalMoney" type="text" placeholder="${formField.toolTip}" maxlength="20" value="${formField.defaultValue}">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						删除状态
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="status" name="status" type="text" placeholder="${formField.toolTip}" maxlength="20" value="${formField.defaultValue}">
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
						rules:  {},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/overhaulProject");
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