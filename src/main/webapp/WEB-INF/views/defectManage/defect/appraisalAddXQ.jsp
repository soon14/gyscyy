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
		<div class="">
			<form class="form-horizontal" role="form"  style='margin-right:20px' id="addForm">
		       <div class="form-group">
					<label class="col-md-1 control-label no-padding-right">
						<span style="color:red;">*</span>处理结果
					</label>
					<div class="col-md-11">
					<textarea id ="approvalOpinions" name="approvalOpinions" placeholder="" style="height:90px; resize:none;" class="col-md-12" maxlength="64"></textarea>
                	</div>
               </div>
	                <div class="form-group " >
	 					<label class="col-md-1 control-label no-padding-right">
	 					审批人
					    </label>
							<div class="col-md-11">
								<select multiple="multiple" size="10" name="duallistbox_demo1[]" id="duallist" >
									<c:forEach items="${userList}" var="userList" varStatus="vs">
										<option value="${userList.loginName}">${userList.name}</option>
									</c:forEach>
								</select>
								<div class="hr hr-16 hr-dotted"></div>
							</div>
				     </div>
			    </form>
    		<div style="margin-right:4px;" >
<!--     			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal"> -->
<!--     				<i class="ace-icon fa fa-times"></i> -->
<!--     				取消 -->
<!--     			</button> -->
    			<button id="saveOneBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;margin-bottom: 20px">
    				<i class="glyphicon glyphicon-pawn"></i>
    				发起验收
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','duallistbox','uploaddropzone'], function(A){
					var demo1 = $('select[name="duallistbox_demo1[]"]').bootstrapDualListbox({infoTextFiltered: '<span class="label label-purple label-lg">Filtered</span>'});
					var container1 = demo1.bootstrapDualListbox('getContainer');
					container1.find('.btn').addClass('btn-white btn-info btn-bold');
					var demo2 = $('select[name="duallistbox_demo2[]"]').bootstrapDualListbox({infoTextFiltered: '<span class="label label-purple label-lg">Filtered</span>'});
					var container2 = demo2.bootstrapDualListbox('getContainer');
					container2.find('.btn').addClass('btn-white btn-info btn-bold');
					var url="";
					//添加按钮
					$('#addForm').validate({
						debug:true,
						rules: {
							userId:{required:true,maxlength:64},
							checkTime:{required:true,maxlength:64},
							approvalOpinions:{required:true,maxlength:64},
						},
						submitHandler: function (form) {
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
							obj.taskId=$("#taskId").val();
							obj.procInstId=$("#procInstId").val();
							obj.defectId=$("#defectId").val();
							obj.processStatus=$("#processStatus").val();
							var selectUser=$('[name="duallistbox_demo1[]"]').val();
							if(selectUser){
								obj.userList=selectUser.join(",");
								}
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
					$("#saveOneBtn").on("click", function(){
						var selectUser=$('[name="duallistbox_demo1[]"]').val();
						if(selectUser==null||selectUser==""){
							alert('请选择审批人!');
							return;
						}
						 url = format_url("/appraisal/biotech");
						$("#addForm").submit();
    				});
					$("#saveTwoBtn").on("click", function(){
						var selectUser=$('[name="duallistbox_demo1[]"]').val();
						if(selectUser==null||selectUser==""){
							alert('请选择检修中心人员!');
							return;
						}
						 url = format_url("/appraisal/reject");
						$("#addForm").submit();
    				});
				});
			});
        </script>
    </body>
</html>