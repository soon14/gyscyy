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
			<form class="form-horizontal" role="form" style="margin-right:100px;" id="addFormGzfzrbdQf">
			<input class="col-md-12" id="id" name="id" type="hidden"  maxlength="64" value="${electricId}">
							<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">原工作负责人</label>
									<div class="col-md-4">
											<input class="col-md-12" id="changeOldPicId" name="changeOldPicId" type="hidden" placeholder="" maxlength="64" value="${workLineTwoEntity.changeOldPicId}">
											<input class="col-md-12" id="changeOldPicName" name="changeOldPicName" type="text" readonly="readonly"  maxlength="64" value="${workLineTwoEntity.changeOldPicName}">
								</div>
								<label class="col-md-2 control-label no-padding-right">变更后工作负责人</label>
								<div class="col-md-4">
											<input class="col-md-12" id="changeNewPicId" name="changeNewPicId" type="hidden"  maxlength="64" value="${workLineTwoEntity.changeNewPicId}">
											<input class="col-md-12" id="changeNewPicName" name="changeNewPicName" type="text" readonly="readonly" maxlength="64" value="${workLineTwoEntity.changeNewPicName}">
								</div>
							</div>
							
							
										<input class="col-md-12" id="changeSignerId" name="changeSignerId" type="hidden" placeholder="" maxlength="64" value="${userEntity.id}">
										<input class="col-md-12" id="changeSignerName" name="changeSignerName" type="hidden" readonly="readonly"  maxlength="64" value="${userEntity.name}">
								
										<input class="col-md-12" id="changeSignerDate" name="changeSignerDate" type="hidden" readonly="readonly"  maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${qfrDate}" type="date"/>'>
								
							
							
						<div class="form-group">
							<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>审批意见</label>
							<div class="col-md-10">
								<textarea id="approveIdea" name="approveIdea" placeholder="请输入审批意见" style="height:60px; resize:none;" class="col-md-12" maxlength="128"></textarea>
							</div>
						</div>	
							
								
			<div class="form-group">
						<label class="col-md-2 control-label no-padding-right">审批人</label>
						<div class="col-sm-10">
							<select multiple="multiple" size="5" name="duallistbox_demo1[]" id="duallist">
								<c:forEach items="${userList}" var="userList" varStatus="vs">
									<option value="${userList.loginName}">${userList.name}</option>
								</c:forEach>
							</select>
						</div>
			</div>
			
			</form>
    		<div style="margin-right:100px;">
    			<button id="disAgreeGzfzrbgQf" class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
    				<i class="glyphicon glyphicon-remove-circle"></i>
    				驳回
    			</button>
    			<button id="agreeGzfzrbgQf" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				同意
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','duallistbox'], function(A){
					var demo1 = $('select[name="duallistbox_demo1[]"]').bootstrapDualListbox({infoTextFiltered: '<span class="label label-purple label-lg">Filtered</span>'});
					var container1 = demo1.bootstrapDualListbox('getContainer');
					container1.find('.btn').addClass('btn-white btn-info btn-bold');
					
					
					$('#addFormGzfzrbdQf').validate({
						debug:true,
						rules:  {
							changeNewPicName:{required:true,maxlength:32},
							approveStarttime:{required:true,maxlength:64},
							approveEndtime:{required:true,maxlength:64},
							approveIdea:{required:true,maxlength:64},
							},
						submitHandler: function (form) {
							
							//添加按钮
							 url = url;
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addFormGzfzrbdQf").serializeObject();
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
					$("#agreeGzfzrbgQf").on("click", function(){
						var yuanPic='${workLineTwoEntity.changeOldPicId}';
						var nowPic=${userEntity.id};
// 						if(yuanPic==nowPic){
// 							alert("工作负责人和签发人不能是同一个人!");
// 							return;
// 						}
						var taskId=$("#taskId").val();
						var procInstId=$("#procInstId").val(); 
						var selectUser=$('[name="duallistbox_demo1[]"]').val();
						if(selectUser==null||selectUser==""){
							alert('请选择下一步审批人!');
							return;
						}
						url = format_url("/workLineTwo/agreeGzfzrbgQf?taskId="+taskId+"&procInstId="+procInstId+"&selectUser="+selectUser);
						$("#addFormGzfzrbdQf").submit();
    				});
					$("#disAgreeGzfzrbgQf").on("click", function(){
						var taskId=$("#taskId").val();
						var procInstId=$("#procInstId").val(); 
						url = format_url("/workLineTwo/disAgreeGzfzrbgQf?taskId="+taskId+"&procInstId="+procInstId);
						$("#addFormGzfzrbdQf").submit();
    				});
					
				});
			});
        </script>
    </body>
</html>