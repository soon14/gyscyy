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
						<span style="color:red;">*</span>典型票名称
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="name" name="name" type="text" placeholder="" maxlength="64" value="">
                    </div>
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>典型票类型
					</label>
					<div class="col-md-4">
					<input class="col-md-12" id="typeName" name="typeName" type="text" 
						placeholder="" maxlength="64" value="${t.typeName}" readonly="readonly">
						<input type="hidden" name="type" id="type" value="${t.type}"/>
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
						rules:  {
							name : {required : true,maxlength : 64},
							type : {required : true,maxlength : 64},
						},
						submitHandler: function (form) {
							var obj = $("#addForm").serializeObject();
							 listFormDialog.close(obj);
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