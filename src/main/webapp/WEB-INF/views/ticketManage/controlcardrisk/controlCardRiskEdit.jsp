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
		<form class="form-horizontal" role="form" style="margin-right:100px;" id="editFormCardRisk">
		        <input type="hidden" id="id" name="id" value="${controlCardRiskEntity.id}" />
		         <input type="hidden"  name="controlId" value="${controlCardRiskEntity.controlId}" />
		         <input type="hidden"  name="uuidCode" value="${controlCardRiskEntity.uuidCode}" />
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">危险点分析</label>
								<div class="col-md-10">
									<textarea id="dangerPoint" name="dangerPoint"    placeholder="" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${controlCardRiskEntity.dangerPoint}</textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">主要控制措施</label>
								<div class="col-md-10">
									<textarea id="mainControl" name="mainControl"    placeholder="" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${controlCardRiskEntity.mainControl}</textarea>
								</div>
								
							</div>
				</form>
    		<div style="margin-right:100px;">
        		<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
        			<i class="ace-icon fa fa-times"></i>
        			取消
        		</button>
        		<button id="editBtnCardRisk" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
        			<i class="ace-icon fa fa-floppy-o"></i>
        			保存
        		</button>
        	</div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					
					$('#editFormCardRisk').validate({
						debug:true,
						rules: {},
						submitHandler: function (form) {
							var id = ${controlCardRiskEntity.id};
							//添加按钮
							var url = format_url("/controlCardRisk/"+id);
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editFormCardRisk").serializeObject();
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('操作成功');
									workSafeTwoDialog.close();
								},
								error:function(v,n){
									alert('操作失败');
								}
							});
						}
					});
					$("#editBtnCardRisk").on("click", function(){
    					$("#editFormCardRisk").submit();
    				});
				});
			});
        </script>
    </body>
</html>