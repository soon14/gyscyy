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
			<form class="form-horizontal" role="form" style="margin-right:100px;" id="addFormGzfzrbd">
			<input class="col-md-12" id="id" name="id" type="hidden"  maxlength="64" value="${electricId}">
							<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">原工作负责人</label>
									<div class="col-md-4">
											<c:if test="${workElectricEntity.changeOldPicName==null||workElectricEntity.changeOldPicName==''}">
												<input class="col-md-12" id="changeOldPicId" name="changeOldPicId" type="hidden" readonly="readonly" maxlength="64" value="${userEntity.id}">
											<input class="col-md-12" id="changeOldPicName" name="changeOldPicName" type="text" readonly="readonly" maxlength="64" value="${userEntity.name}">
											</c:if>
											<c:if test="${workElectricEntity.changeOldPicName!=null&&workElectricEntity.changeOldPicName!=''}">
												<input class="col-md-12" id="changeOldPicId" name="changeOldPicId" type="hidden" placeholder="" maxlength="64" value="${workElectricEntity.changeOldPicId}">
												<input class="col-md-12" id="changeOldPicName" name="changeOldPicName" type="text" readonly="readonly"  maxlength="64" value="${workElectricEntity.changeOldPicName}">
											</c:if>
								</div>
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>变更后工作负责人</label>
								<div class="col-md-4">
											<select class="col-md-12 chosen-select" id="changeNewPicId" name="changeNewPicId"></select>
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
    			<button id="sureGzfzrbg" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
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
					//日期组件
					var changeSignerDateDatePicker = new A.my97datepicker({
						id: "changeSignerDateId",
						name:"changeSignerDate",
						render:"#changeSignerDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					changeSignerDateDatePicker.setValue('<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workElectricEntity.changeSignerDate}" type="date"/>');
					//日期组件
					var changeAllowDateDatePicker = new A.my97datepicker({
						id: "changeAllowDateId",
						name:"changeAllowDate",
						render:"#changeAllowDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					changeAllowDateDatePicker.setValue('<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workElectricEntity.changeAllowDate}" type="date"/>');
					
					
					
					//过滤掉 原工作负责人
	/* 				var ysobj=${userListBox};
					var gl=new Array();
					if(ysobj){
						for(var i=0;i<ysobj.length;i++){
							if(ysobj[i].name!='${userEntity.name}'){
								gl.push(ysobj[i]);
							}
						}
					} */
					
					//combobx组件
					var userListBoxCombobox = new A.combobox({
						render: "#changeNewPicId",
						//返回数据待后台返回TODO
						datasource:${userListBox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					
					
					
					$('#addFormGzfzrbd').validate({
						debug:true,
						rules:  {
							changeNewPicId:{required:true,maxlength:32},
							approveStarttime:{required:true,maxlength:64},
							approveEndtime:{required:true,maxlength:64}
							},
						submitHandler: function (form) {
							
							//添加按钮
							 url = url;
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addFormGzfzrbd").serializeObject();
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
					$("#sureGzfzrbg").on("click", function(){
						
						var taskId=$("#taskId").val();
						var procInstId=$("#procInstId").val(); 
						var selectUser=$('[name="duallistbox_demo1[]"]').val();
						if(selectUser==null||selectUser==""){
							alert('请选择下一步审批人!');
							return;
						}
						url = format_url("/workTicketWindFlow/sureGzfzrbg?taskId="+taskId+"&procInstId="+procInstId+"&selectUser="+selectUser);
						$("#addFormGzfzrbd").submit();
    				});
					
				});
			});
        </script>
    </body>
</html>