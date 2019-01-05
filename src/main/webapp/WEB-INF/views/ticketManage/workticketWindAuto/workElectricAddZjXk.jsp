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
			<div class="page-content">
			<div class="tabbable" >
			<form class="form-horizontal" role="form" style="margin-right:100px;" id="addFormZjXk">
			<input class="col-md-12" id="id" name="id" type="hidden"  maxlength="64" value="${electricId}">
			<input class="col-md-12" id="workPersonId"  type="hidden"  maxlength="64" value="${workPersonId}">
			<h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;">（1）全部工作已结束，设备及安全措施已恢复至开工前状态，工作人员已全部撤离，材料工具已清理完毕。</h5>
							<div class="form-group" style="margin-top: 20px;">
								<label class="col-md-2 control-label no-padding-right">终结时间</label>
								<div class="col-md-4">
											<input class="col-md-12" id="endTime"  type="text" readonly="readonly"  maxlength="128"  value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workTicketEntity.endTime}" type="date"/>' >
								</div>
							</div>
							<h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;">已汇报值长（值班长）。</h5>
							<div class="form-group" style="margin-top: 20px;">
								<label class="col-md-2 control-label no-padding-right">工作负责人签名</label>
								<div class="col-md-4">
												<input class="col-md-12" id="endPicIdZhu" name="endPicIdZhu" type="hidden"  maxlength="128" value="${workTicketEntity.endPicId}">
												<input class="col-md-12" id="endPicNameZhu" name="endPicNameZhu" type="text" readonly="readonly" maxlength="128" value="${workTicketEntity.endPicName}">
								</div>
								<label class="col-md-2 control-label no-padding-right">工作许可人签名</label>
								<div class="col-md-4">
												<input class="col-md-12" id="endAllowIdZhu" name="endAllowIdZhu" type="hidden" placeholder="" maxlength="64" value="${userEntity.id}">
												<input class="col-md-12" id="endAllowNameZhu" name="endAllowNameZhu" type="text" readonly="readonly"  maxlength="64" value="${userEntity.name}">
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>审批意见</label>
								<div class="col-md-10">
									<textarea id="approveIdea" name="approveIdea" placeholder="请输入审批意见" style="height:60px; resize:none;" class="col-md-12" maxlength="128"></textarea>
								</div>
							</div>
			
			</form>
			</div>
			</div>
    		<div style="margin-right:100px;">
    			<button id="disagreeZjXk" class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
    				<i class="glyphicon glyphicon-remove-circle"></i>
    				驳回
    			</button>
    			<button id="agreeZjXk" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				同意
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					var workid='${workIdZjXk}';
					var taskIdZjXk='${taskIdZjXk}';
					var procInstIdZjXk='${procInstIdZjXk}';
					var procDefIdZjXk='${procDefIdZjXk}';
					
					$('#addFormZjXk').validate({
						debug:true,
						rules:  {
							endTimeZhu:{required:true,maxlength:64},
							approveIdea:{required:true,maxlength:64},
							},
						submitHandler: function (form) {
							
							//添加按钮
							 url = url;
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addFormZjXk").serializeObject();
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
										zjXkBtnDialog.close();
// 										window.scrollTo(0,0);
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
					$("#agreeZjXk").on("click", function(){
						url = format_url("/workTicketWindAuto/agreeZjXk?taskId="+taskIdZjXk+"&procInstId="+procInstIdZjXk);
						$("#addFormZjXk").submit();
    				});
					
					$("#disagreeZjXk").on("click", function(){
						var workPersonId=$("#workPersonId").val();
						url = format_url("/workTicketWindAuto/disagreeZjXk?taskId="+taskIdZjXk+"&procInstId="+procInstIdZjXk+"&workPersonId="+workPersonId);
						$("#addFormZjXk").submit();
					});
					$('#btnBackZjXk').on('click',function(){
						var formURL="/workTicketWindAuto/approve/"+ workid;
						A.loadPage({
							render : '#page-container',
							url : format_url("/todoTask/detail?id=" + taskIdZjXk + "&currentPage="+ 1 
									+ "&pageSize=" + 10+"&procInstId="+procInstIdZjXk+
									"&procDefId="+procDefIdZjXk+"&formURL="+formURL)
						});
					});
				});
			});
        </script>
    </body>
</html>