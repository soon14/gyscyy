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
					    名称
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="name" name="name" type="text" placeholder="" maxlength="64" value="${ entity.name }">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    编码
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="code" name="code" type="text" placeholder="" maxlength="64" value="${ entity.code }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    创建时间
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="createDate" name="createDate" type="text" placeholder="" maxlength="0" value="${ entity.createDate }">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    修改时间
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="updateDate" name="updateDate" type="text" placeholder="" maxlength="0" value="${ entity.updateDate }">
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
					    修改人
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="updateUserId" name="updateUserId" type="text" placeholder="" maxlength="64" value="${ entity.updateUserId }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    设备ID
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="equipId" name="equipId" type="text" placeholder="" maxlength="20" value="${ entity.equipId }">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    工作任务
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="workTask" name="workTask" type="text" placeholder="" maxlength="512" value="${ entity.workTask }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    完成状态
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="finishStatus" name="finishStatus" type="text" placeholder="" maxlength="20" value="${ entity.finishStatus }">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    检修日志ID
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="overhaulLogId" name="overhaulLogId" type="text" placeholder="" maxlength="20" value="${ entity.overhaulLogId }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    工作安排Id
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="overhaulArrangeId" name="overhaulArrangeId" type="text" placeholder="" maxlength="20" value="${ entity.overhaulArrangeId }">
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
						rules: {id:{      maxlength:20},name:{      maxlength:64},code:{      maxlength:64},createDate:{      maxlength:0},updateDate:{      maxlength:0},createUserId:{      maxlength:64},updateUserId:{      maxlength:64},equipId:{      maxlength:20},workTask:{      maxlength:512},finishStatus:{      maxlength:20},overhaulLogId:{      maxlength:20},overhaulArrangeId:{      maxlength:20},},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/overhaulWorkTask/"+id);
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