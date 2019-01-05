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
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					   填报单位
				    </label>
				    <div class="col-md-4">
<!-- 				    <div id="searchunitName"></div> -->
					    <input class="col-md-12" id="unitId" name="unitId" readonly="readonly" type="text" placeholder="" maxlength="20" value="${ sysUnitEntity.name }">
					</div>
					 <label class="col-md-2 control-label no-padding-right">
					    填报人
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="userName" name="userName" type="text" readonly="readonly" placeholder="" maxlength="20" value="${ sysUserEntity.name }">
					    <input class="col-md-12" id="userId" name="userId" type="hidden" readonly="readonly" placeholder="" maxlength="20" value="${ sysUserEntity.id }">
				    </div>	
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    填报时间
				    </label>
				    <div class="col-md-4">
<!-- 				    <div id="tbsjDiv"></div> -->
					    <input class="col-md-12" id="fillTime" name="fillTime" readonly="readonly" type="text" placeholder="" maxlength="20" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${eventNotificationEntity.fillTime}" type="both"/>">
					</div>
					<label class="col-md-2 control-label no-padding-right">
					    事件名称
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="eventName" name="eventName" readonly="readonly"  type="text" placeholder="" maxlength="20" value="${ eventNotificationEntity.eventName }">
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
					
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${eventNotificationEntity.fileId},
						autoProcessQueue:false,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true,
					}).render();
					
				});
			});
        </script>
    </body>
</html>