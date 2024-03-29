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
			<form class="form-horizontal" role="form" style="margin-right:100px;" id="addFormYs">
				<div class="form-group" style="margin-top: 20px;">
								<label class="col-md-2 control-label no-padding-right">动火执行人</label>
								<div class="col-md-4">
									<div class="padding-zero inputWidth  text-left">
										 <select id="otherendExecutorSign"  ></select>
								    </div>		
								</div>
								<label class="col-md-2 control-label no-padding-right">动火工作负责人签名</label>
								<div class="col-md-4">
										<input class="col-md-12" id="endPicIdZhu" name="endPicIdZhu" type="hidden"  maxlength="128" value="${userEntity.id}">
										<input class="col-md-12" id="endPicNameZhu" name="endPicNameZhu" type="text" readonly="readonly" maxlength="128" value="${userEntity.name}">
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
    			<button id="disAgreePz" class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
    				<i class="glyphicon glyphicon-remove-circle"></i>
    				驳回
    			</button>
    			<button id="agreePz" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				同意
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','duallistbox'], function(A){
					//日期组件
					var approveStarttimeDatePicker = new A.my97datepicker({
						id: "endTimeZhuId",
						name:"endTimeZhu",
						render:"#qksjDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					
					//动火执行人
					var searchguarderName = new A.combobox({
						render : "#otherendExecutorSign",
						datasource : ${requestUsers},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					
					var demo1 = $('select[name="duallistbox_demo1[]"]').bootstrapDualListbox({infoTextFiltered: '<span class="label label-purple label-lg">Filtered</span>'});
					var container1 = demo1.bootstrapDualListbox('getContainer');
					container1.find('.btn').addClass('btn-white btn-info btn-bold');										
					$("#agreePz").on("click", function(){
						var taskId=$("#taskId").val();
						var procInstId=$("#procInstId").val(); 
						var selectUser=$('[name="duallistbox_demo1[]"]').val();
						if(selectUser==null||selectUser==""){
							alert('请选择下一步审批人!');
							return;
						}
						$('#addFormYs').validate({
							debug:true,
							rules:  {
								approveStarttime:{required:true,maxlength:64},
								approveEndtime:{required:true,maxlength:64},
								approveIdea:{required:true,maxlength:64},
								},
							submitHandler: function (form) {
								var url = format_url("/workFireTwo/agreeFzrys?taskId="+taskId+"&procInstId="+procInstId+"&selectUser="+selectUser);
								//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
								var obj = $("#addFormYs").serializeObject();
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
						$("#addFormYs").submit();
    				});
					
					$("#disAgreePz").on("click", function(){
						var taskId=$("#taskId").val();
						var procInstId=$("#procInstId").val(); 
						$('#addFormYs').validate({
							debug:true,
							rules:  {},
							submitHandler: function (form) {
								var url = format_url("/workFireTwo/disAgreeFzrys?taskId="+taskId+"&procInstId="+procInstId+"&selectUser="+"");
								//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
								var obj = $("#addFormYs").serializeObject();
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
						
						$("#addFormYs").submit();
						
					});
    				
				});
			});
        </script>
    </body>
</html>