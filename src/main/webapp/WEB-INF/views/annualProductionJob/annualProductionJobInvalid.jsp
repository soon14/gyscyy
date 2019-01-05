<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html lang="zh">
    <head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addFormInvalid">
			<input id="invalidId" name="id" type="hidden" value="${invalidId}"/>
               <div class="form-group">
				    <label class="col-md-4 control-label no-padding-right">
						计划内容
				    </label>
				    <div class="col-md-8">
				    	 <input class="col-md-12" id="content" name="content" type="text" placeholder="" value="${entity.content }">
                    </div>
               </div>
               <div class="form-group">
				    <label class="col-md-4 control-label no-padding-right">
						鉴定结果
				    </label>
				    <div class="col-md-8">
				    	 <select id="result" name="result" class="form-control chosen-select" ></select>
                    </div>
               </div>
		       
            </form>
    		<div style="margin-right:100px;">
    			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			<button id="saveInvalid" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree'], function(A){
					//下拉框
					var identifyCombobox = new A.combobox({
						render: '#result',
						datasource:${resultCombobox},
						allowBlank:true,
						options:{
							"disable_search_threshold":10,
							"placeholder_text_single" : "请选择"
						}
					}).render();
					identifyCombobox.setValue("${entity.result}");
					
					$('#addFormInvalid').validate({
						debug:true,
						rules:  {
							content:{required:true,maxlength:64},
							result:{required:true,maxlength:20}
						},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/annualProductionJob/saveInvalid");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addFormInvalid").serializeObject();
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('鉴定成功');
									workticketDialog.close();
									annualProductionJobDatatables.draw();
								},
								error:function(v,n){
									alert('鉴定失败');
								}
							});
						}
					});
					$("#saveInvalid").on("click", function(){
						$("#addFormInvalid").submit();
    				});
    				
				});
			});
        </script>
    </body>
</html>