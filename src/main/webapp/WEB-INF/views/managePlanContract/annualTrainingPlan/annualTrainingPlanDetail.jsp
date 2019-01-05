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
	<div class="breadcrumbs ace-save-state" id="breadcrumbs">
			<ul class="breadcrumb">
				<li>
					<i class="ace-icon fa fa-home home-icon"></i>
					<a href="javascript:void(0);" onclick="firstPage()">首页</a>
				</li>
				<li>
					计划管理
				</li>
				<li class="active">技术质量类计划</li>
				<li class="active">年度培训计划</li>
				<li class="active">查看</li>
			</ul>
		</div>
		<div class="page-content" style="margin-left:30px;margin-right:30px;">
		<div style="float:right;margin-right:50px;">
			<button id="btnBack"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" style="    margin-top: 14px;">
       			<i class="fa fa-reply"></i>
       			返回
       		</button>
		</div>
	<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >基础信息</h5>
		<div class="col-md-12" style="margin-top: 30px">
			<form class="form-horizontal" role="form"  style="margin-right:13%;" id="editForm">
			<input id="id" name="id" type="hidden" value="${entity.id }">
			<input id="trainMember" name="trainMember"  type="hidden" value="${entity.trainMember}" class="col-md-12">	
			  <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>培训单位
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="unitId" name="unitId" readonly="readonly" type="text" placeholder="" maxlength="20" value="${sysUnitEntity.name }">
<!-- 						<div id="unitIdDiv"></div> -->
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>培训名称
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="trainName" name="trainName" readonly="readonly" type="text" placeholder="" maxlength="20" value="${entity.trainName }">
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>培训内容
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="trainContent" name="trainContent" readonly="readonly" type="text" placeholder="" maxlength="20" value="${entity.trainContent }">
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>培训时间
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="trainTime" name="trainTime" readonly="readonly" type="text" placeholder="" maxlength="20" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${entity.trainTime}"  type="both"/>">
<!-- 	                    <div id="trainTimeDiv"></div> -->
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>培训人员
				    </label>
				    <div class="col-md-4">
<%-- 						<input class="col-md-12" id="trainMember" name="trainMember" readonly="readonly" type="text" placeholder="" maxlength="20" value="${sysUserEntity.name }"> --%>
						<select class="col-md-12 chosen-select" id="trainMemberDiv" name="trainMember" disabled="disabled"></select>
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>培训地点
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="trainLocation" readonly="readonly" name="trainLocation" type="text" placeholder="" maxlength="20" value="${entity.trainLocation }">
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>培训类别
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="trainType" name="trainType" readonly="readonly" type="text" placeholder="" maxlength="20" value="${entity.trainTypeName }">
<!-- 						<select class="col-md-12 chosen-select" id="trainType" name="trainType"></select> -->
                    </div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>申请人
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="userId" name="userId" readonly="readonly" type="text" placeholder="" maxlength="20" value="${sysUserEntity.name }">
                    </div>
               </div>
			</form>
        </div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
        			var appData = ${entityJson};
					var id = $('#id').val();
					var trainMembers = $("#trainMember").val().split(",");
					//combobox组件
					var trainMemberCombobox = new A.combobox({
						render: "#trainMemberDiv",
						datasource:${createPeopleCombobox},
						multiple:true,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						},
						initValue:trainMembers
					}).render();
					$("#btnBack").on("click", function(){
						A.loadPage({
							render : '#page-container',
							url : format_url("/annualTrainingPlan/list")
						});
    				});
				});
			});
        </script>
    </body>
</html>