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
			<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>许可时间</label>
									<div class="col-md-4">
										<div id="qksjDiv"></div>
								</div>
					<label class="col-md-2 control-label no-padding-right">工作许可人签名</label>
					<div class="col-md-4">
							<input class="col-md-12" id="gzxkrId"  type="text" readonly="readonly" maxlength="64" value="${userEntity.name}">
					</div>
					<input class="col-md-12" id="id" name="id" type="hidden"  maxlength="64" value="${electricId}">
								<input class="col-md-12" id="allowPicPersonId" name="allowPicPersonId"  type="hidden"  maxlength="64" value="${workTicketLineTwoEntity.guarderId}">
								<input class="col-md-12" id="allowPicPersonName" name="allowPicPersonName" type="hidden"  readonly="readonly" type="text"  maxlength="64" value="${workTicketLineTwoEntity.guarderName}">
			</div>
			
			<div class="form-group">
				<label class="col-md-2 control-label no-padding-right">其他安全注意事项</label>
				<div class="col-md-10">
					<textarea id="otherSafe" name="otherSafe" placeholder="其他安全注意事项" style="height:60px; resize:none;" class="col-md-12" maxlength="128"></textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>审批意见</label>
				<div class="col-md-10">
					<textarea id="approveIdea" name="approveIdea" placeholder="请输入审批意见" style="height:60px; resize:none;" class="col-md-12" maxlength="128"></textarea>
				</div>
			</div>
			</form>
    		<div style="margin-right:100px;">
    		    <button id="disAgreeXk" class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
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
						id: "qksjZhuId",
						name:"qksjZhu",
						render:"#qksjDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					approveStarttimeDatePicker.setValue('${xkdate}');
					
					$('#addFormXk').validate({
						debug:true,
						rules:  {
							qksjZhu:{required:true,maxlength:64},
							approveStarttime:{required:true,maxlength:64},
							approveEndtime:{required:true,maxlength:64},
							approveIdea:{required:true,maxlength:64},
							},
						submitHandler: function (form) {
							var sdate='${sdate}';
							var edate='${edate}';
							if(approveStarttimeDatePicker.getValue()<sdate||approveStarttimeDatePicker.getValue()>edate){
								alert("许可时间应该在批准时间范围之内！");
								return;
							}
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
						url = format_url("/workLineTwo/agreeXk?taskId="+taskId+"&procInstId="+procInstId);
						$("#addFormXk").submit();
						
    				});
					
					$("#disAgreeXk").on("click", function(){
						var taskId=$("#taskId").val();
						var procInstId=$("#procInstId").val(); 
						url = format_url("/workLineTwo/disAgreeXk?taskId="+taskId+"&procInstId="+procInstId+"&selectUser="+"");
						$("#addFormXk").submit();
					});
					
				});
			});
        </script>
    </body>
</html>