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
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="editForm">
			    	<input type="hidden" id="id" name="id" value="${dataMap.id}" />
			
			   <div class="form-group">
			     <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>单位名称
				    </label>
				    <div class="col-md-4">
					    <select id="unitIdDiv" name="unitId" class="col-md-12 chosen-select"></select>
					</div>
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>时间
				    </label>
				    <div class="col-md-4">
						<c:if test="${SysUserEntity.loginName=='super'}">
						<div id="timeDiv"></div>
						</c:if>
						<c:if test="${SysUserEntity.loginName!='super'}">
						<input class="col-md-12"  type="text" name='time'
								readonly="readonly" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${dataMap.time}" type="date"/>'>
						</c:if>
					</div>
				  
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 调度
				    </label>
				    <div class="col-md-4">
					<select id="dispathDiv" class="col-md-12" name="dispath"></select>					    					
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>发令人
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="dispatchPerson" name="dispatchPerson" type="text" placeholder="请输入发令人" maxlength="64" value="${dataMap.dispatchPerson}">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span>受令人
				    </label>
				    <div class="col-md-4">
<!-- 					<select id="dutyChiefPersonDiv" class="col-md-12 chosen-select" -->
<!-- 						name="dutyChiefPerson"></select> -->
						<input class="col-md-12" id="dutyChiefPersonDiv" name="dutyChiefPerson"
						type="text"  maxlength="64"  placeholder="请输入受令人" value="${dataMap.dutyChiefPerson}">
					</div>
    		     </div>
			      <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>联系内容
				    </label>
				    <div class="col-md-10">
					    <textarea name="contactContent" placeholder="请输入联系内容" style="height:100px; resize:none;" class="col-md-12" maxlength="250">${dataMap.contactContent}</textarea>				   
				    </div>
			     </div>
			</form>
    		<div style="margin-right:100px;">
        		<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
        			<i class="ace-icon fa fa-times"></i>
        			取消
        		</button>
        		<button id="editBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
        			<i class="ace-icon fa fa-floppy-o"></i>
        			保存
        		</button>
        	</div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
        			var appData = ${dataMapJson};
        			var loginName = '${SysUserEntity.loginName}';
        			//调度
        			var dispathCombobox = new A.combobox({
						render: "#dispathDiv",
						datasource:${dispatchCombobox},
						options:{
							"disable_search_threshold":10
						},
						initValue:"${dataMap.dispath}"

					}).render();
        			//电站名称
					var unitIdCombobox = new A.combobox({
							render : "#unitIdDiv",
							datasource : ${dispaComTreeList},
							allowBlank: true,
							options : {
								"disable_search_threshold" : 10
							}
					}).render();
					unitIdCombobox.setValue(${dataMap.unitId});
        			if(loginName=='super'){
						//日期组件
						var timeDatePicker = new A.my97datepicker({
							id: "timeId",
							name: "time",
							render: "#timeDiv",
							options : {
									isShowWeek : false,
									skin : 'ext',
									maxDate: "",
									minDate: "",
									dateFmt: "yyyy-MM-dd HH:mm"
							}
						}).render();
						timeDatePicker.setValue('${dataMap.time}');
        			}	
					var id = $('#id').val();
					$('#editForm').validate({
						debug:true,
						rules: {time:{date:true},
							unitId:{required:true,maxlength:11},
							dispath:{required:true,maxlength:64},
							dispatchPerson:{required:true,maxlength:64},
							contactContent:{required:true,maxlength:250},
							dutyChiefPerson:{required:true,maxlength:250}},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/dispaCom/update/");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editForm").serializeObject();
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('操作成功');
									listFormDialog.close();
								},
								error:function(v,n){
									alert('操作失败');
								}
							});
						}
					});
					$("#editBtn").on("click", function(){
    					$("#editForm").submit();
    				});
				});
			});
        </script>
    </body>
</html>