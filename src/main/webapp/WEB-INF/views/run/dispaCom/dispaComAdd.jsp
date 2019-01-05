<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/views/common/meta.jsp"%>
</head>
<body>
	<div class="col-md-12">
		<form class="form-horizontal" role="form" style="margin-right: 100px;"
			id="dispoaaddForm">
			<div class="form-group">
			<label class="col-md-2 control-label no-padding-right"> <span
					style="color: red;">*</span>电站名称
				</label>
				<div class="col-md-4">
				<select id="unitIdDiv" name="unitId" class="col-md-12 chosen-select"></select>
<!-- 					<div id="unitIdDiv"></div> -->
				</div>
				<label class="col-md-2 control-label no-padding-right"> <span
					style="color: red;">*</span>时间
				</label>
				<div class="col-md-4">
<%-- 					<c:if test="${SysUserEntity.loginName=='super'}"> --%>
					<div id="timeDiv"></div>
<%-- 					</c:if> --%>
<%-- 					<c:if test="${SysUserEntity.loginName!='super'}"> --%>
<%-- 					<input class="col-md-12" type="text" name='time' value="${cTime}" readonly="readonly"> --%>
<%-- 					</c:if> --%>
				</div>
				
			</div>
			<div class="form-group">
				<label class="col-md-2 control-label no-padding-right"> <span
					style="color: red;">*</span>调度
				</label>
				<div class="col-md-4">
					<select id="dispathDiv" class="col-md-12 chosen-select"
						name="dispath"></select>
				</div>
				<label class="col-md-2 control-label no-padding-right"> <span
					style="color: red;">*</span>发令人
				</label>
				<div class="col-md-4">
					<input class="col-md-12" id="dispatchPerson" name="dispatchPerson"
						type="text"  maxlength="64"  placeholder="请输入发令人">
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-2 control-label no-padding-right"> <span
					style="color: red;">*</span>受令人
				</label>
				<div class="col-md-4">
<!-- 					<select id="dutyChiefPersonDiv" class="col-md-12 chosen-select" -->
<!-- 						name="dutyChiefPerson"></select> -->
				<input class="col-md-12" id="dutyChiefPerson" name="dutyChiefPerson"
				type="text"  maxlength="64"  placeholder="请输入受令人">
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-2 control-label no-padding-right"> <span
					style="color: red;">*</span>联系内容
				</label>
				<div class="col-md-10">
					<textarea name="contactContent" placeholder=""
						style="height: 100px; resize: none;" class="col-md-12"
						maxlength="128"></textarea>
				</div>
			</div>
		</form>
		<div style="margin-right: 100px;">
			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
				<i class="ace-icon fa fa-times"></i> 取消
			</button>
			<button id="dispoaSaveBtn" class="btn btn-xs btn-success pull-right"
				style="margin-right: 15px;">
				<i class="ace-icon fa fa-floppy-o"></i> 保存
			</button>
		</div>
	</div>
	<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					var loginName = '${SysUserEntity.loginName}';
					//调度
					var dispatchCombobox = new A.combobox({
						render: "#dispathDiv",
						datasource:${dispatchCombobox},
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					//日期组件
					var timeDatePicker = new A.my97datepicker({
						id: "timeId",
						name:"time",
						render:"#timeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					timeDatePicker.setValue('${cTime}');
					//电站名称
					var  unitIdCombobox = new A.combobox({
						render : "#unitIdDiv",
						datasource : ${dispaComTreeList},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					$('#dispoaaddForm').validate({
						debug:true,
						rules:  {time:{required:true,date:true},
							unitId:{required:true,maxlength:64},
							dispath:{required:true,maxlength:64},
							dispatchPerson:{required:true,maxlength:64},
							contactContent:{required:true,maxlength:250},
							dutyChiefPerson:{required:true,maxlength:250}},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/dispaCom/save");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#dispoaaddForm").serializeObject();
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('添加成功');
									listFormDialog.close();
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					$("#dispoaSaveBtn").on("click", function(){
						$("#dispoaaddForm").submit();
    				});
    				
				});
			});
        </script>
</body>
</html>