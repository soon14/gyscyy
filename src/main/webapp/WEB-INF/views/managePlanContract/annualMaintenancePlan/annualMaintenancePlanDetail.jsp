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
				<li class="active">生产运营计划</li>
				<li class="active">运维检修类计划</li>
				<li class="active">年度检修维护计划</li>
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
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="editForm">
			<input type="hidden" id="id" name="id" value="${entity.id }">
			  <div class="form-group" style="margin-top: 30px">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>项目名称
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="annualSubject" name="annualSubject" readonly="readonly" type="text" placeholder="" maxlength="20" value="${entity.annualSubject }">
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>计划费用(万元)
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="annualCharge" name="annualCharge" readonly="readonly" onkeyup="value=value.replace(/[\D]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[\D]/g,''))" type="text" placeholder="" maxlength="20" value="${entity.annualCharge }">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>项目内容
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="annualContent" name="annualContent" readonly="readonly" type="text" placeholder="" maxlength="20" value="${entity.annualContent }">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>责任单位
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="unitId" name="unitId" readonly="readonly" type="text" placeholder="" maxlength="20" value="${sysUnitEntity.name }">
<!-- 						<div id="unitIdDiv"></div> -->
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>计划完成时间
					</label>
					<div class="col-md-4">
					<input class="col-md-12" id="planDate" name="planDate" readonly="readonly" type="text" placeholder="" maxlength="20" value="${entity.planDate}" >
<!-- 	                   <input class="col-md-12" id="planDate" name="planDate" type="text" placeholder="" maxlength="20" value=""> -->
<!-- 	                   <div id="planDateDiv"></div> -->
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>上传人
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="name" readonly="readonly"  type="text" placeholder="" maxlength="20" value="${sysUserEntity.name }">
	                   <input class="col-md-12" id="uploadPerson" name="uploadPerson" type="hidden" placeholder="" maxlength="20" value="${sysUserEntity.id }">
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
							url : format_url("/annualMaintenancePlan/list")
						});
    				});
				});
			});
        </script>
    </body>
</html>