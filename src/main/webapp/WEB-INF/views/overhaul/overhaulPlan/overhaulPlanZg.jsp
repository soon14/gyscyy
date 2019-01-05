</html><%@ page language="java" contentType="text/html; charset=utf-8"  
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
			<form class="form-horizontal" role="form" style="margin-right:100px;" id="addFormSp">
				<input id="id" value="${entity.id }" type="hidden" name="id"/>
			     <input id="auditBtn" value="${entity.auditBtn }" type="hidden" name="auditBtn"/>
			     <input class="col-md-12" id="userList" name="userList" type="hidden"  maxlength="64" value="${userEntity.loginName}"> 
 				 <input class="col-md-12" id="" name="userName" type="hidden" readonly="readonly" maxlength="64" value="${userEntity.name}">
	
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>审批意见
					</label>
					<div class="col-md-10">
					<textarea id="auditMsg" name="auditMsg" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="64"></textarea>
                	</div>
               </div>
			</form>
    		<div class="col-md-12"  style="margin-right:100px;margin-top:20px;margin-bottom:20px;">
    			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal" >
				<i class="ace-icon fa fa-times"></i>
					取消
				</button>
    			<button id="agreeSp" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				确定
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','duallistbox'], function(A){
					var demo1 = $('select[name="duallistbox_demo1[]"]').bootstrapDualListbox({infoTextFiltered: '<span class="label label-purple label-lg">Filtered</span>'});
					var container1 = demo1.bootstrapDualListbox('getContainer');
					container1.find('.btn').addClass('btn-white btn-info btn-bold');
					
					
					$('#agreeSp').on('click',function(){
						var obj = $("#addFormSp").serializeObject();
						
						if($('#auditMsg').val()==null || $('#auditMsg').val()==""){
							alert('请填写审批意见!');
							return;
						}
						
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.auditMsg=$('#auditMsg').val();
						obj.id= $('#id').val();
						var url="overhaulPlan/auditPass";
						$.ajax({
							url :  format_url("/"+url),
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
						
					});
					
				});
			});
        </script>
    </body>
</html>