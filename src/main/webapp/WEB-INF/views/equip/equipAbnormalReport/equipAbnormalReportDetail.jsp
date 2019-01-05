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
				<li class="active">设备异动报告</li>
				<li class="active">查看</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content" style="margin-left:30px;margin-right:30px;">
		<div style="float:right;margin-right:50px;">
			<button id="btnBack"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" style="    margin-top: 14px;">
       			<i class="fa fa-reply"></i>
       			返回
       		</button>
		</div>
       <h5 class='table-title-withoutbtn header smaller blue' style="margin-bottom:0px;">基础信息</h5>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addForm">
			<input class="col-md-12" id="id" name="id" type="hidden"     value="${equipAbnormalReportEntity.id}">
			       <div class="form-group" style="margin-top: 30px">
					    <label class="col-md-2 control-label no-padding-right">
							报告编码
					    </label>
					    <div class="col-md-4">
							<input class="col-md-12" id="reportCode" name="reportCode" type="text" placeholder="" maxlength="64" value="${equipAbnormalReportEntity.reportCode}" readonly="readonly">
	                    </div>
	                    <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;" >*</span>报告名称
						</label>
						<div class="col-md-4">
		                   <input class="col-md-12" id="reportName" name="reportName" type="text" placeholder="" maxlength="64" value="${equipAbnormalReportEntity.reportName}" readonly="readonly">
	                	</div>
	               </div>
			       <div class="form-group">
			       		 <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;" >*</span>设备编号
					    </label>
					    <div class="col-md-4">
							<input class="col-md-12" id="equipCodeDiv" name="equipCodeDiv" type="text" placeholder="" maxlength="64" value="${equipAbnormalReportEntity.equipCode}" readonly="readonly">
	                    </div>
						<label class="col-md-2 control-label no-padding-right">
							设备名称
						</label>
						<div class="col-md-4">
		                   <input class="col-md-12" id="equipName" name="equipName" type="text" placeholder="" maxlength="64" value="${equipAbnormalReportEntity.equipName}" readonly="readonly">
	                	</div>
	               </div>
			       <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;" >*</span>提交时间
						</label>
						<div class="col-md-4">
		                   <div id="submitDateDiv"></div>
		                   <input class="col-md-12" id="submitDate" name="submitDate" type="text"  value="<fmt:formatDate value="${equipAbnormalReportEntity.submitDate}" type="both"/>" readonly="readonly"  >
	                	</div>
		       		    <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;" >*</span>提交人
					    </label>
					    <div class="col-md-4">
							<input class="col-md-12" id="submitPersionName" name="submitPersionName" type="text"   value="${equipAbnormalReportEntity.submitPersionName}" readonly="readonly">
	                    </div>
	               </div>
	               <div class="form-group">
	               <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;" >*</span>设备所属
					    </label>
	               <div class="col-md-4">
		                    <input class="col-md-12" type="text" id="equipBelong" name="equipBelong" value="${equipAbnormalReportEntity.equipBelong }" readonly="readonly"  placeholder="" maxlength="64">
	                    </div>
	               </div>
			       <div class="form-group">
						 <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;" >*</span>执行情况说明
					    </label>
					    <div class="col-md-10">
							<textarea class="col-md-12" id="excuteInfoExplain" name="excuteInfoExplain" type="text"  maxlength="128" style="height:100px; resize:none;" readonly="readonly">${equipAbnormalReportEntity.excuteInfoExplain}</textarea>
	                    </div>
	               </div>
			       <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">
							备注
						</label>
						<div class="col-md-10">
		                   <textarea class="col-md-12" id="remark" name="remark" type="text"  maxlength="128" style="height:100px; resize:none;" readonly="readonly">${equipAbnormalReportEntity.remark}</textarea>
	                	</div>
	               </div>
			       <div class="form-group">
					    <label class="col-md-2 control-label no-padding-right">
							附件
					    </label>
						<div class="col-md-10" id="basefielid"  ></div>
	               </div>
            </form>
		</div>
		</div>
		<script type="text/javascript">
			var selectEquipLedger;
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone','wysiwyg','selectbox'], function(A){
					//基础信息附件上传
					var baseuploaddropzone =new A.uploaddropzone({
						render : "#basefielid",
						fileId:${equipAbnormalReportEntity.fileId},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks:false,//显示删除按钮
						chargeUp:true
					}).render();
					$('#addForm').validate({
						debug:true,
						rules:  {
							reportCode:{required: true},
							reportName:{required: true},
							equipCode:{required: true},
							equipName:{required: true},
							submiePersionId:{required: true},
							submitDate:{required: true},
							excuteInfoExplain:{required: true}},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/equipAbnormalReport/updateEquipAbnormalReport");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
							obj.fileId=JSON.stringify(baseuploaddropzone.getValue());
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('添加成功');
									$("#page-container").load(format_url('/equipAbnormalReport/index'));
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					//返回
					$("#btnBack").on("click", function(e){
						window.scrollTo(0,0);
						$("#page-container").load(format_url('/equipAbnormalReport/index'));
					});
				});
			});
        </script>
    </body>
</html>