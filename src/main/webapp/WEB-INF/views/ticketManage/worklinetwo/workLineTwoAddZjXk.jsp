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
		<div class="breadcrumbs ace-save-state" id="breadcrumbs">
				<ul class="breadcrumb">
					<li>
						<i class="ace-icon fa fa-home home-icon"></i>
						<a href="javascript:void(0);" onclick="firstPage()">首页</a>
					</li>
					<li class="active">业务管理</li>
					<li class="active">待办业务</li>
					<li class="active">待办详细</li>
					<li class="active">终结许可</li>
				</ul>
		</div>
		<div class="col-md-12" >
			<div class="page-content">
			<div class="tabbable" style="margin-top: 50px;">
			<div style="float:right; margin-top:-35px;margin-right:55px;">
					<button id="btnBackZjXk" class="btn btn-xs btn-primary">
						<i class="fa fa-reply"></i>
						返回
					</button>
			</div>
			<form class="form-horizontal" role="form" style="margin-right:100px;" id="addFormZjXk">
			<input class="col-md-12" id="id" name="id" type="hidden"  maxlength="64" value="${electricId}">
			<input class="col-md-12" id="workPersonId"  type="hidden"  maxlength="64" value="${workPersonId}">
			<h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;">（1）全部工作已结束，检修临时安全措施已拆除，常设遮拦已恢复，已恢复作业开始前状态，作业人员已全部撤离，材料工具已清理完毕。</h5>
							<div class="form-group" style="margin-top: 20px;">
								<label class="col-md-2 control-label no-padding-right">终结时间</label>
								<div class="col-md-4">
											<input class="col-md-12" id="endTime"  type="text" readonly="readonly"  maxlength="128"  value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workTicketLineTwoEntity.endTime}" type="date"/>' >
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">备注</label>
								<div class="col-md-10">
									<textarea id="remarkOther" name="remarkOther"   readonly="readonly"
										style="height:80px; resize:none;" class="col-md-12"
										maxlength="128">${workLineTwoEntity.remarkOther}</textarea>
								</div>
							</div>
							<div class="form-group" style="margin-top: 20px;">
								<label class="col-md-2 control-label no-padding-right">工作负责人签名</label>
								<div class="col-md-4">
												<input class="col-md-12" id="endPicIdZhu" name="endPicIdZhu" type="hidden"  maxlength="128" value="${workTicketLineTwoEntity.endPicId}">
												<input class="col-md-12" id="endPicNameZhu" name="endPicNameZhu" type="text" readonly="readonly" maxlength="128" value="${workTicketLineTwoEntity.endPicName}">
								</div>
								<!-- <label class="col-md-2 control-label no-padding-right">工作许可人签名</label>
								<div class="col-md-4"> -->
												<input class="col-md-12" id="endAllowIdZhu" name="endAllowIdZhu" type="hidden" placeholder="" maxlength="64" value="${userEntity.id}">
												<input class="col-md-12" id="endAllowNameZhu" name="endAllowNameZhu" type="hidden" readonly="readonly"  maxlength="64" value="${userEntity.name}">
								<!-- </div> -->
							</div>
							
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>审批意见</label>
								<div class="col-md-10">
									<textarea id="approveIdea" name="approveIdea" placeholder="请输入审批意见" style="height:60px; resize:none;" class="col-md-12" maxlength="128"></textarea>
								</div>
							</div>
							<div class="form-group form-horizontal">
									<label class="col-md-2 control-label no-padding-right">附件</label>
									<div class="col-md-10" id="divfile">
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
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					var workid='${workIdZjXk}';
					var taskIdZjXk='${taskIdZjXk}';
					var procInstIdZjXk='${procInstIdZjXk}';
					var procDefIdZjXk='${procDefIdZjXk}';
					
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:[],
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					
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
							obj.fileid=JSON.stringify(uploaddropzone.getValue());
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
						url = format_url("/workLineTwo/agreeZjXk?taskId="+taskIdZjXk+"&procInstId="+procInstIdZjXk);
						$("#addFormZjXk").submit();
    				});
					
					$("#disagreeZjXk").on("click", function(){
						var workPersonId=$("#workPersonId").val();
						url = format_url("/workLineTwo/disagreeZjXk?taskId="+taskIdZjXk+"&procInstId="+procInstIdZjXk+"&workPersonId="+workPersonId);
						$("#addFormZjXk").submit();
					});
					$('#btnBackZjXk').on('click',function(){
						var formURL="/workTicketLineTwo/approve/"+ workid;
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