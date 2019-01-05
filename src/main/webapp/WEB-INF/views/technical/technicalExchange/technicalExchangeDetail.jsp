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
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>交流申请人
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="userName" readonly="readonly" name="userName" type="text" placeholder="" maxlength="20" value="${sysUserEntity.name }">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>交流单位
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="unitId" name="unitId" readonly="readonly" type="text" placeholder="" maxlength="20" value="${ sysUnitEntity.name }">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>交流主题
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="name" name="name" readonly="readonly" type="text" placeholder="" maxlength="20" value="${technicalExchangeEntity.name }">
                	</div>
                	 <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>交流时间
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="time" name="time" readonly="readonly" type="text" placeholder="" maxlength="20" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${technicalExchangeEntity.time}" type="both"/>">
                    </div>
               </div>
                <div class="form-group">
				 <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>交流内容
				    </label>
				    <div class="col-md-10">
						<textarea id="content" name="content" placeholder="" readonly="readonly" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${technicalExchangeEntity.content }</textarea>
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
						fileId:${technicalExchangeEntity.fileId},
						autoProcessQueue:false,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true,
					}).render();
					
				});
			});
        </script>
    </body>
</html>