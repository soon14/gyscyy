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
			<form class="form-horizontal" role="form" style="margin-right:100px;" id="addFormAQ">
			<input class="col-md-12" id="id" name="id" type="hidden"  maxlength="64" value="${electricId}">

				<div class="form-group">
				<label class="col-md-2 control-label no-padding-right">审批意见</label>
				<div class="col-md-10">
					<textarea id="approveIdea" name="approveIdea" placeholder="请输入审批意见" style="height:100px; resize:none;" class="col-md-12" maxlength="128"></textarea>
				</div>
			</div>
			</form>
    			<div class="col-md-12"  style="margin-right:100px;margin-top:20px;margin-bottom:20px;">
<!--     		<button id="disagreeAQ" class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;"> -->
<!--     				<i class="glyphicon glyphicon-remove-circle"></i> -->
<!--     				驳回 -->
<!--     			</button> -->
    			<button id="agreeAQ" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				同意
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					
					$('#addFormAQ').validate({
						debug:true,
						rules:  {
							approveIdea:{maxlength:64}
							},
						submitHandler: function (form) {
							
							//添加按钮
							 url = url;
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addFormAQ").serializeObject();
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									if(result.result=="success"){
										alert('审批成功');	
										window.scrollTo(0,0);
										workSafeOneDialog.close();
										$("#page-container").load(format_url('/todoTask/list/1/10'));
									}else{
										alert(result.result);
									}
								},
								error:function(v,n){
									alert('审批失败');
								}
							});
						}
					});
					$("#agreeAQ").on("click", function(){
						var taskId=$("#taskId").val();
						var procInstId=$("#procInstId").val(); 
					
						url = format_url("/yearPurchase/agreeAqzj?taskId="+taskId+"&procInstId="+procInstId);
						$("#addFormAQ").submit();
						
    				});
// 					$("#disagreeAQ").on("click", function(){
// 						var taskId=$("#taskId").val();
// 						var procInstId=$("#procInstId").val(); 
// 						url = format_url("/workFire/disagreeAqzj?taskId="+taskId+"&procInstId="+procInstId+"&selectUser="+"");
// 						$("#addFormAQ").submit();
						
//     				});
					
				});
			});
        </script>
    </body>
</html>