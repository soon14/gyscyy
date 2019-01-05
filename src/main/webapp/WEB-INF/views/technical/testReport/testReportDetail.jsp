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
			<input id="id" name="id" value="${id}" type="hidden">
			<input id="testMember" name="testMember"  type="hidden" value="${testReportEntity.testMember}" class="col-md-12">
			   <div class="form-group">
			   <label class="col-md-2 control-label no-padding-right">
					    填报人
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="userName" name="userName" type="text" readonly="readonly" placeholder="" maxlength="20" value="${ sysUser.name }">
					    <input class="col-md-12" id="userId" name="userId" type="hidden" readonly="readonly" placeholder="" maxlength="20" value="${ sysUser.id }">
					</div>
					<label class="col-md-2 control-label no-padding-right">
					    填报单位
				    </label>
				    <div class="col-md-4">
				    <input class="col-md-12" id="unitId" name="unitId" readonly="readonly" type="text" placeholder="" maxlength="20" value="${ sysUnitEntity.name }">
					</div>
				    
				</div>
			   <div class="form-group">
			   	<label class="col-md-2 control-label no-padding-right">
					    项目名称
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="name" name="name" type="text" placeholder="" readonly="readonly" maxlength="20" value="${ testReportEntity.name }">
					</div>
				    <label class="col-md-2 control-label no-padding-right">
					    试验人员
				    </label>
				    <div class="col-md-4">
				    <select id="testMemberDiv" class="col-md-12" name="testMember" disabled="disabled"></select>	
<%-- 				    <input class="col-md-12" id="testMember" readonly="readonly" name="testMember" type="text" placeholder="" maxlength="20" value="${ testReportEntity.testMember }"> --%>
				    </div>	
				    
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    填报时间
				    </label>
				    <div class="col-md-4">
				    <input class="col-md-12" id="time" name="time" readonly="readonly" type="text" placeholder="" maxlength="20" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${testReportEntity.time}" type="both"/>">
				    </div>	
				    
				</div>
			   <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">附件</label>
									<div class="col-md-10" id="divfile">
									</div>
               </div>
			</form>		
				
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','selectbox','uploaddropzone'], function(A){
					var testMembers = $("#testMember").val().split(",");
					//combobox组件
					var btmPersonsIdsCombobox = new A.combobox({
						render: "#testMemberDiv",
						datasource:${testReportCombobox},
						multiple:true,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						},
						initValue:testMembers
					}).render();
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${testReportEntity.fileId},
						autoProcessQueue:false,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true,
					}).render();
					
				});
			});
        </script>
    </body>
</html>