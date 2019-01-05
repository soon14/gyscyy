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
			<form class="form-horizontal" role="form"  style="margin-right:13%;" id="addForm">
			<div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>填写人
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="userName" name="userName" readonly="readonly" type="text" placeholder="" maxlength="20" value="${sysUserEntity.name }">
						<input class="col-md-12" id="userId" name="userId" type="hidden" placeholder="" maxlength="20" value="${sysUserEntity.id }">
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>单位
					</label>
					<div class="col-md-4">
										<input class="col-md-12" id="unitName" name="unitName" readonly="readonly" type="text" placeholder="" maxlength="20" value="${sysUnitEntity.name }">
								</div>
               </div>
			<div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>年号
					</label>
					<div class="col-md-4">
										<input class="col-md-12" id="yearNum" name="yearNum" readonly="readonly" type="text" placeholder="" maxlength="64" value="<fmt:formatDate pattern="yyyy" value="${entity.yearNum}" type="both"/>">
										
								</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>时间
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="time" name="time" readonly="readonly" type="text" placeholder="" maxlength="64" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${entity.time}" type="both"/>">
                    </div>
               </div>

               <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>名称
					</label>
					<div class="col-md-4">
								<input class="col-md-12" id="name" name="name" type="text" placeholder="" readonly="readonly" maxlength="64" value="${entity.name }">
								</div>
               </div>
		       <div class="form-group ">
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>附件</label>
								<div class="col-md-10" id="divfile">
								</div>
							</div>
            </form>
            <div style="margin-right:100px;">
        		<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
        			<i class="ace-icon fa fa-times"></i>
        			取消
        		</button>
        	</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						id:"fileId",
						fileId:${entity.fileId},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true
					}).render();
					$("#backButton").on("click", function(){
						A.loadPage({
							render : '#page-container',
							url : format_url('/safeReport/index')
						});
    				});
    				
				});
			});
        </script>
    </body>
</html>