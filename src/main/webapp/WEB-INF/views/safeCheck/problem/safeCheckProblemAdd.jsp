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
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addForm">
				<input class="col-md-12"  name="unitId" type="hidden" value="${unitId}">
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>问题描述</label>
					<div class="col-md-10">
						<textarea class="col-md-12" id="discrible" name="discrible" style="height:80px; resize:none;" class="col-md-12" maxlength="255"></textarea>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>发现时间</label>
					<div class="col-md-4" style="width:31%">
						<div  id="discoveryTimeDiv" name="discoveryTime" ></div>
					</div>
					<label class="col-md-2 control-label no-padding-right" style="width:21%"><span style="color:red;">*</span>检查方式</label>
					<div class="col-md-4" style="width:31%">
						<select id="modeSelect" name="mode" ></select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>检查人</label>
					<div class="col-md-4"  style="width:31%">
						<select id="checkPersionIdSelect" name="checkPersionId" ></select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>计划整改时间</label>
					<div class="col-md-4"  style="width:31%">
						<div  id="planTimeDiv" name="planTime" ></div>
					</div>
					<label class="col-md-2 control-label no-padding-right" style="width:21%"><span style="color:red;">*</span>整改责任人</label>
					<div class="col-md-4"  style="width:31%">
						<select  id="correctivePersionIdSelect" name="correctivePersionId" ></select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>是否整改</label>
					<div class="col-md-4"  style="width:31%">
						<select  id="isCorrectiveSelect" name="isCorrective"  class="col-md-12"></select>
					</div>
					<label class="col-md-2 control-label no-padding-right" style="width:21%"><span style="color:red;display:none" id="completionTimeLableNeed">*</span>整改完成时间</label>
					<div class="col-md-4"  style="width:31%">
						<div  id="completionTimeDiv" name="completionTime" ></div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;display:none" id="correctiveMeasuresLableNeed">*</span>整改措施</label>
					<div class="col-md-10">
						<textarea class="col-md-12" id="correctiveMeasures" name="correctiveMeasures" style="height:80px; resize:none;" class="col-md-12" maxlength="255"></textarea>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						备注
					</label>
					<div class="col-md-10">
						<textarea class="col-md-12" id="remark" name="remark" style="height:80px; resize:none;" class="col-md-12" maxlength="255"></textarea>
					</div>
				</div>
			</form>
			<div style="margin-right:100px;">
				<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
					<i class="ace-icon fa fa-times"></i>
					取消
				</button>
				<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i>
					保存
				</button>
			</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone','wysiwyg'], function(A){
					
					//发现时间
					var discoveryTimePicker = new A.my97datepicker({
						id: "discoveryTimeDPk",
						name: "discoveryTime",
						render:"#discoveryTimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();

					//检查方式
					var checkModeCombobox = new A.combobox({
						render : "#modeSelect",
						name : "mode",
						datasource : ${checkModeCombobox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();

					//检查人
					var checkPersionCombobox = new A.combobox({
						render : "#checkPersionIdSelect",
						name : "checkPersionId",
						datasource : ${checkPersionCombobox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();

					//计划整改时间
					var planTimePicker = new A.my97datepicker({
						id: "planTimeDPk",
						render:"#planTimeDiv",
						name: "planTime",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();

					//整改责任人
					var correctivePersionCombobox = new A.combobox({
						render : "#correctivePersionIdSelect",
						name : "correctivePersionId",
						datasource : ${correctivePersionCombobox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();

					//是否整改
					var isCorrectiveCombobox = new A.combobox({
						render : "#isCorrectiveSelect",
						name : "isCorrective",
						datasource : ${isCorrectiveCombobox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					
					//整改完成时间
					var completionTimePicker = new A.my97datepicker({
						id: "mpletionTimeDPk",
						render:"#completionTimeDiv",
						name: "completionTime",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					
					$("#isCorrectiveSelect").change(function() {
						if ($("#isCorrectiveSelect").val() =="1") {
							addForm.settings.rules["completionTime"]={required:true};
							addForm.settings.rules["correctiveMeasures"]={required:true};
							$("#completionTimeLableNeed").show();
							$("#correctiveMeasuresLableNeed").show();
							
						} else {
							addForm.settings.rules["completionTime"]={};
							addForm.settings.rules["correctiveMeasures"]={};
							$("#completionTimeLableNeed").hide();
							$("#correctiveMeasuresLableNeed").hide();
						}
					});
					
					var addForm =$('#addForm').validate({
						debug:true,
						rules:  {
							discrible:{required:true, maxlength:255},
							discoveryTime:{required:true},
							mode:{required:true, },
							checkPersionId:{required:true, },
							correctiveMeasures:{maxlength:255},
							planTime:{required:true, },
							correctivePersionId:{required:true, },
							isCorrective:{required:true}
						},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/safeCheckProblem/addEntity");
							var obj = $("#addForm").serializeObject();
							obj.discoveryTime = obj.discoveryTime + ":00";
							obj.planTime = obj.planTime + ":00";
							if (obj.completionTime != null) {
								obj.completionTime = obj.completionTime + ":00";
							}
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
					$("#saveBtn").on("click", function(){
						$("#addForm").submit();
					});
					
				});
			});
		</script>
	</body>
</html>