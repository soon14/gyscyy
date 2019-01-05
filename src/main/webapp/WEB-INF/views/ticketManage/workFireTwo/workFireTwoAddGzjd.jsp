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
			<form class="form-horizontal" role="form" style="margin-right:100px;" id="addFormGzjd">
				<input class="col-md-12" id="id" name="id" type="hidden"  maxlength="64" value="${electricId}">
				<div class="form-group">
						<label class="col-md-4 control-label no-padding-right"><span style="color:red;">*</span>安全交底</label>
						<div class="col-md-8">
								<textarea id="workDisclosure" name="workDisclosure"   style="height:100px; resize:none;" class="col-md-12" maxlength="255"></textarea>
						</div>
				</div>
			
			</form>
    		<div style="margin-right:100px;">
    			
    			<button id="sureGzjd" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				确定
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					
					$('#addFormGzjd').validate({
						debug:true,
						rules:  {
							workDisclosure:{required:true,maxlength:255}
							
							},
						submitHandler: function (form) {
							
							//添加按钮
							 url = url;
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addFormGzjd").serializeObject();
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									if(result.result=="success"){
										alert('保存成功');	
										window.scrollTo(0,0);
										workSafeOneDialog.close();
										$("#page-container").load(format_url('/todoTask/list/1/10'));
									}else{
										alert(result.result);
									}
								},
								error:function(v,n){
									alert('保存失败');
								}
							});
						}
					});
					$("#sureGzjd").on("click", function(){
						
						url = format_url("/workFireTwo/sureGzjd");
						$("#addFormGzjd").submit();
    				});
					
					
				});
			});
        </script>
    </body>
</html>