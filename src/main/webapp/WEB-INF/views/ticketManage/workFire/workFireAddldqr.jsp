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
			<form class="form-horizontal" role="form" style="margin-right:100px;" id="addFormXk">
								<input class="col-md-12" id="id" name="id" type="hidden"   value="${workFireEntity.id}">			
			<div class="form-group">
					<label class="col-md-4 control-label no-padding-right">主管安全生产的领导（总工程师）签名</label>
					<div class="col-md-8">
							<input class="col-md-12" id="gzxkrId"  type="text" readonly="readonly" maxlength="64" value="${userEntity.name}">
					</div>
			</div>
				<div class="form-group">
									<label class="col-md-4 control-label no-padding-right"><span style="color:red;">*</span>允许动火时间</label>
									<div class="col-md-8">
										<div id="qksjDiv"></div>
								</div>
			</div>
			<div class="form-group">
				<label class="col-md-4 control-label no-padding-right"><span style="color:red;">*</span>审批意见</label>
				<div class="col-md-8">
					<textarea id="approveIdea" name="approveIdea" placeholder="请输入审批意见" style="height:60px; resize:none;" class="col-md-12" maxlength="128"></textarea>
				</div>
			</div>
			</form>
    		<div style="margin-right:100px;">
    			
    			<button id="disagreeXk" class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
    				<i class="glyphicon glyphicon-remove-circle"></i>
    				驳回
    			</button>
    			<button id="agreeXk" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				同意
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					
				
					//日期组件
					var approveStarttimeDatePicker = new A.my97datepicker({
						id: "otherAllowDateId",
						name:"otherAllowDate",
						render:"#qksjDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					
					$('#addFormXk').validate({
						debug:true,
						rules:  {
							otherAllowDate:{required:true,maxlength:64},
							approveIdea:{required:true,maxlength:64},
							},
						submitHandler: function (form) {
							
							//添加按钮
							 url = url;
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addFormXk").serializeObject();
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
					$("#agreeXk").on("click", function(){
						var taskId=$("#taskId").val();
						var procInstId=$("#procInstId").val(); 
						url = format_url("/workFire/agreeldqr?taskId="+taskId+"&procInstId="+procInstId);
						$("#addFormXk").submit();
						
    				});
					$("#disagreeXk").on("click", function(){
						var taskId=$("#taskId").val();
						var procInstId=$("#procInstId").val(); 
						url = format_url("/workFire/disAgreeldqr?taskId="+taskId+"&procInstId="+procInstId);
						$("#addFormXk").submit();
						
    				});
				});
			});
        </script>
    </body>
</html>