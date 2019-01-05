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
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						审批意见
					</label>
					<div class="col-md-10">
					<textarea id="auditMsg" name="auditMsg" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="64"></textarea>
                	</div>
               </div>
             <!-- 下面是人的列表 -->
	                <div class="form-group " >
	 					<label class="col-md-2 control-label no-padding-right">
	 					审批人
					    </label>
							<div class="col-sm-10">
							<select multiple="multiple" size="10" name="userList" id="duallist" >
								<c:forEach items="${userList}" var="userList" varStatus="vs">
									<option value="${userList.loginName}">${userList.name}</option>
								</c:forEach>
								<!-- 
								<option value="option6" selected="selected">Option 6</option>
								 -->
							</select>
							<div class="hr hr-16 hr-dotted"></div>
						</div>
				     </div>
			    </form>
    		<div style="margin-right:100px;" id="saveBtn">
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
				seajs.use(['duallistbox'], function(A){
					var demo1 = $('select[name="userList"]').bootstrapDualListbox({infoTextFiltered: '<span class="label label-purple label-lg">Filtered</span>'});
					var container1 = demo1.bootstrapDualListbox('getContainer');
					container1.find('.btn').addClass('btn-white btn-info btn-bold');
					//添加按钮
					var selectUser="";
					$("#function").on("click", function(e){
						 var selectUser=$('[name="userList"]').val();
							if(selectUser==null||selectUser==""){
								alert('请选择值长审批人!');
								return;
							}
							var obj = $("#addForm").serializeObject();
							obj.taskId=$("#taskId").val();
							obj.procInstId=$("#procInstId").val();
							obj.auditMsg=$('#auditMsg').val();
							obj.id= $('#id').val();
							var url="power/firstAudit/";
							selectUser=$('[name="userList"]').val();
							obj.userList=selectUser.join(",");
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
									alert('鉴定失败');
								}
							});
    				});
				});
			});
        </script>
    </body>
</html>