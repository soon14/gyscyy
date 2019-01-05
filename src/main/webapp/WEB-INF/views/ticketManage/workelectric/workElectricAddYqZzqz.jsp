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
			<form class="form-horizontal" role="form" style="margin-right:100px;" id="addFormYqZzqz">
			<input class="col-md-12" id="id" name="id" type="hidden"  maxlength="64" value="${electricId}">
			<input class="col-md-12" id="workPersonId"  type="hidden"  maxlength="64" value="${workPersonId}">
			
							<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>有效期延长到</label>
									<div class="col-md-4">
										<div id="delayDateDiv"></div>
									</div>
									<label class="col-md-2 control-label no-padding-right">值长（值班长）签名</label>
									<div class="col-md-4">
											<input class="col-md-12" id="delayDutyMonitorId" name="delayDutyMonitorId" type="hidden" readonly="readonly" maxlength="64" value="${userEntity.id}">
											<input class="col-md-12" id="delayDutyMonitorName" name="delayDutyMonitorName" type="text" readonly="readonly" maxlength="64" value="${userEntity.name}">
									</div>
							</div>
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
    			<button id="disAgreeYqZzqz" class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
    				<i class="glyphicon glyphicon-remove-circle"></i>
    				驳回
    			</button>
    			<button id="agreeYqZzqz" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
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
					
					//日期组件
					var delayDateDatePicker = new A.my97datepicker({
						id: "delayDateId",
						name:"delayDate",
						render:"#delayDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					delayDateDatePicker.setValue('<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workElectricEntity.delayDate}" type="date"/>');
					
					
					$('#addFormYqZzqz').validate({
						debug:true,
						rules:  {
							delayDate:{required:true,maxlength:20}
							,approveIdea:{required:true,maxlength:128}//zzq修改开始
							},
						submitHandler: function (form) {
							
							//添加按钮
							 url = url;
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addFormYqZzqz").serializeObject();
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
					$("#agreeYqZzqz").on("click", function(){
						
						var taskId=$("#taskId").val();
						var procInstId=$("#procInstId").val(); 
						
						var selectUser=$('[name="duallistbox_demo1[]"]').val();
						if(selectUser==null||selectUser==""){
							alert('请选择下一步审批人!');
							return;
						}
						url = format_url("/workElectric/agreeYqZzqz?taskId="+taskId+"&procInstId="+procInstId+"&selectUser="+selectUser);
						$("#addFormYqZzqz").submit();
						
    				});
					
					$("#disAgreeYqZzqz").on("click", function(){
						var workPersonId=$("#workPersonId").val();
						var taskId=$("#taskId").val();
						var procInstId=$("#procInstId").val(); 
						url = format_url("/workElectric/disAgreeYqZzqz?taskId="+taskId+"&procInstId="+procInstId+"&workPersonId="+workPersonId);
						$("#addFormYqZzqz").submit();
					});
    				
				});
			});
        </script>
    </body>
</html>