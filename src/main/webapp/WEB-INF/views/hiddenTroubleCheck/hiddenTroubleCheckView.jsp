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
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="editForm">
				<input class="col-md-12"  name="unitId" type="hidden" value="${unitId}">
				<input class="col-md-12"  id="id"  name="id" type="hidden" value="${entity.id}">
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>隐患描述</label>
					<div class="col-md-10">
						<textarea class="col-md-12" id="discrible" name="discrible" style="height:80px; resize:none;" class="col-md-12" maxlength="255" readonly>${entity.discrible}</textarea>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>排查时间</label>
					<div class="col-md-4" style="width:31%">
						<input  type="text"  class="col-md-12" value="${entity.discoveryTimeStr}"  readonly>
					</div>
					<label class="col-md-2 control-label no-padding-right" style="width:21%"><span style="color:red;">*</span>排查方式</label>
					<div class="col-md-4" style="width:31%">
						<input  type="text"  class="col-md-12" value="${entity.modeName}"  readonly>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>排查人</label>
					<div class="col-md-4"  style="width:31%">
						<input  type="text"  class="col-md-12" value="${entity.checkPersionName}"  readonly>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>计划整改时间</label>
					<div class="col-md-4"  style="width:31%">
						<input  type="text"  class="col-md-12" value="${entity.planTimeStr}"  readonly>
					</div>
					<label class="col-md-2 control-label no-padding-right" style="width:21%"><span style="color:red;">*</span>整改责任人</label>
					<div class="col-md-4"  style="width:31%">
						<input  type="text"  class="col-md-12" value="${entity.correctivePersionName}"  readonly>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>是否整改</label>
					<div class="col-md-4"  style="width:31%">
						<input  type="text"  class="col-md-12" value="${entity.isCorrectiveName}"  readonly>
					</div>
					<label class="col-md-2 control-label no-padding-right" style="width:21%"><span style="color:red;display:none" id="completionTimeLableNeed">*</span>整改完成时间</label>
					<div class="col-md-4"  style="width:31%">
						<input  type="text"  class="col-md-12" value="${entity.completionTimeStr}"  readonly>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;display:none" id="correctiveMeasuresLableNeed">*</span>整改措施</label>
					<div class="col-md-10">
						<textarea class="col-md-12" id="correctiveMeasures" name="correctiveMeasures" style="height:80px; resize:none;" class="col-md-12" maxlength="255" readonly>${entity.correctiveMeasures}</textarea>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						备注
					</label>
					<div class="col-md-10">
						<textarea class="col-md-12" id="remark" name="remark" style="height:80px; resize:none;" class="col-md-12" maxlength="255" readonly>${entity.remark}</textarea>
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
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone','wysiwyg'], function(A){
					var appData = ${entityJson};
					var id = $('#id').val();
					if (appData.isCorrective == '1') {
						$("#completionTimeLableNeed").show();
						$("#correctiveMeasuresLableNeed").show();
					} else {
						$("#completionTimeLableNeed").hide();
						$("#correctiveMeasuresLableNeed").hide();
					}
				});
			});
		</script>
	</body>
</html>