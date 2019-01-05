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
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addForm">
			<input id="id" value="${entity.id }" type="hidden" name="id"/>
			<input id="excuteUserName"  type="hidden" name="excuteUserName"/>
			<input id="costodyUserName" type="hidden" name="costodyUserName"/>
                  
                   <div class="form-group" style="margin-top:40px;">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>执行人
					</label>
					<div class="col-md-10">
						<select class="col-md-12 chosen-select" id="excuteUserId" name="excuteUserId"></select>
                	</div>
                	  </div>
                	  <div class="form-group"  style="margin-top:30px;">
                	  		 <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>监护人
				    </label>
				    <div class="col-md-10" >
				    	<select class="col-md-12 chosen-select" id="costodyUserId" name="costodyUserId"></select>
                    </div>
                	  
                	  </div>

                	<div class="form-group"  style="margin-top:40px;">
					<label class="col-md-2 control-label no-padding-right">
						审批意见
					</label>
					<div class="col-md-10">
					<textarea id="auditMsg" name="auditMsg" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="64"></textarea>
                	</div>
               </div>
			    </form>
    		<div style="margin-right:100px;margin-top:80px;" id="saveBtn">
    			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			<button id="function" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				通过
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['duallistbox','combobox'], function(A){
					var demo1 = $('select[name="userList"]').bootstrapDualListbox({infoTextFiltered: '<span class="label label-purple label-lg">Filtered</span>'});
					var container1 = demo1.bootstrapDualListbox('getContainer');
					container1.find('.btn').addClass('btn-white btn-info btn-bold');
					//执行人
					var excuteUserId = new A.combobox({
						render : "#excuteUserId",
						datasource : ${requestUsers},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						},
						callback: function(data){
							if(data&&data[0]){
								$("#excuteUserName").val(data[0].Name);
							};
							
						}
					}).render();
					
					//监控人
					var costodyUserId = new A.combobox({
						render : "#costodyUserId",
						datasource : ${requestUsers},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						},
						callback: function(data){
							if(data&&data[0]){
								$("#costodyUserName").val(data[0].Name);
							};
							
						}
					}).render();
					
					
					$('#addForm').validate({
						debug:true,
						rules: {costodyUserId:{required:true,maxlength:64},
							excuteUserId:{required:true,maxlength:64}
							 },
						submitHandler: function (form) {
							if(excuteUserId.getValue() == costodyUserId.getValue()){
								alert("执行人和监护人不能为同一人");
								return;
							}
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
							obj.taskId=$("#taskId").val();
							obj.procInstId=$("#procInstId").val();
							obj.auditMsg=$('#auditMsg').val();
							obj.id= $('#id').val();
							obj.userList= excuteUserId.getValue();
							var url="power/secondAudit/";
							$.ajax({
								url :  format_url("/"+url+"/"+obj.taskId),
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									if(result.result=="success"){
										alert('审批完成');	
										window.scrollTo(0,0);
										listFormDialog.close();
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
					
					
					
					$("#function").on("click", function(e){
						$("#addForm").submit();
    				});
				});
			});
        </script>
    </body>
</html>