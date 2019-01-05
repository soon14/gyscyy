<%@ page language="java" contentType="text/html; charset=utf-8"  
	pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:200px;" id="addTrainPlanForm">
				<input class="col-md-12" id="id" name="id" type="hidden" value="${ entity.id }">
				<input class="col-md-12" id="type" value="${type}" type="hidden">
				<div class="form-group" style='padding-left:10%'>
					<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 padding-zero">
							<label class="col-md-3 control-label no-padding-right">
							<span style="color:red;">*</span>组织单位：
						</label>
						<div class="padding-zero  text-left col-md-8">
							<input class="col-md-12" name="unitName" type="text" placeholder="" maxlength="64" readOnly="readOnly" value="${entity.unitName}">
						</div>
					</div>
					
					<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 padding-zero">
							<label class="col-md-3 control-label no-padding-right" for="form-field-1"><span style="color:red;">*</span>培训分类：</label>
							<div class="padding-zero  text-left col-md-8">
								<input class="col-md-12" id="classify" name="classify" type="text" placeholder="" maxlength="64" readOnly="readOnly" value="${entity.classifyName}">
							</div>
					</div>
					<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 padding-zero">
							<label class="col-md-3 control-label no-padding-right" for="form-field-1"><span style="color:red;">*</span>培训范围：</label>
							<div class="padding-zero  text-left col-md-9">
								<input class="col-md-12" id="range" name="range" type="text" placeholder="" maxlength="64" readOnly="readOnly" value="${entity.rangeName}">
							</div>
						</div>
				</div>
				<div class="form-group" style='padding-left:10%'>
					<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 padding-zero">
						<label class="col-md-3 control-label no-padding-right" for="form-field-1"><span style="color:red;">*</span>培训时间：</label>
							<div class="col-md-8 padding-zero text-left">
							<input class="col-md-12" id="time" name="time" type="text" placeholder="" maxlength="64" readOnly="readOnly" value="${entity.showTime}">
						</div>
						</div>
						<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 padding-zero">
						<label class="col-md-3 control-label no-padding-right">
							<span style="color:red;">*</span>培训人数：
						</label>
						<div class="padding-zero  text-left col-md-8 ">
							<input class="col-md-12" id="count" name="count" type="text" placeholder="" maxlength="64" readOnly="readOnly" value="${ entity.count }">
						</div>
					</div>
					<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 padding-zero">
						<label class="col-md-3 control-label no-padding-right">培训时长(小时)：
						</label>
						<div class="padding-zero text-left col-md-9">
							<input class="col-md-12" id="duration" name="duration" type="text" placeholder="" maxlength="64" readOnly="readOnly" value="${ entity.duration }">
						</div>
					</div>
				</div>
				<div class="form-group" style='padding-left:10%'>
					<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 padding-zero">
						<label class="col-md-3 control-label no-padding-right" for="form-field-1"><span style="color:red;">*</span>培训人：</label>
							<div class="col-md-8 padding-zero text-left">
							<input class="col-md-12" id="trainPersion" name="trainPersion" type="text" placeholder="" maxlength="64" value="${ entity.trainPersion }" readOnly="readOnly" >
						</div>
					</div>
					<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 padding-zero">
						<label class="col-md-3 control-label no-padding-right" for="form-field-1">发文标题：</label>
						<div class="col-md-8 padding-zero text-left">
							<input class="col-md-12" id="dispatchTitle" name="dispatchTitle" type="text"  value="${dataMap.dispatchTitle }" readonly>
						</div>
					</div>
					<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 padding-zero">
						<label class="col-md-3 control-label no-padding-right" for="form-field-1">发文字号：</label>
				   		<div class="col-md-9 padding-zero text-left">
							<input class="col-md-12" id="dispatchNumber" name="dispatchNumber" type="text"  value="${dataMap.dispatchNumber }" readonly>
						</div>
					</div>
				</div>
				<div class="form-group" style='padding-left:10%'>
				<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 padding-zero">
						<label class="col-md-3 control-label no-padding-right">
							<span style="color:red;">*</span>培训地点：
						</label>
						<div class="padding-zero  text-left col-md-8 ">
							<input class="col-md-12" id="trainLocation" name="trainLocation" readonly="readonly" type="text" placeholder="" maxlength="64" value="${entity.trainLocation }">
						</div>
					</div>
				<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 padding-zero">
							<label class="col-md-3 control-label no-padding-right" for="form-field-1"><span style="color:red;">*</span>考核方式：</label>
							<div class="padding-zero  text-left col-md-8">
							<input class="col-md-12" id="assessmentMethod" name="assessmentMethod" readonly="readonly" type="text" placeholder="" maxlength="64" value="${entity.assessmentMethodName }">
							</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-lg-3 col-md-3 col-sm-4 col-xs-12 padding-zero"  style="width:17.5%">
							<label class="col-md-12 control-label no-padding-right padding-zero  " for="form-field-1">
								<span style="color:red;">*</span>培训内容：
							</label>
					</div>
					<div class="text-left col-md-9 padding-zero" style="width:82.5%">
						<textarea name="trainContent" placeholder="" style="height:100px; resize:none;" class="col-md-12" readonly="readonly" maxlength="128">${entity.trainContent }</textarea>
					</div>
               </div>
			<div class="form-group">
					<div class="col-lg-3 col-md-3 col-sm-4 col-xs-12 padding-zero"  style="width:17.5%">
						<label class="col-md-12 control-label no-padding-right">
							<span style="color:red;">*</span>课题名称：
						</label>
					</div>
					<div class="text-left col-md-9 padding-zero" style="width:82.5%">
						<textarea name="titleName" placeholder="" style="height:100px; resize:none;" class="col-md-12" readOnly="readOnly" maxlength="128">${ entity.titleName }</textarea>
					</div>
			   </div>
			   <div class="form-group">
					<div class="col-lg-3 col-md-3 col-sm-4 col-xs-12 padding-zero"  style="width:17.5%">
						<label class="col-md-12 control-label no-padding-right">
								备注：
							</label>
					</div>
					<div class="text-left col-md-9 padding-zero" style="width:82.5%">
						<textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12" readOnly="readOnly" maxlength="128">${ entity.remark }</textarea>
					</div>
			   </div>
			   <div class="form-group">
					<div class="col-lg-3 col-md-3 col-sm-4 col-xs-12 padding-zero"  style="width:17.5%">
						<label class="col-md-12 control-label no-padding-right">
							培训信息：
						</label>
					</div>
					<div class="text-left col-md-9 padding-zero" style="width:82.5%">
						<div id="appendix" name="message">	</div>
					</div>
			   	</div>
		 </form>
		</div>
		 <div style="margin-right:200px;margin-top:10px">
				<button id="rejectBtn"  class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
					<i class="ace-icon fa fa-times"></i>驳回
				</button>
				<button id="agreeBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i>同意
				</button>
			</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['datatables','combobox','combotree','my97datepicker','uploaddropzone','selectbox'], function(A){
					var prossType = $("#type").val(); 
					if ("finish" == prossType) {
						$("#rejectBtn").hide();
						$("#agreeBtn").hide();
						$("#backBtnAddTrainPlan").hide();
					}
					//附件上传
					var uploaddropzone=new A.uploaddropzone({
						render : "#appendix",
						fileId:${entity.message},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true
					}).render();
					
					//由添加迁移页返回到列表页
					$("#backBtnAddTrainPlan").on("click",function(){
						//点击返回判断是否保存
						A.loadPage({
							render : '#page-container',
							url : format_url("/todoTask/list/1/10")
						});
					});
					
					$("#agreeBtn").on("click", function(){
						var obj = $("#addTrainPlanForm").serializeObject();
						obj.id = $("#id").val();
						obj.taskId = $("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.status = "3";
						obj.reviewResult = "1";
						$.ajax({
							url : format_url("/trainManagement/review"),
							contentType : 'application/json',
							dataType : 'JSON',
							type : 'POST',
							data : JSON.stringify(obj),
							success: function(result){
								if(result.result=="success"){
									alert("审批成功");
										window.scrollTo(0,0);
										A.loadPage({
											render : '#page-container',
											url : format_url("/todoTask/list/1/10")
										});
									}else{
										alert(result.errorMsg);
									}
								},
								error:function(v,n){
									alert("审批失败");
								}
						}); 
					});
					$("#rejectBtn").on("click", function(){
						var obj = $("#addTrainPlanForm").serializeObject();
						obj.id = $("#id").val();
						obj.taskId = $("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.status = "4";
						obj.reviewResult = "2";
						$.ajax({
							url : format_url("/trainManagement/review"),
								contentType : 'application/json',
								dataType : 'JSON',
								type : 'POST',
								data : JSON.stringify(obj),
								success: function(result){
									if(result.result=="success"){
										alert("审批成功");
											window.scrollTo(0,0);
											A.loadPage({
												render : '#page-container',
												url : format_url("/todoTask/list/1/10")
											});
									}else{
												alert(result.errorMsg);
									}
								},
								error:function(v,n){
									alert("审批失败");
								}
							}); 
					});
				});
			});
		</script>
	</body>
</html>