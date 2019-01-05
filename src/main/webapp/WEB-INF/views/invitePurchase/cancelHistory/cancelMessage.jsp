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
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="cancelForm">
				<input class="col-md-12" id="itemId" name="itemId" type="hidden" placeholder="" maxlength="20" value="${ itemId }">
				<input class="col-md-12" id="itemType" name="itemType" type="hidden" placeholder="" maxlength="20" value="${ itemType }">
				<input class="col-md-12" id="cancelUserId" name="cancelUserId" type="hidden" placeholder="" maxlength="20" value="${ sysUserEntity.id }">
		       <div class="form-group" style="margin-top: 30px">
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>撤回操作人员
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" readonly="readonly" type="text" placeholder="" value="${ sysUserEntity.name }">
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>撤回时间
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="cancelTime" name="cancelTime" readonly="readonly" type="text" placeholder="" value="${ nowDate }">
                    </div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>撤回原因
				    </label>
				    <div class="col-md-10">
						<textarea placeholder="请输入撤回原因" name="cancelReason" class="col-md-12" style="height:90px; resize:none;"></textarea>
                    </div>
               </div>

            </form>
    		<div style="margin-right:100px;">
    		<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				关闭
    			</button>
    			<button id="editBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    			
    		</div>
		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					
					$('#cancelForm').validate({
						debug:true,
						rules:  {
							cancelReason:{required:true,maxlength:128},
							},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/cancelHistory/savePage");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#cancelForm").serializeObject();
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('撤回成功');
									listFormDialog.close();
								},
								error:function(v,n){
									alert('撤回失败');
								}
							});
						}
					});
					$("#editBtn").on("click", function(){
						$("#cancelForm").submit();
    				});
				});
			});
        </script>
    </body>
</html>