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
<!-- 	<div class="breadcrumbs ace-save-state" id="breadcrumbs" style="margin-bottom:100px;"> -->
<!-- 				<ul class="breadcrumb"> -->
<!-- 					<li> -->
<!-- 						<i class="ace-icon fa fa-home home-icon"></i> -->
<!-- 						<a href="javascript:void(0);" onclick="firstPage()">首页</a> -->
<!-- 					</li> -->
<!-- 					<li class="active">安全管理</li> -->
<!-- 					<li class="active">应急管理</li> -->
<!-- 					<li class="active">查看</li> -->
<!-- 				</ul>/.breadcrumb -->
<!-- 				<div style="margin-right:100px;margin-top:10px;"> -->
<!--         		<button id="backButton" class="btn btn-xs btn-primary pull-right"> -->
<!--     				<i class="fa fa-reply"></i> -->
<!--     				返回 -->
<!--     			</button> -->
<!--         		</div> -->
<!--         		<h5 class="table-title header smaller blue" style="margin-left:30px;margin-right: 20px">基础信息</h5> -->
<!-- 		</div> -->
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:13%;" id="editForm">
			<input id="id" name="id" type="hidden" value="${entity.id }">
			   <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>单位
					</label>
					<div class="col-md-4">
					<input class="col-md-12" id="unitId" name="unitId" type="text" placeholder="" maxlength="64" value="${sysUnitEntity.name}" readonly="readonly">
					</div>
					  <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>填写人
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="userId" name="userId" type="text" placeholder="" maxlength="64" value="${sysUserEntity.name}" readonly="readonly">

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
						<span style="color:red;">*</span>类别
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="type" name="type" type="text" placeholder="" maxlength="64" value="${entity.typeName }" readonly="readonly"  >
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
        			var appData = ${entityJson};
					var id = $('#id').val();
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${entity.fileId},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true
					}).render();
					$("#backButton").on("click", function(){
						A.loadPage({
							render : '#page-container',
							url : format_url('/emergencyManage/index')
						});
    				});
				});
			});
        </script>
    </body>
</html>