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
					设备管理
				</li>
				<li class="active">设备异动管理</li>
				<li class="active">设备异动申请</li>
				<li class="active">查看</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content" style="margin-left:30px;margin-right:30px;">
		<div style="float:right;margin-right:0px;">
			<button id="btnBack"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" style="    margin-top: 14px;">
       			<i class="fa fa-reply"></i>
       			返回
       		</button>
		</div>
		<h5 class='table-title header smaller blue' style="margin-bottom:0px;">基础信息</h5>
		<div class="col-md-12">
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addForm">
			<input class="col-md-12" id="id" name="id" type="hidden" value="${equipAbnormalEntities.id}">
			       <div class="form-group" style="margin-top: 30px">
						<label class="col-md-2 control-label no-padding-right">
							设备异动单号
						</label>
						<div class="col-md-4">
		                   <input class="col-md-12" id="equipAbnormalBill" name="equipAbnormalBill" type="text"  value="${equipAbnormalEntities.equipAbnormalBill}" readonly="readonly" >
	                	</div>
					    <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>单位所属
					    </label>
					    <div class="col-md-4">
							<input class="col-md-12" id="unitId" name="unitId" type="text" value="${equipAbnormalEntities.unitName}" readonly="readonly">
	                    </div>
	               </div>
			       <div class="form-group">
	                    <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>设备异动编号
						</label>
						<div class="col-md-4">
							<input class="col-md-12"  type="text" value="${equipAbnormalEntities.equipmentId}" readonly="readonly">
	                	</div>
					    <label class="col-md-2 control-label no-padding-right">
							设备名称
					    </label>
					    <div class="col-md-4">
							<input class="col-md-12" id="equipName" name="equipName" type="text" value="${equipAbnormalEntities.equipName}" readonly="readonly">
	                    </div>
	               </div>
			       <div class="form-group">
					    <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>申请时间
					    </label>
					    <div class="col-md-4">
							<div id="applyDate"></div>
							<input class="col-md-12" id="applyDate" name="applyDate" type="text"  value="<fmt:formatDate value="${equipAbnormalEntities.applyDate}" type="both"/>" readonly="readonly"  >
	                    </div>
						<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>申请人
						</label>
						<div class="col-md-4">
							<input class="col-md-12" id="applyUserId" name="applyUserId" type="text"   value="${equipAbnormalEntities.applyUserName}" readonly="readonly"  >
	                	</div>
	               </div>
			       <div class="form-group">
					    <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>计划开始时间
					    </label>
					    <div class="col-md-4">
							<input class="col-md-12" id="planBeginDate" name="planBeginDate" type="text"  value="<fmt:formatDate value="${equipAbnormalEntities.planBeginDate}" type="both"/>" readonly="readonly"  >
	                    </div>
						<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>计划结束时间
						</label>
						<div class="col-md-4">
							<input class="col-md-12" id="planBeginDate" name="planBeginDate" type="text"  value="<fmt:formatDate value="${equipAbnormalEntities.planEndDate}" type="both"/>" readonly="readonly"  >
	                	</div>
	               </div>
			       <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>设备异动说明
						</label>
						<div class="col-md-10">
							<textarea class="col-md-12" id="abnormalDesc" name="abnormalDesc" type="text"  maxlength="500" readonly="readonly" style="height:100px; resize:none;">${equipAbnormalEntities.abnormalDesc}</textarea>
	                	</div>
	               </div>
			       <div class="form-group">
					    <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>设备异动原因
					    </label>
					    <div class="col-md-10">
							<textarea class="col-md-12" id="abnormalCause" name="abnormalCause" type="text"  maxlength="500" readonly="readonly" style="height:100px; resize:none;">${equipAbnormalEntities.abnormalCause}</textarea>
	                    </div>
	               </div>
			       <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">
							备注
						</label>
						<div class="col-md-10">
		                   <textarea class="col-md-12" id="remark" name="remark" type="text"  maxlength="500" readonly="readonly" style="height:100px; resize:none;">${equipAbnormalEntities.remark}</textarea>
	                	</div>
	               </div>
		        	<div class="form-group">
					    <label class="col-md-2 control-label no-padding-right">
							附件
					    </label>
						<div class="col-md-10" id="basefielid"  ></div>
               		</div>
            </form>
    			<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone','selectbox'], function(A){
					//附件上传
					var baseuploaddropzone =new A.uploaddropzone({
						render : "#basefielid",
						fileId:${equipAbnormalEntities.fileId},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks:false,//显示删除按钮
						chargeUp:true
					}).render();
					//执行人
					var executeUserIdCombobox = new A.combobox({
						render: "#executeUserId",
						datasource:${userId},
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					//返回
					$("#btnBack").on("click", function(e){
						window.scrollTo(0,0);
						$("#page-container").load(format_url('/equipAbnormal/index/10'));
					});;
				});
			});
 			</script>
		</div>
    </body>
</html>