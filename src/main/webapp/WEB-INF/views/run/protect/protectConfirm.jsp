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
		       <div class="form-group">					
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>监护人
				    </label>
				    <div class="col-md-4">
				    	<select class="col-md-12 chosen-select" id="wardPersonId" name="wardPersonId"></select>
                    </div>
               </div>
            </form>
             <!-- 下面是人的列表 -->
               <form class="form-horizontal" role="form"  style="margin-right:100px;" >
	                <div class="form-group " >
	 					<label class="col-md-2 control-label no-padding-right">
	 					执行人
					    </label>
							<div class="col-sm-10">
								<select multiple="multiple" size="10" name="duallistbox_demo1[]" id="duallist" >
									<c:forEach items="${userList}" var="userList" varStatus="vs">
										<option value="${userList.loginName}">${userList.name}</option>
									</c:forEach>
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
    			<button id="zzConfirm" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
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
					//添加按钮
					var url ="";
					$('#addForm').validate({
						debug:true,
						rules: {
							wardPersonId:{required:true,maxlength:64},
							appraisalTime:{required:true,maxlength:64},
							 },
						submitHandler: function (form) {
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
							obj.id=$("#protectId").val();
							obj.taskId=$("#taskId").val();
							obj.procInstId=$("#procInstId").val();
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									if(result.result=="success"){
										alert('确定成功');	
										window.scrollTo(0,0);
										listFormDialog.close();
										$("#page-container").load(format_url('/todoTask/list/1/10'));
									}else{
										alert(result.result);
									}
								},
								error:function(v,n){
									alert('确定失败');
								}
							});
						}
					});
					$("#zzConfirm").on("click", function(){
						var taskId=$("#taskId").val();
						var procInstId=$("#procInstId").val(); 
						var selectUser=$('[name="duallistbox_demo1[]"]').val();
						if(selectUser==null||selectUser==""){
							alert('请选择下一步审批人!');
							return;
						}
						url = format_url("/protect/zzConfirm?taskId="+taskId+"&procInstId="+procInstId+"&selectUser="+selectUser);
						$("#addForm").submit();
    				});
						//监护人
						var userId = new A.combobox({
							render : "#wardPersonId",
							datasource : ${protectCombobox},
							allowBlank: true,
							options : {
								"disable_search_threshold" : 10
							}
						}).render();
						userId.setValue('${userEntity.id}');
						//日期组件
						var appraisalTimeDiv = new A.my97datepicker({
							id: "appraisalTime",
							name:"appraisalTime",
							render:"#appraisalTimeDiv",
							options : {
									isShowWeek : false,
									skin : 'ext',
									maxDate: "",
									minDate: "",
									dateFmt: "yyyy-MM-dd HH:mm:ss"
							}
						}).render();
						appraisalTimeDiv.setValue('<fmt:formatDate value="${date}" type="both"/>');
				});
			});
        </script>
    </body>
</html>