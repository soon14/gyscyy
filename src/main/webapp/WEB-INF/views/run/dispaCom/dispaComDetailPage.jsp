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
				    			<input  name="unit" type="text" value="${dataMap.unitName}" class="col-md-12" readonly="readonly">				    				    
					</div>
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>时间
				    </label>
				    <div class="col-md-4">

				    <input class="col-md-12" id="time" name="time" type="text"  maxlength="20"  readonly="readonly" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${dataMap.time}" type="date"/>'>		
<%-- 						<input name='time' value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${dataMap.time}" type="text"/>' readonly="readonly"> --%>
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 调度
				    </label>
				    <div class="col-md-4">
						<input  class="col-md-12" name="dispath" value="${dataMap.dispathName}" readonly="readonly" type="text">					    					
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>发令人
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="dispatchPerson" name="dispatchPerson" type="text" placeholder="请输入发令人" maxlength="64" value="${dataMap.dispatchPerson}" readonly="readonly">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span>受令人
				    </label>
				    <div class="col-md-4">
<%-- 				    <input class="col-md-12" id="dutyChiefPerson" name="dutyChiefPerson" type="text"  maxlength="64"  placeholder="请输入受令人" value="${sysUser.name}" readonly="readonly">	 --%>
				    <input class="col-md-12" id="dutyChiefPersonDiv" name="dutyChiefPerson" readonly="readonly"
						type="text"  maxlength="64"  placeholder="请输入受令人" value="${dataMap.dutyChiefPerson}">				    					
				    </div>	
    		     </div>
			      <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>联系内容
				    </label>
				    <div class="col-md-10">
					    <textarea name="contactContent" placeholder="请输入联系内容" style="height:100px; resize:none;" class="col-md-12" maxlength="250" readonly="readonly">${dataMap.contactContent}</textarea>				   
				    </div>
			     </div>
			</form>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
        			var appData = ${dataMapJson};
        			var loginName = '${SysUserEntity.loginName}';
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