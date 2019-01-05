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
			<div class="form-group">
				    <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>填报单位
				    </label>
				    <div class="col-md-4">
							<input class="col-md-12" id="unitId" name="unitId" readonly="readonly" type="text" placeholder="" maxlength="20" value="${ sysUnitEntity.name }">
					    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>填报人
					</label>
					<div class="col-md-4">
						<input class="col-md-12" id="userName" readonly="readonly" name="userName" type="text" placeholder="" maxlength="20" value="${sysUser.name }">
						<input class="col-md-12" id="userId" name="userId" type="hidden" placeholder="" maxlength="20" value="${sysUser.id }">
                	</div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>填报时间
				    </label>
				    <div class="col-md-4">
									<input class="col-md-12" id="time" name="time" readonly="readonly" type="text" placeholder="" maxlength="20" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${eventAnalysisEntity.time}" type="both"/>">
									</div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>事件名称
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="name" name="name" readonly="readonly" type="text" placeholder="" maxlength="20" value="${eventAnalysisEntity.name }">
                	</div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						附件
					</label>
					<div class="col-md-10" id="divfile">
									</div>
               </div>
			</form>		
				
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','selectbox','uploaddropzone'], function(A){
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${eventAnalysisEntity.fileId},
						autoProcessQueue:false,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true,
					}).render();
					
				});
			});
        </script>
    </body>
</html>