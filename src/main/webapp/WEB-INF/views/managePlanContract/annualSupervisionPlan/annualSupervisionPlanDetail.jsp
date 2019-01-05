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
				<li class="active">年度技术监督计划</li>
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
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>项目名称
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="subject" name="subject" readonly="readonly" type="text" placeholder="" maxlength="20" value="${entity.subject }">
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>责任单位
					</label>
					<div class="col-md-4">
						<input class="col-md-12" id="unitId" name="unitId" readonly="readonly" type="text" placeholder="" maxlength="20" value="${sysUnitEntity.name }">
                    </div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>目的及方案
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="purposePlan" readonly="readonly" name="purposePlan" type="text" placeholder="" maxlength="20" value="${entity.purposePlan }">
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>计划完成时间
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="planDate" name="planDate" type="text" readonly="readonly" placeholder="" maxlength="20" value="${entity.planDate}" >
<!-- 	                   <div id="planDateDiv"></div> -->
                	</div>
               </div>
		       <div class="form-group">
					 <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>上传人
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="userId" readonly="readonly" name="userId" type="text" placeholder="" maxlength="20" value="${sysUserEntity.name }">
                    </div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>监督专业
				    </label>
				    <div class="col-md-4">
<!-- 				    <select class="col-md-12 chosen-select" id="supervisionMajor" name="supervisionMajor"></select> -->
						<input class="col-md-12" id="supervisionMajor" readonly="readonly" name="supervisionMajor" type="text" placeholder="" maxlength="20" value="${entity.supervisionMajorName }">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						备注
					</label>
					<div class="col-md-10">
	                    <textarea placeholder="请输入备注" name="remark" readonly="readonly" class="col-md-12" style="height:90px; resize:none;">${entity.remark }</textarea>
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
					$("#btnBack").on("click", function(){
						A.loadPage({
							render : '#page-container',
							url : format_url("/annualSupervisionPlan/list")
						});
    				});
				});
			});
        </script>
    </body>
</html>