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
				<form class="form-horizontal" role="form" style="margin-right:100px;" id="addFormCardRisk">
							<input id="controlId" name="controlId" type="hidden" value="${cardId}"></input>
							<input id="uuid" name="uuidCode" type="hidden" value="${uuid}"></input>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>危险点分析</label>
								<div class="col-md-10">
									<textarea id="dangerPoint" name="dangerPoint"    placeholder="请输入危险点分析" style="height:80px; resize:none;" class="col-md-12" maxlength="128"></textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>主要控制措施</label>
								<div class="col-md-10">
									<textarea id="mainControl" name="mainControl"    placeholder="请输入主要控制措施" style="height:80px; resize:none;" class="col-md-12" maxlength="128"></textarea>
								</div>
								
							</div>
				</form>
    		<div style="margin-right:100px;">
    			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			<button id="saveBtnCardRisk" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					
					$('#addFormCardRisk').validate({
						debug:true,
						rules:  {
							dangerPoint:{required:true,maxlength:128},
							mainControl:{required:true,maxlength:128}
						},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/controlCardRisk");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addFormCardRisk").serializeObject();
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('添加成功');
									workSafeTwoDialog.close();
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					$("#saveBtnCardRisk").on("click", function(){
						$("#addFormCardRisk").submit();
    				});
    				
				});
			});
        </script>
    </body>
</html>